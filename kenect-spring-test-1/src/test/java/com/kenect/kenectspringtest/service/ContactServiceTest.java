package com.kenect.kenectspringtest.service;

import com.kenect.kenectspringtest.exception.InvalidInputException;
import com.kenect.kenectspringtest.model.Contact;
import com.kenect.kenectspringtest.repository.ContactRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
public class ContactServiceTest {
    @Mock
    private ContactRepository contactRepository;

    @InjectMocks
    private ContactService contactService;

    @Test
    public void save() {
        Contact c = mockContact();
        Mockito.when(contactRepository.save(c)).thenReturn(c);
        Contact contactDB = contactService.save(c);
        assertEquals(c.getName(), contactDB.getName());
    }

    @Test(expected = InvalidInputException.class)
    public void saveInputException() {
        contactService.save(null);
    }

    @Test(expected = InvalidInputException.class)
    public void saveInputExceptionName() {
        Contact c = new Contact();
        contactService.save(c);
    }


    @Test
    public void getAllContacts() {
        Mockito.when(contactRepository.findAll()).thenReturn(mockListContact());
        List<Contact> contactList = contactService.getAllContacts();
        assertEquals(contactList.size(), 1);
    }

    private Iterable<Contact> mockListContact() {
        List<Contact> contacts = new ArrayList<>();
        contacts.add(new Contact());
        return contacts;
    }

    private Contact mockContact() {
        Contact mock = new Contact();
        mock.setName("Morris");
        return mock;
    }
}
