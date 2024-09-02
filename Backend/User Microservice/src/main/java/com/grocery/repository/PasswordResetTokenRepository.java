package com.grocery.repository;
 
import java.util.Date;
 
import org.springframework.data.jpa.repository.JpaRepository;
 
import com.grocery.modal.PasswordResetToken;
import com.grocery.modal.User;
 
 
public interface PasswordResetTokenRepository extends JpaRepository<PasswordResetToken, Integer> {
	
	PasswordResetToken findByToken(String token);
	
	PasswordResetToken findByUser(User user);
	
	void deleteAllByExpiryDateBefore(Date now);
}