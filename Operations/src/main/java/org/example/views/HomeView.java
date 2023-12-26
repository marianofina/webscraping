package org.example.views;

import org.example.controllers.HomeController;
import org.example.models.Pais;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

public class HomeView extends JFrame {
    private JPanel panelPrincipal = new JPanel(new BorderLayout());

    // Crea paneles secundarios para las secciones
    private JPanel panelSuperior = new JPanel();
    private JPanel panelIzquierdo = new JPanel();
    private JPanel panelDerecho = new JPanel();
    private JPanel panelCentral = new JPanel();

    /* --- Diseño paneles --- */

    // Panel superior
    private ImageIcon imagen = new ImageIcon("C:\\INDEV Solutions\\WebScraping\\Operations\\src\\main\\resources\\loco_cp.png");
    private JLabel labelImagen = new JLabel(imagen);
    private JLabel labelTitulo = new JLabel("Web Scraping Linkedin");

    // Panel izquierdo
    private JLabel labelTituloIzquierdo = new JLabel("Iniciar proceso de recolección");
    private JComboBox<Pais> comboBoxPaises;
    private JButton ejecutar = new JButton("Ejecutar");

    // Panel derecho
    private JTextField dia = new JTextField("dd");
    private JTextField mes = new JTextField("mm");
    private JTextField año = new JTextField("yyyy");
    private JButton crearArchivo = new JButton("Crear archivo .csv");

    // Panel central
    private JLabel labelTituloCentral = new JLabel("Cargar empresas");
    JFileChooser fileChooser = new JFileChooser();

    //Panel inferior
    private JLabel labelTituloInferior = new JLabel("By code projects");

    public HomeView() {
        List<Pais> paises = HomeController.getPaises();
        DefaultComboBoxModel<Pais> model = new DefaultComboBoxModel<>(paises.toArray(new Pais[0]));
        comboBoxPaises = new JComboBox<>(model);

        this.setTitle("Web Scraping Linkedin");

        // Panel superior
        this.panelSuperior.add(labelImagen);
        this.panelSuperior.add(labelTitulo);
        this.panelSuperior.setBackground(Color.WHITE);
        this.panelPrincipal.add(panelSuperior, BorderLayout.NORTH);

        // Panel izquierdo
        this.panelIzquierdo.add(labelTituloIzquierdo);
        this.panelIzquierdo.add(comboBoxPaises);
        this.panelIzquierdo.add(ejecutar);
        this.panelIzquierdo.setBackground(Color.WHITE);
        this.panelPrincipal.add(panelIzquierdo, BorderLayout.WEST);

        // Panel derecho
        this.panelDerecho.add(dia);
        this.panelDerecho.add(mes);
        this.panelDerecho.add(año);
        this.panelDerecho.add(crearArchivo);
        this.panelDerecho.setBackground(Color.WHITE);
        this.panelPrincipal.add(panelDerecho, BorderLayout.EAST);

        // Panel central
        this.panelCentral.add(labelTituloCentral);
        this.panelCentral.add(fileChooser);
        this.panelCentral.setBackground(Color.WHITE);
        this.panelPrincipal.add(panelCentral, BorderLayout.CENTER);

        // Panel inferior
        this.panelPrincipal.add(labelTituloInferior, BorderLayout.SOUTH);

        this.panelPrincipal.setBackground(Color.WHITE);
        this.getContentPane().add(panelPrincipal);
        this.setSize(300, 300);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        class HandlerEjecutar implements ActionListener {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                Pais pais = (Pais) comboBoxPaises.getSelectedItem();
                assert pais != null;
                HomeController.ejecutar(pais.getId());
            }
        }
        this.ejecutar.addActionListener(new HandlerEjecutar());

        class HandlerCargarEmpresas implements ActionListener {
            @Override
            public void actionPerformed(java.awt.event.ActionEvent e) {
                int returnVal = fileChooser.showOpenDialog(panelPrincipal);
                if (returnVal == JFileChooser.APPROVE_OPTION) {
                    HomeController.cargarEmpresas(fileChooser.getSelectedFile());
                }
            }
        }
        this.fileChooser.addActionListener(new HandlerCargarEmpresas());

        class HandlerCrearArchivo implements ActionListener {
            @Override
            public void actionPerformed(ActionEvent e) {
                String fecha = año.getText() + "-" + mes.getText() + "-" + dia.getText();
                HomeController.crearArchivo(fecha);
            }
        }
        this.crearArchivo.addActionListener(new HandlerCrearArchivo());
    }


}
