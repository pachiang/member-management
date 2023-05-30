function TaskMtnAssignmentManager() {

	const EMP_API_BASE_URL = "http://localhost:8080/tofu/employee/";
	const [waitforassign, setWaitforassign] = React.useState([]);
	const [mtndata, setMtndata] = React.useState([]);
	const [elist, setElist] = React.useState([]);
	const [clist, setClist] = React.useState([]);
	const [plist, setPlist] = React.useState([]);

    //
    const maintenanceApiEndPoint = "http://localhost:8080/tofu/employee/task/mtnlist";
    const avalibleEmployeeByMidApiEndPoint = "http://localhost:8080/tofu/employee/task/available_emplist/mtn/";
    const employeeApiEndPoint = "http://localhost:8080/tofu/employee/allJson";

    const [maintenance, setMaintenance ] = React.useState([]);
    const [employees, setEmployees ] = React.useState([]);
	const [selectedValue, setSelectedValue] = React.useState("default");

    //
    React.useEffect(() => {
        const getMaintenance = async () => {
            // get info (eid) of avalible employees into the table about maintenance
            const { data: res } = await axios.get(maintenanceApiEndPoint);
            const res1 =  res.map(async (element) => {
            const { data: avalibleListOfElement } = await axios.get(avalibleEmployeeByMidApiEndPoint + element.mid)
            const avalibleListOfElementAccount = avalibleListOfElement.map(({ eid, firstName, lastName }) => ({ eid, firstName, lastName }))
            return({
            ...element,
            avalibleList: avalibleListOfElementAccount,
            })});
            const res2 = await Promise.all(res1)
            setMaintenance(res2)

            // get employees info
            const { data: tempEmployeesInfo } = await axios.get(employeeApiEndPoint);
            setEmployees(tempEmployeesInfo)
        };
        getMaintenance();
    }, []);

  const handleChange = async (ele) => {
    setSelectedValue("default");
    // 送出put
    await axios.put('http://localhost:8080/tofu/employee/task/management_mtn/' + ele.mid,
    { "eid": event.target.value });

    // 想辦法讓表格重整
    const { data: res } = await axios.get(maintenanceApiEndPoint);
    const res1 =  res.map(async (element) => {
      const { data: avalibleListOfElement } = await axios.get(avalibleEmployeeByMidApiEndPoint + element.mid)
      const avalibleListOfElementAccount = avalibleListOfElement.map(({ eid, firstName, lastName }) => ({ eid, firstName, lastName }))
      const avalibleListOfElementEid = avalibleListOfElement.map(ele=>ele.eid)
      return({
      ...element,
      avalibleList: avalibleListOfElementAccount,
    })});
    const res2 = await Promise.all(res1)
    setMaintenance(res2)
  };

  //
  const getAccountByEid = (id)=>{
    const employee = employees.find((emp)=>{
      return(emp.eid===id)
    })
    return employee ? employee.account : ''
  }

  let maintenanceTbody = maintenance.map((ele) => {
    return (<tr className="hover" key={ele.mid}>
      <td>{ele.mid}</td>
      <td>{ele.mcategory}</td>
      <td>{ele.pid ? getTheProductName(ele.pid) : ''}</td>
      <td>{ele.cid ? getTheCusName(ele.cid) : ''}</td>
        <td> {ele.appointment === null ? "" : ele.appointment.substring(0, 10)} {ele.appointmenttime}</td>
        <td> {ele.eid ? getTheEmpName(ele.eid) : ''}</td>
      <td>
        <select disabled={ele.status === 'closed' ? true : false}
          onChange={() => handleChange(ele)}
          className="select select-bordered w-full max-w-xs"
          value={selectedValue}
        >
          <option value="default" disabled selected>
            {ele.status !== 'closed' ? ele.status !== 'assigned' ? "assign" : "modify" : "closed"}
          </option>
            {ele.avalibleList.map((ele_ele)=> <option value={ele_ele.eid} key={ele_ele.eid}>{ele_ele.firstName + ' ' + ele_ele.lastName}</option> )}
        </select>
      </td>
    </tr>)
  })














	// 取得待主管分派的任務數量
	React.useEffect(() => {
		const getTasksNumber = async () => {
			const { data: countWaitingAssign } = await axios.get(EMP_API_BASE_URL + "task/waiting_assign");
			//console.log(countWaitingAssign.waitingTotal);
			setWaitforassign(countWaitingAssign);
		};
		getTasksNumber();
	}, []);

	// 取得所有維修申請資料
	React.useEffect(() => {
		const getWaitingMtns = async () => {
			const { data: mtnlist } = await axios.get(EMP_API_BASE_URL + "task/mtnlist"); //用了await，所在的函式就要用async
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
		if(elist.length === 0) return ''
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
		if(clist.length === 0) return ''
        const customer = clist.find((cus) => {
            return (cus.customer_id === cid);
        });
        return (customer.name);
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
		if(plist.length === 0) return ''
        const product = plist.find((pro) => {
            return (pro.productId === pid);
        });
        return (product.productModel);
    }


	return (
		<>
			<div className="container mx-auto px-14 py-16 flex justify-between">
				<Sidebar targetData={maintenance}/>
				<div className="flex-wrap justify-center w-full w-4/5">
					<div className="flex justify-between">
						<p className="text-2xl my-6">目前共有 {maintenance.filter((ele)=>ele.status==='waiting').length} 件預約保養任務待分派</p>
					</div>
					<div className="overflow-x-auto flex">
						<table className="table w-full w-4/5">
							<thead>
								<tr>
									<th>ID</th>
									<th>Maintenance and Repair Category</th>
									<th>Product</th>
									<th>Customer</th>
									<th>Reservation Time</th>
									<th>Employee</th>
									<th>Assign</th>
								</tr>
							</thead>
							<tbody>
								{maintenanceTbody}
							</tbody>
						</table>
					</div>
				</div>
			</div>
		</>

	);
}
