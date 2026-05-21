package com.biblioteca.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TelaPrincipal extends JFrame {

    public TelaPrincipal() {

        Estilo.aplicarTema();

        setTitle("Sistema Biblioteca");
        setSize(760, 620);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        PainelGradiente fundo = new PainelGradiente();
        fundo.setLayout(new BorderLayout());

        JPanel card = new JPanel(new BorderLayout(10, 25));
        card.setOpaque(false);
        card.setBorder(BorderFactory.createEmptyBorder(45, 70, 45, 70));

        JLabel titulo = new JLabel("Sistema de Biblioteca");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 34));
        titulo.setForeground(Color.WHITE);
        titulo.setHorizontalAlignment(JLabel.CENTER);

        JLabel subtitulo = new JLabel("Gerenciamento de leitores, obras, cópias, reservas e empréstimos");
        subtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        subtitulo.setForeground(new Color(220, 220, 220));
        subtitulo.setHorizontalAlignment(JLabel.CENTER);

        JPanel topo = new JPanel(new GridLayout(2, 1, 5, 5));
        topo.setOpaque(false);
        topo.add(titulo);
        topo.add(subtitulo);

        JPanel botoes = new JPanel(new GridLayout(6, 1, 14, 14));
        botoes.setOpaque(false);

        JButton btnLeitor = Estilo.criarBotao("Leitores");
        JButton btnObra = Estilo.criarBotao("Obras");
        JButton btnFuncionario = Estilo.criarBotao("Funcionários");
        JButton btnCopia = Estilo.criarBotao("Cópias");
        JButton btnReserva = Estilo.criarBotao("Reservas");
        JButton btnEmprestimo = Estilo.criarBotao("Empréstimos");

        btnLeitor.addActionListener(e -> new TelaLeitor().setVisible(true));
        btnObra.addActionListener(e -> new TelaObra().setVisible(true));
        btnFuncionario.addActionListener(e -> new TelaFuncionario().setVisible(true));
        btnCopia.addActionListener(e -> new TelaCopia().setVisible(true));
        btnReserva.addActionListener(e -> new TelaReserva().setVisible(true));
        btnEmprestimo.addActionListener(e -> new TelaEmprestimo().setVisible(true));

        botoes.add(btnLeitor);
        botoes.add(btnObra);
        botoes.add(btnFuncionario);
        botoes.add(btnCopia);
        botoes.add(btnReserva);
        botoes.add(btnEmprestimo);

        JLabel rodape = new JLabel("Projeto POO • Java Swing • PostgreSQL");
        rodape.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        rodape.setForeground(new Color(210, 210, 210));
        rodape.setHorizontalAlignment(JLabel.CENTER);

        card.add(topo, BorderLayout.NORTH);
        card.add(botoes, BorderLayout.CENTER);
        card.add(rodape, BorderLayout.SOUTH);

        fundo.add(card, BorderLayout.CENTER);

        setContentPane(fundo);
    }
}