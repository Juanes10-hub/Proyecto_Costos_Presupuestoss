package com.denkitronik.simulacionservice.bridge;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;

public interface SimulacionLibrary extends Library {
    SimulacionLibrary INSTANCE = Native.load("simulacion", SimulacionLibrary.class);

    class ResultadoSimulacion extends Structure {
        public double costo_optimista;
        public double costo_realista;
        public double costo_pesimista;
        public double promedio;
        public double rango_variacion;

        protected List<String> getFieldOrder() {
            return Arrays.asList(
                "costo_optimista", "costo_realista", "costo_pesimista",
                "promedio", "rango_variacion"
            );
        }
    }

    int calcular_simulacion(int equipo, int duracion_meses, double salario_promedio,
                            double overhead_porcentaje, ResultadoSimulacion resultado);
}
