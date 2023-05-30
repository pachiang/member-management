function LeaveApproval() {
	const [posts, setPosts] = React.useState([]);
	const [todos, setTodos] = React.useState([]);
	const [selectedValue, setSelectedValue] = React.useState("default");
	const apiEndPoint = "https://jsonplaceholder.typicode.com/posts";
	const todosApiEndPoint = "https://jsonplaceholder.typicode.com/todos";
	const usersApiEndPoint = "https://jsonplaceholder.typicode.com/users";

	const EMP_API_BASE_URL = "http://localhost:8080/tofu/employee/";
	const [waitingdata, setWaitingdata] = React.useState([]);
	const [processeddata, setProcesseddata] = React.useState([]);
	const [elist, setElist] = React.useState([]);

	// 取得所有等待審核的資料
	React.useEffect(() => {
		const getWaitingList = async () => {
			const { data: waitinglist } = await axios.get(EMP_API_BASE_URL + "leave/waitinglist");
			setWaitingdata(waitinglist.reverse());
			//console.log(mtnlist);
			//console.log(mtnlist.length);
		};
		getWaitingList();
	}, []);

	// 取得處理過的假單資料
	React.useEffect(() => {
		const getProcessedList = async () => {
			const { data: processedlist } = await axios.get(EMP_API_BASE_URL + "leave/processedlist");
			setProcesseddata(processedlist.reverse());
			//console.log(mtnlist);
			//console.log(mtnlist.length);
		};
		getProcessedList();
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
						<p className="text-2xl my-6">目前共有 {waitingdata.length} 件請假申請等待審核中</p>
					</div>
					<div className="overflow-x-auto flex">
						<table className="table w-full w-4/5">
							<thead>
								<tr>
									<th>ID</th>
									<th>Employee</th>
									<th>Leave</th>
									<th>Begin Date</th>
									<th>End Date</th>
									<th>Reject</th>
									<th>Approve</th>
								</tr>
							</thead>
							<tbody>
								{waitingdata.map((anapp) => (
									<tr key={anapp.aid}>
										<td> {anapp.aid} </td>
										<td> {anapp.employee.firstName} {anapp.employee.lastName}</td>
										<td> {anapp.leave.leaveCategory}</td>
										<td> {anapp.beginDate}</td>
										<td> {anapp.endDate} </td>
										<td>
											<button onClick={() => handleComplete(anapp)} className="btn btn-outline">
												Reject
											</button>
										</td>
										<td>
											<button onClick={() => handleComplete(anapp)} className="btn btn-outline">
												Approve
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
