package org.informatics.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "cargo_transport")
@Getter
@Setter
@NoArgsConstructor
public class CargoTransport extends Transport {
    private double weight;
    private boolean isHazardous;
    private boolean isOversized;
    private Double length;
    private Double width;
    private Double height;
    private Double requiredMinTemp;
    private Double requiredMaxTemp;
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CargoType cargoType = CargoType.GENERAL;
}
