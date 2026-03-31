package com.Catalina_Model.Catalina_V_0_3;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;

public class BooleanExpressionSorter {
	
	private static String Or_Symbol ="+";
	private static String And_Symbol ="&";

    // Classe contenitore per restituire sia la stringa che l'ArrayList
    public static class SortedResult {
        private String fullExpression;
        private List<String> productList;

        public SortedResult(String fullExpression, List<String> productList) {
            this.fullExpression = fullExpression;
            this.productList = productList;
        }

        public String getFullExpression() { return fullExpression; }
        public List<String> getProductList() { return productList; }
    }

    /**
     * Metodo principale per parsare e ordinare l'espressione
     */
    public static SortedResult sortExpression(String expression) {
        if (expression == null || expression.trim().isEmpty()) {
            return new SortedResult("", new ArrayList<>());
        }

        // 1. Dividiamo l'espressione nei suoi prodotti separati da '+'
//        String[] rawProducts = expression.split("\\+");
        String[] rawProducts = expression.split("\\"+Or_Symbol);
        List<String> processedProducts = new ArrayList<>();

        // 2. Creiamo un comparatore personalizzato per l'ordine alfabetico.
        // Ignora i caratteri speciali (!, *, parentesi) per confrontare solo le lettere.
        Comparator<String> alphabeticalComparator = (s1, s2) -> {
            String clean1 = s1.replaceAll("[^a-zA-Z0-9]", "");
            String clean2 = s2.replaceAll("[^a-zA-Z0-9]", "");
            int cmp = clean1.compareToIgnoreCase(clean2);
            
            // Se le lettere sono uguali (es. "A" e "!A"), il fallback sull'ordinamento 
            // standard garantisce un ordine deterministico (es. "!A" viene prima di "A")
            if (cmp == 0) {
                return s1.compareTo(s2);
            }
            return cmp;
        };

        // 3. Elaboriamo ogni singolo prodotto
        for (String rawProd : rawProducts) {
            rawProd = rawProd.trim();
            boolean isGloballyNegated = false;
            
            // Gestione della negazione su un intero prodotto: es. !(C * A)
            if (rawProd.startsWith("!(") && rawProd.endsWith(")")) {
                isGloballyNegated = true;
                // Rimuoviamo "!(" e ")" per elaborare il contenuto
                rawProd = rawProd.substring(2, rawProd.length() - 1).trim();
            }

            // Dividiamo il prodotto nei suoi letterali (es. "B", "!A", "C")
//            String[] literals = rawProd.split("\\*");
            String[] literals = rawProd.split("\\"+And_Symbol);
            List<String> literalList = new ArrayList<>();
            for (String lit : literals) {
                literalList.add(lit.trim());
            }

            // Ordiniamo i letterali all'interno del prodotto
            literalList.sort(alphabeticalComparator);

            // Ricostruiamo il prodotto ordinato
            String sortedProduct = String.join(" & ", literalList);
            if (isGloballyNegated) {
                sortedProduct = "!(" + sortedProduct + ")"; // Ripristiniamo la negazione
            }
            
            processedProducts.add(sortedProduct);
        }

        // 4. Ordiniamo l'intera lista di prodotti (dal primo all'ultimo)
        processedProducts.sort(alphabeticalComparator);

        // 5. Ricostruiamo l'espressione finale
        String finalExpression = String.join(" + ", processedProducts);

        return new SortedResult(finalExpression, processedProducts);
    }

    // --- METODO DI TEST ---
    public static void main(String[] args) {
        // Espressione disordinata di test
//    	String input = "C * !A * B + !(D * B * A) + Z * X + A * !C";
//    	String input = "C * !A * B + !(D * B * A) + Z * X + A * !C";
    	String input = "C&!A&B+!(D&B&A)+Z&X+A&!C";
        
        System.out.println("Input Originale: " + input);
        System.out.println("-------------------------------------------------");

        // Esecuzione dell'algoritmo
        SortedResult result = sortExpression(input);

        // Output: La stringa completa ordinata
        System.out.println("Stringa Ordinata: " + result.getFullExpression());
        System.out.println("-------------------------------------------------");

        // Output: L'ArrayList con i singoli prodotti ordinati
        System.out.println("Lista dei singoli prodotti:");
        List<String> products = result.getProductList();
        for (int i = 0; i < products.size(); i++) {
            System.out.println(" Prodotto [" + i + "]: " + products.get(i));
        }
        HashMap<Object, String> prova = new HashMap<Object, String>();
        Integer o1=1;
        Double o2 = 1.00;
        prova.put(o1, "intero");
        prova.put(o2, "virgola mobile");
        
        Integer o3=1;
        System.out.println("--- "+prova.get(o3));
        
    }
}