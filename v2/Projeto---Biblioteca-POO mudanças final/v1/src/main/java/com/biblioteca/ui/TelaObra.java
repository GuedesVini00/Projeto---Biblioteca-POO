package com.biblioteca.ui;

import java.awt.BorderLayout;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

import com.biblioteca.dao.ObraDAO;
import com.biblioteca.model.Obra;

public class TelaObra extends TelaBaseCadastro {

    private JTextField txtTitulo;
    private JTextField txtAutor;
    private JFormattedTextField txtDataPublicacao;
    private JTextField txtCategoria;
    private JComboBox<String> comboTipo;

    public TelaObra() {

        setTitle("Cadastro de Obra");
        setSize(520, 400);
        setLocationRelativeTo(null);

        JPanel fundo = criarFundoCadastro("Cadastro de Obra");
        JPanel painel = criarCardFormulario(6, 2);

        painel.add(new JLabel("Título:"));
        txtTitulo = new JTextField();
        painel.add(txtTitulo);

        painel.add(new JLabel("Autor:"));
        txtAutor = new JTextField();
        painel.add(txtAutor);

        painel.add(new JLabel("Data Publicação:"));
        txtDataPublicacao = criarCampoMascara("##/##/####");
        painel.add(txtDataPublicacao);

        painel.add(new JLabel("Categoria:"));
        txtCategoria = new JTextField();
        painel.add(txtCategoria);

        painel.add(new JLabel("Tipo:"));
        comboTipo = new JComboBox<>(new String[]{"Livro", "Revista", "HQ"});
        painel.add(comboTipo);

        JButton btnSalvar = Estilo.criarBotao("Salvar");
        btnSalvar.addActionListener(e -> salvarObra());

        painel.add(new JLabel(""));
        painel.add(btnSalvar);

        fundo.add(painel, BorderLayout.CENTER);
        setContentPane(fundo);
    }

    private JFormattedTextField criarCampoMascara(String mascara) {
        try {
            MaskFormatter formato = new MaskFormatter(mascara);
            formato.setPlaceholderCharacter('_');
            return new JFormattedTextField(formato);
        } catch (ParseException e) {
            return new JFormattedTextField();
        }
    }

    private void salvarObra() {
        try {
            Obra obra = new Obra();

            obra.setTitulo(txtTitulo.getText());
            obra.setAutor(txtAutor.getText());
            obra.setDataPublicacao(txtDataPublicacao.getText());
            obra.setCategoria(txtCategoria.getText());
            obra.setTipo(comboTipo.getSelectedItem().toString());

            ObraDAO dao = new ObraDAO();
            dao.inserir(obra);

            JOptionPane.showMessageDialog(this, "Obra cadastrada com sucesso!");

        } catch (Exception erro) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao cadastrar obra: " + erro.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}