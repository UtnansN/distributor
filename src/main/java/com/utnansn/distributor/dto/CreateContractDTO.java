package com.utnansn.distributor.dto;

import java.time.LocalDate;

import com.utnansn.distributor.model.enums.ContractType;

import lombok.Data;

@Data
public class CreateContractDTO {
    private long userId;
    
    private LocalDate startDate;

    private ContractType contractType;
}
