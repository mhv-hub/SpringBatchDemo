package com.mhv.batchprocessing.jobDefinition.customerValidateTransformAndLoad.listener;

import com.mhv.batchprocessing.entity.Customer;
import org.springframework.batch.core.SkipListener;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier(value = "validationSkipListenerBean")
public class ValidationSkipListener implements SkipListener<Customer, Customer> {

    @Override
    public void onSkipInRead(Throwable t) {
        System.out.println(">>>>>>>>>>>>>>> Error in CSV record read. Record skipped [ Error : " + t.getClass().getName() + " - " + t.getMessage() + " ]");
    }

    @Override
    public void onSkipInWrite(Customer customer, Throwable t) {
        System.out.println(">>>>>>>>>>>>>>> Error in Kafka write. Record skipped [ Error : " + t.getClass().getName() + " - " + t.getMessage() + " ]");
    }

    @Override
    public void onSkipInProcess(Customer customer, Throwable t) {
        System.out.println(">>>>>>>>>>>>>>> Error in customer validation. Record skipped [ Error : " + t.getClass().getName() + " - " + t.getMessage() + " ]");
    }
}
