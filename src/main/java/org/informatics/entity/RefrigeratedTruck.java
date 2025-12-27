package org.informatics.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "refrigerated_truck")
@Getter
@Setter
@NoArgsConstructor
public class RefrigeratedTruck extends Truck {
    private double minTemperature;
    private double maxTemperature;
}
