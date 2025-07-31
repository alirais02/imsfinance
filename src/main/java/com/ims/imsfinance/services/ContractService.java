package com.ims.imsfinance.services;

import java.sql.Date;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ims.imsfinance.dto.ContractResponse;
import com.ims.imsfinance.dto.CreateContractDTO;
import com.ims.imsfinance.dto.DataResponse;
import com.ims.imsfinance.dto.FilterDto;
import com.ims.imsfinance.entity.Contract;
import com.ims.imsfinance.entity.Installment;
import com.ims.imsfinance.repositories.ContractRepository;
import com.ims.imsfinance.repositories.InstallmentRepository;
import com.ims.imsfinance.services.specification.ContractSpecification;

import jakarta.transaction.Transactional;
import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class ContractService {
  @Autowired
  private ContractRepository contractRepository;

  @Autowired
  private InstallmentRepository installmentRepository;

  public ResponseEntity<Object> getAllContracts(FilterDto filterDto, Pageable page) {
    DataResponse response = new DataResponse();

    try {
      Specification<Contract> contractSpecification = ContractSpecification.contractFilter(filterDto);
      Page<Contract> contracts = contractRepository.findAll(contractSpecification, page);

      long totalData = contractRepository.count(contractSpecification);

      List<ContractResponse> data = contracts.stream().map(contract -> new ContractResponse(
        contract.getContractId(),
        contract.getContractNumber(),
        contract.getContractName(),
        contract.getContractAmount()
      )).toList();

      response.setData(data);
      response.setTotal(totalData);
      response.setMessage("Get all contracts successfully");
      response.setStatus(HttpStatus.OK.getReasonPhrase());
      response.setStatusCode(HttpStatus.OK.value());

      return ResponseEntity.ok().body(response);

    } catch (Exception e) {
      response.setTotal(0);
      response.setData(null);
      response.setMessage("Internal Server Error : " + e.getMessage());
      response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
      response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());

      return ResponseEntity.internalServerError().body(response);
    }
  }

  @Transactional
  public ResponseEntity<Object> createContract(CreateContractDTO createContractDTO) {
    DataResponse response = new DataResponse();

    try {
      Contract contract = new Contract();
      contract.setContractNumber(createContractDTO.getContractNumber());
      contract.setContractName(createContractDTO.getContractName());
      contract.setContractAmount(createContractDTO.getContractAmount());

      contractRepository.save(contract);

      Double principalDebt = createContractDTO.getContractAmount() - (createContractDTO.getContractAmount() * (createContractDTO.getDownPayment() / 100.0));
      Double installmentData;
      if (createContractDTO.getInstallmentCount() <= 12) {
        installmentData = (principalDebt + (principalDebt * 0.12)) / createContractDTO.getInstallmentCount();
      } else if (createContractDTO.getInstallmentCount() > 12 && createContractDTO.getInstallmentCount() <= 24) {
        installmentData = (principalDebt + (principalDebt * 0.14)) / createContractDTO.getInstallmentCount();
      } else {
        installmentData = (principalDebt + (principalDebt * 0.165)) / createContractDTO.getInstallmentCount();
      }

      LocalDate localDate = LocalDate.now();

      for (int i = 1; i <= createContractDTO.getInstallmentCount(); i++) {
        Installment installment = new Installment();
        installment.setInstallmentNumber(i);
        installment.setInstallmentAmount(installmentData);
        installment.setDueDate(localDate.plusMonths(i));
        installment.setContract(contract);
        installment.setPaid(false);
        installmentRepository.save(installment);
      }

      response.setData(null);
      response.setTotal(1);
      response.setMessage("Contract created successfully");
      response.setStatus(HttpStatus.CREATED.getReasonPhrase());
      response.setStatusCode(HttpStatus.CREATED.value());

      return ResponseEntity.status(HttpStatus.CREATED.value()).body(response);

    } catch (Exception e) {
      response.setTotal(0);
      response.setData(null);
      response.setMessage("Internal Server Error : " + e.getMessage());
      response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.value());
      response.setStatus(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase());

      return ResponseEntity.internalServerError().body(response);
    }
  }
}
