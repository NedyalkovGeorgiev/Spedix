package org.informatics.entity;

import java.math.BigDecimal;
import java.util.List;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "company")
@Getter
@Setter
@NoArgsConstructor
public class Company {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false, length = 100)
    private String name;
    private String address;
    private BigDecimal totalRevenue = BigDecimal.ZERO;
    @OneToMany(mappedBy = "company")
    private List<Employee> employees;
    @OneToMany(mappedBy = "company")
    private List<Vehicle> vehicles;
    @OneToMany(mappedBy = "company")
    private List<Transport> transports;
}
