package com.grocery.modal;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "unit")
public class Units {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "unit_id")
    private Long id;

    @Column(name = "unit_name")
    private String unitName;

    @OneToMany(mappedBy = "unit", cascade = CascadeType.DETACH)
    @JsonBackReference
    private List<ProductItem> productItems;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUnitName() {
		return unitName;
	}

	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}

	public List<ProductItem> getProductItems() {
		return productItems;
	}

	public void setProductItems(List<ProductItem> productItems) {
		this.productItems = productItems;
	}

	@Override
	public String toString() {
		return "Units [id=" + id + ", unitName=" + unitName + ", productItems=" + productItems + "]";
	}

	public Units(Long id, String unitName, List<ProductItem> productItems) {
		super();
		this.id = id;
		this.unitName = unitName;
		this.productItems = productItems;
	}

	public Units() {
		super();
		// TODO Auto-generated constructor stub
	}
}
