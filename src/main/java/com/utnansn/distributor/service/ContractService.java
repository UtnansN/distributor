package com.utnansn.distributor.service;

import java.util.Set;

import com.utnansn.distributor.dto.CreateContractDTO;
import com.utnansn.distributor.model.Contract;

public interface ContractService {
    
    public Contract createContract(CreateContractDTO dto);

    public Set<Contract> getContracts();

    public Set<Contract> getContractByUserId(Long id);
}
