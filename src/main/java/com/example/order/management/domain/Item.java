package com.example.order.management.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Item {
    private String id;

    private String desc;

    private int quantity;

}
