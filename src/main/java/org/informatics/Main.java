package org.informatics;

import org.hibernate.Session;
import org.informatics.configuration.SessionFactoryUtil;
import org.informatics.entity.CargoTransport;
import org.informatics.entity.CargoType;
import org.informatics.entity.Client;
import org.informatics.entity.Company;
import org.informatics.entity.Driver;
import org.informatics.entity.Employee;
import org.informatics.entity.Qualification;
import org.informatics.entity.RefrigeratedTruck;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Set;

public class Main {
    public static void main(String[] args) {

        try (Session session = SessionFactoryUtil.getSessionFactory().openSession()) {
            session.beginTransaction();

            Company company = new Company();
            company.setName("Global Spedix Enterprise");
            company.setAddress("123 Logistics Blvd");
            session.persist(company);

            Driver driver = new Driver();
            driver.setName("Captain Jack");
            driver.setSalary(new BigDecimal("4500.50"));
            driver.setCompany(company);
            driver.setQualifications(Set.of(Qualification.HAZMAT, Qualification.HEAVY_LOAD));
            session.persist(driver);

            RefrigeratedTruck reefer = new RefrigeratedTruck();
            reefer.setMake("Volvo");
            reefer.setModel("FH16 Ice");
            reefer.setCompany(company);
            reefer.setMaxLoadWeight(25000.0);
            reefer.setMinTemperature(-25.0);
            reefer.setMaxTemperature(-15.0);
            session.persist(reefer);

            Client client = new Client();
            client.setName("Frozen Foods Corp");
            client.setPhoneNumber("08932322");
            client.setEmail("frozenfoodscorp@example.com");
            session.persist(client);

            CargoTransport transport = new CargoTransport();
            transport.setStartPoint("Port of Burgas");
            transport.setEndPoint("Sofia Central Warehouse");
            transport.setDepartureDate(LocalDateTime.now());
            transport.setArrivalDate(LocalDateTime.now().plusHours(6));
            transport.setPrice(new BigDecimal("1200.00"));
            transport.setPaid(false);
            transport.setCompany(company);
            transport.setClient(client);
            transport.setVehicle(reefer);
            transport.setDriver(driver);
            transport.setWeight(15000.0);
            transport.setHazardous(false);
            transport.setCargoType(CargoType.PERISHABLE);
            session.persist(transport);

            session.getTransaction().commit();
            System.out.println("SUCCESS");
        } catch (Exception e) {
            System.err.println("ERROR");
            e.printStackTrace();
        } finally {
            SessionFactoryUtil.close();
        }

    }
}