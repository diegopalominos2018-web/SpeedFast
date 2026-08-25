public abstract class Pedido {

    private int idPedido;
    private String direccionEntrega;
    private String tipoPedido;
    private double distanciaKm;

    public Pedido(int idPedido, String direccionEntrega, String tipoPedido, double distanciaKm) {
        this.idPedido = idPedido;
        this.direccionEntrega = direccionEntrega;
        this.tipoPedido = tipoPedido;
        this.distanciaKm = distanciaKm;
    }

    public void asignarRepartidor() {
        System.out.println("Asignando repartidor para pedido general...");
    }

    public void asignarRepartidor(String nombreRepartidor) {
        System.out.println("Pedido asignado a " + nombreRepartidor);
    }

    public void mostrarResumen() {
        System.out.println("ID del pedido: " + idPedido);
        System.out.println("Dirección de entrega: " + direccionEntrega);
        System.out.println("Tipo de pedido: " + tipoPedido);
        System.out.println("Distancia: " + distanciaKm + " km");
    }

    public double getDistanciaKm() {
        return distanciaKm;
    }

    public abstract double calcularTiempoEntrega();
}