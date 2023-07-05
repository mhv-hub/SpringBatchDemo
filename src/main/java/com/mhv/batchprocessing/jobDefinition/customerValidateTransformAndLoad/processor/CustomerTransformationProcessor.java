package com.mhv.batchprocessing.jobDefinition.customerValidateTransformAndLoad.processor;

import com.mhv.batchprocessing.entity.Customer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier(value = "customerTransformationProcessorBean")
public class CustomerTransformationProcessor implements ItemProcessor<Customer, Customer> {

    @Override
    public Customer process(Customer customer) throws Exception {
        System.out.println("#### From Transformer : " + customer);
        return customer;
    }
}
