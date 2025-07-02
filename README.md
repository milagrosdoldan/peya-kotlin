readme_content = """
# PeyaDemoApp

Aplicación Android desarrollada en Kotlin como demo de arquitectura moderna utilizando Hilt, Room, Retrofit y Navigation Component.

## 📦 Estructura del Proyecto

app/
├── data/ # Lógica de acceso a datos (API, DB)
│ ├── api/ # Interfaces de Retrofit
│ ├── db/ # Entidades y DAOs de Room
│ └── repository/ # Repositorios que combinan fuentes de datos
├── di/ # Módulos de Hilt para inyección de dependencias
├── domain/ # Lógica de negocio (opcional)
├── ui/ # Capas de presentación (Vie[tecnologias.md](docs/tecnologias.md)wModels, Fragments, Activities)
├── utils/ # Utilidades y extensiones comunes
└── MainActivity.kt # Entrada principal de la app


## 🧰 Tecnologías utilizadas

- **Kotlin**: Lenguaje principal
- **Android Jetpack**:
    - ViewModel, LiveData
    - Navigation Component
    - Room (persistencia local)
- **Hilt**: Inyección de dependencias
- **Retrofit + Gson**: Consumo de APIs REST
- **Glide**: Carga de imágenes
- **Material Components**: Interfaz moderna
- **ViewBinding**: Referencias seguras a vistas

## 🚀 Requisitos

- Android Studio Hedgehog (o superior)
- Gradle 8.9+
- JDK 17
- Mínimo SDK: 24
- Target SDK: 34


