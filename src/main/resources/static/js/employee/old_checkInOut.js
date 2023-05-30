function CheckInOut() {
	const [checkIn, setCheckIn] = React.useState("");
	const [checkOut, setCheckOut] = React.useState("");

	//網頁上的時鐘
	const [date, setDate] = React.useState(new Date());
	React.useEffect(() => {
		let timer = setInterval(() => setDate(new Date()), 1000);
		return function cleanup() {
			clearInterval(timer);
		};
	}, []);

	const todosApiEndPoint = "https://jsonplaceholder.typicode.com/todos";
	//   useEffect(() => {
	//     const getCheckInOut = async () => {
	//       const { data: res } = await axios.get(todosApiEndPoint);
	//       setCheckIn(res[0].title);
	//       setCheckOut(res[1].title);
	//     };
	//     getCheckInOut();
	//   }, []);

	const handleCheckInClick = () => {
		setCheckIn(date.toLocaleTimeString());
	};
	const handleCheckOutClick = () => {
		setCheckOut(date.toLocaleTimeString());
	};
	return (
		<>
			<div className="container mx-auto px-14 py-16 flex justify-between">
				<Sidebar />
				<div className="flex-wrap justify-center w-full w-4/5">
					<div className="flex justify-between">
						<p className="text-2xl my-6">Check In and Check Out</p>
						<p className="text-md my-8">現在時間 {date.toLocaleString()}</p>
					</div>
					<div className="overflow-x-auto flex">
						<button
							onClick={() => handleCheckInClick()}
							disabled={checkIn !== "" ? "true" : ""}
							className="h-16 mr-4 btn bg-emerald-500 hover:bg-emerald-600 border-0 rounded-xl"
						>
							<div className="flex-wrap">
							<div className="w-32 block"><svg xmlns="http://www.w3.org/2000/svg" className="inline-block w-5 h-5 mr-2" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"><path d="M15 3h4a2 2 0 0 1 2 2v14a2 2 0 0 1-2 2h-4M10 17l5-5-5-5M13.8 12H3"/></svg><span className="align-middle text-lg">簽到</span></div>
							<div className="block">{checkIn}</div>
							</div>
						</button>
						<button
							onClick={() => handleCheckOutClick()}
							disabled={checkOut !== "" || checkIn == "" ? "true" : ""}
							className="h-16 mr-4 btn bg-emerald-500 hover:bg-emerald-600 border-0 rounded-xl"
						>
							<div className="flex-wrap">
							<div className="w-32 block"><svg xmlns="http://www.w3.org/2000/svg" className="inline-block w-5 h-5 mr-2" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M10 3H6a2 2 0 0 0-2 2v14c0 1.1.9 2 2 2h4M16 17l5-5-5-5M19.8 12H9"/></svg><span className="align-middle text-lg">簽退</span></div>
							<div className="block">{checkOut}</div>
							</div>
						</button>

					</div>
				</div>
			</div>
		</>
	);
}
