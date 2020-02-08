package com.ftn.upp.services.serviceImpl;

import com.ftn.upp.model.ScientificField;
import com.ftn.upp.repository.ScienceFieldRepository;
import com.ftn.upp.services.ScientificFieldService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ScientificFieldServiceImpl implements ScientificFieldService {

    @Autowired
    private ScienceFieldRepository scienceFieldRepository;

    @Override
    public List<ScientificField> findAll() {
        return this.scienceFieldRepository.findAll();
    }

    @Override
    public ScientificField findByName(String name) {
        return this.scienceFieldRepository.findScientificFieldByName(name);
    }
}
