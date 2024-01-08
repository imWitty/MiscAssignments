/**
 * Program for generating kanji component dependency order via topological sort.
 * 
 * @author (Witt), Acuna
 * @version (version)
 */
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.HashMap;

public class WittMain {

    public static void main(String[] args) {
        // Step 1: Create a hashtable to map IDs to characters
        HashMap<Integer, String> idToCharacter = new HashMap<>();

        // Step 2: Load data-kanji.txt and populate the hashtable
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("data-kanji.txt")))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Skip comment lines
                if (line.startsWith("#")) {
                    continue;
                }
                // Split the line into ID and character
                String[] parts = line.split("\t");
                int id = Integer.parseInt(parts[0]);
                String character = parts[1];
                // Add the ID and character to the hashtable
                idToCharacter.put(id, character);
            }
        } catch (IOException e) {
            System.out.println("Error loading data-kanji.txt: " + e.getMessage());
            return;
        }

        // Step 3: Create an instance of BetterDiGraph
        BetterDiGraph graph = new BetterDiGraph();

        // Step 4: Load data-components.txt and add edges to the graph
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream("data-components.txt")))) {
            String line;
            while ((line = reader.readLine()) != null) {
                // Skip comment lines
                if (line.startsWith("#")) {
                    continue;
                }
                // Split the line into two IDs representing an edge
                String[] parts = line.split("\t");
                int id1 = Integer.parseInt(parts[0]);
                int id2 = Integer.parseInt(parts[1]);
                // Add the edge to the graph
                graph.addEdge(id1, id2);
            }
        } catch (IOException e) {
            System.out.println("Error loading data-components.txt: " + e.getMessage());
            return;
        }

        // Step 5: Create an instance of IntuitiveTopological
        IntuitiveTopological topologicalSort = new IntuitiveTopological(graph);

        // Step 6: Perform the topological sort
        Iterable<Integer> orderedIDs = topologicalSort.order();

        // Step 7: Display the characters in the ordering
        for (int id : orderedIDs) {
            String character = idToCharacter.get(id);
            System.out.print(character + " ");
        }
    }
}
