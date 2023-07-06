package com.mhv.batchprocessing.jobDefinition.customerValidateTransformAndLoad.processor;

import com.mhv.batchprocessing.entity.Customer;
import com.mhv.batchprocessing.entity.TransformedCustomer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier(value = "customerTransformationProcessorBean")
public class CustomerTransformationProcessor implements ItemProcessor<Customer, TransformedCustomer> {

    @Override
    public TransformedCustomer process(Customer customer) throws Exception {
        TransformedCustomer transformedCustomer = new TransformedCustomer(customer);
        transformedCustomer
                .transformCustomerName(customer.getCustomerName())
                .transformLocation(customer.getLocation())
                .transformMembershipStatus(customer.getJoiningDate());
        System.out.println("#### Transformed Customer : " + transformedCustomer);
        return transformedCustomer;
    }
}
