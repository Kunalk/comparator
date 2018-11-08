package com.waes.techinterview.comparator.service.storage.h2;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by Kunal on 08-11-2018.
 */
@Repository
public interface H2DatabaseDao extends JpaRepository<DocumentEntity, Long> {

    DocumentEntity findById(long id);

}
