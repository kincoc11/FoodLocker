package database;

import Comparator.IngredientComparator;
import beans.Category;
import beans.Ingredient;
import beans.Recipe;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedList;
/**
 *
 * @author Yvonne
 */
public class DB_Access {

    private LinkedList<Ingredient> li_ingredients;
    private DB_ConnectionPool connPool;
    private LinkedList<Recipe> li_recipes;
    private HashMap<Recipe, LinkedList<Ingredient>> recipesWithIngredients = new HashMap<>();

    private static DB_Access theInstance = null;

    public static DB_Access getInstance() throws ClassNotFoundException, Exception {
        if (theInstance == null) {
            theInstance = new DB_Access();
        }
        return theInstance;
    }

    private DB_Access() throws ClassNotFoundException, Exception {
        connPool = DB_ConnectionPool.getInstance();
    }

    public LinkedList getIngredients() throws Exception {
        Connection conn = connPool.getConnection();
        Statement stat = conn.createStatement();
        li_ingredients = new LinkedList<>();
        String sqlString = "";
        sqlString = "SELECT * "
                + "FROM ingredient;";

        ResultSet rs = stat.executeQuery(sqlString);

        while (rs.next()) {
            String name = rs.getString("name");
            int ingredient_id = rs.getInt("ingredient_id");

            if (!li_ingredients.contains(name)) {
                Ingredient ingredient = new Ingredient(ingredient_id, name);
                li_ingredients.add(ingredient);
            }
        }

        connPool.releaseConnection(conn);
        return li_ingredients;
    }

    public boolean isIngredientAvailable(String name) throws Exception {
        Connection conn = connPool.getConnection();
        Statement stat = conn.createStatement();
        String sqlString = "";
        sqlString = "SELECT * "
                + "FROM ingredient WHERE UPPER(name) = UPPER('" + name + "');";

        ResultSet rs = stat.executeQuery(sqlString);
        if (rs.next() == true) {
            return true;
        }
        connPool.releaseConnection(conn);
        return false;
    }

    public LinkedList<Recipe> getRecipeForIngredients(LinkedList<String> li_used_ingredients) throws Exception {
        Connection conn = connPool.getConnection();
        Statement stat = conn.createStatement();
        li_recipes = new LinkedList<>();
        int count = 0;
        String sqlString = "";

        sqlString = "SELECT DISTINCT r.recipe_id, r.description, r.title, r.category_id "
                + "FROM Ingredient i "
                + "INNER JOIN Recipe_ingredient ri ON (i.ingredient_id = ri.ingredient_id)  "
                + "INNER JOIN Recipe r ON (r.recipe_id = ri.recipe_id) ";

        for (String str : li_used_ingredients) {
            count++;
            if (count == 1) {
                sqlString += "WHERE UPPER(i.name) = UPPER('" + str + "') GROUP BY r.recipe_id, r.description, r.title, r.category_id ";
            } else {
                sqlString += "INTERSECT "
                        + "SELECT DISTINCT r.recipe_id, r.description, r.title, r.category_id "
                        + "FROM Ingredient i INNER JOIN Recipe_ingredient ri ON (i.ingredient_id = ri.ingredient_id) "
                        + "INNER JOIN Recipe r ON (r.recipe_id = ri.recipe_id) "
                        + "WHERE UPPER(i.name) = UPPER('" + str + "') GROUP BY r.recipe_id, r.description, r.title ";
            }
        }
        ResultSet rs = stat.executeQuery(sqlString);

        while (rs.next()) {
            String description = rs.getString("description");
            int recipe_id = rs.getInt("recipe_id");
            String title = rs.getString("title");
            int category_id = rs.getInt("category_id");

            Recipe recipe = new Recipe(recipe_id, description, title, category_id);
            if (!li_recipes.contains(recipe)) {
                li_recipes.add(recipe);
            }
        }

        connPool.releaseConnection(conn);
        return li_recipes;
    }

