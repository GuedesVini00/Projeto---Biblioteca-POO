package com.biblioteca.ui;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

public class TelaPrincipal extends JFrame {

    public TelaPrincipal() {

        setTitle("Sistema Biblioteca");

        setSize(400,300);

        setLocationRelativeTo(null);

        setDefaultCloseOperation(EXIT_ON_CLOSE);

        JButton btnLeitor = new JButton("Cadastrar Leitores");

        btnLeitor.addActionListener(e -> {
            new TelaLeitor().setVisible(true);
        });

        JPanel painel = new JPanel();

        painel.add(btnLeitor);

        add(painel);
    }
}