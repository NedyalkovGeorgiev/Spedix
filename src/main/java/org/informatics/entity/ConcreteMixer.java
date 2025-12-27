package org.informatics.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "concrete_mixer")
@Getter
@Setter
@NoArgsConstructor
public class ConcreteMixer extends Truck {
    private double drumCapacity;
}
