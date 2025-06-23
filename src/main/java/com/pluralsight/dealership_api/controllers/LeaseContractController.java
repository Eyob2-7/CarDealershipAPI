package com.pluralsight.dealership_api.controllers;

import com.pluralsight.dealership_api.dao.LeaseContractDao;
import com.pluralsight.dealership_api.models.LeaseContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller for managing Lease Contracts.
 * Supports creating new lease contracts and retrieving them by ID.
 */

@RestController
@RequestMapping("/api/lease-contracts")
public class LeaseContractController {

    private final LeaseContractDao dao;

    /**
     * Constructor-based injection of the DAO implementation.
     * @param dao LeaseContractDao implementation
     */

    @Autowired
    public LeaseContractController(LeaseContractDao dao) {
        this.dao = dao;
    }

    /**
     * Retrieves a lease contract by its ID.
     * @param id the lease_contract_id (primary key)
     * @return the corresponding LeaseContract, or null if not found
     */

    @GetMapping("/{id}")
    public LeaseContract getById(@PathVariable int id) {
        return dao.getById(id);
    }

    /**
     * Creates a new lease contract.
     * The expected ending value and lease fee are calculated in the model.
     * @param contract the LeaseContract object sent in the request body
     */

    @PostMapping
    public void add(@RequestBody LeaseContract contract) {
        dao.add(contract);
    }
}

