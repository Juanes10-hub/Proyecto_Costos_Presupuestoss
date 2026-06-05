#include "presupuesto.h"

double calcular_personal(int meses, int equipo, double salario) {
    return meses * equipo * salario;
}

double calcular_infraestructura(int meses, double infra_mensual) {
    return meses * infra_mensual;
}

double calcular_licencias(int meses, double licencias_mensuales) {
    return meses * licencias_mensuales;
}

double calcular_subtotal(double personal, double infra, double licencias) {
    return personal + infra + licencias;
}

double calcular_contingencia(double subtotal, double porcentaje) {
    return subtotal * (porcentaje / 100.0);
}

double calcular_total(double subtotal, double contingencia) {
    return subtotal + contingencia;
}
