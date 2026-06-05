package com.denkitronik.simulacionservice.delivery.rest;

import com.denkitronik.simulacionservice.service.SimulacionService;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/simulacion")
public class SimulacionController {
    
    private final SimulacionService service;
    
    public SimulacionController(SimulacionService service) {
        this.service = service;
    }
    
    @PostMapping("/calcular")
    public Map<String, Object> calcular(@RequestBody Map<String, Object> request) {
        // Escenario Optimista
        int equipo_optimista = ((Number) request.get("equipo_optimista")).intValue();
        int duracion_optimista = ((Number) request.get("duracion_optimista")).intValue();
        double salario_optimista = ((Number) request.get("salario_optimista")).doubleValue();
        double overhead_optimista = ((Number) request.get("overhead_optimista")).doubleValue();
        
        // Escenario Realista
        int equipo_realista = ((Number) request.get("equipo_realista")).intValue();
        int duracion_realista = ((Number) request.get("duracion_realista")).intValue();
        double salario_realista = ((Number) request.get("salario_realista")).doubleValue();
        double overhead_realista = ((Number) request.get("overhead_realista")).doubleValue();
        
        // Escenario Pesimista
        int equipo_pesimista = ((Number) request.get("equipo_pesimista")).intValue();
        int duracion_pesimista = ((Number) request.get("duracion_pesimista")).intValue();
        double salario_pesimista = ((Number) request.get("salario_pesimista")).doubleValue();
        double overhead_pesimista = ((Number) request.get("overhead_pesimista")).doubleValue();
        
        // Calcular costos totales
        double costo_optimista = service.calcularCostoTotal(equipo_optimista, duracion_optimista, salario_optimista, overhead_optimista);
        double costo_realista = service.calcularCostoTotal(equipo_realista, duracion_realista, salario_realista, overhead_realista);
        double costo_pesimista = service.calcularCostoTotal(equipo_pesimista, duracion_pesimista, salario_pesimista, overhead_pesimista);
        
        // Respuesta en una sola llave
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("optimista", costo_optimista);
        response.put("realista", costo_realista);
        response.put("pesimista", costo_pesimista);
        
        return response;
    }
}
