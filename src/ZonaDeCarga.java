import java.util.LinkedList;
import java.util.Queue;

public class ZonaDeCarga {

    private Queue<Pedido> pedidosPendientes;

    public ZonaDeCarga() {
        pedidosPendientes = new LinkedList<>();
        System.out.println("[Zona de carga inicializada]");
    }

    public synchronized void agregarPedido(Pedido p) {
        pedidosPendientes.add(p);

        System.out.println("Pedido #" + p.getIdPedido()
                + " agregado. Destino: " + p.getDireccionEntrega());
    }

    public synchronized Pedido retirarPedido() {

        if (pedidosPendientes.isEmpty()) {
            return null;
        }

        return pedidosPendientes.poll();
    }
}