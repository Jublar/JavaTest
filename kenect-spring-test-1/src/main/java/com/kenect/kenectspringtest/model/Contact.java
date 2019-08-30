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
     * @return the id of the contact.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    /**
     * <p>Setter for the field <code>id</code>.</p>
     *
     * @param id Id of the contact.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * <p>Getter for the field <code>name</code>.</p>
     *
     * @return the name of the contact.
     */
    public String getName() {
        return name;
    }

    /**
     * <p>Setter for the field <code>name</code>.</p>
     *
     * @param name of the contact.
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * <p>Getter for the field <code>emails</code>.</p>
     *
     * @return all contact emails.
     */
    @OneToMany(mappedBy = "contact", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("contact")
    public List<EmailAddress> getEmails() {
        return emails;
    }

    /**
     * <p>Setter for the field <code>emails</code>.</p>
     *
     * @param emails of the contact to set.
     */
    public void setEmails(List<EmailAddress> emails) {
        this.emails = emails;
    }

    /**
     * <p>Getter for the field <code>phones</code>.</p>
     *
     * @return all contact phones.
     */
    @OneToMany(mappedBy = "contact", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("contact")
    public List<Phone> getPhones() {
        return phones;
    }

    /**
     * <p>Setter for the field <code>phones</code>.</p>
     *
     * @param phones of the contact to set.
     */
    public void setPhones(List<Phone> phones) {
        this.phones = phones;
    }

    /**
     * <p>Getter for the field <code>addresses</code>.</p>
     *
     * @return all contact addresses.
     */
    @OneToMany(mappedBy = "contact", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnoreProperties("contact")
    public List<Address> getAddresses() {
        return addresses;
    }

    /**
     * <p>Setter for the field <code>addresses</code>.</p>
     *
     * @param addresses of the contact to set.
     */
    public void setAddresses(List<Address> addresses) {
        this.addresses = addresses;
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
}
