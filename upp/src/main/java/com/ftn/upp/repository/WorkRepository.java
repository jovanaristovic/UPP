package com.ftn.upp.repository;

import com.ftn.upp.model.Authority;
import com.ftn.upp.model.Work;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface WorkRepository extends JpaRepository<Work, Long> {

    Work findWorkByTitle(String title);

}
