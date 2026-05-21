package com.biblioteca.ui;

import java.awt.Color;
import java.awt.GradientPaint;
import java.awt.Graphics;
import java.awt.Graphics2D;

import javax.swing.JPanel;

public class PainelGradiente extends JPanel {

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D g2d = (Graphics2D) g;

        Color cor1 = new Color(15, 32, 39);
        Color cor2 = new Color(44, 83, 100);

        GradientPaint gradiente = new GradientPaint(
                0, 0, cor1,
                getWidth(), getHeight(), cor2
        );

        g2d.setPaint(gradiente);
        g2d.fillRect(0, 0, getWidth(), getHeight());
    }
}
