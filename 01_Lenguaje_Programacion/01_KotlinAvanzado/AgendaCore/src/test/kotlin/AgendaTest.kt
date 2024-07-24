import edu.uvg.ppm2024.*
import kotlin.test.Test
import kotlin.test.assertNotNull

class AgendaTest {

    @Test
    fun testAgregarContacto() {
        val name = "Moises"
        val lastname = "Alonso"
        val phones = mutableListOf<String>("31209493", "12345678")
        val emails = mutableListOf<String>("maalonso@uvg.edu.gt", "moises.alonso.catedras@gmail.com")
        val contacto = Contacto(name, lastname, phones, emails)
        Agenda.agregarContacto(contacto)
        val contactoBuscado: Contacto? = Agenda.buscarContacto("Moises", "Alonso")
        assertNotNull(contactoBuscado)
    }

    @Test
    fun testAgregarEvento() {
        val evento = Evento("Reunión", "2024-08-01", "Reunión con el equipo", TipoEvento.REUNION)
        Agenda.agregarEvento(evento)
        assertNotNull(Agenda.obtenerEvento(0))

    }

}