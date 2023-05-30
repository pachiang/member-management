<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Access Denied</title>
<c:set var="contextRoot" value="${pageContext.request.contextPath}" />
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/axios/1.4.0/axios.min.js"
	integrity="sha512-uMtXmF28A2Ab/JJO2t/vYhlaa/3ahUOgj1Zf27M5rOo8/+fcTUVH0/E0ll68njmjrLqOBjXM3V9NiPFL5ywWPQ=="
	crossorigin="anonymous" referrerpolicy="no-referrer"></script>
<script
	src="https://cdn.jsdelivr.net/npm/react@17.0.2/umd/react.production.min.js"></script>
<script
	src="https://cdn.jsdelivr.net/npm/react-dom@17.0.2/umd/react-dom.production.min.js"></script>
<script
	src="https://cdnjs.cloudflare.com/ajax/libs/react-router-dom/5.2.0/react-router-dom.min.js"></script>
<script src="https://unpkg.com/@babel/standalone/babel.min.js"></script>
<link href="https://cdn.jsdelivr.net/npm/daisyui@2.51.6/dist/full.css"
	rel="stylesheet" type="text/css" />
<script src="https://cdn.tailwindcss.com"></script>
<link rel="stylesheet"
	href="http://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.3.0/css/all.min.css">
<link rel="stylesheet"
	href="${contextRoot}/css/employee/employee.list.css">
</head>
<body>

	<div
		class="flex h-screen justify-center items-center bg-indigo-900 relative">
		<div class="text-center">
		<div><svg viewBox="0 0 64 64" id="Layer_1" version="1.1" xml:space="preserve" xmlns="http://www.w3.org/2000/svg" xmlns:xlink="http://www.w3.org/1999/xlink" fill="#000000"><g id="SVGRepo_bgCarrier" stroke-width="0"></g><g id="SVGRepo_tracerCarrier" stroke-linecap="round" stroke-linejoin="round"></g><g id="SVGRepo_iconCarrier"> <style type="text/css"> .st0{fill:#B4E6DD;} .st1{fill:#80D4C4;} .st2{fill:#D2F0EA;} .st3{fill:#FFFFFF;} .st4{fill:#FBD872;} .st5{fill:#DB7767;} .st6{fill:#F38E7A;} .st7{fill:#F6AF62;} .st8{fill:#32A48E;} .st9{fill:#A38FD8;} .st10{fill:#7C64BD;} .st11{fill:#EAA157;} .st12{fill:#9681CF;} .st13{fill:#F9C46A;} .st14{fill:#CE6B61;} </style> <g> <g> <path class="st2" d="M46.44,36.38H17.56V22.44C17.56,14.48,24.04,8,32,8s14.44,6.48,14.44,14.44V36.38z M23.32,28.7h17.37v-6.26 c0-4.79-3.9-8.68-8.68-8.68c-4.79,0-8.68,3.9-8.68,8.68V28.7z"></path> </g> <path class="st4" d="M32,56L32,56c-9.8,0-17.74-7.94-17.74-17.74V27.74h35.48v10.52C49.74,48.06,41.8,56,32,56z"></path> <path class="st7" d="M36,37.73c0-2.21-1.79-4-4-4c-2.21,0-4,1.79-4,4c0,1.39,0.71,2.62,1.79,3.33v4.74c0,1.22,0.99,2.21,2.21,2.21 c1.22,0,2.21-0.99,2.21-2.21v-4.74C35.29,40.35,36,39.12,36,37.73z"></path> </g> </g></svg></div>
			<h2 class="text-3xl font-bold text-orange-50" >Access Denied</h2>
			<h6 class="text-lg font-medium text-orange-50 mt-2" >您沒有訪問此頁面的權限</h6>
		</div>
		<div class="absolute bottom-20 right-20" >
			<a href="${contextRoot}/tofu/employee/"><h6 class="text-lg font-medium text-orange-50 mr-4 inline-block" >Back to Home Page</h6><div class="inline-block align-bottom"><svg xmlns="http://www.w3.org/2000/svg" width="28" height="28" viewBox="0 0 24 24" fill="none" stroke="white" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M9 18l6-6-6-6"/></svg></div></a>
		</div>
	</div>

</body>
</html>