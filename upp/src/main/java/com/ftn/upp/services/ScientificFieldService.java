package com.ftn.upp.services;

import com.ftn.upp.model.ScientificField;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface ScientificFieldService {

    List<ScientificField> findAll ();
    ScientificField findByName (String name);
}
