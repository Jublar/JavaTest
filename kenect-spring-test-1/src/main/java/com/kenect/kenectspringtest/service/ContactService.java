package com.kenect.kenectspringtest.service;

import com.kenect.kenectspringtest.exception.ElementNotFoundException;
import com.kenect.kenectspringtest.model.Contact;
import com.kenect.kenectspringtest.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class ContactService implements IContactService {

    private ContactRepository contactRepository;

    @Autowired
    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    @Override
    public List<Contact> getAllContacts() {
        return (List<Contact>) contactRepository.findAll();
    }

    @Override
    public List<Contact> getAllContacts(String query) {
        return (List<Contact>) contactRepository.findByQuery(query);
    }

    @Override
    public Contact getById(Long id) {
        Optional<Contact> optionalContact = contactRepository.findById(id);
        return optionalContact.orElseThrow(() -> new ElementNotFoundException("There is no element with id: " + id));
    }

    @Override
    @Transactional
    public Contact save(Contact contact) {
        return contactRepository.save(contact);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Contact contact = this.getById(id);
        contactRepository.delete(contact);
    }
}
