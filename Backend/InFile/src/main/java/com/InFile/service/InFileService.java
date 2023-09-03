package com.InFile.service;

import com.InFile.model.*;
import com.InFile.repository.IInFileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class InFileService implements IInFileService{

    @Autowired
    private IInFileRepository iInFileRepository;
    @Override
    public List<Reporte> getReporte(FiltroReporte filtroReporte) {
        List<Reporte> reportes;

        try {
            reportes = iInFileRepository.getReporte(filtroReporte);
        }catch (Exception ex){
            throw ex;
        }

        return reportes;
    }

    @Override
    public List<FechaInscripcion> newAsignacion(Asignacion asignacion) {
        List<FechaInscripcion> fecha;

        try {
            fecha = iInFileRepository.newAsignacion(asignacion);
        }catch (Exception ex){
            throw ex;
        }

        return fecha;
    }

    @Override
    public List<IdEstudiante> newEstudiante(Estudiante estudiante) {
        List<IdEstudiante> idEstudiante;

        try {
            idEstudiante = iInFileRepository.newEstudiante(estudiante);
        }catch (Exception ex){
            throw ex;
        }

        return idEstudiante;
    }

    @Override
    public List<Carrera> getCarrera() {
        List<Carrera> carreras;

        try {
            carreras = iInFileRepository.getCarrera();
        }catch (Exception ex){
            throw ex;
        }

        return carreras;
    }

    @Override
    public List<Genero> getGenero() {
        List<Genero> generos;

        try {
            generos = iInFileRepository.getGenero();
        }catch (Exception ex){
            throw ex;
        }

        return generos;
    }

    @Override
    public List<GeneroPoesia> getGeneroPoesia() {
        List<GeneroPoesia> generosPoesia;

        try {
            generosPoesia = iInFileRepository.getGeneroPoesia();
        }catch (Exception ex){
            throw ex;
        }

        return generosPoesia;
    }
}
