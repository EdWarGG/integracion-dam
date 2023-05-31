# Pruebas 

## Primera fase de pruebas

- Al crear todos los fragments junto a su layout, había creado un TabLayout y un ViewPager2 en el MainActivity, pero al usar el gráfico de navegación para ir de un fragment a otro al realizar determinadas acciones, la aplicación dejaba de funcionar.
  - Para corregir este problema investigué hasta saber que la mejor opción para el tipo de aplicación que yo estaba desarrollando era usar un BottomNavigationView, por lo que cambié el TabLayout por este último y funcionó sin problemas.

- Algunos fragmentos estaban diseñados de una forma en la que no podía funcionar la inserción de datos en la base de datos. Esto era debido a que para acceder al fragment de creación de ejercicio tenías que pasar por el fragment de creación de rutinas. Debido a esto si no se introducía nombre a la rutina antes de crear el ejercicio este quedaría sin una rutina asignada.
  - Para solucionarlo, cambié la disposición de los fragments para que primero se tuviese que crear la rutina y después al entrar a la rutina se pudiese crear el ejercicio o comida.

## Segunda fase de pruebas

- Después de crear los adapters para visualizar las rutinas y probar la aplicación, al crear una rutina la app dejaba de funcionar.
  - Analizando el problema descubrí que hay problemas si se intenta poner un int a un TextView con el método "setText", por lo que parseé el texto a int y funcionó sin ningún inconveniente.

- A la hora de cargar la imagen de perfil de usuario después de que se seleccionara una de la galería había muchos problemas con la función que estaba usando.
  - En un intento por corregirlo probé a usar otra función para probar si funcionaba y para mi sorpresa, sí funcionaba, y de hecho funcionaba mejor que la anterior ya que tenía más opciones para seleccionar imágenes.

## Última fase de pruebas

- Una vez ya tenía la aplicación desarrolada en su totalidad me puse a probarla a fondo y descubrí algo que, si bien no es un error, puede hacer la experiencia de usuario un poco más tediosa y en algunos casos bsatante confusa. Este problema se trataba de que al presionar el botón "volver" (el típico botón para ir hacia atrás que tienen todos los móviles) se iba a todas las pantallas por las que se había pasado antes por la aplicación. Esto puede ser molesto porque es totalmente innecesario en la mayoría de ocasiones, así que busqué información sobre si esto tenía algún tipo de solución.

  - Después de investigarlo bastante, descubrí que en el propio gráfico de navegación (nav_graph.xml) hay una opción que se llama "Pop Behavior". Esta opción permite que al moverte de un fragment a otro se borre el "rastro" de los fragments por los que se ha pasado anteriormente. Esto en realidad es una solución doble, ya que eliminé el problema anteriormente descrito, y además, al borrar ese rastro innecesario, la aplicación liberaba memoria por lo que a la vez la estaba optimizando. 
