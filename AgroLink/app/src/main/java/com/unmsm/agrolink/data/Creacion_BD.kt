import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper


class DatabaseHelper(context: Context) : SQLiteOpenHelper(context, DATABASE_NAME, null, DATABASE_VERSION) {

    companion object {
        const val DATABASE_NAME = "AgroApp.db"
        const val DATABASE_VERSION = 1
    }

    override fun onCreate(db: SQLiteDatabase) {
        // Tabla Usuarios
        db.execSQL("""
            CREATE TABLE usuarios (
                id_usuario INTEGER PRIMARY KEY AUTOINCREMENT,
                nombre TEXT NOT NULL,
                email TEXT UNIQUE NOT NULL,
                contraseña TEXT NOT NULL,
                fecha_registro DATETIME DEFAULT CURRENT_TIMESTAMP,
                ubicacion TEXT
            )
        """)

        // Tabla Cultivos
        db.execSQL("""
            CREATE TABLE cultivos (
                id_cultivo INTEGER PRIMARY KEY AUTOINCREMENT,
                id_usuario INTEGER NOT NULL,
                tipo_cultivo TEXT NOT NULL,
                cantidad REAL NOT NULL,
                fecha_siembra DATE NOT NULL,
                estado TEXT DEFAULT 'activo',
                FOREIGN KEY (id_usuario) REFERENCES usuarios (id_usuario) ON DELETE CASCADE
            )
        """)

        // Tabla Actividades Agrícolas
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

        // Tabla Notificaciones
        db.execSQL("""
            CREATE TABLE notificaciones (
                id_notificacion INTEGER PRIMARY KEY AUTOINCREMENT,
                id_usuario INTEGER NOT NULL,
                mensaje TEXT NOT NULL,
                tipo TEXT NOT NULL,
                fecha_envio DATETIME DEFAULT CURRENT_TIMESTAMP,
                estado TEXT DEFAULT 'no leído',
                FOREIGN KEY (id_usuario) REFERENCES usuarios (id_usuario) ON DELETE CASCADE
            )
        """)

        // Tabla Clima
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

        // Tabla Reportes
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

        // Tabla Sesiones
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
        // Elimina las tablas si existen y crea nuevas
        db.execSQL("DROP TABLE IF EXISTS usuarios")
        db.execSQL("DROP TABLE IF EXISTS cultivos")
        db.execSQL("DROP TABLE IF EXISTS actividades_agricolas")
        db.execSQL("DROP TABLE IF EXISTS notificaciones")
        db.execSQL("DROP TABLE IF EXISTS clima")
        db.execSQL("DROP TABLE IF EXISTS reportes")
        db.execSQL("DROP TABLE IF EXISTS sesiones")
        onCreate(db)
    }
}

