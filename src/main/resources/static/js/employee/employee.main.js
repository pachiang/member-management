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

	// 取得員工銷售資料
	React.useEffect(() => {
		const getSales = async () => {
			const { data: sales } = await axios.get(EMP_API_BASE_URL + "sales");
			setSalesData(sales);
			console.log(sales)
		};
		getSales();
	}, []);

	//ChartJS.register(LineController, LineElement, PointElement, LinearScale, Title);
	//const { Bar } = ReactChartJS2;

	//let myChart = null;
	// setup 
	//myChart.destroy();
	//if (myChart != null) { myChart.destroy(); }

document.addEventListener("DOMContentLoaded", function () {

    const ctx = document.getElementById("myChart");

    const myChart = new Chart(ctx, {
        type: "bar",
        data: {
            labels: ["Red", "Blue", "Yellow", "Green", "Purple", "Orange"],
            datasets: [
                {
                    label: "# of Votes",
                    data: [12, 19, 3, 5, 2, 3],
                    backgroundColor: [
                        "rgba(255, 99, 132, 0.2)",
                        "rgba(54, 162, 235, 0.2)",
                        "rgba(255, 206, 86, 0.2)",
                        "rgba(75, 192, 192, 0.2)",
                        "rgba(153, 102, 255, 0.2)",
                        "rgba(255, 159, 64, 0.2)",
                    ],
                    borderColor: [
                        "rgba(255, 99, 132, 1)",
                        "rgba(54, 162, 235, 1)",
                        "rgba(255, 206, 86, 1)",
                        "rgba(75, 192, 192, 1)",
                        "rgba(153, 102, 255, 1)",
                        "rgba(255, 159, 64, 1)",
                    ],
                    borderWidth: 1,
                },
            ],
        },
        options: {
            scales: {
                y: {
                    beginAtZero: true,
                },
            },
        },
    });
});

	return (
		<>
			<div className="container mx-auto px-14 py-16 flex justify-between">
				<Sidebar />
				<div className="flex-wrap justify-center w-full w-4/5">
					<div className="flex justify-between">
						<h3 className="text-3xl my-6 text-slate-500"><b>Hello! {myinfo.firstName}</b></h3>
					</div>
					<div className="overflow-x-auto flex">

						<div className="w-1/2" id="chartDiv"><canvas id="myChart" width="800" height="600"></canvas></div>
					</div>
				</div>
			</div>
		</>
	);





}
