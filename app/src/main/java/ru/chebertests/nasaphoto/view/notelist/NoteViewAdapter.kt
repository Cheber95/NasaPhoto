package ru.chebertests.nasaphoto.view.notelist

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import ru.chebertests.nasaphoto.R
import ru.chebertests.nasaphoto.model.notes.Notes

private const val TYPE_NOTE = 0
private const val TYPE_TODO = 1

class NoteViewAdapter(
    private var data: List<Notes>
) : RecyclerView.Adapter<NoteViewAdapter.NoteViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NoteViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            TYPE_NOTE -> {
                NoteViewHolder(inflater.inflate(R.layout.card_item_note, parent, false) as View)
            }
            TYPE_TODO -> {
                NoteViewHolder(inflater.inflate(R.layout.card_item_todo, parent, false) as View)
            }
        }
    }

    override fun onBindViewHolder(holder: NoteViewHolder, position: Int) {

    }

    override fun getItemCount(): Int {

    }

    inner class NoteViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        fun bind() {

        }
    }

}