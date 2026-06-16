package com.example.inspecoesibmec.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.inspecoesibmec.R
import com.example.inspecoesibmec.model.Inspecao

class HistoricoInspecoesAdapter(
    private val inspecoes: List<Inspecao>,
    private val onItemClick: (Inspecao) -> Unit
) : RecyclerView.Adapter<HistoricoInspecoesAdapter.HistoricoViewHolder>() {

    class HistoricoViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtInspecaoTitulo: TextView = itemView.findViewById(R.id.txtInspecaoTitulo)
        val txtInspecaoInfo: TextView = itemView.findViewById(R.id.txtInspecaoInfo)
        val txtInspecaoStatus: TextView = itemView.findViewById(R.id.txtInspecaoStatus)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): HistoricoViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_inspecao, parent, false)

        return HistoricoViewHolder(view)
    }

    override fun onBindViewHolder(holder: HistoricoViewHolder, position: Int) {
        val inspecao = inspecoes[position]

        holder.txtInspecaoTitulo.text = "Inspeção #${inspecao.id}"
        holder.txtInspecaoInfo.text =
            "${inspecao.diaSemana} • ${inspecao.turno}\n${inspecao.dataInspecao}"

        if (inspecao.finalizada) {
            holder.txtInspecaoStatus.text = "● Finalizada"
            holder.txtInspecaoStatus.setTextColor(
                ContextCompat.getColor(holder.itemView.context, R.color.status_verde)
            )
        } else {
            holder.txtInspecaoStatus.text = "● Em andamento"
            holder.txtInspecaoStatus.setTextColor(
                ContextCompat.getColor(holder.itemView.context, R.color.status_amarelo)
            )
        }

        holder.itemView.setOnClickListener {
            onItemClick(inspecao)
        }
    }

    override fun getItemCount(): Int {
        return inspecoes.size
    }
}