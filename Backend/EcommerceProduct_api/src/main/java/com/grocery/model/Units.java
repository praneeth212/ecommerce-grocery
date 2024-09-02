package com.grocery.model;

import java.util.List;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;


@Entity
@Table(name = "unit")
public class Units {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "unit_id")
    private Long id;

    @Column(name = "unit_name", unique = true)
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

	public Units() {
		super();

	}

	public Units(Long id, String unitName, List<ProductItem> productItems) {
		super();
		this.id = id;
		this.unitName = unitName;
		this.productItems = productItems;
	}
    
    
}
