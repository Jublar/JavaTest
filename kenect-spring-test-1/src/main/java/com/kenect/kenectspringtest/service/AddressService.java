package com.kenect.kenectspringtest.service;

import com.kenect.kenectspringtest.constants.Constants;
import com.kenect.kenectspringtest.exception.InvalidInputException;
import com.kenect.kenectspringtest.model.Address;
import com.kenect.kenectspringtest.repository.AddressRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * <p>AddressService class.</p>
 *
 * @author Jublar Garcia
 * @version 1.0
 */
@Service
public class AddressService extends BaseService<Address, Long> {

    /**
     * <p>Constructor for AddressService.</p>
     *
     * @param addressRepository a {@link com.kenect.kenectspringtest.repository.AddressRepository} object.
     */
    @Autowired
    public AddressService(AddressRepository addressRepository) {
        this.repository = addressRepository;
    }

    /** {@inheritDoc} */
    @Override
    public Address save(Address address) {
        if (address == null)
            throw new InvalidInputException(Constants.MSG_ADDRESS_OBJECT_REQUIRED);
        if (address.getStreet() == null || StringUtils.isEmpty(address.getStreet()))
            throw new InvalidInputException(Constants.MSG_STREET_REQUIRED);
        return super.save(address);
    }
}
