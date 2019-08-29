package com.kenect.kenectspringtest.service;

import com.kenect.kenectspringtest.model.Address;

import java.util.List;

/**
 * <p>IAddressElementService interface.</p>
 *
 * @author Jublar Garcia
 * @version 1.0
 */
public interface IAddressElementService<T, ID> {
    /**
     * <p>getAllElements.</p>
     *
     * @param contactId a ID object.
     * @return a {@link java.util.List} object.
     */
    public List<T> getAllElements(ID contactId);
    /**
     * <p>getById.</p>
     *
     * @param id a ID object.
     * @return a T object.
     */
    public T getById(ID id);
    /**
     * <p>save.</p>
     *
     * @param child a T object.
     * @return a T object.
     */
    public T save(T child);
    /**
     * <p>delete.</p>
     *
     * @param id a ID object.
     */
    public void delete(ID id);
}
