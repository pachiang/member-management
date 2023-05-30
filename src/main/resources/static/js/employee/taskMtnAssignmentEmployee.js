function TaskMtnAssignmentEmployee() {

	const EMP_API_BASE_URL = "http://localhost:8080/tofu/employee/";
	const [waittodo, setWaittodo] = React.useState([]);
	const [mtndata, setMtndata] = React.useState([]);
	const [elist, setElist] = React.useState([]);
	const [clist, setClist] = React.useState([]);
	const [plist, setPlist] = React.useState([]);

	// 取得員工被分派到的任務數量
	React.useEffect(() => {
		const getTasksNumber = async () => {
			const { data: countWaitingTodo } = await axios.get(EMP_API_BASE_URL + "task/waiting_todo");
			//console.log(countWaitingAssign.waitingTotal);
			setWaittodo(countWaitingTodo);
		};
		getTasksNumber();
	}, []);

	// 取得所有維修申請資料
	React.useEffect(() => {
		const getWaitingMtns = async () => {
			const { data: mtnlist } = await axios.get(EMP_API_BASE_URL + "task/mtn_todolist"); //用了await，所在的函式就要用async
			setMtndata(mtnlist.reverse());
			//console.log(mtnlist);
			//console.log(mtnlist.length);
		};
		getWaitingMtns();
	}, []);

	// 取得員工資料 權限不符
	//	React.useEffect(() => {
	//		const getEmpList = async () => {
	//			const { data: emplist } = await axios.get(EMP_API_BASE_URL + "allJson");
	//			setElist(emplist);
	//		};
	//		getEmpList();
	//	}, []);

	//	function getTheEmpName(eid) {
	//		let namelist = [];
	//		{ elist.map((emp) => (namelist.push(emp.firstName + " " + emp.lastName))) };
	//		let arraynumber = eid - 1;
	//		let name = namelist[arraynumber];
	//		return name;
	//	}

	// 取得客戶資料
	React.useEffect(() => {
		const getCusList = async () => {
			const { data: cuslist } = await axios.get(EMP_API_BASE_URL + "task/customerlist");
			setClist(cuslist);
		};
		getCusList();
	}, []);

	// 有時間要改用find的方法寫
	function getTheCusName(cid) {
		if (clist.length === 0) return ''
		const customer = clist.find((cus) => {
			return (cus.customer_id === cid);
		});
		return (customer.name);
	}

	function getTheCusPhone(cid) {
		if (clist.length === 0) return ''
		const customer = clist.find((cus) => {
			return (cus.customer_id === cid);
		});
		return (customer.phone);
	}
	
	function getTheCusEmail(cid) {
		if (clist.length === 0) return ''
		const customer = clist.find((cus) => {
			return (cus.customer_id === cid);
		});
		return (customer.email);
	}

	// 取得產品資料
	React.useEffect(() => {
		const getProductList = async () => {
			const { data: productlist } = await axios.get(EMP_API_BASE_URL + "task/productlist");
			setPlist(productlist);
		};
		getProductList();
	}, []);

	// 有時間要改用find的方法寫
	function getTheProductName(pid) {
		if (plist.length === 0) return ''
		const product = plist.find((pro) => {
			return (pro.productId === pid);
		});
		return (product.productModel);
	}


	//取得資料
	//	const [posts, setPosts] = React.useState([]);
	//	const apiEndPoint = "https://jsonplaceholder.typicode.com/posts";
	//	React.useEffect(() => {
	//		const getPosts = async () => {
	//			const { data: res } = await axios.get(apiEndPoint);
	//			setPosts(res);
	//		};
	//		getPosts();
	//	}, []);

	const handleComplete = async (post) => {
		// 把資料寫進資料庫
		await axios.put(EMP_API_BASE_URL + "task/management_mtn/finished/" + post.mid);

		// 更新mtnList使表格重新render
		const { data: mtnlist } = await axios.get(EMP_API_BASE_URL + "task/mtn_todolist"); //用了await，所在的函式就要用async
		setMtndata(mtnlist.reverse());
	};

	return (
		<>
			<div className="container mx-auto px-14 py-16 flex justify-between">
				<Sidebar targetData={mtndata} />
				<div className="flex-wrap justify-center w-full w-4/5">
					<div className="flex justify-between">
						<p className="text-2xl my-6">目前共有 {mtndata.length} 件預約保養任務待完成</p>
					</div>
					<div className="overflow-x-auto flex">
						<table className="table w-full w-4/5">
							<thead>
								<tr>
									<th>ID</th>
									<th>Maintenance Category</th>
									<th>Product</th>
									<th>Customer</th>
									<th>Reservation Time</th>
									<th>Update</th>
								</tr>
							</thead>
							<tbody>
								{mtndata.map((amtn) => (
									<tr key={amtn.mid}>
										<td> {amtn.mid} </td>
										<td> {amtn.mcategory} </td>
										<td> {amtn.pid ? (<img className="inline-block object-scale-down w-12 mr-2" src={"http://localhost:8080/tofu/employee/task/getProductPhoto/" + amtn.pid} />) : null } {amtn.pid ? getTheProductName(amtn.pid) : ''} </td>
										<td> {amtn.cid ? getTheCusName(amtn.cid) : ''} {amtn.cid ? (
											<div className="dropdown dropdown-top dropdown-hover">
												<label tabIndex={0}><svg xmlns="http://www.w3.org/2000/svg" className="w-3 h-3 mx-2" viewBox="0 0 24 24" fill="gray" stroke="gray" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"><g fill="none" fill-rule="evenodd"><path d="M18 14v5a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V8c0-1.1.9-2 2-2h5M15 3h6v6M10 14L20.2 3.8" /></g></svg></label>
												<div tabIndex={0} className="dropdown-content p-2 shadow bg-sky-50 rounded-box h-24 flex items-center pr-8"><div className="avatar">
													<div className="w-16 rounded-full mx-4"><img src={"http://localhost:8080/tofu/downloadPhoto/" + amtn.cid} /></div></div>
													<div>
														<strong className="block text-lg">{amtn.cid ? getTheCusName(amtn.cid) : ''}</strong>
														<span className="block text-slate-400 text-sm mt-1">{amtn.cid ? getTheCusPhone(amtn.cid) : ''}</span>
														<span className="block text-slate-400 text-sm">{amtn.cid ? getTheCusEmail(amtn.cid) : ''}</span>
													</div></div>
											</div>) : null}</td>
										<td> {amtn.appointment === null ? "" : amtn.appointment.substring(0, 10)} {amtn.appointmenttime}</td>
										<td>
											<button onClick={() => handleComplete(amtn)} className="btn btn-outline">
												Completed
											</button>
										</td>
									</tr>
								))}
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</>
	);
}
