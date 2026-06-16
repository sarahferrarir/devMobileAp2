package com.example.inspecoesibmec

import android.os.Bundle
import android.view.View
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment

class ResumoFragment : Fragment(R.layout.fragment_resumo) {

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val cardAnalise = view.findViewById<LinearLayout>(R.id.cardAnaliseResumo)
        val txtMensagemResumo = view.findViewById<TextView>(R.id.txtMensagemResumo)

        val percentualFaltas =
            arguments?.getDouble("percentualFaltas") ?: 0.0

        val percentualFaltasSemJustificativa =
            arguments?.getDouble("percentualFaltasSemJustificativa") ?: 0.0

        when {
            percentualFaltasSemJustificativa > 0 -> {
                cardAnalise.setBackgroundResource(R.drawable.card_status_red)

                txtMensagemResumo.text =
                    "Atenção: houve ausência sem justificativa neste turno. Recomenda-se análise pelo supervisor."

                txtMensagemResumo.setTextColor(
                    ContextCompat.getColor(requireContext(), R.color.status_red_text)
                )
            }

            percentualFaltas > 0 -> {
                cardAnalise.setBackgroundResource(R.drawable.card_status_yellow)

                txtMensagemResumo.text =
                    "Houve ausência registrada, mas todas possuem justificativa."

                txtMensagemResumo.setTextColor(
                    ContextCompat.getColor(requireContext(), R.color.status_yellow_text)
                )
            }

            else -> {
                cardAnalise.setBackgroundResource(R.drawable.card_status_green)

                txtMensagemResumo.text =
                    "Nenhuma ausência foi registrada nesta inspeção."

                txtMensagemResumo.setTextColor(
                    ContextCompat.getColor(requireContext(), R.color.status_green_text)
                )
            }
        }
    }
}