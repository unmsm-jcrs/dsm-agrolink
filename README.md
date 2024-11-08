# AgroLink

AgroLink es una aplicación móvil diseñada para gestionar diversos aspectos de la agricultura, brindando a los usuarios la capacidad de administrar y registrar sus cultivos de manera eficiente. La aplicación permite realizar un seguimiento de las actividades agrícolas, como riego o fertilización, y consultar el clima, entre otras funcionalidades.

## Casos de Uso Implementados

### CUS 1: Iniciar Sesión
- **Descripción**: Permite a los usuarios acceder a la aplicación mediante un nombre de usuario y contraseña.
- **Actor principal**: Usuario
- **Precondiciones**: El usuario debe tener una cuenta creada en la aplicación.

### CUS 2: Registrarse
- **Descripción**: Permite a nuevos usuarios crear una cuenta en la aplicación.
- **Actor principal**: Usuario
- **Precondiciones**: El usuario no debe tener una cuenta existente.

### CUS 3: Agregar Cultivo
- **Descripción**: Permite a los usuarios agregar un nuevo cultivo a su lista.
- **Actor principal**: Usuario
- **Precondiciones**: El usuario debe haber iniciado sesión.

## Casos de Uso Pendientes de Implementación

### CUS 4: Consultar Cultivos
- **Descripción**: Permite a los usuarios ver una lista de sus cultivos y las actividades agrícolas registradas de cada uno.
- **Actor principal**: Usuario
- **Precondiciones**: El usuario debe haber iniciado sesión y tener cultivos registrados.

### CUS 5: Registrar Actividad Agrícola
- **Descripción**: Permite a los usuarios registrar actividades relacionadas con sus cultivos, como riego o fertilización.
- **Actor principal**: Usuario
- **Precondiciones**: El usuario debe haber iniciado sesión y tener cultivos registrados.

### CUS 6: Recibir Notificaciones
- **Descripción**: Permite a los usuarios recibir notificaciones sobre sus cultivos, como alertas de riego o plagas.
- **Actor principal**: Usuario
- **Precondiciones**: El usuario debe haber iniciado sesión y tener cultivos registrados.

### CUS 7: Ver Clima
- **Descripción**: Permite a los usuarios consultar las condiciones climáticas actuales y pronósticos para su ubicación utilizando una API de clima.
- **Actor principal**: Usuario
- **Precondiciones**: El usuario debe haber iniciado sesión.

### CUS 8: Generar Reporte de Cultivos
- **Descripción**: Permite a los usuarios generar un reporte completo sobre sus cultivos, incluyendo estadísticas y actividades registradas.
- **Actor principal**: Usuario
- **Precondiciones**: El usuario debe haber iniciado sesión y tener al menos un cultivo registrado.

## Requisitos

- **Android Studio**: Koala | 2024.1.1
- **Target SDK Version**: 34
- **Min SDK Version**: 24
- **Compile SDK Version**: 34

## Instalación

1. Clona este repositorio.
   ```bash
   git clone https://github.com/usuario/agrolink.git
