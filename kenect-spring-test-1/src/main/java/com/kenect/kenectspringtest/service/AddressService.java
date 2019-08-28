package com.kenect.kenectspringtest.service;

import com.kenect.kenectspringtest.exception.InvalidInputException;
import com.kenect.kenectspringtest.model.Address;
import com.kenect.kenectspringtest.repository.AddressRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService extends BaseService<Address, Long> {

    private AddressRepository addressRepository;

    @Autowired
    public AddressService(AddressRepository addressRepository) {
        this.repository = addressRepository;
    }

    @Override
    public Address save(Address address) {
        if (address == null)
            throw new InvalidInputException("Address object is required");
        if (address.getStreet() == null || StringUtils.isEmpty(address.getStreet()))
            throw new InvalidInputException("Street is required");
        return super.save(address);
    }
}
