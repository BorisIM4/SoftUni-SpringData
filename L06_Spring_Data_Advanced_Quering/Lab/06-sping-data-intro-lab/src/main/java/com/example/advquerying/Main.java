package com.example.advquerying;

import com.example.advquerying.services.IngredientService;
import com.example.advquerying.services.ShampooService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

@Component
public class Main implements CommandLineRunner {

    private final ShampooService shampooService;

    private final IngredientService ingredientService;

    public Main(ShampooService shampooService, IngredientService ingredientService) {
        this.shampooService = shampooService;
        this.ingredientService = ingredientService;
    }

    @Override
    public void run(String... args) throws Exception {
        Scanner scanner= new Scanner(System.in);

//        System.out.println("TASK 1");
//        System.out.println("Enter size (MEDIUM, SMALL, LARGE)");
//        Size size = Size.valueOf(scanner.nextLine());
//        this.shampooService.findAllBySizeOrderById(size).forEach(System.out::println);

//        System.out.println("TASK 2");
//        System.out.println("Enter size (MEDIUM, SMALL, LARGE)");
//        Size sizeTaskTwo = Size.valueOf(scanner.nextLine());
//        long labelId = Long.parseLong(scanner.nextLine());
//        this.shampooService.findAllBySizeOrLabelIdOrderByPrice(sizeTaskTwo, labelId).forEach(System.out::println);

//        System.out.println("Task 3");
//        System.out.println("Enter price");
//        BigDecimal price = new BigDecimal(scanner.nextLine());
//        this.shampooService.findAllByPriceGreaterThanOrderByPriceDesc(price).forEach(System.out::println);

//        System.out.println("Task 4");
//        System.out.println("Enter letter");
//        String nameStartsWith = scanner.nextLine();
//        this.ingredientService.findAllByNameIsStartingWith(nameStartsWith).forEach(System.out::println);

//        System.out.println("Task 5");
//        System.out.println("Enter ingredients");
//        List<String> names = Arrays.asList("Lavender", "Herbs", "Apple");
//        this.ingredientService.findAllByNameIn(names).forEach(System.out::println);

//        System.out.println("Task 6");
//        System.out.println("Enter price");
//        BigDecimal priceLess = new BigDecimal(scanner.nextLine());
//        System.out.println(this.shampooService.countAllByPriceLessThan(priceLess));

//        System.out.println("Task 7");
//        System.out.println("");
//        List<String> namesOfIngredients = Arrays.asList("Berry", "Mineral-Collagen");
//        this.shampooService.findAllByIngredientsName(namesOfIngredients).forEach(System.out::println);

//        System.out.println("Task 8");
//        System.out.println("Enter count");
//        int countOfIngredients = Integer.parseInt(scanner.nextLine());
//        this.shampooService.findAllByIngredientsCount(countOfIngredients).forEach(System.out::println);

//        System.out.println("Task 9");
//        System.out.println("Enter Name");
//        String nameForDeletion = scanner.nextLine();
//        System.out.println("Deleted rows: " + this.ingredientService.deleteAllByName(nameForDeletion));

//        System.out.println("Task 10");
//        this.ingredientService.increaseAllPricesWith10Percents();
//        System.out.println("SUCCESS");

        System.out.println("Task 11");
        List<String> names = Arrays.asList("Lavender", "Herbs");
        this.ingredientService.updatesPriceByName(BigDecimal.valueOf(1.2), names);
    }
}
