package Model;
/**
 * interfaceque permite notificar a diferentes partes
 * cuando cambia el estado de una orden
 * NOTA
 * este interface debe ser implementada por cualquier clase que necesite recibir
 * notificaciones sobre cambios en las ordenes
 */
public interface ActualizacionPedidoObserver {

    void actualizacionPedido(int pedidoId, String nuevoEstado);
}