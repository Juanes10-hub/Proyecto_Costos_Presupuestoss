#include "estimacion.h"
#include <math.h>
#include <string.h>

double calcular_costo_personal(int duracion_meses, int tamano_equipo, double salario_promedio) {
    return duracion_meses * tamano_equipo * salario_promedio;
}

double calcular_costo_infraestructura(int duracion_meses, double infraestructura_mensual) {
    return duracion_meses * infraestructura_mensual;
}

double calcular_costo_licencias(int duracion_meses, double licencias_mensuales) {
    return duracion_meses * licencias_mensuales;
}

double calcular_contingencia(double subtotal, double contingencia_porcentaje) {
    return subtotal * (contingencia_porcentaje / 100.0);
}

int calcular_estimacion(const char *nombre_proyecto, int duracion_meses, int tamano_equipo,
                        double salario_promedio, double infraestructura_mensual,
                        double licencias_mensuales, double contingencia_porcentaje,
                        ResultadoEstimacion *resultado) {
    
    if (!resultado || !nombre_proyecto) return -1;
    if (duracion_meses <= 0 || tamano_equipo <= 0) return -1;
    
    strncpy(resultado->nombre_proyecto, nombre_proyecto, 255);
    resultado->nombre_proyecto[255] = '\0';
    
    resultado->costo_personal = calcular_costo_personal(duracion_meses, tamano_equipo, salario_promedio);
    resultado->costo_infraestructura = calcular_costo_infraestructura(duracion_meses, infraestructura_mensual);
    resultado->costo_licencias = calcular_costo_licencias(duracion_meses, licencias_mensuales);
    
    resultado->subtotal = resultado->costo_personal + resultado->costo_infraestructura + resultado->costo_licencias;
    resultado->contingencia_monto = calcular_contingencia(resultado->subtotal, contingencia_porcentaje);
    resultado->total_presupuesto = resultado->subtotal + resultado->contingencia_monto;
    
    resultado->porcentaje_personal = (resultado->costo_personal / resultado->total_presupuesto) * 100.0;
    resultado->porcentaje_infraestructura = (resultado->costo_infraestructura / resultado->total_presupuesto) * 100.0;
    resultado->porcentaje_licencias = (resultado->costo_licencias / resultado->total_presupuesto) * 100.0;
    resultado->porcentaje_contingencia = (resultado->contingencia_monto / resultado->total_presupuesto) * 100.0;
    
    return 0;
}
