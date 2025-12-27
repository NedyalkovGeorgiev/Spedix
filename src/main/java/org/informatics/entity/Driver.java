package org.informatics.entity;

import java.util.Set;

import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "driver")
@Getter
@Setter
@NoArgsConstructor
public class Driver extends Employee {
    @ElementCollection(targetClass =  Qualification.class)
    @CollectionTable(name = "driver_qualifications", joinColumns = @JoinColumn(name = "driver_id"))
    @Enumerated(EnumType.STRING)
    @Column(name = "qualification")
    private Set<Qualification> qualifications;
}
