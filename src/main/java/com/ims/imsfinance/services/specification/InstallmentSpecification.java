package com.ims.imsfinance.services.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.ims.imsfinance.dto.FilterDto;
import com.ims.imsfinance.entity.Installment;

import jakarta.persistence.criteria.Predicate;

public class InstallmentSpecification {
   public static Specification<Installment> installmentsFilter(FilterDto filterDto) {
    return (root, query, criteriaBuilder) -> {

      List<Predicate> predicates = new ArrayList<>();

      if (filterDto != null && filterDto.getSearch() != null && !filterDto.getSearch().isEmpty()) {
        String searchValue = "%" + filterDto.getSearch().toLowerCase() + "%";
        Predicate installmentNumberPredicate = criteriaBuilder.like(
            criteriaBuilder.lower(root.get("installments").get("installmentNumber")), searchValue);
        predicates.add(installmentNumberPredicate);
      }
      return predicates.isEmpty() ? null : criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    };
  }
}
