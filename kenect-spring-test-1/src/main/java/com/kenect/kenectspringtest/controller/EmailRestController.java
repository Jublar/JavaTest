package com.kenect.kenectspringtest.controller;

import com.kenect.kenectspringtest.model.Contact;
import com.kenect.kenectspringtest.model.EmailAddress;
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
public class EmailRestController {

    private IContactService contactService;
    private IAddressElementService<EmailAddress, Long> emailService;

    @Autowired
    public EmailRestController(IContactService contactService, IAddressElementService<EmailAddress, Long> emailService) {
        this.contactService = contactService;
        this.emailService = emailService;
    }
    
    @GetMapping(value = "/contacts/{id}/emails")
    public Resources<Resource<EmailAddress>> getMails(@PathVariable Long id) {
        List<Resource<EmailAddress>> emails = emailService.getAllElements(id).stream()
                .map(mail -> new Resource<>(mail,
                        linkTo(methodOn(EmailRestController.class).getMail(id, mail.getId())).withSelfRel(),
                        linkTo(methodOn(EmailRestController.class).getMails(id)).withRel("emails")))
                .collect(Collectors.toList());
        return new Resources<>(emails,
                linkTo(methodOn(EmailRestController.class).getMails(id)).withSelfRel());
    }

    @GetMapping(value = "/contacts/{contactId}/emails/{id}")
    public Resource<EmailAddress> getMail(@PathVariable Long contactId, @PathVariable Long id) {
        EmailAddress email = emailService.getById(id);
        return new Resource<>(email,
                linkTo(methodOn(EmailRestController.class).getMail(contactId, email.getId())).withSelfRel(),
                linkTo(methodOn(EmailRestController.class).getMails(contactId)).withRel("emails"));
    }

    @PostMapping(value = "/contacts/{id}/emails")
    public Resource<EmailAddress> addEMail(@PathVariable Long id, @RequestBody EmailAddress email) {
        return getEmailAddressResource(id, email);
    }

    @PutMapping(value = "/contacts/{id}/emails")
    public Resource<EmailAddress> updateEMail(@PathVariable Long id, @RequestBody EmailAddress email) {
        return getEmailAddressResource(id, email);
    }

    private Resource<EmailAddress> getEmailAddressResource(@PathVariable Long id, @RequestBody EmailAddress email) {
        Contact c = contactService.getById(id);
        email.setContact(c);
        EmailAddress emailDB = emailService.save(email);
        return new Resource<>(emailDB,
                linkTo(methodOn(EmailRestController.class).getMail(id, emailDB.getId())).withSelfRel(),
                linkTo(methodOn(EmailRestController.class).getMails(id)).withRel("emails"));
    }

    @DeleteMapping(value = "/emails/{emailId}")
    public void deleteEMail(@PathVariable Long emailId) {
        emailService.delete(emailId);
    }

}
