package com.biblioteca.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class TelaBaseCadastro extends JFrame {

    protected JPanel criarFundoCadastro(String tituloTela) {

        PainelGradiente fundo = new PainelGradiente();
        fundo.setLayout(new BorderLayout());

        JLabel titulo = new JLabel(tituloTela);
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 26));
        titulo.setForeground(Color.WHITE);
        titulo.setHorizontalAlignment(JLabel.CENTER);
        titulo.setBorder(BorderFactory.createEmptyBorder(25, 10, 15, 10));

        fundo.add(titulo, BorderLayout.NORTH);

        return fundo;
    }

    protected JPanel criarCardFormulario(int linhas, int colunas) {

        JPanel card = new JPanel(new GridLayout(linhas, colunas, 10, 10));
        card.setBackground(new Color(255, 255, 255, 230));
        card.setBorder(BorderFactory.createEmptyBorder(25, 25, 25, 25));

        return card;
    }
}
