package modelo;

public class Repartidor implements Runnable {

    private int id;
    private String nombre;
    private ZonaDeCarga zonaDeCarga;

    // Constructor para trabajar con la base de datos
    public Repartidor(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
        this.zonaDeCarga = null;
    }

    // Constructor original, para no romper el funcionamiento anterior
    public Repartidor(String nombre, ZonaDeCarga zonaDeCarga) {
        this.id = 0;
        this.nombre = nombre;
        this.zonaDeCarga = zonaDeCarga;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public ZonaDeCarga getZonaDeCarga() {
        return zonaDeCarga;
    }

    public void setZonaDeCarga(ZonaDeCarga zonaDeCarga) {
        this.zonaDeCarga = zonaDeCarga;
    }

    @Override
    public void run() {

        if (zonaDeCarga == null) {
            return;
        }

        while (true) {

            Pedido pedido = zonaDeCarga.retirarPedido();

            if (pedido == null) {
                break;
            }

            System.out.println("[Repartidor - " + nombre +
                    "] Retirando pedido #" + pedido.getIdPedido() + "...");

            pedido.setEstado(EstadoPedido.EN_REPARTO);

            System.out.println("[Repartidor - " + nombre +
                    "] Estado: " + pedido.getEstado());

            System.out.println("[Repartidor - " + nombre +
                    "] Entregando pedido #" + pedido.getIdPedido() + "...");

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }

            pedido.setEstado(EstadoPedido.ENTREGADO);

            System.out.println("[Repartidor - " + nombre +
                    "] Estado: " + pedido.getEstado());
        }
    }

    @Override
    public String toString() {
        return id + " - " + nombre;
    }
}