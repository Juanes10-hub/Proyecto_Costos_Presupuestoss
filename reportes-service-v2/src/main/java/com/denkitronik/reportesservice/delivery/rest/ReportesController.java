package com.denkitronik.reportesservice.delivery.rest;

import com.denkitronik.reportesservice.service.ReportesService;
import org.springframework.web.bind.annotation.*;

import java.util.LinkedHashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/v1/reportes")
public class ReportesController {
    
    private final ReportesService service;
    
    public ReportesController(ReportesService service) {
        this.service = service;
    }
    
    @PostMapping("/calcular")
    public Map<String, Object> calcular(@RequestBody Map<String, Object> request) {
        // Extraer campos
        String nombre_proyecto = (String) request.get("nombre_proyecto");
        String cliente = (String) request.get("cliente");
        String fecha_reporte = (String) request.get("fecha_reporte");
        double costos_directos = ((Number) request.get("costos_directos")).doubleValue();
        double costos_indirectos = ((Number) request.get("costos_indirectos")).doubleValue();
        int duracion_meses = ((Number) request.get("duracion_meses")).intValue();
        int tamano_equipo = ((Number) request.get("tamano_equipo")).intValue();
        
        // Cálculos
        double costo_total = service.calcularCostoTotal(costos_directos, costos_indirectos);
        double costo_mensual = service.calcularCostoMensual(costo_total, duracion_meses);
        double costo_por_persona = service.calcularCostoPorPersona(costo_mensual, tamano_equipo);
        
        // Usar LinkedHashMap para mantener el orden de inserción
        Map<String, Object> response = new LinkedHashMap<>();
        response.put("nombre_proyecto", nombre_proyecto);
        response.put("cliente", cliente);
        response.put("fecha_reporte", fecha_reporte);
        response.put("costos_directos", costos_directos);
        response.put("costos_indirectos", costos_indirectos);
        response.put("costo_total", costo_total);
        response.put("duracion_meses", duracion_meses);
        response.put("tamano_equipo", tamano_equipo);
        response.put("costo_mensual", costo_mensual);
        response.put("costo_por_persona", costo_por_persona);
        
        return response;
    }
}
