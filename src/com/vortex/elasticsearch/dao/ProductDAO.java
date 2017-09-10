package com.vortex.elasticsearch.dao;

import java.util.List;

import com.vortex.elasticsearch.model.Product;

public interface ProductDAO {
	List<Product> getAll();
}
