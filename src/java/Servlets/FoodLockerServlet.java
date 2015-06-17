package Servlets;

import beans.Category;
import beans.Ingredient;
import beans.Recipe;
import com.itextpdf.text.DocumentException;
import database.DB_Access;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import pdf.PdfCreator;

/**
 *
 * @author Corinna
 */
public class FoodLockerServlet extends HttpServlet {

    private LinkedList<String> li_input_ingredients = new LinkedList<>();
    private LinkedList<Ingredient> li_all_ingredients;
    private LinkedList<Recipe> li_recipes;
    private LinkedList<Category> li_category;
    private PdfCreator pdf = new PdfCreator();
    private DB_Access access;

    public void initalizeListAllIngredients(Ingredient toDeleteIngredient, HttpServletRequest request, HttpServletResponse response) throws Exception {

        StringBuffer sb = new StringBuffer();
        if (toDeleteIngredient == null) {
            li_all_ingredients = access.getIngredients();
        } else {
            li_all_ingredients.remove(toDeleteIngredient);
        }
        sb.append("[");
        for (int i = 0; i < li_all_ingredients.size(); i++) {
            sb.append("\"").append(li_all_ingredients.get(i).getName()).append("\"");
            if (i + 1 < li_all_ingredients.size()) {
                sb.append(",");
            }
        }
        sb.append("]");
        this.getServletContext().setAttribute("attrIngredients", sb);
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            access = DB_Access.getInstance();
        } catch (Exception ex) {
            Logger.getLogger(FoodLockerServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

        try {
            initalizeListAllIngredients(null, request, response);
            li_category = access.getCategory();
            request.setAttribute("li_category", li_category);

        } catch (Exception ex) {
            ex.printStackTrace();
        }
        if (request.getParameter("param") != null) {
            int cat_id = Integer.parseInt(request.getParameter("param"));
            try {
                li_recipes = access.getRecipeForCategory(cat_id);
                request.setAttribute("li_recipes", li_recipes);
            } catch (Exception ex) {
                Logger.getLogger(FoodLockerServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

        request.getRequestDispatcher("jsp/MainJSP.jsp").forward(request, response);
        li_input_ingredients.clear();

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (request.getParameter("txt_ingredient") != null) {
            String ingredient = request.getParameter("txt_ingredient");
            HashMap<Recipe, LinkedList<Ingredient>> shoppingList = new HashMap<>();
            int index = -1;
            try {
                if (ingredient.equals("omnomnom")) {
                    li_recipes = access.getEasterEggRecipes();
                    request.setAttribute("li_recipes", li_recipes);
                } else if (access.isIngredientAvailable(ingredient)) {
                    if (!li_input_ingredients.contains(ingredient) && li_input_ingredients.size() < 10 && ingredient.length() <= 20) {
                        li_input_ingredients.add(ingredient);

                        //wenn Parameter null ist, ist die Checkbox unchecked
                        if (request.getParameter("cb_include") == null) {
                            li_recipes = access.getRecipeForIngredientsWhereAllIngredientsAreAvailable(li_input_ingredients);

                        } else {
                            li_recipes = access.getRecipeForIngredients(li_input_ingredients);
                            for (Recipe r : li_recipes) {
                                LinkedList<Ingredient> li_shoppingList = access.getShoppingList(r, li_input_ingredients);
                                shoppingList.put(r, li_shoppingList);
                            }
                            request.setAttribute("shoppingList", shoppingList);
                            request.setAttribute("checkbox_checked", "checked");
                        }
                        request.setAttribute("li_recipes", li_recipes);

                        for (int i = 0; i < li_all_ingredients.size(); i++) {
                            Ingredient ing = li_all_ingredients.get(i);
                            String name = ing.getName();
                            if (name.equals(ingredient)) {
                                index = ing.getIngredient_id();
                            }
                        }

                        Ingredient ing = new Ingredient(index, ingredient);
                        initalizeListAllIngredients(ing, request, response);
                    }
                } else {

                    request.setAttribute("error", "The ingredient you want to add is not available. We're sorry! :(");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        if(request.getParameter("ingredients")!=null)
        {
//            String list = request.getParameter("ingredients");
//            try {
//                pdf.createPdf(list);
//                Desktop.getDesktop().open(new File(System.getProperty("user.home")+File.separator+"/Desktop"+File.separator+"shopping_list.pdf"));
//            } catch (DocumentException ex) {
//                Logger.getLogger(FoodLockerServlet.class.getName()).log(Level.SEVERE, null, ex);
//            }
        }
        
        request.setAttribute("li_input_ingredients", li_input_ingredients);
        request.getRequestDispatcher("jsp/MainJSP.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
