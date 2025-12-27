package org.informatics.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@Table(name = "passenger_transport")
@Getter
@Setter
@NoArgsConstructor
public class PassengerTransport extends Transport {
    private int passengerCount;
}
