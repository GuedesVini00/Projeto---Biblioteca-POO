package com.biblioteca.ui;

import java.awt.GridLayout;
import java.text.ParseException;

import javax.swing.JButton;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

import com.biblioteca.dao.LeitorDAO;
import com.biblioteca.model.Leitor;

public class TelaLeitor extends JFrame {

    private JTextField txtNome;
    private JTextField txtCpf;
    private JTextField txtDataNascimento;
    private JTextField txtTelefone;
    private JTextField txtEmail;

    public TelaLeitor() {
        setTitle("Cadastro de Leitor");
        setSize(400, 300);
        setLocationRelativeTo(null);

        JPanel painel = new JPanel(new GridLayout(6, 2, 5, 5));

        painel.add(new JLabel("Nome:"));
        txtNome = new JTextField();
        painel.add(txtNome);

        painel.add(new JLabel("CPF:"));
        txtCpf = new JTextField("");
        try {

            MaskFormatter formatoCpf = new MaskFormatter("###.###.###-##");
            formatoCpf.setPlaceholderCharacter('_');

            JFormattedTextField campoCpf = new JFormattedTextField(formatoCpf);

            txtCpf = campoCpf;

            painel.add(campoCpf);

        } 
        catch (ParseException ex) {

            System.out.println("Erro ao criar máscara da data");
        }

        painel.add(new JLabel("Data Nascimento:"));
        txtDataNascimento = new JTextField("");
        try {

            MaskFormatter formatoData = new MaskFormatter("##/##/####");
            formatoData.setPlaceholderCharacter('_');

            JFormattedTextField campoData = new JFormattedTextField(formatoData);

            txtDataNascimento = campoData;

            painel.add(campoData);

        } 
        catch (ParseException ex) {

            System.out.println("Erro ao criar máscara da data");
        }
        painel.add(new JLabel("Telefone:"));
        txtTelefone = new JTextField();
        try {

            MaskFormatter formatoTelefone = new MaskFormatter("(##) #####-####");
            formatoTelefone.setPlaceholderCharacter('_');

            JFormattedTextField campoTelefone = new JFormattedTextField(formatoTelefone);

            txtTelefone = campoTelefone;

            painel.add(campoTelefone);

        } 
        catch (ParseException ex) {

            System.out.println("Erro ao criar máscara da data");
        }


        painel.add(new JLabel("Email:"));
        txtEmail = new JTextField();
        painel.add(txtEmail);

        JButton btnSalvar = new JButton("Salvar");
        btnSalvar.addActionListener(e -> salvarLeitor());

        painel.add(btnSalvar);

        add(painel);
    }

    private void salvarLeitor() {
        try {
            Leitor leitor = new Leitor();

            leitor.setNome(txtNome.getText());
            leitor.setCpf(txtCpf.getText());
            leitor.setDataNascimento(txtDataNascimento.getText());
            leitor.setTelefone(txtTelefone.getText());
            leitor.setEmail(txtEmail.getText());

            LeitorDAO dao = new LeitorDAO();
            dao.inserir(leitor);

            JOptionPane.showMessageDialog(this, "Leitor cadastrado com sucesso!");

        } catch (Exception erro) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao cadastrar leitor: " + erro.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
