package com.utnansn.distributor.dto;

import java.time.LocalDate;

import com.utnansn.distributor.model.enums.ContractType;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CreateContractDTO {
    private long userId;
    
    private LocalDate startDate;

    private ContractType contractType;
}
