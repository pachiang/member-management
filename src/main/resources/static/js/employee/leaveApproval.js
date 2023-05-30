function LeaveApproval() {

    const EMP_API_BASE_URL = "http://localhost:8080/tofu/employee/";
	const [waitingdata, setWaitingdata] = React.useState([]);
	const [processedData, setProcessedData] = React.useState([]);
	const [elist, setElist] = React.useState([]);

	// 取得所有等待審核的資料
	React.useEffect(() => {
		const getWaitingList = async () => {
			const { data: waitinglist } = await axios.get(EMP_API_BASE_URL + "leave/waitinglist");
			setWaitingdata(waitinglist.reverse());
		};
		getWaitingList();
	}, []);

	// 取得處理過的假單資料
	React.useEffect(() => {
		const getProcessedList = async () => {
			const { data: processedList } = await axios.get(EMP_API_BASE_URL + "leave/processedlist");
			setProcessedData(processedList.reverse());
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
        const employee = elist.find((emp) => {
            return (emp.eid === eid);
        });
        return (employee.firstName + ' ' + employee.lastName);
    }

    const handleApprove = async (ele) => {
        await axios.put(EMP_API_BASE_URL + "leave/approve/" + ele.aid);
        const { data: waitinglist } = await axios.get(EMP_API_BASE_URL + "leave/waitinglist");
        setWaitingdata(waitinglist.reverse());
        const { data: processedList } = await axios.get(EMP_API_BASE_URL + "leave/processedlist");
        setProcessedData(processedList.reverse());
    }
    const handleReject = async (ele) => {
        await axios.put(EMP_API_BASE_URL + "leave/reject/" + ele.aid);
        const { data: waitinglist } = await axios.get(EMP_API_BASE_URL + "leave/waitinglist");
        setWaitingdata(waitinglist.reverse());
        const { data: processedList } = await axios.get(EMP_API_BASE_URL + "leave/processedlist");
        setProcessedData(processedList.reverse());
    }

	return (
		<>
			<div className="container mx-auto px-14 py-16 flex justify-between">
				<Sidebar />
				<div className="flex-wrap justify-center w-full w-4/5">
					<div className="flex justify-between">
						<p className="text-2xl my-6">目前共有 {waitingdata.length} 件休假申請等待審核中</p>
					</div>
					<div className="overflow-x-auto">
                    <div className="container mx-auto flex justify-center">
						<table className="table w-full w-4/5">
							<thead>
								<tr>
									<th></th>
									<th>Employee</th>
									<th>Leave</th>
									<th>Begin Date</th>
									<th>End Date</th>
									<th>Reason</th>
									<th>Submit Time</th>
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
										<td> {anapp.leaveReason} </td>
										<td> {anapp.createdDate} </td>
										<td>
											<button onClick={() => handleReject(anapp)} className="btn btn-outline btn-error">
												Reject
											</button>
										</td>
										<td>
											<button onClick={() => handleApprove(anapp)} className="btn btn-outline btn-success">
												Approve
											</button>
										</td>
									</tr>
								))}
							</tbody>
						</table>
                        </div>
                        {/* ===拿來放下面表的地方=== */}
						<div className="container mx-auto justify-between">
                        <div className="flex justify-between">
						<p className="text-2xl my-6">假單審核紀錄</p>
					</div>
                              <table className="table table-zebra w-full">
                                {/* head */}
                                <thead>
                                  <tr>
                                    <th></th>
                                    <th>姓名</th>
                                    <th>假別</th>
                                    <th>起始日期</th>
                                    <th>結束日期</th>
                                    <th>狀態</th>
                                    <th>提交時間</th>
                                  </tr>
                                </thead>
                                <tbody>
                                    {processedData.map((eleOfProcessedData) => (
                                        <tr key={eleOfProcessedData.aid}>
                                            <td> {eleOfProcessedData.aid} </td>
                                            <td> {eleOfProcessedData.employee.firstName} {eleOfProcessedData.employee.lastName}</td>
                                            <td> {eleOfProcessedData.leave.leaveCategory}</td>
                                            <td> {eleOfProcessedData.beginDate}</td>
                                            <td> {eleOfProcessedData.endDate} </td>
                                            <td> {eleOfProcessedData.managerApproved ? "核准" : "駁回"} </td>
                                            <td> {eleOfProcessedData.createdDate.substr(0,10)}</td>
                                        </tr>
                                    ))}
                                </tbody>
                              </table>

                        </div>
					</div>
				</div>
			</div>
		</>

	);
}
