package org.booleanuk.app.dto;

import java.math.BigDecimal;

public record OrderValueDto(
    Long orderId,
    BigDecimal totalValue
) {

}
