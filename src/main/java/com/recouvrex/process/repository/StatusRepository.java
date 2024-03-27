package com.recouvrex.process.repository;

import com.recouvrex.process.model.Profile;
import com.recouvrex.process.model.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface StatusRepository extends JpaRepository <Status, Long> {

	
}
