package com.InFile.model;

import lombok.Data;
import lombok.Getter;

@Data
public class FiltroReporte {
    @Getter
    int carrera;
    @Getter
    int edad;
    @Getter
    int id_genero_poesia;
    @Getter
    String fecha_declamacion;
}
