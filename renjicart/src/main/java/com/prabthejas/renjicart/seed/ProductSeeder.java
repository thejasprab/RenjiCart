package com.prabthejas.renjicart.seed;

import com.prabthejas.renjicart.entity.Product;
import com.prabthejas.renjicart.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ProductSeeder implements CommandLineRunner {

    @Autowired
    private ProductRepository productRepository;

    @Override
    public void run(String... args) throws Exception {
        if(productRepository.count()==0){
//            List<Product> demoProducts = List.of(new Product(null,"Apple Iphone 15",799.0,"Smartphone with a16 chip",4.8,"Amazon",5));
            List<Product> demoProducts = List.of(
                    new Product(null, "Apple iPhone 15", 799.0, "Smartphone with A16 Bionic chip","Electronic", 4.8, "Amazon", 5),
                    new Product(null, "Samsung Galaxy S23", 749.0, "Flagship Android smartphone with Snapdragon processor","Electronic", 4.7, "BestBuy", 8),
                    new Product(null, "Google Pixel 8", 699.0, "Clean Android experience with advanced AI camera","Electronic", 4.6, "Google Store", 6),
                    new Product(null, "MacBook Air M2", 1199.0, "Lightweight laptop with Apple M2 chip","Electronic", 4.9, "Apple Store", 4),
                    new Product(null, "Dell XPS 13", 1099.0, "Compact ultrabook with Intel i7 processor","Electronic", 4.5, "Dell", 7),
                    new Product(null, "Sony WH-1000XM5", 399.0, "Industry-leading noise cancelling headphones","Electronic", 4.8, "Amazon", 12),
                    new Product(null, "Bose QuietComfort Ultra", 429.0, "Premium comfort headphones with spatial audio","Electronic", 4.7, "Bose", 9),
                    new Product(null, "Apple Watch Series 9", 399.0, "Smartwatch with health and fitness tracking","Electronic", 4.6, "Apple Store", 10),
                    new Product(null, "Samsung Galaxy Watch 6", 349.0, "Advanced fitness smartwatch for Android users","Electronic", 4.5, "Samsung", 11),
                    new Product(null, "Logitech MX Master 3S", 99.0, "Ergonomic wireless mouse for productivity","Electronic", 4.8, "Amazon", 20),
                    new Product(null, "Keychron K Pro Mechanical Keyboard", 129.0, "Mechanical keyboard with hot-swappable switches","Electronic", 4.7, "Keychron", 15),
                    new Product(null, "Amazon Echo Dot (5th Gen)", 49.0, "Smart speaker with Alexa voice assistant","Electronic", 4.4, "Amazon", 25)
            );
            productRepository.saveAll(demoProducts);
            System.out.println("Seeded demo data");
        }
        else{
            System.out.println("Products already exists Skipping Seeding");
        }
    }
}
