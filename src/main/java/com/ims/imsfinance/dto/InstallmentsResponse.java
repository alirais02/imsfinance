package com.ims.imsfinance.dto;

import java.time.LocalDate;
import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class InstallmentsResponse {
  private UUID installmentId;
  private String contractNumber;
  private Integer installmentNumber;
  private Double installmentAmount;
  private String dueDate;
}
