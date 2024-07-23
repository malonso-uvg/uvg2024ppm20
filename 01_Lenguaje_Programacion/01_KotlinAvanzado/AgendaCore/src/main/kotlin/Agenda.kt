package edu.uvg.ppm2024

/***
 * Ejemplo de funcion de extension para Contacto
 */
fun Contacto.mostrarDetalles() {
    println("Nombre: $nombre, Teléfono: $telefono, Email: $email")
}

/***
 * Debido a que solo se tendra una sola agenta, creo un unico objeto para agenda
 */
object Agenda {

    private val contactos = mutableListOf<Contacto>()
    private val eventos = mutableListOf<Evento>()

    fun agregarContacto(contacto: Contacto) {
        contactos.add(contacto)
    }

    fun agregarEvento(evento: Evento) {
        eventos.add(evento)
    }

    fun mostrarContactos() {
        contactos.forEach { it.mostrarDetalles() }
    }

    fun mostrarEventos() {
        eventos.forEach { println(it.titulo) }
    }

}