package com.denkitronik.estimacionservice.service;

import com.denkitronik.estimacionservice.bridge.EstimacionLibrary;
import org.springframework.stereotype.Service;
import java.nio.charset.StandardCharsets;

@Service
public class EstimacionService {

    private final EstimacionLibrary lib = EstimacionLibrary.INSTANCE;

    public EstimacionLibrary.ResultadoEstimacion calcularEstimacion(
            String nombreProyecto,
            int duracion_meses,
            int tamano_equipo,
            double salario_promedio,
            double infraestructura_mensual,
            double licencias_mensuales,
            double contingencia_porcentaje) {

        if (duracion_meses <= 0) throw new IllegalArgumentException("Duración debe ser > 0");
        if (tamano_equipo <= 0) throw new IllegalArgumentException("Tamaño equipo debe ser > 0");
        if (salario_promedio <= 0) throw new IllegalArgumentException("Salario debe ser > 0");
        if (contingencia_porcentaje < 0 || contingencia_porcentaje > 100) {
            throw new IllegalArgumentException("Contingencia debe estar entre 0 y 100");
        }

        EstimacionLibrary.ResultadoEstimacion resultado = new EstimacionLibrary.ResultadoEstimacion();
        
        int codigo = lib.calcular_estimacion(
            nombreProyecto,
            duracion_meses,
            tamano_equipo,
            salario_promedio,
            infraestructura_mensual,
            licencias_mensuales,
            contingencia_porcentaje,
            resultado
        );

        if (codigo != 0) {
            throw new RuntimeException("Error al calcular estimación en C");
        }

        return resultado;
    }
}
