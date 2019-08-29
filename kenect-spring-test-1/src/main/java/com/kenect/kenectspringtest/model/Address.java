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
     * @return a {@link java.lang.Long} object.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    /**
     * <p>Setter for the field <code>id</code>.</p>
     *
     * @param id a {@link java.lang.Long} object.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * <p>Getter for the field <code>contact</code>.</p>
     *
     * @return a {@link com.kenect.kenectspringtest.model.Contact} object.
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
     * @param contact a {@link com.kenect.kenectspringtest.model.Contact} object.
     */
    public void setContact(Contact contact) {
        this.contact = contact;
    }

    /**
     * <p>Getter for the field <code>country</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getCountry() {
        return country;
    }

    /**
     * <p>Setter for the field <code>country</code>.</p>
     *
     * @param country a {@link java.lang.String} object.
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * <p>Getter for the field <code>city</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getCity() {
        return city;
    }

    /**
     * <p>Setter for the field <code>city</code>.</p>
     *
     * @param city a {@link java.lang.String} object.
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * <p>Getter for the field <code>street</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getStreet() {
        return street;
    }

    /**
     * <p>Setter for the field <code>street</code>.</p>
     *
     * @param street a {@link java.lang.String} object.
     */
    public void setStreet(String street) {
        this.street = street;
    }

    /**
     * <p>Getter for the field <code>zipCode</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getZipCode() {
        return zipCode;
    }

    /**
     * <p>Setter for the field <code>zipCode</code>.</p>
     *
     * @param zipCode a {@link java.lang.String} object.
     */
    public void setZipCode(String zipCode) {
        this.zipCode = zipCode;
    }

    /**
     * <p>Getter for the field <code>version</code>.</p>
     *
     * @return a int.
     */
    @Version
    public int getVersion() {
        return version;
    }

    /**
     * <p>Setter for the field <code>version</code>.</p>
     *
     * @param version a int.
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
