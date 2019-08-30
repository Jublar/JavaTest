package com.kenect.kenectspringtest.controller;

import com.kenect.kenectspringtest.model.Address;
import com.kenect.kenectspringtest.model.Contact;
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
 * <p>AddressRestController class.</p>
 *
 * @author Jublar Garcia
 * @version 1.0
 */
@RestController
@Api(description = "Operations to manage adresses in Contact Management System")
public class AddressRestController {

    private IContactService contactService;
    private IAddressElementService<Address, Long> addressService;

    /**
     * <p>Constructor for AddressRestController.</p>
     *
     * @param contactService a {@link com.kenect.kenectspringtest.service.IContactService} object.
     * @param addressService a {@link com.kenect.kenectspringtest.service.IAddressElementService} object.
     */
    @Autowired
    public AddressRestController(IContactService contactService, IAddressElementService<Address, Long> addressService) {
        this.contactService = contactService;
        this.addressService = addressService;
    }

    /**
     * <p>getAddresses.</p>
     *
     * @param id a {@link java.lang.Long} object.
     * @return a {@link org.springframework.hateoas.Resources} object.
     */
    @GetMapping(value = "/contacts/{id}/addresses")
    @ApiOperation(value = "View a list of all addresses of a contact", response = Resources.class)
    public Resources<Resource<Address>> getAddresses(@PathVariable Long id) {
        List<Resource<Address>> addresses = addressService.getAllElements(id).stream()
                .map(address -> new Resource<>(address,
                        linkTo(methodOn(AddressRestController.class).getAddress(id, address.getId())).withSelfRel(),
                        linkTo(methodOn(AddressRestController.class).getAddresses(id)).withRel("addresses")))
                .collect(Collectors.toList());
        return new Resources<>(addresses,
                linkTo(methodOn(AddressRestController.class).getAddresses(id)).withSelfRel());
    }

    /**
     * <p>getAddress.</p>
     *
     * @param contactId a {@link java.lang.Long} object.
     * @param id a {@link java.lang.Long} object.
     * @return a {@link org.springframework.hateoas.Resource} object.
     */
    @GetMapping(value = "/contacts/{contactId}/addresses/{id}")
    @ApiOperation(value = "Retrieves an address by id of a contact", response = Resource.class)
    public Resource<Address> getAddress(@PathVariable Long contactId, @PathVariable Long id) {
        Address address = addressService.getById(id);
        return new Resource<>(address,
                linkTo(methodOn(AddressRestController.class).getAddress(contactId, address.getId())).withSelfRel(),
                linkTo(methodOn(AddressRestController.class).getAddresses(contactId)).withRel("addresses"));
    }

    /**
     * <p>addAddress.</p>
     *
     * @param id a {@link java.lang.Long} object.
     * @param address a {@link com.kenect.kenectspringtest.model.Address} object.
     * @return a {@link org.springframework.hateoas.Resource} object.
     */
    @PostMapping(value = "/contacts/{id}/addresses")
    @ApiOperation(value = "Adds an address to a contact", response = Resource.class)
    public Resource<Address> addAddress(@PathVariable Long id, @RequestBody Address address) {
        return getAddressResource(id, address);
    }

    /**
     * <p>updateAddress.</p>
     *
     * @param id a {@link java.lang.Long} object.
     * @param address a {@link com.kenect.kenectspringtest.model.Address} object.
     * @return a {@link org.springframework.hateoas.Resource} object.
     */
    @PutMapping(value = "/contacts/{id}/addresses")
    @ApiOperation(value = "Updates an address of a contact", response = Resource.class)
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

    /**
     * <p>deleteAddress.</p>
     *
     * @param addressId a {@link java.lang.Long} object.
     */
    @DeleteMapping(value = "/addresses/{addressId}")
    @ApiOperation(value = "Deletes an address by id")
    public void deleteAddress(@PathVariable Long addressId) {
        addressService.delete(addressId);
    }

}
