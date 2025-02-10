package com.cts.jobportal.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class CompanyProfilesResponseDTO {

    private int count;
    private List<CompanyProfileDTO> companyProfiles;
}
