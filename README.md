# October Eats
Aplicación de entrega de comida estilo Uber Eats, desarrollada en Java con arquitectura cliente-servidor, patrón MVC, sockets y base de datos MySQL.

## October Eats - Proyecto Final SC-303
October Eats es una aplicación académica simulada de entrega de comida, creada como proyecto final para el curso **Programación Cliente/Servidor Concurrente (SC-303)** de la Universidad Fidélitas.

La arquitectura está compuesta por tres módulos principales:

- 🖥️ **Servidor principal:** Maneja conexiones múltiples mediante sockets TCP y multithreading.
- 🧑‍🍳 **Restaurante:** Interfaz para gestionar pedidos recibidos.
- 👤 **Cliente:** Interfaz para que el usuario final explore menús, realice pedidos y dé seguimiento.

###  Arquitectura y Organización
El proyecto está estructurado siguiendo el patrón **MVC**, con las siguientes capas:

- **Model:** Maneja la lógica del dominio como `Usuario`, `Pedido`, `Producto`, y sus respectivos DAO.
- **View:** Desarrollada en **Java Swing**, contiene interfaces como `InterfazMenu`, `InterfazCarrito`, `InterfazRegistro`, entre otras.
- **Controller:** Controla la lógica de interacción entre vista y modelo. Ejemplo: `Cliente.java`, `CarritoCompras.java`.
- **Conexion:** Contiene las clases multicliente y gestión de red como `Servidor.java`, `ClienteConexion.java`, `RestaurantDispatcher.java`.

### Tecnologías utilizadas

- Lenguaje: Java
- GUI: Java Swing
- Sockets: TCP (cliente-servidor multicliente)
- Base de datos: MySQL
- Diseño: MVC
- Concurrencia: Hilos para manejo de múltiples conexiones

### Estructura del código
src/
- Conexion/                  # Clases de red y sockets
- Controller/                # Lógica de control (carrito, cliente, etc.)
- Model/                     # Clases de dominio y acceso a datos
- View/                      # Interfaces gráficas con Java Swing
- com/mycompany/octobereats1/  # Clase main (OctoberEats1.java)

lib/
- mysql-connector-j-8.0.33.jar
- protobuf-java-3.21.9.jar
