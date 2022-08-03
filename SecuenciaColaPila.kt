import java.util.LinkedList

/**
 * Clase abstracta que modela una secuencia
 */
abstract class Secuencia<T> {

    /**
     * Función que agrega un elemento a la secuencia
     * Args:
     *   e (T): Elemento a agregar
     */
    abstract fun agregar(e: T)

    /**
     * Función que remueve un elemento de la secuencia
     * Retorna: 
     *   T: elemento a ser retornado
     */
    abstract fun remover() : T

    /**
     * Función que determina si la secuencia está vacía
     * Retorna:
     *   Boolean: True si está vacía, False si no.
     */
    abstract fun vacio() : Boolean
}

/**
 * Clase que implementa una Pila, hereda de secuencia
 */
class Pila<T> : Secuencia<T>() {
    private var pila : LinkedList<T> = LinkedList<T>()
    
    /**
     * Función que empila un elemento en la pila
     * Args:
     *   e (T): Elemento a empilar
     */
    override fun agregar(e: T) { pila.add(e) }
    
    /**
     * Función que desempila un elemento de la pila
     * Retorna: 
     *   T: elemento a ser desempilado
     */
    override fun remover() : T {
        if (vacio()) { throw RuntimeException("Error, la pila está vacía.") }
         return pila.removeLast()
    }
    
    /**
     * Función que determina si la secuencia está vacía
     * Retorna:
     *   Boolean: True si está vacía, False si no.
     */
    override fun vacio() : Boolean = (pila.size == 0)
}

/**
 * Clase que implementa una Cola, hereda de secuencia
 */
class Cola<T> : Secuencia<T>() {
    private var cola : LinkedList<T> = LinkedList<T>()
    
    /**
     * Función que encola un elemento en la cola
     * Args:
     *   e (T): Elemento a encolar
     */
    override fun agregar(e: T) { cola.add(e) }
    
    /**
     * Función que desencola un elemento de la cola
     * Retorna: 
     *   T: elemento a ser desencolar
     */
    override fun remover() : T {
        if (vacio()) { throw RuntimeException("Error, la cola está vacía.") }
        return cola.removeFirst()
    }
    
    /**
     * Función que determina si la secuencia está vacía
     * Retorna:
     *   Boolean: True si está vacía, False si no.
     */
    override fun vacio() : Boolean = (cola.size == 0)
}

/**
 * Clase que implementa a la estructura Grafo como una lista de adyacencias
 */
class Grafo {
    var listaAdy : Array<ArrayList<Int>> = arrayOf()
    	get() = field
    
    var pos : MutableMap<Int, Int> = mutableMapOf()
    	get() = field
    
    var numV : Int = 0
    	get() = field
    
    var numL : Int = 0
    	get() = field
    
    /**
     * Función que inserta un vértice al grafo
     * Args: 
     *   v (Int): Vértice a ser insertado
     */
    fun insertarVer(v: Int) {
        if (pos.contains(v)) {
            println("No se pudo completar la inserción, el vértice ya existe")
        } else {
            listaAdy += arrayOf(arrayListOf())
            pos.put(v, listaAdy.size-1)
            numV++
        }
    }
    
    /**
     * Función que inserta un lado al grafo
     * Args: 
     *   u (Int): Uno de los vértices
     *   v (Int): El otro de los vértices
     */
    fun insertarLado(u: Int, v: Int) {
        if (!(pos.contains(v) && pos.contains(u))) {
            println("No se pudo completar la inserción, uno de los vértices no existe")
        } else {
            listaAdy[pos.get(u)!!].add(v)
            listaAdy[pos.get(v)!!].add(u)
            numL++
        }
    }
}

/**
 * Clase que implementa la búsqueda en un grafo
 */
abstract class Busqueda(var g: Grafo) {
    abstract var sec : Secuencia<Int>
 
    /**
     * Función que determina el número de vértices explorados
     * en la búsqueda de un vértice, partiendo desde otro
     * Args:
     *    D (Int): Vértice de partida
     *    H (Int): Vértice buscado
     * Retorna:
     *    Int: Número de vértices explorados en la búsqueda, si no
     *    encuentra el vértice, retorna -1
     */
    fun buscar(D: Int, H: Int) : Int {
        
        var color = IntArray(g.numV)
        var u : Int
        var count : Int = 0
        
        color[D] = 1
        sec.agregar(D)
         
        while (!(sec.vacio())) {
            u = sec.remover()
            count++
            if (u == H) { return count }
            g.listaAdy[g.pos.get(u)!!].forEach {
                if (color[it] == 0) {
                    color[it] = 1
                    sec.agregar(it)
                }
            }
        }
        
    	return -1
        
    }
}

/**
 * Clase que implementa el BFS, hereda de Busqueda
 */
class BFS(g: Grafo) : Busqueda(g) {
    override var sec : Secuencia<Int> = Cola<Int>()
}

/**
 * Clase que implementa el DFS, hereda de Busqueda
 */
class DFS(g: Grafo) : Busqueda(g) {
    override var sec : Secuencia<Int> = Pila<Int>()
}