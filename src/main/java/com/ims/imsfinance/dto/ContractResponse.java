package com.ims.imsfinance.dto;

import java.util.UUID;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ContractResponse {
  private UUID contractId;
  private String contractNumber;
  private String contractName;
  private Double contractAmount;
}
