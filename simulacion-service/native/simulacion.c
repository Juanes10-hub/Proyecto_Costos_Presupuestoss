#include "simulacion.h"
#include <math.h>

int calcular_simulacion(int equipo, int duracion_meses, double salario_promedio,
                        double overhead_porcentaje, ResultadoSimulacion *resultado) {
    
    if (!resultado || equipo <= 0 || duracion_meses <= 0) return -1;
    
    double costo_base = equipo * duracion_meses * salario_promedio;
    double overhead = costo_base * (overhead_porcentaje / 100.0);
    
    // Escenario Optimista: -20% variación
    resultado->costo_optimista = (costo_base + overhead) * 0.80;
    
    // Escenario Realista: costo base + overhead
    resultado->costo_realista = costo_base + overhead;
    
    // Escenario Pesimista: +40% variación
    resultado->costo_pesimista = (costo_base + overhead) * 1.40;
    
    // Promedio
    resultado->promedio = (resultado->costo_optimista + resultado->costo_realista + resultado->costo_pesimista) / 3.0;
    
    // Rango de variación
    resultado->rango_variacion = resultado->costo_pesimista - resultado->costo_optimista;
    
    return 0;
}
