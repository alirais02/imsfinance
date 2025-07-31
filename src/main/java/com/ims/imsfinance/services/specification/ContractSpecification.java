package com.ims.imsfinance.services.specification;

import java.util.ArrayList;
import java.util.List;

import org.springframework.data.jpa.domain.Specification;

import com.ims.imsfinance.dto.FilterDto;
import com.ims.imsfinance.entity.Contract;

import jakarta.persistence.criteria.Predicate;

public class ContractSpecification {
  public static Specification<Contract> contractFilter(FilterDto filterDto) {
    return (root, query, criteriaBuilder) -> {

      List<Predicate> predicates = new ArrayList<>();

      if (filterDto != null && filterDto.getSearch() != null && !filterDto.getSearch().isEmpty()) {
        String searchValue = "%" + filterDto.getSearch().toLowerCase() + "%";
        Predicate contractNumberPredicate = criteriaBuilder.like(
            criteriaBuilder.lower(root.get("contract").get("contractNumber")), searchValue);
        predicates.add(contractNumberPredicate);
      }
      return predicates.isEmpty() ? null : criteriaBuilder.and(predicates.toArray(new Predicate[0]));
    };
  }
}
