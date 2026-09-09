package org.booleanuk.app.dto;

public record SalesDto(
    Long productId,
    String productName,
    long timesSold
) {

}
