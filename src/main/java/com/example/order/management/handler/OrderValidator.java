package com.example.order.management.handler;

import com.example.order.management.domain.Customer;
import com.example.order.management.domain.Order;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class OrderValidator implements Validator {
    @Override
    public boolean supports(Class<?> clazz) {
        return Order.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Order order = (Order) target;
        Customer customer = order.getCustomer();
        if (customer == null || customer.getFirstName() == null || customer.getLastName() == null) {
            errors.rejectValue("customer", "invalid.customer", "Invalid customer details");
        }
        if (order.getItems() == null || order.getItems().size() == 0) {
            errors.rejectValue(
                    "items",
                    "field.min.length",
                    new Object[] {Integer.valueOf(1)},
                    "An order should contain at least 1 item.");
        } else {
            order.getItems().stream()
                    .forEach(
                            i -> {
                                if (i.getQuantity() < 1) {
                                    errors.rejectValue(
                                            "items",
                                            "invalid quantity",
                                            "Minimum item quantity should be 1.");
                                }
                            });
        }
    }
}
