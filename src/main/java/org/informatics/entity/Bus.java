package org.informatics.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "bus")
@Getter
@Setter
@NoArgsConstructor
public class Bus extends Vehicle {
    @Positive(message = "Max passengers must be positive")
    private int maxPassengers;
}
