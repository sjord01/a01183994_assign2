<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" isErrorPage="true" %>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>

<!DOCTYPE html>
<html>
<head>
    <title>COMP3601 ASSIGNMENT 2</title>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <link rel="stylesheet" href="${pageContext.request.contextPath}/resources/css/styles.css">
</head>

<body>
	<header class="primary-header flex">
            <h1>Application Error</h1>
     </header>
     <main>
		<section>
		    <div>
		        <h2>"All that is gold does not glitter, not all those who wander are lost."</h2>
		        <p>We encountered an unexpected issue while processing your request. Please try again later.</p>
		        
		        <div class="error-contents">
                    <h3>Error Details:</h3>
                    <p><span class="txt-red">Status Code:</span> ${pageContext.errorData.statusCode}</p>
                    <p><span class="txt-red">Request URI:</span> ${pageContext.errorData.requestURI}</p>
                    <p><span class="txt-red">Servlet Name:</span> ${pageContext.errorData.servletName}</p>

                    <!-- Handle case where exception data may not be available for a 404 error -->
                    <c:choose>
                        <c:when test="${not empty pageContext.exception}">
                            <p><span class="txt-red">Exception Type:</span> ${pageContext.exception['class'].name}</p>
                            <p><span class="txt-red">Exception Message:</span> ${pageContext.exception.message}</p>
                        </c:when>
                        <c:otherwise>
                            <p><span class="txt-red">No exception available for this error (404 or similar).</span></p>
                        </c:otherwise>
                    </c:choose>
                </div>
		        
		        <div class="btn-error">
		            <a href="${pageContext.request.contextPath}" id="e-return-home">Return to Home Page</a>
		        </div>
		    </div>
		</section>
	</main>
  </body>
</html>