    public LinkedList<Recipe> getRecipeForIngredientsWhereAllIngredientsAreAvailable(LinkedList<String> li_used_ingredients) throws Exception {
        Collections.sort(li_used_ingredients);

        Connection conn = connPool.getConnection();
        Statement stat = conn.createStatement();
        LinkedList<Ingredient> li_ingredientsPerRecipe = new LinkedList<>();
        LinkedList<Recipe> finishedRecipes = new LinkedList<>();
        String sqlString = "";

        sqlString = "SELECT r.recipe_id, r.description, r.title, r.category_id, i.ingredient_id, i.name "
                + "FROM Ingredient i "
                + "INNER JOIN Recipe_ingredient ri ON (i.ingredient_id = ri.ingredient_id) "
                + "INNER JOIN Recipe r ON (r.recipe_id = ri.recipe_id) ";

        ResultSet rs = stat.executeQuery(sqlString);

        while (rs.next()) {
            String description = rs.getString("description");
            int recipe_id = rs.getInt("recipe_id");
            String title = rs.getString("title");
            int category_id = rs.getInt("category_id");

            Recipe r = new Recipe(recipe_id, description, title, category_id);
            li_ingredientsPerRecipe = getIngredientsForRecipe(title);
            recipesWithIngredients.put(r, li_ingredientsPerRecipe);
        }

        connPool.releaseConnection(conn);

        for (Recipe r : recipesWithIngredients.keySet()) {
            LinkedList<Ingredient> li_ingredients = recipesWithIngredients.get(r);
            //nur wenn die # der user-Zutaten gleich sind wie die von einem Rezept muss verglichen werden
            Collections.sort(li_ingredients, new IngredientComparator());

            if (li_ingredients.size() == li_used_ingredients.size()) {
                int countOfEqualIngredients = 0;
                outer:
                for (int i = 0; i < li_used_ingredients.size(); i++) {
                    inner:
                    for (int j = i; j < li_ingredients.size(); j++) {
                        if (li_ingredients.get(j).getName().equals(li_used_ingredients.get(i))) {
                            countOfEqualIngredients++;
                            System.out.println(r.getTitle() + " - Count: " + countOfEqualIngredients);
                            if (countOfEqualIngredients == li_used_ingredients.size()) {
                                finishedRecipes.add(r);
                                break outer;
                            }
                            break inner;

                        } //da die Zutaten in beiden Listen sortiert sind, muss die erste Zutat schon gleich sein, ansonsten wird mit dem nächsten Rezept fortgesetzt
                        else {
                            break outer;
                        }
                    }
                }

            }

        }
        return finishedRecipes;
    }

    public LinkedList<Ingredient> getIngredientsForRecipe(String title) throws Exception {
        Connection conn = connPool.getConnection();
        Statement stat = conn.createStatement();
        LinkedList<Ingredient> li_ingredientsPerRecipe = new LinkedList<>();
        String sqlString = "";
        sqlString = "SELECT i.ingredient_id, i.name "
                + "FROM Ingredient i "
                + "INNER JOIN Recipe_ingredient ri ON (i.ingredient_id = ri.ingredient_id) "
                + "INNER JOIN Recipe r ON (r.recipe_id = ri.recipe_id) WHERE UPPER(r.title)=UPPER('" + title + "')";

        li_ingredientsPerRecipe.clear();
        ResultSet rs = stat.executeQuery(sqlString);

        while (rs.next()) {
            int ingredient_id = rs.getInt("ingredient_id");
            String ingredient = rs.getString("name");
            Ingredient i = new Ingredient(ingredient_id, ingredient);
            li_ingredientsPerRecipe.add(i);
        }

        connPool.releaseConnection(conn);
        return li_ingredientsPerRecipe;
    }
    
