package com.grocery.service;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
 
import com.grocery.modal.PasswordResetToken;
import com.grocery.repository.PasswordResetTokenRepository;
 
@Service
public class PasswordResetTokenService {

	private PasswordResetTokenRepository passwordResetTokenRepository;
 
	public PasswordResetTokenService(PasswordResetTokenRepository passwordResetTokenRepository) {
		super();
		this.passwordResetTokenRepository = passwordResetTokenRepository;
	}

	public PasswordResetToken findByToken(String token) {
		PasswordResetToken passwordResetToken =passwordResetTokenRepository.findByToken(token);
		return passwordResetToken;
	}
 
	public void delete(PasswordResetToken resetToken) {
		passwordResetTokenRepository.delete(resetToken);
	}
 
}

