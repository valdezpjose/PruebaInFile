package com.InFile.controller;

import com.InFile.model.*;
import com.InFile.service.IInFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/InFile") // Asegúrate de usar una barra "/" al principio de la ruta
@CrossOrigin("*")
public class InFileController {
    private final IInFileService iInFileService;

    @Autowired
    public InFileController(IInFileService iInFileService) {
        this.iInFileService = iInFileService;
    }


    @PostMapping("/getReporte")
    public ResponseEntity<List<Reporte>> getReporte(@RequestBody FiltroReporte filtros) {
        List<Reporte> reporte = iInFileService.getReporte(filtros);
        return ResponseEntity.ok(reporte);
    }

    @PostMapping("/newAsignacion")
    public ResponseEntity<List<FechaInscripcion>> newAsignacion(@RequestBody Asignacion asignacion) {
        List<FechaInscripcion> fechaInscripcion = iInFileService.newAsignacion(asignacion);

        if (!fechaInscripcion.isEmpty()) {
            return ResponseEntity.ok(fechaInscripcion);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @PostMapping("/newEstudiante")
    public ResponseEntity<List<IdEstudiante>> newEstudiante(@RequestBody Estudiante estudiante) {
        List<IdEstudiante> idEstudiante = iInFileService.newEstudiante(estudiante);

        if (!idEstudiante.isEmpty()) {
            return ResponseEntity.ok(idEstudiante);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/carreras")
    public ResponseEntity<List<Carrera>> getCarrera() {
        List<Carrera> carreras = iInFileService.getCarrera();

        if (!carreras.isEmpty()) {
            return ResponseEntity.ok(carreras);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/generos")
    public ResponseEntity<List<Genero>> getGenero() {
        List<Genero> generos = iInFileService.getGenero();

        if (!generos.isEmpty()) {
            return ResponseEntity.ok(generos);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }

    @GetMapping("/generos_poesia")
    public ResponseEntity<List<GeneroPoesia>> getGenerosPoesia() {
        List<GeneroPoesia> generosPoesia = iInFileService.getGeneroPoesia();

        if (!generosPoesia.isEmpty()) {
            return ResponseEntity.ok(generosPoesia);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }
}