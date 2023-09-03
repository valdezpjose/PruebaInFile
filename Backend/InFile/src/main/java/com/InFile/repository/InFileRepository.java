package com.InFile.repository;

import com.InFile.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcCall;
import org.springframework.stereotype.Repository;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Repository
public class InFileRepository implements IInFileRepository{
    @Autowired
    private JdbcTemplate  jdbcTemplate = new JdbcTemplate();
    @Override
    public List<Reporte> getReporte(FiltroReporte filtroReporte) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("ConsultarDatos")
                .returningResultSet("reporte", BeanPropertyRowMapper.newInstance(Reporte.class));

        MapSqlParameterSource inParams = new MapSqlParameterSource();

        if (filtroReporte.getCarrera() > 0) {
            inParams.addValue("carrera", filtroReporte.getCarrera());
        }else{
            inParams.addValue("carrera", null);
        }
        if (filtroReporte.getEdad() > 0) {
            inParams.addValue("edad", filtroReporte.getEdad());
        }else{
            inParams.addValue("edad", null);
        }
        if (filtroReporte.getId_genero_poesia() > 0) {
            inParams.addValue("id_genero_poesia", filtroReporte.getId_genero_poesia());
        }else{
            inParams.addValue("id_genero_poesia", null);
        }
        if (filtroReporte.getFecha_declamacion() != "") {
            inParams.addValue("fecha_declamacion", filtroReporte.getFecha_declamacion());
        }else{
            inParams.addValue("fecha_declamacion", null);
        }

        Map<String, Object> result = jdbcCall.execute(inParams);

        return (List<Reporte>) result.get("reporte");
    }

    @Override
    public List<FechaInscripcion> newAsignacion(Asignacion asignacion) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("InsertarAsignacionEstudiante")
                .returningResultSet("fecha", BeanPropertyRowMapper.newInstance(FechaInscripcion.class));

        LocalDate fechaActual = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String fechaFormateada = fechaActual.format(formatter);

        asignacion.setFecha_inscripcion(fechaFormateada);

        int ultimoDigito = Integer.parseInt(asignacion.getCarnet().substring(asignacion.getCarnet().length()-1));

        LocalDate fechaParticipacion;
        if (ultimoDigito == 1 && asignacion.getId_genero_poesia() == 3) {
            fechaParticipacion = calcularFechaDramatico(fechaActual);
        } else if (ultimoDigito == 3 && asignacion.getId_genero_poesia() == 2) {
            fechaParticipacion = calcularFechaEpica(fechaActual);
        } else {
            fechaParticipacion = calcularFechaOtras(fechaActual);
        }

        fechaParticipacion = excluirSabadosDomingos(fechaParticipacion);

        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        String fechaFormateada2 = fechaParticipacion.format(formatter);

        asignacion.setFecha_declamacion(fechaFormateada2);

        SqlParameterSource inParams = new MapSqlParameterSource()
                .addValue("fecha_inscripcion", asignacion.getFecha_inscripcion())
                .addValue("id_estudiante", asignacion.getId_estudiante())
                .addValue("id_genero_poesia", asignacion.getId_genero_poesia())
                .addValue("fecha_declamacion", asignacion.getFecha_declamacion());

        Map<String, Object> result = jdbcCall.execute(inParams);

        return (List<FechaInscripcion>) result.get("fecha");
    }

    private LocalDate calcularFechaDramatico(LocalDate fechaActual) {
        return fechaActual.plusDays(5);
    }

    private LocalDate calcularFechaEpica(LocalDate fechaActual) {

        return fechaActual.with(TemporalAdjusters.lastDayOfMonth());
    }

    private LocalDate calcularFechaOtras(LocalDate fechaActual) {
        return fechaActual.with(TemporalAdjusters.next(DayOfWeek.FRIDAY));
    }

    private LocalDate excluirSabadosDomingos(LocalDate fecha) {

        if (fecha.getDayOfWeek() == DayOfWeek.SATURDAY) {
            fecha = fecha.plusDays(2);
        } else if (fecha.getDayOfWeek() == DayOfWeek.SUNDAY) {
            fecha = fecha.plusDays(1);
        }
        return fecha;
    }

    @Override
    public List<IdEstudiante> newEstudiante(Estudiante estudiante) {
        SimpleJdbcCall jdbcCall = new SimpleJdbcCall(jdbcTemplate)
                .withProcedureName("InsertarEstudiante")
                .returningResultSet("estudiante", BeanPropertyRowMapper.newInstance(IdEstudiante.class));

        SqlParameterSource inParams = new MapSqlParameterSource()
                .addValue("carnet", estudiante.getCarnet())
                .addValue("nombre_estudiante", estudiante.getNombre_estudiante())
                .addValue("direccion", estudiante.getDireccion())
                .addValue("id_genero", estudiante.getId_genero())
                .addValue("telefono", estudiante.getTelefono())
                .addValue("fecha_nacimiento", estudiante.getFecha_nacimiento())
                .addValue("nombre_carrera", estudiante.getNombre_carrera());

        Map<String, Object> result = jdbcCall.execute(inParams);

        return (List<IdEstudiante>) result.get("estudiante");
    }

    @Override
    public List<Carrera> getCarrera() {
        String spName = "ObtenerCarreras";

        List<Carrera> carreras = jdbcTemplate.query(
                "EXEC " + spName,
                new BeanPropertyRowMapper<>(Carrera.class)
        );

        return carreras;
    }

    @Override
    public List<Genero> getGenero() {
        String spName = "ObtenerGeneros";

        List<Genero> generos = jdbcTemplate.query(
                "EXEC " + spName,
                new BeanPropertyRowMapper<>(Genero.class)
        );

        return generos;
    }

    @Override
    public List<GeneroPoesia> getGeneroPoesia() {
        String spName = "ObtenerGenerosPoesia";

        List<GeneroPoesia> generosPoesia = jdbcTemplate.query(
                "EXEC " + spName,
                new BeanPropertyRowMapper<>(GeneroPoesia.class)
        );

        return generosPoesia;
    }
}
