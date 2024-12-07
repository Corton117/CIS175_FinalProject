/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package music.data;

/**
 *
 * @author niall
 */

import java.io.*;
import java.util.*;
import music.business.Product;

public class ProductIO {
    private static List<Product> products = null;
    private static String filePath;

    public static void init(String filePath) {
        ProductIO.filePath = filePath;
    }

    public static List<Product> selectProducts() {
        products = new ArrayList<>();
        File file = new File(filePath);

        try (BufferedReader in = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = in.readLine()) != null) {
                String[] tokens = line.split("\\|");
                if (tokens.length >= 3) {
                    Product p = new Product();
                    p.setCode(tokens[0]);
                    p.setDescription(tokens[1]);
                    p.setPrice(Double.parseDouble(tokens[2]));
                    products.add(p);
                }
            }
        } catch (IOException e) {
        }

        return products;
    }

    public static void insertProduct(Product product) {
        products = selectProducts();
        products.add(product);
        saveProducts(products);
    }

    public static void updateProduct(Product product) {
        products = selectProducts();
        for (int i = 0; i < products.size(); i++) {
            if (products.get(i).getCode().equalsIgnoreCase(product.getCode())) {
                products.set(i, product);
                break;
            }
        }
        saveProducts(products);
    }

    public static void deleteProduct(Product product) {
        products = selectProducts();
        products.removeIf(p -> p.getCode().equalsIgnoreCase(product.getCode()));
        saveProducts(products);
    }

    private static void saveProducts(List<Product> products) {
        try (PrintWriter out = new PrintWriter(new FileWriter(filePath))) {
            for (Product p : products) {
                out.println(p.getCode() + "|" + p.getDescription() + "|" + p.getPrice());
            }
        } catch (IOException e) {
        }
    }
}

