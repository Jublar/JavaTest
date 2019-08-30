package com.kenect.kenectspringtest.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;

/**
 * <p>Phone class.</p>
 *
 * @author Jublar Garcia
 * @version 1.0
 */
@Entity
public class Phone {

    private Long id;
    private String number;
    private String type;
    private Contact contact;
    private int version;

    /**
     * <p>Getter for the field <code>id</code>.</p>
     *
     * @return the phone id.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long getId() {
        return id;
    }

    /**
     * <p>Setter for the field <code>id</code>.</p>
     *
     * @param id of the phone to be set.
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * <p>Setter for the field <code>number</code>.</p>
     *
     * @param number of the phone to be set.
     */
    public void setNumber(String number) {
        this.number = number;
    }

    /**
     * <p>Getter for the field <code>number</code>.</p>
     *
     * @return the phone number.
     */
    public String getNumber() {
        return number;
    }

    /**
     * <p>Getter for the field <code>type</code>.</p>
     *
     * @return the phone type.
     */
    public String getType() {
        return type;
    }

    /**
     * <p>Setter for the field <code>type</code>.</p>
     *
     * @param type of the phone to be set.
     */
    public void setType(String type) {
        this.type = type;
    }

    /**
     * <p>Getter for the field <code>contact</code>.</p>
     *
     * @return the phone contact object.
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
     * @param contact of the phone to be set.
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
        return number;
    }
}
