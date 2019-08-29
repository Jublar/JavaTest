package com.kenect.kenectspringtest.service;

import com.kenect.kenectspringtest.model.Contact;

import java.util.List;

/**
 * <p>IContactService interface.</p>
 *
 * @author Jublar Garcia
 * @version 1.0
 */
public interface IContactService {
    /**
     * <p>getAllContacts.</p>
     *
     * @return a {@link java.util.List} object.
     */
    public List<Contact> getAllContacts();
    /**
     * <p>getAllContacts.</p>
     *
     * @param query a {@link java.lang.String} object.
     * @return a {@link java.util.List} object.
     */
    public List<Contact> getAllContacts(String query);
    /**
     * <p>getById.</p>
     *
     * @param id a {@link java.lang.Long} object.
     * @return a {@link com.kenect.kenectspringtest.model.Contact} object.
     */
    public Contact getById(Long id);
    /**
     * <p>save.</p>
     *
     * @param contact a {@link com.kenect.kenectspringtest.model.Contact} object.
     * @return a {@link com.kenect.kenectspringtest.model.Contact} object.
     */
    public Contact save(Contact contact);
    /**
     * <p>delete.</p>
     *
     * @param id a {@link java.lang.Long} object.
     */
    public void delete(Long id);
}
