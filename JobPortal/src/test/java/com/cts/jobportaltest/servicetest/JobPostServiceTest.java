package com.cts.jobportaltest.servicetest;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.cts.jobportal.dto.JobPostDTO;
import com.cts.jobportal.dto.JobPostsResponseDTO;
import com.cts.jobportal.entity.JobPost;
import com.cts.jobportal.repo.JobPostRepository;
import com.cts.jobportal.service.JobPostService;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class JobPostServiceTest {

    @Mock
    private JobPostRepository jobPostRepository;

    @InjectMocks
    private JobPostService jobPostService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testCreateJobPost() {
        JobPostDTO dto = new JobPostDTO();
        dto.setCompanyId(1L);
        dto.setJobTitle("Software Engineer");
        dto.setPostedOn(LocalDateTime.now());

        JobPost jobPost = new JobPost();
        jobPost.setCompanyId(1L);
        jobPost.setJobTitle("Software Engineer");
        jobPost.setPostedOn(LocalDateTime.now());

        when(jobPostRepository.save(any(JobPost.class))).thenReturn(jobPost);

        JobPostDTO result = jobPostService.createJobPost(dto);

        assertNotNull(result);
        assertEquals("Software Engineer", result.getJobTitle());
    }

    @Test
    public void testGetAllJobPosts() {
        JobPost jobPost1 = new JobPost();
        jobPost1.setCompanyId(1L);
        jobPost1.setJobTitle("Software Engineer");

        JobPost jobPost2 = new JobPost();
        jobPost2.setCompanyId(1L);
        jobPost2.setJobTitle("Data Scientist");

        List<JobPost> jobPosts = Arrays.asList(jobPost1, jobPost2);

        when(jobPostRepository.findByCompanyId(anyLong())).thenReturn(jobPosts);

        JobPostsResponseDTO result = jobPostService.getAllJobPosts(1L);

        assertNotNull(result);
        assertEquals(2, result.getCount());
        assertEquals(2, result.getJobPosts().size());
    }

    @Test
    public void testGetJobPostById() {
        JobPost jobPost = new JobPost();
        jobPost.setCompanyId(1L);
        jobPost.setJobTitle("Software Engineer");

        when(jobPostRepository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.of(jobPost));

        JobPostDTO result = jobPostService.getJobPostById(1L, 1L);

        assertNotNull(result);
        assertEquals("Software Engineer", result.getJobTitle());
    }

    @Test
    public void testUpdateJobPost() {
        JobPostDTO dto = new JobPostDTO();
        dto.setJobTitle("Updated Software Engineer");

        JobPost existingJobPost = new JobPost();
        existingJobPost.setCompanyId(1L);
        existingJobPost.setJobTitle("Software Engineer");

        when(jobPostRepository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.of(existingJobPost));
        when(jobPostRepository.save(any(JobPost.class))).thenReturn(existingJobPost);

        JobPostDTO result = jobPostService.updateJobPost(1L, 1L, dto);

        assertNotNull(result);
        assertEquals("Updated Software Engineer", result.getJobTitle());
    }

    @Test
    public void testDeleteJobPost() {
        JobPost jobPost = new JobPost();
        jobPost.setCompanyId(1L);
        jobPost.setJobTitle("Software Engineer");

        when(jobPostRepository.findByIdAndCompanyId(anyLong(), anyLong())).thenReturn(Optional.of(jobPost));
        doNothing().when(jobPostRepository).delete(any(JobPost.class));

        jobPostService.deleteJobPost(1L, 1L);

        verify(jobPostRepository, times(1)).delete(jobPost);
    }
}