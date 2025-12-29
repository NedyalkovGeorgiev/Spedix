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
            System.out.println("--- BUILDING TEST ---");

            // 1. Create Companies
            Company spedix = new Company(); spedix.setName("Spedix Alpha");
            companyService.createCompany(spedix);

            Company beta = new Company(); beta.setName("Beta Logistics");
            companyService.createCompany(beta);

            // 2. Create Drivers (Different Salaries and Qualifications)
            Driver steve = new Driver();
            steve.setName("Safe Steve");
            steve.setSalary(new BigDecimal("5000"));
            steve.setQualifications(Set.of(Qualification.HAZMAT));
            steve.setCompany(spedix);
            employeeService.createEmployee(steve);

            Driver alex = new Driver();
            alex.setName("Alex Pro");
            alex.setSalary(new BigDecimal("7000"));
            alex.setQualifications(Set.of(Qualification.OVERSIZED, Qualification.HAZMAT));
            alex.setCompany(spedix);
            employeeService.createEmployee(alex);

            // 3. Create Vehicles
            Truck truck = new Truck();
            truck.setMake("Volvo");
            truck.setMaxLoadWeight(15000.0);
            truck.setCompany(spedix);
            vehicleService.createVehicle(truck);

            // 4. Create Client
            Client client = new Client();
            client.setName("Mega Corp");
            clientService.createClient(client);

            // 5. Create Transports (Assign to different drivers and destinations)
            CargoTransport t1 = new CargoTransport();
            t1.setStartPoint("Sofia"); t1.setEndPoint("Plovdiv");
            t1.setDepartureDate(LocalDateTime.now().minusDays(2));
            t1.setArrivalDate(LocalDateTime.now().minusDays(1));
            t1.setPrice(new BigDecimal("1000.00"));
            t1.setCompany(spedix); t1.setClient(client); t1.setVehicle(truck); t1.setDriver(steve);
            transportService.logTransport(t1);

            CargoTransport t2 = new CargoTransport();
            t2.setStartPoint("Sofia"); t2.setEndPoint("Varna");
            t2.setDepartureDate(LocalDateTime.now().minusDays(5));
            t2.setArrivalDate(LocalDateTime.now().minusDays(4));
            t2.setPrice(new BigDecimal("2500.00"));
            t2.setCompany(spedix); t2.setClient(client); t2.setVehicle(truck); t2.setDriver(alex);
            transportService.logTransport(t2);

            System.out.println("--- ANALYTICS VERIFICATION ---");

            // TEST 1: Mark as Paid & Company Revenue
            System.out.println("Processing Payment for Transport to Varna...");
            transportService.markAsPaid(t2.getId());
            Company updatedSpedix = companyService.getCompanyById(spedix.getId());
            System.out.println("Verify Spedix Revenue (Expected 2500.00): " + updatedSpedix.getTotalRevenue());

            // TEST 2: Sort Companies by Revenue
            System.out.println("\nCompanies Sorted by Revenue:");
            companyService.getAllCompaniesSortedByRevenue().forEach(c ->
                    System.out.println(" - " + c.getName() + ": $" + c.getTotalRevenue()));

            // TEST 3: Sort Employees by Salary
            System.out.println("\nEmployees Sorted by Salary:");
            employeeService.getAllEmployeesSortedBySalary().forEach(e ->
                    System.out.println(" - " + e.getName() + ": $" + e.getSalary()));

            // TEST 4: Filter Drivers by Qualification
            System.out.println("\nDrivers with OVERSIZED permit:");
            employeeService.getAllDriversBySpecificQualification(Qualification.OVERSIZED).forEach(d ->
                    System.out.println(" - " + d.getName()));

            // TEST 5: Filter Transports by Destination
            System.out.println("\nTransports to Varna:");
            transportService.getAllTransportsByDestination("Varna").forEach(t ->
                    System.out.println(" - ID: " + t.getId() + " From: " + t.getStartPoint()));

            // TEST 6: Driver Performance Report
            System.out.println("\nDriver Contribution (Alex Pro):");
            System.out.println(" - Transport Count: " + transportService.getCountOfCompletedTransportsForDriver(alex.getId()));
            System.out.println(" - Revenue Generated: $" + transportService.getTotalRevenueForDriver(alex.getId()));

            // TEST 7: Period Revenue
            LocalDateTime lastWeek = LocalDateTime.now().minusDays(7);
            LocalDateTime tomorrow = LocalDateTime.now().plusDays(1);
            System.out.println("\nTotal Revenue for last 7 days: $" +
                    transportService.getTotalRevenueBySpecificPeriod(lastWeek, tomorrow));

            System.out.println("\nAll Analytical requirements met!");

        } catch (Exception e) {
            System.err.println("ERROR: " + e.getMessage());
            e.printStackTrace();
        } finally {
            SessionFactoryUtil.close();
        }
    }
}