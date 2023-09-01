package com.store.implementations;

import com.store.models.ProductDetails;
import com.store.services.CATEGORY;
import com.store.services.ProductsManagement;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.io.*;
import java.util.HashMap;
import java.util.Map;


@NoArgsConstructor
@AllArgsConstructor
public class Products implements ProductsManagement {

    private ProductDetails product;

    private static Map<String, ProductDetails> provisions = new HashMap<>();
    private static Map<String, ProductDetails> vegetables = new HashMap<>();
    private static Map<String, ProductDetails> tools = new HashMap<>();
    private static Map<String, ProductDetails> fruits = new HashMap<>();


    @Override
    public boolean addProductsToShelve() {
        File file = new File("./src/main/resources/products.csv");
        String line = "";

        try(BufferedReader reader = new BufferedReader(new FileReader(file))){
            while ((line = reader.readLine()) != null){
                String[] row = line.split(",");
                product = new ProductDetails();
                if(!row[0].equalsIgnoreCase("name")){
                    product.setNameOfProduct(row[0]);

                    if(row[1].equalsIgnoreCase("fruits")){
                        product.setCAT(CATEGORY.FRUITS);
                    }
                    else if(row[1].equalsIgnoreCase("vegetables")){
                        product.setCAT(CATEGORY.VEGETABLES);
                    }
                    else if(row[1].equalsIgnoreCase("tools")){
                        product.setCAT(CATEGORY.TOOLS);
                    }
                    else if(row[1].equalsIgnoreCase("provisions")){
                        product.setCAT(CATEGORY.PROVISIONS);
                    }

                    product.setPrice(Integer.parseInt(row[2]));
                    product.setQuantity(Integer.parseInt(row[3]));

                    if(product.getCAT() == CATEGORY.FRUITS){
                        fruits.put(product.getNameOfProduct(), product);
                    }
                    if(product.getCAT() == CATEGORY.VEGETABLES){
                        vegetables.put(product.getNameOfProduct(), product);
                    }
                    if(product.getCAT() == CATEGORY.PROVISIONS){
                        provisions.put(product.getNameOfProduct(), product);
                    }
                    if(product.getCAT() == CATEGORY.TOOLS){
                        tools.put(product.getNameOfProduct(), product);
                    }
                }
            }

            System.out.println("Done adding Products to Shelve");
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public boolean updateStock() {
        final String DELIMINATOR = ",";
        final String NEW_LINE = "\n";

        StringBuilder header = new StringBuilder();
        StringBuilder fruitsList = new StringBuilder();
        StringBuilder vegetablesList = new StringBuilder();
        StringBuilder provisionsList = new StringBuilder();
        StringBuilder toolsList = new StringBuilder();
        header.append("Name").append(DELIMINATOR).
                append("Category").append(DELIMINATOR).
                append("Price").append(DELIMINATOR).
                append("Quantity").append(NEW_LINE);

        for(var item : fruits.values()){
            fruitsList.append(item.getNameOfProduct()).append(DELIMINATOR).
                    append(item.getCAT()).append(DELIMINATOR).
                    append(item.getPrice()).append(DELIMINATOR).
                    append(item.getQuantity()).append(NEW_LINE);
        }
        for(var item : vegetables.values()){
            vegetablesList.append(item.getNameOfProduct()).append(DELIMINATOR).
                    append(item.getCAT()).append(DELIMINATOR).
                    append(item.getPrice()).append(DELIMINATOR).
                    append(item.getQuantity()).append(NEW_LINE);
        }
        for(var item : provisions.values()){
            provisionsList.append(item.getNameOfProduct()).append(DELIMINATOR).
                    append(item.getCAT()).append(DELIMINATOR).
                    append(item.getPrice()).append(DELIMINATOR).
                    append(item.getQuantity()).append(NEW_LINE);
        }
        for(var item : tools.values()){
            toolsList.append(item.getNameOfProduct()).append(DELIMINATOR).
                    append(item.getCAT()).append(DELIMINATOR).
                    append(item.getPrice()).append(DELIMINATOR).
                    append(item.getQuantity()).append(NEW_LINE);
        }

        File file = new File("./src/main/resources/new_products.csv");
        try(PrintWriter writer = new PrintWriter(file)){
            writer.write(String.valueOf(header));
            writer.write(String.valueOf(fruitsList));
            writer.write(String.valueOf(vegetablesList));
            writer.write(String.valueOf(provisionsList));
            writer.write(String.valueOf(toolsList));
            return true;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }

    public Map<String, ProductDetails> getProvisions() {
        return provisions;
    }

    public Map<String, ProductDetails> getVegetables() {
        return vegetables;
    }

    public Map<String, ProductDetails> getTools() {
        return tools;
    }

    public Map<String, ProductDetails> getFruits() {
        return fruits;
    }

    private void viewProducts(Map<String, ProductDetails> productMap){
        System.out.println("**********************************************************");
        System.out.println("Products                  Price(₦\u200E)              Quantity");
        for (Map.Entry<String, ProductDetails> items : productMap.entrySet()) {
            System.out.printf("%-25s %-25s %-25s",items.getKey(), items.getValue().getPrice(),items.getValue().getQuantity());
            System.out.println();
        }
        System.out.println();
    }

    void view(CATEGORY CAT){
        if (CAT == CATEGORY.FRUITS){
            viewProducts(fruits);
        }
        else if (CAT == CATEGORY.VEGETABLES){
            viewProducts(vegetables);
        }
        else if (CAT == CATEGORY.PROVISIONS){
            viewProducts(provisions);
        }
        else if (CAT == CATEGORY.TOOLS){
            viewProducts(tools);
        }
        else System.out.println("We do not have such category in our store");
    }
}
