<%-- 
    Document   : MainJSP
    Created on : 10.04.2015, 08:19:23
    Author     : Corinna
--%>

<%@page import="beans.Recipe"%>
<%@page import="java.util.LinkedList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>FoodLocker</title>

        <link type="text/css" rel="stylesheet" href="css/materialize.min.css"  media="screen,projection"/>
        <link type="text/css" rel="stylesheet" href="css/styles.css"  media="screen,projection"/>
        <link rel="stylesheet" href="//code.jquery.com/ui/1.11.4/themes/smoothness/jquery-ui.css">
        <script src="//code.jquery.com/jquery-1.10.2.js"></script>
        <script src="//code.jquery.com/ui/1.11.4/jquery-ui.js"></script>
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
    </head>


    <body style="text-align: center">
        
        <nav>
            <div class="nav-wrapper">
              <ul id="nav-mobile" class="left hide-on-med-and-down">
                <li><a href="sass.html">Sass</a></li>
                <li><a href="components.html">Components</a></li>
                <li><a href="javascript.html">JavaScript</a></li>
              </ul>
            </div>
         </nav>
        
    <center>
        <h1>Foodlocker</h1><br/>
        <span id="error">
            <%
                if (request.getAttribute("error") != null) {
            %><h3 id="h3_error"><%=request.getAttribute("error")%>
            </h3>
            <%
                }
            %>        
        </span>
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
        <div id="div_checkbox_include">
                <input type="checkbox" id="cb_include" checked />
        <label for="cb_include">Do you want to include ingredients you did not mention?</label>
        </div>
        
        <div id="div_listIngredients" >
            <table>
                <%
                    if (request.getAttribute("li_input_ingredients") != null) {
                        LinkedList<String> li_ingredients = (LinkedList<String>) request.getAttribute("li_input_ingredients");
                        for (String ingredient : li_ingredients) {
                %><tr><td id="td_ingredients"><%=ingredient%></td></tr>
                    <% }
                        }%>

            </table>
        </div>
    </form>


    <% if (request.getAttribute("li_recipes") != null) {%> <%
            LinkedList<Recipe> li_recipes = (LinkedList<Recipe>) request.getAttribute("li_recipes");
            
            LinkedList<Recipe> li_Cat0 = new LinkedList<>();
            LinkedList<Recipe> li_Cat1 = new LinkedList<>();
            LinkedList<Recipe> li_Cat2 = new LinkedList<>();
            LinkedList<Recipe> li_Cat3 = new LinkedList<>();
            LinkedList<Recipe> li_Cat4 = new LinkedList<>();
            LinkedList<Recipe> li_Cat5 = new LinkedList<>();
            
            for (Recipe r : li_recipes) 
            {
                if(r.getCategory_id()==0)
                {
                    li_Cat0.add(r);
                }
                else if(r.getCategory_id()==1)
                {
                    li_Cat1.add(r);
                }
                else if(r.getCategory_id()==2)
                {
                    li_Cat2.add(r);
                }
                else if(r.getCategory_id()==3)
                {
                    li_Cat3.add(r);
                }
                else if(r.getCategory_id()==4)
                {
                    li_Cat4.add(r);
                }
                else if(r.getCategory_id()==4)
                {
                    li_Cat4.add(r);
                }
            }
            
            if (!li_recipes.isEmpty()) 
            {
                %><div style="width: 50%">
        <br/><br/>
      
        <ul class="collapsible" data-collapsible="accordion">
            <% if(li_Cat0.size()!=0){ %>
            <li>
                <div class="collapsible-header" id="div_recipeHeader">Breakfast</div>
            </li>
            <%
                
                for (Recipe r : li_Cat0) 
                {
                    
            %>
                <li>
                <div class="collapsible-header"><%=r.getTitle()%></div>
                <div class="collapsible-body" style="text-align:left"><p><%=r.getDescription()%></p></div>
            </li>    <%}}%>
           
            <% if(li_Cat1.size()!=0){ %>
            <li>
                <div class="collapsible-header" id="div_recipeHeader" style="color: #26a69a; font-weight: bold; font-size: 20">Main Dishes</div>
            </li>
            <%
                for (Recipe r : li_Cat1) 
                {
                    
            %>
                <li>
                <div class="collapsible-header"><%=r.getTitle()%></div>
                <div class="collapsible-body" style="text-align:left"><p><%=r.getDescription()%></p></div>
            </li>    <%}}%>
            
            <% if(li_Cat2.size()!=0){ %>
            <li>
                <div class="collapsible-header" id="div_recipeHeader" style="color: #26a69a; font-weight: bold; font-size: 20">Snacks and Side Dishes</div>
            </li>
            <%
                for (Recipe r : li_Cat2) 
                {
                    
            %>
                <li>
                <div class="collapsible-header"><%=r.getTitle()%></div>
                <div class="collapsible-body" style="text-align:left"><p><%=r.getDescription()%></p></div>
            </li>    <%}}%>
            
            <% if(li_Cat3.size()!=0){ %>
            <li>
                <div class="collapsible-header" id="div_recipeHeader" style="color: #26a69a; font-weight: bold; font-size: 20">Desserts</div>
            </li>
            <%
                for (Recipe r : li_Cat3) 
                {
                    
            %>
                <li>
                <div class="collapsible-header"><%=r.getTitle()%></div>
                <div class="collapsible-body" style="text-align:left"><p><%=r.getDescription()%></p></div>
            </li>    <%}}%>
            
            <% if(li_Cat4.size()!=0){ %>
            <li>
                <div class="collapsible-header" id="div_recipeHeader" style="color: #26a69a; font-weight: bold; font-size: 20">Easter</div>
            </li>
            <%
                for (Recipe r : li_Cat4) 
                {
                    
            %>
                <li>
                <div class="collapsible-header"><%=r.getTitle()%></div>
                <div class="collapsible-body" style="text-align:left"><p><%=r.getDescription()%></p></div>
            </li>    <%}}%>
            
            <% if(li_Cat5.size()!=0){ %>
            <li>
                <div class="collapsible-header" id="div_recipeHeader" style="color: #26a69a; font-weight: bold; font-size: 20">Drinks</div>
            </li>
            <%
                for (Recipe r : li_Cat5) 
                {
                    
            %>
                <li>
                <div class="collapsible-header"><%=r.getTitle()%></div>
                <div class="collapsible-body" style="text-align:left"><p><%=r.getDescription()%></p></div>
            </li>    <%}}%>
        </ul>            
    </div><%}
        }
    %>


</center>
</body>
</html>
