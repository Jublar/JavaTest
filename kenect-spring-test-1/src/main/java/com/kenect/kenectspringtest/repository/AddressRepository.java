package com.kenect.kenectspringtest.repository;

import com.kenect.kenectspringtest.model.Address;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * <p>AddressRepository interface.</p>
 *
 * @author Jublar Garcia
 * @version 1.0
 */
@Repository
public interface AddressRepository extends CrudRepository<Address, Long> {
}
