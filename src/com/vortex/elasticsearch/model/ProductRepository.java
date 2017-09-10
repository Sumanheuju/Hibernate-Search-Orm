package com.vortex.elasticsearch.model;

import java.util.List;
import java.util.UUID;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.search.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.search.FullTextQuery;
import org.hibernate.search.FullTextSession;
import org.hibernate.search.Search;
import org.hibernate.search.query.dsl.QueryBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

@Repository
@SuppressWarnings("unchecked")
public class ProductRepository {
	private static final String TITLE_EDGE_NGRAM_INDEX = "edgeNGramTitle";
	private static final String TITLE_NGRAM_INDEX = "nGramTitle";
	
	@Autowired
	private SessionFactory sessionFactory;
	
	@Transactional
	public void indexProducts(){
		try {
		Session session = sessionFactory.getCurrentSession();
		FullTextSession fullTextSession = Search.getFullTextSession(session);
		fullTextSession.createIndexer().startAndWait();
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
	
	@Transactional
	public void addProductToDB(String productName, String productCategory, String productSubCategory, String productType, String productDescription, String productGender, double productPrice, boolean productStatus, int productInStock, String productManufacturer){
		Session session = sessionFactory.getCurrentSession();
		Product product = new Product();
		UUID x = UUID.randomUUID();
		
		product.setProductId(x.toString());
	      product.setProductName(productName);
	      product.setProductCategory(productCategory);
	      product.setProductSubCategory(productSubCategory);
	      product.setProductType(productType);
	      product.setProductDescription(productDescription);
	      product.setProductGender(productGender);
	      product.setProductPrice(productPrice);
	      product.setProductStatus(productStatus);
	      product.setProductInStock(productInStock);
	      product.setProductManufacturer(productManufacturer);
	      
	      session.saveOrUpdate(product);
	}
	
	@Transactional
	public List<Product> searchForProduct(String searchText) throws Exception{
		
		
		try{
			
		Session session = sessionFactory.getCurrentSession();
		FullTextSession fullTextSession = Search.getFullTextSession(session);
		
		System.out.println(">>>>>>>>>>>>>>>>>>>>SearchText>>>>>>>>>>>>>>>>>>>>>"+searchText);
		System.out.println(">>>>>>>>>>>>>>>>>>>>Session>>>>>>>>>>>>>>>>>>>>>"+session);
		
		QueryBuilder queryBuilder = fullTextSession.getSearchFactory()
				.buildQueryBuilder().forEntity(Product.class)
				.overridesForField("productName", "autocompleteNGramAnalyzer")
				.get();
		//Analyzer analyzer = fullTextSession.getSearchFactory().getAnalyzer("customanalyzer");
		Query query = queryBuilder.keyword()
				.onFields("productName",
						"productCategory",
						"productManufacturer",
						"productDescription")
				.matching(searchText).createQuery();
		
		org.hibernate.Query hibernateQuery = fullTextSession.createFullTextQuery(query, Product.class);
		
		System.out.println(">>>>>>>>>>>>>>>>>>>>QueryBuilder>>>>>>>>>>>>>>>>>>>>>"+queryBuilder.toString());
		System.out.println(">>>>>>>>>>>>>>>>>>>>Query>>>>>>>>>>>>>>>>>>>>>"+query);
		System.out.println(">>>>>>>>>>>>>>>>>>>>HiberQuery>>>>>>>>>>>>>>>>>>>>>"+hibernateQuery);
		
		List<Product> results = hibernateQuery.list();
		System.out.println(">>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>>"+results);
		getSuggestion(searchText);
		return results;
		}
		catch(Exception e){
			throw e;
		}
	}
	
	@Transactional(readOnly=true)
	public synchronized List getSuggestion(final String searchText){
		Session session = sessionFactory.getCurrentSession();
		FullTextSession fullTextSession = Search.getFullTextSession(session);
		
		QueryBuilder titleQB = fullTextSession.getSearchFactory()
		   .buildQueryBuilder().forEntity(Product.class).get();

		 Query query = titleQB.phrase().withSlop(2).onField(TITLE_NGRAM_INDEX)
		   .andField(TITLE_EDGE_NGRAM_INDEX).boostedTo(5)
		   .sentence(searchText.toLowerCase()).createQuery();
		 System.out.println(">>>>>>>>>>>>>>Edge>>>>>>"+ TITLE_EDGE_NGRAM_INDEX);
		 System.out.println(">>>>>>>>>>>>>>Edge>>>>>>"+ TITLE_NGRAM_INDEX);
		 FullTextQuery fullTextQuery = fullTextSession.createFullTextQuery(
		    query, Product.class);
		 fullTextQuery.setMaxResults(20);

		 @SuppressWarnings("unchecked")
		 List<Product> results = fullTextQuery.list();
		 System.out.println(">>>>>>>>>>>>>>Edge>>>>>>"+ results);
		 return results;
	}
	
	
}
