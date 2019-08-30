package com.kenect.kenectspringtest.service;

import com.kenect.kenectspringtest.exception.ElementNotFoundException;
import com.kenect.kenectspringtest.exception.InvalidInputException;
import com.kenect.kenectspringtest.model.EmailAddress;
import com.kenect.kenectspringtest.repository.EmailRepository;
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
public class EmailServiceTest {

    @Mock
    private EmailRepository emailRepository;

    @InjectMocks
    private EmailService emailService;

    @Test
    public void save() {
        EmailAddress emailAddress = mockEmailAddress();
        Mockito.when(emailService.save(emailAddress)).thenReturn(emailAddress);
        EmailAddress emailAddressDB = emailService.save(emailAddress);
        assertEquals(emailAddress.getEmail(), emailAddressDB.getEmail());
    }

    @Test(expected = InvalidInputException.class)
    public void saveInputException() {
        emailService.save(null);
    }

    @Test(expected = InvalidInputException.class)
    public void saveInputExceptionEmail() {
        EmailAddress emailAddress = new EmailAddress();
        emailService.save(emailAddress);
    }

    @Test(expected = InvalidInputException.class)
    public void saveInputExceptionEmailFormat() {
        EmailAddress emailAddress = new EmailAddress();
        emailAddress.setEmail("not valid mail");
        emailService.save(emailAddress);
    }

    @Test
    public void getAllMails() {
        Mockito.when(emailRepository.findAll()).thenReturn(mockListEmailAddress());
        List<EmailAddress> emailAddressList = emailService.getAllElements(1L);
        assertEquals(emailAddressList.size(), 1);
    }

    @Test
    public void getById() {
        EmailAddress mock = mockEmailAddress();
        Optional<EmailAddress> optionalEmailAddress = Optional.of(mock);
        Mockito.when(emailRepository.findById(1L)).thenReturn(optionalEmailAddress);
        EmailAddress emailAddress = emailService.getById(1L);
        assertEquals(emailAddress.getEmail(), mock.getEmail());
    }

    @Test(expected = ElementNotFoundException.class)
    public void getByIdNotFound() {
        EmailAddress mock = mockEmailAddress();
        Optional<EmailAddress> optionalEmailAddress = Optional.of(mock);
        Mockito.when(emailRepository.findById(1L)).thenReturn(optionalEmailAddress);
        emailService.getById(2L);
    }

    private List<EmailAddress> mockListEmailAddress() {
        List<EmailAddress> emailAddresses = new ArrayList<>();
        emailAddresses.add(new EmailAddress());
        return emailAddresses;
    }

    private EmailAddress mockEmailAddress() {
        EmailAddress mock = new EmailAddress();
        mock.setEmail("morris@acme.com");
        return mock;
    }
}
