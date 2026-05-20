package com.biblioteca.ui;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class TelaPrincipal extends JFrame {

    public TelaPrincipal() {

        setTitle("Sistema Biblioteca");
        setSize(400, 300);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JButton btnLeitor = new JButton("Leitores");
        JButton btnObra = new JButton("Obras");

        btnLeitor.addActionListener(e -> {
            new TelaLeitor().setVisible(true);
        });

        btnObra.addActionListener(e -> {
            new TelaObra().setVisible(true);
        });

        JPanel painel = new JPanel(new GridLayout(2, 1, 10, 10));

        painel.add(btnLeitor);
        painel.add(btnObra);

        add(painel);
    }
}