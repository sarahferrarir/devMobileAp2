package com.example.inspecoesibmec.network

import com.example.inspecoesibmec.model.LoginRequest
import com.example.inspecoesibmec.model.Usuario
import com.example.inspecoesibmec.model.Inspecao
import com.example.inspecoesibmec.model.InspecaoRequest
import com.example.inspecoesibmec.model.Aula
import com.example.inspecoesibmec.model.RegistroPresenca
import com.example.inspecoesibmec.model.ResumoInspecao
import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST
import retrofit2.http.GET
import retrofit2.http.PUT
import retrofit2.http.Path
import retrofit2.http.Query

interface ApiService {

    @POST("usuarios/login")
    fun login(
        @Body request: LoginRequest
    ): Call<Usuario>

    @POST("inspecoes")
    fun criarInspecao(
        @Body inspecao: InspecaoRequest
    ): Call<Inspecao>

    @GET("inspecoes")
    fun listarInspecoes(): Call<List<Inspecao>>

    @GET("aulas")
    fun listarAulas(
        @Query("diaSemana") diaSemana: String,
        @Query("turno") turno: String
    ): Call<List<Aula>>

    @POST("registros")
    fun salvarRegistro(
        @Body registro: RegistroPresenca
    ): Call<RegistroPresenca>

    @GET("registros/resumo/{inspecaoId}")
    fun buscarResumo(
        @Path("inspecaoId") inspecaoId: Int
    ): Call<ResumoInspecao>

    @PUT("inspecoes/{id}/finalizar")
    fun finalizarInspecao(
        @Path("id") id: Int
    ): Call<Inspecao>
}