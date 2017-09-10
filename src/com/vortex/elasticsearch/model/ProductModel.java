package com.vortex.elasticsearch.model;

public class ProductModel {
	
	private String productName;
	private String productCategory;
	private String productSubCategory;
	private String productType;
	private String productDescription;
	private String productGender;
	private Double productPrice;
	private boolean productStatus;
	private int productInStock;
	private String productManufacturer;
	
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public String getProductCategory() {
		return productCategory;
	}
	public void setProductCategory(String productCategory) {
		this.productCategory = productCategory;
	}
	public String getProductSubCategory() {
		return productSubCategory;
	}
	public void setProductSubCategory(String productSubCategory) {
		this.productSubCategory = productSubCategory;
	}
	public String getProductType() {
		return productType;
	}
	public void setProductType(String productType) {
		this.productType = productType;
	}
	public String getProductDescription() {
		return productDescription;
	}
	public void setProductDescription(String productDescription) {
		this.productDescription = productDescription;
	}
	public String getProductGender() {
		return productGender;
	}
	public void setProductGender(String productGender) {
		this.productGender = productGender;
	}
	public Double getProductPrice() {
		return productPrice;
	}
	public void setProductPrice(Double productPrice) {
		this.productPrice = productPrice;
	}
	public boolean isProductStatus() {
		return productStatus;
	}
	public void setProductStatus(boolean productStatus) {
		this.productStatus = productStatus;
	}
	public int getProductInStock() {
		return productInStock;
	}
	public void setProductInStock(int productInStock) {
		this.productInStock = productInStock;
	}
	public String getProductManufacturer() {
		return productManufacturer;
	}
	public void setProductManufacturer(String productManufacturer) {
		this.productManufacturer = productManufacturer;
	}
	@Override
	public String toString() {
		return "ProductModel [productName=" + productName + ", productCategory=" + productCategory
				+ ", productSubCategory=" + productSubCategory + ", productType=" + productType
				+ ", productDescription=" + productDescription + ", productGender=" + productGender + ", productPrice="
				+ productPrice + ", productStatus=" + productStatus + ", productInStock=" + productInStock
				+ ", productManufacturer=" + productManufacturer + "]";
	}
	
	
}
