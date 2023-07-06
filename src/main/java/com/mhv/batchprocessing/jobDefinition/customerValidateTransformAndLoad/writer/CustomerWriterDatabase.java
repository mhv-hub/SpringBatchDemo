package com.mhv.batchprocessing.jobDefinition.customerValidateTransformAndLoad.writer;

import com.mhv.batchprocessing.entity.TransformedCustomer;
import com.mhv.batchprocessing.repository.CustomerRepository;
import org.springframework.batch.item.Chunk;
import org.springframework.batch.item.ItemWriter;
import org.springframework.batch.item.data.RepositoryItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

@Component
public class CustomerWriterDatabase implements ItemWriter<TransformedCustomer> {

    @Autowired
    private CustomerRepository customerRepository;

    @Bean(name = "customerDataWriterBean")
    public RepositoryItemWriter<TransformedCustomer> itemWriter(){
        RepositoryItemWriter<TransformedCustomer> repositoryItemWriter = new RepositoryItemWriter<>();
        repositoryItemWriter.setRepository(customerRepository);
        repositoryItemWriter.setMethodName("save");
        return repositoryItemWriter;
    }

    @Override
    public void write(Chunk<? extends TransformedCustomer> chunk) throws Exception {
        System.out.println("****** Writing data for customer to db");
    }
}
