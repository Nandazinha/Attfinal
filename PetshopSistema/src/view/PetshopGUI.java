/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package view;

/**
 *
 * @author Ferna
 */
import petshop.model.Dono;
import petshop.model.Pet;
import petshop.dao.DonoDAO;
import petshop.dao.PetDAO;
import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.util.List;

public class PetshopGUI extends JFrame {
    private JTabbedPane tabbedPane;
    
    // Componentes para a aba de Donos
    private JTable tableDonos;
    private DefaultTableModel modelDonos;
    private JTextField txtNomeDono, txtTelefone, txtEmail;
    private JButton btnAdicionarDono, btnEditarDono, btnExcluirDono, btnLimparDono;
    
    // Componentes para a aba de Pets
    private JTable tablePets;
    private DefaultTableModel modelPets;
    private JTextField txtNomePet, txtEspecie, txtRaca, txtIdade;
    private JComboBox<Dono> comboDono;
    private JButton btnAdicionarPet, btnEditarPet, btnExcluirPet, btnLimparPet;
    
    private DonoDAO donoDAO;
    private PetDAO petDAO;
    private int idSelecionadoDono = -1;
    private int idSelecionadoPet = -1;

    public PetshopGUI() {
        donoDAO = new DonoDAO();
        petDAO = new PetDAO();
        
        initializeUI();
        carregarDonos();
        carregarPets();
        carregarComboDonos();
    }

    private void initializeUI() {
        setTitle("Sistema Petshop - CRUD");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        
        tabbedPane = new JTabbedPane();
        
        // Aba de Donos
        tabbedPane.addTab("Donos", criarPanelDonos());
        
        // Aba de Pets
        tabbedPane.addTab("Pets", criarPanelPets());
        
        add(tabbedPane);
    }

    private JPanel criarPanelDonos() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Painel de formulário
        JPanel formPanel = new JPanel(new GridLayout(2, 4, 5, 5));
        formPanel.setBorder(BorderFactory.createTitledBorder("Dados do Dono"));
        
        formPanel.add(new JLabel("Nome:"));
        txtNomeDono = new JTextField();
        formPanel.add(txtNomeDono);
        
        formPanel.add(new JLabel("Telefone:"));
        txtTelefone = new JTextField();
        formPanel.add(txtTelefone);
        
        formPanel.add(new JLabel("Email:"));
        txtEmail = new JTextField();
        formPanel.add(txtEmail);
        
        // Painel de botões
        JPanel buttonPanel = new JPanel(new FlowLayout());
        btnAdicionarDono = new JButton("Adicionar");
        btnEditarDono = new JButton("Editar");
        btnExcluirDono = new JButton("Excluir");
        btnLimparDono = new JButton("Limpar");
        
        buttonPanel.add(btnAdicionarDono);
        buttonPanel.add(btnEditarDono);
        buttonPanel.add(btnExcluirDono);
        buttonPanel.add(btnLimparDono);
        
        // Tabela
        modelDonos = new DefaultTableModel(new String[]{"ID", "Nome", "Telefone", "Email"}, 0);
        tableDonos = new JTable(modelDonos);
        JScrollPane scrollPane = new JScrollPane(tableDonos);
        
