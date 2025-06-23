package com.pluralsight.dealership_api.controllers;

import com.pluralsight.dealership_api.dao.SalesContractDao;
import com.pluralsight.dealership_api.models.SalesContract;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * REST controller to handle endpoints related to Sales Contracts.
 * Supports creating a new sales contract and retrieving a contract by ID.
 */

@RestController
@RequestMapping("/api/sales-contracts")
public class SalesContractsController {

    private final SalesContractDao dao;

    /**
     * Constructor-based injection of the SalesContractDao.
     * Spring will automatically wire the correct implementation.
     *
     * @param dao SalesContractDao to interact with the database
     */

    @Autowired
    public SalesContractsController(SalesContractDao dao) {
        this.dao = dao;
    }

    /**
     * Retrieves a SalesContract by its ID.
     *
     * @param id The unique ID of the sales contract
     * @return The SalesContract object, or null if not found
     */

    @GetMapping("/{id}")
    public SalesContract getById(@PathVariable int id) {
        return dao.getById(id);
    }

    /**
     * Adds a new sales contract to the database.
     * All fee calculations (sales tax, processing, etc.) are done in the model.
     *
     * @param contract The SalesContract object sent from the client (JSON)
     */

    @PostMapping
    public void add(@RequestBody SalesContract contract) {
        dao.add(contract);
    }
}
