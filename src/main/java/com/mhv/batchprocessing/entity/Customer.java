package com.mhv.batchprocessing.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Customer {

    private long id;
    private String uniqueId;
    private String customerName;
    private String customerGender;
    private LocalDate customerDateOfBirth;
    private String customerType;
    private String location;
    private LocalDate joiningDate;
    private String activeStatus;
}
