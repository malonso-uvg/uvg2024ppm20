package edu.uvg.ppm2024

fun addContact(){
    var nombre: String = ""
    var apellido: String = ""
    val telefonos = mutableListOf<String>()
    val emails = mutableListOf<String>()

    println("Ingrese el nombre del contacto")
    nombre = readln()
    println("Ingrese el apellido del contacto")
    apellido = readln()
    val nuevoContacto: Contacto = Contacto(nombre, apellido, telefonos, emails)

    var entrada: String = ""
    while (!entrada.uppercase().equals("NO")){
        println("Ingrese el numero de telefono o NO para salir")
        entrada = readln()

        if (!entrada.uppercase().equals("NO")){
            nuevoContacto.telefono.add(entrada)
        }
    }

    entrada = ""
    while (!entrada.uppercase().equals("NO")){
        println("Ingrese el correo electronico o NO para salir")
        entrada = readln()

        if (!entrada.uppercase().equals("NO")){
            nuevoContacto.email.add(entrada)
        }
    }

    Agenda.agregarContacto(nuevoContacto)

    println("Contacto Guardado!")
}

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
fun main() {

    println("Bienvenido a el programa de agenda electronica")

    var opt:String = ""

    while (!opt.uppercase().equals("SALIR")){
        println("Seleccione su opcion")
        println("1. Agregar un contacto")
        println("2. Mostrar agenda")
        println("Escriba SALIR para finalizar el programa")

        opt = readln().uppercase()

        when(opt){
            "1" -> addContact()
            "2" -> Agenda.mostrarContactos()
            "SALIR" -> println("Hasta Luego")
        }

    }


}
