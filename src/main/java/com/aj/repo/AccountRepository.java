package com.aj.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aj.model.AccountDetails;

@Repository
public interface AccountRepository extends JpaRepository<AccountDetails, Long> {

}
