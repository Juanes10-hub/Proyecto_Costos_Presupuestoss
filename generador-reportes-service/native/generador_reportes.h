#ifndef REPORTES_H
#define REPORTES_H

#include <stddef.h>

typedef struct {
    char nombre_proyecto[256];
    char cliente[256];
    char fecha[20];
    double costos_directos;
    double costos_indirectos;
    double costo_total;
    int duracion_meses;
    int tamano_equipo;
    double costo_mensual;
    double costo_por_persona;
} DatosReporte;

typedef struct {
    char reporte_json[2048];
    int longitud;
} ReporteGenerado;

// Función principal: genera reporte en formato JSON
int generar_reporte(DatosReporte *datos, ReporteGenerado *reporte);

// Función auxiliar: valida datos
int validar_datos_reporte(DatosReporte *datos);

#endif // REPORTES_H
