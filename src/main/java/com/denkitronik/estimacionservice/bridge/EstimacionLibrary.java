package com.denkitronik.estimacionservice.bridge;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;

/**
 * EstimacionLibrary — Interfaz JNA que declara las funciones de libestimacion.so.
 * Mapea la biblioteca C a Java, permitiendo llamar calcular_estimacion desde Spring Boot.
 */
public interface EstimacionLibrary extends Library {

    /**
     * INSTANCE carga libestimacion.so una sola vez.
     * JNA busca en: jna.library.path, java.library.path, y rutas del sistema.
     */
    EstimacionLibrary INSTANCE = Native.load("estimacion", EstimacionLibrary.class);

    /**
     * ResultadoEstimacion — Estructura que mapea la struct C de C hacia Java.
     * Los campos deben coincidir exactamente con los de estimacion.h.
     */
    class ResultadoEstimacion extends Structure {
        public double costo_personal;
        public double costo_infraestructura;
        public double costo_licencias;
        public double subtotal;
        public double contingencia_monto;
        public double total_presupuesto;
        public double porcentaje_personal;
        public double porcentaje_infraestructura;
        public double porcentaje_licencias;
        public double porcentaje_contingencia;

        @Override
        protected List<String> getFieldOrder() {
            return Arrays.asList(
                "costo_personal", "costo_infraestructura", "costo_licencias",
                "subtotal", "contingencia_monto", "total_presupuesto",
                "porcentaje_personal", "porcentaje_infraestructura",
                "porcentaje_licencias", "porcentaje_contingencia"
            );
        }
    }

    /**
     * Declara la función calcular_estimacion de libestimacion.so.
     * Los nombres de parámetros no importan, pero el orden y tipos SÍ.
     */
    int calcular_estimacion(
        int duracion_meses,
        int tamano_equipo,
        double salario_promedio,
        double infraestructura_mensual,
        double licencias_mensuales,
        double contingencia_porcentaje,
        ResultadoEstimacion resultado
    );
}
