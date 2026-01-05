package org.informatics.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "tanker")
@Getter
@Setter
@NoArgsConstructor
public class Tanker extends Truck {
    @Positive
    private double volumeCapacity;
    @Enumerated(EnumType.STRING)
    @NotNull(message = "Tanker cargo type is required")
    private CargoType type;
    private boolean isPressurized;
}
