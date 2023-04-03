package com.example.order.management.domain;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class Customer {

    private String id;

    private String firstName;

    private String lastName;
}
