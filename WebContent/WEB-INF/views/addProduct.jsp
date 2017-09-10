<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    
<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>FullText Search - Hibernate Search</title>
</head>
<body>
	<h1>Add Record to Book Table</h1>
    <form:form action="addProductToDB" method="post">
    
      Product Name: <form:input path="productName"/><br/>
      Product Category: <form:input path="productCategory" /><br/>
      Product Sub-Category: <form:input path="productSubCategory" /><br/>
      Product Type: <form:input path="productType" /><br/>
      Product Description:<br/>
      <form:textarea path="productDescription" rows="20"/><br/>
      Product Gender: <form:input path="productGender" /><br/>
      Product Price: <form:input path="productPrice" /><br/>
      Product Status: <form:input path="productStatus" /><br/>
      Product In-Stock: <form:input path="productInStock" /><br/>
      Product Manufacturer: <form:input path="productManufacturer" /><br/>
      
      <input type="reset"/>
      <input type="submit"/>
      
    </form:form>
</body>
</html>
