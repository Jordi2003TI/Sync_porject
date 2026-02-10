package com.jorlina.syncapp.model.perfilrecicler

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jorlina.syncapp.R
import com.jorlina.syncapp.model.menuprincipalrecicler.SyncHolder
import com.jorlina.syncapp.model.SyncItem

class PerfilAdapter (
    private var items: List<SyncItem>,
    private val onItemClick: (SyncItem) -> Unit,
): RecyclerView.Adapter<PerfilHolder>(){
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PerfilHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.elemento_crud, parent, false)
        return PerfilHolder(view, onItemClick)
    }

    override fun onBindViewHolder(holder: PerfilHolder, position: Int){
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





