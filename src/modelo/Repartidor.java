package modelo;

public class Repartidor implements Runnable {

    private String nombre;
    private ZonaDeCarga zonaDeCarga;

    public Repartidor(String nombre, ZonaDeCarga zonaDeCarga) {
        this.nombre = nombre;
        this.zonaDeCarga = zonaDeCarga;
    }

    @Override
    public void run() {

        while (true) {

            Pedido pedido = zonaDeCarga.retirarPedido();

            if (pedido == null) {
                break;
            }

            System.out.println("[modelo.Repartidor - " + nombre +
                    "] Retirando pedido #" + pedido.getIdPedido() + "...");

            pedido.setEstado(EstadoPedido.EN_REPARTO);

            System.out.println("[modelo.Repartidor - " + nombre +
                    "] Estado: " + pedido.getEstado());

            System.out.println("[modelo.Repartidor - " + nombre +
                    "] Entregando pedido #" + pedido.getIdPedido() + "...");

            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
                break;
            }

            pedido.setEstado(EstadoPedido.ENTREGADO);

            System.out.println("[modelo.Repartidor - " + nombre +
                    "] Estado: " + pedido.getEstado());
        }
    }
}