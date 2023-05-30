function TaskBookAssignmentEmployee() {

	const EMP_API_BASE_URL = "http://localhost:8080/tofu/employee/";
	const [waittodo, setWaittodo] = React.useState([]);
	const [bookdata, setBookdata] = React.useState([]);
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

	// 取得員工自己被分配到的預約賞車資料
	React.useEffect(() => {
		const getWaitingBooks = async () => {
			const { data: booklist } = await axios.get(EMP_API_BASE_URL + "task/book_todolist");
			setBookdata(booklist.reverse());
			//console.log(mtnlist);
			//console.log(mtnlist.length);
		};
		getWaitingBooks();
	}, []);

	// 取得員工資料
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


	// React.useEffect(()=>{console.log(plist)}, [plist])



	const handleComplete = async (post) => {
		//		post.title = "Updated title";
		// 把資料寫進資料庫
		await axios.put(EMP_API_BASE_URL + "task/management_book/finished/" + post.book_id);

		// 更新mtnList使表格重新render
		const { data: booklist } = await axios.get(EMP_API_BASE_URL + "task/book_todolist");
		setBookdata(booklist.reverse());
	};

	return (
		<>
			<div className="container mx-auto px-14 py-16 flex justify-between">
				<Sidebar targetData={bookdata} />
				<div className="flex-wrap justify-center w-full w-4/5">
					<div className="flex justify-between">
						<p className="text-2xl my-6">目前共有 {bookdata.length} 件預約賞車任務待完成</p>
					</div>
					<div className="overflow-x-auto flex">
						<table className="table w-full w-4/5">
							<thead>
								<tr>
									<th>ID</th>
									<th>Product</th>
									<th>Customer</th>
									<th>Reservation Time</th>
									<th>Update</th>
								</tr>
							</thead>
							<tbody>
								{bookdata.map((book) => (
									<tr key={book.book_id}>
										<td> {book.book_id} </td>
										<td> {book.f_product_id ? getTheProductName(book.f_product_id) : ''} </td>
										<td> {book.f_customer_id ? getTheCusName(book.f_customer_id) : ''} </td>
										<td> {book.book_date}</td>
										<td>
											<button onClick={() => handleComplete(book)} className="btn">
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
