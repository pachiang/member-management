function TaskBookAssignmentManager() {
	const [posts, setPosts] = React.useState([]);
	const [todos, setTodos] = React.useState([]);
	const [selectedValue, setSelectedValue] = React.useState("default");
	const apiEndPoint = "https://jsonplaceholder.typicode.com/posts";
	const todosApiEndPoint = "https://jsonplaceholder.typicode.com/todos";
	const usersApiEndPoint = "https://jsonplaceholder.typicode.com/users";

	const EMP_API_BASE_URL = "http://localhost:8080/tofu/employee/";
	const [waitforassign, setWaitforassign] = React.useState([]);
	const [bookdata, setBookdata] = React.useState([]);
	const [elist, setElist] = React.useState([]);
	const [clist, setClist] = React.useState([]);
	const [plist, setPlist] = React.useState([]);


	// 取得待主管分派的任務數量
	React.useEffect(() => {
		const getTasksNumber = async () => {
			const { data: countWaitingAssign } = await axios.get(EMP_API_BASE_URL + "task/waiting_assign");
			//console.log(countWaitingAssign.waitingTotal);
			setWaitforassign(countWaitingAssign);
		};
		getTasksNumber();
	}, []);

	// 取得所有賞車預約資料
	React.useEffect(() => {
		const getWaitingBooks = async () => {
			const { data: booklist } = await axios.get(EMP_API_BASE_URL + "task/booklist");
			setBookdata(booklist.reverse());
			//console.log(mtnlist);
			//console.log(mtnlist.length);
		};
		getWaitingBooks();
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
	
	//function getPhoto(pid) {
		//let photolist = [];
		//{ plist.map((product) => (photolist.push("data:image/png;base64," + product.photos[0].photo))) };
		//let arraynumber = pid - 1;
		//let photo = photolist[arraynumber];
		//return photo;
	//}




	// 取得任務的所有資料
	React.useEffect(() => {
		const getTodos = async () => {
			//只要取得response body裡面的data，而且要把data取名為res
			const { data: res } = await axios.get(todosApiEndPoint); //用了await，所在的函式就要用async
			setTodos(res);
		};
		getTodos();
	}, []);
	const handleChange = async (todo) => {
		setSelectedValue("default");

		//取得使用者輸入的值，並寫入資料庫
		todo.userId = event.target.value;
		await axios.put(todosApiEndPoint + "/" + todo.id);

		//為了把舊的列表換成新的列表
		const todosClone = [...todos];
		const index = todosClone.indexOf(todo);
		todosClone[index] = { ...todo };
		setTodos(todosClone);
	};

	return (
		<>
			<div className="container mx-auto px-14 py-16 flex justify-between">
				<Sidebar />
				<div className="flex-wrap justify-center w-full w-4/5">
					<div className="flex justify-between">
						<p className="text-2xl my-6">目前共有 {waitforassign.waitingBooks} 件預約賞車任務待分派</p>
					</div>
					<div className="overflow-x-auto flex">
						<table className="table">
							<thead>
								<tr>
									<th>ID</th>
									<th>Product</th>
									<th>Customer</th>
									<th>Reservation Time</th>
									<th>Employee</th>
									<th>Assign</th>
								</tr>
							</thead>
							<tbody>
								{bookdata.map((book) => (
									<tr key={book.book_id}>
										<td> {book.book_id} </td>
										<td> {getTheProductName(book.f_product_id)} </td>
										<td> {getTheCusName(book.f_customer_id)} </td>
										<td> {book.book_date}</td>
										<td> {getTheEmpName(book.f_employee_id)} </td>
										<td>
											<select
												onChange={() => handleChange(book)}
												className="select select-bordered w-full max-w-xs"
												value={selectedValue}
											>
												<option value="default" disabled selected>
													{book.status === 'assigned' ? "Modify" : "Assign"}
												</option>
												<option value="Han Solo">Han Solo</option>
												<option value="Greedo">Greedo</option>
											</select>
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
