package Conexion;

import Controller.Orden;

public class ServidorRestaurante {

    /**
     * Simula el envío de la orden al restaurante.
     *
     * @param orden Objeto Orden recibido del cliente.
     * @param restauranteId Identificador del restaurante destino.
     */
    public void enviarOrdenARestaurante(Orden orden, int restauranteId) {
        System.out.println("Preparando para enviar la orden al restaurante con ID " + restauranteId);
        System.out.println("Orden a enviar: " + orden);
        // Aquí se implementaría la lógica para enviar la orden a la conexión correspondiente del restaurante.
    }
}
