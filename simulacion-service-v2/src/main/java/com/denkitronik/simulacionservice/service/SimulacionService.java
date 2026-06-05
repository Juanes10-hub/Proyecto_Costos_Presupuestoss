package com.denkitronik.simulacionservice.service;

import com.denkitronik.simulacionservice.bridge.SimulacionLibrary;
import org.springframework.stereotype.Service;

@Service
public class SimulacionService {
    
    private final SimulacionLibrary lib = SimulacionLibrary.INSTANCE;
    
    public double calcularCostoTotal(int equipo, int duracion_meses, double salario_promedio, double overhead_porcentaje) {
        return lib.calcular_costo_total(equipo, duracion_meses, salario_promedio, overhead_porcentaje);
    }
}
