package com.kenect.kenectspringtest.service;

import com.kenect.kenectspringtest.constants.Constants;
import com.kenect.kenectspringtest.exception.ElementNotFoundException;
import com.kenect.kenectspringtest.model.Contact;
import com.kenect.kenectspringtest.repository.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

/**
 * <p>ContactService class.</p>
 *
 * @author Jublar Garcia
 * @version 1.0
 */
@Service
public class ContactService implements IContactService {

    private ContactRepository contactRepository;

    /**
     * <p>Constructor for ContactService.</p>
     *
     * @param contactRepository a {@link com.kenect.kenectspringtest.repository.ContactRepository} object.
     */
    @Autowired
    public ContactService(ContactRepository contactRepository) {
        this.contactRepository = contactRepository;
    }

    /** {@inheritDoc} */
    @Override
    public List<Contact> getAllContacts() {
        return (List<Contact>) contactRepository.findAll();
    }

    /** {@inheritDoc} */
    @Override
    public List<Contact> getAllContacts(String query) {
        return (List<Contact>) contactRepository.findByQuery(query);
    }

    /** {@inheritDoc} */
    @Override
    public Contact getById(Long id) {
        Optional<Contact> optionalContact = contactRepository.findById(id);
        return optionalContact.orElseThrow(() -> new ElementNotFoundException(String.format(Constants.MSG_NO_ELEMENT, id)));
    }

    /** {@inheritDoc} */
    @Override
    @Transactional
    public Contact save(Contact contact) {
        return contactRepository.save(contact);
    }

    /** {@inheritDoc} */
    @Override
    @Transactional
    public void delete(Long id) {
        Contact contact = this.getById(id);
        contactRepository.delete(contact);
    }
}
