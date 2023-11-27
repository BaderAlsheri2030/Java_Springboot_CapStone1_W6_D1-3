package com.example.amazonsystem.Model;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class Product {
    @NotEmpty(message = "product id cannot be empty")
    private String id;
    @NotEmpty(message = "Product name cannot be empty")
    @Size(min = 4, message = "Product name must be more than 3 length long")
    private String name;
    @NotNull(message = "Price cannot be empty")
    @Positive(message = "price must be a positive number")
    private double price;
    @NotEmpty(message = "Category ID cannot be empty")
    private String categoryID;

}
