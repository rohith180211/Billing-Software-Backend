package com.example.billingsoftware.io;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@Builder
@NoArgsConstructor
public class ItemRequest {
    private String name;
    private BigDecimal price;
    private String categoryId;
    private String description;
}
