package com.kenect.kenectspringtest.service;

import com.kenect.kenectspringtest.exception.ElementNotFoundException;
import com.kenect.kenectspringtest.exception.InvalidInputException;
import com.kenect.kenectspringtest.model.Address;
import com.kenect.kenectspringtest.repository.AddressRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
public class AddressServiceTest {

    @Mock
    private AddressRepository addressRepository;

    @InjectMocks
    private AddressService addressService;

    @Test
    public void save() {
        Address address = mockAddress();
        Mockito.when(addressService.save(address)).thenReturn(address);
        Address addressDB = addressService.save(address);
        assertEquals(address.getStreet(), addressDB.getStreet());
    }

    @Test(expected = InvalidInputException.class)
    public void saveInputException() {
        addressService.save(null);
    }

    @Test(expected = InvalidInputException.class)
    public void saveInputExceptionName() {
        Address address = new Address();
        addressService.save(address);
    }

    @Test
    public void getAllAddresses() {
        Mockito.when(addressRepository.findAll()).thenReturn(mockListAddress());
        List<Address> contactList = addressService.getAllElements(1L);
        assertEquals(contactList.size(), 1);
    }

    @Test
    public void getById() {
        Address mock = mockAddress();
        Optional<Address> optionalContact = Optional.of(mock);
        Mockito.when(addressRepository.findById(1L)).thenReturn(optionalContact);
        Address address = addressService.getById(1L);
        assertEquals(address.getStreet(), mock.getStreet());
    }

    @Test(expected = ElementNotFoundException.class)
    public void getByIdNotFound() {
        Address mock = mockAddress();
        Optional<Address> optionalContact = Optional.of(mock);
        Mockito.when(addressRepository.findById(1L)).thenReturn(optionalContact);
        addressService.getById(2L);
    }

    private List<Address> mockListAddress() {
        List<Address> addresses = new ArrayList<>();
        addresses.add(new Address());
        return addresses;
    }

    private Address mockAddress() {
        Address mock = new Address();
        mock.setStreet("5th ave");
        return mock;
    }
}
