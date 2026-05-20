package com.denkitronik.estimacionservice.delivery.rest;

import com.denkitronik.estimacionservice.bridge.EstimacionLibrary;
import com.denkitronik.estimacionservice.service.EstimacionService;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/estimacion")
@CrossOrigin(origins = "*", maxAge = 3600)
public class EstimacionController {

    private final EstimacionService service;

    public EstimacionController(EstimacionService service) {
        this.service = service;
    }

    @PostMapping("/calcular")
    public Map<String, Object> calcularEstimacion(@RequestBody Map<String, Object> request) {
        try {
            String nombreProyecto = (String) request.get("nombre_proyecto");
            int duracion = ((Number) request.get("duracion_meses")).intValue();
            int tamano = ((Number) request.get("tamano_equipo")).intValue();
            double salario = ((Number) request.get("salario_promedio_mensual")).doubleValue();
            double infraestructura = ((Number) request.get("infraestructura_mensual")).doubleValue();
            double licencias = ((Number) request.get("licencias_mensuales")).doubleValue();
            double contingencia = ((Number) request.get("contingencia_porcentaje")).doubleValue();

            EstimacionLibrary.ResultadoEstimacion resultado = service.calcularEstimacion(
                nombreProyecto, duracion, tamano, salario, infraestructura, licencias, contingencia
            );

            return Map.of(
                "nombre_proyecto", new String(resultado.nombre_proyecto).split("\0")[0],
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
            return Map.of("error", e.getMessage());
        }
    }
}
