package com.kenect.kenectspringtest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * <p>Address class.</p>
 *
 * @author Jublar Garcia
 * @version 1.0
 */
@Entity
public class Address {

    private Long id;
    private String country;
    private String city;
    private String street;
    private String zipCode;
    private Contact contact;
    private int version;

    /**
     * <p>Getter for the field <code>id</code>.</p>
     *
     * @return the address id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    /**
     * <p>Setter for the field <code>id</code>.</p>
     *
     * @param id of the address to be set.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * <p>Getter for the field <code>contact</code>.</p>
     *
     * @return the address contact.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "contact_id")
    @JsonIgnore
    public Contact getContact() {
        return contact;
    }

    /**
     * <p>Setter for the field <code>contact</code>.</p>
     *
     * @param contact of the address to be set.
     */
    public void setContact(Contact contact) {
        this.contact = contact;
    }

    /**
     * <p>Getter for the field <code>country</code>.</p>
     *
     * @return the address country.
     */
    public String getCountry() {
        return country;
    }

    /**
     * <p>Setter for the field <code>country</code>.</p>
     *
     * @param country of the address to be set.
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * <p>Getter for the field <code>city</code>.</p>
     *
     * @return the address city.
     */
    public String getCity() {
        return city;
    }

    /**
     * <p>Setter for the field <code>city</code>.</p>
     *
     * @param city of the address to be set.
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * <p>Getter for the field <code>street</code>.</p>
     *
     * @return the address street.
     */
    public String getStreet() {
        return street;
    }

    /**
     * <p>Setter for the field <code>street</code>.</p>
     *
     * @param street of the address to be set.
     */
    public void setStreet(String street) {
        this.street = street;
    }

    /**
     * <p>Getter for the field <code>zipCode</code>.</p>
     *
     * @return the address zip code.
     */
    public String getZipCode() {
        return zipCode;
    }

    /**
     * <p>Setter for the field <code>zipCode</code>.</p>
     *
     * @param zipCode of the address to be set.
     */
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    /**
     * <p>Getter for the field <code>version</code>.</p>
     *
     * @return version number.
     */
    @Version
    public int getVersion() {
        return version;
    }

    /**
     * <p>Setter for the field <code>version</code>.</p>
     *
     * @param version sets the version number.
     */
    public void setVersion(int version) {
        this.version = version;
    }

    /** {@inheritDoc} */
    @Override
    public String toString() {
        return String.format("%s %s %s %s",country, city, street, zipCode);
    }
}
