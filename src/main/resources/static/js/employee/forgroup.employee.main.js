function MainPage() {

	const myinfo_API_BASE_URL = "http://localhost:8080/tofu/info/employee/";
	const EMP_API_BASE_URL = "http://localhost:8080/tofu/employee/";

	const [myinfo, setMyinfo] = React.useState([]);
	const [salesdata, setSalesData] = React.useState([]);

	// 取得使用者的資料
	React.useEffect(() => {
		const getLoggedinInfo = async () => {
			const { data: myinfo } = await axios.get(EMP_API_BASE_URL + "leave/my_info");
			setMyinfo(myinfo);
		};
		getLoggedinInfo();
	}, []);

	return (
		<>
			<div className="container mx-auto px-14 py-16 flex justify-between">
				<Sidebar />
				<div className="flex-wrap justify-center w-full w-4/5">
					<div className="flex justify-between">
						<h3 className="text-3xl my-6 text-slate-500"><b>Hello! {myinfo.firstName}</b></h3>
					</div>
					<div className="overflow-x-auto flex">
						
					</div>
				</div>
			</div>
		</>
	);





}
