package com.utnansn.distributor.controller;

import java.util.List;
import java.util.Set;

import com.utnansn.distributor.dto.CreateContractDTO;
import com.utnansn.distributor.model.Contract;
import com.utnansn.distributor.service.ContractService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/")
public class ContractController {

    private ContractService contractService;

    public ContractController(ContractService contractService) {
        this.contractService = contractService;
    }

    @PostMapping("/contracts")
    public Contract insertContract(@RequestBody CreateContractDTO contractDTO) {
        return contractService.createContract(contractDTO);
    }

    @GetMapping("/contracts")
    public List<Contract> getContracts() {
        return null;
    }

    @GetMapping("/user/{id}/contracts")
    public Set<Contract> getUserContracts(@PathVariable Long id) {
        return contractService.getContractByUserId(id);
    }

}
