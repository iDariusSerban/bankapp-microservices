package com.microservices.accounts.service;

import com.microservices.accounts.dto.CardsDto;
import com.microservices.accounts.dto.CustomerDetailsDto;
import com.microservices.accounts.entity.Customer;
import com.microservices.accounts.repository.CustomerRepository;
import com.microservices.accounts.service.client.CardsFeignClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private CardsFeignClient cardsFeignClient;


    public CustomerDetailsDto fetchCustomerDetails(String mobileNumber) throws Exception {
        Customer customer = customerRepository.findByMobileNumber(mobileNumber).orElseThrow(()->new Exception("sfgfdgfd"));
        CustomerDetailsDto customerDetailsDto = new CustomerDetailsDto();
        customerDetailsDto.setName(customer.getName());
        ResponseEntity<CardsDto> cardsDtoResponseEntity = cardsFeignClient.fetchCardDetails(mobileNumber);
        if (cardsDtoResponseEntity != null) {
            customerDetailsDto.setCardsDto(cardsDtoResponseEntity.getBody());
        }
        return customerDetailsDto;

    }
}


























