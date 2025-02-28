package com.aj.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.aj.model.Response;
import com.aj.model.User;
import com.aj.repo.UserRepository;
import com.aj.service.GeneralService;
import com.aj.service.JwtService;
import com.aj.service.UserInfoService;
import com.aj.utils.FilesUtils;
import com.aj.utils.ResponseConstant;
import com.aj.vo.AuthRequest;
import com.aj.vo.AuthResponse;
import com.aj.vo.PasswordReset;
import com.aj.vo.UserVo;

@RestController
@RequestMapping("/api/auth")
public class UserController {

    @Autowired
    private UserInfoService userService;
    @Autowired
	AuthenticationManager authenticationManager;
    @Autowired
	private JwtService jwtService;
    
    @Autowired
    GeneralService generalService;
    
    @Autowired
    UserRepository repository;

	private static final Logger log = LoggerFactory.getLogger(UserController.class);

	 @PostMapping("/register")
	 public Response<UserVo> registerUser(@RequestBody User user) {
	        try {
	            UserVo responseUserVo = userService.addUser(user);
	            return new Response<>(ResponseConstant.SUCCESS_CODE, "User registered successfully.", responseUserVo);
	        } catch (RuntimeException ex) {
	            return new Response<>(ResponseConstant.VALIDATION_ERROR_CODE, ex.getMessage(), null);
	        } catch (Exception e) {
	            return new Response<>(ResponseConstant.INTERNAL_SERVER_ERROR_CODE, "An error occurred: " + e.getMessage(), null);
	        }
	    }



	 @PostMapping("/generateToken")
	 public Response<AuthResponse> authenticateAndGetToken(@RequestBody AuthRequest authRequest) {
	     try {
	         // Validate user credentials
	         User user = userService.validateUser(
	             authRequest.getUsername(), 
	             authRequest.getEmail(), 
	             authRequest.getMobileNumber(), 
	             authRequest.getPassword()
	         );

	         if (user == null) {
	             return new Response<>(
	                 ResponseConstant.UNAUTHORIZED_CODE,
	                 "Unauthorized: Invalid credentials.",
	                 null
	             );
	         }

	         // Generate token and create response
	         String token = jwtService.generateToken(user.getUsername());
	         AuthResponse authResponse = new AuthResponse(
	             token,
	             user.getRole().name(),
	             FilesUtils.getImage(null), // Placeholder for image logic
	             user.getEmail(),
	             user.getUsername()
	         );

	         // Determine the login method used
	         String loginField = determineLoginField(authRequest);
	         return new Response<>(
	             ResponseConstant.SUCCESS_CODE,
	             "Login successful using " + loginField,
	             authResponse
	         );

	     } catch (Exception e) {
	         log.error("Error during authentication: {}", e.getMessage());
	         return new Response<>(
	             ResponseConstant.INTERNAL_SERVER_ERROR_CODE,
	             "An error occurred: " + e.getMessage(),
	             null
	         );
	     }
	 }
	 
	 @PostMapping("/logout")
	    public Response<String> logout(@RequestHeader("Authorization") String authorizationHeader) {
	        try {
	            if (authorizationHeader == null || authorizationHeader.isEmpty()) {
	                return new Response<>(
	                    ResponseConstant.BAD_REQUEST_CODE,
	                    "Authorization header is required",
	                    null
	                );
	            }

	            boolean success = userService.logout(authorizationHeader);
	            if (success) {
	                return new Response<>(
	                    ResponseConstant.SUCCESS_CODE,
	                    "Logout successful. Token invalidated in real-time.",
	                    null
	                );
	            } else {
	                return new Response<>(
	                    ResponseConstant.UNAUTHORIZED_CODE,
	                    "Logout failed: Invalid or already expired/blacklisted token",
	                    null
	                );
	            }
	        } catch (Exception e) {
	            return new Response<>(
	                ResponseConstant.INTERNAL_SERVER_ERROR_CODE,
	                "An error occurred during logout: " + e.getMessage(),
	                null
	            );
	        }
	    }
	 // Helper method to determine login field
	 private String determineLoginField(AuthRequest authRequest) {
	     if (authRequest.getUsername() != null && !authRequest.getUsername().isEmpty()) {
	         return "username: " + authRequest.getUsername();
	     } else if (authRequest.getEmail() != null && !authRequest.getEmail().isEmpty()) {
	         return "email: " + authRequest.getEmail();
	     } else if (authRequest.getMobileNumber() != null) {
	         return "mobile number: " + authRequest.getMobileNumber();
	     }
	     return "unknown field";
	 }

	 
	 @PostMapping("/forgot-password")
	 public Response<String> forgotPassword(@RequestBody PasswordReset request) {
	     try {
	         if (request.getEmail() == null) {
	             throw new RuntimeException("Email is required");
	         }
	         userService.forgotPassword(request.getEmail());
	         return new Response<>(
	                 ResponseConstant.SUCCESS_CODE,
	                 "If the email exists, a reset link has been sent.",
	                 null
	         );
	     } catch (RuntimeException e) {
	         return new Response<>(
	                 ResponseConstant.BAD_REQUEST_CODE,
	                 e.getMessage().equals(ResponseConstant.TOO_MANY_REQUEST_MSG) ? ResponseConstant.TOO_MANY_REQUEST_MSG : e.getMessage(),
	                 null
	         );
	     } catch (Exception e) {
	         return new Response<>(
	                 ResponseConstant.INTERNAL_SERVER_ERROR_CODE,
	                 "An unexpected error occurred",
	                 null
	         );
	         }
	     }

