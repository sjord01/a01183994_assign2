<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri='http://java.sun.com/jsp/jstl/core' prefix='c' %>

<section id="add-employee-form">
    <h2>Add New Employee</h2>
    
    <form action="${pageContext.request.contextPath}" method="post">
        <div class="form-group">
            <label for="employeeId">Employee ID:</label>
            <input type="text" id="employeeId" name="id" required>
        </div>
        
        <div class="form-group">
            <label for="firstName">First Name:</label>
            <input type="text" id="firstName" name="firstName" maxlength="30" required>
        </div>
        
        <div class="form-group">
            <label for="lastName">Last Name:</label>
            <input type="text" id="lastName" name="lastName" maxlength="30" required>
        </div>
        
        <div class="form-group">
            <label for="dob">Date of Birth:</label>
            <input type="date" id="dob" name="dob" required>
        </div>
        
        <button class="btn-form" type="submit">Add Employee</button>
    </form>
    
     <c:if test="${not empty resultCode}">
        <div class="message ${resultCode == 200 ? 'success' : 'error'}">
            Code: ${resultCode} - ${resultDescription}
        </div>
    </c:if>
    
    <!-- Display age validation error -->
    <c:if test="${not empty sessionScope.ageError}">
        <div class="message error">
            ${sessionScope.ageError}
            <c:remove var="ageError" scope="session"/>
        </div>
    </c:if>
    
    <!-- Display First Name error -->
    <c:if test="${not empty sessionScope.firstNameError}">
        <div class="message error">${sessionScope.firstNameError}</div>
        <c:remove var="firstNameError" scope="session"/>
    </c:if>
    
    <!-- Display Last Name error -->
    <c:if test="${not empty sessionScope.lastNameError}">
        <div class="message error">${sessionScope.lastNameError}</div>
        <c:remove var="lastNameError" scope="session"/>
    </c:if>
</section>