package com.grocery.modal;
 
import java.util.Date;
 
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
 
@Entity
public class PasswordResetToken {
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Id
	private Integer id;
	private @NonNull String token;
	@ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER)
	@JoinColumn(name = "user_id", nullable = false)
	private @NonNull User user;
	private @NonNull  Date expiryDate;
	
 
	public PasswordResetToken(@NonNull String token, @NonNull User user, @NonNull Date expiryDate) {
		super();
		this.token = token;
		this.user = user;
		this.expiryDate = expiryDate;
	}

	public PasswordResetToken() {
		super();
		// TODO Auto-generated constructor stub
	}

	@Override
	public String toString() {
		return "PasswordResetToken [id=" + id + ", token=" + token + ", user=" + user + ", expiryDate=" + expiryDate
				+ "]";
	}
 
	public Integer getId() {
		return id;
	}
 
	public void setId(Integer id) {
		this.id = id;
	}
 
	public String getToken() {
		return token;
	}
 
	public void setToken(String token) {
		this.token = token;
	}
 
	public User getUser() {
		return user;
	}
 
	public void setUser(User user) {
		this.user = user;
	}
 
	public Date getExpiryDate() {
		return expiryDate;
	}
 
	public void setExpiryDate(Date expiryDate) {
		this.expiryDate = expiryDate;
	}
 
	public boolean isExpired() {
        return expiryDate.before(new Date());
    }
 
}