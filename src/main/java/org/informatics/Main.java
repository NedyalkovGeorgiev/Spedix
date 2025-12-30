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
import org.informatics.entity.Truck;
import org.informatics.service.ClientService;
import org.informatics.service.CompanyService;
import org.informatics.service.EmployeeService;
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
            System.out.println("--- INITIALIZING DTO-DRIVEN UNIVERSE ---");

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
            transport.setDepartureDate(LocalDateTime.now());
            transport.setArrivalDate(LocalDateTime.now().plusDays(2));
            transport.setPrice(new BigDecimal("3200"));
            transport.setCompany(company);
            transport.setClient(client);
            transport.setVehicle(truck);
            transport.setDriver(driver);
            transportService.logTransport(transport);

            System.out.println("\n--- ANALYTICS & DTO VERIFICATION ---");

            // TEST 1: Get Companies (DTO)
            List<CompanyDTO> companies = companyService.getCompanies();
            System.out.println("Companies in system: " + companies.size());
            companies.forEach(c -> System.out.println(" - " + c.getName() + " (Employees: " + c.getEmployeeCount() + ")"));

            // TEST 2: Get Drivers (DTO + Lazy Collections)
            List<DriverDTO> drivers = employeeService.getDriversDTO();
            System.out.println("\nDrivers in system: " + drivers.size());
            drivers.forEach(d -> System.out.println(" - " + d.getName() + " | Permits: " + d.getQualifications()));

            // TEST 3: Get ALL Employees (DTO Inheritance Test)
            System.out.println("\nAll Staff (Sorted by Salary):");
            employeeService.getAllEmployeesSortedBySalary().forEach(e ->
                    System.out.println(" - " + e.getName() + " [$" + e.getSalary() + "] (Company: " + e.getCompanyName() + ")"));

            // TEST 4: Get Transports
            List<TransportDTO> transports = transportService.getAllTransportsDTO();
            System.out.println("\nTransports recorded: " + transports.size());
            transports.forEach(t -> System.out.println(" - ID: " + t.getId() + " | From: " + t.getStartPoint() + " | Driver: " + t.getDriverName()));

            System.out.println("\nARCHITECTURE VERIFIED: DTOs are clean and LAZY loading is safe!");

        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
            e.printStackTrace();
        } finally {
            SessionFactoryUtil.close();
        }
    }
}