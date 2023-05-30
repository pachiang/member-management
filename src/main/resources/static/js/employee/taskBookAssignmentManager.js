function TaskBookAssignmentManager() {

	const EMP_API_BASE_URL = "http://localhost:8080/tofu/employee/";
	const [waitforassign, setWaitforassign] = React.useState([]);
	const [bookdata, setBookdata] = React.useState([]);
	const [elist, setElist] = React.useState([]);
	const [clist, setClist] = React.useState([]);
	const [plist, setPlist] = React.useState([]);

	//
	const maintenanceApiEndPoint = "http://localhost:8080/tofu/employee/task/booklist";
	const avalibleEmployeeByMidApiEndPoint = "http://localhost:8080/tofu/employee/task/available_emplist/book/";
	const employeeApiEndPoint = "http://localhost:8080/tofu/employee/allJson";

	const [books, setBooks] = React.useState([]);
	const [employees, setEmployees] = React.useState([]);
	const [selectedValue, setSelectedValue] = React.useState("default");

	//分頁
	const [loading, setLoading] = React.useState(false);
	const [currentPage, setCurrentPage] = React.useState(1); //當前頁面，預設是第一頁
	const [postsPerPage, setPostsPerPage] = React.useState(10); //每頁預設是10筆

	//
	React.useEffect(() => {
		setLoading(true);  //正在加載
		const getBooks = async () => {
			// get info (eid) of avalible employees into the table about maintenance
			const { data: res } = await axios.get(EMP_API_BASE_URL + "task/booklist");
			const res1 = res.map(async (element) => {
				const { data: avalibleListOfElement } = await axios.get(avalibleEmployeeByMidApiEndPoint + element.book_id)
				const avalibleListOfElementAccount = avalibleListOfElement.map(({ eid, firstName, lastName }) => ({ eid, firstName, lastName }))
				//				const avalibleListOfElementEid = avalibleListOfElement.map(ele => ele.eid)
				return ({
					...element,
					avalibleList: avalibleListOfElementAccount,
				})
			});
			const res2 = await Promise.all(res1)
			setBooks(res2.reverse())
			setLoading(false);  //加載完成
			// get employees info
			const { data: tempEmployeesInfo } = await axios.get(employeeApiEndPoint);
			setEmployees(tempEmployeesInfo)
		};
		getBooks();
	}, []);

	const handleChange = async (ele) => {
		setSelectedValue("default");
		// 送出put
		await axios.put('http://localhost:8080/tofu/employee/task/management_book/' + ele.book_id,
			{ "f_employee_id": event.target.value });
		// 想辦法讓表格重整
		const { data: res } = await axios.get(EMP_API_BASE_URL + "task/booklist");
		const res1 = res.map(async (element) => {
			const { data: avalibleListOfElement } = await axios.get(avalibleEmployeeByMidApiEndPoint + element.book_id)
			const avalibleListOfElementAccount = avalibleListOfElement.map(({ eid, firstName, lastName }) => ({ eid, firstName, lastName }))
			return ({
				...element,
				avalibleList: avalibleListOfElementAccount,
			})
		});
		const res2 = await Promise.all(res1)
		setBooks(res2.reverse())
	};

	const getAccountByEid = (id) => {
		const employee = employees.find((emp) => {
			return (emp.eid === id)
		})
		return employee ? employee.account : ''
	}


	// Get current posts
	const indexOfLastPost = currentPage * postsPerPage;  //本頁的最後一筆資料index
	const indexOfFirstPost = indexOfLastPost - postsPerPage; //本頁的第一筆資料index
	let currentPosts = books.slice(indexOfFirstPost, indexOfLastPost);

	// Change page
	const paginateFront = () => setCurrentPage(currentPage + 1);
	const paginateBack = () => setCurrentPage(currentPage - 1);
	const paginate = (pageNumber) => setCurrentPage(pageNumber);

	let booksTbody = currentPosts.map((ele) => {
		return (<tr className="hover" key={ele.book_id}>
			<td>{ele.book_id}</td>
			<td>{ele.f_product_id ? (<img className="inline-block object-scale-down w-12 mr-2" src={"http://localhost:8080/tofu/employee/task/getProductPhoto/" + ele.f_product_id} />) : null } {ele.f_product_id ? getTheProductName(ele.f_product_id) : ''}</td>
			<td>{ele.f_customer_id ? getTheCusName(ele.f_customer_id) : ''} {ele.f_customer_id ? (
				<div className="dropdown dropdown-top dropdown-hover">
					<label tabIndex={0}><svg xmlns="http://www.w3.org/2000/svg" className="w-3 h-3 mx-2" viewBox="0 0 24 24" fill="gray" stroke="gray" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"><g fill="none" fill-rule="evenodd"><path d="M18 14v5a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V8c0-1.1.9-2 2-2h5M15 3h6v6M10 14L20.2 3.8" /></g></svg></label>
					<div tabIndex={0} className="dropdown-content p-2 shadow bg-sky-50 rounded-box h-24 flex items-center pr-8"><div className="avatar">
						<div className="w-16 rounded-full mx-4"><img src={"http://localhost:8080/tofu/downloadPhoto/" + ele.f_customer_id} /></div></div>
						<div>
							<strong className="block text-lg">{ele.f_customer_id ? getTheCusName(ele.f_customer_id) : ''}</strong>
							<span className="block text-slate-400 text-sm mt-1">{ele.f_customer_id ? getTheCusPhone(ele.f_customer_id) : ''}</span>
							<span className="block text-slate-400 text-sm">{ele.f_customer_id ? getTheCusEmail(ele.f_customer_id) : ''}</span>
						</div></div>
				</div>) : null}</td>
			<td>{ele.book_date}</td>
			<td>{ele.f_employee_id ? getTheEmpName(ele.f_employee_id) : ''}</td>
			<td>
				<select disabled={ele.status === 'closed' ? true : false}
					onChange={() => handleChange(ele)}
					className="select select-bordered w-full max-w-xs"
					value={selectedValue}
				>
					<option value="default" disabled selected>
						{ele.status !== 'closed' ? ele.status !== 'assigned' ? "assign" : "modify" : "closed"}
					</option>
					{ele.avalibleList.map((ele_ele) => <option value={ele_ele.eid} key={ele_ele.eid}>{ele_ele.firstName + ' ' + ele_ele.lastName}</option>)}
				</select>
			</td>
		</tr>)
	})




	// 取得待主管分派的任務數量
	React.useEffect(() => {
		const getTasksNumber = async () => {
			const { data: countWaitingAssign } = await axios.get(EMP_API_BASE_URL + "task/waiting_assign");
			setWaitforassign(countWaitingAssign);
		};
		getTasksNumber();
	}, []);

	// 取得所有賞車預約資料
	React.useEffect(() => {
		const getWaitingBooks = async () => {
			const { data: booklist } = await axios.get(EMP_API_BASE_URL + "task/booklist");
			setBookdata(booklist.reverse());
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

	//	function getTheEmpName(eid) {
	//		let namelist = [];
	//		{ elist.map((emp) => (namelist.push(emp.firstName + " " + emp.lastName))) };
	//		let arraynumber = eid - 1;
	//		let name = namelist[arraynumber];
	//		return name;
	//	}

	function getTheEmpName(eid) {
		if (elist.length === 0) return ''
		const employee = elist.find((emp) => {
			return (emp.eid === eid);
		});
		return (employee.firstName + ' ' + employee.lastName);
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


	function getTheProductName(pid) {
		if (plist.length === 0) return ''
		const product = plist.find((pro) => {
			return (pro.productId === pid);
		});
		return (product.productModel);
	}


	//function getPhoto(pid) {
	//let photolist = [];
	//{ plist.map((product) => (photolist.push("data:image/png;base64," + product.photos[0].photo))) };
	//let arraynumber = pid - 1;
	//let photo = photolist[arraynumber];
	//return photo;
	//}

	//此頁套用cupcake, emerald的theme
	React.useEffect(() => {
		document.querySelector("html").setAttribute("data-theme", "emerald");
	}, []);

	//如果頁面還在加載中
	if (loading) {
		return (
			<>
				<div className="container mx-auto px-14 flex justify-between">
					<Sidebar targetData={books} />
					<div className="flex-wrap justify-center w-full w-4/5">

						<div className="overflow-x-auto flex h-screen justify-center items-center">
							<div className="sk-fading-circle">
								<div className="sk-circle1 sk-circle"></div>
								<div className="sk-circle2 sk-circle"></div>
								<div className="sk-circle3 sk-circle"></div>
								<div className="sk-circle4 sk-circle"></div>
								<div className="sk-circle5 sk-circle"></div>
								<div className="sk-circle6 sk-circle"></div>
								<div className="sk-circle7 sk-circle"></div>
								<div className="sk-circle8 sk-circle"></div>
								<div className="sk-circle9 sk-circle"></div>
								<div className="sk-circle10 sk-circle"></div>
								<div className="sk-circle11 sk-circle"></div>
								<div className="sk-circle12 sk-circle"></div>
							</div>
						</div>


					</div>
				</div>
			</>
		)
	}

	return (
		<>
			<div className="container mx-auto px-14 py-16 flex justify-between">
				<Sidebar targetData={books} />
				<div className="flex-wrap justify-center w-full w-4/5">
					<div className="flex justify-between">
						<p className="text-2xl my-6">目前共有 {books.filter((ele) => ele.status === 'waiting').length} 件預約賞車任務待分派</p>
					</div>
					<div className="overflow-x-auto">
						<table className="table w-full w-4/5">
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
								{booksTbody}
							</tbody>
						</table>
						<div><Pagination
							postsPerPage={postsPerPage}
							totalPosts={books.length}
							paginateBack={paginateBack}
							paginateFront={paginateFront}
							paginate={paginate}
							currentPage={currentPage}
						/>
						</div>
					</div>


				</div>
			</div>
		</>

	);
}
