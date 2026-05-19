package com.denkitronik.estimacionservice.service;

import com.denkitronik.estimacionservice.bridge.EstimacionLibrary;
import org.springframework.stereotype.Service;

/**
 * EstimacionService — Capa de servicio que encapsula las llamadas a libestimacion.so.
 * Proporciona validaciones y lógica de negocio antes de llamar a la biblioteca nativa.
 */
@Service
public class EstimacionService {

    private final EstimacionLibrary lib = EstimacionLibrary.INSTANCE;

    /**
     * calcularEstimacion — Calcula el presupuesto total usando la biblioteca C.
     * 
     * @return Estructura con todos los costos y porcentajes calculados.
     * @throws IllegalArgumentException si los parámetros son inválidos.
     */
    public EstimacionLibrary.ResultadoEstimacion calcularEstimacion(
            int duracionMeses,
            int tamanoEquipo,
            double salarioPromedio,
            double infraestructuraMensual,
            double licenciasMenusuales,
            double contingenciaProcentaje) {

        // Validaciones
        if (duracionMeses <= 0) {
            throw new IllegalArgumentException("Duración debe ser mayor a 0");
        }
        if (tamanoEquipo <= 0) {
            throw new IllegalArgumentException("Tamaño de equipo debe ser mayor a 0");
        }
        if (salarioPromedio <= 0) {
            throw new IllegalArgumentException("Salario promedio debe ser mayor a 0");
        }
        if (contingenciaProcentaje < 0 || contingenciaProcentaje > 100) {
            throw new IllegalArgumentException("Contingencia debe estar entre 0 y 100");
        }

        // Llamar la biblioteca C
        EstimacionLibrary.ResultadoEstimacion resultado = new EstimacionLibrary.ResultadoEstimacion();
        int status = lib.calcular_estimacion(
            duracionMeses,
            tamanoEquipo,
            salarioPromedio,
            infraestructuraMensual,
            licenciasMenusuales,
            contingenciaProcentaje,
            resultado
        );

        if (status != 0) {
            throw new RuntimeException("Error calculando estimación (status: " + status + ")");
        }

        return resultado;
    }
}
