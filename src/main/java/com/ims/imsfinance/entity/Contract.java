package com.ims.imsfinance.entity;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Table(name = "contracts")
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Contract {

  @Id
  @Column(name = "contract_id", unique = true, nullable = false)
  @GeneratedValue(strategy = GenerationType.AUTO)
  private UUID contractId;

  @Column(name = "contract_number", unique = true, nullable = false)
  private String contractNumber;

  @Column(name = "contract_name", nullable = false)
  private String contractName;

  @Column(name = "contract_amount", nullable = false)
  private Double contractAmount;

  @OneToMany(mappedBy = "contract", fetch = FetchType.LAZY)
  private List<Installment> installments;
}
