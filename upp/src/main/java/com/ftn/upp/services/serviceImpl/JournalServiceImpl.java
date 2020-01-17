package com.ftn.upp.services.serviceImpl;

import com.ftn.upp.model.Journal;
import com.ftn.upp.repository.JournalRepository;
import com.ftn.upp.services.JournalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class JournalServiceImpl implements JournalService {

    @Autowired
    private JournalRepository journalRepository;

    @Override
    public Journal findJournalByTitle(String title) {
        Journal journal = this.journalRepository.findJournalByTitle(title);
        return journal;
    }

    @Override
    public Journal saveJournal(Journal journal) {
        return journalRepository.save(journal);
    }

    @Override
    public Journal findJournalByISSN(String ISSN) {
        return this.journalRepository.findJournalByISSN(ISSN);
    }
}
