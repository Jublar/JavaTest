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
     * <p>Setter for the field <code>number</code>.</p>
     *
     * @param number a {@link java.lang.String} object.
     */
    public void setNumber(String number) {
        this.number = number;
    }

    /**
     * <p>Getter for the field <code>number</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getNumber() {
        return number;
    }

    /**
     * <p>Getter for the field <code>type</code>.</p>
     *
     * @return a {@link java.lang.String} object.
     */
    public String getType() {
        return type;
    }

    /**
     * <p>Setter for the field <code>type</code>.</p>
     *
     * @param type a {@link java.lang.String} object.
     */
    public void setType(String type) {
        this.type = type;
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
        return number;
    }
}
