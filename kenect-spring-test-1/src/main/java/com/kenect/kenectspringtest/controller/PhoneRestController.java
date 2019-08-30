package com.kenect.kenectspringtest.controller;

import com.kenect.kenectspringtest.model.Contact;
import com.kenect.kenectspringtest.model.Phone;
import com.kenect.kenectspringtest.service.IAddressElementService;
import com.kenect.kenectspringtest.service.IContactService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

/**
 * <p>PhoneRestController class.</p>
 *
 * @author Jublar Garcia
 * @version 1.0
 */
@RestController
@Api(description = "Operations to manage phones in Contact Management System")
public class PhoneRestController {

    private IContactService contactService;
    private IAddressElementService<Phone, Long> phoneService;

    /**
     * <p>Constructor for PhoneRestController.</p>
     *
     * @param contactService a {@link com.kenect.kenectspringtest.service.IContactService} object.
     * @param phoneService a {@link com.kenect.kenectspringtest.service.IAddressElementService} object.
     */
    @Autowired
    public PhoneRestController(IContactService contactService, IAddressElementService<Phone, Long> phoneService) {
        this.contactService = contactService;
        this.phoneService = phoneService;
    }

    /**
     * <p>getPhones.</p>
     *
     * @param id a {@link java.lang.Long} object.
     * @return a {@link org.springframework.hateoas.Resources} object.
     */
    @GetMapping(value = "/contacts/{id}/phones")
    @ApiOperation(value = "View a list of all phones of a contact", response = Resources.class)
    public Resources<Resource<Phone>> getPhones(@PathVariable Long id) {
        List<Resource<Phone>> phones = phoneService.getAllElements(id).stream()
                .map(phone -> new Resource<>(phone,
                        linkTo(methodOn(PhoneRestController.class).getPhone(id, phone.getId())).withSelfRel(),
                        linkTo(methodOn(PhoneRestController.class).getPhones(id)).withRel("phones")))
                .collect(Collectors.toList());
        return new Resources<>(phones,
                linkTo(methodOn(PhoneRestController.class).getPhones(id)).withSelfRel());
    }

    /**
     * <p>getPhone.</p>
     *
     * @param contactId a {@link java.lang.Long} object.
     * @param id a {@link java.lang.Long} object.
     * @return a {@link org.springframework.hateoas.Resource} object.
     */
    @GetMapping(value = "/contacts/{contactId}/phones/{id}")
    @ApiOperation(value = "Retrieves a phone by id of a contact", response = Resource.class)
    public Resource<Phone> getPhone(@PathVariable Long contactId, @PathVariable Long id) {
        Phone phone = phoneService.getById(id);
        return new Resource<>(phone,
                linkTo(methodOn(PhoneRestController.class).getPhone(contactId, phone.getId())).withSelfRel(),
                linkTo(methodOn(PhoneRestController.class).getPhones(contactId)).withRel("Phones"));
    }

    /**
     * <p>addPhone.</p>
     *
     * @param id a {@link java.lang.Long} object.
     * @param phone a {@link com.kenect.kenectspringtest.model.Phone} object.
     * @return a {@link org.springframework.hateoas.Resource} object.
     */
    @PostMapping(value = "/contacts/{id}/phones")
    @ApiOperation(value = "Adds a phone to a contact", response = Resource.class)
    public Resource<Phone> addPhone(@PathVariable Long id, @RequestBody Phone phone) {
        return getPhoneResource(id, phone);
    }

    /**
     * <p>updatePhone.</p>
     *
     * @param id a {@link java.lang.Long} object.
     * @param phone a {@link com.kenect.kenectspringtest.model.Phone} object.
     * @return a {@link org.springframework.hateoas.Resource} object.
     */
    @PutMapping(value = "/contacts/{id}/phones")
    @ApiOperation(value = "Updates a phone of a contact", response = Resource.class)
    public Resource<Phone> updatePhone(@PathVariable Long id, @RequestBody Phone phone) {
        return getPhoneResource(id, phone);
    }

    private Resource<Phone> getPhoneResource(@PathVariable Long id, @RequestBody Phone phone) {
        Contact c = contactService.getById(id);
        phone.setContact(c);
        Phone phoneDB = phoneService.save(phone);
        return new Resource<>(phoneDB,
                linkTo(methodOn(PhoneRestController.class).getPhone(id, phoneDB.getId())).withSelfRel(),
                linkTo(methodOn(PhoneRestController.class).getPhones(id)).withRel("phones"));
    }

    /**
     * <p>deletePhone.</p>
     *
     * @param phoneId a {@link java.lang.Long} object.
     */
    @DeleteMapping(value = "/phones/{phoneId}")
    @ApiOperation(value = "Deletes a phone by id")
    public void deletePhone(@PathVariable Long phoneId) {
        phoneService.delete(phoneId);
    }

}
