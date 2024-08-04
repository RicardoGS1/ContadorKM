
# ContadorKM

Es una aplicación de seguimiento de actividad física que utiliza tecnologías modernas de Android, como Jetpack Compose, la arquitectura MVVM y la API de Google Maps. La aplicación permite a los usuarios realizar un seguimiento de sus actividades de carrera, mostrando rutas en tiempo real en un mapa interactivo y almacenando estadísticas esenciales mediante la base de datos de Room.

## Características
1. Seguimiento en vivo de la actividad de carrera mediante GPS.
2. Seguimiento de la ruta de carrera del usuario en el mapa mediante la biblioteca Google Map Compose.
3. Al utilizar el servicio de primer plano, incluso si el usuario cierra la aplicación y la elimina del segundo plano, esta aplicación continúa rastreando las estadísticas de ejecución del usuario.
4. Base de datos de para almacenar y gestionar estadísticas de ejecución.
5. Manejo de navegación mediante el componente de navegación Jetpack.
6. Nuevo selector de imágenes de Jetpack Compose: ayuda a seleccionar imágenes sin ningún permiso.
7. Soporte de color dinámico en temas oscuros y claros.
8. Estadísticas semanales, mensuales, anuales y general.


## Capturas

|                                                                                                                         |                                                                                                                           |
|-------------------------------------------------------------------------------------------------------------------------|---------------------------------------------------------------------------------------------------------------------------|
| ![run_track_home_ss](https://github.com/user-attachments/assets/3ffc4843-c56b-460e-b3b8-bfdfef19ad4c)  | ![runtrack_live_tracking_ss](https://github.com/user-attachments/assets/02594c14-9ead-4cf9-aa96-061005f059b3) |

### Google Map Integracion

Sigue estos pasos si quieres mostrar Google Maps.

1. Crea y obtén la clave API de Google Maps usando esta.
   [guide](https://developers.google.com/maps/documentation/android-sdk/get-api-key)
2. Abre el archivo `local.properties`.
3. Ingresa tu clave API de esta manera::

```
MAPS_API_KEY=your_maps_api_key
```
