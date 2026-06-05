package com.denkitronik.reportesservice.bridge;

import com.sun.jna.Library;
import com.sun.jna.Native;

public interface ReportesLibrary extends Library {
    ReportesLibrary INSTANCE = Native.load("reportes", ReportesLibrary.class);
    
    double calcular_costo_total(double directos, double indirectos);
    double calcular_costo_mensual(double total, int duracion_meses);
    double calcular_costo_por_persona(double mensual, int tamano_equipo);
}
