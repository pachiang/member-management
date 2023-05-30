function CheckInOut() {
	const checksEndpointUrl = 'http://localhost:8080/tofu/employee/checks';
	const todayCheckEndpointUrl = 'http://localhost:8080/tofu/employee/todayscheck';
	const checkInEndPoint = 'http://localhost:8080/tofu/employee/check';
	const checkOutEndPoint = 'http://localhost:8080/tofu/employee/check';

	//網頁上的時鐘
	const [time, setTime] = React.useState(new Date());
	React.useEffect(() => {
		let timer = setInterval(() => setTime(new Date()), 1000);
		return function cleanup() {
			clearInterval(timer);
		};
	}, []);


	const isToday = (someDate) => {
		const today = new Date()
		return someDate.getDate() == today.getDate() &&
			someDate.getMonth() == today.getMonth() &&
			someDate.getFullYear() == today.getFullYear()
	}
	// const formatDate = (someDate) => {
	//   let tempArray = someDate.toISOString().split('T')
	//   return tempArray[0] + ' ' + tempArray[1].substring(0, 8)
	// }

	// 把js Date轉成目標格式字串所需的兩個函式
	const pad = (v) => {
		return (v < 10) ? '0' + v : v
	}
	const getDateString = (d) => {
		let year = d.getFullYear()
		let month = pad(d.getMonth() + 1)
		let day = pad(d.getDate())
		let hour = pad(d.getHours())
		let min = pad(d.getMinutes())
		let sec = pad(d.getSeconds())
		return year + "-" + month + "-" + day + " " + hour + ":" + min + ":" + sec
	}

	const [checkInfo, setCheckInfo] = React.useState([]); // 儲存該使用者所有打卡資料
	// const [todayCheckInfo, setTodayCheckInfo] = useState([]); // 儲存該使用者今日打卡資料，用來決定按鈕是否禁用
	const [todayCheckIn, setTodayCheckIn] = React.useState(false);
	const [todayCheckOut, setTodayCheckOut] = React.useState(false);
	const [todayCheckInTime, setTodayCheckInTime] = React.useState(null);
	const [todayCheckOutTime, setTodayCheckOutTime] = React.useState(null);
	const [error, setError] = React.useState(null);
	
	// const [date, setDate] = React.useState(new Date());

	// 進網頁時先取得該user所有打卡資料；存入checkInfo。判斷是否有今日checkIn與checkOut，有的話改動todayCheckIn與todayCheckOut，禁用相應按鈕。
	React.useEffect(() => {
		const fetchData = async () => {
			let date = new Date()
			console.log(getDateString(date))

			try {
				const checkResponse = await axios.get(checksEndpointUrl);
				setCheckInfo(checkResponse.data.reverse());
				console.log(checkInfo);
			} catch (error) {
				setError(error);
			}
			try {
				const todayCheckResponse = await axios.get(todayCheckEndpointUrl);
				// setTodayCheckInfo(todayCheckResponse.data);

				// 在網頁剛開的情況下，依據todayCheckResponse的值判斷是否禁用打卡按鈕
				if (todayCheckResponse.data !== null && todayCheckResponse.data !== "") {
					setTodayCheckIn(true)
					setTodayCheckInTime(todayCheckResponse.data.checkInTime)
					if (todayCheckResponse.data.checkOutTime !== null && todayCheckResponse.data.checkOutTime !== undefined) {
						setTodayCheckOut(true)
						setTodayCheckOutTime(todayCheckResponse.data.checkOutTime)
					}
				}
			} catch (error) {
				setError(error);
			}
		}
		fetchData();
	}, [todayCheckIn, todayCheckOut]);

	let checkInfoTbody = checkInfo.map((checkInfoEle) => {
		return (<tr className="hover" key={checkInfoEle.cid}>
			<td>{checkInfoEle.cid}</td>
			<td>{checkInfoEle.checkInTime === null ? "" : checkInfoEle.checkInTime.substring(0, 10)}</td>
			<td>{checkInfoEle.checkInTime === null ? "" : checkInfoEle.checkInTime.substring(11)}</td>
			<td>{checkInfoEle.checkOutTime === null ? "" : checkInfoEle.checkOutTime.substring(11)}</td>
		</tr>)
	})

	const handleCheckInClick = async () => {
		const tempDate = new Date()
		await axios.post(checkInEndPoint, {
			"checkInTime": getDateString(tempDate)
		});
		// 改變todayCheckIn，禁用簽到按鈕
		setTodayCheckIn(true)
	};

	const handleCheckOutClick = async () => {
		const tempDate = new Date()
		await axios.put(checkOutEndPoint, {
			"checkOutTime": getDateString(tempDate)
		});
		// 改變todayCheckIn，禁用簽到按鈕
		setTodayCheckOut(true)
	};

	if (error) {
		return <div>Error: {error.message}</div>;
	} else if (!checkInfo) {
		return <div>Loading...</div>;
	} else {
		return (
			<>
				<div className="container mx-auto px-14 py-16 flex justify-between">
					<Sidebar />
					<div className="flex-wrap justify-center w-full w-4/5">
						<div className="flex justify-between">
							<p className="text-2xl my-6">Check In and Check Out</p>
							<p className="text-md my-8">現在時間 {time.toLocaleString()}</p>
						</div>

						<div className="overflow-x-auto flex">
							<button
								onClick={() => handleCheckInClick()}
								disabled={todayCheckIn}
								className="h-16 mr-4 btn bg-emerald-500 hover:bg-emerald-600 border-0 rounded-xl"
							>
								<div className="flex-wrap">
									<div className="w-32 block"><svg xmlns="http://www.w3.org/2000/svg" className="inline-block w-5 h-5 mr-2" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"><path d="M15 3h4a2 2 0 0 1 2 2v14a2 2 0 0 1-2 2h-4M10 17l5-5-5-5M13.8 12H3" /></svg><span className="align-middle text-lg">簽到</span></div>
									<div className="block">{todayCheckInTime === null | todayCheckInTime === "" ? " " : todayCheckInTime.substring(11)}</div>
								</div>
							</button>
							<button
								onClick={() => handleCheckOutClick()}
								disabled={todayCheckOut}
								className="h-16 mr-4 btn bg-emerald-500 hover:bg-emerald-600 border-0 rounded-xl"
							>
								<div className="flex-wrap">
									<div className="w-32 block"><svg xmlns="http://www.w3.org/2000/svg" className="inline-block w-5 h-5 mr-2" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"><path d="M10 3H6a2 2 0 0 0-2 2v14c0 1.1.9 2 2 2h4M16 17l5-5-5-5M19.8 12H9" /></svg><span className="align-middle text-lg">簽退</span></div>
									<div className="block">{todayCheckOutTime === null | todayCheckOutTime === "" ? " " : todayCheckOutTime.substring(11)}</div>
								</div>
							</button>
						</div>

						<div>
							<table className="table w-full w-4/5 mt-10">
								<thead className="text-left">
									<tr>
										<th>ID</th>
										<th>Date</th>
										<th>CheckInTime</th>
										<th>CheckOutTime</th>
									</tr>
								</thead>
								<tbody>{checkInfoTbody}</tbody>
							</table>
						</div>

					</div>
				</div>
			</>)
	}
}