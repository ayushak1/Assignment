package com.ayush.trulyias.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.ayush.trulyias.R
import com.ayush.trulyias.models.List2

class InnerListAdapter(
    private val list2: List<List2>,
    private val onItemClickListener: (List2) -> Unit
) : RecyclerView.Adapter<InnerListAdapter.InnerListViewHolder>() {

    inner class InnerListViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView.findViewById(R.id.imageView)
        val textView1: TextView = itemView.findViewById(R.id.textView1)
        val textView2: TextView = itemView.findViewById(R.id.textView2)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): InnerListViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_adapter2, parent, false)
        return InnerListViewHolder(view)
    }

    override fun getItemCount(): Int = list2.size

    override fun onBindViewHolder(holder: InnerListViewHolder, position: Int) {
        val item = list2[position]
        holder.imageView.setImageResource(list2[position].image)
        holder.textView1.text = item.text1
        holder.textView2.text = item.text2
        holder.itemView.setOnClickListener {
            onItemClickListener(item)
        }
    }
}
