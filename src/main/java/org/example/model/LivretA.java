package org.example.model;

import jakarta.persistence.Entity;

import java.util.List;

@Entity
public class LivretA extends Compte {
    private double taux;

    public LivretA() {
    }

    public LivretA(Long numero, double solde, Client client, List<Operation> operations) {
        super(numero, solde, client, operations);
    }

    public double getTaux() {
        return taux;
    }

    public void setTaux(double taux) {
        this.taux = taux;
    }
}

