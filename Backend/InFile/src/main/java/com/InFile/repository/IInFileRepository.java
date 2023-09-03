package com.InFile.repository;

import com.InFile.model.*;

import java.util.List;

public interface IInFileRepository {
    public List<Reporte> getReporte(FiltroReporte filtroReporte);
    public List<FechaInscripcion> newAsignacion(Asignacion asignacion);
    public List<IdEstudiante> newEstudiante(Estudiante estudiante);
    public List<Carrera> getCarrera();
    public List<Genero> getGenero();
    public List<GeneroPoesia> getGeneroPoesia();
}
