package com.example.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "addresses")
public class Addresses {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id;

	@Column(name="address_line1")
	private String address_line1;
	
	@Column(name="address_line2")
	private String address_line2;
	
    @Column(name = "country")
    private String country;

    @Column(name = "city")
    private String city;

    @Column(name = "state")
    private String state;

    @Column(name = "zipcode")
    private String zipcode;
    
    @Column(name = "default_address")
    private Boolean default_address;
    
    @ManyToOne
    @JoinColumn(name="user_id")
    @JsonIgnore
    private User user;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAddress_line1() {
		return address_line1;
	}

	public void setAddress_line1(String address_line1) {
		this.address_line1 = address_line1;
	}

	public String getAddress_line2() {
		return address_line2;
	}

	public void setAddress_line2(String address_line2) {
		this.address_line2 = address_line2;
	}

	public String getCountry() {
		return country;
	}

	public void setCountry(String country) {
		this.country = country;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZipcode() {
		return zipcode;
	}

	public void setZipcode(String zipcode) {
		this.zipcode = zipcode;
	}

	public Boolean getDefault_address() {
		return default_address;
	}

	public void setDefault_address(Boolean default_address) {
		this.default_address = default_address;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

	@Override
	public String toString() {
		return "Address [id=" + id + ", address_line1=" + address_line1 + ", address_line2=" + address_line2
				+ ", country=" + country + ", city=" + city + ", state=" + state + ", zipcode=" + zipcode
				+ ", default_address=" + default_address + ", user=" + user + "]";
	}

	public Addresses(Long id, String address_line1, String address_line2, String country, String city, String state,
			String zipcode, Boolean default_address, User user) {
		super();
		this.id = id;
		this.address_line1 = address_line1;
		this.address_line2 = address_line2;
		this.country = country;
		this.city = city;
		this.state = state;
		this.zipcode = zipcode;
		this.default_address = default_address;
		this.user = user;
	}

	public Addresses() {
		super();
		// TODO Auto-generated constructor stub
	}
}
    
	