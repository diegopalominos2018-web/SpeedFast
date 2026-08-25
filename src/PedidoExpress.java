public class PedidoExpress extends Pedido {

    public PedidoExpress(int idPedido, String direccionEntrega, double distanciaKm) {
        super(idPedido, direccionEntrega, "Express", distanciaKm);
    }

    @Override
    public void asignarRepartidor() {
        System.out.println("[Pedido Express]");
        System.out.println("Asignando repartidor...");
        System.out.println("-> Buscando repartidor más cercano... OK");
        System.out.println("-> Disponibilidad inmediata... OK");
    }

    @Override
    public void asignarRepartidor(String nombreRepartidor) {
        System.out.println("-> Repartidor express asignado a " + nombreRepartidor);
    }

    @Override
    public double calcularTiempoEntrega() {
        if (getDistanciaKm() > 5) {
            return 15;
        }

        return 10;
    }
}