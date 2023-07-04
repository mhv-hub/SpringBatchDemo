package com.mhv.batchprocessing.jobDefinition.customerValidateTransformAndLoad.listener;

import org.springframework.batch.core.ExitStatus;
import org.springframework.batch.core.StepExecution;
import org.springframework.batch.core.StepExecutionListener;
import org.springframework.core.io.ClassPathResource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class CsvProcessStepListener implements StepExecutionListener {

    @Override
    public ExitStatus afterStep(StepExecution stepExecution) {
        try {
            String fileName = stepExecution.getJobParameters().getString("fileName");
            System.out.println("FileName to be deleted : " + fileName);
            runCustomMethod(fileName);
            System.out.println("File deleted : " + fileName);
        } catch (IOException e) {
            System.out.println("CSV file deletion failed : " + e.getMessage());
        }
        return stepExecution.getExitStatus();
    }
    private void runCustomMethod(String fileName) throws IOException {
        File fileLocation = new ClassPathResource("customerData/").getFile();
        Path path = Paths.get(fileLocation.getAbsolutePath() + File.separator + fileName);
        Files.delete(path);
    }
}
