package org.example.model;

import jakarta.persistence.*;

import java.util.List;

@Entity
public class Banque {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;

    @OneToMany(mappedBy = "banque", cascade = CascadeType.ALL)
    private List<Client> clients;

    public Banque() {
    }

    public Banque(Long id, String nom, List<Client> clients) {
        this.id = id;
        this.nom = nom;
        this.clients = clients;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public List<Client> getClients() {
        return clients;
    }

    public void setClients(List<Client> clients) {
        this.clients = clients;
    }
}
