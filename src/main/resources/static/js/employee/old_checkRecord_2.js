function CheckRecord() {
	const [posts, setPosts] = React.useState([]);
	const [todos, setTodos] = React.useState([]);

	const EMP_API_BASE_URL = "http://localhost:8080/tofu/employee/";
	const [checkdata, setCheckdata] = React.useState([]);
	const [elist, setElist] = React.useState([]);

	//分頁
	const [loading, setLoading] = React.useState(false);
	const [currentPage, setCurrentPage] = React.useState(1); //當前頁面，預設是第一頁
	const [postsPerPage, setPostsPerPage] = React.useState(10); //每頁預設是10筆

	// 取得所有打卡資料
	React.useEffect(() => {
		setLoading(true);  //正在加載
		const getCheckList = async () => {
			const { data: checklist } = await axios.get(EMP_API_BASE_URL + "checkrecord");
			setCheckdata(checklist.reverse());
			setLoading(false);  //加載完成
		};
		getCheckList();
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
        const employee = elist.find((emp) => {
            return (emp.eid === eid);
        });
        return (employee.firstName + ' ' + employee.lastName);
    }

    const handleSearchByNameChanged = async (e)=>{
        const { data: checkData } = await axios.get(EMP_API_BASE_URL + "search_check", {
                                                                                           "name": 'an',
                                                                                           "checkDate": null
                                                                                       });
        setCheckdata(checkData)
    }

	//此頁套用cupcake, emerald的theme
	React.useEffect(() => {
		document.querySelector("html").setAttribute("data-theme", "emerald");
	}, []);

	// Get current posts
	const indexOfLastPost = currentPage * postsPerPage;  //本頁的最後一筆資料index
	const indexOfFirstPost = indexOfLastPost - postsPerPage; //本頁的第一筆資料index
	const currentPosts = checkdata.slice(indexOfFirstPost, indexOfLastPost);

	// Change page
	const paginateFront = () => setCurrentPage(currentPage + 1);
	const paginateBack = () => setCurrentPage(currentPage - 1);
	const paginate = (pageNumber) => setCurrentPage(pageNumber);

	// 多條件搜尋，取得員工姓名清單
	function getEmpNameList() {
		let namelist = [];
		{ elist.map((emp) => (namelist.push(emp.firstName + " " + emp.lastName))) };
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
	
	// 接收使用者輸入的每頁筆數，並設定為pagination參數
	const handlePostsPerPage = (event) => {
		const userInputNumber = event.target.value;
		setPostsPerPage(userInputNumber);
		setCurrentPage(1);
		//console.log(userInputNumber);
	};
	

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
					<div className="flex justify-between">
						<p className="text-2xl my-6">員工出勤紀錄</p>
					</div>
					<div className="overflow-x-auto">
						<div className="container mx-auto py-4 flexjustify-between">
							<form>
								<div className="flex justify-between">
									<div>
										<label for="name">Search by Name: </label><input onChange={handleSearchByNameChanged} id="name" name="name" type="text" placeholder="Type Name here" className="w-64 ml-4 rounded-md border-0 py-1.5 px-2 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-sky-900 sm:text-sm sm:leading-6" />
										<label for="name" className="ml-6">Search by Date: </label><input id="date" name="checkDate" type="date" className="w-56 ml-4 rounded-md border-0 py-1.5 px-2 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-sky-900 sm:text-sm sm:leading-6" />
									</div>
									<div>
										<label for="postsPerPage" className="ml-6">每頁顯示 </label><input id="postsPerPage" name="postsPerPage" type="number" defaultValue="10" onChange={handlePostsPerPage} onKeyDown={ (evt) => evt.key === '0' && evt.preventDefault() } className="w-14 ml-2 mr-2 rounded-md border-0 py-1.5 px-2 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-sky-900 sm:text-sm sm:leading-6" /> 筆資料
									</div>
								</div>
							</form>
						</div>
						<div className="container mx-auto flex justify-center">
							<table className="table w-full w-4/5">
								<thead>
									<tr>
										<th>ID</th>
										<th>Employee ID</th>
										<th>Employee</th>
										<th>Date</th>
										<th>Check In</th>
										<th>Check Out</th>
									</tr>
								</thead>
								<tbody>
									{currentPosts.map((check) => (
										<tr key={check.cid}>
											<td> {check.cid}</td>
											<td> {check.eid}</td>
											<td> {check.firstName} {check.lastName}</td>
											<td> {check.checks.checkInTime === null ? "" : check.checks.checkInTime.substring(0, 10)}</td>
											<td> {check.checks.checkInTime === null ? "" : check.checks.checkInTime.substring(11)}</td>
											<td> {check.checks.checkOutTime === null ? "" : check.checks.checkOutTime.substring(11)} </td>
										</tr>
									))}
								</tbody>
							</table>
						</div>
						<div><Pagination
							postsPerPage={postsPerPage}
							totalPosts={checkdata.length}
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
