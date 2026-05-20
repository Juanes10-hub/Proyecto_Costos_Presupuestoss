package com.denkitronik.generadorreportesservice.delivery.rest;

import com.denkitronik.generadorreportesservice.service.ReportesService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/v1/reportes")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ReportesController {

    private final ReportesService service;

    public ReportesController(ReportesService service) {
        this.service = service;
    }

    @PostMapping("/generar")
    public Map<String, Object> generarReporte(@RequestBody Map<String, Object> request) {
        try {
            String nombreProyecto = (String) request.get("nombre_proyecto");
            String cliente = (String) request.get("cliente");
            String fecha = (String) request.get("fecha");
            double costoDirecto = ((Number) request.get("costos_directos")).doubleValue();
            double costoIndirecto = ((Number) request.get("costos_indirectos")).doubleValue();
            double costoTotal = ((Number) request.get("costo_total")).doubleValue();
            int duracionMeses = ((Number) request.get("duracion_meses")).intValue();
            int tamanoEquipo = ((Number) request.get("tamano_equipo")).intValue();
            double costoMensual = ((Number) request.get("costo_mensual")).doubleValue();
            double costoPorPersona = ((Number) request.get("costo_por_persona")).doubleValue();

            String reporteJson = service.generarReporte(
                nombreProyecto, cliente, fecha,
                costoDirecto, costoIndirecto, costoTotal,
                duracionMeses, tamanoEquipo,
                costoMensual, costoPorPersona
            );

            return Map.of("status", "success", "reporte", reporteJson);
        } catch (Exception e) {
            return Map.of("status", "error", "mensaje", e.getMessage());
        }
    }
}
