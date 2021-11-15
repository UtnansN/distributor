package com.utnansn.distributor;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;
import static org.springframework.util.Assert.isTrue;

import java.util.Arrays;

import javax.transaction.Transactional;

import com.utnansn.distributor.dto.ContractSearchDTO;
import com.utnansn.distributor.model.enums.ContractType;
import com.utnansn.distributor.model.enums.UserType;
import com.utnansn.distributor.repository.ContractRepository;
import com.utnansn.distributor.service.ContractService;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.TestInstance.Lifecycle;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;

/**
 * Functional tests to check fetching contracts by search query from an embedded database.
 * A SQL file might be difficult to maintain if writing these kinds of tests like this,
 * although did learn something new I suppose. Works for testing the search functionality
 * of the API in a state like this.
 */
@SpringBootTest
@ActiveProfiles("test")
@TestInstance(Lifecycle.PER_CLASS)
@Transactional
@Sql("/import-search-data.sql")
public class ContractSearchTests {
    
    @Autowired
    public ContractService service;

    @Autowired
    public ContractRepository repository;

    @Test
    public void testGetContracts_noFilter_returnsAllContracts() {
        // Arrange
        var dto = new ContractSearchDTO();

        // Act
        var actual = service.getContracts(dto);

        // Assert
        var all = repository.findAll();
        assertFalse(all.isEmpty(), "Database should not be empty after initialization");
        assertEquals(all.size(), actual.size(), "No filter should return all contracts");
    }

    @Test
    public void testGetContracts_byStartDate_found() {
        // Arrange
        final var startDate = "1990-01-01";

        var dto = ContractSearchDTO.builder()
            .startDate(startDate)
            .build();

        // Act
        var actual = service.getContracts(dto);

        // Assert
        assertEquals(1, actual.size(), "Returned collection should contain exactly one match");
        
        var match = actual.iterator().next();
        assertEquals(startDate, match.getStartDate().toString(), "Start date of found contract should match the search date");
    }

    @Test
    public void testGetContracts_byStartDate_noMatch() {
        // Arrange
        var dto = ContractSearchDTO.builder()
            .startDate("9999-01-01")
            .build();

        // Act
        var actual = service.getContracts(dto);

        // Assert
        assertTrue(actual.isEmpty(), "Returned collection should contain no matches!");
    }

    @Test
    public void testGetContracts_byFirstName_oneMatch() {
        // Arrange
        final var name = "ONE_MATCH";

        var dto = ContractSearchDTO.builder()
            .firstName(name)
            .build();

        // Act
        var actual = service.getContracts(dto);

        // Assert
        assertEquals(1, actual.size(), "Returned collection should contain exactly one match!");
        
        var match = actual.iterator().next();
        assertEquals(name, match.getOwner().getFirstName(), "Contract owner's name should match the searched name");
    }

    @Test
    public void testGetContracts_byFirstName_noMatch() {
        // Arrange
        var dto = ContractSearchDTO.builder()
            .firstName("NO_MATCH")
            .build();

        // Act
        var actual = service.getContracts(dto);

        // Assert
        isTrue(actual.isEmpty(), "Returned collection should be empty!");
        assertTrue(actual.isEmpty(), "Returned collection should contain no matches!");
    }

    @Test
    public void testGetContracts_byFirstNameAndDate_oneMatch() {
        // Arrange
        final var name = "MrStartDate";
        final var startDate = "2015-01-01";

        var dto = ContractSearchDTO.builder()
            .firstName(name)
            .startDate(startDate)
            .build();

        // Act
        var actual = service.getContracts(dto);

        // Assert
        isTrue(actual.size() == 1, "Returned collection should return a match!");
    }

    @Test
    public void testGetContracts_byUserType_correctType() {
        // Arrange
        final var userType = UserType.BUSINESS;

        var dto = ContractSearchDTO.builder()
            .userType(userType)
            .build();

        // Act
        var actual = service.getContracts(dto);

        // Assert
        assertTrue(actual.stream().allMatch(c -> c.getOwner().getUserType() == userType), "All user types should match the one provided in the DTO");
    }

    @Test
    public void testGetContracts_byContractType_correctType() {
        // Arrange
        final var contractType = ContractType.GAS;

        var dto = ContractSearchDTO.builder()
            .contractType(contractType)
            .build();

        // Act
        var actual = service.getContracts(dto);

        // Assert
        assertTrue(actual.stream().allMatch(c -> c.getContractType() == contractType), "All contract types should match the one provided in the DTO");
    }

    @Test
    public void testGetContracts_allValues_oneMatch() {
        // Arrange
        var searchDTO = getFullMatchDTO();

        // Act
        var actual = service.getContracts(searchDTO);

        // Assert
        assertEquals(1, actual.size(), "Contracts should contain exactly one match");
        
        var match = actual.iterator().next();
        assertEquals(searchDTO.getFirstName(), match.getOwner().getFirstName(), "Names should match");
        assertEquals(searchDTO.getStartDate(), match.getStartDate().toString(), "Start date should match");
        assertEquals(searchDTO.getUserType(), match.getOwner().getUserType(), "User type should match");
        assertEquals(searchDTO.getContractType(), match.getContractType(), "Contract type should match");
    }

    @Test
    public void testGetContracts_offByName_noMatch() {
        // Arrange
        var searchDTO = getFullMatchDTO();
        searchDTO.setFirstName("NO_MATCH_EVER");

        // Act
        var actual = service.getContracts(searchDTO);

        // Assert
        assertTrue(actual.isEmpty(), "There should not be a match");
    }

    @Test
    public void testGetContracts_offByStartDate_noMatch() {
        // Arrange
        var searchDTO = getFullMatchDTO();
        searchDTO.setStartDate("9999-01-01");

        // Act
        var actual = service.getContracts(searchDTO);

        // Assert
        assertTrue(actual.isEmpty(), "There should not be a match");
    }

    @Test
    public void testGetContracts_offByUserType_noMatch() {
        // Arrange
        var searchDTO = getFullMatchDTO();
 
        var noMatchType = Arrays.stream(UserType.values()).filter(t -> t != searchDTO.getUserType()).findFirst();
        if (!noMatchType.isPresent()) {
            fail("No other user type found");
        }
        searchDTO.setUserType(noMatchType.get());

        // Act
        var actual = service.getContracts(searchDTO);

        // Assert
        assertTrue(actual.isEmpty(), "There should not be a match");
    }

    @Test
    public void testGetContracts_offByContractType_noMatch() {
        // Arrange
        var searchDTO = getFullMatchDTO();

        var noMatchType = Arrays.stream(ContractType.values()).filter(t -> t != searchDTO.getContractType()).findFirst();
        if (!noMatchType.isPresent()) {
            fail("No other contract type found");
        }
        searchDTO.setContractType(noMatchType.get());

        // Act
        var actual = service.getContracts(searchDTO);

        // Assert
        assertTrue(actual.isEmpty(), "There should not be a match");
    }

    private ContractSearchDTO getFullMatchDTO() {
        final var name = "Bob";
        final var startDate = "2010-01-01";
        final var userType = UserType.BUSINESS;
        final var contractType = ContractType.GAS_AND_ELECTRICITY;

        return ContractSearchDTO.builder()
            .firstName(name)
            .startDate(startDate)
            .userType(userType)
            .contractType(contractType)
            .build();
    }
}
