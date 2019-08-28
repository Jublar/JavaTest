package com.kenect.kenectspringtest.repository;

import com.kenect.kenectspringtest.model.EmailAddress;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmailRepository extends CrudRepository<EmailAddress, Long> {
}
