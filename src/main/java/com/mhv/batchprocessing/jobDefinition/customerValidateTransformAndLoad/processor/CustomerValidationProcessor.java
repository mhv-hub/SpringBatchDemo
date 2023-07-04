package com.mhv.batchprocessing.jobDefinition.customerValidateTransformAndLoad.processor;

import com.mhv.batchprocessing.entity.Customer;
import com.mhv.batchprocessing.entity.CustomerType;
import com.mhv.batchprocessing.entity.Location;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
@Qualifier(value = "customerCsvDataProcessor")
public class CustomerValidationProcessor implements ItemProcessor<Customer, Customer> {

    @Override
    public Customer process(Customer customer) throws Exception {
        if(customer.getCustomerName() == null || customer.getCustomerName().length() < 3) {
            System.out.println("Record reject due to invalid name [ CUSTOMER : " + customer + " ]");
            return null;
        }
        if(customer.getCustomerType() == null || Arrays.stream(CustomerType.values()).noneMatch(type -> type.toString().equals(customer.getCustomerType()))){
            System.out.println("Record reject due to invalid customer type [ CUSTOMER : " + customer + " ]");
            return null;
        }
        if(customer.getLocation() == null || Arrays.stream(Location.values()).noneMatch(location -> location.toString().equals(customer.getLocation()))){
            System.out.println("Record reject due to invalid location [ CUSTOMER : " + customer + " ]");
            return null;
        }
        if(customer.getActiveStatus() == null || (!customer.getActiveStatus().equals("ACTIVE")) && !customer.getActiveStatus().equals("INACTIVE")){
            System.out.println("Record reject due to invalid active status value [ CUSTOMER : " + customer + " ]");
            return null;
        }
        return customer;
    }
}
