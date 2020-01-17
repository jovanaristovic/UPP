package com.ftn.upp.repository;

import com.ftn.upp.model.Journal;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface JournalRepository extends JpaRepository<Journal, Long> {

    Journal findJournalByTitle(String title);
    Journal findJournalByISSN(String ISSN);
}
