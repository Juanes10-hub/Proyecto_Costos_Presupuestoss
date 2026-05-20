package com.denkitronik.simulacionservice.service;

import com.denkitronik.simulacionservice.bridge.SimulacionLibrary;
import org.springframework.stereotype.Service;

@Service
public class SimulacionService {

    private final SimulacionLibrary lib = SimulacionLibrary.INSTANCE;

    public SimulacionLibrary.ResultadoSimulacion calcularSimulacion(int equipo, int duracion_meses,
                                                                    double salario_promedio,
                                                                    double overhead_porcentaje) {
        
        if (equipo <= 0 || duracion_meses <= 0 || salario_promedio <= 0) {
            throw new IllegalArgumentException("Parámetros deben ser positivos");
        }
        if (overhead_porcentaje < 0 || overhead_porcentaje > 100) {
            throw new IllegalArgumentException("Overhead debe estar entre 0 y 100");
        }

        SimulacionLibrary.ResultadoSimulacion resultado = new SimulacionLibrary.ResultadoSimulacion();
        
        int codigo = lib.calcular_simulacion(equipo, duracion_meses, salario_promedio,
                                             overhead_porcentaje, resultado);

        if (codigo != 0) {
            throw new RuntimeException("Error al calcular simulación en C");
        }

        return resultado;
    }
}
