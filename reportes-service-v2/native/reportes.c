#include "reportes.h"

double calcular_costo_total(double directos, double indirectos) {
    return directos + indirectos;
}

double calcular_costo_mensual(double total, int duracion_meses) {
    if (duracion_meses <= 0) return 0;
    return total / duracion_meses;
}

double calcular_costo_por_persona(double mensual, int tamano_equipo) {
    if (tamano_equipo <= 0) return 0;
    return mensual / tamano_equipo;
}
