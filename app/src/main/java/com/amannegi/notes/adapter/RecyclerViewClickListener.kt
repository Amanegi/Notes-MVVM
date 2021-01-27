package com.amannegi.notes.adapter

interface RecyclerViewItemClickListener {
    // custom interface to handle context menu clicks on recyclerview item

    fun onEditMenuClick(position: Int)

    fun onDeleteMenuClick(position: Int)

}