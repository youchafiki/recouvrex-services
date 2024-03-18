package com.recouvrex.process.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.cglib.core.Local;

import java.math.BigDecimal;
import java.time.LocalDate;

//Classe echeance
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name="due_date")
public class DueDate  extends BaseEntity{

    private LocalDate paymentDueDate; // ?????

    private String dueDateStatus; //Statut échéance

    private BigDecimal principalAmount; //montant capital, partie de l'échéance utilisé pour payer le prêt initial

    private BigDecimal interestAmount; // intérêts

    private BigDecimal insuranceAmount; // Montant assurance

    private BigDecimal ancillaryCharge; //Montant accessoires

    private BigDecimal remainingPrincipalBalance; //Capital restant dû

    private LocalDate startDate; //date ouverture de l'échéance

    private LocalDate modificationDate; //Date de modification

    private BigDecimal totalInstallmentAmount; //montant total échéance

    private BigDecimal latePaymentCharge; //Pénalité de retard

    private BigDecimal unpaidPrincipalAmount; // montant capital impayé

    private BigDecimal accruedInterest; // montant intérêt impayé

    private BigDecimal unpaidInsurancePrenium; //Montant assurance impayé

    private BigDecimal unpaidAncillaryCharges; //Montant accessoires impayés
}
