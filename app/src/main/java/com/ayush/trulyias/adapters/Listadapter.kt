package com.ayush.trulyias.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ayush.trulyias.R
import com.ayush.trulyias.models.List1
import com.ayush.trulyias.models.List2

class Listadapter(
    private var listofChapters:List<List1>,
    private val onItemClickListener: (List2) -> Unit
):RecyclerView.Adapter<Listadapter.listViewHolder>() {


    class listViewHolder(itemView:View):RecyclerView.ViewHolder(itemView){
        val chapterName:TextView =itemView.findViewById(R.id.chapterName)
        val childRecyclerView:RecyclerView = itemView.findViewById(R.id.childRecyclerView)
        val linearLayout:LinearLayout = itemView.findViewById(R.id.linearLayout)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): listViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.rv_adapter,parent,false)
        return listViewHolder(view)
    }

    override fun getItemCount(): Int {
        return listofChapters.size
    }

    override fun onBindViewHolder(holder: listViewHolder, position: Int) {
        val currentList = listofChapters[position]
        holder.chapterName.text = currentList.ChapterName
        holder.childRecyclerView.setHasFixedSize(true)
        holder.childRecyclerView.layoutManager = LinearLayoutManager(holder.itemView.context, LinearLayoutManager.HORIZONTAL, false)


        val adapter = InnerListAdapter(currentList.childItemList, onItemClickListener)
        holder.childRecyclerView.adapter = adapter


        val isExpandale = currentList.isExpandale
        holder.childRecyclerView.visibility = if(isExpandale) View.VISIBLE else View.GONE

        holder.linearLayout.setOnClickListener {

            OtherExpanded(position)
            currentList.isExpandale = !currentList.isExpandale
            notifyItemChanged(position)
        }


    }

    private fun OtherExpanded(position: Int){
        val temp =listofChapters.indexOfFirst {
            it.isExpandale
        }
        if (temp>=0 && temp != position){
            listofChapters[temp].isExpandale = false
            notifyItemChanged(temp)
        }
    }


}