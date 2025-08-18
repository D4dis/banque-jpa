package org.example.model;

import jakarta.persistence.Entity;

import java.time.LocalDate;
import java.util.List;

@Entity
public class AssuranceVie extends Compte {
    private LocalDate dateFin;
    private double taux;

    public AssuranceVie() {
    }

    public AssuranceVie(Long numero, double solde, Client client, List<Operation> operations) {
        super(numero, solde, client, operations);
    }

    public LocalDate getDateFin() {
        return dateFin;
    }

    public void setDateFin(LocalDate dateFin) {
        this.dateFin = dateFin;
    }

    public double getTaux() {
        return taux;
    }

    public void setTaux(double taux) {
        this.taux = taux;
    }
}
