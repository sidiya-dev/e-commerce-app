package com.ec.ecommerce.dto;

import com.ec.ecommerce.model.Address;

public record CustomerResponseDTO(
        String id,
        String firstName,
        String lastName,
        String email,
        Address address
) {
}
