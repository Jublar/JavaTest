package com.kenect.kenectspringtest.controller;

import com.kenect.kenectspringtest.model.Address;
import com.kenect.kenectspringtest.model.Contact;
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
public class AddressRestController {

    private IContactService contactService;
    private IAddressElementService<Address, Long> addressService;

    @Autowired
    public AddressRestController(IContactService contactService, IAddressElementService<Address, Long> addressService) {
        this.contactService = contactService;
        this.addressService = addressService;
    }

    @GetMapping(value = "/contacts/{id}/addresses")
    public Resources<Resource<Address>> getAddresses(@PathVariable Long id) {
        List<Resource<Address>> addresses = addressService.getAllElements(id).stream()
                .map(address -> new Resource<>(address,
                        linkTo(methodOn(AddressRestController.class).getAddress(id, address.getId())).withSelfRel(),
                        linkTo(methodOn(AddressRestController.class).getAddresses(id)).withRel("addresses")))
                .collect(Collectors.toList());
        return new Resources<>(addresses,
                linkTo(methodOn(AddressRestController.class).getAddresses(id)).withSelfRel());
    }

    @GetMapping(value = "/contacts/{contactId}/addresses/{id}")
    public Resource<Address> getAddress(@PathVariable Long contactId, @PathVariable Long id) {
        Address address = addressService.getById(id);
        return new Resource<>(address,
                linkTo(methodOn(AddressRestController.class).getAddress(contactId, address.getId())).withSelfRel(),
                linkTo(methodOn(AddressRestController.class).getAddresses(contactId)).withRel("addresses"));
    }

    @PostMapping(value = "/contacts/{id}/addresses")
    public Resource<Address> addAddress(@PathVariable Long id, @RequestBody Address address) {
        return getAddressResource(id, address);
    }

    @PutMapping(value = "/contacts/{id}/addresses")
    public Resource<Address> updateAddress(@PathVariable Long id, @RequestBody Address address) {
        return getAddressResource(id, address);
    }

    private Resource<Address> getAddressResource(@PathVariable Long id, @RequestBody Address address) {
        Contact c = contactService.getById(id);
        address.setContact(c);
        Address addressDB = addressService.save(address);
        return new Resource<>(addressDB,
                linkTo(methodOn(AddressRestController.class).getAddress(id, addressDB.getId())).withSelfRel(),
                linkTo(methodOn(AddressRestController.class).getAddresses(id)).withRel("addresses"));
    }

    @DeleteMapping(value = "/addresses/{addressId}")
    public void deleteAddress(@PathVariable Long addressId) {
        addressService.delete(addressId);
    }

}
