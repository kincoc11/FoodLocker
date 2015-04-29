<%-- 
    Document   : MainJSP
    Created on : 10.04.2015, 08:19:23
    Author     : Corinna
--%>

<%@page import="java.util.LinkedList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>TODO supply a title</title>
        
        <link type="text/css" rel="stylesheet" href="css/materialize.min.css"  media="screen,projection"/>
        <link type="text/css" rel="stylesheet" href="css/styles.css"  media="screen,projection"/>
        <script type="text/javascript" src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
        <script type="text/javascript" src="js/materialize.min.js"></script>
        <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body style="text-align: center">
    <center>
        <h1>Foodlocker</h1><br/>
        <div class="input-field col s6" style="width: 25%">
            <form action="FoodLockerServlet" method="POST">
                <input name ="txt_ingredient" type="text" onkeydown="
                        if (event.keyCode === 13)
                        {
                            this.form.submit();
                            return false;
                        }">
                <label for="txt_ingredient">Ingredient</label>
        </div>
        <input type="checkbox" id="cb_include" />
        <label for="cb_include">Do you want to include ingredients you did not mention?</label>
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
    <div style="width: 50%">
        <br/><br/>
        <ul class="collapsible" data-collapsible="accordion">
            <li>
                <div class="collapsible-header">First</div>
                <div class="collapsible-body"><p>Lorem ipsum dolor sit amet.</p></div>
            </li>
            <li>
                <div class="collapsible-header"></i>Second</div>
                <div class="collapsible-body"><p>Lorem ipsum dolor sit amet.</p></div>
            </li>
            <li>
                <div class="collapsible-header"></i>Third</div>
                <div class="collapsible-body"><p>Lorem ipsum dolor sit amet.</p></div>
            </li>
        </ul>
    </div>
</center>
</body>
</html>
