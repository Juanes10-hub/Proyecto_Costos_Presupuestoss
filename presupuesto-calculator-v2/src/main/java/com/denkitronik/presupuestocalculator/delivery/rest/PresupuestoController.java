package com.denkitronik.presupuestocalculator.delivery.rest;

import com.denkitronik.presupuestocalculator.service.PresupuestoService;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * PresupuestoController — Expone las operaciones de libpresupuesto como endpoints REST.
 * 
 * Todos los endpoints son GET con parámetros en la query string para
 * facilitar las pruebas directamente desde el navegador.
 */
@RestController
@RequestMapping("/api/v1/presupuesto")
public class PresupuestoController {
    
    private final PresupuestoService service;
    
    public PresupuestoController(PresupuestoService service) {
        this.service = service;
    }
    
    /**
     * GET /api/v1/presupuesto/calcular?nombre_proyecto=MiProyecto&duracion_meses=3&tamano_equipo=5&salario_promedio_mensual=3000&infraestructura_mensual=1000&licencias_mensuales=500&contingencia_porcentaje=0
     * 
     * Respuesta: {
     *   "nombre_proyecto": "MiProyecto",
     *   "personal": 45000,
     *   "infraestructura": 3000,
     *   "licencias": 1500,
     *   "subtotal": 49500,
     *   "contingencia": 0,
     *   "total": 49500
     * }
     */
    @GetMapping("/calcular")
    public Map<String, Object> calcular(
            @RequestParam String nombre_proyecto,
            @RequestParam int duracion_meses,
            @RequestParam int tamano_equipo,
            @RequestParam double salario_promedio_mensual,
            @RequestParam double infraestructura_mensual,
            @RequestParam double licencias_mensuales,
            @RequestParam double contingencia_porcentaje) {
        
        double personal = service.calcularPersonal(duracion_meses, tamano_equipo, salario_promedio_mensual);
        double infraestructura = service.calcularInfraestructura(duracion_meses, infraestructura_mensual);
        double licencias = service.calcularLicencias(duracion_meses, licencias_mensuales);
        double subtotal = service.calcularSubtotal(personal, infraestructura, licencias);
        double contingencia = service.calcularContingencia(subtotal, contingencia_porcentaje);
        double total = service.calcularTotal(subtotal, contingencia);
        
        return Map.of(
            "nombre_proyecto", nombre_proyecto,
            "personal", personal,
            "infraestructura", infraestructura,
            "licencias", licencias,
            "subtotal", subtotal,
            "contingencia", contingencia,
            "total", total
        );
    }
}
