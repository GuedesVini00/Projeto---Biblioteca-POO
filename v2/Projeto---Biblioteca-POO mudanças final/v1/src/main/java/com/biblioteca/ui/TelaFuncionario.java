package com.biblioteca.ui;

import java.awt.GridLayout;
import java.text.ParseException;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.text.MaskFormatter;

import com.biblioteca.dao.FuncionarioDAO;
import com.biblioteca.model.Funcionario;

public class TelaFuncionario extends JFrame {

    private JTextField txtNome;
    private JFormattedTextField txtCpf;
    private JFormattedTextField txtDataNascimento;
    private JFormattedTextField txtTelefone;
    private JTextField txtEmail;
    private JComboBox<String> comboCargo;

    public TelaFuncionario() {

        setTitle("Cadastro de Funcionário");
        setSize(450, 350);
        setLocationRelativeTo(null);

        JPanel painel = new JPanel(new GridLayout(7, 2, 8, 8));

        painel.setBorder(BorderFactory.createEmptyBorder(20,20,20,20));

        painel.add(new JLabel("Nome:"));
        txtNome = new JTextField();
        painel.add(txtNome);

        painel.add(new JLabel("CPF:"));
        txtCpf = criarCampoMascara("###.###.###-##");
        painel.add(txtCpf);

        painel.add(new JLabel("Data Nascimento:"));
        txtDataNascimento = criarCampoMascara("##/##/####");
        painel.add(txtDataNascimento);

        painel.add(new JLabel("Telefone:"));
        txtTelefone = criarCampoMascara("(##) #####-####");
        painel.add(txtTelefone);

        painel.add(new JLabel("Email:"));
        txtEmail = new JTextField();
        painel.add(txtEmail);

        painel.add(new JLabel("Cargo:"));

        comboCargo = new JComboBox<>(new String[]{
            "Bibliotecário",
            "Atendente",
            "Administrador"
        });

        painel.add(comboCargo);

        JButton btnSalvar = Estilo.criarBotao("Salvar");

        btnSalvar.addActionListener(e -> salvarFuncionario());

        painel.add(new JLabel(""));
        painel.add(btnSalvar);

        add(painel);
    }

    private JFormattedTextField criarCampoMascara(String mascara){

        try {

            MaskFormatter formato = new MaskFormatter(mascara);

            formato.setPlaceholderCharacter('_');

            return new JFormattedTextField(formato);

        }
        catch(ParseException e){

            return new JFormattedTextField();
        }
    }

    private void salvarFuncionario(){

        try {

            Funcionario funcionario = new Funcionario();

            funcionario.setNome(txtNome.getText());
            funcionario.setCpf(txtCpf.getText());
            funcionario.setDataNascimento(txtDataNascimento.getText());
            funcionario.setTelefone(txtTelefone.getText());
            funcionario.setEmail(txtEmail.getText());

            funcionario.setCargo(
                comboCargo.getSelectedItem().toString()
            );

            FuncionarioDAO dao = new FuncionarioDAO();

            dao.inserir(funcionario);

            JOptionPane.showMessageDialog(this,
                    "Funcionário cadastrado com sucesso!");

        }
        catch(Exception erro){

            JOptionPane.showMessageDialog(this,
                    "Erro: " + erro.getMessage());
        }
    }
}