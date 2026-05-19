package com.denkitronik.estimacionservice.delivery.rest;

import com.denkitronik.estimacionservice.bridge.EstimacionLibrary;
import com.denkitronik.estimacionservice.service.EstimacionService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * EstimacionController — Expone el cálculo de presupuestos como endpoint REST.
 * Recibe datos del frontend, llama al servicio y retorna JSON.
 */
@RestController
@RequestMapping("/api/v1/estimacion")
@CrossOrigin(origins = "*", maxAge = 3600)
public class EstimacionController {

    private final EstimacionService service;

    public EstimacionController(EstimacionService service) {
        this.service = service;
    }

    /**
     * POST /api/v1/estimacion/calcular
     * Calcula el presupuesto total basado en los parámetros del proyecto.
     */
    @PostMapping("/calcular")
    public Map<String, Object> calcularEstimacion(@RequestBody Map<String, Object> request) {
        try {
            // Extraer parámetros del JSON
            int duracionMeses = ((Number) request.get("duracion_meses")).intValue();
            int tamanoEquipo = ((Number) request.get("tamano_equipo")).intValue();
            double salarioPromedio = ((Number) request.get("salario_promedio_mensual")).doubleValue();
            double infraestructuraMensual = ((Number) request.get("infraestructura_mensual")).doubleValue();
            double licenciasMenusuales = ((Number) request.get("licencias_mensuales")).doubleValue();
            double contingenciaProcentaje = ((Number) request.get("contingencia_porcentaje")).doubleValue();

            // Llamar servicio
            EstimacionLibrary.ResultadoEstimacion resultado = service.calcularEstimacion(
                duracionMeses,
                tamanoEquipo,
                salarioPromedio,
                infraestructuraMensual,
                licenciasMenusuales,
                contingenciaProcentaje
            );

            // Retornar respuesta
            return Map.of(
                "costos", Map.of(
                    "personal", resultado.costo_personal,
                    "infraestructura", resultado.costo_infraestructura,
                    "licencias", resultado.costo_licencias,
                    "subtotal", resultado.subtotal,
                    "contingencia", resultado.contingencia_monto,
                    "total", resultado.total_presupuesto
                ),
                "distribucion", Map.of(
                    "personal", resultado.porcentaje_personal,
                    "infraestructura", resultado.porcentaje_infraestructura,
                    "licencias", resultado.porcentaje_licencias,
                    "contingencia", resultado.porcentaje_contingencia
                )
            );
        } catch (Exception e) {
            return Map.of(
                "error", e.getMessage(),
                "status", "FAILED"
            );
        }
    }
}
