package com.denkitronik.reportesservice.service;

import com.denkitronik.reportesservice.bridge.ReportesLibrary;
import org.springframework.stereotype.Service;

@Service
public class ReportesService {
    
    private final ReportesLibrary lib = ReportesLibrary.INSTANCE;
    
    public double calcularCostoTotal(double directos, double indirectos) {
        return lib.calcular_costo_total(directos, indirectos);
    }
    
    public double calcularCostoMensual(double total, int duracion_meses) {
        return lib.calcular_costo_mensual(total, duracion_meses);
    }
    
    public double calcularCostoPorPersona(double mensual, int tamano_equipo) {
        return lib.calcular_costo_por_persona(mensual, tamano_equipo);
    }
}
