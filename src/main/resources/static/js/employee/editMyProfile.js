function EditMyProfile() {

	const myinfo_API_BASE_URL = "http://localhost:8080/tofu/info/employee/";
	const EMP_API_BASE_URL = "http://localhost:8080/tofu/employee/";

	const [myinfo, setMyinfo] = React.useState([]);
	
	//此頁套用cupcake, emerald的theme
	React.useEffect(() => {
		document.querySelector("html").setAttribute("data-theme", "emerald");
	}, []);

	// 取得使用者的資料
	React.useEffect(() => {
		const getLoggedinInfo = async () => {
			const { data: myinfo } = await axios.get(EMP_API_BASE_URL + "leave/my_info");
			setMyinfo(myinfo);
		};
		getLoggedinInfo();
	}, []);

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

	let modifiedUserInfo = myinfo
	const updateUser = async (e) => {
		e.preventDefault();
		let filteredUser = Object.fromEntries(
			Object.entries(modifiedUserInfo).filter(([key, value]) => key !== "age")
		);
		filteredUser = Object.fromEntries(
			Object.entries(modifiedUserInfo).filter(([key, value]) => key !== "checks")
		);
		const response = await fetch(EMP_API_BASE_URL + "edit/" + myinfo.eid, {
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
		const _myinfo = await response.json();
		window.location.href = "http://localhost:8080/tofu/employee/edit_my_profile";
		// setResponseUser(_user);
		// reset(e);
		// document.getElementById('my-modal').
		// window.opener.location.reload();
	};
	
	const handleChange = (event) => {
		const value = event.target.value;
		// setModifiedUserInfo({ ...user, [event.target.name]: value });
		modifiedUserInfo[event.target.name] = value
		// console.log(modifiedUserInfo)
	};


	//function getPhoto(pid) {
	//let photolist = [];
	//{ plist.map((product) => (photolist.push("data:image/png;base64," + product.photos[0].photo))) };
	//let arraynumber = pid - 1;
	//let photo = photolist[arraynumber];
	//return photo;
	//}

	return (
		<>
			<div className="container mx-auto px-14 py-16 flex justify-between">
				<Sidebar />
				<div className="flex-wrap justify-center w-full w-4/5">
					<div className="flex justify-between">
						<p className="text-2xl my-6">修改個人資料</p>
					</div>
					<div className="overflow-x-auto">

						<div className="container mx-auto flex justify-center">
							<form>
								<div className="space-y-12">
									{/* 分隔線 */}
									<div className="border-b border-gray-900/10 pb-12" hidden></div>
									{/* 分隔線 */}
									{/* <div className="border-b border-gray-900/10 pb-12"> */}
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
														defaultValue={myinfo.eid}
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
														disabled="true"
														type="text"
														name="firstName"
														id="firstName"
														defaultValue={myinfo.firstName}
														key={myinfo.firstName}
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
														disabled="true"
														type="text"
														name="lastName"
														id="lastName"
														defaultValue={myinfo.lastName}
														key={myinfo.lastName}
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
														disabled="true"
														type="text"
														name="account"
														id="account"
														defaultValue={myinfo.account}
														key={myinfo.account}
														onChange={(e) => handleChange(e)}
														autoComplete="account"
														className="block w-full rounded-md border-0 py-1.5 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
													/>
												</div>
											</div>

											{/* 密碼 */}
											<div className="mt-6 sm:col-span-2">
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
														defaultValue={myinfo.password}
														key={myinfo.password}
														onChange={(e) => handleChange(e)}
														autoComplete="password"
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
														defaultValue={myinfo.email}
														key={myinfo.email}
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
											<div className="mt-2 flex-wrap justify-center items-center gap-x-3 w-56">

												{changed === false ? (<img src={"http://localhost:8080/tofu/employee/getEmployeePhoto/" + myinfo.eid}></img>) : (<img src={image}></img>)}

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
														key={myinfo.photo}
														onChange={(e) => handleImageChange(e)}
													/>
												</label>
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
													defaultValue={myinfo.birthday}
													key={myinfo.birthday}
													onChange={(e) => handleChange(e)}
													className="block w-full rounded-md border-0 py-1.5 px-2 text-gray-900 shadow-sm ring-1 ring-inset ring-gray-300 placeholder:text-gray-400 focus:ring-2 focus:ring-inset focus:ring-indigo-600 sm:text-sm sm:leading-6"
												/>
											</div>
										</div>

										{/* 電話 */}
										<div className="sm:col-span-2">
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
													defaultValue={myinfo.phone}
													key={myinfo.phone}
													onChange={(e) => handleChange(e)}
													autoComplete="phone"
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
															defaultChecked={myinfo.gender === "Male"}
															key={myinfo.gender === "Male"}
															onChange={(e) => handleChange(e)}
															className="radio h-4 w-4 border-gray-300 text-indigo-600 focus:ring-indigo-600"
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
															defaultChecked={myinfo.gender === "Female"}
															key={myinfo.gender === "Female"}
															onChange={(e) => handleChange(e)}
															className="radio h-4 w-4 border-gray-300 text-indigo-600 focus:ring-indigo-600"
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
															defaultChecked={myinfo.gender === "Not-disclosed"}
															key={myinfo.gender === "Not-disclosed"}
															onChange={(e) => handleChange(e)}
															className="radio h-4 w-4 border-gray-300 text-indigo-600 focus:ring-indigo-600"
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


									</div>
									{/* </div> */}

									<div className="mt-10 grid grid-cols-1 gap-x-6 gap-y-8 sm:grid-cols-6">
										<div className="modal-action mr-16 justify-end">
											<a href="#" onClick={updateUser} className="btn btn-primary">
												Save
											</a>
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
					</div>
				</div>
			</div>
		</>

	);
}
