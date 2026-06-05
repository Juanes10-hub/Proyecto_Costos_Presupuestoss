package com.denkitronik.simulacionservice.bridge;

import com.sun.jna.Library;
import com.sun.jna.Native;

public interface SimulacionLibrary extends Library {
    SimulacionLibrary INSTANCE = Native.load("simulacion", SimulacionLibrary.class);
    
    double calcular_costo_total(int equipo, int duracion_meses, double salario_promedio, double overhead_porcentaje);
}
