package org.informatics;

import org.informatics.configuration.SessionFactoryUtil;
import org.informatics.dto.CompanyDTO;
import org.informatics.dto.DriverDTO;
import org.informatics.dto.TransportDTO;
import org.informatics.entity.CargoTransport;
import org.informatics.entity.Client;
import org.informatics.entity.Company;
import org.informatics.entity.Driver;
import org.informatics.entity.Qualification;
import org.informatics.entity.Staff;
import org.informatics.entity.Truck;
import org.informatics.service.ClientService;
import org.informatics.service.CompanyService;
import org.informatics.service.EmployeeService;
import org.informatics.service.FileService;
import org.informatics.service.TransportService;
import org.informatics.service.VehicleService;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;

public class Main {
    public static void main(String[] args) {

        CompanyService companyService = new CompanyService();
        EmployeeService employeeService = new EmployeeService();
        VehicleService vehicleService = new VehicleService();
        ClientService clientService = new ClientService();
        TransportService transportService = new TransportService();

        try {
            System.out.println("--- INITIALIZING ---");

            // 1. Create Base Data
            Company company = new Company();
            company.setName("Spedix Elite");
            companyService.createCompany(company);

            Driver driver = new Driver();
            driver.setName("Pro Driver Alex");
            driver.setSalary(new BigDecimal("6500"));
            driver.setQualifications(Set.of(Qualification.HAZMAT, Qualification.OVERSIZED));
            driver.setCompany(company);
            employeeService.createEmployee(driver);

            Driver driver2 = new Driver();
            driver2.setName("Safe Sarah");
            driver2.setSalary(new BigDecimal("5800"));
            driver2.setQualifications(Set.of(Qualification.HAZMAT));
            driver2.setCompany(company);
            employeeService.createEmployee(driver2);

            Staff sarah = new Staff();
            sarah.setName("Sarah Manager");
            sarah.setSalary(new BigDecimal("7000"));
            sarah.setCompany(company);
            employeeService.createEmployee(sarah);

            try {
                System.out.println("\nTesting Validation: Attempting to create employee with negative salary...");
                Staff badStaff = new Staff();
                badStaff.setName("Bad Data");
                badStaff.setSalary(new BigDecimal("-100"));
                badStaff.setCompany(company);
                employeeService.createEmployee(badStaff);
            } catch (IllegalArgumentException e) {
                System.out.println("Validation Shield correctly blocked bad data: " + e.getMessage());
            }

            Truck truck = new Truck();
            truck.setMake("Scania");
            truck.setMaxLoadWeight(20000.0);
            truck.setCompany(company);
            vehicleService.createVehicle(truck);

            Client client = new Client();
            client.setName("Global Retail");
            clientService.createClient(client);

            // 2. Create Transport
            CargoTransport transport = new CargoTransport();
            transport.setStartPoint("Sofia");
            transport.setEndPoint("Munich");
            transport.setWeight(1500.0);
            transport.setDepartureDate(LocalDateTime.now());
            transport.setArrivalDate(LocalDateTime.now().plusDays(2));
            transport.setPrice(new BigDecimal("3200"));
            transport.setCompany(company);
            transport.setClient(client);
            transport.setVehicle(truck);
            transport.setDriver(driver);
            transportService.logTransport(transport);

            CargoTransport transport2 = new CargoTransport();
            transport2.setStartPoint("Berlin");
            transport2.setEndPoint("Paris");
            transport2.setWeight(2200.0);
            transport2.setDepartureDate(LocalDateTime.now().plusDays(1));
            transport2.setArrivalDate(LocalDateTime.now().plusDays(3));
            transport2.setPrice(new BigDecimal("2100.50"));
            transport2.setCompany(company);
            transport2.setClient(client);
            transport2.setVehicle(truck);
            transport2.setDriver(driver2);
            transportService.logTransport(transport2);

            // Mark first transport as paid to generate revenue
            transportService.markAsPaid(1L);

            System.out.println("\n--- ANALYTICS & DTO VERIFICATION ---");

            // TEST 1: Get Companies (DTO)
            List<CompanyDTO> companies = companyService.getCompanies();
            System.out.println("Companies in system: " + companies.size());
            companies.forEach(c -> System.out.println(" - " + c.getName() + " (Employees: " + c.getEmployeeCount() + ")"));

            // TEST 2: Companies Sorted by Revenue
            System.out.println("\nCompanies Sorted by Revenue:");
            companyService.getAllCompaniesSortedByRevenue().forEach(c ->
                    System.out.println(" - " + c.getName() + ": $" + c.getRevenue()));

            // TEST 3: Filtering Transports by Destination
            System.out.println("\nTransports to Munich:");
            transportService.getAllTransportsByDestination("Munich").forEach(t ->
                    System.out.println(" - Transport ID: " + t.getId() + " from " + t.getStartPoint()));

            // TEST 4: Driver Performance Analytics
            System.out.println("\nPerformance Stats for Alex:");
            System.out.println(" - Transports Completed: " + transportService.getCountOfCompletedTransportsForDriver(driver.getId()));
            System.out.println(" - Revenue Contribution: $" + transportService.getTotalRevenueForDriver(driver.getId()));

            // TEST 5: Total Revenue for Specific Period
            LocalDateTime start = LocalDateTime.now().minusDays(1);
            LocalDateTime end = LocalDateTime.now().plusDays(7);
            System.out.println("\nTotal Revenue (Next 7 days): $" + transportService.getTotalRevenueBySpecificPeriod(start, end));

            // TEST 6: Business Rule Validation (HAZMAT Check)
            try {
                System.out.println("\nTesting Business Rule: Assigning HAZMAT cargo to non-qualified driver...");
                Driver simpleDriver = new Driver();
                simpleDriver.setName("Unqualified Bob");
                simpleDriver.setSalary(new BigDecimal("3000"));
                simpleDriver.setCompany(company);
                employeeService.createEmployee(simpleDriver);

                CargoTransport hazmatTransport = new CargoTransport();
                hazmatTransport.setStartPoint("Sofia");
                hazmatTransport.setEndPoint("Varna");
                hazmatTransport.setWeight(500.0);
                hazmatTransport.setPrice(new BigDecimal("1000"));
                hazmatTransport.setDepartureDate(LocalDateTime.now());
                hazmatTransport.setArrivalDate(LocalDateTime.now().plusDays(1));
                hazmatTransport.setHazardous(true);
                hazmatTransport.setCompany(company);
                hazmatTransport.setClient(client);
                hazmatTransport.setVehicle(truck);
                hazmatTransport.setDriver(simpleDriver);

                transportService.logTransport(hazmatTransport);
            } catch (IllegalArgumentException e) {
                System.out.println("Business Logic Shield correctly blocked Bob: " + e.getMessage());
            }

            // TEST 7: Get Drivers (DTO + Lazy Collections)
            List<DriverDTO> drivers = employeeService.getDriversDTO();
            System.out.println("\nDrivers in system: " + drivers.size());
            drivers.forEach(d -> System.out.println(" - " + d.getName() + " | Permits: " + d.getQualifications()));

            // TEST 8: Get ALL Employees (DTO Inheritance Test)
            System.out.println("\nAll Staff (Sorted by Salary):");
            employeeService.getAllEmployeesSortedBySalary().forEach(e ->
                    System.out.println(" - " + e.getName() + " [$" + e.getSalary() + "] (Company: " + e.getCompanyName() + ")"));

            // TEST 9: Get Transports
            List<TransportDTO> transports = transportService.getAllTransportsDTO();
            System.out.println("\nTransports recorded: " + transports.size());
            transports.forEach(t -> System.out.println(
                    String.format(" - ID: %d | From: %s | To: %s | Price: $%.2f | Driver: %s",
                            t.getId(), t.getStartPoint(), t.getEndPoint(), t.getPrice(), t.getDriverName())
            ));

            // TEST 10: Export to File
            System.out.println("\nExporting Transports to File...");
            FileService fileService = new FileService();
            fileService.exportTransportsReport(transports, "transport_report.txt");

            System.out.println("\nARCHITECTURE VERIFIED: DTOs are clean and LAZY loading is safe!");

        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
            e.printStackTrace();
        } finally {
            SessionFactoryUtil.close();
        }
    }
}
