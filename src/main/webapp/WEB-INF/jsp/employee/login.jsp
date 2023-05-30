<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Log in</title>
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

<!-- import daisyUI and Tailwind CSS-->
<link href="https://cdn.jsdelivr.net/npm/daisyui@2.51.6/dist/full.css"
	rel="stylesheet" type="text/css" />
<script src="https://cdn.tailwindcss.com"></script>
<!-- import font awesome-->
<link rel="stylesheet"
	href="http://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.3.0/css/all.min.css">
<!-- import 自己的 CSS -->
<link rel="stylesheet"
	href="${contextRoot}/css/employee/employee.list.css">
</head>
<body class="bg-gradient-to-br from-pink-700 to-cyan-800">
	<div
		class="relative flex flex-col items-center justify-center h-screen overflow-hidden">
		<div
			class="w-full p-6 bg-white border-t-4 border-gray-600 rounded-md shadow-md border-top lg:max-w-sm shadow-2xl">
			<div>
			<h1 class="text-3xl font-semibold text-center text-gray-700">Tofu
				Cars</h1></div>
			<form action="${contextRoot}/authenticateTheUser" method="POST"
				class="space-y-4">

				<c:if test="${param.error != null}">
					<!-- 錯誤訊息 -->
					<div class="bg-red-100 rounded-lg h-10 mt-8 pl-4 pt-2 pb-2">
						<p class="text-red-600 inline-block align-middle">請輸入正確的帳號或密碼</p>
					</div>
				</c:if>
				
				<c:if test="${param.logout != null}">
					<!-- 登出訊息 -->
					<div class="bg-blue-100 rounded-lg h-10 mt-8 pl-4 pt-2 pb-2">
						<p class="text-blue-600 inline-block align-middle">您已登出帳戶</p>
					</div>
				</c:if>

				<!-- 帳號 -->
				<div>
					<label class="label"> <span class="text-base label-text">User
							Name</span>
					</label> <input type="text" placeholder="Enter User Name" name="username" id="username" 
						class="w-full input input-bordered" />
				</div>



				<!-- 密碼（未輸入） -->
				<div>
					<label class="label"> <span class="text-base label-text">Password</span>
					</label> <input type="password" placeholder="Enter Password"
						name="password" id="password" class="w-full input input-bordered" />
				</div>

				<!-- 密碼（輸入錯誤） -->
				<div class="hidden">
					<label class="label"> <span
						class="text-base label-text text-red-500">Password</span>
					</label> <input type="password" placeholder="Enter Password"
						name="password" 
						class="w-full input input-bordered border border-red-500" />
					<p class="text-red-500 text-xs italic mt-2">Sorry! You entered
						invalid username or password.</p>
				</div>

				<div class="text-right mr-2">
				<a href="#"
					class="text-xs text-gray-600 hover:underline hover:text-blue-600 hidden">Forget
					Password?</a>
				<a href="${contextRoot}"
					class="text-xs text-gray-600 hover:text-blue-600 mr-2">Home Page</a>
				<a href="#"
					class="text-xs text-gray-600 hover:text-blue-600 mr-2" id="manager">Manager Demo</a> 
				<a href="#"
					class="text-xs text-gray-600 hover:text-blue-600" id="employee">Employee Demo</a>
				</div>
				<div>
					<button class="btn btn-block">Login</button>
				</div>
			</form>
		</div>
	</div>

<script>

	document
		.querySelector('#manager')
		.addEventListener('click', function() {
				document.querySelector('#username').value = 'Emma';
				document.querySelector('#password').value = '1234';
		});
		
	document
	.querySelector('#employee')
	.addEventListener('click', function() {
			document.querySelector('#username').value = 'Emma2';
			document.querySelector('#password').value = '1234';
	});	

</script>

</body>
</html>