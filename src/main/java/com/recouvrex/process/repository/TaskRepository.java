package com.recouvrex.process.repository;

import com.recouvrex.process.model.Case;
import com.recouvrex.process.model.Task;
import com.recouvrex.process.model.enums.StatusEnum;
import org.apache.ibatis.annotations.Param;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TaskRepository extends JpaRepository <Task, Long> {

	@Query(value = "SELECT * FROM task t WHERE  t.case_id = :caseId", nativeQuery = true)
	List<Task> findByCaseId(Long caseId);

	@Query(value = "SELECT * FROM task t WHERE  t.case_id = :caseId AND t.user_id = :userId", nativeQuery = true)
	List<Task> findByCaseIdAndUserId(Long caseId, Long userId);
}
