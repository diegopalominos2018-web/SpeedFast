public class Main {

    public static void main(String[] args) {

        // Creación de los pedidos utilizando referencias de tipo Pedido
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

        // Controlador de envíos para gestionar el historial
        ControladorDeEnvios controlador = new ControladorDeEnvios();

        // =========================================================
        // PEDIDO COMIDA
        // =========================================================

        System.out.println("========================================");
        System.out.println("           PEDIDO COMIDA");
        System.out.println("========================================");

        comida.mostrarResumen();
        controlador.reservarPedido(comida);
        System.out.println("Tiempo estimado: "
                + comida.calcularTiempoEntrega()
                + " minutos");

        System.out.println();
        System.out.println("Asignación automática:");
        comida.asignarRepartidor();

        System.out.println();
        System.out.println("Asignación manual:");
        comida.asignarRepartidor("Carlos");

        System.out.println();
        System.out.println("Despacho:");
        ((Despachable) comida).despachar();

        System.out.println();
        System.out.println("Historial:");
        ((Rastreable) comida).verHistorial();

        controlador.agregarAlHistorial(comida);


        // =========================================================
        // PEDIDO ENCOMIENDA
        // =========================================================

        System.out.println();
        System.out.println("========================================");
        System.out.println("          PEDIDO ENCOMIENDA");
        System.out.println("========================================");

        encomienda.mostrarResumen();
        controlador.reservarPedido(encomienda);
        System.out.println("Tiempo estimado: "
                + encomienda.calcularTiempoEntrega()
                + " minutos");

        System.out.println();
        System.out.println("Asignación automática:");
        encomienda.asignarRepartidor();

        System.out.println();
        System.out.println("Asignación manual:");
        encomienda.asignarRepartidor("Andrea");

        System.out.println();
        System.out.println("Despacho:");
        ((Despachable) encomienda).despachar();

        System.out.println();
        System.out.println("Cancelación:");
        ((Cancelable) encomienda).cancelar();

        controlador.agregarAlHistorial(encomienda);


        // =========================================================
        // PEDIDO EXPRESS
        // =========================================================

        System.out.println();
        System.out.println("========================================");
        System.out.println("           PEDIDO EXPRESS");
        System.out.println("========================================");

        express.mostrarResumen();
        controlador.reservarPedido(express);
        System.out.println("Tiempo estimado: "
                + express.calcularTiempoEntrega()
                + " minutos");

        System.out.println();
        System.out.println("Asignación automática:");
        express.asignarRepartidor();

        System.out.println();
        System.out.println("Asignación manual:");
        express.asignarRepartidor("Felipe");

        System.out.println();
        System.out.println("Cancelación:");
        ((Cancelable) express).cancelar();

        System.out.println();
        System.out.println("Historial:");
        ((Rastreable) express).verHistorial();

        controlador.agregarAlHistorial(express);


        // =========================================================
        // HISTORIAL GENERAL
        // =========================================================

        System.out.println();
        System.out.println("========================================");
        System.out.println("        HISTORIAL GENERAL");
        System.out.println("========================================");

        controlador.verHistorial();
    }
}