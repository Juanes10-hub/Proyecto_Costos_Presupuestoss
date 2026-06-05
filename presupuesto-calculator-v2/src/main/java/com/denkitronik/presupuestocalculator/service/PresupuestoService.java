package com.denkitronik.presupuestocalculator.service;

import com.denkitronik.presupuestocalculator.bridge.PresupuestoLibrary;
import org.springframework.stereotype.Service;

/**
 * PresupuestoService — Capa de servicio que encapsula las llamadas a la biblioteca nativa.
 * 
 * Esta clase actúa como intermediario: el controlador no llama directamente
 * a JNA. Así, si en el futuro cambias la implementación, solo modificas este archivo.
 */
@Service
public class PresupuestoService {
    
    /**
     * Reutilizamos INSTANCE — JNA carga la biblioteca una sola vez
     * y mantiene la instancia para toda la aplicación.
     */
    private final PresupuestoLibrary lib = PresupuestoLibrary.INSTANCE;
    
    public double calcularPersonal(int meses, int equipo, double salario) {
        return lib.calcular_personal(meses, equipo, salario);
    }
    
    public double calcularInfraestructura(int meses, double infraMensual) {
        return lib.calcular_infraestructura(meses, infraMensual);
    }
    
    public double calcularLicencias(int meses, double licenciasMensuales) {
        return lib.calcular_licencias(meses, licenciasMensuales);
    }
    
    public double calcularSubtotal(double personal, double infra, double licencias) {
        return lib.calcular_subtotal(personal, infra, licencias);
    }
    
    public double calcularContingencia(double subtotal, double porcentaje) {
        if (porcentaje < 0) {
            throw new IllegalArgumentException("El porcentaje de contingencia no puede ser negativo: " + porcentaje);
        }
        return lib.calcular_contingencia(subtotal, porcentaje);
    }
    
    public double calcularTotal(double subtotal, double contingencia) {
        return lib.calcular_total(subtotal, contingencia);
    }
}
