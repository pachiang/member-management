<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Employee System Mainpage</title>
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

<!-- chart.js -->
<!-- <script src="https://cdnjs.cloudflare.com/ajax/libs/Chart.js/4.3.0/chart.min.js" integrity="sha512-mlz/Fs1VtBou2TrUkGzX4VoGvybkD9nkeXWJm3rle0DPHssYYx4j+8kIS15T78ttGfmOjH0lLaBXGcShaVkdkg==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>-->
<!-- <script src="https://cdnjs.cloudflare.com/ajax/libs/react-chartjs/1.2.0/react-chartjs.min.js" integrity="sha512-2kfxqw44S6yapKbmdgSkjIkgOParE7RJfve5OxnbvQC49boT/LXhVvj9h4ILYyKHATf5yPwmv8yQkkXIaeDeLg==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>-->

<!--<script src="https://cdn.bootcdn.net/ajax/libs/Chart.js/4.2.1/chart.umd.min.js"></script>-->
<script src="https://cdn.jsdelivr.net/npm/chart.js"></script>
<script src="https://cdn.jsdelivr.net/npm/react-chartjs-2/dist/react-chartjs-2.umd.min.js"></script>
<script src="https://code.highcharts.com/highcharts.js"></script>
<!-- <script src="https://cdn.jsdelivr.net/npm/react-chartjs-2/dist/react-chartjs-2.min.js"></script>-->
<!--<script src="https://cdn.staticfile.org/echarts/4.3.0/echarts.min.js"></script>-->
<!--<script src="https://cdnjs.cloudflare.com/ajax/libs/victory/36.6.10/victory.min.js" integrity="sha512-0hArjHyhqDYMVr40sV2HkQXwEAPRGc4dsNTvFp5ePDztY3Cas+tIyBwn4ILlTIsMssKMsOQ7jic5qk2wXmnKNg==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>-->
<!-- <script src="https://cdnjs.cloudflare.com/ajax/libs/canvasjs/1.7.0/canvasjs.min.js" integrity="sha512-FJ2OYvUIXUqCcPf1stu+oTBlhn54W0UisZB/TNrZaVMHHhYvLBV9jMbvJYtvDe5x/WVaoXZ6KB+Uqe5hT2vlyA==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>-->
<!-- <script src="https://cdn.jsdelivr.net/npm/canvas@2.11.2/browser.min.js"></script>-->
<!-- <script src="https://cdn.jsdelivr.net/npm/jsdom@22.0.0/lib/jsdom/living/generated/utils.min.js"></script>-->
<!-- <script src="https://cdn.jsdelivr.net/npm/jsdom-canvas-2@11.6.3-alpha.0/lib/api.min.js"></script>-->
<!-- <script src="https://cdn.jsdelivr.net/npm/@rinminase/ng-charts@next"></script>-->
<!-- <script src="https://cdnjs.cloudflare.com/ajax/libs/react-chartjs-2/5.2.0/index.js" integrity="sha512-/05tDKRzZzFEETPnD58aoGLVA7tC/2Ua4RiCzyUOhmSVSFqdZ+5atWtzEN/hSeNUlUrQIgYw8d8LYyk9+fuhaw==" crossorigin="anonymous" referrerpolicy="no-referrer"></script>-->

<!-- import font awesome-->
<link rel="stylesheet"
	href="http://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.3.0/css/all.min.css">
<!-- import 自己的 CSS -->
<link rel="stylesheet" href="${contextRoot}/css/employee/employee.list.css">
</head>
<body id="journal-scroll">
<div id="root"></div>
<script type="text/babel" src="${contextRoot}/js/employee/components/sidebar.js"></script>
<!-- <script type="text/babel" src="${contextRoot}/js/employee/editEmployee_1.js"></script> -->
<script type="text/babel" src="${contextRoot}/js/employee/employee.main.js"></script>
<script type="text/babel">ReactDOM.render(<MainPage />, document.getElementById('root'));</script>
</body>
</html>