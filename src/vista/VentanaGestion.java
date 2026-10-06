package vista;

import dao.EntregaDAO;
import dao.PedidoDAO;
import dao.RepartidorDAO;
import modelo.Entrega;
import modelo.EstadoPedido;
import modelo.Pedido;
import modelo.Repartidor;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class VentanaGestion extends JFrame {

    private final RepartidorDAO repartidorDAO = new RepartidorDAO();
    private final PedidoDAO pedidoDAO = new PedidoDAO();
    private final EntregaDAO entregaDAO = new EntregaDAO();

    private JTable tablaRepartidores;
    private JTable tablaPedidos;
    private JTable tablaEntregas;

    private DefaultTableModel modeloRepartidores;
    private DefaultTableModel modeloPedidos;
    private DefaultTableModel modeloEntregas;

    private JComboBox<Pedido> cmbPedido;
    private JComboBox<Repartidor> cmbRepartidor;

    private JTextField txtIdEntrega;
    private JTextField txtFecha;
    private JTextField txtHora;

    public VentanaGestion() {

        setTitle("Gestión SpeedFast");
        setSize(900, 600);
        setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
        setLocationRelativeTo(null);

        JTabbedPane pestañas = new JTabbedPane();

        pestañas.addTab("Repartidores", crearPanelRepartidores());
        pestañas.addTab("Pedidos", crearPanelPedidos());
        pestañas.addTab("Entregas", crearPanelEntregas());

        add(pestañas);

        cargarRepartidores();
        cargarPedidos();
        cargarEntregas();
        cargarCombos();
    }

    // =========================================================
    // REPARTIDORES
    // =========================================================

    private JPanel crearPanelRepartidores() {

        JPanel panel = new JPanel(new BorderLayout());

        modeloRepartidores = new DefaultTableModel(
                new Object[]{"ID", "Nombre"}, 0
        );

        tablaRepartidores = new JTable(modeloRepartidores);

        JButton btnCrear = new JButton("Crear");
        JButton btnEditar = new JButton("Editar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnActualizar = new JButton("Actualizar");

        btnCrear.addActionListener(e -> crearRepartidor());
        btnEditar.addActionListener(e -> editarRepartidor());
        btnEliminar.addActionListener(e -> eliminarRepartidor());
        btnActualizar.addActionListener(e -> cargarRepartidores());

        JPanel botones = new JPanel();

        botones.add(btnCrear);
        botones.add(btnEditar);
        botones.add(btnEliminar);
        botones.add(btnActualizar);

        panel.add(new JScrollPane(tablaRepartidores), BorderLayout.CENTER);
        panel.add(botones, BorderLayout.SOUTH);

        return panel;
    }

    private void cargarRepartidores() {

        if (modeloRepartidores == null) {
            return;
        }

        modeloRepartidores.setRowCount(0);

        List<Repartidor> lista = repartidorDAO.listarTodos();

        for (Repartidor repartidor : lista) {

            modeloRepartidores.addRow(new Object[]{
                    repartidor.getId(),
                    repartidor.getNombre()
            });
        }

        if (cmbRepartidor != null) {
            cargarComboRepartidores();
        }
    }

    private void crearRepartidor() {

        String idTexto = JOptionPane.showInputDialog(
                this,
                "Ingrese ID del repartidor:"
        );

        if (idTexto == null) {
            return;
        }

        String nombre = JOptionPane.showInputDialog(
                this,
                "Ingrese nombre del repartidor:"
        );

        if (nombre == null || nombre.trim().isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debe ingresar un nombre."
            );

            return;
        }

        try {

            int id = Integer.parseInt(idTexto.trim());

            Repartidor repartidor =
                    new Repartidor(id, nombre.trim());

            if (repartidorDAO.crear(repartidor)) {

                JOptionPane.showMessageDialog(
                        this,
                        "Repartidor creado correctamente."
                );

                cargarRepartidores();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "No se pudo crear el repartidor."
                );
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "El ID debe ser un número entero."
            );
        }
    }

    private void editarRepartidor() {

        int fila = tablaRepartidores.getSelectedRow();

        if (fila == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Seleccione un repartidor."
            );

            return;
        }

        int id = (int) modeloRepartidores.getValueAt(fila, 0);

        String nombre = JOptionPane.showInputDialog(
                this,
                "Nuevo nombre:",
                modeloRepartidores.getValueAt(fila, 1)
        );

        if (nombre == null || nombre.trim().isEmpty()) {
            return;
        }

        Repartidor repartidor =
                new Repartidor(id, nombre.trim());

        if (repartidorDAO.actualizar(repartidor)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Repartidor actualizado."
            );

            cargarRepartidores();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "No se pudo actualizar."
            );
        }
    }

    private void eliminarRepartidor() {

        int fila = tablaRepartidores.getSelectedRow();

        if (fila == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Seleccione un repartidor."
            );

            return;
        }

        int id = (int) modeloRepartidores.getValueAt(fila, 0);

        int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "¿Eliminar el repartidor seleccionado?",
                "Confirmar",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmacion != JOptionPane.YES_OPTION) {
            return;
        }

        if (repartidorDAO.eliminar(id)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Repartidor eliminado."
            );

            cargarRepartidores();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "No se pudo eliminar. Puede tener una entrega asociada."
            );
        }
    }

    // =========================================================
    // PEDIDOS
    // =========================================================

    private JPanel crearPanelPedidos() {

        JPanel panel = new JPanel(new BorderLayout());

        modeloPedidos = new DefaultTableModel(
                new Object[]{
                        "ID",
                        "Dirección",
                        "Tipo",
                        "Distancia",
                        "Estado"
                }, 0
        );

        tablaPedidos = new JTable(modeloPedidos);

        JButton btnEditar = new JButton("Editar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnActualizar = new JButton("Actualizar");

        btnEditar.addActionListener(e -> editarPedido());
        btnEliminar.addActionListener(e -> eliminarPedido());
        btnActualizar.addActionListener(e -> cargarPedidos());

        JPanel botones = new JPanel();

        botones.add(btnEditar);
        botones.add(btnEliminar);
        botones.add(btnActualizar);

        panel.add(new JScrollPane(tablaPedidos), BorderLayout.CENTER);
        panel.add(botones, BorderLayout.SOUTH);

        return panel;
    }

    private void cargarPedidos() {

        if (modeloPedidos == null) {
            return;
        }

        modeloPedidos.setRowCount(0);

        List<Pedido> lista = pedidoDAO.listarTodos();

        for (Pedido pedido : lista) {

            modeloPedidos.addRow(new Object[]{
                    pedido.getIdPedido(),
                    pedido.getDireccionEntrega(),
                    pedido.getTipoPedido(),
                    pedido.getDistanciaKm(),
                    pedido.getEstado()
            });
        }

        if (cmbPedido != null) {
            cargarComboPedidos();
        }
    }

    private void editarPedido() {

        int fila = tablaPedidos.getSelectedRow();

        if (fila == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Seleccione un pedido."
            );

            return;
        }

        int id = (int) modeloPedidos.getValueAt(fila, 0);

        String direccion = JOptionPane.showInputDialog(
                this,
                "Nueva dirección:",
                modeloPedidos.getValueAt(fila, 1)
        );

        if (direccion == null || direccion.trim().isEmpty()) {
            return;
        }

        String distanciaTexto = JOptionPane.showInputDialog(
                this,
                "Nueva distancia:",
                modeloPedidos.getValueAt(fila, 3)
        );

        if (distanciaTexto == null) {
            return;
        }

        try {

            double distancia =
                    Double.parseDouble(distanciaTexto.trim());

            String tipo =
                    modeloPedidos.getValueAt(fila, 2).toString();

            EstadoPedido estado =
                    EstadoPedido.valueOf(
                            modeloPedidos.getValueAt(fila, 4).toString()
                    );

            Pedido pedido = new Pedido(
                    id,
                    direccion.trim(),
                    tipo,
                    distancia
            );

            pedido.setEstado(estado);

            if (pedidoDAO.actualizar(pedido)) {

                JOptionPane.showMessageDialog(
                        this,
                        "Pedido actualizado."
                );

                cargarPedidos();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "No se pudo actualizar el pedido."
                );
            }

        } catch (NumberFormatException e) {

            JOptionPane.showMessageDialog(
                    this,
                    "La distancia debe ser numérica."
            );
        }
    }

    private void eliminarPedido() {

        int fila = tablaPedidos.getSelectedRow();

        if (fila == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Seleccione un pedido."
            );

            return;
        }

        int id = (int) modeloPedidos.getValueAt(fila, 0);

        int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "¿Eliminar el pedido seleccionado?",
                "Confirmar",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmacion != JOptionPane.YES_OPTION) {
            return;
        }

        if (pedidoDAO.eliminar(id)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Pedido eliminado."
            );

            cargarPedidos();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "No se pudo eliminar el pedido."
            );
        }
    }

    // =========================================================
    // ENTREGAS
    // =========================================================

    private JPanel crearPanelEntregas() {

        JPanel panel = new JPanel(new BorderLayout());

        modeloEntregas = new DefaultTableModel(
                new Object[]{
                        "ID",
                        "Pedido",
                        "Repartidor",
                        "Fecha",
                        "Hora"
                }, 0
        );

        tablaEntregas = new JTable(modeloEntregas);

        JPanel formulario = new JPanel(
                new GridLayout(2, 6, 5, 5)
        );

        formulario.add(new JLabel("ID:"));
        formulario.add(new JLabel("Pedido:"));
        formulario.add(new JLabel("Repartidor:"));
        formulario.add(new JLabel("Fecha:"));
        formulario.add(new JLabel("Hora:"));
        formulario.add(new JLabel(""));

        txtIdEntrega = new JTextField();
        cmbPedido = new JComboBox<>();
        cmbRepartidor = new JComboBox<>();
        txtFecha = new JTextField(
                LocalDate.now().toString()
        );
        txtHora = new JTextField(
                LocalTime.now()
                        .withNano(0)
                        .toString()
        );

        formulario.add(txtIdEntrega);
        formulario.add(cmbPedido);
        formulario.add(cmbRepartidor);
        formulario.add(txtFecha);
        formulario.add(txtHora);
        formulario.add(new JLabel("yyyy-MM-dd / HH:mm:ss"));

        JButton btnCrear = new JButton("Crear");
        JButton btnEditar = new JButton("Editar");
        JButton btnEliminar = new JButton("Eliminar");
        JButton btnActualizar = new JButton("Actualizar");

        btnCrear.addActionListener(e -> crearEntrega());
        btnEditar.addActionListener(e -> editarEntrega());
        btnEliminar.addActionListener(e -> eliminarEntrega());
        btnActualizar.addActionListener(e -> cargarEntregas());

        JPanel botones = new JPanel();

        botones.add(btnCrear);
        botones.add(btnEditar);
        botones.add(btnEliminar);
        botones.add(btnActualizar);

        JPanel superior = new JPanel(new BorderLayout());

        superior.add(formulario, BorderLayout.CENTER);
        superior.add(botones, BorderLayout.SOUTH);

        panel.add(superior, BorderLayout.NORTH);
        panel.add(new JScrollPane(tablaEntregas), BorderLayout.CENTER);

        return panel;
    }

    private void cargarEntregas() {

        if (modeloEntregas == null) {
            return;
        }

        modeloEntregas.setRowCount(0);

        List<Entrega> lista = entregaDAO.listarTodos();

        for (Entrega entrega : lista) {

            modeloEntregas.addRow(new Object[]{
                    entrega.getId(),
                    entrega.getIdPedido(),
                    entrega.getIdRepartidor(),
                    entrega.getFecha(),
                    entrega.getHora()
            });
        }
    }

    private void cargarCombos() {

        if (cmbPedido != null) {
            cargarComboPedidos();
        }

        if (cmbRepartidor != null) {
            cargarComboRepartidores();
        }
    }

    private void cargarComboPedidos() {

        cmbPedido.removeAllItems();

        for (Pedido pedido : pedidoDAO.listarTodos()) {
            cmbPedido.addItem(pedido);
        }
    }

    private void cargarComboRepartidores() {

        cmbRepartidor.removeAllItems();

        for (Repartidor repartidor : repartidorDAO.listarTodos()) {
            cmbRepartidor.addItem(repartidor);
        }
    }

    private void crearEntrega() {

        if (txtIdEntrega.getText().trim().isEmpty()) {

            JOptionPane.showMessageDialog(
                    this,
                    "Ingrese el ID de la entrega."
            );

            return;
        }

        if (cmbPedido.getSelectedItem() == null ||
                cmbRepartidor.getSelectedItem() == null) {

            JOptionPane.showMessageDialog(
                    this,
                    "Debe seleccionar un pedido y un repartidor."
            );

            return;
        }

        try {

            int id = Integer.parseInt(
                    txtIdEntrega.getText().trim()
            );

            Pedido pedido =
                    (Pedido) cmbPedido.getSelectedItem();

            Repartidor repartidor =
                    (Repartidor) cmbRepartidor.getSelectedItem();

            LocalDate fecha =
                    LocalDate.parse(txtFecha.getText().trim());

            LocalTime hora =
                    LocalTime.parse(txtHora.getText().trim());

            Entrega entrega = new Entrega(
                    id,
                    pedido.getIdPedido(),
                    repartidor.getId(),
                    fecha,
                    hora
            );

            if (entregaDAO.crear(entrega)) {

                JOptionPane.showMessageDialog(
                        this,
                        "Entrega creada correctamente."
                );

                cargarEntregas();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "No se pudo crear la entrega."
                );
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Datos inválidos:\n" + e.getMessage()
            );
        }
    }

    private void editarEntrega() {

        int fila = tablaEntregas.getSelectedRow();

        if (fila == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Seleccione una entrega."
            );

            return;
        }

        int id = (int) modeloEntregas.getValueAt(fila, 0);

        try {

            int idPedido = Integer.parseInt(
                    JOptionPane.showInputDialog(
                            this,
                            "ID del pedido:",
                            modeloEntregas.getValueAt(fila, 1)
                    )
            );

            int idRepartidor = Integer.parseInt(
                    JOptionPane.showInputDialog(
                            this,
                            "ID del repartidor:",
                            modeloEntregas.getValueAt(fila, 2)
                    )
            );

            LocalDate fecha = LocalDate.parse(
                    JOptionPane.showInputDialog(
                            this,
                            "Fecha:",
                            modeloEntregas.getValueAt(fila, 3)
                    )
            );

            LocalTime hora = LocalTime.parse(
                    JOptionPane.showInputDialog(
                            this,
                            "Hora:",
                            modeloEntregas.getValueAt(fila, 4)
                    )
            );

            Entrega entrega = new Entrega(
                    id,
                    idPedido,
                    idRepartidor,
                    fecha,
                    hora
            );

            if (entregaDAO.actualizar(entrega)) {

                JOptionPane.showMessageDialog(
                        this,
                        "Entrega actualizada."
                );

                cargarEntregas();

            } else {

                JOptionPane.showMessageDialog(
                        this,
                        "No se pudo actualizar."
                );
            }

        } catch (Exception e) {

            JOptionPane.showMessageDialog(
                    this,
                    "Datos inválidos:\n" + e.getMessage()
            );
        }
    }

    private void eliminarEntrega() {

        int fila = tablaEntregas.getSelectedRow();

        if (fila == -1) {

            JOptionPane.showMessageDialog(
                    this,
                    "Seleccione una entrega."
            );

            return;
        }

        int id = (int) modeloEntregas.getValueAt(fila, 0);

        int confirmacion = JOptionPane.showConfirmDialog(
                this,
                "¿Eliminar la entrega seleccionada?",
                "Confirmar",
                JOptionPane.YES_NO_OPTION
        );

        if (confirmacion != JOptionPane.YES_OPTION) {
            return;
        }

        if (entregaDAO.eliminar(id)) {

            JOptionPane.showMessageDialog(
                    this,
                    "Entrega eliminada."
            );

            cargarEntregas();

        } else {

            JOptionPane.showMessageDialog(
                    this,
                    "No se pudo eliminar la entrega."
            );
        }
    }
}