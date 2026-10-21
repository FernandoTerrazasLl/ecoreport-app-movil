package com.example.ecoreport.data

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

@Dao
interface ReporteDao {

    @Insert
    suspend fun insertar(
        reporte: ReporteEntity
    )

    @Query(
        "SELECT * FROM reportes ORDER BY id DESC"
    )
    suspend fun obtenerTodos():
            List<ReporteEntity>

    @Query(
        "SELECT * FROM reportes WHERE estado = :estado ORDER BY id DESC"
    )
    suspend fun obtenerPorEstado(
        estado: String
    ): List<ReporteEntity>

    @Query(
        "SELECT * FROM reportes WHERE categoria = :categoria ORDER BY id DESC"
    )
    suspend fun obtenerPorCategoria(
        categoria: String
    ): List<ReporteEntity>

    @Query(
        """
        SELECT * FROM reportes 
        WHERE (:estado = 'TODOS' OR estado = :estado) 
          AND (:categoria = '' OR LOWER(categoria) LIKE '%' || LOWER(:categoria) || '%') 
        ORDER BY id DESC
        """
    )
    suspend fun filtrarReportes(
        estado: String,
        categoria: String
    ): List<ReporteEntity>

    @Update
    suspend fun actualizar(
        reporte: ReporteEntity
    )

    @Delete
    suspend fun eliminar(
        reporte: ReporteEntity
    )
}