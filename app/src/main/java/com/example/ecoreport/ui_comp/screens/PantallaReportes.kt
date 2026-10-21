package com.example.ecoreport.ui.screens

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ExposedDropdownMenuAnchorType
import androidx.compose.material3.ExposedDropdownMenuBox
import androidx.compose.material3.ExposedDropdownMenuDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.ecoreport.data.ReporteEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PantallaReportes(
    reportes: List<ReporteEntity>,
    filtroEstadoActual: String = "TODOS",
    filtroCategoriaActual: String = "",
    alAplicarFiltros: (estado: String, categoria: String) -> Unit = { _, _ -> },
    filtroActual: String = filtroEstadoActual,
    alFiltrarPorEstado: (String) -> Unit = { estado -> alAplicarFiltros(estado, "") },
    seleccionarReporte: (ReporteEntity) -> Unit,
    volver: () -> Unit
) {

    val opcionesFiltro = listOf("TODOS", "PENDIENTE", "EN_PROCESO", "RESUELTO")

    var estadoSeleccionado by remember(filtroEstadoActual) {
        mutableStateOf(filtroEstadoActual)
    }

    var categoriaInput by remember(filtroCategoriaActual) {
        mutableStateOf(filtroCategoriaActual)
    }

    var expandidoEstado by remember {
        mutableStateOf(false)
    }

    fun obtenerEtiquetaEstado(estado: String): String {
        return when (estado) {
            "TODOS" -> "Todos"
            "PENDIENTE" -> "Pendiente"
            "EN_PROCESO" -> "En Proceso"
            "RESUELTO" -> "Resuelto"
            else -> estado
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(20.dp)
    ) {

        Text(
            text = "Reportes",
            style = MaterialTheme.typography.headlineMedium,
            fontWeight = FontWeight.Bold
        )

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        // Filtro de Estado como Select (Dropdown)
        ExposedDropdownMenuBox(
            expanded = expandidoEstado,
            onExpandedChange = {
                expandidoEstado = !expandidoEstado
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            OutlinedTextField(
                value = obtenerEtiquetaEstado(estadoSeleccionado),
                onValueChange = {},
                readOnly = true,
                label = {
                    Text("Estado")
                },
                trailingIcon = {
                    ExposedDropdownMenuDefaults.TrailingIcon(
                        expanded = expandidoEstado
                    )
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .menuAnchor(ExposedDropdownMenuAnchorType.PrimaryNotEditable)
            )

            ExposedDropdownMenu(
                expanded = expandidoEstado,
                onDismissRequest = {
                    expandidoEstado = false
                }
            ) {
                opcionesFiltro.forEach { estado ->
                    DropdownMenuItem(
                        text = {
                            Text(text = obtenerEtiquetaEstado(estado))
                        },
                        onClick = {
                            estadoSeleccionado = estado
                            expandidoEstado = false
                        }
                    )
                }
            }
        }

        Spacer(
            modifier = Modifier.height(10.dp)
        )

        // Filtro de Categoría como Input de texto
        OutlinedTextField(
            value = categoriaInput,
            onValueChange = {
                categoriaInput = it
            },
            label = {
                Text("Categoría")
            },
            placeholder = {
                Text("Filtrar por categoría")
            },
            singleLine = true,
            modifier = Modifier.fillMaxWidth()
        )

        Spacer(
            modifier = Modifier.height(12.dp)
        )

        // Botón con el texto "Buscar" para aplicar los filtros
        Button(
            onClick = {
                alAplicarFiltros(estadoSeleccionado, categoriaInput.trim())
                alFiltrarPorEstado(estadoSeleccionado)
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text(
                text = "Buscar"
            )
        }

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        if (reportes.isEmpty()) {

            Box(
                modifier = Modifier
                    .weight(1f)
                    .fillMaxWidth()
            ) {
                Text(
                    text = "No existen reportes registrados."
                )
            }

        } else {

            LazyColumn(
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(10.dp)
            ) {

                items(
                    items = reportes,
                    key = { reporte ->
                        reporte.id
                    }
                ) { reporte ->

                    Card(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable {
                                seleccionarReporte(reporte)
                            },

                        shape = RoundedCornerShape(16.dp),

                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 4.dp
                        )
                    ) {

                        Column(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(16.dp)
                        ) {

                            Text(
                                text = reporte.titulo,
                                style = MaterialTheme.typography.titleMedium,
                                fontWeight = FontWeight.Bold
                            )

                            Spacer(
                                modifier = Modifier.height(6.dp)
                            )

                            Text(
                                text = "Categoría: ${reporte.categoria}"
                            )

                            Text(
                                text = "Ubicación: ${reporte.ubicacion}"
                            )

                            Text(
                                text = "Estado: ${reporte.estado}"
                            )

                            Text(
                                text = "Fecha: ${reporte.fecha}"
                            )

                            Spacer(
                                modifier = Modifier.height(6.dp)
                            )

                            Text(
                                text = "Presiona para ver el detalle",
                                style = MaterialTheme.typography.bodySmall
                            )
                        }
                    }
                }
            }
        }

        Spacer(
            modifier = Modifier.height(16.dp)
        )

        OutlinedButton(
            onClick = volver,
            modifier = Modifier.fillMaxWidth()
        ) {

            Text(
                text = "VOLVER"
            )
        }
    }
}
