package com.vortex.elasticsearch.model;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

import org.apache.lucene.analysis.core.KeywordTokenizerFactory;
import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.core.StopFilterFactory;
import org.apache.lucene.analysis.miscellaneous.WordDelimiterFilterFactory;
import org.apache.lucene.analysis.ngram.EdgeNGramFilterFactory;
import org.apache.lucene.analysis.ngram.NGramFilterFactory;
import org.apache.lucene.analysis.pattern.PatternReplaceFilterFactory;
import org.apache.lucene.analysis.standard.StandardTokenizerFactory;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.search.annotations.Analyze;
import org.hibernate.search.annotations.Analyzer;
import org.hibernate.search.annotations.AnalyzerDef;
import org.hibernate.search.annotations.AnalyzerDefs;
import org.hibernate.search.annotations.Field;
import org.hibernate.search.annotations.Fields;
import org.hibernate.search.annotations.Index;
import org.hibernate.search.annotations.Indexed;
import org.hibernate.search.annotations.Parameter;
import org.hibernate.search.annotations.Store;
import org.hibernate.search.annotations.TokenFilterDef;
import org.hibernate.search.annotations.TokenizerDef;
import org.hibernate.validator.constraints.NotEmpty;


@Entity
@Indexed
@Table(name="products")
@AnalyzerDefs({

@AnalyzerDef(name = "autocompleteEdgeAnalyzer",

// Split input into tokens according to tokenizer
tokenizer = @TokenizerDef(factory = KeywordTokenizerFactory.class),

filters = {
 // Normalize token text to lowercase, as the user is unlikely to
 // care about casing when searching for matches
 @TokenFilterDef(factory = PatternReplaceFilterFactory.class, params = {
   @Parameter(name = "pattern",value = "([^a-zA-Z0-9\\.])"),
   @Parameter(name = "replacement", value = " "),
   @Parameter(name = "replace", value = "all") }),
 @TokenFilterDef(factory = LowerCaseFilterFactory.class),
 @TokenFilterDef(factory = StopFilterFactory.class),
 // Index partial words starting at the front, so we can provide
 // Autocomplete functionality
 @TokenFilterDef(factory = EdgeNGramFilterFactory.class, params = {
   @Parameter(name = "minGramSize", value = "3"),
   @Parameter(name = "maxGramSize", value = "50") }) }),

@AnalyzerDef(name = "autocompleteNGramAnalyzer",

// Split input into tokens according to tokenizer
tokenizer = @TokenizerDef(factory = StandardTokenizerFactory.class),

filters = {
 // Normalize token text to lowercase, as the user is unlikely to
 // care about casing when searching for matches
 @TokenFilterDef(factory = WordDelimiterFilterFactory.class),
 @TokenFilterDef(factory = LowerCaseFilterFactory.class),
 @TokenFilterDef(factory = NGramFilterFactory.class, params = {
   @Parameter(name = "minGramSize", value = "3"),
   @Parameter(name = "maxGramSize", value = "5") }),
 @TokenFilterDef(factory = PatternReplaceFilterFactory.class, params = {
   @Parameter(name = "pattern",value = "([^a-zA-Z0-9\\.])"),
   @Parameter(name = "replacement", value = " "),
   @Parameter(name = "replace", value = "all") })
}),

@AnalyzerDef(name = "standardAnalyzer",

// Split input into tokens according to tokenizer
tokenizer = @TokenizerDef(factory = StandardTokenizerFactory.class),

filters = {
 // Normalize token text to lowercase, as the user is unlikely to
 // care about casing when searching for matches
 @TokenFilterDef(factory = WordDelimiterFilterFactory.class),
 @TokenFilterDef(factory = LowerCaseFilterFactory.class),
 @TokenFilterDef(factory = PatternReplaceFilterFactory.class, params = {
   @Parameter(name = "pattern", value = "([^a-zA-Z0-9\\.])"),
   @Parameter(name = "replacement", value = " "),
   @Parameter(name = "replace", value = "all") })
}) // Def
})
public class Product implements Serializable{
	private static final long serialVersionUID = 1L;
	
	@Id
	@Column(name="product_id",unique=true)
	@NotNull(message = "The product id must not be empty")
	private String productId;
	
	@Column(name="product_name")
	@NotEmpty(message = "The product name must not be empty")
	@Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
	@Fields({
		  @Field(name = "productName", index = Index.YES, store = Store.YES,
		analyze = Analyze.YES, analyzer = @Analyzer(definition = "standardAnalyzer")),
		  @Field(name = "edgeNGramTitle", index = Index.YES, store = Store.NO,
		analyze = Analyze.YES, analyzer = @Analyzer(definition = "autocompleteEdgeAnalyzer")),
		  @Field(name = "nGramTitle", index = Index.YES, store = Store.NO,
		analyze = Analyze.YES, analyzer = @Analyzer(definition = "autocompleteNGramAnalyzer"))
		})
	private String productName;
	
	@Column(name="product_category")
	@Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
	private String productCategory;
	
	@Column(name="product_sub_category")
	@Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
	private String productSubCategory;
	
	@Column(name="product_type")
	@Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
	private String productType;
	
	@Column(name="product_description")
	@Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
	private String productDescription;
	
	@Column(name="product_gender")
	@NotEmpty(message = "Gender must be specified")
	@Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
	private String productGender;
	
	@Column(name="product_price")
	@Min(value=1, message = "The product price must not be zero or less")
	@NotNull(message = "Please specify a price")
	@Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
	private Double productPrice;
	
	@Column(name="product_status")
	@Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
	private boolean productStatus;
	
	@Column(name="product_in_stock")
	@Min(value=0, message = "The product stock must not be less than zero")
	@Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
	private int productInStock;
	
	@Column(name="product_manufacturer")
	@Field(index=Index.YES, analyze=Analyze.YES, store=Store.NO)
	private String productManufacturer;
	
	
	@CreationTimestamp
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "added_date", nullable = false, updatable=false)
    private Date addedDate;
	
	@UpdateTimestamp
	@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "modified_date")
    private Date modifiedDate;


	public Product(){}

	public String getProductId() {
		return productId;
	}

	public void setProductId(String id) {
		productId = id;
	}

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
	
	
}
