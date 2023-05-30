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
			<div className="ml-2">
				<p className='text-sm text-gray-700 mt-4 mb-4 text-slate-400'>
					顯示
					<span className='font-medium'> {currentPage * postsPerPage - postsPerPage + 1} </span>
					至
					<span className='font-medium'> {currentPage === Math.max(...pageNumbers) ? totalPosts : currentPage * postsPerPage} </span>
					筆資料，共有
					<span className='font-medium'> {totalPosts} </span>
					筆資料
				</p>
			</div>
			<nav className='block'></nav>
			<div className="ml-2">
				<nav
					className='relative z-0 inline-flex rounded-md shadow-sm -space-x-px'
					aria-label='Pagination'
				>
					<div className="btn-group">
						<button className={currentPage === 1 ? "btn btn-sm btn-outline text-slate-400 border border-slate-300 btn-disabled":"btn border text-slate-400 border-slate-300 btn-outline btn-sm"} onClick={() => {
							paginateBack();
						}}><span><svg xmlns="http://www.w3.org/2000/svg" className="w-4 h-4" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"><path d="M15 18l-6-6 6-6" /></svg></span></button>
						{pageNumbers.map((number) => (
							<button onClick={() => {
								paginate(number);
							}} className={currentPage === number ? "btn btn-sm border btn-outline border-slate-300 text-slate-400 btn-active" : "btn border-slate-300 border text-slate-400 btn-outline btn-sm"}>{number}</button>
						))}
						<button onClick={() => {
							paginateFront();
						}} className={currentPage === Math.max(...pageNumbers) ? "btn border btn-sm btn-outline border-slate-300 text-slate-400 btn-disabled":"btn border-slate-300 text-slate-400 border btn-outline btn-sm"}>
							<span><svg xmlns="http://www.w3.org/2000/svg" className="w-4 h-4" viewBox="0 0 24 24" fill="none" stroke="currentColor" strokeWidth="2" strokeLinecap="round" strokeLinejoin="round"><path d="M9 18l6-6-6-6" /></svg></span>
						</button>

					</div>



				</nav>
			</div>
		</>
	);
}

ReactDOM.render(<Pagination />, document.getElementById('root'));