package guru.springframework.bootstrap;

import guru.springframework.domain.*;
import guru.springframework.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

@Component
public class DevBootstrap implements ApplicationListener<ContextRefreshedEvent> {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    UnitOfMeasureRepository unitOfMeasureRepository;

    @Autowired
    RecipeRepository recipeRepository;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {

        init();
    }


    public void init(){

        Recipe recipe = new Recipe();
        recipe.setCookTime(10);
        recipe.setDescription("My Recipe");
        recipe.setDifficulty(Difficulty.EASY);
        recipe.setDirections("COOK IT LIKE YOU MEAN IT");
        recipe.setPrepTime(20);
        recipe.setServings(5);
        recipe.setSource("Some Source");
        recipe.setUrl("SOME URL.com");
        recipe = recipeRepository.save(recipe);

        Set<Category> categories = new HashSet<>();

        for (int i = 0; i < 5; i++) {
            Category cat = new Category();
            cat.setDescription("NEW DESC " + i);
            cat.setRecipes(new HashSet<>(Arrays.asList(recipe, recipeRepository.save(new Recipe()))));

            categoryRepository.save(cat);

            categories.add(cat);
        }

        recipe.setCategories(categories);
        Note note = new Note();
        note.setRecipe(recipe);
        note.setRecipeNotes("MY RECIPE NOTES");

        recipe.setNote(note);

        Set<Ingredient> ingredients = new HashSet<>();
        for (int i = 0; i < 3 ; i++) {
            Ingredient ing = new Ingredient();
            ing.setAmount(4);
            ing.setDescription("GARLIC PEPPER");
            ing.setRecipe(recipe);
            UnitOfMeasure uom = new UnitOfMeasure();
            uom.setDescription("TEASPOONS");

            unitOfMeasureRepository.save(uom);
            ing.setUom(uom);
            //

            ingredients.add(ing);
        }

        recipe.setIngredients(ingredients);

        //
        recipeRepository.save(recipe);


    }
}
