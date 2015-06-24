package Servlets;

import beans.Category;
import beans.Ingredient;
import beans.Recipe;
import com.itextpdf.text.DocumentException;
import database.DB_Access;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import util.ServletUtil;
import pdf.PdfCreator;

/**
 *
 * @author Corinna
 */
public class FoodLockerServlet extends HttpServlet
{

    private LinkedList<String> li_input_ingredients = new LinkedList<>();
    private LinkedList<Ingredient> li_all_ingredients;
    private LinkedList<Recipe> li_recipes;
    private LinkedList<Category> li_category;
    private PdfCreator pdf = new PdfCreator();
    private DB_Access access;
    private String pdfPath = System.getProperty("user.home") + File.separator + "/Desktop" + File.separator;

    private int countIngredientInput = 1;
    private LinkedList<String> li_txt_ingredient = new LinkedList<>();
    private LinkedList<Integer> li_txt_menge = new LinkedList<>();
    private LinkedList<String> li_txt_einheit = new LinkedList<>();
    private String title;
    private String description;
    private String category;
    private String inputRecipeError = "";
    private boolean isError = false;
    private String einfuegenErfolgreich = "";

    /**
     * The list of available ingredients for the drop down menu in the
     * MainJSP.jsp is generated using a stringbuffer so it can be handled as an
     * array in the javascript script in the jsp file.
     *
     * @param toDeleteIngredient
     * @param request
     * @param response
     * @throws Exception
     */
    public void initializeListOfAvailableIngredients(Ingredient toDeleteIngredient, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        StringBuffer sb = new StringBuffer();
        if (toDeleteIngredient == null)
        {
            li_all_ingredients = access.getIngredients();
        } else
        {
            li_all_ingredients.remove(toDeleteIngredient);
        }
        sb.append("[");
        for (int i = 0; i < li_all_ingredients.size(); i++)
        {
            sb.append("\"").append(li_all_ingredients.get(i).getName()).append("\"");
            if (i + 1 < li_all_ingredients.size())
            {
                sb.append(",");
            }
        }
        sb.append("]");
        this.getServletContext().setAttribute("attrIngredients", sb);

    }

