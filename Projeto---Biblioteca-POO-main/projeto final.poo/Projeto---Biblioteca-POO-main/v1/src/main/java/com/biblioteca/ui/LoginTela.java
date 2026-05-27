package com.biblioteca.ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Font;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import com.biblioteca.service.LoginService;

public class LoginTela extends JFrame {

    private JTextField txtUsuario;
    private JPasswordField txtSenha;

    public LoginTela() {

        Estilo.aplicarTema();

        setTitle("Login - Sistema Biblioteca");
        setSize(580, 490);
        setLocationRelativeTo(null);
        setDefaultCloseOperation(EXIT_ON_CLOSE);

        PainelGradiente fundo = new PainelGradiente();
        fundo.setLayout(new BorderLayout());

        JPanel card = new JPanel(new BorderLayout(10, 25));
        card.setOpaque(false);
        card.setBorder(BorderFactory.createEmptyBorder(50, 70, 50, 70));

        JLabel titulo = new JLabel("Acesso ao Sistema");
        titulo.setFont(new Font("Segoe UI", Font.BOLD, 30));
        titulo.setForeground(Color.WHITE);
        titulo.setHorizontalAlignment(JLabel.CENTER);

        JLabel subtitulo = new JLabel("Informe seu usuário e senha");
        subtitulo.setFont(new Font("Segoe UI", Font.PLAIN, 15));
        subtitulo.setForeground(new Color(220, 220, 220));
        subtitulo.setHorizontalAlignment(JLabel.CENTER);

        JPanel topo = new JPanel(new GridLayout(2, 1, 5, 5));
        topo.setOpaque(false);
        topo.add(titulo);
        topo.add(subtitulo);

        JPanel campos = new JPanel(new GridLayout(4, 1, 8, 8));
        campos.setOpaque(false);

        JLabel lblUsuario = new JLabel("Usuário:");
        lblUsuario.setForeground(Color.WHITE);
        lblUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        txtUsuario = new JTextField();

        JLabel lblSenha = new JLabel("Senha:");
        lblSenha.setForeground(Color.WHITE);
        lblSenha.setFont(new Font("Segoe UI", Font.PLAIN, 14));

        txtSenha = new JPasswordField();

        campos.add(lblUsuario);
        campos.add(txtUsuario);
        campos.add(lblSenha);
        campos.add(txtSenha);

        JButton btnEntrar = Estilo.criarBotao("Entrar");

        btnEntrar.addActionListener(e -> entrar());

        JLabel rodape = new JLabel("Projeto POO • Java Swing • PostgreSQL");
        rodape.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        rodape.setForeground(new Color(210, 210, 210));
        rodape.setHorizontalAlignment(JLabel.CENTER);

        JPanel inferior = new JPanel(new GridLayout(2, 1, 10, 10));
        inferior.setOpaque(false);
        inferior.add(btnEntrar);
        inferior.add(rodape);

        card.add(topo, BorderLayout.NORTH);
        card.add(campos, BorderLayout.CENTER);
        card.add(inferior, BorderLayout.SOUTH);

        fundo.add(card, BorderLayout.CENTER);

        setContentPane(fundo);
    }

    private void entrar() {
        String usuario = txtUsuario.getText();
        String senha = new String(txtSenha.getPassword());

        LoginService service = new LoginService();

        if (service.autenticar(usuario, senha)) {
            JOptionPane.showMessageDialog(this, "Login realizado com sucesso!");
            new TelaPrincipal().setVisible(true);
            dispose();
        } else
            {
            JOptionPane.showMessageDialog(this, "Usuário ou senha inválidos!");
        }
    }
}