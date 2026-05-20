package com.biblioteca.ui;

import java.awt.GridLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.biblioteca.dao.ObraDAO;
import com.biblioteca.model.Obra;

public class TelaObra extends JFrame {

    private JTextField txtTitulo;
    private JTextField txtAutor;
    private JTextField txtDataPublicacao;
    private JTextField txtCategoria;
    private JTextField txtTipo;

    public TelaObra() {

        setTitle("Cadastro de Obra");
        setSize(450, 320);
        setLocationRelativeTo(null);

        JPanel painel = new JPanel(new GridLayout(6, 2, 5, 5));

        painel.add(new JLabel("Título:"));
        txtTitulo = new JTextField();
        painel.add(txtTitulo);

        painel.add(new JLabel("Autor:"));
        txtAutor = new JTextField();
        painel.add(txtAutor);

        painel.add(new JLabel("Data Publicação:"));
        txtDataPublicacao = new JTextField();
        painel.add(txtDataPublicacao);

        painel.add(new JLabel("Categoria:"));
        txtCategoria = new JTextField();
        painel.add(txtCategoria);

        painel.add(new JLabel("Tipo:"));
        txtTipo = new JTextField();
        painel.add(txtTipo);

        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(e -> salvarObra());

        painel.add(btnSalvar);

        add(painel);
    }

    private void salvarObra() {

        try {
            Obra obra = new Obra();

            obra.setTitulo(txtTitulo.getText());
            obra.setAutor(txtAutor.getText());
            obra.setDataPublicacao(txtDataPublicacao.getText());
            obra.setCategoria(txtCategoria.getText());
            obra.setTipo(txtTipo.getText());

            ObraDAO dao = new ObraDAO();
            dao.inserir(obra);

            JOptionPane.showMessageDialog(this, "Obra cadastrada com sucesso!");

            limparCampos();

        } catch (Exception erro) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao cadastrar obra: " + erro.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limparCampos() {
        txtTitulo.setText("");
        txtAutor.setText("");
        txtDataPublicacao.setText("");
        txtCategoria.setText("");
        txtTipo.setText("");
    }
}
