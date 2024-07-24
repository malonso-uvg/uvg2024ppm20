package edu.uvg.ppm2024

/***
 * Ejemplo de data class, guarda los contactos por nombre, apellido, telefonos y correos electronicos
 */
data class Contacto(val nombre: String, val apellido: String, val telefono: MutableList<String>, val email: MutableList<String>)

/***
 * Ejemplo de interfaz, servira para notificar de acuerdo a la configuracion establecida
 */
interface Notificable {
    fun notificar()
}

/***
 * Ejemplo de clase que podra tener subclases, de acuerdo a la palabra open, implementa la interfaz notificable
 */
open class Evento(val titulo: String, val fecha: String, val descripcion: String, val tipo: TipoEvento) : Notificable {
    override fun notificar() {
        println("Notificación del evento: $titulo")
    }

    companion object {
        const val DEFAULT_DESCRIPCION = "Sin descripción"
    }
}


/***
 * Ejemplo de una subclase de la clase Evento, incluye sus propiedades y metodos
 */
class EventoImportante(titulo: String, fecha: String, descripcion: String, tipo: TipoEvento, val prioridad: Int) : Evento(titulo, fecha, descripcion, tipo)

/***
 * Ejemplo de colecciones ENUM
 */
enum class TipoEvento {
    REUNION, CUMPLEANOS, ANIVERSARIO, OTRO
}


