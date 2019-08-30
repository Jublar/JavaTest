package com.kenect.kenectspringtest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * <p>EmailAddress class.</p>
 *
 * @author Jublar Garcia
 * @version 1.0
 */
@Entity
public class EmailAddress {

    private Long id;
    private String email;
    private Contact contact;
    private int version;

    /**
     * <p>Getter for the field <code>id</code>.</p>
     *
     * @return the email id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    /**
     * <p>Setter for the field <code>id</code>.</p>
     *
     * @param id of the email to be set.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * <p>Getter for the field <code>email</code>.</p>
     *
     * @return the email address.
     */
    public String getEmail() {
        return email;
    }

    /**
     * <p>Setter for the field <code>email</code>.</p>
     *
     * @param email address to be set.
     */
    public void setEmail(String email) {
        this.email = email;
    }

    /**
     * <p>Getter for the field <code>contact</code>.</p>
     *
     * @return the email contact object.
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name="contact_id")
    @JsonIgnore
    public Contact getContact() {
        return contact;
    }

    /**
     * <p>Setter for the field <code>contact</code>.</p>
     *
     * @param contact of the email to be set.
     */
    public void setContact(Contact contact) {
        this.contact = contact;
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
        return email;
    }
}
