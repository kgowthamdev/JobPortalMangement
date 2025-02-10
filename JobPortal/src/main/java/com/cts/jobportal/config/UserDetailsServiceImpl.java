package com.cts.jobportal.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import org.springframework.stereotype.Component;

import com.cts.jobportal.constants.ExceptionMessages;
import com.cts.jobportal.entity.ApplicantUser;
import com.cts.jobportal.entity.CompanyUser;
import com.cts.jobportal.exception.UserException;
import com.cts.jobportal.repo.ApplicantUserRepository;
import com.cts.jobportal.repo.CompanyUserRepository;


@Component
public class UserDetailsServiceImpl implements UserDetailsService {

	@Autowired
	private ApplicantUserRepository userRepository;

	@Autowired
	private CompanyUserRepository companyUserRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws RuntimeException {

		ApplicantUser user = userRepository.findByUsername(username);
		CompanyUser companyUser = companyUserRepository.findByUsername(username);
		if (user == null && companyUser == null) {
			throw new UserException(ExceptionMessages.USERNAME_NOT_FOUND, HttpStatus.NOT_FOUND);
		}
		if (user == null) {
			user = new ApplicantUser();
			user.setApplicantId(companyUser.getCompanyId());
			user.setUsername(companyUser.getUsername());
			user.setPassword(companyUser.getPassword());
			user.setEmail(companyUser.getEmail());
			user.setRoles(companyUser.getRoles());
		}
		return new CustomerUserDetails(user);
	}

}
