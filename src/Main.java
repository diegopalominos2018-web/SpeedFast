public class Main {

    public static void main(String[] args) {

        ZonaDeCarga zonaDeCarga = new ZonaDeCarga();

        // Agregar 5 pedidos
        zonaDeCarga.agregarPedido(
                new Pedido(1, "Santiago Centro", "General", 5.0));

        zonaDeCarga.agregarPedido(
                new Pedido(2, "Providencia", "General", 7.0));

        zonaDeCarga.agregarPedido(
                new Pedido(3, "Ñuñoa", "General", 6.0));

        zonaDeCarga.agregarPedido(
                new Pedido(4, "Recoleta", "General", 8.0));

        zonaDeCarga.agregarPedido(
                new Pedido(5, "Las Condes", "General", 10.0));

        // Crear 3 repartidores
        Thread repartidor1 = new Thread(
                new Repartidor("Juan", zonaDeCarga));

        Thread repartidor2 = new Thread(
                new Repartidor("Camila", zonaDeCarga));

        Thread repartidor3 = new Thread(
                new Repartidor("Pedro", zonaDeCarga));

        // Iniciar los 3 hilos
        repartidor1.start();
        repartidor2.start();
        repartidor3.start();

        // Esperar a que terminen los 3 hilos
        try {
            repartidor1.join();
            repartidor2.join();
            repartidor3.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        System.out.println();
        System.out.println("Todos los pedidos han sido entregados correctamente");
    }
}