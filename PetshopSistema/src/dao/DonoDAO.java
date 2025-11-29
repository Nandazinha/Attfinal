/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author Ferna
 */
import petshop.model.Dono;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DonoDAO {

    public void create(Dono dono) {
        String sql = "INSERT INTO donos (nome, telefone, email) VALUES (?, ?, ?)";
        
        try (Connection conn = ConexaoDAO.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, dono.getNome());
            stmt.setString(2, dono.getTelefone());
            stmt.setString(3, dono.getEmail());
            
            stmt.executeUpdate();
            
            // Recupera o ID gerado
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    dono.setId(generatedKeys.getInt(1));
                }
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao criar dono: " + e.getMessage());
        }
    }

    public List<Dono> readAll() {
        String sql = "SELECT * FROM donos ORDER BY nome";
        List<Dono> donos = new ArrayList<>();
        
        try (Connection conn = ConexaoDAO.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Dono dono = new Dono();
                dono.setId(rs.getInt("id"));
                dono.setNome(rs.getString("nome"));
                dono.setTelefone(rs.getString("telefone"));
                dono.setEmail(rs.getString("email"));
                donos.add(dono);
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar donos: " + e.getMessage());
        }
        
        return donos;
    }

    public void update(Dono dono) {
        String sql = "UPDATE donos SET nome = ?, telefone = ?, email = ? WHERE id = ?";
        
        try (Connection conn = ConexaoDAO.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, dono.getNome());
            stmt.setString(2, dono.getTelefone());
            stmt.setString(3, dono.getEmail());
            stmt.setInt(4, dono.getId());
            
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar dono: " + e.getMessage());
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM donos WHERE id = ?";
        
        try (Connection conn = ConexaoDAO.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar dono: " + e.getMessage());
        }
    }

    public Dono readById(int id) {
        String sql = "SELECT * FROM donos WHERE id = ?";
        Dono dono = null;
        
        try (Connection conn = ConexaoDAO.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    dono = new Dono();
                    dono.setId(rs.getInt("id"));
                    dono.setNome(rs.getString("nome"));
                    dono.setTelefone(rs.getString("telefone"));
                    dono.setEmail(rs.getString("email"));
                }
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar dono: " + e.getMessage());
        }
        
        return dono;
    }
}
