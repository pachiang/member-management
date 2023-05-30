function Sidebar() {

	const USER_API_BASE_URL = "http://localhost:8080/tofu/info/employee/";
	const EMP_API_BASE_URL = "http://localhost:8080/tofu/employee/";
	const [account, setAccount] = React.useState([]);
	const [id, setId] = React.useState(null);
	const [role, setRole] = React.useState([]);
	const [waitforassign, setWaitforassign] = React.useState([]);
	const [waittodo, setWaittodo] = React.useState([]);

	// 取得使用者的Account名稱資料
	React.useEffect(() => {
		const getLoggedinInfo = async () => {
			const { data: infoAccount } = await axios.get(USER_API_BASE_URL + "user"); //用了await，所在的函式就要用async
			setAccount(infoAccount.name);
		};
		getLoggedinInfo();
	}, []);

	// 取得使用者的ID資料
	React.useEffect(() => {
		const getLoggedinInfo = async () => {
			const { data: infoID } = await axios.get(USER_API_BASE_URL + "userId"); //用了await，所在的函式就要用async
			setId(infoID.id);
		};
		getLoggedinInfo();
	}, []);

	// 取得使用者的role資料
	React.useEffect(() => {
		const getLoggedinInfo = async () => {
			const { data: infoRole } = await axios.get(USER_API_BASE_URL + "role"); //用了await，所在的函式就要用async
			setRole(infoRole[0]);
		};
		getLoggedinInfo();
	}, []);
	
	// 取得待主管分派的任務數量
	React.useEffect(() => {
		const getTasksNumber = async () => {
			const { data: countWaitingAssign } = await axios.get(EMP_API_BASE_URL + "task/waiting_assign");
			//console.log(countWaitingAssign.waitingTotal);
			setWaitforassign(countWaitingAssign);	
		};
		getTasksNumber();
	}, []);
	
	// 取得員工被分派到的任務數量
	React.useEffect(() => {
		const getTasksNumber = async () => {
			const { data: countWaitingTodo } = await axios.get(EMP_API_BASE_URL + "task/waiting_todo");
			//console.log(countWaitingAssign.waitingTotal);
			setWaittodo(countWaitingTodo);	
		};
		getTasksNumber();
	}, []);



	return (
		<div className="flex">
			<div className="min-h-screen flex flex-col flex-none w-64 flex-shrink-0 antialiased bg-opacity-0 bg-gray-50 text-gray-200">
				<div className="fixed flex flex-col top-0 left-0 w-64 bg-gray-700 h-full border-r">
					<div className="flex items-center justify-center h-20 border-b border-gray-700">
						<div><svg xmlns="http://www.w3.org/2000/svg" className="w-32 h-20" stroke="white" fill="white" viewBox="0 0 500 131">
							<g>
								<path d="M127.1,32H81c-2.4,0-4.2,0.5-5.4,1.6c-1.3,1.1-1.9,2.5-1.9,4.4c0,1.8,0.6,3.2,1.8,4.3c1.2,1.1,3,1.6,5.5,1.6h15.8v52
		c0,3,0.7,5.2,2,6.6c1.4,1.5,3.1,2.2,5.2,2.2c2.1,0,3.8-0.7,5.2-2.2c1.3-1.4,2-3.7,2-6.7V44h15.8c2.5,0,4.3-0.5,5.6-1.6
		c1.2-1.1,1.9-2.5,1.9-4.3c0-1.8-0.6-3.2-1.8-4.3C131.4,32.6,129.6,32,127.1,32z"/>
								<path d="M323.7,32h-34.2c-2.1,0-3.7,0.3-5.1,0.9c-1.3,0.6-2.3,1.6-2.9,2.9c-0.6,1.3-0.9,3-0.9,5.1V96c0,3,0.7,5.2,2,6.6
		c1.4,1.5,3.1,2.2,5.2,2.2c2.1,0,3.8-0.7,5.2-2.2c1.4-1.4,2-3.7,2-6.7V72.4H319c2.2,0,3.8-0.5,4.9-1.5c1.1-1,1.7-2.3,1.7-4
		s-0.5-3-1.6-4c-1.1-1-2.7-1.5-5-1.5h-23.9V43.2h28.6c2.3,0,4-0.5,5.2-1.5s1.7-2.4,1.7-4.1c0-1.7-0.6-3-1.7-4S326,32,323.7,32z"/>
								<path d="M429.6,32.9c-1.4-1.4-3.1-2.2-5.3-2.2c-2.2,0-3.9,0.7-5.2,2.2c-1.3,1.4-2,3.7-2,6.6v35.5c0,6.1-1.2,10.7-3.5,13.7
		c-2.3,3-6.3,4.5-12,4.5c-4.1,0-7.3-0.7-9.6-2.2c-2.3-1.5-3.9-3.6-4.8-6.2c-0.9-2.7-1.3-6-1.3-10V39.6c0-2.9-0.7-5.1-2-6.6
		s-3.1-2.2-5.3-2.2c-2.1,0-3.9,0.7-5.2,2.2s-2,3.7-2,6.6V74c0,5.4,0.6,10.1,1.8,13.9c1.2,3.9,3,7.1,5.5,9.6c2.5,2.5,5.7,4.3,9.7,5.5
		c4,1.2,8.7,1.8,14.2,1.8c4.6,0,8.7-0.6,12.1-1.7c3.5-1.1,6.5-2.9,9.1-5.3c3-2.8,5.1-6.1,6.2-9.9c1.1-3.7,1.6-8.4,1.6-14V39.6
		C431.7,36.6,431,34.4,429.6,32.9z"/>
								<path d="M201.7,31.7c-19.7,0-35.7,16-35.7,35.7s16,35.7,35.7,35.7c19.7,0,35.7-16,35.7-35.7S221.4,31.7,201.7,31.7z M219.8,77.7
		l-18.1,10.4l-18.1-10.4V56.9l18.1-10.4l18.1,10.4V77.7z"/>
							</g>
						</svg><div className="w-32 h-0.5 border-b border-gray-500"></div></div>
					</div>
					<div
						className="overflow-y-auto overflow-x-hidden flex-grow"
						id="journal-scroll"
					>
						<ul className="flex flex-col py-4 space-y-1">
							<li className="px-5">
								<div className="flex flex-row items-center h-8">
									<div className="text-sm font-light tracking-wide text-gray-300">
										Menu
									</div>
								</div>
							</li>
							<li className="hidden">
								<a
									href="#"
									className="relative flex flex-row items-center h-11 focus:outline-none hover:bg-gray-50 text-gray-200 hover:text-gray-800 border-l-4 border-transparent hover:border-indigo-500 pr-6"
								>
									<span className="inline-flex justify-center items-center ml-4">
										<svg
											className="w-5 h-5"
											fill="none"
											stroke="currentColor"
											viewBox="0 0 24 24"
											xmlns="http://www.w3.org/2000/svg"
										>
											<path
												strokeLinecap="round"
												strokeLinejoin="round"
												strokeWidth="2"
												d="M3 12l2-2m0 0l7-7 7 7M5 10v10a1 1 0 001 1h3m10-11l2 2m-2-2v10a1 1 0 01-1 1h-3m-6 0a1 1 0 001-1v-4a1 1 0 011-1h2a1 1 0 011 1v4a1 1 0 001 1m-6 0h6"
											></path>
										</svg>
									</span>{" "}
									<span className="ml-2 text-sm tracking-wide truncate">
										Dashboard
									</span>
								</a>
							</li>
							<li>
								<a
									href={EMP_API_BASE_URL + "check"}
									className="relative flex flex-row items-center h-11 focus:outline-none hover:bg-gray-50 text-gray-200 hover:text-gray-800 border-l-4 border-transparent hover:border-indigo-500 pr-6"
								>
									<span className="inline-flex justify-center items-center ml-4">
										<svg xmlns="http://www.w3.org/2000/svg" className="w-5 h-5" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"><circle cx="12" cy="12" r="10"></circle><polyline points="12 6 12 12 16 14"></polyline></svg>
									</span>{" "}
									<span className="ml-2 text-sm tracking-wide truncate">
										打卡
									</span>
								</a>
							</li>
							{role === "ROLE_MANAGER" ? (<li>
								<a
									href={EMP_API_BASE_URL + "allcheckin"}
									className="relative flex flex-row items-center h-11 focus:outline-none hover:bg-gray-50 text-gray-200 hover:text-gray-800 border-l-4 border-transparent hover:border-indigo-500 pr-6"
								>
									<span className="inline-flex justify-center items-center ml-4">
										<svg xmlns="http://www.w3.org/2000/svg" className="w-5 h-5" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"><polyline points="9 11 12 14 22 4"></polyline><path d="M21 12v7a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V5a2 2 0 0 1 2-2h11"></path></svg>
									</span>{" "}
									<span className="ml-2 text-sm tracking-wide truncate">
										差勤管理
									</span>
								</a>
							</li>):null}
														{role === "ROLE_MANAGER" ? (<li>
								<a
									href={EMP_API_BASE_URL + "all"}
									className="relative flex flex-row items-center h-11 focus:outline-none hover:bg-gray-50 text-gray-200 hover:text-gray-800 border-l-4 border-transparent hover:border-indigo-500 pr-6"
								>
									<span className="inline-flex justify-center items-center ml-4">
										<svg
											className="w-5 h-5"
											fill="none"
											stroke="currentColor"
											viewBox="0 0 24 24"
											xmlns="http://www.w3.org/2000/svg"
										>
											<path
												strokeLinecap="round"
												strokeLinejoin="round"
												strokeWidth="2"
												d="M16 7a4 4 0 11-8 0 4 4 0 018 0zM12 14a7 7 0 00-7 7h14a7 7 0 00-7-7z"
											></path>
										</svg>
									</span>{" "}
									<span className="ml-2 text-sm tracking-wide truncate">
										員工管理
									</span>
								</a>
							</li>) : null}
							
							<li className="hidden">
								<a
									href="#"
									className="relative flex flex-row items-center h-11 focus:outline-none hover:bg-gray-50 text-gray-200 hover:text-gray-800 border-l-4 border-transparent hover:border-indigo-500 pr-6"
								>
									<span className="inline-flex justify-center items-center ml-4">
										<svg
											className="w-5 h-5"
											fill="none"
											stroke="currentColor"
											viewBox="0 0 24 24"
											xmlns="http://www.w3.org/2000/svg"
										>
											<path
												strokeLinecap="round"
												strokeLinejoin="round"
												strokeWidth="2"
												d="M20 13V6a2 2 0 00-2-2H6a2 2 0 00-2 2v7m16 0v5a2 2 0 01-2 2H6a2 2 0 01-2-2v-5m16 0h-2.586a1 1 0 00-.707.293l-2.414 2.414a1 1 0 01-.707.293h-3.172a1 1 0 01-.707-.293l-2.414-2.414A1 1 0 006.586 13H4"
											></path>
										</svg>
									</span>{" "}
									<span className="ml-2 text-sm tracking-wide truncate">
										Inbox
									</span>{" "}
									<span className="px-2 py-0.5 ml-auto text-xs font-medium tracking-wide text-indigo-500 bg-indigo-50 rounded-full">
										New
									</span>
								</a>
							</li>
							<li className="hidden">
								<a
									href="#"
									className="relative flex flex-row items-center h-11 focus:outline-none hover:bg-gray-50 text-gray-200 hover:text-gray-800 border-l-4 border-transparent hover:border-indigo-500 pr-6"
								>
									<span className="inline-flex justify-center items-center ml-4">
										<svg
											className="w-5 h-5"
											fill="none"
											stroke="currentColor"
											viewBox="0 0 24 24"
											xmlns="http://www.w3.org/2000/svg"
										>
											<path
												strokeLinecap="round"
												strokeLinejoin="round"
												strokeWidth="2"
												d="M7 8h10M7 12h4m1 8l-4-4H5a2 2 0 01-2-2V6a2 2 0 012-2h14a2 2 0 012 2v8a2 2 0 01-2 2h-3l-4 4z"
											></path>
										</svg>
									</span>{" "}
									<span className="ml-2 text-sm tracking-wide truncate">
										Messages
									</span>
								</a>
							</li>
							<li className="hidden">
								<a
									href="#"
									className="relative flex flex-row items-center h-11 focus:outline-none hover:bg-gray-50 text-gray-200 hover:text-gray-800 border-l-4 border-transparent hover:border-indigo-500 pr-6"
								>
									<span className="inline-flex justify-center items-center ml-4">
										<svg
											className="w-5 h-5"
											fill="none"
											stroke="currentColor"
											viewBox="0 0 24 24"
											xmlns="http://www.w3.org/2000/svg"
										>
											<path
												strokeLinecap="round"
												strokeLinejoin="round"
												strokeWidth="2"
												d="M15 17h5l-1.405-1.405A2.032 2.032 0 0118 14.158V11a6.002 6.002 0 00-4-5.659V5a2 2 0 10-4 0v.341C7.67 6.165 6 8.388 6 11v3.159c0 .538-.214 1.055-.595 1.436L4 17h5m6 0v1a3 3 0 11-6 0v-1m6 0H9"
											></path>
										</svg>
									</span>{" "}
									<span className="ml-2 text-sm tracking-wide truncate">
										Notifications
									</span>
									<span className="px-2 py-0.5 ml-auto text-xs font-medium tracking-wide text-red-500 bg-red-50 rounded-full">
										1.2k
									</span>
								</a>
							</li>
							<li className="px-5">
								<div className="flex flex-row items-center h-8">
									<div className="text-sm font-light tracking-wide text-gray-300">
										Tasks
									</div>
								</div>
							</li>
							
							{role === "ROLE_MANAGER" ? (<li>
								<a
									href={EMP_API_BASE_URL + "task/management_book"}
									className="relative flex flex-row items-center h-11 focus:outline-none hover:bg-gray-50 text-gray-200 hover:text-gray-800 border-l-4 border-transparent hover:border-indigo-500 pr-6"
								>
									<span className="inline-flex justify-center items-center ml-4">
										<svg xmlns="http://www.w3.org/2000/svg" className="w-5 h-5" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"><rect x="3" y="4" width="18" height="18" rx="2" ry="2"></rect><line x1="16" y1="2" x2="16" y2="6"></line><line x1="8" y1="2" x2="8" y2="6"></line><line x1="3" y1="10" x2="21" y2="10"></line></svg>
									</span>{" "}
									<span className="ml-2 text-sm tracking-wide truncate">
										預約賞車管理
									</span>
									

										{waitforassign.waitingBooks === 0 ? null : <span className="px-2 py-0.5 ml-auto text-xs font-medium tracking-wide text-red-500 bg-red-50 rounded-full">{waitforassign.waitingBooks}</span>}
									
								</a>
							</li>):(<li>
								<a
									href={EMP_API_BASE_URL + "task/todo_book"}
									className="relative flex flex-row items-center h-11 focus:outline-none hover:bg-gray-50 text-gray-200 hover:text-gray-800 border-l-4 border-transparent hover:border-indigo-500 pr-6"
								>
									<span className="inline-flex justify-center items-center ml-4">
										<svg xmlns="http://www.w3.org/2000/svg" className="w-5 h-5" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"><rect x="3" y="4" width="18" height="18" rx="2" ry="2"></rect><line x1="16" y1="2" x2="16" y2="6"></line><line x1="8" y1="2" x2="8" y2="6"></line><line x1="3" y1="10" x2="21" y2="10"></line></svg>
									</span>{" "}
									<span className="ml-2 text-sm tracking-wide truncate">
										預約賞車任務
									</span>

										{waittodo.waitingBooks === 0 ? null : <span className="px-2 py-0.5 ml-auto text-xs font-medium tracking-wide text-red-500 bg-red-50 rounded-full">{waittodo.waitingBooks}</span>}
									
								</a>
							</li>)}
							
							{role === "ROLE_MANAGER" ? (<li>
								<a
									href={EMP_API_BASE_URL + "task/management_mtn"}
									className="relative flex flex-row items-center h-11 focus:outline-none hover:bg-gray-50 text-gray-200 hover:text-gray-800 border-l-4 border-transparent hover:border-indigo-500 pr-6"
								>
									<span className="inline-flex justify-center items-center ml-4">
										<svg
											className="w-5 h-5"
											fill="none"
											stroke="currentColor"
											viewBox="0 0 24 24"
											xmlns="http://www.w3.org/2000/svg"
										>
											<path
												strokeLinecap="round"
												strokeLinejoin="round"
												strokeWidth="2"
												d="M9 5H7a2 2 0 00-2 2v12a2 2 0 002 2h10a2 2 0 002-2V7a2 2 0 00-2-2h-2M9 5a2 2 0 002 2h2a2 2 0 002-2M9 5a2 2 0 012-2h2a2 2 0 012 2m-3 7h3m-3 4h3m-6-4h.01M9 16h.01"
											></path>
										</svg>
									</span>{" "}
									<span className="ml-2 text-sm tracking-wide truncate">
										維修保養管理
									</span>
									
										
										
										{waitforassign.waitingMtns === 0 ? null : <span className="px-2 py-0.5 ml-auto text-xs font-medium tracking-wide text-red-500 bg-red-50 rounded-full">{waitforassign.waitingMtns}</span>}
									
								</a>
							</li>):(<li>
								<a
									href={EMP_API_BASE_URL + "task/todo_mtn"}
									className="relative flex flex-row items-center h-11 focus:outline-none hover:bg-gray-50 text-gray-200 hover:text-gray-800 border-l-4 border-transparent hover:border-indigo-500 pr-6"
								>
									<span className="inline-flex justify-center items-center ml-4">
										<svg
											className="w-5 h-5"
											fill="none"
											stroke="currentColor"
											viewBox="0 0 24 24"
											xmlns="http://www.w3.org/2000/svg"
										>
											<path
												strokeLinecap="round"
												strokeLinejoin="round"
												strokeWidth="2"
												d="M9 5H7a2 2 0 00-2 2v12a2 2 0 002 2h10a2 2 0 002-2V7a2 2 0 00-2-2h-2M9 5a2 2 0 002 2h2a2 2 0 002-2M9 5a2 2 0 012-2h2a2 2 0 012 2m-3 7h3m-3 4h3m-6-4h.01M9 16h.01"
											></path>
										</svg>
									</span>{" "}
									<span className="ml-2 text-sm tracking-wide truncate">
										維修保養任務
									</span>
									
										{waittodo.waitingMtns === 0 ? null : <span className="px-2 py-0.5 ml-auto text-xs font-medium tracking-wide text-red-500 bg-red-50 rounded-full">{waittodo.waitingMtns}</span>}
									
								</a>
							</li>)}
							
							{role === "ROLE_MANAGER" ? (<li>
								<a
									href={EMP_API_BASE_URL + "leave/approval"}
									className="relative flex flex-row items-center h-11 focus:outline-none hover:bg-gray-50 text-gray-200 hover:text-gray-800 border-l-4 border-transparent hover:border-indigo-500 pr-6"
								>
									<span className="inline-flex justify-center items-center ml-4">
										<svg
											className="w-5 h-5"
											fill="none"
											stroke="currentColor"
											viewBox="0 0 24 24"
											xmlns="http://www.w3.org/2000/svg"
										>
											<path
												strokeLinecap="round"
												strokeLinejoin="round"
												strokeWidth="2"
												d="M20 13V6a2 2 0 00-2-2H6a2 2 0 00-2 2v7m16 0v5a2 2 0 01-2 2H6a2 2 0 01-2-2v-5m16 0h-2.586a1 1 0 00-.707.293l-2.414 2.414a1 1 0 01-.707.293h-3.172a1 1 0 01-.707-.293l-2.414-2.414A1 1 0 006.586 13H4"
											></path>
										</svg>
									</span>{" "}
									<span className="ml-2 text-sm tracking-wide truncate">
										假單核准
									</span>{" "}
									<span className="px-2 py-0.5 ml-auto text-xs font-medium tracking-wide text-indigo-500 bg-indigo-50 rounded-full">
										New
									</span>
								</a>
							</li>) : (<li>
								<a
									href={EMP_API_BASE_URL + "leave/application"}
									className="relative flex flex-row items-center h-11 focus:outline-none hover:bg-gray-50 text-gray-200 hover:text-gray-800 border-l-4 border-transparent hover:border-indigo-500 pr-6"
								>
									<span className="inline-flex justify-center items-center ml-4">
										<svg xmlns="http://www.w3.org/2000/svg" className="w-5 h-5" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"><path d="M3 15v4c0 1.1.9 2 2 2h14a2 2 0 0 0 2-2v-4M17 8l-5-5-5 5M12 4.2v10.3"/></svg>
									</span>{" "}
									<span className="ml-2 text-sm tracking-wide truncate">
										請假申請
									</span>{" "}
									<span className="px-2 py-0.5 ml-auto text-xs font-medium tracking-wide text-indigo-500 bg-indigo-50 rounded-full">
										New
									</span>
								</a>
							</li>)}
							
							<li className="hidden">
								<a
									href="#"
									className="relative flex flex-row items-center h-11 focus:outline-none hover:bg-gray-50 text-gray-200 hover:text-gray-800 border-l-4 border-transparent hover:border-indigo-500 pr-6"
								>
									<span className="inline-flex justify-center items-center ml-4">
										<svg
											className="w-5 h-5"
											fill="none"
											stroke="currentColor"
											viewBox="0 0 24 24"
											xmlns="http://www.w3.org/2000/svg"
										>
											<path
												strokeLinecap="round"
												strokeLinejoin="round"
												strokeWidth="2"
												d="M12 4.354a4 4 0 110 5.292M15 21H3v-1a6 6 0 0112 0v1zm0 0h6v-1a6 6 0 00-9-5.197M13 7a4 4 0 11-8 0 4 4 0 018 0z"
											></path>
										</svg>
									</span>{" "}
									<span className="ml-2 text-sm tracking-wide truncate">
										Clients
									</span>
									<span className="px-2 py-0.5 ml-auto text-xs font-medium tracking-wide text-green-500 bg-green-50 rounded-full">
										15
									</span>
								</a>
							</li>
							<li className="px-5">
								<div className="flex flex-row items-center h-8">
									<div className="text-sm font-light tracking-wide text-gray-300">
										Settings
									</div>
								</div>
							</li>




							<li>
								<a
									href="#"
									className="relative flex flex-row items-center h-11 focus:outline-none hover:bg-gray-50 text-gray-200 hover:text-gray-800 border-l-4 border-transparent hover:border-indigo-500 pr-6"
								>
									<span className="inline-flex justify-center items-center ml-4">
										<svg
											className="w-5 h-5"
											fill="none"
											stroke="currentColor"
											viewBox="0 0 24 24"
											xmlns="http://www.w3.org/2000/svg"
										>
											<path
												strokeLinecap="round"
												strokeLinejoin="round"
												strokeWidth="2"
												d="M10.325 4.317c.426-1.756 2.924-1.756 3.35 0a1.724 1.724 0 002.573 1.066c1.543-.94 3.31.826 2.37 2.37a1.724 1.724 0 001.065 2.572c1.756.426 1.756 2.924 0 3.35a1.724 1.724 0 00-1.066 2.573c.94 1.543-.826 3.31-2.37 2.37a1.724 1.724 0 00-2.572 1.065c-.426 1.756-2.924 1.756-3.35 0a1.724 1.724 0 00-2.573-1.066c-1.543.94-3.31-.826-2.37-2.37a1.724 1.724 0 00-1.065-2.572c-1.756-.426-1.756-2.924 0-3.35a1.724 1.724 0 001.066-2.573c-.94-1.543.826-3.31 2.37-2.37.996.608 2.296.07 2.572-1.065z"
											></path>
											<path
												strokeLinecap="round"
												strokeLinejoin="round"
												strokeWidth="2"
												d="M15 12a3 3 0 11-6 0 3 3 0 016 0z"
											></path>
										</svg>
									</span>{" "}
									<span className="ml-2 text-sm tracking-wide truncate">
										個人資料修改
									</span>
								</a>
							</li>


							<li>
								<form action="http://localhost:8080/tofu/logout" method="POST">
									<a
										className="relative flex flex-row items-center h-11 focus:outline-none hover:bg-gray-50 text-gray-200 hover:text-gray-800 border-l-4 border-transparent hover:border-indigo-500 pr-6"
									>
										<span className="inline-flex justify-center items-center ml-4">
											<svg
												className="w-5 h-5"
												fill="none"
												stroke="currentColor"
												viewBox="0 0 24 24"
												xmlns="http://www.w3.org/2000/svg"
											>
												<path
													strokeLinecap="round"
													strokeLinejoin="round"
													strokeWidth="2"
													d="M17 16l4-4m0 0l-4-4m4 4H7m6 4v1a3 3 0 01-3 3H6a3 3 0 01-3-3V7a3 3 0 013-3h4a3 3 0 013 3v1"
												></path>
											</svg>
										</span>{" "}
										<span className="ml-2 text-sm tracking-wide truncate">
											<input type="submit" value="登出" />

										</span>
									</a>
								</form>
							</li>


						</ul>
					</div>

					<div className="flex items-center p-2 mt-12 space-x-4 justify-self-end">

						{id == null ? (<svg
							className="h-12 w-12 text-gray-300"
							viewBox="0 0 24 24"
							fill="currentColor"
							aria-hidden="true"
						>
							<path
								fillRule="evenodd"
								d="M18.685 19.097A9.723 9.723 0 0021.75 12c0-5.385-4.365-9.75-9.75-9.75S2.25 6.615 2.25 12a9.723 9.723 0 003.065 7.097A9.716 9.716 0 0012 21.75a9.716 9.716 0 006.685-2.653zm-12.54-1.285A7.486 7.486 0 0112 15a7.486 7.486 0 015.855 2.812A8.224 8.224 0 0112 20.25a8.224 8.224 0 01-5.855-2.438zM15.75 9a3.75 3.75 0 11-7.5 0 3.75 3.75 0 017.5 0z"
								clipRule="evenodd"
							/>
						</svg>) : (<img
							src={"http://localhost:8080/tofu/employee/getEmployeePhoto/" + id}
							alt=""
							className="w-12 h-12 rounded-lg dark:bg-gray-500"
						/>)}



						<div>
							<h2 className="text-lg font-semibold">{account}</h2>
							<span className="flex items-center space-x-1 hidden">
								{" "}
								<a
									rel="noopener noreferrer"
									href="#"
									className="text-xs hover:underline dark:text-gray-400"
								>
									View profile
								</a>
							</span>
						</div>
					</div>
				</div>
			</div>
		</div>
	);
}

ReactDOM.render(<Sidebar />, document.getElementById('root'));
