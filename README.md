# 🌱 AgroLink

AgroLink es una aplicación móvil diseñada para facilitar la gestión de cultivos y actividades agrícolas. Con AgroLink, los agricultores pueden llevar un registro detallado de sus cultivos, programar actividades como riego y fertilización, y obtener información climática relevante, todo desde su dispositivo móvil.

## 🚀 Funcionalidades

### Casos de Uso Implementados

- **CUS 1: Iniciar Sesión**
  - 🔑 **Descripción**: Acceso seguro a la aplicación mediante usuario y contraseña.
  - 👤 **Actor**: Usuario
  - ✅ **Precondición**: El usuario debe tener una cuenta activa en la aplicación.

- **CUS 2: Registrarse**
  - 📝 **Descripción**: Creación de cuenta para nuevos usuarios.
  - 👤 **Actor**: Usuario
  - ✅ **Precondición**: No debe existir una cuenta asociada.

- **CUS 3: Agregar Cultivo**
  - 🌾 **Descripción**: Permite agregar un nuevo cultivo a la lista de gestión.
  - 👤 **Actor**: Usuario
  - ✅ **Precondición**: El usuario debe haber iniciado sesión.

### Casos de Uso Pendientes

- **CUS 4: Consultar Cultivos**
  - 📋 **Descripción**: Visualización de la lista de cultivos y sus actividades registradas.
  - 👤 **Actor**: Usuario
  - ✅ **Precondición**: Usuario registrado y con cultivos.

- **CUS 5: Registrar Actividad Agrícola**
  - 💧 **Descripción**: Registro de actividades como riego y fertilización.
  - 👤 **Actor**: Usuario
  - ✅ **Precondición**: Usuario registrado y con cultivos.

- **CUS 6: Recibir Notificaciones**
  - 🔔 **Descripción**: Notificaciones de alerta para actividades como riego y detección de plagas.
  - 👤 **Actor**: Usuario
  - ✅ **Precondición**: Usuario registrado y con cultivos.

- **CUS 7: Ver Clima**
  - ☀️ **Descripción**: Información climática actual y pronóstico para la ubicación.
  - 👤 **Actor**: Usuario
  - ✅ **Precondición**: Usuario registrado.

- **CUS 8: Generar Reporte de Cultivos**
  - 📈 **Descripción**: Generación de reporte completo de cultivos con estadísticas.
  - 👤 **Actor**: Usuario
  - ✅ **Precondición**: Usuario registrado con al menos un cultivo.

## 📋 Requisitos del Sistema

- **Android Studio**: Koala | 2024.1.1
- **SDK Objetivo**: 34
- **SDK Mínimo**: 24
- **SDK de Compilación**: 34

## 🛠️ Instalación

1. Clona el repositorio:
   ```bash
   git clone https://github.com/usuario/agrolink.git
