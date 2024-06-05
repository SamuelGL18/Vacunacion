# Manual de Usuario
***

Esta aplicacion proporciona utilidades para tener un control de registro de pacientes vacunados en el contexto de la pandemia  
de COVID-19. El sistema utiliza arboles binarios de busqueda y arboles AVL para optimizar el tiempo de busqueda de las personas.  
para obtener un mayor tiempo optimo, se recomienda guardar sus registros en la opcion arboles AVL, ya que estos cuentan con mecanismos  
especiales para evitar dar pasos adicionales al buscar a una persona. El programa cuenta con opciones de seguridad y optimizacion de almacenamiento. 

# GUI
***

![Menu principal](/capturas/menuPrincipal.png)  
La aplicacion cuenta con funcionalidades de navegacion para simplificar la experiencia del usuario. Utilice los botones para navegar segun le convenga.  
La mayoria de los botonos no tienen mayor explicacion.

# Menus arboles

## Ejemplo del menu
***

![Menu de un arbol](/capturas/menuABB.png)

## Buscar
***

Para buscar un nodo es importante ingresar en el campo de texto el <mark>DPI</mark> de la persona que se desea buscar, de lo contrario, no se mostrara nada.

## Agregar
***

![Menu agregar](/capturas/actualizarPersona.png)  
Esta opcion reedirigira al usuario al menu `Agregar`. En el debera rellanar los campos solicitados para poder crear un registro de la persona.  

## Actualizar
***

![Menu actualizar](/capturas/actualizarPersona.png)  
En esta ventana, podra actualizar los campos de un registro especifico, para ello, debe seleccionar una fila de la tabla (la que desea actualizar) y rellenar  
los campos requeridos. Si no desea cambiar el valor de un campo especifico, dejelo como esta.

## Eliminar
***

Para eliminar, debera eligir la fila que contiene el registro que desea eliminar y luego hacer click en el boton eliminar.  

## Cargar archivo
***

Al clickear este boton aparecera una ventana para explorar archivos, en ella, seleccione el archivo con registros que desea cargar en cualquiera de los 2 arboles.  

## Ver arbol
***

Este boton mostrara una ventana emergente en la que se podra visualizar graficamente el arbol que se ha estado creando.  

## Encriptar y desencriptar
***

Estas opciones podran encriptar y desencriptar todos sus registros. <mark> No desencripte sus registros si no estan encriptados </mark> ya que esto puede conllevar a  
corrupcion de datos. Para asegurarse de no cometer ese error un mensaje le aparecera.  

![dialogo](/capturas/dialogoDesencriptar.png)  

## Comprimir y descomprimir
***

Estas opciontes crearan archivos de texto nuevos llamados: `arbol_comprimido.txt` y `arbol_descomprimido.txt` respectivamente.  