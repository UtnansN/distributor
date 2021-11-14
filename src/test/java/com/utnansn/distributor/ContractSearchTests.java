package com.utnansn.distributor;

import static org.springframework.util.Assert.isTrue;

import com.utnansn.distributor.dto.ContractSearchDTO;
import com.utnansn.distributor.model.User;
import com.utnansn.distributor.model.enums.UserType;
import com.utnansn.distributor.service.ContractService;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@ActiveProfiles("test")
public class ContractSearchTests {
    
    @Autowired
    public ContractService service;

    public User createUser(String firstName, String lastName, UserType type) {
        var user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setUserType(type);
        return user;
    }

    @BeforeAll
    public void init() {
        
    }

    @Test
    public void testGetContracts_byStartDate_found() {
        // Arrange
        var dto = ContractSearchDTO.builder()
            .startDate("1990-01-01")
            .build();

        // Act
        var contracts = service.getContracts(dto);

        // Assert
        isTrue(contracts.size() == 1, "Returned collection should return a match");
    }

    @Test
    public void testGetContracts_byStartDate_noMatch() {
        // Arrange
        var dto = ContractSearchDTO.builder()
            .startDate("9999-01-01")
            .build();

        // Act
        var contracts = service.getContracts(dto);

        // Assert
        isTrue(contracts.isEmpty(), "Returned collection should be empty!");
    }

    @Test
    public void testGetContracts_byFirstName_oneMatch() {
        // Arrange
        var name = "ONE_MATCH";

        var dto = ContractSearchDTO.builder()
            .firstName(name)
            .build();

        // Act
        var contracts = service.getContracts(dto);

        // Assert
        isTrue(contracts.size() == 1, "Returned collection should contain one entry!");
    }

    @Test
    public void testGetContracts_byFirstName_noMatch() {
        // Arrange
        var dto = ContractSearchDTO.builder()
            .firstName("NO_MATCH")
            .build();

        // Act
        var contracts = service.getContracts(dto);

        // Assert
        isTrue(contracts.isEmpty(), "Returned collection should be empty!");
    }

    @Test
    public void testGetContracts_byFirstNameAndDate_oneMatch() {
        // Arrange
        final var name = "MrStartDate";
        final var startDate = "2010-05-05";

        var dto = ContractSearchDTO.builder()
            .firstName(name)
            .startDate(startDate)
            .build();

        // Act
        var contracts = service.getContracts(dto);

        // Assert
        isTrue(contracts.size() == 1, "Returned collection should return a match!");
    }
}
