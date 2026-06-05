#include "simulacion.h"

double calcular_costo_total(int equipo, int duracion_meses, double salario_promedio, double overhead_porcentaje) {
    double salario_total = equipo * duracion_meses * salario_promedio;
    double costo_total = salario_total * (1.0 + (overhead_porcentaje / 100.0));
    return costo_total;
}
