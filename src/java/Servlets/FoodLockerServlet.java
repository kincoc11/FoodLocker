/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Servlets;

import beans.Ingredient;
import beans.Recipe;
import database.DB_Access;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @author Corinna
 */
public class FoodLockerServlet extends HttpServlet {

    private LinkedList<String> li_input_ingredients = new LinkedList<>();
    private LinkedList<Ingredient> li_all_ingredients;
    private LinkedList<Recipe> li_recipes;
    DB_Access access;

    public void initalizeListAllIngredients(Ingredient toDeleteIngredient, HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        StringBuffer sb = new StringBuffer();
        if (toDeleteIngredient == null) 
        {
            li_all_ingredients = access.getIngredients();
        }
        else
        {
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
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        request.getRequestDispatcher("jsp/MainJSP.jsp").forward(request, response);
        li_input_ingredients.clear();

    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        if (request.getParameter("txt_ingredient") != null) {
            String ingredient = request.getParameter("txt_ingredient");
            int index = -1;
            try {
                if (access.isIngredientAvailable(ingredient)) {
                    if (!li_input_ingredients.contains(ingredient) && li_input_ingredients.size() < 10 && ingredient.length() <= 20) {
                        li_input_ingredients.add(ingredient);
                        li_recipes = access.getRecipeForIngredients(li_input_ingredients);
                        request.setAttribute("li_recipes", li_recipes);
                        
                        for(int i = 0; i<li_all_ingredients.size(); i++)
                        {
                            Ingredient ing = li_all_ingredients.get(i);
                            String name = ing.getName();
                            if(name.equals(ingredient))
                            {
                                index = ing.getIngredient_id();
                            }
                        }
                        
                        Ingredient ing = new Ingredient(index, ingredient);
                        initalizeListAllIngredients(ing, request, response);
                    }
                }
                else
                {
                    System.out.println("salödkfjasöldkfjasölykdfjalösdf");
                    request.setAttribute("error", "The ingredient you want to add is not available. We're sorry! :(");
                }
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
        request.setAttribute("li_input_ingredients", li_input_ingredients);
        request.getRequestDispatcher("jsp/MainJSP.jsp").forward(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
