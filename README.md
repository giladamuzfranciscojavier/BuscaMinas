# Buscaminas
Versión del clásico juego de ordenador desarrollada para el módulo de Programación de DAM. Si bien este proyecto solo cuenta con interfaz de usuario por consola, al estar estructurado en una arquitectura por capas sería posible desarrollar versiones con interfaz de usuario gráfica empleando la misma lógica de negocio.

## Problemas conocidos
- Los tableros grandes no se muestran correctamente por ajuste de línea. La única solución posible sería limitar el tamaño.
- Los tableros exageradamente grandes provocan desbordamiento de pila ya que el algoritmo de detección de minas funciona mediante recursión. Una solución sería mejorar el funcionamiento de dicho algoritmo, aunque considerando que solo supone un problema en casos de uso extremos y el problema indicado en el punto anterior, lo más lógico sería limitar el tamaño

Si bien son problemas bastante sencillos de solucionar, el proyecto se mantendrá tal y como está por cuestiones de preservación.
