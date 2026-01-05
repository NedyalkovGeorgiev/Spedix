package org.informatics.service;

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
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class MappingTest {

    @Test
    void testDriverToDTOConversion() {
        EmployeeService employeeService = new EmployeeService();

        Company co = new Company();
        co.setName("Spedix Logistics");
        Driver driver = new Driver();
        driver.setName("Alex");
        driver.setSalary(new BigDecimal("5000"));
        driver.setCompany(co);
        driver.setQualifications(Set.of(Qualification.HAZMAT));

        DriverDTO dto = employeeService.convertToDriverDTO(driver);

        assertEquals("Alex", dto.getName());
        assertEquals("Spedix Logistics", dto.getCompanyName());
        assertTrue(dto.getQualifications().contains("HAZMAT"));
    }

    @Test
    void testCompanyToDTOConversion() {
        CompanyService companyService = new CompanyService();

        Company co = new Company();
        co.setName("Beta Logistics");
        co.setAddress("Berlin");
        co.setTotalRevenue(new BigDecimal("10000"));
        co.setEmployees(Arrays.asList(new Driver(), new Staff()));

        CompanyDTO dto = companyService.convertToDTO(co);

        assertEquals("Beta Logistics", dto.getName());
        assertEquals(2, dto.getEmployeeCount());
        assertEquals(new BigDecimal("10000"), dto.getRevenue());
    }

    @Test
    void testTransportToDTOConversion() {
        TransportService transportService = new TransportService();

        CargoTransport cargo = new CargoTransport();
        cargo.setId(100L);
        cargo.setStartPoint("Sofia");
        cargo.setEndPoint("London");
        cargo.setPrice(new BigDecimal("2500"));

        Driver d = new Driver();
        d.setName("Speedy Steve");
        Client c = new Client();
        c.setName("Mega Corp");
        Truck v = new Truck();
        v.setMake("Volvo");
        v.setModel("FH16");

        cargo.setDriver(d);
        cargo.setClient(c);
        cargo.setVehicle(v);

        TransportDTO dto = transportService.convertToDTO(cargo);

        assertEquals(100L, dto.getId());
        assertEquals("Speedy Steve", dto.getDriverName());
        assertEquals("Mega Corp", dto.getClientName());
        assertTrue(dto.getVehicleInfo().contains("Volvo FH16"));
    }
}
