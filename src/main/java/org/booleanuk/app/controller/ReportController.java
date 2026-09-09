package org.booleanuk.app.controller;

import java.util.List;

import org.booleanuk.app.dto.ValueDto;
import org.booleanuk.app.dto.SalesDto;
import org.booleanuk.app.dto.OrderValueDto;
import org.booleanuk.app.service.ReportService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController 
@RequestMapping ("/reports")
public class ReportController {
    private final ReportService reportService;
    public ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping ("customer-values")
    public List<ValueDto> getCustomerValues() {
        return reportService.getCustomerValues();
    }

    @GetMapping ("product-sales")
    public List<SalesDto> getProductSales() {
        return reportService.getProductSales();
    }

    @GetMapping ("orders-by-values")
    public List<OrderValueDto> getOrderByValue() {
        return reportService.getOrdersByValue();
    }
}
