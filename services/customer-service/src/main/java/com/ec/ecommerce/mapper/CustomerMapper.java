package com.ec.ecommerce.mapper;

import com.ec.ecommerce.dto.CustomerRequestDTO;
import com.ec.ecommerce.dto.CustomerResponseDTO;
import com.ec.ecommerce.model.Customer;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    CustomerResponseDTO toDTO(Customer customer);
    Customer toEntity(CustomerRequestDTO dto);
    void updateCustomer(CustomerRequestDTO customerRequestDTO, @MappingTarget Customer customer);
}
