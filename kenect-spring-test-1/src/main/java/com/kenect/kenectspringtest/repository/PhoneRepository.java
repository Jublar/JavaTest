package com.kenect.kenectspringtest.repository;

import com.kenect.kenectspringtest.model.Phone;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PhoneRepository extends CrudRepository<Phone, Long> {
}
