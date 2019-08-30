package com.kenect.kenectspringtest.service;

import com.kenect.kenectspringtest.exception.ElementNotFoundException;
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
import java.util.Optional;

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

    @Test
    public void getAllContactsQuery() {
        Mockito.when(contactRepository.findByQuery(Mockito.anyString())).thenReturn(mockListContact());
        List<Contact> contactList = contactService.getAllContacts("morris");
        assertEquals(contactList.size(), 1);
    }

    @Test
    public void getById() {
        Contact mock = mockContact();
        Optional<Contact> optionalContact = Optional.of(mock);
        Mockito.when(contactRepository.findById(1L)).thenReturn(optionalContact);
        Contact contact = contactService.getById(1L);
        assertEquals(contact.getName(), mock.getName());
    }

    @Test(expected = ElementNotFoundException.class)
    public void getByIdNotFound() {
        Contact mock = mockContact();
        Optional<Contact> optionalContact = Optional.of(mock);
        Mockito.when(contactRepository.findById(1L)).thenReturn(optionalContact);
        contactService.getById(2L);
    }

    private List<Contact> mockListContact() {
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
