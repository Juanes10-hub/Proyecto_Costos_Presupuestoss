#include "generador_reportes.h"
#include <stdio.h>
#include <string.h>
#include <stdlib.h>

int validar_datos_reporte(DatosReporte *datos) {
    if (!datos) return 0;
    if (datos->costo_total <= 0) return 0;
    if (datos->duracion_meses <= 0) return 0;
    if (datos->tamano_equipo <= 0) return 0;
    return 1;
}

int generar_reporte(DatosReporte *datos, ReporteGenerado *reporte) {
    if (!validar_datos_reporte(datos) || !reporte) {
        return -1;
    }

    // Construir JSON
    int offset = snprintf(reporte->reporte_json, sizeof(reporte->reporte_json),
        "{\n"
        "  \"informacion_proyecto\": {\n"
        "    \"nombre\": \"%s\",\n"
        "    \"cliente\": \"%s\",\n"
        "    \"fecha\": \"%s\"\n"
        "  },\n"
        "  \"costos\": {\n"
        "    \"directos\": %.2f,\n"
        "    \"indirectos\": %.2f,\n"
        "    \"total\": %.2f\n"
        "  },\n"
        "  \"detalles\": {\n"
        "    \"duracion_meses\": %d,\n"
        "    \"tamano_equipo\": %d,\n"
        "    \"costo_mensual\": %.2f,\n"
        "    \"costo_por_persona\": %.2f\n"
        "  }\n"
        "}",
        datos->nombre_proyecto,
        datos->cliente,
        datos->fecha,
        datos->costos_directos,
        datos->costos_indirectos,
        datos->costo_total,
        datos->duracion_meses,
        datos->tamano_equipo,
        datos->costo_mensual,
        datos->costo_por_persona
    );

    reporte->longitud = offset;
    return offset > 0 ? 0 : -1;
}
