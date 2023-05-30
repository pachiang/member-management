<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Employees</title>

<!-- 設定變數 -->
<!-- contextRoot也會找到webapp -->
<!-- contextRoot可以找到servletContext -->
<!-- 首頁 -->
<!-- ${contextRoot}/ -->
<c:set var="contextRoot" value="${pageContext.request.contextPath}" />
<!-- 載入bootstrap的css -->
<link href="${contextRoot}/css/bootstrap.min.css" rel="stylesheet">
<!-- 載入bootstrap和jQuety的js -->
<script type="text/javascript"
	src="${contextRoot}/js/bootstrap.bundle.min.js"></script>
<script type="text/javascript"
	src="${contextRoot}/js/jquery-3.6.4.min.js"></script>
<script type="text/javascript"
	src="${contextRoot}/js/bootbox.all.min.js"></script>
<script type="text/javascript"
	src="${contextRoot}/js/axios-1.1.2.min.js"></script>
<link rel="stylesheet"
	href="http://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.3.0/css/all.min.css">
<style>
.bd-placeholder-img {
	font-size: 1.125rem;
	text-anchor: middle;
	-webkit-user-select: none;
	-moz-user-select: none;
	user-select: none;
}

@media ( min-width : 768px) {
	.bd-placeholder-img-lg {
		font-size: 3.5rem;
	}
}

h2 {
	margin-top: 40px;
	display: inline-block;
}

h4 {
	margin-top: 25px;
	margin-right: 40px;
	display: inline-block;
	color: gray;
}

#create {
	margin-top: 40px;
	margin-bottom: 40px;
	display: inline-block;
}

#logout {
	margin-top: 20px;
	margin-bottom: 10px;
	display: inline-block;
}

.userUtilBar {
	display: flex;
	justify-content: space-between;
	margin: 2% 10% 2% 10%;
}

.nav {
	display: flex;
	justify-content: right;
	margin: 0 10% 0 10%;
}

.table-responsive {
	margin: 2% 10% 2% 10%;
}

.userListHeading {
	font-weight: bolder;
}

.empPhoto {
	height: 60px;
}

td {
	text-align: center;
	vertical-align: middle;
}

th {
	text-align: center;
}
</style>
</head>
<body>

	<main class="px-md-4">
		<div class="nav">

			<h4>Hello, ${loginedUserName}!</h4>

			<a href="logout.controller"><button id="logout"
					class="btn btn-outline-secondary">Log out</button></a>

		</div>
		<div class="userUtilBar">

			<h2>Employees</h2>

			<a href="http://localhost:8080/SpringHw/create"><button
					id="create" class="btn btn-outline-secondary">Add new employee</button></a>

		</div>
		<div class="table-responsive">
			<table class="table table-striped table-sm table-hover">
				<thead>
					<tr>
						<th scope="col">ID</th>
						<th scope="col">Photo</th>
						<th scope="col">Name</th>
						<th scope="col">Age</th>
						<th scope="col">Department</th>
						<th scope="col">Position</th>
						<th scope="col">Hire Date</th>
						<th scope="col">Email</th>
						<th scope="col">Edit</th>
						<th scope="col">Delete</th>
					</tr>
				</thead>
				<tbody>
					<c:forEach items="${employeeList}" var="emp">
						<tr>
							<td>${emp.eid}</td>
							<td><img class="empPhoto"
								src="${contextRoot}/employee/getEmployeePhoto/${emp.eid}"></td>
							<td>${emp.firstName} ${emp.lastName}</td>
							<td>${emp.age}</td>
							<td>${emp.department}</td>
							<td>${emp.position}</td>
							<td>${emp.hireDate}</td>
							<td>${emp.email}</td>
							<td><a
								href="http://localhost:8080/tofu/update?id=${emp.eid}"><button
										class="btn btn-outline-primary" id="e${emp.eid}">
										<i class="fas fa-user-edit"></i> Edit
									</button></a></td>
							<td><a
								href="http://localhost:8080/tofu/delete?id=${user.id}"><button
										class="deleteButton btn btn-outline-danger" id="d${emp.eid}">
										<i class="fas fa-trash-alt"></i> Delete
									</button></a></td>
						</tr>
					</c:forEach>
				</tbody>
			</table>
		</div>
	</main>




</body>
</html>