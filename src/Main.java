public class Main {

    public static void main(String[] args) {

        Pedido comida = new PedidoComida(
                1,
                "Av. Providencia 123"
        );

        Pedido encomienda = new PedidoEncomienda(
                2,
                "Av. Grecia 456"
        );

        Pedido express = new PedidoExpress(
                3,
                "Av. Macul 2202"
        );

        comida.asignarRepartidor();
        comida.asignarRepartidor("Juan José Pérez");

        System.out.println();

        encomienda.asignarRepartidor();
        encomienda.asignarRepartidor("Camila Sotomayor");

        System.out.println();

        express.asignarRepartidor();
        express.asignarRepartidor("Luis Díaz");
    }
}