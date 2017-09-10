<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
    <c:set var="SITE_URL" value="${pageContext.request.contextPath}" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Hibernate Search !!</title>
<script src="${SITE_URL}/resources/js/jquery-ui.js"></script>
<script src="${SITE_URL}/resources/js/jquery.js"></script>

<link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/jquery-ui.css">
</head>
<script>
$(function(){
	$("#searchText").autocomplete({
		source: "/search",
		minLength: 1,
		select:function(event,ui){
			$("#searchText").value = ui.item.value;
			document.searchForm.submit();
		}
	});
});
</script>     
<body>

	<h1>Search for Products</h1>
    <form action="doSearch" method="post">
      Search: <input type="text" name="searchText" id="searchText"/><br/>
      <input type="reset"/>
      <input type="submit"/>
    </form>
</body>
</html>

