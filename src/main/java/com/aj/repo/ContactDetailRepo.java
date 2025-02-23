package com.aj.repo;

import org.springframework.data.jpa.repository.JpaRepository;

import com.aj.model.ContactDetails;

public interface ContactDetailRepo extends JpaRepository<ContactDetails, Integer> {

}