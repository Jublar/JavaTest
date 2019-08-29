package com.kenect.kenectspringtest.repository;

import com.kenect.kenectspringtest.model.Phone;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * <p>PhoneRepository interface.</p>
 *
 * @author Jublar Garcia
 * @version 1.0
 */
@Repository
public interface PhoneRepository extends CrudRepository<Phone, Long> {
}
