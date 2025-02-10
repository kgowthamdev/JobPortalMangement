package com.cts.jobportal.dto;

import com.cts.jobportal.constants.JobApplicationStatus;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobApplicationDTO {
    private Long id;
    private Long jobId;
    private Long applicantId;
    private JobApplicationStatus status;
}
