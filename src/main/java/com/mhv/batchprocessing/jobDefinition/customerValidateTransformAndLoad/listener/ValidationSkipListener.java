package com.mhv.batchprocessing.jobDefinition.customerValidateTransformAndLoad.listener;

import com.mhv.batchprocessing.dto.RejectedRecord;
import com.mhv.batchprocessing.entity.Customer;
import org.springframework.batch.core.SkipListener;
import org.springframework.batch.core.configuration.annotation.StepScope;
import org.springframework.batch.item.ExecutionContext;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Qualifier(value = "validationSkipListenerBean")
@StepScope
@SuppressWarnings({"ConstantConditions", "unchecked"})
public class ValidationSkipListener implements SkipListener<Customer, Customer> {

    @Value("#{stepExecution.jobExecution.executionContext}")
    private ExecutionContext executionContext;

    @Override
    public void onSkipInRead(Throwable t) {
        System.out.println(">>>>>>>>>>>>>>> Error in CSV record read. Record skipped [ Error : " + t.getClass().getName() + " - " + t.getMessage() + " ]");
        List<RejectedRecord> rejectedRecords = (List<RejectedRecord>) executionContext.get("rejectedRecordList");
        rejectedRecords.add(new RejectedRecord(t.getClass().getSimpleName() + " -> " + t.getMessage(), null));
    }

    @Override
    public void onSkipInWrite(Customer customer, Throwable t) {
        System.out.println(">>>>>>>>>>>>>>> Error in Kafka write. Record skipped [ Error : " + t.getClass().getName() + " - " + t.getMessage() + " ]");
        List<RejectedRecord> rejectedRecords = (List<RejectedRecord>) executionContext.get("rejectedRecordList");
        rejectedRecords.add(new RejectedRecord(t.getClass().getSimpleName() + " -> " + t.getMessage(), customer.toStringCsvFormat().toString()));
    }

    @Override
    public void onSkipInProcess(Customer customer, Throwable t) {
        System.out.println(">>>>>>>>>>>>>>> Error in customer validation. Record skipped [ Error : " + t.getClass().getName() + " - " + t.getMessage() + " ]");
        List<RejectedRecord> rejectedRecords = (List<RejectedRecord>) executionContext.get("rejectedRecordList");
        rejectedRecords.add(new RejectedRecord(t.getClass().getSimpleName() + " -> " + t.getMessage(), customer.toStringCsvFormat().toString()));
    }
}
