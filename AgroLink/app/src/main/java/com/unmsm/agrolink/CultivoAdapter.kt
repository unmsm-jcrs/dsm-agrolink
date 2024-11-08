package com.unmsm.agrolink.data

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.unmsm.agrolink.R

class CultivoAdapter(private var cultivoList: List<Cultivo>) : RecyclerView.Adapter<CultivoAdapter.CultivoViewHolder>() {

    class CultivoViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val textViewTipoCultivo: TextView = view.findViewById(R.id.textViewTipoCultivo)
        val textViewFechaSiembra: TextView = view.findViewById(R.id.textViewFechaSiembra)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): CultivoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_cultivo, parent, false)
        return CultivoViewHolder(view)
    }

    override fun onBindViewHolder(holder: CultivoViewHolder, position: Int) {
        val cultivo = cultivoList[position]
        holder.textViewTipoCultivo.text = cultivo.tipo
        holder.textViewFechaSiembra.text = cultivo.fechaSiembra
    }

    override fun getItemCount() = cultivoList.size

    fun updateData(newCultivoList: List<Cultivo>) {
        cultivoList = newCultivoList
        notifyDataSetChanged()
    }
}
