package com.Catalina_Model.Catalina_V_0_3;
import java.util.*;
import java.util.stream.Collectors;

public class TSOPSorter {

    // Lista che contiene i singoli prodotti (termini) già ordinati
    private List<String> sortedProductList;

    /**
     * Costruttore: Prende l'espressione grezza, la elabora e salva i risultati.
     */
    public TSOPSorter(String expression) {
        this.sortedProductList = parseAndSort(expression);
    }

    /**
     * Restituisce l'n-esimo prodotto della somma.
     * @param index L'indice (parte da 0).
     * @return La stringa del prodotto (es: "A & B")
     */
    public String getProduct(int index) {
        if (index < 0 || index >= sortedProductList.size()) {
            throw new IndexOutOfBoundsException("Indice " + index + " non valido. Ci sono solo " + sortedProductList.size() + " prodotti.");
        }
        return sortedProductList.get(index);
    }

    /**
     * Restituisce il numero totale di prodotti trovati.
     */
    public int getProductCount() {
        return sortedProductList.size();
    }

    /**
     * Restituisce l'intera lista dei prodotti (per iterazioni esterne).
     */
    public List<String> getAllProducts() {
        return new ArrayList<>(sortedProductList); // Restituisce una copia per sicurezza
    }

    /**
     * Restituisce l'intera espressione ricostruita come stringa unica.
     */
    @Override
    public String toString() {
        return String.join(" | ", sortedProductList);
    }

    // --- Logica di Parsing (Privata) ---

    private List<String> parseAndSort(String expression) {
        if (expression == null || expression.isEmpty()) return new ArrayList<>();

        // 1. Split per OR (|)
        String[] terms = expression.split("\\|");
        List<String> processedTerms = new ArrayList<>();

        for (String term : terms) {
            // Pulizia
            term = term.trim().replaceAll("^\\(|\\)$", "");
            
            // 2. Split per AND (&) e ordinamento letterali interni
            List<String> literals = Arrays.asList(term.split("&"));
            literals = literals.stream().map(String::trim).collect(Collectors.toList());
            Collections.sort(literals, new LiteralComparator());

            // Ricostruzione termine: "A & C"
            processedTerms.add(String.join(" & ", literals));
        }

        // 3. Ordinamento dei termini stessi tra loro e rimozione duplicati
        processedTerms = processedTerms.stream().distinct().collect(Collectors.toList());
        Collections.sort(processedTerms);

        return processedTerms;
    }

    // --- Comparatore (lo stesso di prima) ---
    private static class LiteralComparator implements Comparator<String> {
        @Override
        public int compare(String s1, String s2) {
            String var1 = s1.replace("!", "");
            String var2 = s2.replace("!", "");
            int nameCompare = var1.compareTo(var2);
            if (nameCompare != 0) return nameCompare;
            return s1.length() - s2.length(); 
        }
    }

    // --- MAIN DI ESEMPIO ---
    public static void main(String[] args) {
        String input = "Zebra & !Alfa | Beta & Gamma | !Alfa & Delta";
        
        System.out.println("Input: " + input);
        System.out.println("------------------------------------------------");

        // 1. Creiamo l'oggetto (il parsing avviene qui)
        TSOPSorter sorter = new TSOPSorter(input);

        // 2. Possiamo accedere all'intera espressione ordinata
        System.out.println("Stringa Completa: " + sorter.toString());

        // 3. Possiamo accedere ai singoli prodotti per indice
        System.out.println("\n--- Accesso per Indice ---");
        
        for (int i = 0; i < sorter.getProductCount(); i++) {
            String prodotto = sorter.getProduct(i);
            System.out.println("Prodotto " + i + ": " + prodotto);
        }

        // Esempio di accesso diretto:
        // L'ordine atteso dei termini (alfabetico) è:
        // 0: !Alfa & Delta
        // 1: !Alfa & Zebra
        // 2: Beta & Gamma
        System.out.println("\nAccesso diretto al 2° elemento: " + sorter.getProduct(1));
    }
}