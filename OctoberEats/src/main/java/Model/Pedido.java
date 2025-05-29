package Model;

import Controller.Orden;
import java.util.Date;
import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private int id;
    private int usuarioId;
    private int restauranteId;
    private String estado;
    private Date fechaPedido;
    private Date fechaEntregaEstimada;
    private Date fechaEntregaReal;
    private List<Orden> orden;

    public Pedido(int id, int usuarioId, int restauranteId, String estado, Date fechaPedido) {
        this.id = id;
        this.usuarioId = usuarioId;
        this.restauranteId = restauranteId;
        this.estado = estado;
        this.fechaPedido = fechaPedido;
        this.orden = new ArrayList<>();
    }

    public void agregarObjeto(Orden objeto) {
        orden.add(objeto);
    }

    public void actualizarEstado(String nuevoEstado) {
        this.estado = nuevoEstado;
    }

    public List<Orden> getObjetos() {
        return orden;
    }
}