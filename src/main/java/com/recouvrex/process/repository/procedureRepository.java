package com.recouvrex.process.repository;

import com.recouvrex.process.model.Procedure;
import org.springframework.data.jpa.repository.JpaRepository;

public interface procedureRepository extends JpaRepository <Procedure, Long> {

	
}