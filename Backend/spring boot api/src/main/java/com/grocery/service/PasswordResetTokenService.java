package com.grocery.service;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
import com.grocery.modal.PasswordResetToken;
import com.grocery.repository.PasswordResetTokenRepository;
 
import jakarta.transaction.Transactional;
 
@Service
public class PasswordResetTokenService {
	@Autowired
	private PasswordResetTokenRepository passwordResetTokenRepository;
 
	public PasswordResetToken findByToken(String token) {
		PasswordResetToken passwordResetToken =passwordResetTokenRepository.findByToken(token);
		return passwordResetToken;
	}
 
	public void delete(PasswordResetToken resetToken) {
		passwordResetTokenRepository.delete(resetToken);
	}
 
}

