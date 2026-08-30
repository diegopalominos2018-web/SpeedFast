import java.util.ArrayList;

public class ControladorDeEnvios implements Despachable, Cancelable, Rastreable {

    private ArrayList<Pedido> historial;

    public ControladorDeEnvios() {
        historial = new ArrayList<>();
    }

    @Override
    public void despachar() {
        System.out.println("→ Pedido despachado correctamente.");
    }

    @Override
    public void cancelar() {
        System.out.println("→ Pedido cancelado exitosamente.");
    }

    @Override
    public void verHistorial() {
        System.out.println("Historial de entregas:");

        for (Pedido pedido : historial) {
            System.out.println("- Pedido #" + pedido.getIdPedido()
                    + " (" + pedido.getTipoPedido() + ")");
        }
    }

    public void agregarAlHistorial(Pedido pedido) {
        historial.add(pedido);
    }
    public void reservarPedido(Pedido pedido) {
        System.out.println("→ Pedido #" + pedido.getIdPedido()
                + " (" + pedido.getTipoPedido() + ") reservado correctamente.");
    }
}