package org.booleanuk.app.dto;

import java.math.BigDecimal;

public record ValueDto(
    Long customerId,
    String customerName,
    BigDecimal totalValue
) {

}
