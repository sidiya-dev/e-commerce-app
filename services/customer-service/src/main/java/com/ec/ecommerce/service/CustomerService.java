package com.ec.ecommerce.service;

import com.ec.ecommerce.dto.CustomerRequestDTO;
import com.ec.ecommerce.dto.CustomerResponseDTO;
import com.ec.ecommerce.exception.CustomerNotFoundException;
import com.ec.ecommerce.exception.EmailAlreadyExistsException;
import com.ec.ecommerce.mapper.CustomerMapper;
import com.ec.ecommerce.model.Customer;
import com.ec.ecommerce.repository.CustomerRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@AllArgsConstructor
@Service
public class CustomerService {
    final private CustomerRepository customerRepository;
    final private CustomerMapper customerMapper;

    public List<CustomerResponseDTO> getAllCustomers() {

        return customerRepository.findAll()
                .stream()
                .map(customerMapper::toDTO)
                .toList();
    }

    public CustomerResponseDTO createCustomer(CustomerRequestDTO customerRequestDTO) {
        validateEmailUnique(customerRequestDTO.getEmail());
        Customer customer = customerMapper.toEntity(customerRequestDTO);
        customer = customerRepository.save(customer);
        return customerMapper.toDTO(customer);
    }

    public CustomerResponseDTO getCustomerById(String id) {
        return customerMapper.toDTO(findCustomerOrThrow(id));
    }
    public CustomerResponseDTO updateCustomerById(String id, CustomerRequestDTO customerRequestDTO) {
        Customer customer = findCustomerOrThrow(id);
        validateEmailUnique(customerRequestDTO.getEmail(), id);
        customerMapper.updateCustomer(customerRequestDTO, customer);
        customer = customerRepository.save(customer);
        return customerMapper.toDTO(customer);
    }
    public void deleteCustomerById(String id) {
        findCustomerOrThrow(id);
        customerRepository.deleteById(id);
    }

    // helpers
    private Customer findCustomerOrThrow(String id) {
        return customerRepository.findById(id)
                .orElseThrow(() ->
                        new CustomerNotFoundException("Customer with id " + id + " not found"));
    }

    private void validateEmailUnique(String email) {
        if (customerRepository.existsByEmail(email)) {
            throw new EmailAlreadyExistsException("Customer with email " + email + " already exists");
        }
    }
    private void validateEmailUnique(String email, String id) {
        if (customerRepository.existsByEmailAndIdNot(email, id)) {
            throw new EmailAlreadyExistsException("Customer with email " + email + " already exists");
        }
    }
}
