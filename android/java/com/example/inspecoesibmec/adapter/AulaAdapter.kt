package com.example.inspecoesibmec.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.example.inspecoesibmec.R
import com.example.inspecoesibmec.model.Aula

class AulaAdapter(
    private val aulas: List<Aula>,
    private val aulasInspecionadas: MutableSet<Int>,
    private val onItemClick: (Aula) -> Unit
) : RecyclerView.Adapter<AulaAdapter.AulaViewHolder>() {

    class AulaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val txtDisciplina: TextView = itemView.findViewById(R.id.txtDisciplina)
        val txtProfessor: TextView = itemView.findViewById(R.id.txtProfessor)
        val txtSalaTurma: TextView = itemView.findViewById(R.id.txtSalaTurma)
        val txtInspecionado: TextView = itemView.findViewById(R.id.txtInspecionado)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AulaViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_aula, parent, false)

        return AulaViewHolder(view)
    }

    override fun onBindViewHolder(holder: AulaViewHolder, position: Int) {
        val aula = aulas[position]

        holder.txtDisciplina.text = aula.disciplina
        holder.txtProfessor.text = "Professor: ${aula.professor}"
        holder.txtSalaTurma.text = "Sala: ${aula.sala} | Turma: ${aula.turmaId}"

        val inspecionado = aulasInspecionadas.contains(aula.id)

        if (inspecionado) {

            holder.txtInspecionado.text = "Inspecionado: Sim"

            holder.txtInspecionado.setTextColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.status_verde
                )
            )

        } else {

            holder.txtInspecionado.text = "Inspecionado: Não"

            holder.txtInspecionado.setTextColor(
                ContextCompat.getColor(
                    holder.itemView.context,
                    R.color.status_vermelho
                )
            )
        }

        holder.itemView.setOnClickListener {
            onItemClick(aula)
        }
    }

    override fun getItemCount(): Int {
        return aulas.size
    }
}