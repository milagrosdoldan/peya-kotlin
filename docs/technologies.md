# Tecnologías utilizadas en PeyaDemoApp

## Lenguaje principal

**Kotlin**  
Elegido por su sintaxis concisa, interoperabilidad con Java y soporte oficial en Android, facilitando un desarrollo moderno y eficiente.

## Versiones

- **SDK mínimo:** 24 (Android 7.0)  
  Para asegurar compatibilidad con la mayoría de dispositivos activos sin sacrificar acceso a nuevas APIs.
- **Versión de Java:** 17  
  Por soporte a características modernas del lenguaje y optimizaciones de rendimiento.

## Arquitectura adoptada

**MVVM (Model-View-ViewModel)**  
Separa la lógica de negocio, UI y datos para facilitar pruebas, mantenimiento y escalabilidad del proyecto.

## Frameworks y librerías clave

- **Hilt**  
  Simplifica la inyección de dependencias en Android, reduciendo código boilerplate y facilitando la gestión de ciclos de vida.

- **Retrofit (con Gson)**  
  Permite consumir APIs REST de forma eficiente y sencilla, con soporte para serialización JSON mediante Gson.

- **Room**  
  Proporciona una capa de abstracción sobre SQLite para manejo seguro y eficiente de bases de datos locales.

- **Navigation Component**  
  Facilita la navegación entre pantallas, manejo del back stack y acciones declarativas, mejorando la UX y mantenimiento.

- **ViewModel y LiveData**  
  Gestionan y observan datos con respeto al ciclo de vida de la UI, evitando fugas de memoria y mejorando la reactividad.

- **Glide**  
  Librería optimizada para la carga y cacheo eficiente de imágenes en Android.

- **Material Components**  
  Garantizan una interfaz moderna y consistente conforme a las guías de diseño de Google.

---

Este conjunto de tecnologías asegura un proyecto robusto, mantenible y alineado con las mejores prácticas actuales en desarrollo Android.
