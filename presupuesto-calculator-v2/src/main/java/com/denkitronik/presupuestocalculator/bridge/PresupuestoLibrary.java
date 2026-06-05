package com.denkitronik.presupuestocalculator.bridge;

import com.sun.jna.Library;
import com.sun.jna.Native;

/**
 * PresupuestoLibrary — Interfaz JNA que declara las funciones de libpresupuesto.so.
 * 
 * JNA lee esta interfaz en tiempo de ejecución y genera automáticamente
 * el puente hacia el código nativo. No se necesita ningún archivo C adicional.
 * 
 * Regla de JNA: los nombres de los métodos deben coincidir EXACTAMENTE
 * con los nombres de las funciones en la biblioteca C.
 */
public interface PresupuestoLibrary extends Library {
    
    /**
     * INSTANCE es la única instancia de la biblioteca cargada.
     * Native.load("presupuesto", ...) busca libpresupuesto.so en el sistema.
     */
    PresupuestoLibrary INSTANCE = Native.load("presupuesto", PresupuestoLibrary.class);
    
    /* ── Declaraciones que mapean exactamente a las funciones de presupuesto.c ── */
    
    double calcular_personal(int meses, int equipo, double salario);
    double calcular_infraestructura(int meses, double infra_mensual);
    double calcular_licencias(int meses, double licencias_mensuales);
    double calcular_subtotal(double personal, double infra, double licencias);
    double calcular_contingencia(double subtotal, double porcentaje);
    double calcular_total(double subtotal, double contingencia);
}
