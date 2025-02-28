package com.aj.repo;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.aj.model.Role;
import com.aj.model.User;
import com.aj.vo.UserVo;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

	Optional<User> findByUsername(String username);

	Optional<User> findByMobileNumber(String mobileNumber);

	Optional<User> findByEmail(String email);

	boolean existsByUsername(String username);

	boolean existsByEmail(String email);

	boolean existsByMobileNumber(String mobileNumber);

	List<UserVo> findByOrginizationIdAndRole(long orginizationId, Role role);

	void save(Optional<User> user);



	

}
