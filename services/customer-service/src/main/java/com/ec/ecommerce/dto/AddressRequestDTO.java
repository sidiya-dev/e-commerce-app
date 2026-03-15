package com.ec.ecommerce.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressRequestDTO {

    @NotBlank
    @Size(min = 2, max = 100)
    private String country;

    @NotBlank
    @Size(min = 2, max = 50)
    private String state;

    @NotBlank
    @Size(min = 2, max = 50)
    private String street;

    @NotBlank
    @Size(min = 2, max = 50)
    private String city;

}
