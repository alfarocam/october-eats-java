package Model;

import java.sql.*;
import java.util.*;

public class GestorPedidos {
    private static final String URL = "jdbc:mysql://localhost:3306/prueba_bd2";
    private static final String USER = "root";
    private static final String PASSWORD = "prueba1";
    
    private List<ActualizacionPedidoObserver> observadores = new ArrayList<>();
    private static GestorPedidos instancia;

    private GestorPedidos() {}

    public static GestorPedidos getInstance() {
        if (instancia == null) {
            instancia = new GestorPedidos();
        }
        return instancia;
    }

    public void registrarObservador(ActualizacionPedidoObserver observador) {
        observadores.add(observador);
    }
}