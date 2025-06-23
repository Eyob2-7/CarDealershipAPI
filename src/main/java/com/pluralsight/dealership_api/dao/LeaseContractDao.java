package com.pluralsight.dealership_api.dao;

import com.pluralsight.dealership_api.dealership.LeaseContract;

public interface LeaseContractDao {
    LeaseContract getById(int id);
    void add(LeaseContract contract);
}
