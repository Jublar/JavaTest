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

/**
 * <p>EmailRestController class.</p>
 *
 * @author Jublar Garcia
 * @version 1.0
 */
@RestController
public class EmailRestController {

    private IContactService contactService;
    private IAddressElementService<EmailAddress, Long> emailService;

    @Autowired
    /**
     * <p>Constructor for EmailRestController.</p>
     *
     * @param contactService a {@link com.kenect.kenectspringtest.service.IContactService} object.
     * @param emailService a {@link com.kenect.kenectspringtest.service.IAddressElementService} object.
     */
    public EmailRestController(IContactService contactService, IAddressElementService<EmailAddress, Long> emailService) {
        this.contactService = contactService;
        this.emailService = emailService;
    }
    
    /**
     * <p>getMails.</p>
     *
     * @param id a {@link java.lang.Long} object.
     * @return a {@link org.springframework.hateoas.Resources} object.
     */
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

    /**
     * <p>getMail.</p>
     *
     * @param contactId a {@link java.lang.Long} object.
     * @param id a {@link java.lang.Long} object.
     * @return a {@link org.springframework.hateoas.Resource} object.
     */
    @GetMapping(value = "/contacts/{contactId}/emails/{id}")
    public Resource<EmailAddress> getMail(@PathVariable Long contactId, @PathVariable Long id) {
        EmailAddress email = emailService.getById(id);
        return new Resource<>(email,
                linkTo(methodOn(EmailRestController.class).getMail(contactId, email.getId())).withSelfRel(),
                linkTo(methodOn(EmailRestController.class).getMails(contactId)).withRel("emails"));
    }

    /**
     * <p>addEMail.</p>
     *
     * @param id a {@link java.lang.Long} object.
     * @param email a {@link com.kenect.kenectspringtest.model.EmailAddress} object.
     * @return a {@link org.springframework.hateoas.Resource} object.
     */
    @PostMapping(value = "/contacts/{id}/emails")
    public Resource<EmailAddress> addEMail(@PathVariable Long id, @RequestBody EmailAddress email) {
        return getEmailAddressResource(id, email);
    }

    /**
     * <p>updateEMail.</p>
     *
     * @param id a {@link java.lang.Long} object.
     * @param email a {@link com.kenect.kenectspringtest.model.EmailAddress} object.
     * @return a {@link org.springframework.hateoas.Resource} object.
     */
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

    /**
     * <p>deleteEMail.</p>
     *
     * @param emailId a {@link java.lang.Long} object.
     */
    @DeleteMapping(value = "/emails/{emailId}")
    public void deleteEMail(@PathVariable Long emailId) {
        emailService.delete(emailId);
    }

}
