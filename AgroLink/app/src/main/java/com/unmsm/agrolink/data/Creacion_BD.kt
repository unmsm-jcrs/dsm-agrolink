// DatabaseHelper.kt

package com.unmsm.agrolink.data

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import com.unmsm.agrolink.models.Cultivo
import com.unmsm.agrolink.models.Actividad

class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "AgroApp.db"
        const val DATABASE_VERSION = 2 // Incrementamos la versión
    }

    override fun onCreate(db: SQLiteDatabase) {
        db.execSQL("""
            CREATE TABLE usuarios (
                id_usuario INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre TEXT NOT NULL,
                email TEXT UNIQUE NOT NULL,
                contrasena TEXT NOT NULL,
                fecha_registro DATETIME DEFAULT CURRENT_TIMESTAMP,
                ubicacion TEXT
            )
        """)

        db.execSQL("""
    CREATE TABLE cultivos (
         id_cultivo INTEGER PRIMARY KEY AUTOINCREMENT,
    id_usuario INTEGER NOT NULL,
    tipo_cultivo TEXT NOT NULL,
    cantidad REAL NOT NULL,
    fecha_siembra DATE NOT NULL,
    estado INTEGER DEFAULT 8, -- 1: cosechado, 2: malogrado, 8: en proceso
    visibilidad INTEGER DEFAULT 1, -- 0=oculto, 1=visible
    fecha_cosechado DATE NOT NULL, -- Fecha de cosecha obligatoria
    FOREIGN KEY (id_usuario) REFERENCES usuarios (id_usuario) ON DELETE CASCADE
)
""")

        db.execSQL("""
            CREATE TABLE actividades_agricolas (
                id_actividad INTEGER PRIMARY KEY AUTOINCREMENT,
                id_cultivo INTEGER NOT NULL,
                tipo_actividad TEXT NOT NULL,
                fecha DATE NOT NULL,
                nota TEXT,
                cantidad REAL,
                FOREIGN KEY (id_cultivo) REFERENCES cultivos (id_cultivo) ON DELETE CASCADE
            )
        """)

        db.execSQL("""
            CREATE TABLE notificaciones (
                id_notificacion INTEGER PRIMARY KEY AUTOINCREMENT,
                id_usuario INTEGER NOT NULL,
                mensaje TEXT NOT NULL,
                tipo TEXT NOT NULL,
                fecha_envio DATETIME DEFAULT CURRENT_TIMESTAMP,
                estado TEXT DEFAULT 'no_leido',
                FOREIGN KEY (id_usuario) REFERENCES usuarios (id_usuario) ON DELETE CASCADE
            )
        """)

        db.execSQL("""
            CREATE TABLE clima (
                id_clima INTEGER PRIMARY KEY AUTOINCREMENT,
                id_usuario INTEGER NOT NULL,
                fecha DATE NOT NULL,
                temperatura REAL,
                humedad REAL,
                pronostico TEXT,
                FOREIGN KEY (id_usuario) REFERENCES usuarios (id_usuario) ON DELETE CASCADE
            )
        """)

        db.execSQL("""
            CREATE TABLE reportes (
                id_reporte INTEGER PRIMARY KEY AUTOINCREMENT,
                id_usuario INTEGER NOT NULL,
                id_cultivo INTEGER NOT NULL,
                fecha_generacion DATETIME DEFAULT CURRENT_TIMESTAMP,
                formato TEXT NOT NULL,
                contenido BLOB,
                FOREIGN KEY (id_usuario) REFERENCES usuarios (id_usuario) ON DELETE CASCADE,
                FOREIGN KEY (id_cultivo) REFERENCES cultivos (id_cultivo) ON DELETE CASCADE
            )
        """)

        db.execSQL("""
            CREATE TABLE sesiones (
                id_sesion INTEGER PRIMARY KEY AUTOINCREMENT,
                id_usuario INTEGER NOT NULL,
                fecha_inicio DATETIME DEFAULT CURRENT_TIMESTAMP,
                fecha_fin DATETIME,
                estado_sesion TEXT DEFAULT 'activa',
                FOREIGN KEY (id_usuario) REFERENCES usuarios (id_usuario) ON DELETE CASCADE
            )
        """)
    }

    override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
        // Eliminar tablas y recrearlas
        db.execSQL("DROP TABLE IF EXISTS sesiones")
        db.execSQL("DROP TABLE IF EXISTS reportes")
        db.execSQL("DROP TABLE IF EXISTS clima")
        db.execSQL("DROP TABLE IF EXISTS notificaciones")
        db.execSQL("DROP TABLE IF EXISTS actividades_agricolas")
        db.execSQL("DROP TABLE IF EXISTS cultivos")
        db.execSQL("DROP TABLE IF EXISTS usuarios")
        onCreate(db)
    }

    // Método para insertar un cultivo
    fun insertCultivo(idUsuario: Int, tipoCultivo: String, cantidad: Double, fechaSiembra: String, visibilidad: Int, estado: Int, fechaCosechado: String): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("id_usuario", idUsuario) // ID del usuario asociado al cultivo
            put("tipo_cultivo", tipoCultivo) // Tipo de cultivo
            put("cantidad", cantidad) // Cantidad sembrada
            put("fecha_siembra", fechaSiembra) // Fecha de siembra
            put("visibilidad", visibilidad) // Nuevo: visibilidad del cultivo (0=oculto, 1=visible)
            put("estado", estado) // Estado del cultivo
            put("fecha_cosechado", fechaCosechado) // Nuevo: fecha de cosecha
        }
        val newRowId = db.insert("cultivos", null, values) // Inserta los datos
        db.close() // Cierra la conexión
        return newRowId // Devuelve el ID de la nueva fila
    }

    // Método para obtener todos los cultivos de un usuario específico
    fun getCultivosPorUsuario(idUsuario: Int): List<Cultivo> {
        val cultivos = mutableListOf<Cultivo>() // Lista para almacenar los cultivos
        val db = readableDatabase
        val cursor = db.query(
            "cultivos", // Tabla de la consulta
            null, // Todas las columnas
            "id_usuario = ?", // Condición: usuario específico
            arrayOf(idUsuario.toString()), // Sustituye el "?" por el ID del usuario
            null,
            null,
            null
        )
        with(cursor) {
            while (moveToNext()) {
                val cultivo = Cultivo(
                    idCultivo = getInt(getColumnIndexOrThrow("id_cultivo")), // ID del cultivo
                    idUsuario = getInt(getColumnIndexOrThrow("id_usuario")), // ID del usuario
                    tipoCultivo = getString(getColumnIndexOrThrow("tipo_cultivo")), // Tipo de cultivo
                    cantidad = getDouble(getColumnIndexOrThrow("cantidad")), // Cantidad sembrada
                    fechaSiembra = getString(getColumnIndexOrThrow("fecha_siembra")), // Fecha de siembra
                    visibilidad = getInt(getColumnIndexOrThrow("visibilidad")), // Nuevo: visibilidad
                    estado = getInt(getColumnIndexOrThrow("estado")), // Estado del cultivo
                    fechaCosechado = getString(getColumnIndexOrThrow("fecha_cosechado")) // Nuevo: fecha de cosecha
                )
                cultivos.add(cultivo) // Agrega el cultivo a la lista
            }
        }
        cursor.close() // Cierra el cursor
        db.close() // Cierra la conexión
        return cultivos // Devuelve la lista de cultivos
    }


    // Método para obtener un cultivo por su ID
    fun getCultivoById(idCultivo: Int): Cultivo? {
        val db = readableDatabase
        val cursor = db.query(
            "cultivos", // Tabla
            null, // Todas las columnas
            "id_cultivo = ?", // Condición por ID del cultivo
            arrayOf(idCultivo.toString()), // Sustituye el "?" por el ID del cultivo
            null,
            null,
            null
        )
        var cultivo: Cultivo? = null
        if (cursor.moveToFirst()) {
            cultivo = Cultivo(
                idCultivo = cursor.getInt(cursor.getColumnIndexOrThrow("id_cultivo")), // ID del cultivo
                idUsuario = cursor.getInt(cursor.getColumnIndexOrThrow("id_usuario")), // ID del usuario
                tipoCultivo = cursor.getString(cursor.getColumnIndexOrThrow("tipo_cultivo")), // Tipo de cultivo
                cantidad = cursor.getDouble(cursor.getColumnIndexOrThrow("cantidad")), // Cantidad sembrada
                fechaSiembra = cursor.getString(cursor.getColumnIndexOrThrow("fecha_siembra")), // Fecha de siembra
                visibilidad = cursor.getInt(cursor.getColumnIndexOrThrow("visibilidad")), // Nuevo: visibilidad
                estado = cursor.getInt(cursor.getColumnIndexOrThrow("estado")), // Estado del cultivo
                fechaCosechado = cursor.getString(cursor.getColumnIndexOrThrow("fecha_cosechado")) // Nuevo: fecha de cosecha
            )
        }
        cursor.close() // Cierra el cursor
        db.close() // Cierra la conexión
        return cultivo // Devuelve el cultivo, o null si no existe
    }


    // Método para insertar una actividad
    fun insertActividad(cultivoId: Int, tipoActividad: String, fecha: String, nota: String?, cantidad: Double?): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("id_cultivo", cultivoId)
            put("tipo_actividad", tipoActividad)
            put("fecha", fecha)
            put("nota", nota)
            put("cantidad", cantidad)
        }
        val newRowId = db.insert("actividades_agricolas", null, values)
        db.close()
        return newRowId
    }

    // Método para obtener todas las actividades de un cultivo específico
    fun getActividadesPorCultivo(cultivoId: Int): List<Actividad> {
        val actividades = mutableListOf<Actividad>()
        val db = readableDatabase
        val cursor = db.query(
            "actividades_agricolas",
            null,
            "id_cultivo = ?",
            arrayOf(cultivoId.toString()),
            null,
            null,
            null
        )
        with(cursor) {
            while (moveToNext()) {
                val actividad = Actividad(
                    idActividad = getInt(getColumnIndexOrThrow("id_actividad")),
                    idCultivo = getInt(getColumnIndexOrThrow("id_cultivo")),
                    tipoActividad = getString(getColumnIndexOrThrow("tipo_actividad")),
                    fecha = getString(getColumnIndexOrThrow("fecha")),
                    nota = getString(getColumnIndexOrThrow("nota")),
                    cantidad = getDouble(getColumnIndexOrThrow("cantidad"))
                )
                actividades.add(actividad)
            }
        }
        cursor.close()
        db.close()
        return actividades
    }

    // Método para insertar una notificación
    fun insertNotificacion(idUsuario: Int, mensaje: String, tipo: String): Long {
        val db = writableDatabase
        val values = ContentValues().apply {
            put("id_usuario", idUsuario)
            put("mensaje", mensaje)
            put("tipo", tipo)
        }
        val newRowId = db.insert("notificaciones", null, values)
        db.close()
        return newRowId
    }

    // Método para registrar un nuevo usuario con manejo de excepciones
    fun registerUser(nombre: String, email: String, contrasena: String): Boolean {
        return try {
            val db = writableDatabase
            val values = ContentValues().apply {
                put("nombre", nombre)
                put("email", email)
                put("contrasena", contrasena)
            }
            val newRowId = db.insert("usuarios", null, values)
            db.close()
            newRowId != -1L // Retorna true si el registro fue exitoso
        } catch (e: Exception) {
            Log.e("DatabaseHelper", "Error al registrar usuario", e)
            false
        }
    }

    // Método para verificar las credenciales de inicio de sesión
    fun verificarCredenciales(email: String, contrasena: String): Int? {
        val db = readableDatabase
        val cursor = db.query(
            "usuarios",
            arrayOf("id_usuario"),
            "email = ? AND contrasena = ?",
            arrayOf(email, contrasena),
            null,
            null,
            null
        )
        val userId = if (cursor.moveToFirst()) {
            cursor.getInt(cursor.getColumnIndexOrThrow("id_usuario"))
        } else {
            null
        }
        cursor.close()
        db.close()
        return userId
    }

    // Método para obtener todas las notificaciones de un usuario específico
    fun getNotificacionesPorUsuario(idUsuario: Int): List<String> {
        val notificaciones = mutableListOf<String>()
        val db = readableDatabase
        val cursor = db.query(
            "notificaciones",
            arrayOf("mensaje"),
            "id_usuario = ?",
            arrayOf(idUsuario.toString()),
            null,
            null,
            null
        )
        with(cursor) {
            while (moveToNext()) {
                notificaciones.add(getString(getColumnIndexOrThrow("mensaje")))
            }
        }
        cursor.close()
        db.close()
        return notificaciones
    }

    fun deleteCultivo(idCultivo: Int) {
        val db = writableDatabase
        db.delete("cultivos", "id_cultivo = ?", arrayOf(idCultivo.toString()))
        db.close()
    }

    fun deleteActividad(idActividad: Int) {
        val db = writableDatabase
        db.delete("actividades_agricolas", "id_actividad = ?", arrayOf(idActividad.toString()))
        db.close()
    }
}