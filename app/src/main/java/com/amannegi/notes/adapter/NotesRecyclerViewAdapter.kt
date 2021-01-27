package com.amannegi.notes.adapter

import android.view.*
import androidx.recyclerview.widget.RecyclerView
import com.amannegi.notes.database.Note
import com.amannegi.notes.databinding.NoteRowLayoutBinding

class NotesRecyclerViewAdapter(
    private val notes: List<Note>,
    private val listener: RecyclerViewItemClickListener
) : RecyclerView.Adapter<NotesRecyclerViewAdapter.NoteViewHolder>() {


    inner class NoteViewHolder(private val binding: NoteRowLayoutBinding) :
        RecyclerView.ViewHolder(binding.root), View.OnCreateContextMenuListener,
        MenuItem.OnMenuItemClickListener, View.OnClickListener {

        private val ID_CONTEXT_MENU_EDIT = 101
        private val ID_CONTEXT_MENU_DELETE = 102

        init {
            // registering the view for click events
            binding.root.setOnCreateContextMenuListener(this)
            binding.root.setOnClickListener(this)
        }

        fun bind(note: Note) {
            binding.title.text = note.title
            binding.body.text = note.body
            binding.timestamp.text = note.timestamp
        }

        override fun onCreateContextMenu(
            menu: ContextMenu?,
            v: View?,
            menuInfo: ContextMenu.ContextMenuInfo?
        ) {
            // creating context menu
            menu?.setHeaderTitle("Select action")
            val menuEdit = menu?.add(Menu.NONE, ID_CONTEXT_MENU_EDIT, 1, "Edit")
            val menuDelete = menu?.add(Menu.NONE, ID_CONTEXT_MENU_DELETE, 2, "Delete")
            menuEdit?.setOnMenuItemClickListener(this)
            menuDelete?.setOnMenuItemClickListener(this)
        }

        override fun onMenuItemClick(item: MenuItem?): Boolean {
            val position = adapterPosition
            // check for whether the item is not clicked while delete animation
            if (position != RecyclerView.NO_POSITION) {
                // attaching click handler to respective context menu from custom click listener
                when (item?.itemId) {
                    ID_CONTEXT_MENU_EDIT -> {
                        listener.onEditMenuClick(position)
                        return true
                    }
                    ID_CONTEXT_MENU_DELETE -> {
                        listener.onDeleteMenuClick(position)
                        return true
                    }
                }

            }
            return false
        }

        override fun onClick(v: View?) {
            // to add ripple effect on cardview
            // removing this removes the ripple from card
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val binding =
            NoteRowLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return NoteViewHolder(binding)
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {
        val note = notes[position]
        holder.bind(note)
    }

    override fun getItemCount(): Int = notes.size
}