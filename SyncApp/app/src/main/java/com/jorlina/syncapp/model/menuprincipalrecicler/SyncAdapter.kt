package com.jorlina.syncapp.model.menuprincipalrecicler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jorlina.syncapp.R
import com.jorlina.syncapp.model.menuprincipalrecicler.SyncHolder
import com.jorlina.syncapp.model.SyncItem

class SyncAdapter (
    private var items: List<SyncItem>,
    private val onItemClick: (SyncItem) -> Unit,
): RecyclerView.Adapter<SyncHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): SyncHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.elemento_recycle_view, parent, false)
        return SyncHolder(view, onItemClick)
    }

    override fun onBindViewHolder(holder: SyncHolder, position: Int){
        val item = items[position]
        holder.bind(item)
    }

    override fun getItemCount(): Int{
        return items.size
    }

    fun updateList(newList: List<SyncItem>){
        items = newList
        notifyDataSetChanged()
    }
}