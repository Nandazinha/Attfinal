/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

/**
 *
 * @author Ferna
 */
import petshop.model.Pet;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PetDAO {

    public void create(Pet pet) {
        String sql = "INSERT INTO pets (nome, especie, raca, idade, id_dono) VALUES (?, ?, ?, ?, ?)";
        
        try (Connection conn = ConexaoDAO.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            
            stmt.setString(1, pet.getNome());
            stmt.setString(2, pet.getEspecie());
            stmt.setString(3, pet.getRaca());
            stmt.setInt(4, pet.getIdade());
            stmt.setInt(5, pet.getIdDono());
            
            stmt.executeUpdate();
            
            // Recupera o ID gerado
            try (ResultSet generatedKeys = stmt.getGeneratedKeys()) {
                if (generatedKeys.next()) {
                    pet.setId(generatedKeys.getInt(1));
                }
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao criar pet: " + e.getMessage());
        }
    }

    public List<Pet> readAll() {
        String sql = "SELECT p.*, d.nome as nome_dono FROM pets p " +
                    "LEFT JOIN donos d ON p.id_dono = d.id ORDER BY p.nome";
        List<Pet> pets = new ArrayList<>();
        
        try (Connection conn = ConexaoDAO.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            
            while (rs.next()) {
                Pet pet = new Pet();
                pet.setId(rs.getInt("id"));
                pet.setNome(rs.getString("nome"));
                pet.setEspecie(rs.getString("especie"));
                pet.setRaca(rs.getString("raca"));
                pet.setIdade(rs.getInt("idade"));
                pet.setIdDono(rs.getInt("id_dono"));
                pet.setNomeDono(rs.getString("nome_dono"));
                pets.add(pet);
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar pets: " + e.getMessage());
        }
        
        return pets;
    }

    public void update(Pet pet) {
        String sql = "UPDATE pets SET nome = ?, especie = ?, raca = ?, idade = ?, id_dono = ? WHERE id = ?";
        
        try (Connection conn = ConexaoDAO.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setString(1, pet.getNome());
            stmt.setString(2, pet.getEspecie());
            stmt.setString(3, pet.getRaca());
            stmt.setInt(4, pet.getIdade());
            stmt.setInt(5, pet.getIdDono());
            stmt.setInt(6, pet.getId());
            
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao atualizar pet: " + e.getMessage());
        }
    }

    public void delete(int id) {
        String sql = "DELETE FROM pets WHERE id = ?";
        
        try (Connection conn = ConexaoDAO.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            stmt.executeUpdate();
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao deletar pet: " + e.getMessage());
        }
    }

    public Pet readById(int id) {
        String sql = "SELECT p.*, d.nome as nome_dono FROM pets p " +
                    "LEFT JOIN donos d ON p.id_dono = d.id WHERE p.id = ?";
        Pet pet = null;
        
        try (Connection conn = ConexaoDAO.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            
            stmt.setInt(1, id);
            try (ResultSet rs = stmt.executeQuery()) {
                if (rs.next()) {
                    pet = new Pet();
                    pet.setId(rs.getInt("id"));
                    pet.setNome(rs.getString("nome"));
                    pet.setEspecie(rs.getString("especie"));
                    pet.setRaca(rs.getString("raca"));
                    pet.setIdade(rs.getInt("idade"));
                    pet.setIdDono(rs.getInt("id_dono"));
                    pet.setNomeDono(rs.getString("nome_dono"));
                }
            }
            
        } catch (SQLException e) {
            throw new RuntimeException("Erro ao buscar pet: " + e.getMessage());
        }
        
        return pet;
    }
}