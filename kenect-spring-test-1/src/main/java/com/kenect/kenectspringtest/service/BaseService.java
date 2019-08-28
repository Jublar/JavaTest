package com.kenect.kenectspringtest.service;

import com.kenect.kenectspringtest.exception.ElementNotFoundException;
import com.kenect.kenectspringtest.exception.InvalidInputException;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public class BaseService<T, ID> implements IAddressElementService<T, ID> {

    protected CrudRepository<T, ID> repository;

    @Override
    public List<T> getAllElements(ID contactId) {
        return (List<T>) repository.findAll();
    }

    @Override
    public T getById(ID id) {
        Optional<T> optional = repository.findById(id);
        return optional.orElseThrow(() -> new ElementNotFoundException("There is no element with id: " + id));
    }

    @Override
    public T save(T element) {
        return repository.save(element);
    }

    @Override
    public void delete(ID id) {
        T element = this.getById(id);
        if(element == null)
            throw new InvalidInputException(String.format("%s with id %d does not exists.", element.getClass(), id));
        repository.delete(element);
    }

}
