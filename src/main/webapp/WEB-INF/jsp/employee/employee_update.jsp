<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Employees</title>
<c:set var="contextRoot" value="${pageContext.request.contextPath}" />

<!-- import axios -->
<script src="https://cdnjs.cloudflare.com/ajax/libs/axios/1.4.0/axios.min.js" integrity="sha512-uMtXmF28A2Ab/JJO2t/vYhlaa/3ahUOgj1Zf27M5rOo8/+fcTUVH0/E0ll68njmjrLqOBjXM3V9NiPFL5ywWPQ==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>

<!-- import React, ReactDOM and Babel -->
<!--  <script>var exports = {};</script>-->
<script src="https://cdn.jsdelivr.net/npm/react@17.0.2/umd/react.production.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/react-dom@17.0.2/umd/react-dom.production.min.js"></script>
<script src="https://cdnjs.cloudflare.com/ajax/libs/react-router-dom/5.2.0/react-router-dom.min.js"></script>
<script src="https://unpkg.com/@babel/standalone/babel.min.js"></script>
<!--  <script type="module" src="${contextRoot}/js/index.js"></script> -->

<!-- import daisyUI and Tailwind CSS-->
<link href="https://cdn.jsdelivr.net/npm/daisyui@2.51.6/dist/full.css" rel="stylesheet" type="text/css" />
<script src="https://cdn.tailwindcss.com"></script>
<!-- import font awesome-->
<link rel="stylesheet"
	href="http://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.3.0/css/all.min.css">
<!-- import 自己的 CSS -->
<link rel="stylesheet" href="${contextRoot}/css/employee/employee.list.css">
</head>
<body>
<div id="root"></div>
<script type="text/babel" src="${contextRoot}/js/employee/components/sidebar.js"></script>
<!--  <script type="text/babel" src="${contextRoot}/js/employee/employee.edit.js"></script>-->
<script type="text/babel" src="${contextRoot}/js/employee/Editemployee.js"></script>
</body>
</html>