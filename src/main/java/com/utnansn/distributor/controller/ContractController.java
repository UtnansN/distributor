package com.utnansn.distributor.controller;

import java.util.Collection;

import com.utnansn.distributor.dto.ContractSearchDTO;
import com.utnansn.distributor.dto.CreateContractDTO;
import com.utnansn.distributor.model.Contract;
import com.utnansn.distributor.service.ContractService;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.swagger.annotations.ApiOperation;

@RestController
@RequestMapping("/")
public class ContractController {

    private ContractService contractService;

    public ContractController(ContractService contractService) {
        this.contractService = contractService;
    }

    @GetMapping("/contracts")
    @ApiOperation(value = "Gets all contracts filtered by search parameters in URL",
        notes = "Gets all contracts if no restrictions are set. Ignores unknown search filters.")
    public Collection<Contract> getContracts(ContractSearchDTO searchParams) {
        return contractService.getContracts(searchParams);
    }

    @PostMapping("/contracts")
    @ApiOperation(value = "Adds a contract")
    public Contract insertContract(@RequestBody CreateContractDTO contractDTO) {
        return contractService.createContract(contractDTO);
    }

    @GetMapping("/user/{id}/contracts")
    @ApiOperation(value = "Gets all contracts based on the User ID")
    public Collection<Contract> getUserContracts(@PathVariable Long id) {
        return contractService.getContractByUserId(id);
    }
}
