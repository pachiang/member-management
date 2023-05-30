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

	// 取得員工資料
	React.useEffect(() => {
		const getEmpList = async () => {
			const { data: emplist } = await axios.get(EMP_API_BASE_URL + "allJson");
			setElist(emplist);
		};
		getEmpList();
	}, []);

	function getTheEmpName(eid) {
		let namelist = [];
		{ elist.map((emp) => (namelist.push(emp.firstName + " " + emp.lastName))) };
		let arraynumber = eid - 1;
		let name = namelist[arraynumber];
		return name;
	}

	// 取得客戶資料
	React.useEffect(() => {
		const getCusList = async () => {
			const { data: cuslist } = await axios.get(EMP_API_BASE_URL + "task/customerlist");
			setClist(cuslist);
		};
		getCusList();
	}, []);

	function getTheCusName(cid) {
		let namelist = [];
		{ clist.map((cus) => (namelist.push(cus.name))) };
		let arraynumber = cid - 1;
		let name = namelist[arraynumber];
		return name;
	}
	
	// 取得產品資料
	React.useEffect(() => {
		const getProductList = async () => {
			const { data: productlist } = await axios.get(EMP_API_BASE_URL + "task/productlist");
			setPlist(productlist);
		};
		getProductList();
	}, []);

	function getTheProductName(pid) {
		let namelist = [];
		{ plist.map((product) => (namelist.push(product.productModel))) };
		let arraynumber = pid - 1;
		let name = namelist[arraynumber];
		return name;
	}
	
	
	//取得資料
	const [posts, setPosts] = React.useState([]);
	const apiEndPoint = "https://jsonplaceholder.typicode.com/posts";
	React.useEffect(() => {
		const getPosts = async () => {
			const { data: res } = await axios.get(apiEndPoint);
			setPosts(res);
		};
		getPosts();
	}, []);

	const handleComplete = async (post) => {
		post.title = "Updated title";
		// 把資料寫進資料庫
		await axios.put(apiEndPoint + "/" + post.id);

		//把舊的列表換成新的列表，把被點到的資料列刪除（過濾掉）
		setPosts(posts.filter((p) => p.id !== post.id));
		// const postsClone = [...posts];
		// const index = postsClone.indexOf(post);
		// postsClone[index] = { ...post };
		// setPosts(postsClone);
	};

	return (
		<>
			<div className="container mx-auto px-14 py-16 flex justify-between">
				<Sidebar />
				<div className="flex-wrap justify-center w-full w-4/5">
					<div className="flex justify-between">
						<p className="text-2xl my-6">目前共有 {waittodo.waitingMtns} 件預約保養任務待完成</p>
					</div>
					<div className="overflow-x-auto flex">
						<table className="table">
							<thead>
								<tr>
									<th>ID</th>
									<th>Maintenance and Repair Category</th>
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
										<td> {getTheProductName(amtn.pid)} </td>
										<td> {getTheCusName(amtn.cid)} </td>
										<td> {amtn.appointment} {amtn.appointmenttime}</td>
										<td>
											<button onClick={() => handleComplete(amtn)} className="btn">
												Update
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
