package com.denkitronik.simulacionservice.delivery.rest;

import com.denkitronik.simulacionservice.service.SimulacionService;
import com.denkitronik.simulacionservice.bridge.SimulacionLibrary;
import org.springframework.web.bind.annotation.*;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/simulacion")
@CrossOrigin(origins = "*", maxAge = 3600)
public class SimulacionController {

    private final SimulacionService service;

    public SimulacionController(SimulacionService service) {
        this.service = service;
    }

    @PostMapping("/calcular")
    public Map<String, Object> calcularSimulacion(@RequestBody Map<String, Object> request) {
        try {
            int equipo = ((Number) request.get("equipo")).intValue();
            int duracion = ((Number) request.get("duracion_meses")).intValue();
            double salario = ((Number) request.get("salario_promedio")).doubleValue();
            double overhead = ((Number) request.get("overhead_porcentaje")).doubleValue();

            SimulacionLibrary.ResultadoSimulacion resultado = service.calcularSimulacion(
                equipo, duracion, salario, overhead
            );

            return Map.of(
                "escenarios", Map.of(
                    "optimista", resultado.costo_optimista,
                    "realista", resultado.costo_realista,
                    "pesimista", resultado.costo_pesimista
                ),
                "estadisticas", Map.of(
                    "promedio", resultado.promedio,
                    "rango_variacion", resultado.rango_variacion
                )
            );
        } catch (Exception e) {
            return Map.of("error", e.getMessage());
        }
    }
}
