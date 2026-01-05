package org.informatics.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "truck")
@Getter
@Setter
@NoArgsConstructor
public class Truck extends Vehicle {
    @Positive(message = "Max load weight must be positive")
    private double maxLoadWeight;
}
