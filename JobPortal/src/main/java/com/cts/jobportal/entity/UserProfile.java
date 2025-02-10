package com.cts.jobportal.entity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Long applicantId;
    private String firstname;
    private String lastname;
    private String email;
    private String contact;
    private String location;
    private String dob;
    private String description;

    @Convert(converter = SkillsConverter.class)
    private List<String> skills;

    private String resume;
}
