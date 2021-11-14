package com.utnansn.distributor.dto;

import com.utnansn.distributor.model.enums.ContractType;
import com.utnansn.distributor.model.enums.UserType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ContractSearchDTO {
    private String firstName;

    private String startDate;

    private ContractType contractType;

    private UserType userType;
}