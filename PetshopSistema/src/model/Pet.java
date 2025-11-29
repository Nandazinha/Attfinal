/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package model;

/**
 *
 * @author Ferna
 */
public class Pet {
    private int id;
    private String nome;
    private String especie;
    private String raca;
    private int idade;
    private int idDono;
    private String nomeDono;

    public Pet() {}

    public Pet(String nome, String especie, String raca, int idade, int idDono) {
        this.nome = nome;
        this.especie = especie;
        this.raca = raca;
        this.idade = idade;
        this.idDono = idDono;
    }

    // Getters e Setters
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getEspecie() { return especie; }
    public void setEspecie(String especie) { this.especie = especie; }

    public String getRaca() { return raca; }
    public void setRaca(String raca) { this.raca = raca; }

    public int getIdade() { return idade; }
    public void setIdade(int idade) { this.idade = idade; }

    public int getIdDono() { return idDono; }
    public void setIdDono(int idDono) { this.idDono = idDono; }

    public String getNomeDono() { return nomeDono; }
    public void setNomeDono(String nomeDono) { this.nomeDono = nomeDono; }

    @Override
    public String toString() {
        return nome + " - " + especie + " (" + raca + ") - " + idade + " anos";
    }
}