	    @PostMapping("/reset-password")
	    public Response<String> resetPassword(@RequestBody PasswordReset request) {
	        try {
	            if (request.getToken() == null || request.getNewPassword() == null) {
	                throw new RuntimeException("Token and new password are required");
	            }
	            boolean success = userService.resetPassword(request.getToken(), request.getNewPassword());
	            if (success) {
	                return new Response<>(
	                    ResponseConstant.SUCCESS_CODE,
	                    "Password reset successfully",
	                    null
	                );
	            } else {
	                return new Response<>(
	                    ResponseConstant.BAD_REQUEST_CODE,
	                    "Invalid or expired reset token",
	                    null
	                );
	            }
	        } catch (RuntimeException e) {
	            return new Response<>(
	                ResponseConstant.BAD_REQUEST_CODE,
	                e.getMessage(),
	                null
	            );
	        } catch (Exception e) {
	            return new Response<>(
	                ResponseConstant.INTERNAL_SERVER_ERROR_CODE,
	                "An unexpected error occurred",
	                null
	            );
	        }
	    }



 // check if Username is exist
    @GetMapping("/username/avaibility")
    public ResponseEntity<String> varifyUsernameAvailibiility(@RequestParam("un") String user) {
         if(userService.isUsernameAvailable(user)) {
        	 return ResponseEntity.ok("Username available.");
         }
            return ResponseEntity.badRequest().body("Username already taken.");
        
    }

    @PutMapping("/update-details") // Restrict access to authorized users
    public Response<UserVo> updateUserDetails(@RequestBody UserVo userVo) {
        log.info("Received request to update user details for user: {}", userVo.getUsername());
        try {
            UserVo updatedUser = userService.updateUserDetails(userVo);
            return new Response<>(ResponseConstant.SUCCESS_CODE, "User details updated successfully", updatedUser);
        } catch (RuntimeException e) {
            log.error("Error updating user details: {}", e.getMessage());
            return new Response<>(ResponseConstant.VALIDATION_ERROR_CODE, e.getMessage(), null);
        } catch (Exception e) {
            log.error("Unexpected error updating user details: {}", e.getMessage());
            return new Response<>(ResponseConstant.INTERNAL_SERVER_ERROR_CODE, "Failed to update user details", null);
        }
    }



  
    @GetMapping("/profile")
    @Secured(value = { "ROLE_USER" })
    public Response<UserVo> getUserProfile() {
        try {
            log.info("Fetching profile for the current user...");
            
            // Fetch the profile from the service
            UserVo userProfile = userService.getUserProfile();
            
            // Return success response
            return new Response<>(
                ResponseConstant.SUCCESS_CODE,
                "User profile fetched successfully.",
                userProfile
            );
        } catch (RuntimeException ex) {
            log.error("Error occurred while fetching user profile: {}", ex.getMessage());
            
            // Return error response
            return new Response<>(
                ResponseConstant.VALIDATION_ERROR_CODE,
                ex.getMessage(),
                null
            );
        } catch (Exception ex) {
            log.error("Unexpected error occurred: {}", ex.getMessage());
            
            // Return generic error response
            return new Response<>(
                ResponseConstant.INTERNAL_SERVER_ERROR_CODE,
                "An unexpected error occurred.",
                null
            );
        }
    }

    @PostMapping("/profile-change")
	public Response<byte[]> uploadFile(@RequestParam("file") MultipartFile file) {
		try {
			String currentUsername = generalService.getCurrentUsername();
			
			if (currentUsername != null) {
				User user = userService.getUserByUsername(currentUsername);
				if (user != null) {
					String username = user.getUsername();
					user.setProfilePath(FilesUtils.storeImage(file, "profile", username));
					user=repository.save(user);
					String path = user.getProfilePath();
					return new Response<byte[]>(ResponseConstant.SUCCESS_CODE, "profile changed successfully",
							FilesUtils.getImage(path));
				}else {
					return new Response<byte[]>(ResponseConstant.RECORD_NOT_FOUND_CODE, "User not found uisng id", null);
				}
			}
			return new Response<byte[]>(ResponseConstant.RECORD_NOT_FOUND_CODE, "User not found uisng id", null);
		} catch (Exception e) {
			return new Response<byte[]>(ResponseConstant.INTERNAL_SERVER_ERROR_CODE, e.getMessage(), null);
		}
	}
}

    


