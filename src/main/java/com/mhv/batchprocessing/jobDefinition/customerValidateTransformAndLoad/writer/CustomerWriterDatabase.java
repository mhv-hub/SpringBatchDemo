package com.mhv.batchprocessing.jobDefinition.customerValidateTransformAndLoad.writer;

import com.mhv.batchprocessing.entity.Customer;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
@Qualifier(value = "customerDataWriterBean")
public class CustomerWriterDatabase implements ItemWriter<Customer> {
    @Override
    public void write(Chunk<? extends Customer> chunk) throws Exception {
        System.out.println("****** Writing data for customer to db");
    }
}
