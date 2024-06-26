package com.recouvrex.process.service;

import com.recouvrex.process.model.Case;
import com.recouvrex.process.model.enums.FollowingActionEnum;
import com.recouvrex.process.model.enums.ProcessingActionEnum;
import com.recouvrex.process.model.enums.StatusEnum;

import java.util.List;
import java.util.Optional;

public interface CaseService {

		 List<Case> findAll();
		 List<Case> findByCaseId(String caseId);
	   Optional<Case> findById(long id);
		 Case save(Case cas);
		 void deleteById(long id);
		 void deleteAll();
		 List<Case> findByStatus(StatusEnum status);

    Case createCase(Case cas);

	Case decideOnAction(String caseId, FollowingActionEnum followingAction, Long statusId);

	List<Case> filterCase(String caseId, Long statusId, Long procedureId, Long userId);

    Case processCollectAction(String caseId, ProcessingActionEnum processingAction, Long statusId);

	Case processCollectAction(String caseId, Long procedureId, Long statusId, ProcessingActionEnum processingAction);
}
