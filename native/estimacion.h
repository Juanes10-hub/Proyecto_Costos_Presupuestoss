/* estimacion.h */
#ifndef ESTIMACION_H
#define ESTIMACION_H

#include <stddef.h>

typedef struct {
    double costo_personal;
    double costo_infraestructura;
    double costo_licencias;
    double subtotal;
    double contingencia_monto;
    double total_presupuesto;
    double porcentaje_personal;
    double porcentaje_infraestructura;
    double porcentaje_licencias;
    double porcentaje_contingencia;
} ResultadoEstimacion;

int calcular_estimacion(int duracion_meses, int tamano_equipo, double salario_promedio,
                        double infraestructura_mensual, double licencias_mensuales,
                        double contingencia_porcentaje, ResultadoEstimacion *resultado);

#endif
