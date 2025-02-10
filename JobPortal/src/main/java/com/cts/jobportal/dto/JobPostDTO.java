package com.cts.jobportal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobPostDTO {
    private Long id;
    private Long companyId;
    private String jobTitle;
    private List<String> skills;
    private String description;
    private String workExperience;
    private String salaryRange;
    private String location;
    private String employmentType;
    private int noApplied;
    private LocalDateTime postedOn;
}
