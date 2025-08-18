package org.example.model;

import jakarta.persistence.Entity;

import java.time.LocalDateTime;

@Entity
public class Virement extends Operation {
    private String beneficiaire;

    public Virement() {
    }

    public Virement(Long id, LocalDateTime date, double montant, String motif, Compte compte) {
        super(id, date, montant, motif, compte);
    }

    public String getBeneficiaire() {
        return beneficiaire;
    }

    public void setBeneficiaire(String beneficiaire) {
        this.beneficiaire = beneficiaire;
    }
}
