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

    fun buscarContacto(nombre: String, apellido: String): Contacto? {
        for (contact in contactos){
            if (contact.nombre.uppercase().equals(nombre.uppercase())
                && contact.apellido.uppercase().equals(apellido.uppercase())) {
                return contact
            }
        }

        return null
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

    fun obtenerEvento(index: Int): Evento{
        return eventos.get(index)
    }

}