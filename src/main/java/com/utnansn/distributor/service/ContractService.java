package com.utnansn.distributor.service;

import java.util.Collection;
import com.utnansn.distributor.dto.ContractSearchDTO;
import com.utnansn.distributor.dto.CreateContractDTO;
import com.utnansn.distributor.model.Contract;

public interface ContractService {
    
    public Contract createContract(CreateContractDTO dto);

    public Collection<Contract> getContracts(ContractSearchDTO searchDTO);

    public Collection<Contract> getContractByUserId(Long id);

    public void deleteContract(Long id);
}
