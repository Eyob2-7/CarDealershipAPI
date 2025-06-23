package com.pluralsight.dealership_api.dao;

import com.pluralsight.dealership_api.dealership.SalesContract;

public interface SalesContractDao {
    SalesContract getById(int id);
    void add(SalesContract contract);
}
