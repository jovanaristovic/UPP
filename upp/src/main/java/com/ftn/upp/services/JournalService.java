package com.ftn.upp.services;

import com.ftn.upp.model.Journal;
import org.springframework.stereotype.Service;

@Service
public interface JournalService {

    Journal findJournalByTitle (String title);
    Journal saveJournal (Journal journal);
    Journal findJournalByISSN(String ISSN);
}
