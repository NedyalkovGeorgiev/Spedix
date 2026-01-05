package org.informatics.service;

import org.informatics.entity.Company;
import org.informatics.entity.Staff;
import org.informatics.validator.EntityValidator;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.*;

public class EntityValidatorTest {

    @Test
    void testBlankCompanyNameShouldFail() {
        Company company = new Company();
        company.setName("");

        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            EntityValidator.validate(company);
        });
        assertTrue(ex.getMessage().contains("Company name is required"));
    }

    @Test
    void testNegativeSalaryShouldFail() {
        Staff staff = new Staff();
        staff.setName("John");
        staff.setSalary(new BigDecimal("-500"));

        Exception ex = assertThrows(IllegalArgumentException.class, () -> {
            EntityValidator.validate(staff);
        });
        assertTrue(ex.getMessage().contains("must be a positive number"));
    }
}
