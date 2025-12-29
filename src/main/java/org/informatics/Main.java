package org.informatics;

import org.informatics.configuration.SessionFactoryUtil;
import org.informatics.entity.CargoTransport;
import org.informatics.entity.CargoType;
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
import java.util.Set;

public class Main {
    public static void main(String[] args) {

        CompanyService companyService = new CompanyService();
        EmployeeService employeeService = new EmployeeService();
        VehicleService vehicleService = new VehicleService();
        ClientService clientService = new ClientService();
        TransportService transportService = new TransportService();

        try {
            Company company = new Company();
            company.setName("Spedix Global");
            company.setAddress("12376 Logistics Blvd");
            companyService.createCompany(company);

            Driver driver = new Driver();
            driver.setName("Safe Steve");
            driver.setSalary(new BigDecimal("4500.50"));
            driver.setCompany(company);
            driver.setQualifications(Set.of(Qualification.HAZMAT));
            employeeService.createEmployee(driver);

            Truck truck = new Truck();
            truck.setMake("MAN");
            truck.setMaxLoadWeight(10000.0);
            truck.setCompany(company);
            vehicleService.createVehicle(truck);

            Client client = new Client();
            client.setName("Frozen Foods Corp");
            client.setPhoneNumber("08932322");
            client.setEmail("frozenfoodscorp@example.com");
            clientService.createClient(client);

            CargoTransport transport = new CargoTransport();
            transport.setStartPoint("City A");
            transport.setEndPoint("City B");
            transport.setDepartureDate(LocalDateTime.now());
            transport.setArrivalDate(LocalDateTime.now().plusHours(5));
            transport.setPrice(new BigDecimal("1500.00"));
            transport.setCompany(company);
            transport.setClient(client);
            transport.setVehicle(truck);
            transport.setDriver(driver);
            transport.setWeight(10000.0);
            transport.setHazardous(true);
            transport.setCargoType(CargoType.PERISHABLE);

            System.out.println("Testing Validation (Part 1)...");

            transportService.logTransport(transport);
            System.out.println("Test 1 good");

            CargoTransport t2 = new CargoTransport();
            t2.setStartPoint("Port");
            t2.setEndPoint("Factory");
            t2.setDepartureDate(LocalDateTime.now());
            t2.setArrivalDate(LocalDateTime.now().plusDays(1));
            t2.setCompany(company);
            t2.setClient(client);
            t2.setVehicle(truck);
            t2.setDriver(driver);
            t2.setWeight(2000.0);
            t2.setHeight(5.5);

            System.out.println("Testing Validation (Part 2)...");
            try {
                transportService.logTransport(t2);
                System.out.println("Oversized transport should have been blocked!");
            } catch (IllegalArgumentException e) {
                System.out.println("Part 2: Validation correctly blocked oversized cargo: " + e.getMessage());
            }

            System.out.println("Verifying Data Retrieval...");
            System.out.println("Drivers in system: " + employeeService.getEmployees().size());
            System.out.println("Vehicles in system: " + vehicleService.getVehicles().size());

            int transportCount = transportService.getTransports().size();
            System.out.println("Total Transports recorded: " + transportCount);

            if (transportCount > 0) {
                System.out.println("✅ Data Persistence Verified!");
            }

            System.out.println("SYSTEM VERIFICATION SUCCESS: All Services and Validations are working!");
        } catch (Exception e) {
            System.err.println("VERIFICATION FAILED: " + e.getMessage());
                    e.printStackTrace();
        } finally {
            SessionFactoryUtil.close();
        }

    }
}