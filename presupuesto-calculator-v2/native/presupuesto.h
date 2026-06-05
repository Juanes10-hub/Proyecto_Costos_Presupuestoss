#ifndef PRESUPUESTO_H
#define PRESUPUESTO_H

double calcular_personal(int meses, int equipo, double salario);
double calcular_infraestructura(int meses, double infra_mensual);
double calcular_licencias(int meses, double licencias_mensuales);
double calcular_subtotal(double personal, double infra, double licencias);
double calcular_contingencia(double subtotal, double porcentaje);
double calcular_total(double subtotal, double contingencia);

#endif
