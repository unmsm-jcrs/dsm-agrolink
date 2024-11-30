# 🌱 AgroLink

AgroLink es una aplicación móvil diseñada para facilitar la gestión de cultivos y actividades agrícolas. Con AgroLink, los agricultores pueden llevar un registro detallado de sus cultivos, programar actividades como riego y fertilización, y obtener información climática relevante, todo desde su dispositivo móvil.

## 🚀 Funcionalidades

### Casos de Uso Implementados

- **CUS 1: Iniciar Sesión**
  - 🔑 **Descripción**: Permite a los usuarios acceder a la aplicación mediante un nombre de usuario y contraseña.
  - 👤 **Actor**: Usuario
  - ✅ **Precondición**: El usuario debe tener una cuenta creada en la aplicación.

- **CUS 2: Registrarse**
  - 📝 **Descripción**: Permite a nuevos usuarios crear una cuenta en la aplicación proporcionando su información personal.
  - 👤 **Actor**: Usuario
  - ✅ **Precondición**: El usuario no debe tener una cuenta existente.

- **CUS 3: Gestionar Cultivos**
  - 🌾 **Descripción**: Permite agregar, borrar cultivos y gestionar las actividades asociadas (riego, fertilización, etc.).
  - 👤 **Actor**: Usuario
  - ✅ **Precondición**: El usuario debe haber iniciado sesión.

- **CUS 4: Gestionar Cosecha**
  - 🌾 **Descripción**: Permite registrar la cosecha de un cultivo o desecharlo si no es viable.
  - 👤 **Actor**: Usuario
  - ✅ **Precondición**: El usuario debe haber iniciado sesión y tener cultivos registrados.

- **CUS 5: Ver Clima**
  - ☀️ **Descripción**: Permite consultar las condiciones climáticas actuales y los pronósticos para la ubicación del usuario.
  - 👤 **Actor**: Usuario
  - ✅ **Precondición**: El usuario debe haber iniciado sesión y permitir el acceso a su ubicación.

### Casos de Uso Pendientes

- **CUS 6: Generar Reporte de Cultivos**
  - 📈 **Descripción**: Permite generar un reporte sobre los cultivos, mostrando estadísticas relevantes.
  - 👤 **Actor**: Usuario
  - ✅ **Precondición**: El usuario debe haber iniciado sesión y tener al menos un cultivo registrado.

## 📋 Requisitos del Sistema

- **Android Studio**: Koala | 2024.1.1
- **SDK Objetivo**: 34
- **SDK Mínimo**: 24
- **SDK de Compilación**: 34

## 🛠️ Instalación

1. Clona el repositorio:
   ```bash
   git clone https://github.com/usuario/agrolink.git
