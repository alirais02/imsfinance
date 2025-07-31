package com.ims.imsfinance.entity;

import java.time.LocalDate;
import java.util.UUID;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Table(name = "installments")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Installment {
  @Id
  @Column(name = "installment_id", unique = true, nullable = false, updatable = false)
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID installmentId;

  @Column(name = "installment_number", nullable = false)
  private Integer installmentNumber;

  @Column(name = "installment_amount", nullable = false)
  private Double installmentAmount;

  @Column(name = "due_date", nullable = false)
  private LocalDate dueDate;

  @Column(name = "paid", nullable = false)
  private Boolean paid;

  @ManyToOne(fetch = FetchType.LAZY, optional = false)
  @JoinColumn(name = "contract_id", referencedColumnName = "contract_id", nullable = false)
  private Contract contract;
}
