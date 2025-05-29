package Conexion;

import java.io.ObjectOutputStream;

public class ServerManager {
    private static ObjectOutputStream restaurantOut = null;

    public static synchronized void setRestaurantOut(ObjectOutputStream out) {
        restaurantOut = out;
        System.out.println("[ServerManager] Se registró la conexión del restaurante.");
    }

    public static synchronized ObjectOutputStream getRestaurantOut() {
        return restaurantOut;
    }

    public static synchronized boolean isRestaurantConnected() {
        return restaurantOut != null;
    }
}
