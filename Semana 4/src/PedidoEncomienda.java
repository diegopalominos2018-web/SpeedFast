public class PedidoEncomienda extends Pedido implements Despachable, Cancelable {

    public PedidoEncomienda(int idPedido, String direccionEntrega, double distanciaKm) {
        super(idPedido, direccionEntrega, "Encomienda", distanciaKm);
    }

    @Override
    public void asignarRepartidor() {
        System.out.println("[Pedido Encomienda]");
        System.out.println("Asignando repartidor...");
        System.out.println("→ Verificando vehículo disponible... OK");
    }

    @Override
    public void asignarRepartidor(String nombreRepartidor) {
        System.out.println("→ Repartidor asignado a " + nombreRepartidor);
    }

    @Override
    public double calcularTiempoEntrega() {
        return Math.round(20 + (1.5 * getDistanciaKm()));
    }

    @Override
    public void despachar() {
        System.out.println("→ Pedido de encomienda despachado correctamente.");
    }

    @Override
    public void cancelar() {
        System.out.println("→ Pedido de encomienda cancelado exitosamente.");
    }
}