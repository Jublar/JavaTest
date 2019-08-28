package com.kenect.kenectspringtest.repository;

import com.kenect.kenectspringtest.model.Address;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AddressRepository extends CrudRepository<Address, Long> {
}
