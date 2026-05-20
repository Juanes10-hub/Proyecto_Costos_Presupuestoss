package com.denkitronik.estimacionservice.bridge;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;

public interface EstimacionLibrary extends Library {
    EstimacionLibrary INSTANCE = Native.load("estimacion", EstimacionLibrary.class);

    class ResultadoEstimacion extends Structure {
        public byte[] nombre_proyecto = new byte[256];
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

        protected List<String> getFieldOrder() {
            return Arrays.asList(
                "nombre_proyecto",
                "costo_personal", "costo_infraestructura", "costo_licencias",
                "subtotal", "contingencia_monto", "total_presupuesto",
                "porcentaje_personal", "porcentaje_infraestructura",
                "porcentaje_licencias", "porcentaje_contingencia"
            );
        }
    }

    int calcular_estimacion(String nombre_proyecto, int duracion_meses, int tamano_equipo,
                            double salario_promedio, double infraestructura_mensual,
                            double licencias_mensuales, double contingencia_porcentaje,
                            ResultadoEstimacion resultado);
}
