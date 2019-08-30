package com.kenect.kenectspringtest.controller;

import com.kenect.kenectspringtest.dto.SearchPayLoad;
import com.kenect.kenectspringtest.model.Contact;
import com.kenect.kenectspringtest.service.IContactService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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

/**
 * <p>ContactRestController class.</p>
 *
 * @author Jublar Garcia
 * @version 1.0
 */
@RestController
@Api(description = "Operations to manage contacts in Contact Management System")
public class ContactRestController {

    private IContactService contactService;

    /**
     * <p>Constructor for ContactRestController.</p>
     *
     * @param contactService a {@link com.kenect.kenectspringtest.service.IContactService} object.
     */
    @Autowired
    public ContactRestController(IContactService contactService) {
        this.contactService = contactService;
    }

    /**
     * <p>contacts.</p>
     *
     * @return a {@link org.springframework.hateoas.Resources} object.
     */
    @GetMapping(value = "/contacts")
    @ApiOperation(value = "View a list of available contacts", response = Resources.class)
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

    /**
     * <p>contactById.</p>
     *
     * @param id a {@link java.lang.Long} object.
     * @return a {@link org.springframework.hateoas.Resource} object.
     */
    @GetMapping("/contacts/{id}")
    @ApiOperation(value = "Retrieve a contact by id", response = Resource.class)
    public Resource<Contact> contactById(@PathVariable Long id) {
        Contact contact = contactService.getById(id);
        return new Resource<>(contact,
                linkTo(methodOn(ContactRestController.class).contactById(id)).withSelfRel(),
                createMailsLink(contact.getId()),
                createAddressesLink(contact.getId()),
                createPhonesLink(contact.getId()),
                linkTo(methodOn(ContactRestController.class).contacts()).withRel("contacts"));
    }

    /**
     * <p>contacts.</p>
     *
     * @param search a {@link com.kenect.kenectspringtest.dto.SearchPayLoad} object.
     * @return a {@link org.springframework.hateoas.Resources} object.
     */
    @PostMapping(value = "/contacts/search")
    @ApiOperation(value = "Search contacts by name, email, phone, address", response = Resources.class)
    public Resources<Resource<Contact>> contacts(@RequestBody SearchPayLoad search) {
        List<Resource<Contact>> contacts = contactService.getAllContacts(search.getQuery()).stream()
                .map(contact -> new Resource<>(contact,
                        linkTo(methodOn(ContactRestController.class).contactById(contact.getId())).withSelfRel(),
                        linkTo(methodOn(ContactRestController.class).contacts()).withRel("contacts")))
                .collect(Collectors.toList());
        return new Resources<>(contacts,
                linkTo(methodOn(ContactRestController.class).contacts()).withSelfRel());
    }

    /**
     * <p>saveContact.</p>
     *
     * @param contact a {@link com.kenect.kenectspringtest.model.Contact} object.
     * @return a {@link org.springframework.hateoas.Resource} object.
     */
    @PostMapping(value = "/contacts")
    @ApiOperation(value = "Method to create a new contact", response = Resource.class)
    public Resource<Contact> saveContact(@RequestBody Contact contact) {
        Contact contactDB = contactService.save(contact);
        return new Resource<>(contactDB,
                linkTo(methodOn(ContactRestController.class).contactById(contactDB.getId())).withSelfRel(),
                linkTo(methodOn(ContactRestController.class).contacts()).withRel("contacts"));
    }

    /**
     * <p>updateContact.</p>
     *
     * @param contact a {@link com.kenect.kenectspringtest.model.Contact} object.
     * @return a {@link org.springframework.hateoas.Resource} object.
     */
    @PutMapping(value = "/contacts")
    @ApiOperation(value = "Method to update a contact", response = Resource.class)
    public Resource<Contact> updateContact(@RequestBody Contact contact) {
        Contact contactDB = contactService.save(contact);
        return new Resource<>(contactDB,
                linkTo(methodOn(ContactRestController.class).contactById(contactDB.getId())).withSelfRel(),
                linkTo(methodOn(ContactRestController.class).contacts()).withRel("contacts"));
    }

    /**
     * <p>delete.</p>
     *
     * @param id a {@link java.lang.Long} object.
     * @return a {@link org.springframework.http.ResponseEntity} object.
     */
    @DeleteMapping(value = "/contacts/{id}")
    @ApiOperation(value = "Method to delete a contact", response = ResponseEntity.class)
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
