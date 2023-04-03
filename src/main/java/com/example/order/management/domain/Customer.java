package com.example.order.management.domain;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Customer {

    private String id;

    private String firstName;

    private String lastName;
}
