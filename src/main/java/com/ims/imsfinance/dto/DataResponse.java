package com.ims.imsfinance.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DataResponse {
  private long total;
  private Object data;
  private Integer statusCode;
  private String message;
  private String status;
}
