# Desarrollo

MyFitPlan fue desarrollada usando el IDE **Android Studio**, ya que esta es la herramienta oficial que proporciona Google para el desarrollo de aplicaciones para Android. El lenguaje en el que se ha programado la aplicación es **Java**. 

## Diseño de la aplicación

- La única herramienta externa a Android Studio usada para la realizacíon de iconos fue *[**Canva**](https://www.canva.com/es_es/)*, en la que se hizo el icono principal de MyFitPlan.

- Para las fuentes de texto se ha usado una fuente de Google Fonts llamada *[**Bebas neue**](https://fonts.google.com/specimen/Bebas+Neue).* 

- Para el resto de iconos, logos y colores se han usado las herramientas que proporciona Android Studio, como la de creación de Vector Assets.

- Para el aspecto general de la aplicación y sus componentes como textos y botones se ha usado la versión más reciente de Material Design de Google, *[**Material3**](https://m3.material.io/)*, con el objetivo de hacer que la aplicación cuente con una estética más novedosa y actualizada.

- MyFitPlan cuenta con temas claro y oscuro totalmente funcionales. Hacer esto no ha sido tarea fácil, y para conseguirlo se crearon multitud de "styles" para los diferentes componentes, logrando que estos cambiaran en función de si estaba usando el tema claro o el oscuro. Esto tuvo que ser así ya que un TextView, por ejemplo, no cambia de color por sí solo al cambiar de tema, pero si en el archivo "themes.xml" se añade un estilo en el que se pone que el "textColor" sea negro; y en "themes.xml(night)" añades un estilo con el mismo nombre que en "themes.xml", en el que diga que el "textColor" es blanco, al asignarle ese estilo a un TextView, este cambiará de color dependiendo del tema que se use. Gracias a esta posibilidad la gran mayoría de componentes de la app reaccionarán según el tema usado. 

## Estructura de MyFitPlan

- Para la estructura de la aplicación se ha seguido el recomendado "Single Activity Structure", que consiste en usar una única Activity en la que se alojan múltiples Fragments, haciendo de ese modo que la aplicación sea más rápida y ligera. Esto significa que MyFitPlan solo tiene un Actyvity y todas las demás pantallas son fragmentos alojados en ella. 

- Para conseguirlo, se ha hecho uso de un BottomNavigationView para moverse fácilmente por los fragments principales. Este tiene tres opciones, que son los tres fragments principales. Este solo será visible si se está en uno de estos fragments. 

- También se ha usado un *[**gráfico de navegación**](https://developer.android.com/guide/navigation/navigation-getting-started?hl=es-419)* para navegar por los diferentes fragments dependiendo de las acciones que realice el usuario. En este se especifican mediante flechas hacia qué fragmento se irá al realizar una acción. Este gráfico es muy útil ya que las acciones se quedan registradas con un id, por lo que es muy fácil usarlas en las clases Java de los fragments. Otra función muy importante de este gráfico es la de decidir en qué dirección se irá al pulsar el botón de retroceder del teléfono, ya que si por ejemplo, se va de Fragment1 a Fragment2, se puede decidir en el gráfico que al pulsar en retroceder, en vez de ir del Fragment2 al Fragment1, se vaya hacia otro fragment.

- Otro punto muy importante es que MyFitPlan cuenta con una base de datos SQLite para guardar los datos de usuario, los ejercicios, comidas y rutinas que se quieran crear. Esta cuenta con una clase principal y varios POJO de los diferentes objetos que tendrá la base. En MyFitPlan, el usuario podrá crear, editar y borrar cada una de las cosas que se introduzcan en la base de datos, notificándole mediante mensajes "Toast" de cada una de sus acciones, y en caso de borrar algun dato, se mostrará un dialog antes de que el usuario realice la acción. 

- También es importante comentar que la aplicación, mediante el uso de Intents, ofrece la posibilidad de conectarse con Google Calendar para programar eventos con las diferentes rutinas tanto de entrenamiento como de alimentación. Otra posibilidad que ofrecen es la de enviar contenido mediante otras aplicaciones como Gmail o Whatsapp, que será usado para enviar un mensaje con un enlace al apartado releases de este repositorio, para así compartir la aplicación fáclmente con otras personas.

- Por último, se han intentado seguir en medida de lo posible buenas prácticas de programación en general y buenas prácticas de programación Android, como puede ser el uso de ContraintLayout en los diferentes fragments y el uso de el archivo "colors.xml" para almacenar los colores usados en la app, y "strings.xml" donde se almacenas con id los diferentes Strings usados en las vistas.
