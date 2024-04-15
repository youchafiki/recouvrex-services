package com.recouvrex.process.service.impl;

import com.recouvrex.process.model.Case;
import com.recouvrex.process.model.Procedure;
import com.recouvrex.process.model.Status;
import com.recouvrex.process.model.enums.FollowingActionEnum;
import com.recouvrex.process.model.enums.ProcessingActionEnum;
import com.recouvrex.process.model.enums.StatusEnum;
import com.recouvrex.process.repository.CaseRepository;
import com.recouvrex.process.repository.ProcedureRepository;
import com.recouvrex.process.repository.StatusRepository;
import com.recouvrex.process.service.CaseService;
import com.recouvrex.process.utils.CaseSpecifications;
import com.recouvrex.process.utils.IdentificationTool;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.apache.commons.lang3.StringUtils;
import org.camunda.bpm.engine.ProcessEngine;
import org.camunda.bpm.engine.RuntimeService;
import org.camunda.bpm.engine.TaskService;
import org.camunda.bpm.engine.runtime.Execution;
import org.camunda.bpm.engine.runtime.ProcessInstanceWithVariables;
import org.camunda.bpm.engine.task.Task;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;


@Service
public class CaseServiceImpl implements CaseService {

    @Autowired
    CaseRepository caseRepository;

    @Autowired
    StatusRepository statusRepository;

    @Autowired
    ProcedureRepository procedureRepository;

    @Autowired
    RuntimeService runtimeService;

    @Autowired
    TaskService taskService;

    @Autowired
    ProcessEngine processInEngine;

    @Override
    public List<Case> findAll() {
        return caseRepository.findAll();
    }

    @Override
    public List<Case> findByCaseId(String caseId) {
        return caseRepository.findByCaseId(caseId);
    }

    @Override
    public Optional<Case> findById(long id) {
        return caseRepository.findById(id);
    }

    @Override
    public Case save(Case cas) {
        return caseRepository.save(cas);
    }

    @Override
    public void deleteById(long id) {
         caseRepository.deleteById(id);
    }

    @Override
    public void deleteAll() {
        caseRepository.deleteAll();
    }

    @Override
    public List<Case> findByStatus(StatusEnum status) {
        return caseRepository.findByStatus(status);
    }

    @Override
    public Case createCase(Case cas) {
        cas.setCaseId(IdentificationTool.generateCaseId());
        Case _case = save(cas);

        ProcessInstanceWithVariables instance = runtimeService.createProcessInstanceByKey("recouvrex-process")
                .businessKey(_case.getCaseId())
                .setVariable("case", _case)
                .executeWithVariablesInReturn();

        return _case;
    }

    @Override
    public Case decideOnAction(String caseId, FollowingActionEnum followingAction, Long statusId) {
        Execution execution = runtimeService.createExecutionQuery().processInstanceBusinessKey(caseId).singleResult();
        runtimeService.setVariable(execution.getId(), "followingAction", followingAction);
        runtimeService.setVariable(execution.getId(), "statusId", statusId);
        //Case _case =caseRepository.findByCaseId(caseId).get(0);
        //_case.setStatus();
        //caseRepository.save(_case);

        Task task = processInEngine.getTaskService().createTaskQuery()
                .processInstanceBusinessKey(caseId)
                .taskDefinitionKey("follow-collect")
                .singleResult();

        taskService.complete(task.getId());
        return (Case) runtimeService.getVariable(execution.getId(), "case");
    }
    @Override
    public List<Case> filterCase(String caseId, Long statusId, Long procedureId, Long userId){
        Specification<Case> spec = CaseSpecifications.withCriteria(caseId, statusId, procedureId, userId);
        return caseRepository.findAll(spec);
    }

    @Override
    public Case processCollectAction(String caseId, ProcessingActionEnum processingAction, Long statusId) {
        Execution execution = runtimeService.createExecutionQuery().processInstanceBusinessKey(caseId).singleResult();
        runtimeService.setVariable(execution.getId(), "processingAction", processingAction);
        runtimeService.setVariable(execution.getId(), "statusId", statusId);

        Task task = processInEngine.getTaskService().createTaskQuery()
                .processInstanceBusinessKey(caseId)
                .taskDefinitionKey("process-collect-action")
                .singleResult();

        taskService.complete(task.getId());
        return (Case) runtimeService.getVariable(execution.getId(), "case");
    }

    @Override
    public Case processCollectAction(String caseId, Long procedureId, Long statusId, ProcessingActionEnum processingAction) {
        return null;
    }
    @PersistenceContext
    private EntityManager entityManager;

}
