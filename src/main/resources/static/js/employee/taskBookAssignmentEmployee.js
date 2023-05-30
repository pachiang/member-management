function TaskBookAssignmentEmployee() {
	
	const EMP_API_BASE_URL = "http://localhost:8080/tofu/employee/";
	const [waittodo, setWaittodo] = React.useState([]);
	const [bookdata, setBookdata] = React.useState([]);
	const [elist, setElist] = React.useState([]);
	const [clist, setClist] = React.useState([]);
	const [plist, setPlist] = React.useState([]);
    const [dateChanged, setDateChanged] = React.useState("");
    const [thisModalId, setThisModalId] = React.useState(-1);

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
		if(clist.length === 0) return ''
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
		if(plist.length === 0) return ''
        const product = plist.find((pro) => {
            return (pro.productId === pid);
        });
        return (product.productModel);
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

    // 按下開啟modal的按鈕時要做的事： 1.設定此modal對應的book_id; 2.重設時間 3.把modal開啟
    const openModal = (book) => {
        setThisModalId(book.book_id)
        setDateChanged("")
        document.getElementById('my-modal').checked = true;
    }
	React.useEffect(()=>{
	    console.log(thisModalId)
	},[thisModalId])

	// modal中的修改日期input值改變時
	const handleChange = (e) => {
        setDateChanged(e.target.value)
	};
	React.useEffect(()=>{
	    console.log(dateChanged)
	},[dateChanged])

	// 按下送出修改日期結果按鈕時
	const handleSubmit = async () => {
	    // 送出修改請求
	    if(dateChanged!=="")  await axios.put(EMP_API_BASE_URL + "task/management_book/edit/" + thisModalId, {'book_date': dateChanged});

        // 拿新資料並重新渲染
        const { data: booklist } = await axios.get(EMP_API_BASE_URL + "task/book_todolist");
        setBookdata(booklist.reverse());

        // 關閉modal
        document.getElementById('my-modal').checked = false;
	};


	return (
		<>
			<div className="container mx-auto px-14 py-16 flex justify-between">
				<Sidebar targetData={bookdata}/>
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
										<td> {book.f_product_id ? (<img className="inline-block object-scale-down w-12 mr-2" src={"http://localhost:8080/tofu/employee/task/getProductPhoto/" + book.f_product_id} />) : null } {book.f_product_id ? getTheProductName(book.f_product_id) : ''} </td>
										<td> {book.f_customer_id ? getTheCusName(book.f_customer_id): ''} {book.f_customer_id ? (
											<div className="dropdown dropdown-top dropdown-hover">
												<label tabIndex={0}><svg xmlns="http://www.w3.org/2000/svg" className="w-3 h-3 mx-2" viewBox="0 0 24 24" fill="gray" stroke="gray" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"><g fill="none" fill-rule="evenodd"><path d="M18 14v5a2 2 0 0 1-2 2H5a2 2 0 0 1-2-2V8c0-1.1.9-2 2-2h5M15 3h6v6M10 14L20.2 3.8" /></g></svg></label>
												<div tabIndex={0} className="dropdown-content p-2 shadow bg-sky-50 rounded-box h-24 flex items-center pr-8"><div className="avatar">
													<div className="w-16 rounded-full mx-4"><img src={"http://localhost:8080/tofu/downloadPhoto/" + book.f_customer_id} /></div></div>
													<div>
														<strong className="block text-lg">{book.f_customer_id ? getTheCusName(book.f_customer_id) : ''}</strong>
														<span className="block text-slate-400 text-sm mt-1">{book.f_customer_id ? getTheCusPhone(book.f_customer_id) : ''}</span>
														<span className="block text-slate-400 text-sm">{book.f_customer_id ? getTheCusEmail(book.f_customer_id) : ''}</span>
													</div></div>
											</div>) : null}</td>
										<td> {book.book_date} {book.book_date ? (<div className="inline-block"><button onClick={() => openModal(book)} className="btn btn-ghost btn-xs"><svg xmlns="http://www.w3.org/2000/svg" className="w-3 h-3 mx-2"  viewBox="0 0 24 24" fill="none" stroke="gray" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"><path d="M20 14.66V20a2 2 0 0 1-2 2H4a2 2 0 0 1-2-2V6a2 2 0 0 1 2-2h5.34"></path><polygon points="18 2 22 6 12 16 8 16 8 12 18 2"></polygon></svg></button></div>) : null}</td>
										<td>
											<button onClick={() => handleComplete(book)} className="btn btn-outline">
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

			<input type="checkbox" id="my-modal" className="modal-toggle" />
            <label htmlFor="my-modal" className="modal cursor-pointer">
              <label className="modal-box relative" htmlFor="">
                <label htmlFor="my-modal-3" className="btn btn-sm btn-circle absolute right-2 top-2">✕</label>
                <h3 className="text-lg font-bold">更改日期</h3>
                <div className="mr-6">
                    <div className="mt-2">
                        <input
                            name="beginDate"
                            type="date"
                            required="required"
                            onChange={(e) => handleChange(e)}
                            value={dateChanged}
                            className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
                        />
                    </div>
                </div>
                <p className="py-4">確認您要更改的日期</p>
                <button onClick={handleSubmit} className="btn btn-md">
                    Save
                </button>
              </label>
            </label>
		</>
	);
}
