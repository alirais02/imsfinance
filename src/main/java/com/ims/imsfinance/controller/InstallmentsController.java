package com.ims.imsfinance.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.ims.imsfinance.dto.FilterDto;
import com.ims.imsfinance.dto.PageRequestDTO;
import com.ims.imsfinance.services.InstallmentsService;

import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/installment-management")
@Tag(name = "Installment Management", description = "Operations related to installments")
public class InstallmentsController {
  @Autowired
  private InstallmentsService installmentsService;

  @GetMapping("/installments")
  public ResponseEntity<Object> getAllInstallments(
    @ModelAttribute FilterDto filterDto, PageRequestDTO page) {
    return installmentsService.getAllInstallments(filterDto, page.getPage());
  }
}
