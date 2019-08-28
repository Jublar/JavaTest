package com.kenect.kenectspringtest.service;

import com.kenect.kenectspringtest.model.Address;

import java.util.List;

public interface IAddressElementService<T, ID> {
    public List<T> getAllElements(ID contactId);
    public T getById(ID id);
    public T save(T child);
    public void delete(ID id);
}