    public LinkedList<Ingredient> getShoppingList(Recipe r, LinkedList<String> li_used_ingredients) throws Exception {
        LinkedList<Ingredient> li_ingredientsPerRecipe = getIngredientsForRecipe(r.getTitle());

        Collections.sort(li_ingredientsPerRecipe, new IngredientComparator());
        Collections.sort(li_used_ingredients);

        for(int i = 0; i<li_ingredientsPerRecipe.size(); i++)
        {
            for(int j = 0; j<li_used_ingredients.size(); j++)
            {
                if(li_ingredientsPerRecipe.get(i).getName().equals(li_used_ingredients.get(j)))
                {
                    li_ingredientsPerRecipe.remove(li_ingredientsPerRecipe.get(i));
                }
            }
            
        }

        return li_ingredientsPerRecipe;
    }

    public LinkedList<Recipe> getRecipeForCategory(int cat_id) throws Exception {
        Connection conn = connPool.getConnection();
        Statement stat = conn.createStatement();

        li_recipes = new LinkedList<>();
        String sqlString = "";
        sqlString = "SELECT * "
                + "FROM recipe r "
                + "WHERE r.category_id = " + cat_id;

        ResultSet rs = stat.executeQuery(sqlString);

        while (rs.next()) {
            String description = rs.getString("description");
            int recipe_id = rs.getInt("recipe_id");
            String title = rs.getString("title");
            int category_id = rs.getInt("category_id");

            Recipe recipe = new Recipe(recipe_id, description, title, category_id);
            if (!li_recipes.contains(recipe)) {
                li_recipes.add(recipe);
            }
        }

        connPool.releaseConnection(conn);
        return li_recipes;
    }

    public LinkedList<Category> getCategory() throws SQLException, Exception {
        Connection conn = connPool.getConnection();
        Statement stat = conn.createStatement();

        LinkedList<Category> li_category = new LinkedList<>();

        String sqlString = "";
        sqlString = "SELECT * "
                + "FROM category ";

        ResultSet rs = stat.executeQuery(sqlString);

        while (rs.next()) {
            String name = rs.getString("name");
            int category_id = rs.getInt("category_id");

            Category category = new Category(name, category_id);

            if (!li_category.contains(category)) {
                li_category.add(category);
            }
        }

        connPool.releaseConnection(conn);
        return li_category;
    }

    public LinkedList<Recipe> getEasterEggRecipes() throws Exception {
        Connection conn = connPool.getConnection();
        Statement stat = conn.createStatement();

        li_recipes = new LinkedList<>();
        String sqlString = "";
        sqlString = "SELECT * "
                + "FROM recipe r "
                + "WHERE r.recipe_id = 1";

        ResultSet rs = stat.executeQuery(sqlString);

        while (rs.next()) {
            String description = rs.getString("description");
            int recipe_id = rs.getInt("recipe_id");
            String title = rs.getString("title");
            int category_id = rs.getInt("category_id");

            Recipe recipe = new Recipe(recipe_id, description, title, category_id);
            if (!li_recipes.contains(recipe)) {
                li_recipes.add(recipe);
            }
        }

        connPool.releaseConnection(conn);
        return li_recipes;
    }
    
    public LinkedList<String> updateIngredientList(String toDeleteIngredient, LinkedList<String> li_oldIngredients)
    {
        LinkedList<String> li_updatedIngredients = new LinkedList<>(); 
        
        for (String str : li_oldIngredients) 
        {
            if(!str.equals(toDeleteIngredient))
            {
                li_updatedIngredients.add(str);
         
            }
        }
        
        return li_updatedIngredients;
    }
    
    public void insertOwnRecipe(String title, String description, LinkedList<Integer> li_amount, LinkedList<String> li_unit, LinkedList<String> li_toInsertIngredients) throws SQLException, Exception
    {
        Connection conn = connPool.getConnection();
        Statement stat = conn.createStatement();

   

        String sqlString = "";
        sqlString = "SELECT * "
                + "FROM category ";

        stat.executeQuery(sqlString);

      

        connPool.releaseConnection(conn);
       // return li_category;
    }
}
