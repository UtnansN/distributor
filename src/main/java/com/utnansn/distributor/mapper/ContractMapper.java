package com.utnansn.distributor.mapper;

import com.utnansn.distributor.dto.CreateContractDTO;
import com.utnansn.distributor.model.Contract;

import org.springframework.stereotype.Component;

@Component
public class ContractMapper {

    public Contract toEntity(CreateContractDTO dto) {
        return Contract.builder()
            .startDate(dto.getStartDate())
            .contractType(dto.getContractType())
            .build();
    };

}
