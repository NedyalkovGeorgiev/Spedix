package org.informatics.dto;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TransportDTO {
    private Long id;
    private String startPoint;
    private String endPoint;
    private LocalDateTime departureDate;
    private BigDecimal price;
    private String driverName;
    private String clientName;
    private String vehicleInfo;
}
