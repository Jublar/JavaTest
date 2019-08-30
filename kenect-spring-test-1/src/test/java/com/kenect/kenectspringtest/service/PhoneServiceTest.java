package com.kenect.kenectspringtest.service;

import com.kenect.kenectspringtest.exception.ElementNotFoundException;
import com.kenect.kenectspringtest.exception.InvalidInputException;
import com.kenect.kenectspringtest.model.Phone;
import com.kenect.kenectspringtest.repository.PhoneRepository;
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
public class PhoneServiceTest {

    @Mock
    private PhoneRepository phoneRepository;

    @InjectMocks
    private PhoneService phoneService;

    @Test
    public void save() {
        Phone phone = mockPhone();
        Mockito.when(phoneService.save(phone)).thenReturn(phone);
        Phone phoneDB = phoneService.save(phone);
        assertEquals(phone.getNumber(), phoneDB.getNumber());
    }

    @Test(expected = InvalidInputException.class)
    public void saveInputException() {
        phoneService.save(null);
    }

    @Test(expected = InvalidInputException.class)
    public void saveInputExceptionName() {
        Phone phone = new Phone();
        phoneService.save(phone);
    }

    @Test(expected = InvalidInputException.class)
    public void saveInputExceptionNameFormat() {
        Phone phone = new Phone();
        phone.setNumber("111");
        phoneService.save(phone);
    }

    @Test
    public void getAllPhones() {
        Mockito.when(phoneRepository.findAll()).thenReturn(mockListPhones());
        List<Phone> phoneList = phoneService.getAllElements(1L);
        assertEquals(phoneList.size(), 1);
    }

    @Test
    public void getById() {
        Phone mock = mockPhone();
        Optional<Phone> optionalPhone = Optional.of(mock);
        Mockito.when(phoneRepository.findById(1L)).thenReturn(optionalPhone);
        Phone phone = phoneService.getById(1L);
        assertEquals(phone.getNumber(), mock.getNumber());
    }

    @Test(expected = ElementNotFoundException.class)
    public void getByIdNotFound() {
        Phone mock = mockPhone();
        Optional<Phone> optionalPhone = Optional.of(mock);
        Mockito.when(phoneRepository.findById(1L)).thenReturn(optionalPhone);
        phoneService.getById(2L);
    }

    private List<Phone> mockListPhones() {
        List<Phone> phones = new ArrayList<>();
        phones.add(new Phone());
        return phones;
    }

    private Phone mockPhone() {
        Phone mock = new Phone();
        mock.setNumber("1234567890");
        return mock;
    }
}