    /**
     * Checks if parameters from the InputJSP are null or Empty. If they are
     * null or Empty it forwards back to the InoutJSP with an error message
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     * @throws Exception
     */
    public void checkNewIngredientInput(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, Exception
    {

        if (request.getParameter("txt_menge0") != null && !request.getParameter("txt_menge0").isEmpty())
        {
            try
            {
                Integer.parseInt(request.getParameter("txt_menge0"));
            } catch (Exception e)
            {
                inputRecipeError = "Please enter a numeric amount";
                request.setAttribute("inputRecipeError", inputRecipeError);

                isError = true;
                request.setAttribute("isError", isError);
                request.setAttribute("countIngredientInput", countIngredientInput);
                request.getRequestDispatcher("jsp/InputJSP.jsp").forward(request, response);
            }
        }
        if (request.getParameter("txt_title") != null && request.getParameter("textarea") != null
                && request.getParameter("txt_category") != null && request.getParameter("txt_menge0") != null
                && request.getParameter("txt_einheit0") != null && request.getParameter("txt_ingredient0") != null
                && !request.getParameter("txt_title").isEmpty() && !request.getParameter("textarea").isEmpty()
                && !request.getParameter("txt_category").isEmpty() && !request.getParameter("txt_menge0").isEmpty()
                && !request.getParameter("txt_einheit0").isEmpty() && !request.getParameter("txt_ingredient0").isEmpty()
                && access.isIngredientAvailable(request.getParameter("txt_ingredient0")) == true
                && access.isUnitAvailable(request.getParameter("txt_einheit0")) == true)
        {

            countIngredientInput++;
            isError = false;
        } else
        {
            isError = true;
        }

        request.setAttribute("isError", isError);
        request.setAttribute("countIngredientInput", countIngredientInput);

    }

    /**
     * Checks if any parameters from the InputJSP are null If not it saves the
     * parameters into variables
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     * @throws Exception
     */
    public void saveInput(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException, Exception
    {
        if (request.getParameter("txt_title").isEmpty())
        {

            inputRecipeError = "Please enter a title";
            request.setAttribute("inputRecipeError", inputRecipeError);
            request.setAttribute("countIngredientInput", countIngredientInput);

        } else if (request.getParameter("textarea").isEmpty())
        {

            inputRecipeError = "Please enter a description";
            request.setAttribute("inputRecipeError", inputRecipeError);
            request.setAttribute("countIngredientInput", countIngredientInput);
        } else if (request.getParameter("txt_category").isEmpty())
        {

            inputRecipeError = "Please enter a category";
            request.setAttribute("inputRecipeError", inputRecipeError);
            request.setAttribute("countIngredientInput", countIngredientInput);
        } else if (request.getParameter("txt_menge0").isEmpty())
        {

            inputRecipeError = "Please enter an amount";
            request.setAttribute("inputRecipeError", inputRecipeError);
            request.setAttribute("countIngredientInput", countIngredientInput);
        } else if (request.getParameter("txt_einheit0").isEmpty())
        {

            inputRecipeError = "Please enter an unit";
            request.setAttribute("inputRecipeError", inputRecipeError);
            request.setAttribute("countIngredientInput", countIngredientInput);
        } else if (request.getParameter("txt_ingredient0").isEmpty())
        {

            inputRecipeError = "Please enter an ingredient";
            request.setAttribute("inputRecipeError", inputRecipeError);
            request.setAttribute("countIngredientInput", countIngredientInput);
        } else if (access.isIngredientAvailable(request.getParameter("txt_ingredient0")) == false)
        {
            inputRecipeError = "Please enter a valid ingredient";
            request.setAttribute("inputRecipeError", inputRecipeError);
            request.setAttribute("countIngredientInput", countIngredientInput);
        } else if (access.isUnitAvailable(request.getParameter("txt_einheit0")) == false)
        {
            inputRecipeError = "Please enter a valid unit";
            request.setAttribute("inputRecipeError", inputRecipeError);
            request.setAttribute("countIngredientInput", countIngredientInput);
        } else
        {
            title = ServletUtil.filter(request.getParameter("txt_title"));
            description = ServletUtil.filter(request.getParameter("textarea"));
            category = request.getParameter("txt_category");
            li_txt_menge.add(Integer.parseInt(request.getParameter("txt_menge0")));
            li_txt_einheit.add(request.getParameter("txt_einheit0"));
            li_txt_ingredient.add(request.getParameter("txt_ingredient0"));

        }

    }

    /**
     * Checks if the category from the InputJSP is available
     *
     * @param request
     * @param response
     * @throws Exception
     */
    public void checkCategory(HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        String test_category = request.getParameter("txt_category");
        String final_category = "";

        if (test_category.toUpperCase().equals("SNACKS"))
        {
            final_category = "Snacks and Side Dishes";
        } else if (test_category.toUpperCase().equals("MAIN"))
        {
            final_category = "Main Dishes";
        } else
        {
            final_category = test_category;
        }

        if (access.isCategoryAvailable(final_category) == false)
        {
            inputRecipeError = "Please enter a valid category";
            request.setAttribute("inputRecipeError", inputRecipeError);
            request.setAttribute("countIngredientInput", countIngredientInput);
            request.getRequestDispatcher("jsp/InputJSP.jsp").forward(request, response);

        } else
        {
            category = final_category;
        }

    }

    /**
     * Checks if the title from the InputJSP is not null or empty
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void checkTitle(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        if (request.getParameter("txt_title").isEmpty())
        {
            inputRecipeError = "Please enter a title";
            request.setAttribute("inputRecipeError", inputRecipeError);
            request.setAttribute("countIngredientInput", countIngredientInput);
            request.getRequestDispatcher("jsp/InputJSP.jsp").forward(request, response);
        } else
        {
            title = request.getParameter("txt_title");
        }
    }

    /**
     * Checks if the description from the InputJSP is not null or Empty
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void checkDescription(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        if (request.getParameter("textarea").isEmpty())
        {
            inputRecipeError = "Please enter a description";
            request.setAttribute("inputRecipeError", inputRecipeError);
            request.setAttribute("countIngredientInput", countIngredientInput);
            request.getRequestDispatcher("jsp/InputJSP.jsp").forward(request, response);
        } else
        {
            description = request.getParameter("textarea");
        }
    }

    /**
     * Checks if at least one ingredient has been entered for the new recipe
     *
     * @param request
     * @param response
     * @throws ServletException
     * @throws IOException
     */
    public void checkIngredients(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        if (li_txt_ingredient.size() == 0)
        {
            inputRecipeError = "Please enter at least one ingredient";
            request.setAttribute("inputRecipeError", inputRecipeError);
            request.setAttribute("countIngredientInput", countIngredientInput);
            request.getRequestDispatcher("jsp/InputJSP.jsp").forward(request, response);
        }
    }

    /**
     * Deletes all attributes from both the request object and the session
     * object.
     *
     * @param request
     * @param response
     */
    public void restartQuery(HttpServletRequest request, HttpServletResponse response)
    {
        Enumeration<String> enumAttributes = request.getAttributeNames();
        while (enumAttributes.hasMoreElements())
        {
            request.removeAttribute(enumAttributes.nextElement());
        }

        enumAttributes = request.getSession().getAttributeNames();
        while (enumAttributes.hasMoreElements())
        {
            request.getSession().removeAttribute(enumAttributes.nextElement());
        }

        if (li_input_ingredients != null)
        {
            li_input_ingredients.clear();
        }
        if (li_recipes != null)
        {
            li_recipes.clear();

        }

        if (li_category != null)
        {
            li_category.clear();

        }
        countIngredientInput = 1;

        if (li_txt_ingredient != null)
        {
            li_txt_ingredient.clear();

        }

        if (li_txt_menge != null)
        {
            li_txt_menge.clear();

        }

        if (li_txt_einheit != null)
        {
            li_txt_einheit.clear();

        }
        isError = false;
        
    }

    /**
     * Reads the ingredients of a recipe, the title and the description as
     * parameters from hidden textfields. Then the description is formatted
     * properly. Finally a pdf file is created on the user's desktop and opened.
     *
     * @param request
     * @param response
     */
    public void createShoppingListForRecipe(HttpServletRequest request, HttpServletResponse response)
    {
        String list = request.getParameter("ingredientsOfRecipes");
        String recipeTitle = request.getParameter("recipeTitle");
        String recipeDescription = request.getParameter("recipeDescription");
        recipeDescription = recipeDescription.replace("<br/>", "\n");
        recipeDescription = recipeDescription.replace("<b>", "");
        recipeDescription = recipeDescription.replace("</b>", "");
        recipeDescription = recipeDescription.replace("<i>", "");
        recipeDescription = recipeDescription.replace("</i>", "");
     

        try
        {
            String pdfPathFull = pdf.createPdf(pdfPath, list, recipeTitle, recipeDescription);
            Desktop.getDesktop().open(new File(pdfPathFull));
        } catch (DocumentException ex)
        {
            System.out.println(ex.getMessage());
        } catch (IOException ex)
        {
            System.out.println(ex.getMessage());
        }
    }

    /**
     * Firstly checks if the ingredient has the right format. Then calls the
     * right method for the recipe query by checking the checkbox (whether all
     * ingredients must be included or not). Afterwards deletes the ingredient
     * from the source of the ingredient drop down menu in the jsp.
     *
     * @param ingredient
     * @param request
     * @param response
     * @throws Exception
     */
    public void callRightMethodForRecipeQuery(String ingredient, HttpServletRequest request, HttpServletResponse response) throws Exception
    {
        HashMap<Recipe, LinkedList<Ingredient>> shoppingList = new HashMap<>();
        if (!li_input_ingredients.contains(ingredient) && li_input_ingredients.size() < 10 && ingredient.length() <= 25)
        {
            li_input_ingredients.add(ingredient);

            if (request.getParameter("cb_include") == null)
            {
                li_recipes = access.getRecipeForIngredientsWhereAllIngredientsAreAvailable(li_input_ingredients);

            } else
            {

                li_recipes = access.getRecipeForIngredients(li_input_ingredients);
                for (Recipe r : li_recipes)
                {
                    LinkedList<Ingredient> li_shoppingList = access.getShoppingList(r, li_input_ingredients);
                    shoppingList.put(r, li_shoppingList);
                }

            }
            request.getSession().setAttribute("shoppingList", shoppingList);
            request.getSession().setAttribute("checkbox_checked", "checked");
        }
        request.getSession().setAttribute("li_recipes", li_recipes);
        Ingredient ing = findIndexForIngredientStringAndCreateIngredientObject(ingredient);
        initializeListOfAvailableIngredients(ing, request, response);

    }

    /**
     * Searches in the list of all ingredients for the index so it can be
     * deleted from the source of the drop down list in the jsp
     *
     * @param ingredient
     * @return
     */
    public Ingredient findIndexForIngredientStringAndCreateIngredientObject(String ingredient)
    {
        int index = -1;
        for (int i = 0; i < li_all_ingredients.size(); i++)
        {
            Ingredient ing = li_all_ingredients.get(i);
            String name = ing.getName();
            if (name.equals(ingredient))
            {
                index = ing.getIngredient_id();
            }
        }

        Ingredient ing = new Ingredient(index, ingredient);
        return ing;
    }

    public void showRecipesForSpecificCategory(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {
        int cat_id = -1;

        countIngredientInput = 1;
        request.setAttribute("countIngredientInput", countIngredientInput);

        try
        {
            initializeListOfAvailableIngredients(null, request, response);
            li_category = access.getCategory();
            request.setAttribute("li_category", li_category);

        } catch (Exception ex)
        {
            System.out.println(ex.getMessage());
        }
        if (request.getParameter("param") != null)
        {
            cat_id = Integer.parseInt(request.getParameter("param"));
            if (cat_id >= 0 && cat_id <= 4)
            {
                try
                {
                    li_recipes = access.getRecipeForCategory(cat_id);
                    request.getSession().setAttribute("li_recipes", li_recipes);
                } catch (Exception ex)
                {
                    Logger.getLogger(FoodLockerServlet.class.getName()).log(Level.SEVERE, null, ex);
                }
            } else if (cat_id == 5)
            {
                li_txt_menge.clear();
                li_txt_einheit.clear();
                li_txt_ingredient.clear();
                countIngredientInput = 1;
                title = "";
                description = "";
                category = "";
                request.getRequestDispatcher("jsp/InputJSP.jsp").forward(request, response);

            } else if (cat_id == 6 )
            {
                restartQuery(request, response);
                einfuegenErfolgreich = "";
                request.setAttribute("einfuegenErfolgreich", einfuegenErfolgreich);
            }
            else if(cat_id == 7)
            {
                //restartQuery(request, response);
                einfuegenErfolgreich = "";
                request.setAttribute("einfuegenErfolgreich", einfuegenErfolgreich);
                 li_txt_menge.clear();
                li_txt_einheit.clear();
                li_txt_ingredient.clear();
                countIngredientInput = 1;
                title = ""; 
                description = ""; 
                category=""; 
                request.getRequestDispatcher("jsp/InputJSP.jsp").forward(request, response);

            }

        }
        if (cat_id != 5)
        {
            // da failt no was wenn ma as fenster neu öffnet
            //li_input_ingredients.clear();
            request.getRequestDispatcher("jsp/MainJSP.jsp").forward(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {
        request.setAttribute("isError", isError);
        einfuegenErfolgreich = "";
        request.setAttribute("einfuegenErfolgreich", einfuegenErfolgreich);
        try
        {
            access = DB_Access.getInstance();
        } catch (Exception ex)
        {
            Logger.getLogger(FoodLockerServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        showRecipesForSpecificCategory(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException
    {

        try
        {
            access = DB_Access.getInstance();
        } catch (Exception ex)
        {
            Logger.getLogger(FoodLockerServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
        if (request.getParameter("bt_newInsertNewIngredient") != null)
        {

            try
            {
                checkNewIngredientInput(request, response);
            } catch (Exception ex)
            {
                Logger.getLogger(FoodLockerServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            try
            {
                saveInput(request, response);
            } catch (Exception ex)
            {
                Logger.getLogger(FoodLockerServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            Ingredient ing = findIndexForIngredientStringAndCreateIngredientObject(request.getParameter("txt_ingredient0"));
            try
            {
                initializeListOfAvailableIngredients(ing, request, response);
            } catch (Exception ex)
            {
                Logger.getLogger(FoodLockerServlet.class.getName()).log(Level.SEVERE, null, ex);
            }
            request.getRequestDispatcher("jsp/InputJSP.jsp").forward(request, response);

        } else if (request.getParameter("bt_submitRecipe") != null)
        {
            try
            {
                checkCategory(request, response);
                checkTitle(request, response);
                checkDescription(request, response);
                checkIngredients(request, response);

                access.createSqlStringForOwnRecipeInsert(title, description, category, li_txt_menge, li_txt_einheit, li_txt_ingredient);
                restartQuery(request, response);
                initializeListOfAvailableIngredients(null, request, response);
                einfuegenErfolgreich = "Your recipe is now available";
                request.setAttribute("einfuegenErfolgreich", einfuegenErfolgreich);
                request.getRequestDispatcher("jsp/MainJSP.jsp").forward(request, response);
            } catch (Exception ex)
            {
                System.out.println(ex.toString());

            }

        }

        if (request.getParameter("txt_ingredient") != null)
        {
            String ingredient = request.getParameter("txt_ingredient");

            try
            {
                if (ingredient.equals("omnomnom"))
                {
                    li_recipes = access.getEasterEggRecipes();
                    request.getSession().setAttribute("li_recipes", li_recipes);
                    request.setAttribute("surprise", "surprise");
                } else if (access.isIngredientAvailable(ingredient))
                {
                    callRightMethodForRecipeQuery(ingredient, request, response);
                } else
                {
                    request.setAttribute("error", "The ingredient you want to add is not available. We're sorry! :(");
                }

                request.getSession().setAttribute("li_input_ingredients", li_input_ingredients);
                request.getRequestDispatcher("jsp/MainJSP.jsp").forward(request, response);
            } catch (Exception ex)
            {
                System.out.println(ex.getMessage());
            }
        }
        if (request.getParameter("ingredientsOfRecipes") != null && !request.getParameter("ingredientsOfRecipes").isEmpty())
        {
            createShoppingListForRecipe(request, response);
            request.getSession().setAttribute("li_input_ingredients", li_input_ingredients);
            request.getRequestDispatcher("jsp/MainJSP.jsp").forward(request, response);
        }

    }

    @Override
    public String getServletInfo()
    {
        return "Short description";
    }// </editor-fold>

}
