package com.biblioteca.ui;

import java.awt.GridLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import com.biblioteca.dao.CopiaDAO;
import com.biblioteca.dao.ObraDAO;
import com.biblioteca.model.Copia;
import com.biblioteca.model.Obra;

public class TelaCopia extends JFrame {

    private JTextField txtCodigoPatrimonio;
    private JComboBox<String> comboStatus;
    private JComboBox<Obra> comboObra;

    public TelaCopia() {

        setTitle("Cadastro de Cópia");
        setSize(450, 280);
        setLocationRelativeTo(null);

        JPanel painel = new JPanel(new GridLayout(4, 2, 8, 8));
        painel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        painel.add(new JLabel("Código Patrimônio:"));
        txtCodigoPatrimonio = new JTextField();
        painel.add(txtCodigoPatrimonio);

        painel.add(new JLabel("Status:"));
        comboStatus = new JComboBox<>(new String[]{
                "DISPONIVEL",
                "EMPRESTADO",
                "RESERVADO"
        });
        painel.add(comboStatus);

        painel.add(new JLabel("Obra:"));
        comboObra = new JComboBox<>();
        carregarObras();
        painel.add(comboObra);

        JButton btnSalvar = Estilo.criarBotao("Salvar");
        btnSalvar.addActionListener(e -> salvarCopia());

        painel.add(new JLabel(""));
        painel.add(btnSalvar);

        add(painel);
    }

    private void carregarObras() {
        try {
            ObraDAO dao = new ObraDAO();
            List<Obra> obras = dao.listarTodos();

            for (Obra obra : obras) {
                comboObra.addItem(obra);
            }

        } catch (Exception erro) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao carregar obras: " + erro.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void salvarCopia() {
        try {
            Copia copia = new Copia();

            copia.setCodigoPatrimonio(txtCodigoPatrimonio.getText());
            copia.setStatus(comboStatus.getSelectedItem().toString());
            copia.setObra((Obra) comboObra.getSelectedItem());

            CopiaDAO dao = new CopiaDAO();
            dao.inserir(copia);

            JOptionPane.showMessageDialog(this, "Cópia cadastrada com sucesso!");

        } catch (Exception erro) {
            JOptionPane.showMessageDialog(this,
                    "Erro ao cadastrar cópia: " + erro.getMessage(),
                    "Erro",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
