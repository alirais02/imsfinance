package com.ims.imsfinance.dto;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.domain.Sort.Order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageRequestDTO {
    private String sortBy;
    private Integer pageSize;
    private Integer pageNumber;

    public Pageable getPage(){
        int pageNumberValue = (pageNumber != null) ? (pageNumber < 1 ? 1 : pageNumber) : 1;
        int pageSizeValue = (pageSize != null) ? (pageSize < 1 ? 1 : pageSize) : 10;

        List<Order> orders = new ArrayList<>();
        
        if (sortBy != null && !sortBy.isEmpty()) {
            String[] parts = sortBy.split(",");
            String sortField = parts[0];
            String sortOrder = parts.length > 1 ? parts[1] : "ASC";
            Order order = new Order(Sort.Direction.fromString(sortOrder), sortField).ignoreCase();
            orders.add(order);
            orders.add(new Order(Sort.Direction.ASC, "customerName").ignoreCase());
        }

        return org.springframework.data.domain.PageRequest.of(pageNumberValue - 1, pageSizeValue, Sort.by(orders));
    }
}
