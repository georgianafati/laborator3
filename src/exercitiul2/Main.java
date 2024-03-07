package exercitiul2;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

    public class Main {
        private static List<Produs> produse = new ArrayList<>();
        private static double incasari = 0;

        public static void main(String[] args) {
            citesteProdusedinFisier("src/exercitiul2/produse.csv");
            meniu();
        }

        private static void citesteProdusedinFisier(String numeFisier) {
            try (BufferedReader br = new BufferedReader(new FileReader(numeFisier))) {
                String linie;
                while ((linie = br.readLine()) != null) {
                    String[] elemente = linie.split(",");
                    String denumire = elemente[0];
                    double pret = Double.parseDouble(elemente[1]);
                    int cantitate = Integer.parseInt(elemente[2]);
                    LocalDate dataExpirare = LocalDate.parse(elemente[3]);
                    produse.add(new Produs(denumire, pret, cantitate, dataExpirare));
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        private static void meniu() {
            Scanner scanner = new Scanner(System.in);
            boolean continuare = true;
            while (continuare) {
                System.out.println("Meniu:");
                System.out.println("1. Afișare produse");
                System.out.println("2. Afișare produse expirate");
                System.out.println("3. Vânzare produs");
                System.out.println("4. Afișare produse cu preț minim");
                System.out.println("5. Salvare produse cu cantitate mai mică decât o valoare dată");
                System.out.println("6. Ieșire");
                System.out.print("Selectați opțiunea: ");
                int optiune = scanner.nextInt();
                scanner.nextLine();

                switch (optiune) {
                    case 1:
                        afisareProduse();
                        break;
                    case 2:
                        afisareProduseExpirate();
                        break;
                    case 3:
                        vanzareProdus();
                        break;
                    case 4:
                        afisareProduseCuPretMinim();
                        break;
                    case 5:
                        salvareProduseCuCantitateMica();
                        break;
                    case 6:
                        continuare = false;
                        break;
                    default:
                        System.out.println("Opțiune invalidă! Încercați din nou.");
                }
            }
        }

        private static void afisareProduse() {
            System.out.println("Lista de produse:");
            for (Produs produs : produse) {
                System.out.println(produs);
            }
        }

        private static void afisareProduseExpirate() {
            System.out.println("Produse expirate:");
            LocalDate azi = LocalDate.now();
            for (Produs produs : produse) {
                if (produs.getDataExpirare().isBefore(azi)) {
                    System.out.println(produs);
                }
            }
        }

        private static void vanzareProdus() {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Introduceți numele produsului pe care doriți să-l vindeți:");
            String numeProdus = scanner.nextLine();
            boolean produsGasit = false;
            for (Produs produs : produse) {
                if (produs.getDenumire().equalsIgnoreCase(numeProdus)) {
                    produsGasit = true;
                    System.out.println("Introduceți cantitatea pe care doriți să o vindeți:");
                    int cantitateVanduta = scanner.nextInt();
                    if (cantitateVanduta <= produs.getCantitate()) {
                        produs.setCantitate(produs.getCantitate() - cantitateVanduta);
                        incasari += produs.getPret() * cantitateVanduta;
                        if (produs.getCantitate() == 0) {
                            produse.remove(produs);
                        }
                        System.out.println("Vânzare realizată cu succes!");
                    } else {
                        System.out.println("Nu există suficientă cantitate pe stoc!");
                    }
                    break;
                }
            }
            if (!produsGasit) {
                System.out.println("Produsul nu a fost găsit în listă.");
            }
        }

        private static void afisareProduseCuPretMinim() {
            if (produse.isEmpty()) {
                System.out.println("Nu există produse în listă.");
                return;
            }

            double pretMinim = produse.stream().min(Comparator.comparing(Produs::getPret)).get().getPret();
            System.out.println("Produse cu preț minim:");
            for (Produs produs : produse) {
                if (produs.getPret() == pretMinim) {
                    System.out.println(produs);
                }
            }
        }

        private static void salvareProduseCuCantitateMica() {
            Scanner scanner = new Scanner(System.in);
            System.out.println("Introduceți valoarea cantității:");
            int valoareCantitate = scanner.nextInt();
            try (FileWriter writer = new FileWriter("produse_mici.csv")) {
                for (Produs produs : produse) {
                    if (produs.getCantitate() < valoareCantitate) {
                        writer.write(produs.getDenumire() + "," + produs.getPret() + "," + produs.getCantitate() + "," + produs.getDataExpirare() + "\n");
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            System.out.println("Produsele cu cantitate mai mică decât " + valoareCantitate + " au fost salvate în fișierul produse_mici.csv");
        }
    }


