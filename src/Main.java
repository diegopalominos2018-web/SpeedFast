public class Main {

    public static void main(String[] args) {

        Pedido comida = new PedidoComida(
                1,
                "Av. Providencia 123",
                5.5
        );

        Pedido encomienda = new PedidoEncomienda(
                2,
                "Av. Grecia 456",
                10.0
        );

        Pedido express = new PedidoExpress(
                3,
                "Av. Macul 2202",
                3.0
        );

        System.out.println("===== PEDIDO COMIDA =====");
        comida.mostrarResumen();
        System.out.println("Tiempo estimado: "
                + comida.calcularTiempoEntrega() + " minutos");

        System.out.println();

        System.out.println("===== PEDIDO ENCOMIENDA =====");
        encomienda.mostrarResumen();
        System.out.println("Tiempo estimado: "
                + encomienda.calcularTiempoEntrega() + " minutos");

        System.out.println();

        System.out.println("===== PEDIDO EXPRESS =====");
        express.mostrarResumen();
        System.out.println("Tiempo estimado: "
                + express.calcularTiempoEntrega() + " minutos");
    }
}