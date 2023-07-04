package com.mhv.batchprocessing.controller;

import com.mhv.batchprocessing.exceptionHandeller.CustomException;
import com.mhv.batchprocessing.service.customer.CustomerService;
import org.springframework.batch.core.JobParametersInvalidException;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartException;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;

@RestController
public class CustomerController {

    @Autowired
    private CustomerService customerService;

    @PostMapping("/api/customer/process-and-load")
    public ResponseEntity<String> validateInputFileAndTriggerCustomerService(@RequestParam("csv-customer-data") MultipartFile multipartFile) throws IOException, MultipartException, CustomException, JobInstanceAlreadyCompleteException, JobExecutionAlreadyRunningException, JobParametersInvalidException, JobRestartException {
        if(multipartFile == null || multipartFile.getOriginalFilename() == null){
            throw new CustomException("Invalid or no file provided");
        }
        String fileName = multipartFile.getOriginalFilename();
        if(!fileName.endsWith(".csv")){
            throw new CustomException("Only CSV file is supported. Please upload a valid file");
        }else{
            int randomNum = (int) (Math.random() * 9999) + 1;
            fileName = "customerDataFile_" + randomNum + ".csv";
        }
        File filLocation = new ClassPathResource("customerData/").getFile();
        Path path = Paths.get(filLocation.getAbsolutePath() + File.separator + fileName);
        Files.copy(multipartFile.getInputStream(), path, StandardCopyOption.REPLACE_EXISTING);
        customerService.triggerCustomerValidationJob(fileName);
        return ResponseEntity.ok().body("Job started");
    }
}
