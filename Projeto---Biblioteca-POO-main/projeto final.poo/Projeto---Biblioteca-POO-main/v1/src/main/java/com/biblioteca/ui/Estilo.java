package com.biblioteca.ui;

import java.awt.Color;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.UIManager;

public class Estilo {

    public static final Color AZUL_ESCURO = new Color(25, 42, 86);
    public static final Color BRANCO = Color.WHITE;

    public static final Font FONTE_NORMAL = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font FONTE_BOTAO = new Font("Segoe UI", Font.BOLD, 15);

    public static void aplicarTema() {
        UIManager.put("Button.focus", false);
        UIManager.put("Label.font", FONTE_NORMAL);
        UIManager.put("TextField.font", FONTE_NORMAL);
        UIManager.put("FormattedTextField.font", FONTE_NORMAL);
        UIManager.put("ComboBox.font", FONTE_NORMAL);
    }

    public static JButton criarBotao(String texto) {
        JButton botao = new JButton(texto);
        botao.setFont(FONTE_BOTAO);
        botao.setBackground(AZUL_ESCURO);
        botao.setForeground(BRANCO);
        botao.setFocusPainted(false);
        botao.setBorder(BorderFactory.createEmptyBorder(12, 20, 12, 20));
        return botao;
    }
}