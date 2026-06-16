package com.example.inspecoesibmec

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import com.example.inspecoesibmec.model.RegistroPresenca
import com.example.inspecoesibmec.network.ApiClient
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RegistroPresencaActivity : AppCompatActivity() {

    private lateinit var txtDisciplina: TextView
    private lateinit var txtProfessor: TextView
    private lateinit var txtSala: TextView
    private lateinit var txtTurma: TextView

    private lateinit var radioGroup: RadioGroup
    private lateinit var editObservacao: EditText
    private lateinit var btnSalvar: Button

    private var inspecaoId = 0
    private var aulaId = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_registro_presenca)

        txtDisciplina = findViewById(R.id.txtDisciplinaRegistro)
        txtProfessor = findViewById(R.id.txtProfessorRegistro)
        txtSala = findViewById(R.id.txtSalaRegistro)
        txtTurma = findViewById(R.id.txtTurmaRegistro)

        radioGroup = findViewById(R.id.radioGroupStatus)
        editObservacao = findViewById(R.id.editObservacao)
        btnSalvar = findViewById(R.id.btnSalvarRegistro)

        inspecaoId = intent.getIntExtra("inspecaoId", 0)
        aulaId = intent.getIntExtra("aulaId", 0)

        txtDisciplina.text = intent.getStringExtra("disciplina")
        txtProfessor.text = "Professor: " + intent.getStringExtra("professor")
        txtSala.text = "Sala: " + intent.getStringExtra("sala")
        txtTurma.text = "Turma: " + intent.getIntExtra("turmaId", 0)

        btnSalvar.setOnClickListener {
            salvarRegistro()
        }
    }

    private fun salvarRegistro() {

        val status = when (radioGroup.checkedRadioButtonId) {

            R.id.radioPresente ->
                "PRESENTE"

            R.id.radioAusenteJustificada ->
                "AUSENTE_COM_JUSTIFICATIVA"

            R.id.radioAusenteSemJustificativa ->
                "AUSENTE_SEM_JUSTIFICATIVA"

            else -> {
                Toast.makeText(
                    this,
                    "Selecione um status",
                    Toast.LENGTH_SHORT
                ).show()

                return
            }
        }

        val registro = RegistroPresenca(
            inspecaoId = inspecaoId,
            aulaId = aulaId,
            status = status,
            observacao = editObservacao.text.toString()
        )

        ApiClient.apiService.salvarRegistro(registro)
            .enqueue(object : Callback<RegistroPresenca> {

                override fun onResponse(
                    call: Call<RegistroPresenca>,
                    response: Response<RegistroPresenca>
                ) {

                    if (response.isSuccessful) {

                        Toast.makeText(
                            this@RegistroPresencaActivity,
                            "Registro salvo",
                            Toast.LENGTH_SHORT
                        ).show()

                        val resultIntent = android.content.Intent()
                        resultIntent.putExtra("aulaId", aulaId)
                        setResult(RESULT_OK, resultIntent)
                        finish()
                    }
                }

                override fun onFailure(
                    call: Call<RegistroPresenca>,
                    t: Throwable
                ) {

                    Toast.makeText(
                        this@RegistroPresencaActivity,
                        "Erro: ${t.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            })
    }
}