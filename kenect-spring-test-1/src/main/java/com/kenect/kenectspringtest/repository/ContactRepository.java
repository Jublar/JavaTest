package com.kenect.kenectspringtest.repository;

import com.kenect.kenectspringtest.model.Contact;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>ContactRepository interface.</p>
 *
 * @author Jublar Garcia
 * @version 1.0
 */
@Repository
public interface ContactRepository extends CrudRepository<Contact, Long> {
    /**
     * <p>findByQuery.</p>
     *
     * @param query a {@link java.lang.String} object.
     * @return a {@link java.util.List} object.
     */
    @Query("SELECT c FROM Contact c " +
            "left join Address a on a.contact.id = c.id " +
            "left join EmailAddress e on e.contact.id = c.id " +
            "left join Phone p on p.contact.id = c.id " +
            "where c.name like CONCAT('%',:query,'%') " +
            "or a.street like CONCAT('%',:query,'%') " +
            "or e.email like CONCAT('%',:query,'%') " +
            "or p.number like CONCAT('%',:query,'%')")
    List<Contact> findByQuery(@Param("query") String query);
}
