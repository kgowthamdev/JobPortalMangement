package com.cts.jobportal.entity;

import com.cts.jobportal.constants.JobApplicationStatus;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long jobId;
    private Long applicantId;
    
    @Enumerated(EnumType.STRING)
    private JobApplicationStatus status;
}
