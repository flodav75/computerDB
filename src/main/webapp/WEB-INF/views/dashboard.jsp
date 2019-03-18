<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<title>Computer Database</title>
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<meta charset="utf-8">
<!-- Bootstrap -->
<link href="<c:url value="/ressources/static/css/bootstrap.min.css"/>" rel="stylesheet" media="screen">
<link href="<c:url value="/ressources/static/css/font-awesome.css"/>" rel="stylesheet" media="screen">
<link href="<c:url value="/ressources/static/css/main.css"/>" rel="stylesheet" media="screen">

</head>
<body>
    <header class="navbar navbar-inverse navbar-fixed-top">
        <div class="container">
            <a class="navbar-brand" href="index"> Application - Computer Database </a>
        </div>
    </header>

    <section id="main">
        <div class="container">
            <h1 id="homeTitle">
	            <c:choose>
		         <c:when test = "${nbComputer == null || nbComputer==0 }">
		                        <p> No computer found </p>
		         </c:when>
		         <c:otherwise>
		             <p><c:out value="${nbComputer}"></c:out> Computers found</p> 
		         </c:otherwise>
	      		</c:choose>
            </h1>
            <div id="actions" class="form-horizontal">
                <div class="pull-left">
                    <form id="searchForm" action="index" method="GET" class="form-inline">
            			<input type="hidden" name="limit" value="10">
						<input type="hidden" name="currentPage" value="1">
                        <input type="search" id="searchbox" name="search" class="form-control" value="${searchName }" placeholder="Search name" />
                        <input type="submit" id="searchsubmit" value="Filter by name"
                        class="btn btn-primary" />
                    </form>
                </div>
                <div class="pull-right">
                    <a class="btn btn-success" id="addComputer" href="add">Add Computer</a> 
                    <a class="btn btn-default" id="editComputer" href="#" onclick="$.fn.toggleEditMode();">Edit</a>
                </div>
            </div>
        </div>

        <form id="deleteForm" action="DeleteComputer" method="POST">
            <input type="hidden" name="selection" value="">
        </form>

        <div class="container" style="margin-top: 10px;">
            <table class="table table-striped table-bordered">
                <thead>
                    <tr>
                        <!-- Variable declarations for passing labels as parameters -->
                        <!-- Table header for Computer Name -->

                        <th class="editMode" style="width: 60px; height: 22px;">
                            <input type="checkbox" id="selectall" /> 
                            <span style="vertical-align: top;">
                                 -  <a href="#" id="deleteSelected" onclick="$.fn.deleteSelected();">
                                        <i class="fa fa-trash-o fa-lg"></i>
                                    </a>
                            </span>
                        </th>
                        <th>
                            Computer name <a href="index?groupBy=asc&limit=${limit}&pageNumber=${pageNumber}&search=${searchName}" id="deleteSelected" >
                                        <i class="fas fa-sort-up"></i>
                                    </a>
                                    <a href="index?groupBy=desc&limit=${limit}&pageNumber=${pageNumber}&search=${searchName}" id="deleteSelected" >
                                        <i class="fas fa-sort-down"></i>
                                    </a>
                        </th>
                        <th>
                            Introduced date
                        </th>
                        <!-- Table header for Discontinued Date -->
                        <th>
                            Discontinued date
                        </th>
                        <!-- Table header for Company -->
                        <th>
                            Company
                        </th>

                    </tr>
                </thead>
                <!-- Browse attribute computers -->
                <tbody id="results">
                 <c:if test="${computers != null }"> 
 					<c:forEach var="computer" items="${computers}" varStatus="loop">
		                <tr>
	                        <td class="editMode">
	                            <input type="checkbox" name="cb" class="cb" value="${computer.getId()}">
	                        </td>
	                        <td>
	                            <a href="edit?idComputer=${computer.getId()}">${computer.getComputerName()}</a>
	                        </td>
	                        <td>${ computer.getIntroduced()}</td>
	                        <td>${ computer.getDiscontinued()}</td>
	                        <td>${computer.getCompanyName()}</td>
		                </tr>             
	                </c:forEach>               
                 </c:if> 
                </tbody>
            </table>
        </div>
    </section>

    <footer class="navbar-fixed-bottom">
        <div class="container text-center">
            <ul class="pagination">
                <li>
                   <a href="index?limit=${limit}&pageNumber=1" aria-label="Previous">
                      <span aria-hidden="true">&laquo;</span>
                  </a>
              </li>
               <c:choose>
		         <c:when test = "${ pageNumber<3 }">
		         	<c:choose>	
			          <c:when test = "${ max<6 }">
						   <c:forEach var="page" begin ="1" end="${max }" step="1"> 
			         		<li><a href="index?limit=${limit}&pageNumber=${page }&search=${searchName}&limit=${pageSizeValue}&pageNumber=${pageNumber}">${page}</a></li>
			         </c:forEach>
	 				   </c:when> 
				       <c:otherwise> 
					        <li><a href="index?limit=${limit}&pageNumber=1&search=${searchName}&groupBy=${groupBy}">1</a></li>
			              	<li><a href="index?limit=${limit}&pageNumber=2&search=${searchName}&groupBy=${groupBy}">2</a></li>
			              	<li><a href="index?limit=${limit}&pageNumber=3&search=${searchName}&groupBy=${groupBy}">3</a></li>
			              	<li><a href="index?limit=${limit}&pageNumber=4&search=${searchName}&groupBy=${groupBy}">4</a></li>				
			              	<li><a href="index?limit=${limit}&pageNumber=5&search=${searchName}&groupBy=${groupBy}">5</a></li>      
	 			        </c:otherwise>
		              </c:choose>
			      </c:when>
			      <c:when test = "${ pageNumber>max-3 }">
					  <li><a href="index?limit=${limit}&pageNumber=${max-4}&search=${searchName}&groupBy=${groupBy}">${max-4}</a></li>
		              <li><a href="index?limit=${limit}&pageNumber=${max-3}&search=${searchName}&groupBy=${groupBy}">${max-3}</a></li>
		              <li><a href="index?limit=${limit}&pageNumber=${max-2}&search=${searchName}&groupBy=${groupBy}">${max-2}</a></li>
		              <li><a href="index?limit=${limit}&pageNumber=${max-1}&search=${searchName}&groupBy=${groupBy}">${max-1}</a></li>
		              <li><a href="index?limit=${limit}&pageNumber=${max}&search=${searchName}&groupBy=${groupBy} ">${max}</a></li>
 				   </c:when> 
			       <c:otherwise> 
				        <li><a href="index?limit=${limit}&pageNumber=${pageNumber-2}&search=${searchName}&groupBy=${groupBy}">${pageNumber-2}</a></li>
		              	<li><a href="index?limit=${limit}&pageNumber=${pageNumber-1}&search=${searchName}&groupBy=${groupBy}">${pageNumber-1}</a></li>
		              	<li><a href="index?limit=${limit}&pageNumber=${pageNumber}&search=${searchName}&groupBy=${groupBy}">${pageNumber}</a></li>
		              	<li><a href="index?limit=${limit}&pageNumber=${pageNumber+1}&search=${searchName}&groupBy=${groupBy}">${pageNumber+1}</a></li>				
		              	<li><a href="index?limit=${limit}&pageNumber=${pageNumber+2}&search=${searchName}&groupBy=${groupBy}">${pageNumber+2}</a></li>      
 			        </c:otherwise> 

	      		</c:choose>
	              <li>
	                <a href="index?limit=${limit}&pageNumber=${max}" aria-label="Next">
	                    <span aria-hidden="true">&raquo;</span>
	                </a>
	            </li>
        	</ul>
	        <div class="btn-group btn-group-sm pull-right" role="group" >
	           <c:set var="pageSizeValues">10,20,50,100</c:set>
        		<c:forTokens items="${pageSizeValues}" var="pageSizeValue" delims=",">
	           		 <a role="button" type="button" class="btn btn-default" href="http://localhost:8080/ComputerDatabase/index?limit=${pageSizeValue}&pageNumber=${pageNumber}&search=${searchName}&groupBy=${groupBy}">
	            	${pageSizeValue}
	            	</a>
			</c:forTokens>
		
	        </div>
        </div>
    </footer>
<script src="<c:url value="/ressources/static/js/jquery.min.js"/>"></script>
<script src="<c:url value="/ressources/static/js/bootstrap.min.js"/>"></script>
<script src="<c:url value="/ressources/static/js/dashboard.js"/>"></script>

</body>
</html>
