<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<body>
    <h2>Found Products</h2>
 
    <c:forEach var="product" items="${foundProducts}">
        <ul>
          <li>${product.getProductName()}</li>
          <li>${product.getProductCategory()}</li>
          <li>${product.getProductDescription()}</li>
          <li>${product.getProductManufacturer()}</li>
        </ul>
        <hr>
    </c:forEach>
 
</body>
</html>