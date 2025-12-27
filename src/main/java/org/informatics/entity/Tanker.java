package org.informatics.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tanker")
@Getter
@Setter
@NoArgsConstructor
public class Tanker extends Vehicle {
    private double volumeCapacity;
    @Enumerated(EnumType.STRING)
    private CargoType type;
    private boolean isPressurized;
}
