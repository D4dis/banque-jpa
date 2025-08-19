package org.example;

import jakarta.persistence.EntityManager;
import jakarta.persistence.EntityManagerFactory;
import jakarta.persistence.Persistence;
import org.example.model.*;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    public static void main(String[] args) {
        EntityManagerFactory emf = Persistence.createEntityManagerFactory("banque");
        EntityManager em = emf.createEntityManager();

        try {
            // Commencer une transaction
            em.getTransaction().begin();

            // 1. Créer une banque avec constructeur complet
            Banque banque = new Banque(null, "Banque Populaire", new ArrayList<>());
            em.persist(banque);

            // 2. Créer une adresse avec constructeur
            Adresse adresse = new Adresse(123, "Rue de la République", 75001, "Paris");

            // 3. Créer un client avec constructeur complet
            Client client = new Client(null, "Dupont", "Jean", LocalDate.of(1985, 5, 15), adresse, banque, new ArrayList<>());
            em.persist(client);

            // 4. Créer un compte courant avec constructeur complet
            Compte compteCourant = new Compte(null, 1500.0, client, new ArrayList<>());
            em.persist(compteCourant);

            // 5. Créer un Livret A avec constructeur complet
            LivretA livretA = new LivretA(null, 5000.0, client, new ArrayList<>());
            livretA.setTaux(0.75);
            em.persist(livretA);

            // 6. Créer une Assurance Vie avec constructeur complet
            AssuranceVie assuranceVie = new AssuranceVie(null, 15000.0, client, new ArrayList<>());
            assuranceVie.setTaux(2.5);
            assuranceVie.setDateFin(LocalDate.of(2030, 12, 31));
            em.persist(assuranceVie);

            // 7. Créer des opérations avec constructeur complet
            Operation operation1 = new Operation(null, LocalDateTime.now().minusDays(5), -50.0, "Retrait DAB", compteCourant);
            em.persist(operation1);

            // 8. Créer un virement avec constructeur complet
            Virement virement = new Virement(null, LocalDateTime.now().minusDays(2), -200.0, "Virement vers épargne", compteCourant);
            virement.setBeneficiaire("Livret A");
            em.persist(virement);

            // 9. Ajouter les comptes au client
            client.getComptes().add(compteCourant);
            client.getComptes().add(livretA);
            client.getComptes().add(assuranceVie);

            // 10. Ajouter le client à la banque
            banque.getClients().add(client);

            // Valider la transaction
            em.getTransaction().commit();

            System.out.println("Donnees inserees avec succes !");
        } catch (Exception e) {
            if (em.getTransaction().isActive()) {
                em.getTransaction().rollback();
            }
            System.err.println("Erreur lors de l'insertion des donnees:");
            e.printStackTrace();
        } finally {
            em.close();
            emf.close();
        }
    }
}