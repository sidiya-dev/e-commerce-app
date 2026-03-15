package com.ec.ecommerce.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.validation.annotation.Validated;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Address {
    @NotBlank
    @Size(min = 2, max = 50)
    private String country;

    @NotBlank
    @Size(min = 2, max = 50)
    private String state;

    @NotBlank
    @Size(min = 2, max = 50)
    private String city;

    @NotBlank
    @Size(min = 2, max = 50)
    private String street;
}
