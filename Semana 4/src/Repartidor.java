import java.util.ArrayList;
import java.util.Random;

public class Repartidor implements Runnable {

    private String nombre;
    private ArrayList<Pedido> pedidos;
    private Random random = new Random();

    public Repartidor(String nombre, ArrayList<Pedido> pedidos) {
        this.nombre = nombre;
        this.pedidos = pedidos;
    }

    @Override
    public void run() {

        for (Pedido pedido : pedidos) {

            System.out.println(
                    "Repartidor " + nombre + " está procesando un pedido:"
            );

            pedido.mostrarResumen();

            System.out.println(
                    "Repartidor " + nombre + " está realizando la entrega..."
            );

            try {

                Thread.sleep((random.nextInt(4) + 2) * 1000);

                System.out.println(
                        "Repartidor " + nombre
                                + " terminó la entrega del pedido."
                );

                System.out.println();

            } catch (InterruptedException e) {

                Thread.currentThread().interrupt();
            }
        }
    }
}