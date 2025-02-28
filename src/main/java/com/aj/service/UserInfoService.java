package com.aj.service;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.aj.api.UserController;
import com.aj.model.Role;
import com.aj.model.User;
import com.aj.repo.UserRepository;
import com.aj.utils.DateUtil;
import com.aj.utils.FilesUtils;
import com.aj.vo.UserInfoDetails;
import com.aj.vo.UserVo;

@Service
public class UserInfoService implements UserDetailsService {

	@Autowired
	private UserRepository repository;
	
	private static final Logger log = LoggerFactory.getLogger(UserController.class);

	@Autowired
	private PasswordEncoder encoder;
	@Autowired
	private BCryptPasswordEncoder passwordEncoder; // Inject BCryptPasswordEncoder
	
	@Autowired
	GeneralService generalService;
	
	@Autowired
	CommonService commonService;
	
	@Autowired
	JwtService  jwtService;
	
	@Autowired
    private ResetTokenService tokenService;

    @Autowired
    private EmailService emailService;

    @Autowired
    private RateLimitService rateLimitService;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Optional<User> userDetail = repository.findByUsername(username);

		return userDetail.map(UserInfoDetails::new) // Map the User to UserInfoDetails
				.orElseThrow(() -> new UsernameNotFoundException("User not found: " + username)); // Throw excep
	}


	public User validateUser(String username, String email, String mobileNumber, String password) {
	    log.info("Validating user with credentials: username = {}, email = {}, mobileNumber = {}", 
	             username, email, mobileNumber);

	    User user = null;

	    if (username != null) {
	        user = repository.findByUsername(username).orElse(null);
	    }
	    if (user == null && email != null) {
	        user = repository.findByEmail(email).orElse(null);
	    }
	    if (user == null && mobileNumber != null) {
	        user = repository.findByMobileNumber(mobileNumber).orElse(null);
	    }

	    if (user == null) {
	        log.warn("User not found with the provided credentials.");
	        return null;
	    }

	    // Validate password
	    if (!passwordEncoder.matches(password, user.getPassword())) {
	        log.warn("Invalid password for user: {}", user.getUsername());
	        return null;
	    }

	    log.info("User authenticated successfully: {}", user.getUsername());
	    return user;
	}
	
	public boolean logout(String token) {
        try {
            String jwt = token.startsWith("Bearer ") ? token.substring(7) : token;
            String username = jwtService.extractUsername(jwt);

            if (jwtService.validateToken(jwt, new org.springframework.security.core.userdetails.User(
                    username, "", new java.util.ArrayList<>())) && !jwtService.isTokenBlacklisted(jwt)) {
                jwtService.blacklistToken(jwt);
               return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            return false;
        }
    }
        
	public User getUserByUsername(String username) {
		return repository.findByUsername(username).orElse(null);
	}

	public boolean isUsernameAvailable(String user) {
		User userD = repository.findByUsername(user).orElse(null); // Fetch user by username
		return userD == null;
	}

	public User getById(Long id) {
		return repository.findById(id).orElse(null);
	}

	/**
	 * 
	 * @param userInfo     this api is used to register a new user on the application 
	 * mobilenumber, username, and eamilid must be unique two user cannot register using same data 
	 * and role of the register user is bydefault user 
	 * 
	 * @return
	 */
	
	//used for registration
	public UserVo addUser(User userVo) {
	    // Validate required fields
	    if (userVo.getUsername() == null || userVo.getUsername().isEmpty()) {
	        throw new RuntimeException("Username is required");
	    }
	    if (userVo.getPassword() == null || userVo.getPassword().isEmpty()) {
	        throw new RuntimeException("Password is required");
	    }
	    if (userVo.getEmail() == null || userVo.getEmail().isEmpty()) {
	        throw new RuntimeException("Email is required");
	    }
	    if (userVo.getMobileNumber() == null || userVo.getMobileNumber().isEmpty()) {
	        throw new RuntimeException("Mobile number is required");
	    }

	    // Check for duplicate entries
	    if (repository.existsByUsername(userVo.getUsername())) {
	        throw new RuntimeException("Username '" + userVo.getUsername() + "' already exists.");
	    }
	    if (repository.existsByEmail(userVo.getEmail())) {
	        throw new RuntimeException("Email '" + userVo.getEmail() + "' already exists.");
	    }
	    if (repository.existsByMobileNumber(userVo.getMobileNumber())) {
	        throw new RuntimeException("Mobile number '" + userVo.getMobileNumber() + "' already exists.");
	    }

	    // Create a new User entity and copy properties
	    User user = new User();
	    BeanUtils.copyProperties(userVo, user);

	    // Validate input fields
	    validatePasswordStrength(userVo.getPassword());
	    validateMobileNumber(userVo.getMobileNumber());
	    validateEmailFormat(userVo.getEmail());

	    // Encode password and set additional fields
	    user.setPassword(passwordEncoder.encode(userVo.getPassword()));
	    user.setCreatedDate(new Timestamp(System.currentTimeMillis()));

	    // Determine and set the role
	    String role = (userVo.getRole() != null && !userVo.getRole().isBlank()) ? userVo.getRole().name() : "ROLE_USER";
	    try {
	        user.setRole(Role.valueOf(role));
	    } catch (IllegalArgumentException e) {
	        throw new RuntimeException("Invalid role provided: " + role);
	    }

	    // Save the user to the database
	    User savedUser = repository.save(user);

	    // Prepare the UserVo for response
	    UserVo savedUserVo = new UserVo();
	    BeanUtils.copyProperties(savedUser, savedUserVo);
	    savedUserVo.setCreatedDate(DateUtil.convertTimestampToString(savedUser.getCreatedDate()));
	    savedUserVo.setRole(role);

	    return savedUserVo;
	}



	public List<UserVo> addUser1(User userInfo) {
		 List<UserVo> list = new ArrayList<>();
		 
		 Long orginizationId = commonService.orginizationIdIdFromContext();
	        userInfo.setOrginizationId(orginizationId);
	    if (userInfo.getUsername() == null || userInfo.getUsername().isEmpty()) {
	        throw new RuntimeException("Username is required");
	    }
	    if (userInfo.getPassword() == null || userInfo.getPassword().isEmpty()) {
	        throw new RuntimeException("Password is required");
	    }
	    if (userInfo.getEmail() == null || userInfo.getEmail().isEmpty()) {
	        throw new RuntimeException("Email is required");
	    }
	    if (userInfo.getMobileNumber() == null ||userInfo.getMobileNumber().isEmpty()) {
	        throw new RuntimeException("Mobile number is required");
	    }

	    if (repository.existsByUsername(userInfo.getUsername())) {
	        throw new RuntimeException("Username '" + userInfo.getUsername() + "' already exists.");
	    }
	    if (repository.existsByEmail(userInfo.getEmail())) {
	        throw new RuntimeException("Email '" + userInfo.getEmail() + "' already exists.");
	    }
	    if (repository.existsByMobileNumber(userInfo.getMobileNumber())) {
	        throw new RuntimeException("Mobile number '" + userInfo.getMobileNumber() + "' already exists.");
	    }
	    userInfo.setPassword(passwordEncoder.encode(userInfo.getPassword()));
	    
        validatePasswordStrength(userInfo.getPassword());
	    validateMobileNumber(userInfo.getMobileNumber());
	    validateEmailFormat(userInfo.getEmail());

        User savedUser = repository.save(userInfo);
        if (savedUser != null && savedUser.getOrginizationId() != 0) {
            repository.findByOrginizationIdAndRole(savedUser.getOrginizationId(), userInfo.getRole())
                .forEach(e -> {
                    UserVo userVo = new UserVo();
                    BeanUtils.copyProperties(e, userVo);
                    userVo.setPassword(null);
                    userVo.setCreatedDate(DateUtil.convertTimestampToString(savedUser.getCreatedDate()));
                    userVo.setRole(e.getRole());
                    list.add(userVo);
                });
        } else if (savedUser != null) {
            UserVo userVo = new UserVo();
            BeanUtils.copyProperties(savedUser, userVo);
            userVo.setPassword(null);
            userVo.setRole(savedUser.getRole().name());
            list.add(userVo);
        }
        return list;
	}
	
	public void forgotPassword(String email) {
        validateEmailFormat(email);

        if (!rateLimitService.allowRequest(email)) {
            throw new RuntimeException("Too many requests. Please try again later.");
        }

        Optional<User> user = repository.findByEmail(email);
        if (user == null) {
            return; // Silent failure
        }

        String resetToken = tokenService.generateResetToken(email);
        emailService.sendResetPasswordEmail(email, resetToken);
    }
	
	
	public boolean resetPassword(String token, String newPassword) {
	    if (token == null) {
	        throw new RuntimeException("Reset token is required");
	    }
	    validatePasswordStrength(newPassword);

	    String email = tokenService.validateAndGetEmail(token);
	    if (email == null) {
	        return false;
	    }

	    Optional<User> optionalUser = repository.findByEmail(email);
	    if (optionalUser.isEmpty()) {
	        return false;
	    }

	    User user = optionalUser.get();
	    user.setPassword(newPassword);
	  //  user.encodePassword(passwordEncoder);
	    repository.save(user);
	    return true;
	}

	 
	 private void validateMobileNumber(String mobileNumber) {
		    // Check if mobile number is not null and matches exactly 10 digits.
		    if (mobileNumber == null || !mobileNumber.matches("^\\d{10}$")) {
		        throw new RuntimeException("Invalid mobile number format. It must be 10 digits.");
		    }
		}

		private void validateEmailFormat(String email) {
		    // Email format validation using a more precise regex.
		    String emailRegex = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
		    if (email == null || !email.matches(emailRegex)) {
		        throw new RuntimeException("Invalid email format.");
		    }
		}

		private void validatePasswordStrength(String password) {
		    // Ensure password is at least 8 characters long, contains uppercase, lowercase, digit, and special character.
		    if (password == null || password.length() < 8 || 
		        !password.matches(".*[A-Z].*") || // Contains at least one uppercase letter
		        !password.matches(".*[a-z].*") || // Contains at least one lowercase letter
		        !password.matches(".*\\d.*") ||   // Contains at least one digit
		        !password.matches(".*[!@#$%^&*()_+\\-=\\[\\]{};':\"\\\\|,.<>\\/?].*")) { // Contains special character
		        throw new RuntimeException("Password must be at least 8 characters long, contain at least one uppercase letter, one lowercase letter, one digit, and one special character.");
		    }
		}

  
	    
	/**
	 * user can update their mobilenumber, profilepath and username  by using this api of the at currentusername
	 * @param userVo
	 * @return
	 */

	//update profile
	public UserVo updateUserDetails(UserVo userVo) {
        log.info("Attempting to update user details");

        String currentUsername = generalService.getCurrentUsername();
        User user = repository.findByUsername(currentUsername)
                .orElseThrow(() -> new RuntimeException("User not found for username: " + currentUsername));
        if (userVo.getUsername() != null) {
            user.setUsername(userVo.getUsername());
            log.info("Updated username to: {}", userVo.getUsername());
        }
        if (userVo.getMobileNumber() != null) {
            user.setMobileNumber(userVo.getMobileNumber());
            log.info("Updated mobile number to: {}", userVo.getMobileNumber());
        }
        if (userVo.getProfilePath() != null) {
            userVo.setProfilePath(userVo.getProfilePath());
            log.info("Updated profile photo.");
        }
        user.setUpdatedDate(new Timestamp(System.currentTimeMillis()));

        User updatedUser = repository.save(user);

        log.info("User details updated successfully for user: {}", updatedUser.getUsername());

        UserVo responseVo = new UserVo();
        BeanUtils.copyProperties(updatedUser, responseVo);
        responseVo.setUpdatedDate(DateUtil.convertTimestampToString(updatedUser.getUpdatedDate()));

        return responseVo;
    }


	   public UserVo getUserProfile() {
        String currentUsername = generalService.getCurrentUsername();
        
        User user = repository.findByUsername(currentUsername)
                .orElseThrow(() -> new RuntimeException("User not found for username: " + currentUsername));

        UserVo userVo = new UserVo();
        BeanUtils.copyProperties(user, userVo);

        userVo.setProfilePath(FilesUtils.getImage(user.getProfilePath()));
        System.out.println(user.getProfilePath());
        
        String createdDate = DateUtil.convertTimestampToString(user.getCreatedDate());
        userVo.setCreatedDate(createdDate);

        return userVo;
     }
	   
	


}