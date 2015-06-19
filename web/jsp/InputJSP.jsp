<%-- 
    Document   : InputJSP
    Created on : 19.06.2015, 08:44:36
    Author     : Yvonne
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
        <title>Insert Recipe</title>
        
        
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
        
    </head>
    <body style="text-align: center">
        
        
        <nav id="nav_style">
            <div class="nav-wrapper">
                <ul id="nav-mobile" class="right hide-on-med-and-down">
                    <li><a href="FoodLockerServlet">Zurück</a></li> 

                </ul>
            </div>
        </nav>
        
    <center>
         <h1>Insert your own Recipe</h1><br/>
        
         <div class="input-field col s6" style="width: 25%">
                <input id ="txt_title" name ="txt_title" type="text">
                <label for="txt_title">Title</label>
        </div>
         
         
         <table border="0">
            <tr>
                <td>
                    <div class="input-field col s6" style="width: 25%">
                    <input id ="txt_menge" name ="txt_menge" type="text">
                    <label for="txt_title">Amount</label>
                    </div>
                    
                </td>
                <td>
                    <div class="input-field col s6" style="width: 25%">
                    <input id ="txt_ingredient" name ="txt_ingredient" type="text">
                     <label for="txt_ingredient">Ingredient</label>
                    </div>
                    
                </td>
                <td>
                    <form action="#" method="POST">
                        <button class="btn waves-effect waves-light" style="margin-left: 27px;" onclick="this.form.submit()">+</button>  
                    </form>
                </td>
            </tr>
             
         </table>

         
        
    </center>
        
    </body>
</html>
