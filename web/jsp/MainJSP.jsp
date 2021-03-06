<%-- 
    Document   : MainJSP
    Created on : 10.04.2015, 08:19:23
    Author     : Corinna
--%>

<%@page import="java.util.HashMap"%>
<%@page import="beans.Ingredient"%>
<%@page import="beans.Ingredient"%>
<%@page import="beans.Category"%>
<%@page import="beans.Recipe"%>
<%@page import="java.util.LinkedList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <link type="text/css" rel="stylesheet" href="css/materialize.min.css"  media="screen,projection"/>
        <link type="text/css" rel="stylesheet" href="css/styles.css"  media="screen,projection"/>
        <link rel="stylesheet" href="css/jquery-ui.css">
        <script src="js/jquery-1.10.2.js"></script>
        <script src="js/jquery-ui.js"></script>
        <script type="text/javascript" src="js/materialize.min.js"></script>
        <script>

            $(function ()
            {
                var arrayIngredients = <%= this.getServletContext().getAttribute("attrIngredients")%>;

                $("#txt_ingredient").autocomplete({
                    source: arrayIngredients
                });
            });
        </script>

        <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <%
            if (request.getParameter("param") == null)
            {
        %>      <title>Food Locker</title><%
            }   
            else if (Integer.parseInt(request.getParameter("param")) == 0)
            {
        %>      <title>Breakfast</title><%
            }   
            else if (Integer.parseInt(request.getParameter("param")) == 1)
            {
        %>      <title>Main Dishes</title><%
        } 
            else if (Integer.parseInt(request.getParameter("param")) == 2)
            {
        %>      <title>Side Dishes</title><%
            } 
            else if (Integer.parseInt(request.getParameter("param")) == 3)
            {
        %>      <title>Desserts</title><%
        } 
            else if (Integer.parseInt(request.getParameter("param")) == 4)
            {
        %>      <title>Easter</title><%
            } 
            else
            {
        %>      <title>Food Locker</title><%
            }
        %>
    </head>
    <body>
        <%
            LinkedList<Category> li_category = (LinkedList) request.getAttribute("li_category");
        %>

        <nav id="nav_style">
            <div class="nav-wrapper">
                <ul id="nav-mobile" class="left hide-on-med-and-down">
                    <li><a href="FoodLockerServlet?param=0">Breakfasts</a></li> 
                    <li><a href="FoodLockerServlet?param=1">Main Dishes</a></li>
                    <li><a href="FoodLockerServlet?param=2">Side Dishes</a></li>
                    <li><a href="FoodLockerServlet?param=3">Desserts</a></li>
                    <li><a href="FoodLockerServlet?param=4">Easter</a></li>
                </ul>
                <ul id="nav-mobile" class="right hide-on-med-and-down">
                    <li><a href="FoodLockerServlet?param=5">Add Recipe</a></li>
                    <li><a href="FoodLockerServlet?param=6">Restart</a></li>
                </ul>
            </div>
        </nav>
    <center>
        <h1>Foodlocker</h1><br/>
        <div class="input-field col s6" style="width: 25%">
            <form action="FoodLockerServlet" method="POST">
                <input id ="txt_ingredient" name ="txt_ingredient" type="text" onkeydown="
                        if (event.keyCode === 13)
                        {
                            this.form.submit();
                            return false;
                        }">
                <label for="txt_ingredient">Ingredient</label>
        </div>
        <div id="div_center">
            <%          if (session.getAttribute("checkbox_checked") != null)
                        {
            %>
            <input type="checkbox" id="cb_include" name="cb_include" checked />
            <%          } 
                        else
                        {
            %>
            <input type="checkbox" id="cb_include" name="cb_include" />
            <%
                        }
            %>
            <label for="cb_include">Do you want to include ingredients you did not mention?</label>
        </div>
        <div id="div_listIngredients" >
            <table>
                <%
                    if (session.getAttribute("li_input_ingredients") != null)
                    {
                        LinkedList<String> li_ingredients = (LinkedList<String>) session.getAttribute("li_input_ingredients");
                        for (String ingredient : li_ingredients)
                        {
                %>          <tr><td id="td_ingredients"><%=ingredient%></td></tr>
                <% 
                        }
                    }%>
            </table>
        </div>
    </form>
    <div id="div_center">
        <%          if (request.getAttribute("error") != null)
                    {
        %>              <h3 id="h3_error"><%=request.getAttribute("error")%></h3>
        <%
                    }
        %>        
        <%          if (request.getAttribute("einfuegenErfolgreich") != null)
                    {
        %>              <h3 id="h3_einfuegenErfolgreich"><%=request.getAttribute("einfuegenErfolgreich")%></h3>
        <%
                    }
        %>        
    </div>
    <%              if (session.getAttribute("li_recipes") != null)
                    {
    %> 
    <%
                        if (request.getAttribute("surprise") != null)
                        {
    %>
    <h1 style="color:hotpink; font-size: 50px">Approved by Coki and Yvonne!</h1>
    <a href="FoodLockerServlet?param=6" title="Back to normal mode">
        <img src="res/surprise.jpg" height="300" width="450">
    </a>
    <%}
        LinkedList<Recipe> li_recipes = (LinkedList<Recipe>) session.getAttribute("li_recipes");
        HashMap<Recipe, LinkedList<Ingredient>> shoppingList = null;

        if (li_recipes.isEmpty())
        {
    %>      <div id="div_center">
                <h3 id="h3_error">Unfortunately no recipes were found! We're sorry!</h3>
            </div>
    <%
        } 
        else
        {
            LinkedList<Recipe> li_Cat0 = new LinkedList<>();
            LinkedList<Recipe> li_Cat1 = new LinkedList<>();
            LinkedList<Recipe> li_Cat2 = new LinkedList<>();
            LinkedList<Recipe> li_Cat3 = new LinkedList<>();
            LinkedList<Recipe> li_Cat4 = new LinkedList<>();

            for (Recipe r : li_recipes)
            {
                if (r.getCategory_id() == 0)
                {
                    li_Cat0.add(r);
                } 
                else if (r.getCategory_id() == 1)
                {
                    li_Cat1.add(r);
                } 
                else if (r.getCategory_id() == 2)
                {
                    li_Cat2.add(r);
                } 
                else if (r.getCategory_id() == 3)
                {
                    li_Cat3.add(r);
                } 
                else if (r.getCategory_id() == 4)
                {
                    li_Cat4.add(r);
                }
            }
            if (session.getAttribute("shoppingList") != null)
            {
                shoppingList = (HashMap<Recipe, LinkedList<Ingredient>>) session.getAttribute("shoppingList");
            }
            if (!li_recipes.isEmpty())
            {
    %>      <div style="width: 50%">
                <br/>
                <br/>
                <ul class="collapsible" data-collapsible="accordion">
    <% 
                if (li_Cat0.size() != 0)
                { 
    %>
                    <li>
                        <div class="collapsible-header" id="div_recipeHeader" >Breakfast</div>
                    </li>
    <%
                    for (Recipe r : li_Cat0)
                    {
    %>
                        <li>
                            <div class="collapsible-header"><%=r.getTitle()%></div>
                            <div class="collapsible-body" style="text-align:left"><p><%=r.getDescription()%><br/>
    <%
                            if (session.getAttribute("shoppingList") != null)
                            {
                                if (shoppingList.containsKey(r))
                                {
                                    LinkedList<Ingredient> ings = shoppingList.get(r);
                                    String list = "";
                                    for (Ingredient i : ings)
                                    {
                                        list += i.getName() + ";";
                                    }
                                    if (!list.equals(""))
                                    {
                        %>              <form action="FoodLockerServlet" method="POST">
                                            <button class="btn waves-effect waves-light" style="margin-left: 27px;" onclick="this.form.submit()">Print Shopping List</button>
                                            <input type="hidden" name="ingredientsOfRecipes" value="<%=list%>" />
                                            <input type="hidden" name="recipeTitle" value="<%=r.getTitle()%>" />
                                            <input type="hidden" name="recipeDescription" value="<%=r.getDescription()%>" />
                                        </form>
                                        <br/>
    <%
                                    }
                                }
    %>                      </div>
                        </li>
    <%
                            }
                    }
                }
    %>

    <% if (li_Cat1.size() != 0)
       { 
    %>
            <li>
                <div class="collapsible-header" id="div_recipeHeader">Main Dishes</div>
            </li>
            <%
                for (Recipe r : li_Cat1)
                {

            %>
            <li>
                <div class="collapsible-header"><%=r.getTitle()%></div>
                <div class="collapsible-body" style="text-align:left"><p><%=r.getDescription()%><br>
                        <%
                            if (session.getAttribute("shoppingList") != null)
                            {
                                if (shoppingList.containsKey(r))
                                {
                                    LinkedList<Ingredient> ings = shoppingList.get(r);
                                    String list = "";
                                    for (Ingredient i : ings)
                                    {
                                        list += i.getName() + ";";
                                    }
                                    if (!list.equals(""))
                                    {
    %>                                  <form action="FoodLockerServlet" method="POST">
                                            <button class="btn waves-effect waves-light" style="margin-left: 27px;" onclick="this.form.submit()">Print Shopping List</button>
                                            <input type="hidden" name="ingredientsOfRecipes" value="<%=list%>" />
                                            <input type="hidden" name="recipeTitle" value="<%=r.getTitle()%>" />
                                            <input type="hidden" name="recipeDescription" value="<%=r.getDescription()%>" />
                                        </form>
                                        <br/>
    <%
                                    }
                                }
    %>
                </div>
            </li>    
    <%                      }
                    }
        }
    %>

    <%         if (li_Cat2.size() != 0)
               { 
    %>
                    <li>
                        <div class="collapsible-header" id="div_recipeHeader" >Snacks and Side Dishes</div>
                    </li>
    <%
                    for (Recipe r : li_Cat2)
                    {
    %>
                        <li>
                            <div class="collapsible-header"><%=r.getTitle()%></div>
                            <div class="collapsible-body" style="text-align:left"><p><%=r.getDescription()%><br>
    <%
                            if (session.getAttribute("shoppingList") != null)
                            {
                                if (shoppingList.containsKey(r))
                                {
                                    LinkedList<Ingredient> ings = shoppingList.get(r);
                                    String list = "";
                                    for (Ingredient i : ings)
                                    {
                                        list += i.getName() + ";";
                                    }
                                    if (!list.equals(""))
                                    {
    %>                                  <form action="FoodLockerServlet" method="POST">
                                            <button class="btn waves-effect waves-light" style="margin-left: 27px;" onclick="this.form.submit()">Print Shopping List</button>
                                            <input type="hidden" name="ingredientsOfRecipes" value="<%=list%>" />
                                            <input type="hidden" name="recipeTitle" value="<%=r.getTitle()%>" />
                                            <input type="hidden" name="recipeDescription" value="<%=r.getDescription()%>" />
                                        </form>
                                        <br/>
    <%
                                    }
                            }
                        }
    %>
                            </div></li>    
    <%
                    }
                }
    %>

    <%          if (li_Cat3.size() != 0)
                { 
    %>
                    <li>
                        <div class="collapsible-header" id="div_recipeHeader" >Desserts</div>
                    </li>
    <%
                    for (Recipe r : li_Cat3)
                    {
    %>
                        <li>
                            <div class="collapsible-header"><%=r.getTitle()%></div>
                            <div class="collapsible-body" style="text-align:left"><p><%=r.getDescription()%><br>
    <%
                            if (session.getAttribute("shoppingList") != null)
                            {
                                if (shoppingList.containsKey(r))
                                {
                                    LinkedList<Ingredient> ings = shoppingList.get(r);
                                    String list = "";
                                    for (Ingredient i : ings)
                                    {
                                        list += i.getName() + ";";
                                    }
                                    if (!list.equals(""))
                                    {
    %>                                  <form action="FoodLockerServlet" method="POST">
                                            <button class="btn waves-effect waves-light" style="margin-left: 27px;" onclick="this.form.submit()">Print Shopping List</button>
                                            <input type="hidden" name="ingredientsOfRecipes" value="<%=list%>" />
                                            <input type="hidden" name="recipeTitle" value="<%=r.getTitle()%>" />
                                            <input type="hidden" name="recipeDescription" value="<%=r.getDescription()%>" />
                                        </form>
                                        <br/>
    <%
                                    }
                                }
    %>
                            </div></li>    
    <%
                            }
                    }
                }
    %>

    <%          if (li_Cat4.size() != 0)
                {
    %>
                    <li>
                        <div class="collapsible-header" id="div_recipeHeader" >Easter</div>
                    </li>
    <%
                    for (Recipe r : li_Cat4)
                    {
    %>
                        <li>
                            <div class="collapsible-header"><%=r.getTitle()%></div>
                            <div class="collapsible-body" style="text-align:left"><p><%=r.getDescription()%><br>
    <%
                            if (session.getAttribute("shoppingList") != null)
                            {
                                if (shoppingList.containsKey(r))
                                {
                                    LinkedList<Ingredient> ings = shoppingList.get(r);
                                    String list = "";
                                    for (Ingredient i : ings)
                                    {
                                        list += i.getName() + ";";
                                    }
                                    if (!list.equals(""))
                                    {
    %>                                  <form action="FoodLockerServlet" method="POST">
                                        <button class="btn waves-effect waves-light" style="margin-left: 27px;" onclick="this.form.submit()">Print Shopping List</button>
                                        <input type="hidden" name="ingredientsOfRecipes" value="<%=list%>" />
                                        <input type="hidden" name="recipeTitle" value="<%=r.getTitle()%>" />
                                        <input type="hidden" name="recipeDescription" value="<%=r.getDescription()%>" />
                                        </form>
                                        <br/>
    <%                              }
                                }
    %>
                            </div></li>    
    <%
                            }
                    }
                }
    %>


        </ul>            
    </div>
    <%      }
           }
        }
        if (request.getAttribute("surprise") != null)
        {
    %>
            <audio autoplay="true" src="res/surprise.mp3">
    <%
        }
    %>
</center>
</body>
</html>
