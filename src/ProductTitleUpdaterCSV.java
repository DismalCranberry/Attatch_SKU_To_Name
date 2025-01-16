import java.io.*;
import java.util.*;

public class ProductTitleUpdaterCSV {

    public static void main(String[] args) {
        // File paths for input files
        String productTitleFile = "product_titles.txt"; // Product titles file
        String skuFile = "sku_numbers.txt";             // SKU numbers file
        String outputFile = "updated_product_titles.csv"; // Output CSV file

        try {
            // Read product titles and SKU numbers into lists
            List<String> productTitles = readFile(productTitleFile);
            List<String> skuNumbers = readFile(skuFile);

            // Check if the lists are of the same size
            if (productTitles.size() != skuNumbers.size()) {
                System.out.println("Error: The number of product titles and SKUs do not match.");
                return;
            }

            // Prepare the output CSV file for writing
            BufferedWriter writer = getBufferedWriter(outputFile, productTitles, skuNumbers);

            // Close the writer
            writer.close();
            System.out.println("Updated product titles written to " + outputFile);

        } catch (IOException e) {
            System.out.println("Error reading/writing files: " + e.getMessage());
        }
    }

    private static BufferedWriter getBufferedWriter(String outputFile, List<String> productTitles, List<String> skuNumbers) throws IOException {
        BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));

        // Write the header for the CSV file
        writer.write("sku,ProductNameWithSKU\n");

        // Loop through the product titles and SKU numbers, and write the combined data
        for (int i = 0; i < productTitles.size(); i++) {
            // Remove commas from the product title
            String cleanedTitle = productTitles.get(i).replace(",", "");
            String updatedTitle = cleanedTitle + " -" + skuNumbers.get(i);
            writer.write(skuNumbers.get(i) + "," + updatedTitle + "\n");
        }
        return writer;
    }

    // Method to read a file and return its lines as a list of strings
    private static List<String> readFile(String fileName) throws IOException {
        List<String> lines = new ArrayList<>();
        BufferedReader reader = new BufferedReader(new FileReader(fileName));
        String line;

        while ((line = reader.readLine()) != null) {
            lines.add(line.trim()); // Remove any extra spaces or newlines
        }

        reader.close();
        return lines;
    }
}
