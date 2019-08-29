package com.kenect.kenectspringtest.service;

import com.kenect.kenectspringtest.constants.Constants;
import com.kenect.kenectspringtest.exception.ElementNotFoundException;
import com.kenect.kenectspringtest.exception.InvalidInputException;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

/**
 * <p>BaseService class.</p>
 *
 * @author Jublar Garcia
 * @version 1.0
 */
public class BaseService<T, ID> implements IAddressElementService<T, ID> {

    protected CrudRepository<T, ID> repository;

    /** {@inheritDoc} */
    @Override
    public List<T> getAllElements(ID contactId) {
        return (List<T>) repository.findAll();
    }

    /** {@inheritDoc} */
    @Override
    public T getById(ID id) {
        Optional<T> optional = repository.findById(id);
        return optional.orElseThrow(() -> new ElementNotFoundException(String.format(Constants.MSG_NO_ELEMENT, id)));
    }

    /** {@inheritDoc} */
    @Override
    public T save(T element) {
        return repository.save(element);
    }

    /** {@inheritDoc} */
    @Override
    public void delete(ID id) {
        T element = this.getById(id);
        if(element == null)
            throw new InvalidInputException(String.format(Constants.MSG_ELEMENT_DOES_NOT_EXISTS, element.getClass(), id));
        repository.delete(element);
    }

}
