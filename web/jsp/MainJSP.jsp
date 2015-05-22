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
            if (!li_recipes.isEmpty()) 
            {
                %><div style="width: 50%">
        <br/><br/><ul class="collapsible" data-collapsible="accordion"><%
                for (Recipe r : li_recipes) {%>
            <li><div class="collapsible-header"><%=r.getTitle()%></div>
                <div class="collapsible-body" style="text-align:left"><p><%=r.getDescription()%></p></div></li>
                        <%}%>
        </ul>            
    </div><%}
        }
    %>


</center>
</body>
</html>
