package com.grocery.dto;


public interface ProductItemDetails {
    String getCategoryName();
    Long getProductId();
    String getProdName();
    String getProductImg();
    String getBrand();
    String getDescription();
    Long getProductItemId();
    Double getPrice();
    Double getSalePrice();
    Double getDiscountPercentage();
    Integer getQuantityInStock();
    Long getUnitId();
    String getUnitName();
}
