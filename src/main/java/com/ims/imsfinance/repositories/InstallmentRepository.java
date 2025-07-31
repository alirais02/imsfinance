package com.ims.imsfinance.repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import com.ims.imsfinance.entity.Installment;

public interface InstallmentRepository extends JpaRepository<Installment, UUID>, JpaSpecificationExecutor<Installment> {
    // Add custom query methods if needed
    List<Installment> findByContract_ContractId(UUID contractId);
}
