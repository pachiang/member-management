function EditEmployee({ userInfo }) {
	const USER_API_BASE_URL = "http://localhost:8080/tofu/employee";
	// console.log("userInfo  ", userInfo)
	const [user, setUser] = React.useState(userInfo[0])
	React.useEffect(() =>
		setUser(userInfo[0]), [userInfo])

	// 處理圖片預覽
	const [image, setImage] = React.useState(null);
	const imageInputRef = React.useRef(null);
	const [changed, setChanged] = React.useState(false);

	const handleImageChange = (e) => {
		setChanged(true);
		const uploadImage = e.target.files[0];
		setImage(URL.createObjectURL(uploadImage));
		
		//處理檔案上傳
		const reader = new FileReader();
		reader.readAsDataURL(uploadImage);
		reader.onloadend = () => {
			// 將圖片轉換為base64字串
			const base64String = reader.result.replace("data:", "").replace(/^.+,/, "");
			// 將字串存到修改的資料中
			modifiedUserInfo[e.target.name] = base64String;
		};
		console.log(uploadImage);
		console.log(JSON.stringify(modifiedUserInfo));
	};

	// let user = userInfo[0]
	// console.log("user  ", user)

	// const [user, setUser] = useState(userInfo[0])
	// let user = userInfo[0]
	// console.log("in modal  ", user)
	// userInfo[0].eid
	// console.log(typeof userInfo);
	// console.log(user)

	//  const [modifiedUserInfo, setModifiedUserInfo] = React.useState(user)
	let modifiedUserInfo = user

	// console.log(userInfo.userInfo[0].eid)
	// console.log(userInfo.eid)
	// return(<><h1>hello</h1></>);

	// const [gender, setGender] = useState("Not-disclosed");
	// const [isOpen, setIsOpen] = useState(false);
	// const [user, setUser] = useState({
	//   eid: "",
	//   firstName: "",
	//   lastName: "",
	//   account: "",
	//   password: "",
	//   gender: "",
	//   birthday: "",
	//   email: "",
	//   phone: "",
	//   department: "",
	//   position: "",
	//   hireDate: "",
	//   salary: "",
	//   enabled: "",
	//   authority: "",
	//   photo: "",
	// });
	// useEffect(() => {
	//   const fetchData = async () => {
	//     try {
	//       const response = await fetch(USER_API_BASE_URL + "/findemp/" + userId, {
	//         method: "GET",
	//         headers: {
	//           "Content-Type": "application/json",
	//         },
	//       });
	//       const _user = await response.json();
	//       setUser(_user);
	//       setIsOpen(true);
	//       setGender(user.gender);
	//       console.log(gender);
	//     } catch (error) {
	//       console.log(error);
	//     }
	//   };
	//   if (userId) {
	//     fetchData();
	//   }
	// }, [userId, gender]);

	// function closeModal() {
	//   setIsOpen(false);
	// }

	// function openModal() {
	//   setIsOpen(true);
	// }

	const handleChange = (event) => {
		const value = event.target.value;
		// setModifiedUserInfo({ ...user, [event.target.name]: value });
		modifiedUserInfo[event.target.name] = value
		// console.log(modifiedUserInfo)
	};

	// const reset = (e) => {
	//   e.preventDefault();
	//   setIsOpen(false);
	// };

	const updateUser = async (e) => {
		e.preventDefault();
		const filteredUser = Object.fromEntries(
			Object.entries(modifiedUserInfo).filter(([key, value]) => key !== "age")
		);
		const response = await fetch(USER_API_BASE_URL + "/edit/" + user.eid, {
			method: "PUT",
			headers: {
				"Content-Type": "application/json",
			},
			body: JSON.stringify(filteredUser),
		});
		if (!response.ok) {
			console.log(JSON.stringify(filteredUser));
			throw new Error("Something went wrong");
		}
		const _user = await response.json();
		window.location.href = "http://localhost:8080/tofu/employee/all";
		// setResponseUser(_user);
		// reset(e);
		// document.getElementById('my-modal').
		// window.opener.location.reload();
	};

	return (
		<div className="modal" id="my-modal">
			<div className="modal-box max-w-none w-5/12" id="journal-scroll">
				<h3 className="font-bold text-lg" hidden>
					Congratulations random Internet user!
				</h3>
				{/* 表單放在這兒 */}
				<div className="container mx-auto px-14 py-16 flex justify-between">
					<form>
						<div className="space-y-12">
							{/* 分隔線 */}
							<div className="border-b border-gray-900/10 pb-12" hidden></div>
							{/* 分隔線 */}
							{/* <div className="border-b border-gray-900/10 pb-12"> */}
							<h2 className="text-xl font-semibold leading-7 text-gray-900">
								Profile
							</h2>
							<p className="mt-1 text-sm leading-6 text-gray-600" hidden>
								This information will be displayed publicly so be careful what
								you share.
							</p>

							<div className="mt-10 grid grid-cols-1 gap-x-6 gap-y-8 sm:grid-cols-5">
								<div className="mt-10 grid col-span-3">
									{/* ID */}
									<div className="sm:col-span-2 mr-6" hidden>
										<label
											htmlFor="eid"
											className="block text-sm font-medium leading-6 text-gray-900"
										>
											ID
										</label>
										<div className="mt-2">
											<input
												type="text"
												name="eid"
												id="eid"
												autoComplete="eid"
												defaultValue={user.eid}
												onChange={(e) => handleChange(e)}
												className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
											/>
										</div>
									</div>

									{/* 名字 */}
									<div className="sm:col-span-2 mr-6">
										<label
											htmlFor="firstName"
											className="block text-sm font-medium leading-6 text-gray-900"
										>
											First name
										</label>
										<div className="mt-2">
											<input
												type="text"
												name="firstName"
												id="firstName"
												defaultValue={user.firstName}
												key={user.firstName}
												onChange={(e) => handleChange(e)}
												autoComplete="firstName"
												className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
											/>
										</div>
									</div>

									{/* 姓氏 */}
									<div className="sm:col-span-2">
										<label
											htmlFor="lastName"
											className="block text-sm font-medium leading-6 text-gray-900"
										>
											Last name
										</label>
										<div className="mt-2">
											<input
												type="text"
												name="lastName"
												id="lastName"
												defaultValue={user.lastName}
												key={user.lastName}
												onChange={(e) => handleChange(e)}
												autoComplete="lastName"
												className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
											/>
										</div>
									</div>

									{/* 帳號 */}
									<div className="mt-6 sm:col-span-2 mr-6">
										<label
											htmlFor="account"
											className="block text-sm font-medium leading-6 text-gray-900"
										>
											Account
										</label>
										<div className="mt-2">
											<input
												type="text"
												name="account"
												id="account"
												defaultValue={user.account}
												key={user.account}
												onChange={(e) => handleChange(e)}
												autoComplete="account"
												className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
											/>
										</div>
									</div>

									{/* 密碼 */}
									<div className="mt-6 sm:col-span-2" hidden>
										<label
											htmlFor="password"
											className="block text-sm font-medium leading-6 text-gray-900"
										>
											Password
										</label>
										<div className="mt-2">
											<input
												type="password"
												name="password"
												id="password"
												defaultValue={user.password}
												key={user.password}
												onChange={(e) => handleChange(e)}
												autoComplete="password"
												className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
											/>
										</div>
									</div>

									{/* 電話 */}
									<div className="mt-6 sm:col-span-2">
										<label
											htmlFor="phone"
											className="block text-sm font-medium leading-6 text-gray-900"
										>
											Phone Number
										</label>
										<div className="mt-2">
											<input
												id="phone"
												name="phone"
												type="text"
												defaultValue={user.phone}
												key={user.phone}
												onChange={(e) => handleChange(e)}
												autoComplete="phone"
												className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
											/>
										</div>
									</div>
									
									{/* 電子郵件 */}
								<div className="col-span-4 mt-6">
									<label
										htmlFor="email"
										className="block text-sm font-medium leading-6 text-gray-900"
									>
										Email address
									</label>
									<div className="mt-2">
										<input
											id="email"
											name="email"
											type="email"
											autoComplete="email"
											defaultValue={user.email}
											key={user.email}
											onChange={(e) => handleChange(e)}
											className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
										/>
									</div>
								</div>
									
								</div>

								{/* 照片 */}
								<div className="mt-10 grid col-span-2">
									<label
										htmlFor="photoChange"
										className="block text-sm font-medium leading-6 text-gray-900"
									>
										Photo
									</label>
									<div className="mt-2 flex-wrap justify-center items-center gap-x-3">

										{changed === false ? (<img src={"http://localhost:8080/tofu/employee/getEmployeePhoto/" + user.eid}></img>) : (<img src={image}></img>)}

										{/* <svg
                      className="h-48 w-48 text-gray-300"
                      viewBox="0 0 24 24"
                      fill="currentColor"
                      aria-hidden="true"
                    >
                      <path
                        fillRule="evenodd"
                        d="M18.685 19.097A9.723 9.723 0 0021.75 12c0-5.385-4.365-9.75-9.75-9.75S2.25 6.615 2.25 12a9.723 9.723 0 003.065 7.097A9.716 9.716 0 0012 21.75a9.716 9.716 0 006.685-2.653zm-12.54-1.285A7.486 7.486 0 0112 15a7.486 7.486 0 015.855 2.812A8.224 8.224 0 0112 20.25a8.224 8.224 0 01-5.855-2.438zM15.75 9a3.75 3.75 0 11-7.5 0 3.75 3.75 0 017.5 0z"
                        clipRule="evenodd"
                      />
                    </svg> */}
										<button
											id="photoChange"
											type="button"
											onClick={() => imageInputRef.current.click()}
											className="rounded-md bg-white px-2.5 py-1.5 text-sm font-semibold text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 hover:bg-gray-50"
										>
											Change
										</button>
										<label htmlFor="photo" style={{ display: 'none' }}>
											<input
												id="photo"
												name="photo"
												type="file"
												ref={imageInputRef}
												key={user.photo}
												onChange={(e) => handleImageChange(e)}
											/>
										</label>
									</div>
								</div>

								

								{/* 部門 */}
								<div className="col-span-2">
									<label
										htmlFor="department"
										className="block text-sm font-medium leading-6 text-gray-900"
									>
										Department
									</label>
									<div className="mt-2">
										<input
											id="department"
											name="department"
											type="text"
											// autoComplete="department"
											defaultValue={user.department}
											key={user.department}
											onChange={(e) => handleChange(e)}
											className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
										/>
									</div>
								</div>

								{/* 職位 */}
								<div className="col-span-2">
									<label
										htmlFor="position"
										className="block text-sm font-medium leading-6 text-gray-900"
									>
										Position
									</label>
									<div className="mt-2">
										<input
											id="position"
											name="position"
											type="text"
											autoComplete="position"
											defaultValue={user.position}
											key={user.position}
											onChange={(e) => handleChange(e)}
											className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
										/>
									</div>
								</div>

								{/* 生日 */}
								<div className="col-span-2">
									<label
										htmlFor="birthday"
										className="block text-sm font-medium leading-6 text-gray-900"
									>
										Birthday
									</label>
									<div className="mt-2">
										<input
											id="birthday"
											name="birthday"
											type="date"
											autoComplete="birthday"
											defaultValue={user.birthday}
											key={user.birthday}
											onChange={(e) => handleChange(e)}
											className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
										/>
									</div>
								</div>

								{/* 到職日期 */}
								<div className="col-span-2">
									<label
										htmlFor="hireDate"
										className="block text-sm font-medium leading-6 text-gray-900"
									>
										Hire Date
									</label>
									<div className="mt-2">
										<input
											id="hireDate"
											name="hireDate"
											type="date"
											autoComplete="hireDate"
											defaultValue={user.hireDate}
											key={user.hireDate}
											onChange={(e) => handleChange(e)}
											className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
										/>
									</div>
								</div>

								{/* 性別 */}
								<div className="col-span-4 flex-wrap">
									<fieldset>
										<legend

											className="block text-sm font-medium leading-6 text-gray-900"
										>
											Gender
										</legend>
										<div className="mt-4">
											{/* 男性 */}
											<div className="mr-10 inline-flex items-center gap-x-3 inline-block">
												<input
													id="male"
													name="gender"
													type="radio"
													value="Male"
													defaultChecked={user.gender === "Male"}
													key={user.gender === "Male"}
													onChange={(e) => handleChange(e)}
													className="h-4 w-4 border-gray-300 text-indigo-600 focus:ring-indigo-600"
												/>
												<label
													htmlFor="male"
													className="inline-block text-sm font-medium leading-6 text-gray-900"
												>
													Male
												</label>
											</div>

											{/* 女性 */}
											<div className="mr-10 inline-flex items-center gap-x-3 inline-block">
												<input
													id="female"
													name="gender"
													type="radio"
													value="Female"
													defaultChecked={user.gender === "Female"}
													key={user.gender === "Female"}
													onChange={(e) => handleChange(e)}
													className="h-4 w-4 border-gray-300 text-indigo-600 focus:ring-indigo-600"
												/>
												<label
													htmlFor="female"
													className="inline-block text-sm font-medium leading-6 text-gray-900"
												>
													Female
												</label>
											</div>
											{/* 不願揭露 */}
											<div className="inline-flex items-center gap-x-3 inline-block">
												<input
													id="not-disclosed"
													name="gender"
													type="radio"
													value="Not-disclosed"
													defaultChecked={user.gender === "Not-disclosed"}
													key={user.gender === "Not-disclosed"}
													onChange={(e) => handleChange(e)}
													className="h-4 w-4 border-gray-300 text-indigo-600 focus:ring-indigo-600"
												/>
												<label
													htmlFor="not-disclosed"
													className="inline-block text-sm font-medium leading-6 text-gray-900"
												>
													Do not wish to disclose
												</label>
											</div>
										</div>
									</fieldset>
								</div>

								{/* 權限 */}
								<div className="col-span-2" hidden>
									<fieldset>
										<legend

											className="block text-sm font-medium leading-6 text-gray-900"
										>
											Authority
										</legend>
										<div className="mt-6 space-y-6">
											<div className="flex items-center gap-x-3">
												<input
													readOnly
													id="employee"
													name="authority"
													type="radio"
													value="ROLE_EMPLOYEE"
													defaultChecked={user.authority === "ROLE_EMPLOYEE"}
													key={user.authority === "ROLE_EMPLOYEE"}
													onChange={(e) => handleChange(e)}
													className="h-4 w-4 border-gray-300 text-indigo-600 focus:ring-indigo-600"
												/>
												<label
													htmlFor="employee"
													className="block text-sm font-medium leading-6 text-gray-900"
												>
													Employee
												</label>
											</div>

											<div className="flex items-center gap-x-3">
												<input
													id="manager"
													name="authority"
													type="radio"
													value="ROLE_MANAGER"
													defaultChecked={user.authority === "ROLE_MANAGER"}
													key={user.authority === "ROLE_MANAGER"}
													onChange={(e) => handleChange(e)}
													className="h-4 w-4 border-gray-300 text-indigo-600 focus:ring-indigo-600"
												/>
												<label
													htmlFor="manager"
													className="block text-sm font-medium leading-6 text-gray-900"
												>
													Manager
												</label>
											</div>
										</div>
									</fieldset>
								</div>
								{/* 薪資 */}
								<div className="col-span-2" hidden>
									<label
										htmlFor="salary"
										className="block text-sm font-medium leading-6 text-gray-900"
									>
										Salary
									</label>
									<div className="mt-2">
										<input
											id="salary"
											name="salary"
											type="salary"
											autoComplete="salary"
											className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
										/>
									</div>
								</div>
							</div>
							{/* </div> */}

							<div className="mt-10 grid grid-cols-1 gap-x-6 gap-y-8 sm:grid-cols-6">
								{/* 拖曳上傳照片 */}
								<div className="col-span-full" hidden>
									<legend

										className="block text-sm font-medium leading-6 text-gray-900"
									>
										Cover photo
									</legend>
									<div className="mt-2 flex justify-center rounded-lg border border-dashed border-gray-900/25 px-6 py-10">
										<div className="text-center">
											<svg
												className="mx-auto h-12 w-12 text-gray-300"
												viewBox="0 0 24 24"
												fill="currentColor"
												aria-hidden="true"
											>
												<path
													fillRule="evenodd"
													d="M1.5 6a2.25 2.25 0 012.25-2.25h16.5A2.25 2.25 0 0122.5 6v12a2.25 2.25 0 01-2.25 2.25H3.75A2.25 2.25 0 011.5 18V6zM3 16.06V18c0 .414.336.75.75.75h16.5A.75.75 0 0021 18v-1.94l-2.69-2.689a1.5 1.5 0 00-2.12 0l-.88.879.97.97a.75.75 0 11-1.06 1.06l-5.16-5.159a1.5 1.5 0 00-2.12 0L3 16.061zm10.125-7.81a1.125 1.125 0 112.25 0 1.125 1.125 0 01-2.25 0z"
													clipRule="evenodd"
												/>
											</svg>
											<div className="mt-4 flex text-sm leading-6 text-gray-600">
												<label
													htmlFor="file-upload"
													className="relative cursor-pointer rounded-md bg-white font-semibold text-indigo-600 focus-within:outline-none focus-within:ring-2 focus-within:ring-indigo-600 focus-within:ring-offset-2 hover:text-indigo-500"
												>
													<span>Upload a file</span>
													<input
														id="file-upload"
														name="file-upload"
														type="file"
														className="sr-only"
													/>
												</label>
												<p className="pl-1">or drag and drop</p>
											</div>
											<p className="text-xs leading-5 text-gray-600">
												PNG, JPG, GIF up to 10MB
											</p>
										</div>
									</div>
								</div>

								{/* 國家下拉選單 */}
								<div className="sm:col-span-3" hidden>
									<label
										htmlFor="country"
										className="block text-sm font-medium leading-6 text-gray-900"
									>
										Country
									</label>
									<div className="mt-2">
										<select
											id="country"
											name="country"
											autoComplete="country-name"
											className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:max-w-xs sm:text-sm sm:leading-6"
										>
											<option>United States</option>
											<option>Canada</option>
											<option>Mexico</option>
										</select>
									</div>
								</div>
							</div>
						</div>

						{/* <div className="mt-6 flex items-center justify-start gap-x-6">
              <button
                type="button"
                className="text-sm font-semibold leading-6 text-gray-900"
              >
                Cancel
              </button>
              <button
                type="submit"
                className="rounded-md bg-indigo-600 px-3 py-2 text-sm font-semibold text-white shadow-sm hover:bg-indigo-500 focus-visible:outline focus-visible:outline-2 focus-visible:outline-offset-2 focus-visible:outline-indigo-600"
              >
                Save
              </button>
            </div> */}
					</form>
					{/* 分隔線 */}
					<div className="border-b border-gray-900/10 pb-12"></div>
					{/* 分隔線 */}
				</div>
				{/* 表單結束 */}

				<div className="modal-action mr-16">
					<a href="#" className="btn btn-outline btn-primary">
						Cancel
					</a>
					<a href="#" onClick={updateUser} className="btn btn-primary">
						Save
					</a>
				</div>
			</div>
		</div>

		/* 修改按鈕 */
		/* <button
					onClick={updateUser}
					className="rounded text-white font-semibold bg-green-400 hover:bg-green-700 py-2 px-6"
				>
					Update
				</button> */
		/* 關閉視窗按鈕 */
		/* <button
					onClick={reset}
					className="rounded text-white font-semibold bg-red-400 hover:bg-red-700 py-2 px-6"
				>
					Close
				</button> */

	);
}
