package com.acme.ordermanagement.customer.service;

import com.acme.ordermanagement.common.exception.ResourceAlreadyExistsException;
import com.acme.ordermanagement.customer.entity.Customer;
import com.acme.ordermanagement.customer.repository.CustomerRepository;
import com.acme.ordermanagement.customer.dto.CreateCustomerRequest;
import com.acme.ordermanagement.customer.dto.CustomerResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class CustomerService {

    private final CustomerRepository customerRepository;

    @Transactional
    public CustomerResponse createCustomer(CreateCustomerRequest request) {
        customerRepository.findByEmail(request.email())
                .ifPresent(existing -> {
                    throw new ResourceAlreadyExistsException(
                            "Customer already exists with email: " + request.email()
                    );
                });

        Customer customer = new Customer(request.fullName(), request.email());
        Customer saved = customerRepository.save(customer);

        return new CustomerResponse(
                saved.getId(),
                saved.getFullName(),
                saved.getEmail()
        );
    }
}