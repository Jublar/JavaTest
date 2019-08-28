package com.kenect.kenectspringtest.service;

import com.kenect.kenectspringtest.model.Contact;

import java.util.List;

public interface IContactService {
    public List<Contact> getAllContacts();
    public List<Contact> getAllContacts(String query);
    public Contact getById(Long id);
    public Contact save(Contact contact);
    public void delete(Long id);
}
