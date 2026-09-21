package vista;

import javax.swing.*;
import java.awt.*;

public class VentanaPrincipal extends JFrame {

    private JButton btnRegistrarPedido;
    private JButton btnListarPedidos;

    public VentanaPrincipal() {

        setTitle("SpeedFast");
        setSize(500, 300);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        JLabel titulo = new JLabel("SPEEDFAST", SwingConstants.CENTER);
        titulo.setFont(new Font("Arial", Font.BOLD, 28));

        btnRegistrarPedido = new JButton("Registrar pedido");
        btnListarPedidos = new JButton("Listar pedidos");

        // Botón Registrar pedido
        btnRegistrarPedido.addActionListener(e -> {
            VentanaRegistroPedido ventana = new VentanaRegistroPedido();
            ventana.setVisible(true);
        });

        // Botón Listar pedidos
        btnListarPedidos.addActionListener(e -> {
            VentanaListaPedidos ventana = new VentanaListaPedidos();
            ventana.setVisible(true);
        });

        JPanel panelBotones = new JPanel();
        panelBotones.add(btnRegistrarPedido);
        panelBotones.add(btnListarPedidos);

        setLayout(new BorderLayout());

        add(titulo, BorderLayout.NORTH);
        add(panelBotones, BorderLayout.CENTER);
    }
}