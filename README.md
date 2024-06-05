# Manual Tecnico
***

Este es el proyecto final del curso de Programación III. Debido a algunos inconvenientes con el tiempo  
la parte gráfica del programa no pudo ser modulada; todos los paneles, botones, tablas y demás componentes  
y listeners se encuentran dentro de un solo archivo .form llamado `App.form`. El proyecto fue desarrollado en el IDE Intellij IDEA.

## GUI
***

Para el sistema de navegación que el programa utiliza, se implementó el *CardLayout* como LayoutManager y se estableció  
el panel `panelMenuPrincipal` como la default card. El intercambio de las cartas (navegacion) es manejado por la funcion  
`irA()`. Esta elimina el panel anterior y lo reemplaza por panel que contiene el menu que el usuario solicito. El `panelMenuPrincipal`  
cuenta unicamente con 2 botones para la navegacion entre el menu de los arboles binarios de busqueda `panelABB` y el menu de los arboles  
AVL `panelAVL` ambos estan anidados en un `JPanel` respectivamente que utiliza el *CardLayout* para mejorar la lectura del arbol de jerarquia  
en el editor de los componentes ya que cuentan con otros submenus para agregar y actualizar personas. Cada boton en los menus de los arboles hace  
lo que indica.

### Tablas
***

Las JTable tienen sus propias funciones para ser actualizadas, creadas y cargar los datos de los arboles anteriores. Como nota importante, en  
la parte del GUI se incluye la funcion `actualizarTabla` para que esta esta sicronizada despues de cada modificacion a cualquier arbol.

## Arboles
***

La definicion de estas estructuras de datos se encuentra en la clase `ArbolBB.java` y `ArbolAVL.java` respectivamente. En esta se encuentra asi mismo, la  
definicion de la clase Nodo esta contiene los atributos definidos en el documento con los lineamientos del proyecto, ademas, el constructor inicializa sus valores (a excepcion  
del dpi y nombre) a `"Vacio"`. La logica para la inserccion, eliminacion, actualizacion y los recorridos son identicos a los de tradicionales.

## Metodos adicionales de los arboles

### Metodos GUI
***

Se refiere metodos como inserccion, eliminacion y actualizacion que contienen el sufijo *GUI*. La funcion de estos es reflejar los cambios tanto en el la estructura  
de arbol binario de busqueda como en la `JTable`, archivos de texto y el archivo dot.

### Actualizar GUI
***

Este metodo es utilizado al final de cada modificacion que sufre cualquiera de los 2 arboles. El objetivo de este metodo es crear el archivo de texto que contiene  
los nodos de los arboles, los archivos son llamados `arbolBB.txt` y `arbolAVL.txt` respectivamente. Para crear el archivo dot, el metodo se ayuda de 2 archivos de texto  
que continen los datos de todos los nodos y sus relaciones. Al finalizar el recorrido preorder del arbol, los archivos `dotDeclaraciones.txt` y `dotRelaciones.txt` son fucionadas  
en el archivo dot, obteniendo asi la sincronizacion de todo en cualquier modificacion.

### Cargar arbol guardado
***

El metodo para cargar el arbol creado por el usuario es llamado `cargarArbol()` este lee el archivo de texto generado al insertar un nodo en cualquiera de los 2 tipos de  
arboles.

### Buscar
***

El metodo `buscar()` muestra en un dialogo los datos de la persona encontrada, asi como tambien, el tiempo que tomo encontrarlo.

### Encriptacion, desencriptar
***

Los 2 arboles cuentan con una clase de utilidad llamada `EncriptacionSustitucion.java`. Esta permite encriptar y desencriptar utilizando un `HashMap` que mapea todos los caracteres  
que pueden presentarse en los nodos a otros caracteres. Esto se le aplica a cada caracter en un registro; reemplazando el valor del caracter por su equivalente en el `HashMap` llamado `diccionario`.  
La desencriptacion invierte las llaves del `HashMap` para que asi los valores apunten a las llaves (en esencia reviertiendo los equivalentes). A continuacion se muestra el `HashMap` utilizado:

| Clave | Valor |
|-------|-------|
| /     | R     |
| 0     | T     |
| 1     | E     |
| 2     | I     |
| 3     | u     |
| 4     | y     |
| 5     | ú     |
| 6     | 6     |
| 7     | f     |
| 8     | M     |
| 9     | o     |
| A     | Z     |
| Á     | 1     |
| B     | b     |
| C     | r     |
| D     | q     |
| E     | C     |
| F     | 9     |
| G     | V     |
| H     | g     |
| I     | d     |
| É     | z     |
| J     | Y     |
| K     | w     |
| L     | c     |
| M     | S     |
| Í     | 5     |
| N     | P     |
| O     | O     |
| P     | U     |
| Q     | Á     |
| R     | /     |
| S     | X     |
| Ó     | h     |
| T     | J     |
| U     | 7     |
| V     | 4     |
| W     | é     |
| X     | k     |
| Y     | É     |
| Z     | L     |
| Ú     | K     |
| a     | Q     |
| á     | 2     |
| b     | W     |
| c     | Í     |
| d     | i     |
| e     | m     |
| f     | l     |
| g     | F     |
| h     | n     |
| i     | H     |
| é     | Ú     |
| j     | B     |
| k     | x     |
| l     | D     |
| m     | s     |
| í     | G     |
| n     | N     |
| o     | t     |
| p     | ó     |
| q     | 3     |
| r     | v     |
| s     | e     |
| ó     | á     |
| t     | 8     |
| u     | í     |
| v     | a     |
| w     | 0     |
| x     | j     |
| y     | Ó     |
| z     | p     |
| ú     | A     |

### Comprimir, descomprimir
***

Estas funciones se ayudan de la clase de utilidad `Comprimidor` para comprimir y descomprimir el archivo de texto que contiene  
los nodos de cualquier arbol en preorder. Esta clase lee el archivo original palabra por palabra para obtener la frecuencia  
con que aparecen y asi identificar cuales son las palabras mas frecuentes y reemplazarlas por una abreviacion por ejemplo: `"Samuel"`  
a `"T1"` dependiendo de la frecuencia. Al crear el diccionario este contendra todas la palabras del documento original, estas seran  
sus claves y sus frecuencias seran los valores a los que apuntan. Para ahorrar memoria en el programa el diccionario sera reducido a  
unicamente 10 palabras comunes. Teniendo asi un diccionario con 10 entradas. Para mejor lectura el diccionario sera ordenado desendentemente.  
Durante el segundo recorrido del archivo original, los registros seran separados por palabras y si coinciden con una palabra fecuente del dicconario  
estos seran reemplazados por el valor del mismo. Asi aplica tambien para la descompresion, inviertiendo las claves por los valores. Los archivos  
generados por esta clase seran llamados `arbol_comprimido.txt` y `arbol_descomprimido.txt` respectivamente.
