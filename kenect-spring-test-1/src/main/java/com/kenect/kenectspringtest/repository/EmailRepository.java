package com.kenect.kenectspringtest.repository;

import com.kenect.kenectspringtest.model.EmailAddress;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * <p>EmailRepository interface.</p>
 *
 * @author Jublar Garcia
 * @version 1.0
 */
@Repository
public interface EmailRepository extends CrudRepository<EmailAddress, Long> {
}
