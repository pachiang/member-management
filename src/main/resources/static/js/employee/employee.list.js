function Home() {
	// data是一個state，用useState來宣告它是一個state，React會去關注state是否改變，改變會重新渲染它所在的那個component（這裡是Home），所以會重新渲染Home
	// setData是一個void function，它會改變state的值，它新的值會變成data新的值，也就是把data更新成setData輸入的值
	// 要改data要呼叫setData
	// 原先寫法：data = data + 1
	// 現在寫法：setData(data + 1)
	// useState([])的()裡面要放初始狀態值

	const EMP_API_BASE_URL = "http://localhost:8080/tofu/employee/";
	const [listForMulSearch, setListForMulSearch] = React.useState(null);
    const [searchData, setSearchData] = React.useState({ "name": null,
                                                         "hireDateStart": null,
                                                         "hireDateEnd": null,
                                                         "department": "default",
                                                         "position" : "default"});

	const [data, setData] = React.useState([]);
	const [currentUserInfo, setCurrentUserInfo] = React.useState([]);

	// show modal depend on button click
	const [showModal, setShowModal] = React.useState(false);

	//分頁
	const [loading, setLoading] = React.useState(false);
	const [currentPage, setCurrentPage] = React.useState(1); //當前頁面，預設是第一頁
	const [postsPerPage, setPostsPerPage] = React.useState(10); //每頁預設是10筆

	// 刪除資料列的function
	const tableRowRemove = (index) => {
		// e.preventDefault();
		axios
			.delete("http://localhost:8080/tofu/employee/delete/" + index)
			.then((response) => {
				console.log(response);
			})
			.catch((error) => {
				console.log(error);
			});

		let dataRows = [...data]; //data就是axios取得的資料，只是重新存一個變數叫dataRow
		dataRows = dataRows.filter((dataRow) => dataRow.eid !== index); //每次拿一列出來，一列中有eid屬性，將每列的eid屬性一一比對是否是按鈕的Index，再另存成一個dataRows
		// dataRow.splice(index, 1); //從index開始往後的一個
		// console.log(index);
		setData(dataRows);
	};
	// 取得資料，傳送一個get request到後端，取得employee的json資料
	React.useEffect(() => {
		setLoading(true);  //正在加載
		axios
			.get("http://localhost:8080/tofu/employee/allJson")
			.then((response) => {
				setData(response.data);
				setLoading(false);  //加載完成
			})
			.catch((error) => {
				console.log(error);
			});
	}, []);

	// 多條件搜尋，取得員工姓名清單
	function getEmpNameList() {
		let namelist = [];
		{ data.map((emp) => (namelist.push(emp.firstName + " " + emp.lastName))) };
		return namelist;
	}

	$(function() {
		var list = getEmpNameList();
		$("#name").autocomplete({
			source: list,
			classes: {
				"ui-autocomplete": "highlight"
			}
		});
	});
	
	// reset button
    const handleReset = ()=>{ setSearchData({ "name": null,
                                             "hireDateStart": null,
                                             "hireDateEnd": null,
                                             "department": "default",
                                             "position" : "default"})}

	//此頁套用cupcake, emerald的theme
	React.useEffect(() => {
		document.querySelector("html").setAttribute("data-theme", "emerald");
	}, []);

	// Get current posts
	const indexOfLastPost = currentPage * postsPerPage;  //本頁的最後一筆資料index
	const indexOfFirstPost = indexOfLastPost - postsPerPage; //本頁的第一筆資料index
	const currentPosts = data.slice(indexOfFirstPost, indexOfLastPost);

	// Change page
	const paginateFront = () => setCurrentPage(currentPage + 1);
	const paginateBack = () => setCurrentPage(currentPage - 1);
	const paginate = (pageNumber) => setCurrentPage(pageNumber);

	// 接收使用者輸入的每頁筆數，並設定為pagination參數
	const handlePostsPerPage = (event) => {
		const userInputNumber = event.target.value;
		setPostsPerPage(userInputNumber);
		setCurrentPage(1);
		//console.log(userInputNumber);
	};

    // 拿department與position資料
//    React.useEffect( async () => {
//        const { data : res } = await axios.get(EMP_API_BASE_URL + "listForMulSearch");
//        console.log(res)
//        setListForMulSearch(res)
//    })
    let positionOptions = ["Assistant",
                          "Designer",
                          "Engineer",
                          "Manager",
                          "Sales Representative"].map((pos)=><option value={pos}>{pos}</option>)
    let departmentOptions = ["Human Resource",
                            "Product",
                            "Public Relations",
                            "Sales" ].map((dep)=><option value={dep}>{dep}</option>)

    const handleSearchDataChanged = async (e)=>{
        setSearchData({...searchData, [e.target.name]:e.target.value})
    }
    React.useEffect( async () => {
        // 把searchData換成要送出的body，如果是default的地方換成null
        const bodyForSearch = {...searchData, ['position'] : searchData.position==='default' ? null : searchData.position, ['department'] : searchData.department==='default' ? null : searchData.department}
        console.log(bodyForSearch)
        const { data: res } = await axios.post(EMP_API_BASE_URL + "search", bodyForSearch);
        setData(res)
    }, [searchData])


	// 使用map把data裡面的東西一個一個拿出來，叫employee，想成迴圈
	// => 不用寫 function
	// 記得給每個 tr 一個 key
	let dta = currentPosts.map((employee) => {
		return (
			<tr className="hover" key={employee.eid}>
				<td>{employee.eid}</td>
				<td>
					<img
						className="empPhoto object-scale-down"
						src={
							"http://localhost:8080/tofu/employee/getEmployeePhoto/" +
							employee.eid
						}
					/>
				</td>
				<td>
					{employee.firstName} {employee.lastName}
				</td>
				<td>{getAge(employee.birthday)}</td>
				<td>{employee.department}</td>
				<td>{employee.position}</td>
				<td>{employee.hireDate}</td>
				<td>{employee.email}</td>
				<td>
					{/* <a href={"http://localhost:8080/tofu/employee/edit/" + employee.eid}> */}
					<a href="#my-modal">
						<button
							className="btn btn-outline btn-secondary btn-xs"
							id={"e" + employee.eid}
							onClick={(e) => {
								setCurrentUserInfo(data.filter((user) => user.eid === employee.eid));
								setShowModal(true)
							}}
						>
							<i className="fas fa-user-edit"></i>
						</button>
					</a>
					{/* </a> */}
				</td>
				<td>
					<button
						className="btn btn-outline btn-error btn-xs"
						onClick={() => tableRowRemove(employee.eid)}
						id={"d" + employee.eid}
					>
						<i className="fas fa-trash-alt"></i>
					</button>
				</td>
			</tr>
		);
	});

	//如果頁面還在加載中
	if (loading) {
		return (
			<>
				<div className="container mx-auto px-14 flex justify-between">
					<Sidebar />
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
				<Sidebar />
				<div className="flex-wrap justify-center w-full w-4/5">
					<div className="w-4/5">
						<p className="text-2xl my-6">Employee List</p>
					</div>
					<div className="overflow-x-auto justify-center">
						<div className="container mx-auto py-4 flex justify-between">
							<form>
								<div className="flex justify-between">
									<div>
										<label htmlFor="name">Search by Name: </label><input value={searchData.name} onChange={(e) => handleSearchDataChanged(e)} id="name" name="name" type="text" placeholder="Type Name here" className="w-44 ml-4 rounded-md border-0 py-1.5 px-2 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-sky-900 sm:text-sm sm:leading-6" />
										<label htmlFor="hireDateStart" className="ml-6">By Hire Date: </label><input value={searchData.hireDateStart} onChange={(e) => handleSearchDataChanged(e)} id="hireDateStart" name="hireDateStart" type="date" className="w-44 ml-4 rounded-md border-0 py-1.5 px-2 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-sky-900 sm:text-sm sm:leading-6" />
										<label htmlFor="hireDateEnd" className="ml-2"> to </label><input value={searchData.hireDateEnd} onChange={(e) => handleSearchDataChanged(e)} id="hireDateEnd" name="hireDateEnd" type="date" className="w-44 ml-2 rounded-md border-0 py-1.5 px-2 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-sky-900 sm:text-sm sm:leading-6" />
										<div className="mt-4">
											<label htmlFor="department" >By Department: </label><select value={searchData.department} onChange={(e) => handleSearchDataChanged(e)} id="department" name="department" className="select select-bordered select-sm ml-6 w-44 max-w-xs"><option value="default" disabled selected>Select Department</option>{departmentOptions}</select>
											<label htmlFor="position" className="ml-6">By Position: </label><select value={searchData.position} onChange={(e) => handleSearchDataChanged(e)} id="position" name="position" className="select select-bordered select-sm ml-6 w-44 max-w-xs"><option value="default" disabled selected>Select Position</option>{positionOptions}</select>
											<button type="reset" onClick={handleReset} className="btn btn-outline btn-sm ml-6">Reset</button>
										</div>
									</div>
								</div>
							</form>
						</div>



						<div className="container mx-auto py-4 flex justify-end">
							<label htmlFor="postsPerPage" className="ml-6">每頁顯示 </label><input id="postsPerPage" name="postsPerPage" type="number" defaultValue="10" onChange={handlePostsPerPage} onKeyDown={(evt) => evt.key === '0' && evt.preventDefault()} className="w-14 ml-2 mr-2 rounded-md border-0 py-1.5 px-2 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-sky-900 sm:text-sm sm:leading-6" /> 筆資料
						</div>
						<div className="container mx-auto flex justify-center">

							<table className="table w-full w-4/5">
								<thead className="text-left">
									<tr>
										<th>ID</th>
										<th>Photo</th>
										<th>Name</th>
										<th>Age</th>
										<th>Department</th>
										<th>Position</th>
										<th>Hire Date</th>
										<th>Email</th>
										<th>Edit</th>
										<th>Delete</th>
									</tr>
								</thead>
								<tbody>{dta}</tbody>
							</table>
						</div>
						<div><Pagination
							postsPerPage={postsPerPage}
							totalPosts={data.length}
							paginateBack={paginateBack}
							paginateFront={paginateFront}
							paginate={paginate}
							currentPage={currentPage}
						/>
						</div>

					</div >
				</div >
			</div >
			{showModal ? <EditEmployee userInfo={currentUserInfo} /> : null
			}
		</>
	);

	//使用生日來計算年齡
	function getAge(birthday) {
		const today = new Date();
		const birthDate = new Date(birthday);
		let age = today.getFullYear() - birthDate.getFullYear();
		const month = today.getMonth() - birthDate.getMonth();
		if (month < 0 || (month === 0 && today.getDate() < birthDate.getDate())) {
			age--;
		}
		return age;
	}
}
