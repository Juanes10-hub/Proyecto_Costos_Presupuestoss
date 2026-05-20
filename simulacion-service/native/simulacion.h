#ifndef SIMULACION_H
#define SIMULACION_H

typedef struct {
    double costo_optimista;
    double costo_realista;
    double costo_pesimista;
    double promedio;
    double rango_variacion;
} ResultadoSimulacion;

// Calcula 3 escenarios
int calcular_simulacion(int equipo, int duracion_meses, double salario_promedio,
                        double overhead_porcentaje, ResultadoSimulacion *resultado);

#endif
