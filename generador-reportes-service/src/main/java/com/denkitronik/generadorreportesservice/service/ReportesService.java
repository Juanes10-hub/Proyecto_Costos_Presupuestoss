package com.denkitronik.generadorreportesservice.service;

import com.denkitronik.generadorreportesservice.bridge.ReportesLibrary;
import org.springframework.stereotype.Service;
import java.nio.charset.StandardCharsets;

@Service
public class ReportesService {

    private final ReportesLibrary lib = ReportesLibrary.INSTANCE;

    public String generarReporte(String nombreProyecto, String cliente, String fecha,
                                  double costoDirecto, double costoIndirecto,
                                  double costoTotal, int duracionMeses, int tamanoEquipo,
                                  double costoMensual, double costoPorPersona) {

        if (costoTotal <= 0 || duracionMeses <= 0 || tamanoEquipo <= 0) {
            throw new IllegalArgumentException("Los costos y duraciones deben ser positivos");
        }

        ReportesLibrary.DatosReporte datos = new ReportesLibrary.DatosReporte();
        System.arraycopy(nombreProyecto.getBytes(StandardCharsets.UTF_8), 0, datos.nombre_proyecto, 0, Math.min(nombreProyecto.getBytes(StandardCharsets.UTF_8).length, 255));
        System.arraycopy(cliente.getBytes(StandardCharsets.UTF_8), 0, datos.cliente, 0, Math.min(cliente.getBytes(StandardCharsets.UTF_8).length, 255));
        System.arraycopy(fecha.getBytes(StandardCharsets.UTF_8), 0, datos.fecha, 0, Math.min(fecha.getBytes(StandardCharsets.UTF_8).length, 19));
        datos.costos_directos = costoDirecto;
        datos.costos_indirectos = costoIndirecto;
        datos.costo_total = costoTotal;
        datos.duracion_meses = duracionMeses;
        datos.tamano_equipo = tamanoEquipo;
        datos.costo_mensual = costoMensual;
        datos.costo_por_persona = costoPorPersona;

        ReportesLibrary.ReporteGenerado reporte = new ReportesLibrary.ReporteGenerado();
        int resultado = lib.generar_reporte(datos, reporte);

        if (resultado != 0) {
            throw new RuntimeException("Error al generar reporte en C");
        }

        return new String(reporte.reporte_json, 0, reporte.longitud, StandardCharsets.UTF_8);
    }
}
