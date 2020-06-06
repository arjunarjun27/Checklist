package com.arjunchecklist

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.recyclerview_item.view.*

class CheckListAdapter internal constructor(
    context: Context?, val onItemClickListener: OnItemClickListener
) : RecyclerView.Adapter<CheckListAdapter.WordViewHolder>() {

    private val inflater: LayoutInflater = LayoutInflater.from(context)
    private var words = emptyList<CheckList>() // Cached copy of words


    inner class WordViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val title = itemView.textView
        val time = itemView.time;
        val date = itemView.date;
        fun bind(current: CheckList, onItemClickListener: CheckListAdapter.OnItemClickListener) {
            title.text = current.title;
            time.text = current.time;
            date.text = current.date;
            itemView.imageViewDelete.setOnClickListener {
                onItemClickListener.onDeleteItemclicked(current);
            }
            itemView.imageViewEdit.setOnClickListener {
                onItemClickListener.onEditItemclicked(current)
            }
        }

        val wordItemView: TextView = itemView.findViewById(R.id.textView)
        val worddate: TextView = itemView.findViewById(R.id.date)
        val wordtime: TextView = itemView.findViewById(R.id.time)

        val imageViewDelete: ImageView = itemView.findViewById(R.id.imageViewDelete)
        val imageViewEdit: ImageView = itemView.findViewById(R.id.imageViewEdit)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): WordViewHolder {
        val itemView = inflater.inflate(R.layout.recyclerview_item, parent, false)
        return WordViewHolder(itemView)
    }

    override fun onBindViewHolder(holder: WordViewHolder, position: Int) {
        val current = words[position]
        holder.bind(current, onItemClickListener);

    }

    internal fun setWords(words: List<CheckList>) {
        this.words = words
        notifyDataSetChanged()
    }

    override fun getItemCount() = words.size


    interface OnItemClickListener {
        fun onDeleteItemclicked(checklist: CheckList)
        fun onEditItemclicked(checklist: CheckList)

    }
}