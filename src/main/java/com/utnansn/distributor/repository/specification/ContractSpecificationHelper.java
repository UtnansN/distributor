package com.utnansn.distributor.repository.specification;

import java.util.ArrayList;

import javax.persistence.criteria.Join;
import javax.persistence.criteria.Predicate;

import com.utnansn.distributor.dto.ContractSearchDTO;
import com.utnansn.distributor.model.Contract;
import com.utnansn.distributor.model.User;

import org.springframework.data.jpa.domain.Specification;

/**
 * Class for static functions that provide Contract specifications
 */
public class ContractSpecificationHelper {
    
    /**
     * Provides a search specification based on non-null fields in the DTO.
     * If all fields are null, returns unfiltered data.
     * @param dto - The DTO that contains all filter fields
     * @return A specification that filters by all non-null ContractSearchDTO fields
     */
    public static Specification<Contract> getSearchSpecification(ContractSearchDTO dto) {
        return (root, criteriaQuery, criteriaBuilder) -> {
            criteriaQuery.distinct(true);
            
            var predicates = new ArrayList<Predicate>();
            
            if (dto.getFirstName() != null) {
                Join<Contract, User> join = root.join("owner");
                predicates.add(criteriaBuilder.equal(join.get("firstName"), dto.getFirstName()));
            }
            if (dto.getStartDate() != null) { 
                predicates.add(criteriaBuilder.equal(root.get("startDate").as(String.class), dto.getStartDate()));
            }
            if (dto.getContractType() != null) {
                predicates.add(criteriaBuilder.equal(root.get("contractType"), dto.getContractType()));
            }
            if (dto.getUserType() != null) {
                Join<Contract, User> join = root.join("owner");
                predicates.add(criteriaBuilder.equal(join.get("userType"), dto.getUserType()));
            }

            return criteriaBuilder.and(predicates.toArray(Predicate[]::new));
        };
    }
}
