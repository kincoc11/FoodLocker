package database;

import beans.Ingredient;
import beans.Recipe;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.Statement;
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

    private static DB_Access theInstance = null;
    

    public static DB_Access getInstance() throws ClassNotFoundException, Exception {
        if (theInstance == null) {
            theInstance = new DB_Access();
        }
        return theInstance;
    }

    private DB_Access() throws ClassNotFoundException, Exception {
        connPool = DB_ConnectionPool.getInstance();
        
        getRecipeForCategory("Breakfast");
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
//asdasdasdasdasd
   
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
            if (count == 1) 
            {
                sqlString += "WHERE UPPER(i.name) = UPPER('" + str + "') GROUP BY r.recipe_id, r.description, r.title, r.category_id ";
            } 
            else
            {
                sqlString += "INTERSECT "
                        + "SELECT DISTINCT r.recipe_id, r.description, r.title, r.category_id "
                        + "FROM Ingredient i INNER JOIN Recipe_ingredient ri ON (i.ingredient_id = ri.ingredient_id) "
                        + "INNER JOIN Recipe r ON (r.recipe_id = ri.recipe_id) "
                        + "WHERE UPPER(i.name) = UPPER('" + str + "') GROUP BY r.recipe_id, r.description, r.title ";
            }
        }
        System.out.println(sqlString);
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
    
    
    public LinkedList getRecipeForCategory(String category) throws Exception
    {
        Connection conn = connPool.getConnection(); 
        Statement stat = conn.createStatement(); 
        
        li_recipes = new LinkedList<>(); 
        String sqlString = "";
        sqlString ="SELECT * " +
                    "FROM recipe r INNER JOIN category c ON(r.category_id = c.category_id) " +
                    "WHERE UPPER(c.name) = UPPER('"+category+"')"; 
    
    
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
        
        for (Recipe r : li_recipes) {
            
            System.out.println(r.getTitle());
        }
        
        connPool.releaseConnection(conn);
        return li_recipes; 
    }
    
  
}
