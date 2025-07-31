package com.ims.imsfinance.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateContractDTO {
  @NotNull(message = "Contract number cannot be null")
  @Size(min = 1, max = 50, message = "Contract number must be between 1 and 50 characters")
  private String contractNumber;

  @NotNull(message = "Contract name cannot be null")
  @Size(min = 1, max = 100, message = "Contract name must be between 1 and 100 characters")
  private String contractName;

  @NotNull(message = "Contract amount cannot be null")
  @Positive(message = "Contract amount must be a positive number")
  private Double contractAmount;

  @Positive(message = "Down payment must be a positive number")
  private Integer downPayment;

  @NotNull(message = "Installment count cannot be null")
  @Positive(message = "Installment count must be a positive number")
  @Size(min = 1, message = "Installment count must be at least 1")
  private Integer installmentCount;
}
