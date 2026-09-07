import java.util.ArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

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

        // Nuevos pedidos para trabajar con los 3 repartidores
        Pedido comida2 = new PedidoComida(
                4,
                "Av. Las Naciones 789",
                7.0
        );

        Pedido encomienda2 = new PedidoEncomienda(
                5,
                "Av. Pajaritos 1234",
                8.5
        );

        Pedido express2 = new PedidoExpress(
                6,
                "Av. 5 de Abril 555",
                4.0
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


        // =========================================================
        // REPARTIDORES Y EJECUCIÓN CONCURRENTE
        // =========================================================

        System.out.println();
        System.out.println("========================================");
        System.out.println("       INICIO DE REPARTOS");
        System.out.println("========================================");


        // Lista de pedidos de Camila
        ArrayList<Pedido> pedidosCamila = new ArrayList<>();
        pedidosCamila.add(comida);
        pedidosCamila.add(comida2);


        // Lista de pedidos de Luis
        ArrayList<Pedido> pedidosLuis = new ArrayList<>();
        pedidosLuis.add(encomienda);
        pedidosLuis.add(encomienda2);


        // Lista de pedidos de Felipe
        ArrayList<Pedido> pedidosFelipe = new ArrayList<>();
        pedidosFelipe.add(express);
        pedidosFelipe.add(express2);


        // Creación de los 3 repartidores
        Repartidor camila = new Repartidor("Camila", pedidosCamila);
        Repartidor luis = new Repartidor("Luis", pedidosLuis);
        Repartidor felipe = new Repartidor("Felipe", pedidosFelipe);


        // ExecutorService con 3 hilos
        ExecutorService executor = Executors.newFixedThreadPool(3);


        // Enviamos los 3 repartidores al ExecutorService
        executor.submit(camila);
        executor.submit(luis);
        executor.submit(felipe);


        // No se aceptan nuevas tareas
        executor.shutdown();


        // Esperamos que los 3 repartidores terminen
        try {

            if (executor.awaitTermination(30, TimeUnit.SECONDS)) {

                System.out.println();
                System.out.println("========================================");
                System.out.println("       TODOS LOS REPARTOS TERMINARON");
                System.out.println("========================================");
            }

        } catch (InterruptedException e) {

            Thread.currentThread().interrupt();

        }

    }
}