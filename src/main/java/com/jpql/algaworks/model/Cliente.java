package com.jpql.algaworks.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "cliente")
public class Cliente {

     @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

 //    @Column(name = "cli_nome")
    private String nome;

     public Integer getId() {
        return id;
    }

     public void setId(Integer id) {
        this.id = id;
    }

     public String getNome() {
        return nome;
    }

     public void setNome(String nome) {
        this.nome = nome;
    }

     @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

         Cliente cliente = (Cliente) o;

         return id.equals(cliente.id);
    }

     @Override
    public int hashCode() {
        return id.hashCode();
    }
}