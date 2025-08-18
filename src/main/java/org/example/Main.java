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

            // 1. Creer une banque
            Banque banque = new Banque();
            banque.setNom("Banque Populaire");
            banque.setClients(new ArrayList<>());
            em.persist(banque);

            // 2. Creer une adresse
            Adresse adresse = new Adresse(123, "Rue de la Republique", 75001, "Paris");

            // 3. Creer un client
            Client client = new Client();
            client.setNom("Dupont");
            client.setPrenom("Jean");
            client.setDateNaissance(LocalDate.of(1985, 5, 15));
            client.setAdresse(adresse);
            client.setBanque(banque);
            client.setComptes(new ArrayList<>());
            em.persist(client);

            // 4. Creer un compte courant (classe de base Compte)
            Compte compteCourant = new Compte();
            compteCourant.setSolde(1500.0);
            compteCourant.setClient(client);
            compteCourant.setOperations(new ArrayList<>());
            em.persist(compteCourant);

            // 5. Creer un Livret A
            LivretA livretA = new LivretA();
            livretA.setSolde(5000.0);
            livretA.setTaux(0.75);
            livretA.setClient(client);
            livretA.setOperations(new ArrayList<>());
            em.persist(livretA);

            // 6. Creer une Assurance Vie
            AssuranceVie assuranceVie = new AssuranceVie();
            assuranceVie.setSolde(15000.0);
            assuranceVie.setTaux(2.5);
            assuranceVie.setDateFin(LocalDate.of(2030, 12, 31));
            assuranceVie.setClient(client);
            assuranceVie.setOperations(new ArrayList<>());
            em.persist(assuranceVie);

            // 7. Creer des operations
            Operation operation1 = new Operation();
            operation1.setDate(LocalDateTime.now().minusDays(5));
            operation1.setMontant(-50.0);
            operation1.setMotif("Retrait DAB");
            operation1.setCompte(compteCourant);
            em.persist(operation1);

            // 8. Creer un virement
            Virement virement = new Virement();
            virement.setDate(LocalDateTime.now().minusDays(2));
            virement.setMontant(-200.0);
            virement.setMotif("Virement vers epargne");
            virement.setBeneficiaire("Livret A");
            virement.setCompte(compteCourant);
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

            // Test de lecture pour verification
            System.out.println("\n--- Verification des donnees ---");
            Client clientLu = em.find(Client.class, client.getId());
            System.out.println("Client: " + clientLu.getPrenom() + " " + clientLu.getNom());
            System.out.println("Adresse: " + clientLu.getAdresse().getNumero() + " " +
                    clientLu.getAdresse().getRue() + ", " +
                    clientLu.getAdresse().getCodePostal() + " " +
                    clientLu.getAdresse().getVille());
            System.out.println("Banque: " + clientLu.getBanque().getNom());
            System.out.println("Nombre de comptes: " + clientLu.getComptes().size());

            for (Compte compte : clientLu.getComptes()) {
                String typeCompte = compte.getClass().getSimpleName();
                System.out.println("- " + typeCompte + " (ID: " + compte.getNumero() +
                        ", Solde: " + compte.getSolde() + "€)");

                // Afficher les operations de chaque compte
                if (!compte.getOperations().isEmpty()) {
                    System.out.println("  Operations:");
                    for (Operation op : compte.getOperations()) {
                        System.out.println("    * " + op.getMotif() + ": " + op.getMontant() + "€");
                    }
                }
            }

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