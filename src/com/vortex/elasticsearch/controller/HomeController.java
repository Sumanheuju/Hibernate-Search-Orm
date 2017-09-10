package com.vortex.elasticsearch.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.vortex.elasticsearch.dao.ProductDAO;
import com.vortex.elasticsearch.model.Product;
import com.vortex.elasticsearch.model.ProductModel;
import com.vortex.elasticsearch.model.ProductRepository;

@Controller
public class HomeController {
	
	@Autowired
	private ProductDAO productDAO;
	
	@Autowired
	private ProductRepository productRepo;
	
	@RequestMapping("/")
	@ResponseBody
	public List<Product> home(){
		return productDAO.getAll();
	}
	
	@RequestMapping(value = "/addProduct", method = RequestMethod.GET)
	public ModelAndView addProductPage() {
		ModelAndView mav = new ModelAndView("addProduct", "command", new ProductModel());
		return mav;
	}
	
	@RequestMapping(value = "/addProductToDB", method = RequestMethod.POST)
	public ModelAndView addBookToDB(@ModelAttribute("ProductModel") ProductModel productInfo) throws Exception {
		
		productRepo.addProductToDB(productInfo.getProductName(),
								productInfo.getProductCategory(),
								productInfo.getProductSubCategory(),
								productInfo.getProductType(),
								productInfo.getProductDescription(),
								productInfo.getProductGender(),
								productInfo.getProductPrice(),
								productInfo.isProductStatus(),
								productInfo.getProductInStock(),
								productInfo.getProductManufacturer());

		ModelAndView mav = new ModelAndView("done");
		mav.addObject("message", "Add Product to DB successfully");
		return mav;
	}
	
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ModelAndView searchPage() {
		ModelAndView mav = new ModelAndView("search");
		return mav;
	}
	
	@RequestMapping(value = "/doSearch", method = RequestMethod.POST)
	public ModelAndView search(@RequestParam("searchText") String searchText) throws Exception {
		List<Product> allFound = productRepo.searchForProduct(searchText);
		List<ProductModel> productModels = new ArrayList<ProductModel>();
		
		for (Product p : allFound) 
		{
			ProductModel pm = new ProductModel();
			
			pm.setProductName(p.getProductName());
			System.out.println(">>>>>>>>>>ProductModel>>>>>>>>>>>>>>>>"+pm.getProductName());
			pm.setProductCategory(p.getProductCategory());
			System.out.println(">>>>>>>>>>ProductModel>>>>>>>>>>>>>>>>"+pm.getProductCategory());
			pm.setProductSubCategory(p.getProductSubCategory());
			pm.setProductType(p.getProductType());
			pm.setProductDescription(p.getProductDescription());
			pm.setProductGender(p.getProductGender());
			pm.setProductPrice(p.getProductPrice());
			System.out.println(">>>>>>>>>>ProductModel>>>>>>>>>>>>>>>>"+pm.getProductPrice());

			pm.setProductStatus(p.isProductStatus());
			pm.setProductInStock(p.getProductInStock());
			pm.setProductManufacturer(p.getProductManufacturer());
			System.out.println(">>>>>>>>>>ProductModel>>>>>>>>>>>>>>>>Im Here !!!!!"+pm.getProductManufacturer());

			productModels.add(pm);
			
			System.err.println(">>>>>>>>>>>>>>>>>>List>>>>"+ productModels.toString());
		}

		ModelAndView mav = new ModelAndView("foundProducts");
		mav.addObject("foundProducts", productModels);
		return mav;
	}
	
}
