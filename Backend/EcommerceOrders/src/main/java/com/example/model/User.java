package com.example.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
 
@Entity
@Table(name = "users")
public class User {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	@Column(name = "email")
    private String email;
 
    @Column(name = "first_name")
    private String fName;
    @Column(name = "last_name")
    private String lName;
    @Column(name = "mobile_no")
    private String mobile;
 
    @Column(name = "password")
    private String password;
 
//  @Enumerated(EnumType.STRING)
    private String role;
    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Addresses> addresses=new ArrayList<>();
    
    
 
	public User() {
		super();
		// TODO Auto-generated constructor stub
	}
 
	public User(Long id, String email, String fName, String lName, String mobile, String password, String role) {
		super();
		this.id = id;
		this.email = email;
		this.fName = fName;
		this.lName = lName;
		this.mobile = mobile;
		this.password = password;
		this.role = role;
	}
 
	@Override
	public String toString() {
		return "User [id=" + id + ", email=" + email + ", fName=" + fName + ", lName=" + lName + ", mobile=" + mobile
				+ ", password=" + password + ", role=" + role + "]";
	}
 
	public Long getId() {
		return id;
	}
 
	public void setId(Long id) {
		this.id = id;
	}
 
	public String getEmail() {
		return email;
	}
 
	public void setEmail(String email) {
		this.email = email;
	}
 
	public String getFName() {
		return fName;
	}
 
	public void setFName(String fName) {
		this.fName = fName;
	}
 
	public String getLName() {
		return lName;
	}
 
	public void setLName(String lName) {
		this.lName = lName;
	}
 
	public String getMobile() {
		return mobile;
	}
 
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
 
	public String getPassword() {
		return password;
	}
 
	public void setPassword(String password) {
		this.password = password;
	}
 
	public String getRole() {
		return role;
	}
 
	public void setRole(String role) {
		this.role = role;
	}
 
}