package com.kenect.kenectspringtest.controller;

import com.kenect.kenectspringtest.model.Contact;
import com.kenect.kenectspringtest.model.Phone;
import com.kenect.kenectspringtest.service.IAddressElementService;
import com.kenect.kenectspringtest.service.IContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.core.DummyInvocationUtils.methodOn;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;

@RestController
public class PhoneRestController {

    private IContactService contactService;
    private IAddressElementService<Phone, Long> phoneService;

    @Autowired
    public PhoneRestController(IContactService contactService, IAddressElementService<Phone, Long> phoneService) {
        this.contactService = contactService;
        this.phoneService = phoneService;
    }

    @GetMapping(value = "/contacts/{id}/phones")
    public Resources<Resource<Phone>> getPhones(@PathVariable Long id) {
        List<Resource<Phone>> phones = phoneService.getAllElements(id).stream()
                .map(phone -> new Resource<>(phone,
                        linkTo(methodOn(PhoneRestController.class).getPhone(id, phone.getId())).withSelfRel(),
                        linkTo(methodOn(PhoneRestController.class).getPhones(id)).withRel("phones")))
                .collect(Collectors.toList());
        return new Resources<>(phones,
                linkTo(methodOn(PhoneRestController.class).getPhones(id)).withSelfRel());
    }

    @GetMapping(value = "/contacts/{contactId}/phones/{id}")
    public Resource<Phone> getPhone(@PathVariable Long contactId, @PathVariable Long id) {
        Phone phone = phoneService.getById(id);
        return new Resource<>(phone,
                linkTo(methodOn(PhoneRestController.class).getPhone(contactId, phone.getId())).withSelfRel(),
                linkTo(methodOn(PhoneRestController.class).getPhones(contactId)).withRel("Phones"));
    }

    @PostMapping(value = "/contacts/{id}/phones")
    public Resource<Phone> addPhone(@PathVariable Long id, @RequestBody Phone phone) {
        return getPhoneResource(id, phone);
    }

    @PutMapping(value = "/contacts/{id}/phones")
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

    @DeleteMapping(value = "/phones/{phoneId}")
    public void deletePhone(@PathVariable Long phoneId) {
        phoneService.delete(phoneId);
    }

}
