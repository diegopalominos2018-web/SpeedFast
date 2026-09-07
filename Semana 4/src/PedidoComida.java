public class PedidoComida extends Pedido implements Despachable, Rastreable {

    public PedidoComida(int idPedido, String direccionEntrega, double distanciaKm) {
        super(idPedido, direccionEntrega, "Comida", distanciaKm);
    }

    @Override
    public void asignarRepartidor() {
        System.out.println("[Pedido Comida]");
        System.out.println("Asignando repartidor...");
        System.out.println("→ Verificando mochila térmica... OK");
    }

    @Override
    public void asignarRepartidor(String nombreRepartidor) {
        System.out.println("→ Repartidor con mochila térmica asignado a " + nombreRepartidor);
    }

    @Override
    public double calcularTiempoEntrega() {
        return 15 + (2 * getDistanciaKm());
    }

    @Override
    public void despachar() {
        System.out.println("→ Pedido de comida despachado correctamente.");
    }

    @Override
    public void verHistorial() {
        System.out.println("→ Historial de Pedido Comida consultado.");
    }
}