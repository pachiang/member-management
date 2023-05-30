function Pagination({
	postsPerPage,
	totalPosts,
	paginateFront,
	paginateBack,
	paginate,
	currentPage,
}) {
	const pageNumbers = [];

	for (let i = 1; i <= Math.ceil(totalPosts / postsPerPage); i++) {
		pageNumbers.push(i);
	}

	return (
		<>
			<div>
				<p className='text-sm text-gray-700 mt-4 mb-4'>
					顯示
					<span className='font-medium'> {currentPage * postsPerPage - 10} </span>
					至
					<span className='font-medium'> {currentPage * postsPerPage} </span>
					筆資料，共有
					<span className='font-medium'> {totalPosts} </span>
					筆資料
				</p>
			</div>
			<nav className='block'></nav>
			<div>
				<nav
					className='relative z-0 inline-flex rounded-md shadow-sm -space-x-px'
					aria-label='Pagination'
				>
					<a
						onClick={() => {
							paginateBack();
						}}
						href='#'
						className='relative inline-flex items-center px-2 py-2 rounded-l-md border border-gray-300 bg-white text-sm font-medium text-gray-500 hover:bg-gray-50'
					>
						<span><svg xmlns="http://www.w3.org/2000/svg" className="w-5 h-5" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M15 18l-6-6 6-6" /></svg></span>
					</a>
					<ul className='flex pl-0 rounded list-none flex-wrap'>
						<li>
							{pageNumbers.map((number) => (
								<a
									onClick={() => {
										paginate(number);
									}}
									href='#'
									className={
										currentPage === number
											? "bg-blue border-red-300 text-red-500 hover:bg-blue-200 relative inline-flex items-center px-4 py-2 border text-sm font-medium"
											: "bg-white border-gray-300 text-gray-500 hover:bg-blue-200 relative inline-flex items-center px-4 py-2 border text-sm font-medium"
									}
								>
									{number}
								</a>
							))}
						</li>
					</ul>

					<a
						onClick={() => {
							paginateFront();
						}}
						href='#'
						className='relative inline-flex items-center px-2 py-2 rounded-r-md border border-gray-300 bg-white text-sm font-medium text-gray-500 hover:bg-gray-50'
					>
						<span><svg xmlns="http://www.w3.org/2000/svg" width="24" height="24" viewBox="0 0 24 24" fill="none" stroke="currentColor" stroke-width="2" stroke-linecap="round" stroke-linejoin="round"><path d="M9 18l6-6-6-6" /></svg></span>
					</a>
				</nav>
			</div>
		</>
	);
}

ReactDOM.render(<Pagination />, document.getElementById('root'));