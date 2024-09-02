package com.grocery.service;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
 
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.grocery.config.JwtProvider;
import com.grocery.exception.UserException;
import com.grocery.modal.PasswordResetToken;
import com.grocery.modal.User;
import com.grocery.repository.PasswordResetTokenRepository;
import com.grocery.repository.UserRepository;

@Service
public class UserService {
	private UserRepository userRepository;
	private JwtProvider jwtProvider;
	private PasswordEncoder passwordEncoder;
	private PasswordResetTokenRepository passwordResetTokenRepository;
	private JavaMailSender javaMailSender;
	public UserService(
			UserRepository userRepository,
			JwtProvider jwtProvider,
			PasswordEncoder passwordEncoder,
			PasswordResetTokenRepository passwordResetTokenRepository,
			JavaMailSender javaMailSender) {
		this.userRepository=userRepository;
		this.jwtProvider=jwtProvider;
		this.passwordEncoder=passwordEncoder;
		this.passwordResetTokenRepository=passwordResetTokenRepository;
		this.javaMailSender=javaMailSender;
	}
	public User findUserProfileByJwt(String jwt) throws UserException {
		String email = jwtProvider.getEmailFromJwtToken(jwt);
		User user = userRepository.findByEmail(email);
		if(user == null) {
			throw new UserException("user doesn't exist with email " + email);
		}
		return user;
	}
	public User findUserById(Long userId) throws UserException {
		Optional<User> user = userRepository.findById(userId);
		if(user.isPresent()){
			return user.get();
		}
		throw new UserException("user not found with id " + userId);
	}
	public List<User> findAllUsers() {
		return userRepository.findAll();
	}
	public User findUserByEmail(String username) throws UserException {
		User user = userRepository.findByEmail(username);
		if(user != null) {
			return user;
		}
		throw new UserException("User does not exist with username " + username);
	}
	public User updateUserProfile(User updatedUser) throws UserException {
        User user = userRepository.findById(updatedUser.getId())
                .orElseThrow(() -> new UserException("User not found"));
        user.setFirstName(updatedUser.getFirstName());
        user.setLastName(updatedUser.getLastName());
        user.setMobile(updatedUser.getMobile());
        return userRepository.save(user);
    }
    public void updatePassword(User user, String newPassword) {
        user.setPassword(passwordEncoder.encode(newPassword));
        userRepository.save(user);
    }
	public void sendPasswordResetEmail(User user) {
        String resetToken = generateRandomToken();
        Date expiryDate = calculateExpiryDate();
        // Checking if token already exists for this user
        PasswordResetToken passwordResetToken = passwordResetTokenRepository.findByUser(user);
        if(passwordResetToken != null) {
        	passwordResetToken.setToken(resetToken);
        	passwordResetToken.setExpiryDate(expiryDate);
        } else {
        	passwordResetToken = new PasswordResetToken(resetToken, user, expiryDate);
        }
        passwordResetTokenRepository.save(passwordResetToken);
        sendEmail(user.getEmail(), "Password Reset", "Click the following link to reset your password: http://localhost:3000/account/reset-password?token=" + resetToken);
	}
	private String generateRandomToken() {
	    return UUID.randomUUID().toString();
	}
	private Date calculateExpiryDate() {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        cal.add(Calendar.MINUTE, 10); // 10 minutes from now
        return cal.getTime();
    }
	private void sendEmail(String to, String subject, String message) {
	    SimpleMailMessage mailMessage = new SimpleMailMessage();
	    mailMessage.setTo(to);
	    mailMessage.setSubject(subject);
	    mailMessage.setText(message);
	    javaMailSender.send(mailMessage);
	}
}