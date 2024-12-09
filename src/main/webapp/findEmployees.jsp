<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>

<section id="find-employee-form">
    <h2>Find Employee</h2>
    
    <form action="${pageContext.request.contextPath}" method="get">
        <div class="form-group">
            <label for="searchId">Employee ID:</label>
            <input type="text" id="searchId" name="searchId" 
                   pattern="[Aa]0[0-9]{7}" 
                   title="ID must start with 'A0' followed by 7 digits" required>
        </div>
        
        <button class="btn-form" type="submit">Find Employee</button>
    </form>
    
    <c:if test="${not empty searchResultCode}">
        <div class="message ${searchResultCode == 0 ? 'success' : 'error'}">
            <c:choose>
                <c:when test="${searchResultCode == 0}">
                    Found <span class="txt-blue">${searchResult}</span><br/>
                    Result Code: ${searchResultCode} Description: ${searchResultDescription}
                </c:when>
                <c:otherwise>
                    Result Code: ${searchResultCode} Description: ${searchResultDescription}
                </c:otherwise>
            </c:choose>
        </div>
    </c:if>
    
</section>