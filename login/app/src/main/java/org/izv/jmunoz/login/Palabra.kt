package org.izv.jmunoz.login

class Palabra {
    private lateinit var nombre: String
    private lateinit var id: Integer

    constructor()

    constructor(nombre: String, id: Int)

    override fun toString(): String {
        return "Palabra(nombre='$nombre', id=$id)"
    }

    fun getDB(): List<String> {
        return listOf(
            /*
            pegar 1 pagina, ejecutar, borrar la pagina y pegar la siguiente y ejecutar asi sucesivamente,
            el programa no tiene potencia para añadir tantas palabras a la vez, por eso 1 pagina en cada               ejecución
            */
        )
    }



}