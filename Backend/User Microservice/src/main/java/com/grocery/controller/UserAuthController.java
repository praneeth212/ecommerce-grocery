package com.grocery.controller;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import com.grocery.config.JwtProvider;
import com.grocery.domain.USER_ROLE;
import com.grocery.exception.UserException;
import com.grocery.modal.Address;
import com.grocery.modal.PasswordResetToken;
import com.grocery.modal.User;
import com.grocery.repository.UserRepository;
import com.grocery.request.LoginRequest;
import com.grocery.request.ResetPasswordRequest;
import com.grocery.response.AuthResponse;
import com.grocery.service.AddressService;
import com.grocery.service.CustomUserDetails;
import com.grocery.service.PasswordResetTokenService;
import com.grocery.service.UserService;
import jakarta.validation.Valid;

@RestController
public class UserAuthController {

    private UserRepository userRepository;
    private PasswordEncoder passwordEncoder;
    private JwtProvider jwtTokenProvider;
    private CustomUserDetails customUserDetails;
    private PasswordResetTokenService passwordResetTokenService;
    private UserService userService;
    private AddressService addressService;

    public UserAuthController(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtProvider jwtTokenProvider,
                         CustomUserDetails customUserDetails, PasswordResetTokenService passwordResetTokenService,
                         UserService userService, AddressService addressService) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.customUserDetails = customUserDetails;
        this.passwordResetTokenService = passwordResetTokenService;
        this.userService = userService;
        this.addressService=addressService;
    }
    
    @GetMapping("/users/{id}")
    public ResponseEntity<User> findById(@PathVariable Long id) {
        try {
            User user = userService.findUserById(id);
            return ResponseEntity.ok(user);
        } catch (UserException e) {
            return ResponseEntity.status(404).body(null);
        }
    }
    
    @GetMapping("users/email/{email}")
    public ResponseEntity<User> findUserByEmail(@PathVariable String email) {
        try {
            User user = userService.findUserByEmail(email);
            return ResponseEntity.ok(user);
        } catch (UserException e) {
            return ResponseEntity.badRequest().body(null);
        }
    }

    @PostMapping("/auth/signup")
    public ResponseEntity<AuthResponse> createUserHandler(@Valid @RequestBody User user) throws UserException {
    	String email = user.getEmail();
        String password = user.getPassword();
        String firstName=user.getFirstName();
        String lastName=user.getLastName();
        USER_ROLE role = user.getRole();
        User isEmailExist=userRepository.findByEmail(email);
        
        AuthResponse authResponse = new AuthResponse();

        if (isEmailExist!=null) {
        	authResponse.setMessage("Email Is Already Used With Another Account");
            return new ResponseEntity<>(authResponse, HttpStatus.CONFLICT);
        }

        // Creating new user
		User createdUser= new User();
		createdUser.setEmail(email);
		createdUser.setFirstName(firstName);
		createdUser.setLastName(lastName);
        createdUser.setPassword(passwordEncoder.encode(password));
        createdUser.setRole(role);
        User savedUser= userRepository.save(createdUser);

        List<GrantedAuthority> authorities=new ArrayList<>();

		authorities.add(new SimpleGrantedAuthority(role.toString()));


		Authentication authentication = new UsernamePasswordAuthenticationToken(email, password,authorities);
		SecurityContextHolder.getContext().setAuthentication(authentication);

		String token = jwtTokenProvider.generateToken(authentication);

		authResponse.setJwt(token);
		authResponse.setMessage("Register Success");
		authResponse.setRole(savedUser.getRole());

		return new ResponseEntity<>(authResponse, HttpStatus.OK);
    }
    
    @PostMapping("/auth/signin")
    public ResponseEntity<AuthResponse> signin(@RequestBody LoginRequest loginRequest) {
        String username = loginRequest.getEmail();
        String password = loginRequest.getPassword();
        try {
            Authentication authentication = authenticate(username, password);
            SecurityContextHolder.getContext().setAuthentication(authentication);

            String token = jwtTokenProvider.generateToken(authentication);
            AuthResponse authResponse = new AuthResponse();
            authResponse.setMessage("Login Success");
            authResponse.setJwt(token);
            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();

            String roleName = authorities.isEmpty() ? null : authorities.iterator().next().getAuthority();
            authResponse.setRole(USER_ROLE.valueOf(roleName));

            return new ResponseEntity<>(authResponse, HttpStatus.OK);
        } catch (BadCredentialsException e) {
            AuthResponse authResponse = new AuthResponse();
            authResponse.setMessage(e.getMessage());
            return new ResponseEntity<>(authResponse, HttpStatus.UNAUTHORIZED);
        }
    }

    @PostMapping("/auth/reset-password")
    public ResponseEntity<String> resetPassword(@RequestBody ResetPasswordRequest req) throws UserException {
    	 PasswordResetToken resetToken = passwordResetTokenService.findByToken(req.getToken());
    	 
	        if (resetToken == null ) {
	        	throw new UserException("token is required...");
	        }
	        if(resetToken.isExpired()) {
	        	passwordResetTokenService.delete(resetToken);
	        	throw new UserException("token get expired...");
	        }

	        // Updating user's password
	        User user = resetToken.getUser();
	        userService.updatePassword(user, req.getPassword());

	        // Deleting the token
	        passwordResetTokenService.delete(resetToken);
	        String res=new String();
	        res= "Password Reset";

	        return ResponseEntity.ok(res);
    }

    @PostMapping("/auth/reset-password-request")
    public ResponseEntity<String> resetPassword(@RequestParam("email") String email) throws UserException {
    	User user = userService.findUserByEmail(email);
        System.out.println("ResetPasswordController.resetPassword()");

        if (user == null) {
        	throw new UserException("user not found");
        }

        userService.sendPasswordResetEmail(user);

        String res=new String();
        res = "Reset Password Request";

        return ResponseEntity.ok(res);
    }

    @GetMapping("/api/users/profile")
    public ResponseEntity<User> getUserProfileHandler(@RequestHeader("Authorization") String jwt) throws UserException {
		User user = userService.findUserProfileByJwt(jwt);
		return new ResponseEntity<User>(user,HttpStatus.ACCEPTED);
    }

    @PutMapping("/api/users/updateprofile")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<User> updateUserProfileHandler(@RequestBody User user) throws UserException {
    	User updatedUser = userService.updateUserProfile(user);
        return new ResponseEntity<>(updatedUser, HttpStatus.OK);
    }

    @PostMapping("/api/users/{userId}/addresses")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<Address> addAddressToUser(@PathVariable Long userId, @RequestBody Address address) {
    	Address savedAddress = addressService.saveAddress(userId, address);
        return new ResponseEntity<>(savedAddress, HttpStatus.CREATED);
    }

    @GetMapping("/api/users/{userId}/addresses")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<List<Address>> getUserAddresses(@PathVariable Long userId) {
    	List<Address> addresses = addressService.getAddressesByUserId(userId);
        return new ResponseEntity<>(addresses, HttpStatus.OK);
    }

    @PutMapping("/api/users/{userId}/addresses/{addressId}/default")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<List<Address>> setDefaultAddress(@PathVariable Long userId, @PathVariable Long addressId) {
    	List<Address> updatedAddresses = addressService.setDefaultAddress(userId, addressId);
        return new ResponseEntity<>(updatedAddresses, HttpStatus.OK);
    }

    @PutMapping("/api/users/{userId}/addresses/{addressId}")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<Address> updateAddress(@PathVariable Long userId, @PathVariable Long addressId, @RequestBody Address address) {
    	Address updatedAddress = addressService.updateAddress(userId, addressId, address);
        return new ResponseEntity<>(updatedAddress, HttpStatus.OK);
    }

    @DeleteMapping("/api/users/{userId}/addresses/{addressId}")
    @PreAuthorize("hasRole('ROLE_CUSTOMER')")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long userId, @PathVariable Long addressId) {
    	addressService.deleteAddress(userId, addressId);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    
    @GetMapping("/api/users/{userId}/default")
    public ResponseEntity<Address> getDefaultAddress(@PathVariable Long userId) {
        Address defaultAddress = addressService.findDefaultAddressByUserId(userId);
        if (defaultAddress != null) {
            return ResponseEntity.ok(defaultAddress);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    private Authentication authenticate(String username, String password) {
        UserDetails userDetails = customUserDetails.loadUserByUsername(username);
        if (userDetails == null) {
            throw new BadCredentialsException("Invalid username or password");
        }
        if (!passwordEncoder.matches(password, userDetails.getPassword())) {
            throw new BadCredentialsException("Invalid username or password");
        }
        return new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
    }
}
