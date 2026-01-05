package org.informatics.entity;

import jakarta.persistence.Column;
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
@Table(name = "cargo_transport")
@Getter
@Setter
@NoArgsConstructor
public class CargoTransport extends Transport {
    
    @Positive(message = "Weight must be a positive number")
    private double weight;
    
    private boolean isHazardous;
    private boolean isOversized;
    
    @Positive(message = "Length must be positive")
    private Double length;
    
    @Positive(message = "Width must be positive")
    private Double width;
    
    @Positive(message = "Height must be positive")
    private Double height;
    
    private Double requiredMinTemp;
    private Double requiredMaxTemp;
    
    @NotNull(message = "Cargo type is required")
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private CargoType cargoType = CargoType.GENERAL;
}
