package com.ims.imsfinance.services;

import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.ims.imsfinance.dto.DataResponse;
import com.ims.imsfinance.dto.FilterDto;
import com.ims.imsfinance.dto.InstallmentsResponse;
import com.ims.imsfinance.entity.Installment;
import com.ims.imsfinance.repositories.InstallmentRepository;
import com.ims.imsfinance.services.specification.InstallmentSpecification;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class InstallmentsService {
  @Autowired
  private InstallmentRepository installmentsRepository;

  DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

  public ResponseEntity<Object> getAllInstallments(FilterDto filterDto, Pageable page) {
    DataResponse response = new DataResponse();

    try {
      Specification<Installment> installmentsSpecification = InstallmentSpecification.installmentsFilter(filterDto);
      Page<Installment> installments = installmentsRepository.findAll(installmentsSpecification, page);

      long totalData = installmentsRepository.count(installmentsSpecification);

      List<InstallmentsResponse> data = installments.stream().map(installment -> new InstallmentsResponse(
        installment.getInstallmentId(),
        installment.getContract().getContractNumber(),
        installment.getInstallmentNumber(),
        installment.getInstallmentAmount(),
        installment.getDueDate().format(formatter)
      )).toList();

      response.setData(data);
      response.setTotal(totalData);
      response.setMessage("Get all installments successfully");
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

  public ResponseEntity<Object> getInstallmentsByContractId(UUID contractId) {
    DataResponse response = new DataResponse();

    try {
      List<Installment> installments = installmentsRepository.findByContract_ContractId(contractId);
      long totalData = installments.size();

      List<InstallmentsResponse> data = installments.stream().map(installment -> new InstallmentsResponse(
        installment.getInstallmentId(),
        installment.getContract().getContractNumber(),
        installment.getInstallmentNumber(),
        installment.getInstallmentAmount(),
        installment.getDueDate().format(formatter)
      )).toList();

      response.setData(data);
      response.setTotal(totalData);
      response.setMessage("Get installments by contract ID successfully");
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
}
