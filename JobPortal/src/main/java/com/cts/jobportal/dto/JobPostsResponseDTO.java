package com.cts.jobportal.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class JobPostsResponseDTO {
    private int count;
    private List<JobPostDTO> jobPosts;
}
