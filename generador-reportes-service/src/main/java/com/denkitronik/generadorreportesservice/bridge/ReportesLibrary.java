package com.denkitronik.generadorreportesservice.bridge;

import com.sun.jna.Library;
import com.sun.jna.Native;
import com.sun.jna.Structure;
import java.util.Arrays;
import java.util.List;

public interface ReportesLibrary extends Library {
    ReportesLibrary INSTANCE = Native.load("generador_reportes", ReportesLibrary.class);

    class DatosReporte extends Structure {
        public byte[] nombre_proyecto = new byte[256];
        public byte[] cliente = new byte[256];
        public byte[] fecha = new byte[20];
        public double costos_directos;
        public double costos_indirectos;
        public double costo_total;
        public int duracion_meses;
        public int tamano_equipo;
        public double costo_mensual;
        public double costo_por_persona;

        protected List<String> getFieldOrder() {
            return Arrays.asList(
                "nombre_proyecto", "cliente", "fecha",
                "costos_directos", "costos_indirectos", "costo_total",
                "duracion_meses", "tamano_equipo", "costo_mensual", "costo_por_persona"
            );
        }
    }

    class ReporteGenerado extends Structure {
        public byte[] reporte_json = new byte[2048];
        public int longitud;

        protected List<String> getFieldOrder() {
            return Arrays.asList("reporte_json", "longitud");
        }
    }

    int generar_reporte(DatosReporte datos, ReporteGenerado reporte);
    int validar_datos_reporte(DatosReporte datos);
}