        // Adicionar componentes ao painel principal
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(formPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // Listeners
        btnAdicionarDono.addActionListener(e -> adicionarDono());
        btnEditarDono.addActionListener(e -> editarDono());
        btnExcluirDono.addActionListener(e -> excluirDono());
        btnLimparDono.addActionListener(e -> limparFormularioDono());
        
        tableDonos.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tableDonos.getSelectedRow() != -1) {
                selecionarDono();
            }
        });
        
        return panel;
    }

    private JPanel criarPanelPets() {
        JPanel panel = new JPanel(new BorderLayout());
        
        // Painel de formulário
        JPanel formPanel = new JPanel(new GridLayout(3, 4, 5, 5));
        formPanel.setBorder(BorderFactory.createTitledBorder("Dados do Pet"));
        
        formPanel.add(new JLabel("Nome:"));
        txtNomePet = new JTextField();
        formPanel.add(txtNomePet);
        
        formPanel.add(new JLabel("Espécie:"));
        txtEspecie = new JTextField();
        formPanel.add(txtEspecie);
        
        formPanel.add(new JLabel("Raça:"));
        txtRaca = new JTextField();
        formPanel.add(txtRaca);
        
        formPanel.add(new JLabel("Idade:"));
        txtIdade = new JTextField();
        formPanel.add(txtIdade);
        
        formPanel.add(new JLabel("Dono:"));
        comboDono = new JComboBox<>();
        formPanel.add(comboDono);
        
        // Painel de botões
        JPanel buttonPanel = new JPanel(new FlowLayout());
        btnAdicionarPet = new JButton("Adicionar");
        btnEditarPet = new JButton("Editar");
        btnExcluirPet = new JButton("Excluir");
        btnLimparPet = new JButton("Limpar");
        
        buttonPanel.add(btnAdicionarPet);
        buttonPanel.add(btnEditarPet);
        buttonPanel.add(btnExcluirPet);
        buttonPanel.add(btnLimparPet);
        
        // Tabela
        modelPets = new DefaultTableModel(new String[]{"ID", "Nome", "Espécie", "Raça", "Idade", "Dono"}, 0);
        tablePets = new JTable(modelPets);
        JScrollPane scrollPane = new JScrollPane(tablePets);
        
        // Adicionar componentes ao painel principal
        JPanel topPanel = new JPanel(new BorderLayout());
        topPanel.add(formPanel, BorderLayout.CENTER);
        topPanel.add(buttonPanel, BorderLayout.SOUTH);
        
        panel.add(topPanel, BorderLayout.NORTH);
        panel.add(scrollPane, BorderLayout.CENTER);
        
        // Listeners
        btnAdicionarPet.addActionListener(e -> adicionarPet());
        btnEditarPet.addActionListener(e -> editarPet());
        btnExcluirPet.addActionListener(e -> excluirPet());
        btnLimparPet.addActionListener(e -> limparFormularioPet());
        
        tablePets.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting() && tablePets.getSelectedRow() != -1) {
                selecionarPet();
            }
        });
        
        return panel;
    }

    // Métodos para Donos
    private void carregarDonos() {
        modelDonos.setRowCount(0);
        List<Dono> donos = donoDAO.readAll();
        for (Dono dono : donos) {
            modelDonos.addRow(new Object[]{
                dono.getId(), dono.getNome(), dono.getTelefone(), dono.getEmail()
            });
        }
    }

    private void adicionarDono() {
        try {
            Dono dono = new Dono(
                txtNomeDono.getText(),
                txtTelefone.getText(),
                txtEmail.getText()
            );
            donoDAO.create(dono);
            carregarDonos();
            carregarComboDonos();
            limparFormularioDono();
            JOptionPane.showMessageDialog(this, "Dono adicionado com sucesso!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao adicionar dono: " + e.getMessage());
        }
    }

    private void editarDono() {
        if (idSelecionadoDono == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um dono para editar!");
            return;
        }
        
        try {
            Dono dono = new Dono(
                txtNomeDono.getText(),
                txtTelefone.getText(),
                txtEmail.getText()
            );
            dono.setId(idSelecionadoDono);
            donoDAO.update(dono);
            carregarDonos();
            carregarComboDonos();
            limparFormularioDono();
            JOptionPane.showMessageDialog(this, "Dono atualizado com sucesso!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar dono: " + e.getMessage());
        }
    }

    private void excluirDono() {
        if (idSelecionadoDono == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um dono para excluir!");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Tem certeza que deseja excluir este dono?", "Confirmação", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                donoDAO.delete(idSelecionadoDono);
                carregarDonos();
                carregarComboDonos();
                limparFormularioDono();
                JOptionPane.showMessageDialog(this, "Dono excluído com sucesso!");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro ao excluir dono: " + e.getMessage());
            }
        }
    }

    private void selecionarDono() {
        int row = tableDonos.getSelectedRow();
        if (row != -1) {
            idSelecionadoDono = (int) modelDonos.getValueAt(row, 0);
            txtNomeDono.setText(modelDonos.getValueAt(row, 1).toString());
            txtTelefone.setText(modelDonos.getValueAt(row, 2).toString());
            txtEmail.setText(modelDonos.getValueAt(row, 3).toString());
        }
    }

    private void limparFormularioDono() {
        txtNomeDono.setText("");
        txtTelefone.setText("");
        txtEmail.setText("");
        idSelecionadoDono = -1;
        tableDonos.clearSelection();
    }

    // Métodos para Pets
    private void carregarPets() {
        modelPets.setRowCount(0);
        List<Pet> pets = petDAO.readAll();
        for (Pet pet : pets) {
            modelPets.addRow(new Object[]{
                pet.getId(), pet.getNome(), pet.getEspecie(), 
                pet.getRaca(), pet.getIdade(), pet.getNomeDono()
            });
        }
    }

    private void carregarComboDonos() {
        comboDono.removeAllItems();
        List<Dono> donos = donoDAO.readAll();
        for (Dono dono : donos) {
            comboDono.addItem(dono);
        }
    }

    private void adicionarPet() {
        try {
            Dono donoSelecionado = (Dono) comboDono.getSelectedItem();
            if (donoSelecionado == null) {
                JOptionPane.showMessageDialog(this, "Selecione um dono!");
                return;
            }
            
            Pet pet = new Pet(
                txtNomePet.getText(),
                txtEspecie.getText(),
                txtRaca.getText(),
                Integer.parseInt(txtIdade.getText()),
                donoSelecionado.getId()
            );
            petDAO.create(pet);
            carregarPets();
            limparFormularioPet();
            JOptionPane.showMessageDialog(this, "Pet adicionado com sucesso!");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Idade deve ser um número!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao adicionar pet: " + e.getMessage());
        }
    }

    private void editarPet() {
        if (idSelecionadoPet == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um pet para editar!");
            return;
        }
        
        try {
            Dono donoSelecionado = (Dono) comboDono.getSelectedItem();
            if (donoSelecionado == null) {
                JOptionPane.showMessageDialog(this, "Selecione um dono!");
                return;
            }
            
            Pet pet = new Pet(
                txtNomePet.getText(),
                txtEspecie.getText(),
                txtRaca.getText(),
                Integer.parseInt(txtIdade.getText()),
                donoSelecionado.getId()
            );
            pet.setId(idSelecionadoPet);
            petDAO.update(pet);
            carregarPets();
            limparFormularioPet();
            JOptionPane.showMessageDialog(this, "Pet atualizado com sucesso!");
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this, "Idade deve ser um número!");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Erro ao atualizar pet: " + e.getMessage());
        }
    }

    private void excluirPet() {
        if (idSelecionadoPet == -1) {
            JOptionPane.showMessageDialog(this, "Selecione um pet para excluir!");
            return;
        }
        
        int confirm = JOptionPane.showConfirmDialog(this, 
            "Tem certeza que deseja excluir este pet?", "Confirmação", JOptionPane.YES_NO_OPTION);
        
        if (confirm == JOptionPane.YES_OPTION) {
            try {
                petDAO.delete(idSelecionadoPet);
                carregarPets();
                limparFormularioPet();
                JOptionPane.showMessageDialog(this, "Pet excluído com sucesso!");
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, "Erro ao excluir pet: " + e.getMessage());
            }
        }
    }

    private void selecionarPet() {
        int row = tablePets.getSelectedRow();
        if (row != -1) {
            idSelecionadoPet = (int) modelPets.getValueAt(row, 0);
            txtNomePet.setText(modelPets.getValueAt(row, 1).toString());
            txtEspecie.setText(modelPets.getValueAt(row, 2).toString());
            txtRaca.setText(modelPets.getValueAt(row, 3).toString());
            txtIdade.setText(modelPets.getValueAt(row, 4).toString());
            
            // Selecionar o dono correto no combobox
            String nomeDono = modelPets.getValueAt(row, 5).toString();
            for (int i = 0; i < comboDono.getItemCount(); i++) {
                if (comboDono.getItemAt(i).toString().contains(nomeDono)) {
                    comboDono.setSelectedIndex(i);
                    break;
                }
            }
        }
    }

    private void limparFormularioPet() {
        txtNomePet.setText("");
        txtEspecie.setText("");
        txtRaca.setText("");
        txtIdade.setText("");
        idSelecionadoPet = -1;
        tablePets.clearSelection();
    }
}
