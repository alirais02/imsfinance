package com.ims.imsfinance.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ims.imsfinance.dto.CreateContractDTO;
import com.ims.imsfinance.dto.FilterDto;
import com.ims.imsfinance.dto.PageRequestDTO;
import com.ims.imsfinance.services.ContractService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/contract-management")
@Tag(name = "Contract Management", description = "Operations related to contracts")
public class ContractController {
  @Autowired
  private ContractService contractService;

  @GetMapping("/contracts")
  public ResponseEntity<Object> getAllContracts(
    @ModelAttribute FilterDto filterDto, PageRequestDTO page) {
    return contractService.getAllContracts(filterDto, page.getPage());
  }

  @PostMapping(
        path = "/create-contract",
        consumes = {MediaType.APPLICATION_JSON_VALUE},
        produces = {MediaType.APPLICATION_JSON_VALUE}
    )
  public ResponseEntity<Object> createContract(@RequestBody CreateContractDTO createContractDTO) {
    return contractService.createContract(createContractDTO);
  }
}
