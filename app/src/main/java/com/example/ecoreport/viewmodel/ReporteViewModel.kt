package com.example.ecoreport.viewmodel

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.ecoreport.data.ReporteEntity
import com.example.ecoreport.repository.ReporteRepository
import kotlinx.coroutines.launch

class ReporteViewModel (
    private val repository: ReporteRepository
): ViewModel() {
    val reportes = mutableStateListOf<ReporteEntity>()

    var filtroEstadoActual by mutableStateOf("TODOS")
        private set

    init {
        cargarReportes()
    }

    fun filtrarPorEstado(estado: String) {
        filtroEstadoActual = estado
        viewModelScope.launch {
            val datos = if (estado == "TODOS") {
                repository.obtenerTodos()
            } else {
                repository.obtenerPorEstado(estado)
            }
            reportes.clear()
            reportes.addAll(datos)
        }
    }

    fun cargarReportes() {
        filtrarPorEstado(filtroEstadoActual)
    }

    fun insertar(reporte: ReporteEntity) {
        viewModelScope.launch {
            repository.insertar(reporte)
            cargarReportes()
        }
    }

    fun eliminar(reporte: ReporteEntity) {
        viewModelScope.launch {
            repository.eliminar(reporte)

            cargarReportes()
        }
    }

    fun actualizar(reporte: ReporteEntity) {
        viewModelScope.launch {
            repository.actualizar(reporte)

            cargarReportes()
        }
    }
}