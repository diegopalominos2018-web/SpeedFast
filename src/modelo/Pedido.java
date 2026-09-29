package modelo;

public class Pedido {

    private int idPedido;
    private String direccionEntrega;
    private String tipoPedido;
    private double distanciaKm;
    private EstadoPedido estado;
    private String nombreRepartidor;

    public Pedido(int idPedido, String direccionEntrega, String tipoPedido, double distanciaKm) {
        this.idPedido = idPedido;
        this.direccionEntrega = direccionEntrega;
        this.tipoPedido = tipoPedido;
        this.distanciaKm = distanciaKm;
        this.estado = EstadoPedido.PENDIENTE;
        this.nombreRepartidor = "";
    }

    public void asignarRepartidor() {
        System.out.println("Asignando repartidor para pedido general...");
    }

    public void asignarRepartidor(String nombreRepartidor) {
        this.nombreRepartidor = nombreRepartidor;
        System.out.println("modelo.Pedido asignado a " + nombreRepartidor);
    }

    public void mostrarResumen() {
        System.out.println("ID del pedido: " + idPedido);
        System.out.println("Dirección de entrega: " + direccionEntrega);
        System.out.println("Tipo de pedido: " + tipoPedido);
        System.out.println("Distancia: " + distanciaKm + " km");
        System.out.println("Estado: " + estado);
        System.out.println("Repartidor: " + nombreRepartidor);
    }

    public double getDistanciaKm() {
        return distanciaKm;
    }

    public int getIdPedido() {
        return idPedido;
    }

    public String getTipoPedido() {
        return tipoPedido;
    }

    public String getDireccionEntrega() {
        return direccionEntrega;
    }

    public void setDireccionEntrega(String direccionEntrega) {
        this.direccionEntrega = direccionEntrega;
    }

    public EstadoPedido getEstado() {
        return estado;
    }

    public void setEstado(EstadoPedido estado) {
        this.estado = estado;
    }

    public String getNombreRepartidor() {
        return nombreRepartidor;
    }

    @Override
    public String toString() {
        return "modelo.Pedido #" + idPedido +
                " | Dirección: " + direccionEntrega +
                " | Estado: " + estado;
    }

    public double calcularTiempoEntrega() {
        return 0;
    }
}