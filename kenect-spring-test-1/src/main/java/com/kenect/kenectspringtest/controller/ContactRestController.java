package com.kenect.kenectspringtest.controller;

import com.kenect.kenectspringtest.dto.SearchPayLoad;
import com.kenect.kenectspringtest.model.Contact;
import com.kenect.kenectspringtest.service.IContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
public class ContactRestController {

    private IContactService contactService;

    @Autowired
    public ContactRestController(IContactService contactService) {
        this.contactService = contactService;
    }

    @GetMapping(value = "/contacts")
    public Resources<Resource<Contact>> contacts() {
        List<Resource<Contact>> contacts = contactService.getAllContacts().stream()
                .map(contact -> new Resource<>(contact,
                        linkTo(methodOn(ContactRestController.class).contactById(contact.getId())).withSelfRel(),
                        createMailsLink(contact.getId()),
                        createAddressesLink(contact.getId()),
                        createPhonesLink(contact.getId()),
                        linkTo(methodOn(ContactRestController.class).contacts()).withRel("contacts")))
                .collect(Collectors.toList());
        return new Resources<>(contacts,
                linkTo(methodOn(ContactRestController.class).contacts()).withSelfRel());
    }

    @GetMapping("/contacts/{id}")
    public Resource<Contact> contactById(@PathVariable Long id) {
        Contact contact = contactService.getById(id);
        return new Resource<>(contact,
                linkTo(methodOn(ContactRestController.class).contactById(id)).withSelfRel(),
                createMailsLink(contact.getId()),
                createAddressesLink(contact.getId()),
                createPhonesLink(contact.getId()),
                linkTo(methodOn(ContactRestController.class).contacts()).withRel("contacts"));
    }

    @PostMapping(value = "/contacts/search")
    public Resources<Resource<Contact>> contacts(@RequestBody SearchPayLoad search) {
        List<Resource<Contact>> contacts = contactService.getAllContacts(search.getQuery()).stream()
                .map(contact -> new Resource<>(contact,
                        linkTo(methodOn(ContactRestController.class).contactById(contact.getId())).withSelfRel(),
                        linkTo(methodOn(ContactRestController.class).contacts()).withRel("contacts")))
                .collect(Collectors.toList());
        return new Resources<>(contacts,
                linkTo(methodOn(ContactRestController.class).contacts()).withSelfRel());
    }

    @PostMapping(value = "/contacts")
    public Resource<Contact> saveContact(@RequestBody Contact contact) {
        Contact contactDB = contactService.save(contact);
        return new Resource<>(contactDB,
                linkTo(methodOn(ContactRestController.class).contactById(contactDB.getId())).withSelfRel(),
                linkTo(methodOn(ContactRestController.class).contacts()).withRel("contacts"));
    }

    @PutMapping(value = "/contacts")
    public Resource<Contact> updateContact(@RequestBody Contact contact) {
        Contact contactDB = contactService.save(contact);
        return new Resource<>(contactDB,
                linkTo(methodOn(ContactRestController.class).contactById(contactDB.getId())).withSelfRel(),
                linkTo(methodOn(ContactRestController.class).contacts()).withRel("contacts"));
    }

    @DeleteMapping(value = "/contacts/{id}")
    public ResponseEntity<?> delete(@PathVariable Long id) {
        contactService.delete(id);
        return ResponseEntity.noContent().build();
    }

    private static Link createMailsLink(long contactId) {
        return linkTo(methodOn(EmailRestController.class)
                .getMails(contactId)).withRel("emails");
    }

    private static Link createAddressesLink(long contactId) {
        return linkTo(methodOn(AddressRestController.class)
                .getAddresses(contactId)).withRel("addresses");
    }

    private static Link createPhonesLink(long contactId) {
        return linkTo(methodOn(PhoneRestController.class)
                .getPhones(contactId)).withRel("phones");
    }

}
