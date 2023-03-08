package com.trdz.astro_days.view.segment_book

interface WindowNoteOnClick {
	fun onItemClick(data: DataLine, position: Int)
	fun onItemClickLong(data: DataLine, position: Int)
	fun onItemMove(fromPosition: Int, toPosition: Int)
	fun onItemRemove(position: Int)
}
