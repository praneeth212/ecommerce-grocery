package com.grocery.service;
 
import java.util.Date;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
 
import com.grocery.repository.PasswordResetTokenRepository;
 
import jakarta.transaction.Transactional;
 
@Component
public class TokenCleaupTask {
	
	
	private PasswordResetTokenRepository tokenRepository;
	
	public TokenCleaupTask(PasswordResetTokenRepository tokenRepository) {
		super();
		this.tokenRepository = tokenRepository;
	}

	// @Scheduled(fixedRate = 600000) // This will run for every 10 minutes
	@Scheduled(fixedRate = 900000) // 900000 milliseconds = 15 * 60 * 1000 minutes
	@Transactional
	public void cleanUpExpiredTokens() {
		tokenRepository.deleteAllByExpiryDateBefore(new Date());
	}
 
}