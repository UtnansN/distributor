package com.utnansn.distributor.service;

import java.util.Set;

import com.utnansn.distributor.dto.CreateContractDTO;
import com.utnansn.distributor.mapper.ContractMapper;
import com.utnansn.distributor.model.Contract;
import com.utnansn.distributor.model.User;
import com.utnansn.distributor.repository.ContractRepository;
import com.utnansn.distributor.repository.UserRepository;

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

    public Set<Contract> getContracts() {
        return null;
    }

    public Set<Contract> getContractByUserId(Long userId) {
        User user = userRepository.getById(userId);
        
        return user.getContracts();
    }
}
