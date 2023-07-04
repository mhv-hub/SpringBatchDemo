package com.mhv.batchprocessing.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDate;

@Entity
@Table(name = "CUSTOMER_INFO")
@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "CUSTOMER_ID")
    private long id;

    @Column(name = "NAME", length = 50)
    private String customerName;

    @Column(name = "GENDER", length = 6)
    private String customerGender;

    @Column(name = "DOB")
    private LocalDate customerDateOfBirth;

    @Column(name = "TYPE", length = 30)
    private String customerType;

    @Column(name = "LOCATION", length = 40)
    private String location;

    @Column(name = "JOINING_DATE")
    private LocalDate joiningDate;

    @Column(name = "ACTIVE_STATUS")
    private String activeStatus;
}
