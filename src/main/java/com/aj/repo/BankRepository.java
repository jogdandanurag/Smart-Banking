package com.aj.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aj.model.BankDetails;
import com.aj.vo.BankDetailsVo;


@Repository
public interface BankRepository extends JpaRepository<BankDetails, Long> {

	BankDetails save(BankDetailsVo bankRegister);

	boolean existsByBankName(String bankName);

}
