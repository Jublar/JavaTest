package com.kenect.kenectspringtest.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import javax.persistence.*;
import java.util.List;

/**
 * <p>Contact class.</p>
 *
 * @author Jublar Garcia
 * @version 1.0
 */
@Entity
public class Contact {

    private Long id;
    private String name;
    private List<EmailAddress> emails;
    private List<Phone> phones;
    private List<Address> addresses;
    private int version;

    /**
     * <p>Constructor for Contact.</p>
     */
    public Contact() {}

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
     * <p>Getter for the field <code>name</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getName() {
        return name;
    }

    /**
     * <p>Setter for the field <code>name</code>.</p>
     *
     * @param name a {@link java.lang.String} object.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * <p>Getter for the field <code>emails</code>.</p>
     *
     * @return a {@link java.util.List} object.
     */
    @OneToMany(mappedBy = "contact", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("contact")
    public List<EmailAddress> getEmails() {
        return emails;
    }

    /**
     * <p>Setter for the field <code>emails</code>.</p>
     *
     * @param emails a {@link java.util.List} object.
     */
    public void setEmails(List<EmailAddress> emails) {
        this.emails = emails;
    }

    /**
     * <p>Getter for the field <code>phones</code>.</p>
     *
     * @return a {@link java.util.List} object.
     */
    @OneToMany(mappedBy = "contact", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("contact")
    public List<Phone> getPhones() {
        return phones;
    }

    /**
     * <p>Setter for the field <code>phones</code>.</p>
     *
     * @param phones a {@link java.util.List} object.
     */
    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }

    /**
     * <p>Getter for the field <code>addresses</code>.</p>
     *
     * @return a {@link java.util.List} object.
     */
    @OneToMany(mappedBy = "contact", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("contact")
    public List<Address> getAddresses() {
        return addresses;
    }

    /**
     * <p>Setter for the field <code>addresses</code>.</p>
     *
     * @param addresses a {@link java.util.List} object.
     */
    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
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
}
