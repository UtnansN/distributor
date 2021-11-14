package com.utnansn.distributor.service;

import java.util.Collection;

import com.utnansn.distributor.dto.ContractSearchDTO;
import com.utnansn.distributor.dto.CreateContractDTO;
import com.utnansn.distributor.mapper.ContractMapper;
import com.utnansn.distributor.model.Contract;
import com.utnansn.distributor.repository.ContractRepository;
import com.utnansn.distributor.repository.UserRepository;
import com.utnansn.distributor.repository.specification.ContractSpecificationHelper;

import org.springframework.stereotype.Service;

@Service
public class ContractServiceImpl implements ContractService {
    
    private ContractRepository contractRepository;

    private UserRepository userRepository;

    private ContractMapper mapper;

    public ContractServiceImpl(ContractRepository contractRepository, UserRepository userRepository, ContractMapper mapper) {
        this.contractRepository = contractRepository;
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    public Contract createContract(CreateContractDTO dto) { 
        var contract = mapper.toEntity(dto);
        
        var user = userRepository
            .findById(dto.getUserId())
            .get();
        contract.setOwner(user);
        
        return contractRepository.save(contract);
    }

    public Collection<Contract> getContracts(ContractSearchDTO searchDTO) {
        var searchSpec = ContractSpecificationHelper.getSearchSpecification(searchDTO);

        return contractRepository.findAll(searchSpec);
    }

    public Collection<Contract> getContractByUserId(Long userId) {
        return userRepository.getById(userId).getContracts();
    }
}
