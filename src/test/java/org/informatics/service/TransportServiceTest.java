package org.informatics.service;

import org.informatics.entity.*;
import org.informatics.configuration.TransportConfig;
import org.informatics.dto.TransportDTO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.io.File;

import static org.junit.jupiter.api.Assertions.*;

public class TransportServiceTest {

    private TransportService transportService;
    private EmployeeService employeeService;
    private CompanyService companyService;
    private ClientService clientService;
    private VehicleService vehicleService;

    @BeforeEach
    void setUp() {
        transportService = new TransportService();
        employeeService = new EmployeeService();
        companyService = new CompanyService();
        clientService = new ClientService();
        vehicleService = new VehicleService();
    }

    private CargoTransport createCargoWithDependencies(Set<Qualification> driverQuals) {
        Company co = new Company(); co.setName("Test Co");
        companyService.createCompany(co);

        Driver driver = new Driver(); 
        driver.setName("Test Driver"); driver.setSalary(new BigDecimal("1000"));
        driver.setQualifications(driverQuals);
        driver.setCompany(co);
        employeeService.createEmployee(driver);

        Truck truck = new Truck(); truck.setMake("Test Truck"); truck.setMaxLoadWeight(10000.0);
        vehicleService.createVehicle(truck);

        Client client = new Client(); client.setName("Test Client");
        clientService.createClient(client);

        CargoTransport cargo = new CargoTransport();
        cargo.setStartPoint("A"); cargo.setEndPoint("B");
        cargo.setWeight(500.0); cargo.setPrice(new BigDecimal("100"));
        cargo.setDepartureDate(LocalDateTime.now());
        cargo.setArrivalDate(LocalDateTime.now().plusDays(1));
        
        cargo.setDriver(driver);
        cargo.setVehicle(truck);
        cargo.setClient(client);
        cargo.setCompany(co);
        
        return cargo;
    }

    @Test
    void testOversizedHeightDetection() {
        CargoTransport cargo = createCargoWithDependencies(Set.of(Qualification.OVERSIZED));
        cargo.setHeight(TransportConfig.LEGAL_HEIGHT_LIMIT + 1.0); 
        transportService.validateBusinessRules(cargo);
        assertTrue(cargo.isOversized(), "Cargo should be flagged as oversized!");
    }

    @Test
    void testUnqualifiedHazmatDriverShouldFail() {
        CargoTransport cargo = createCargoWithDependencies(Set.of());
        cargo.setHazardous(true);
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            transportService.validateBusinessRules(cargo);
        });
        assertEquals("Driver not qualified for Hazardous Cargo!", exception.getMessage());
    }

    @Test
    void testInvalidDatesShouldFail() {
        CargoTransport cargo = createCargoWithDependencies(Set.of());
        cargo.setDepartureDate(LocalDateTime.now().plusDays(10));
        cargo.setArrivalDate(LocalDateTime.now().plusDays(1)); 
        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            transportService.validateBusinessRules(cargo);
        });
        assertTrue(exception.getMessage().contains("Arrival cannot be before Departure"));
    }

    @Test
    void testWeightLimitExceededShouldFail() {
        CargoTransport cargo = createCargoWithDependencies(Set.of());
        cargo.setWeight(15000.0);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            transportService.validateBusinessRules(cargo);
        });
        assertEquals("Cargo weight exceeds truck capacity!", exception.getMessage());
    }

    @Test
    void testRefrigeratedTruckTempRangeExceededShouldFail() {
        CargoTransport cargo = createCargoWithDependencies(Set.of());
        
        RefrigeratedTruck reefer = new RefrigeratedTruck();
        reefer.setMinTemperature(-20.0f);
        reefer.setMaxTemperature(5.0f);
        reefer.setMaxLoadWeight(10000.0);
        reefer.setMake("ThermoKing");
        vehicleService.createVehicle(reefer);
        
        cargo.setVehicle(reefer);
        cargo.setRequiredMinTemp(-25.0);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            transportService.validateBusinessRules(cargo);
        });
        assertEquals("Truck cannot reach required minimum temperature!", exception.getMessage());
    }

    @Test
    void testPassengerCapacityExceededShouldFail() {
        Company co = new Company(); co.setName("Bus Co");
        companyService.createCompany(co);
        Driver driver = new Driver(); driver.setName("Bus Dave"); driver.setSalary(new BigDecimal("1000"));
        driver.setQualifications(Set.of()); driver.setCompany(co);
        employeeService.createEmployee(driver);
        Client client = new Client(); client.setName("Tour Group");
        clientService.createClient(client);

        Bus bus = new Bus(); bus.setMaxPassengers(50); bus.setMake("Mercedes");
        vehicleService.createVehicle(bus);

        PassengerTransport transport = new PassengerTransport();
        transport.setPassengerCount(60);
        transport.setStartPoint("A"); transport.setEndPoint("B");
        transport.setDepartureDate(LocalDateTime.now());
        transport.setArrivalDate(LocalDateTime.now().plusDays(1));
        transport.setPrice(new BigDecimal("500"));
        transport.setDriver(driver); transport.setVehicle(bus); transport.setClient(client);

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            transportService.validateBusinessRules(transport);
        });
        assertEquals("Passenger count exceeds bus capacity!", exception.getMessage());
    }

    @Test
    void testMarkAsPaidUpdatesCompanyRevenue() {
        // 1. Setup
        CargoTransport transport = createCargoWithDependencies(Set.of());
        BigDecimal price = new BigDecimal("5000.00");
        transport.setPrice(price);
        transportService.logTransport(transport); 

        Company company = transport.getCompany();
        assertNotNull(company, "Transport must have an assigned company");
        
        BigDecimal initialRevenue = company.getTotalRevenue() != null ? company.getTotalRevenue() : BigDecimal.ZERO;

        // 2. Execute
        transportService.markAsPaid(transport.getId());

        // 3. Verify
        Company updatedCompany = companyService.getCompanyById(company.getId());
        BigDecimal expectedRevenue = initialRevenue.add(price);
        
        assertEquals(0, expectedRevenue.compareTo(updatedCompany.getTotalRevenue()),
                "Company revenue should increase by the transport price!");
    }

    @Test
    void testFileReportExport() {
        // 1. Setup - Create a transport
        createCargoWithDependencies(Set.of());
        List<TransportDTO> dtos = transportService.getAllTransportsDTO();
        
        // 2. Execute
        FileService fileService = new FileService();
        String testPath = "test_report_export.txt";
        fileService.exportTransportsReport(dtos, testPath);
        
        // 3. Verify
        File file = new File(testPath);
        assertTrue(file.exists(), "Report file should be created");
        assertTrue(file.length() > 0, "Report file should not be empty");
        
        // Cleanup
        if (file.exists()) {
            file.delete();
        }
    }
}
