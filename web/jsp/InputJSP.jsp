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
                    <li><a href="FoodLockerServlet">Back</a></li> 

                </ul>
            </div>
        </nav>
        
        <% 
        int anzZutaten = (Integer)request.getAttribute("countIngredientInput"); 
        int count = 0; 
        int idCount = -1; 
        String title = ""; 
        String description = ""; 
        
       
        
        if(request.getParameter("txt_title")!= null) 
        { 
           title = request.getParameter("txt_title");
        }
        if(request.getParameter("textarea")!= null) 
        { 
           description = request.getParameter("textarea");
        }
        
       %> 
        
        
    <center>
        <form action="FoodLockerServlet" method="POST">
         <h1>Insert your own Recipe</h1><br/>
        
         <div class="input-field col s6" style="width: 25%">
             <input id ="txt_title" name ="txt_title" type="text" 
                    value= <%=title%>> 
                <label for="txt_title">Title</label>
        </div>
        
        <div style="width: 50%" class="row">
              <div class="row">
                <div class="input-field col s12">
                  <textarea id="textarea" name ="textarea" class="materialize-textarea"><%=description%></textarea>
                  <label for="textarea">Description</label>
                </div>
                </div> 
          </div>
         
         
         
             
         <%
            
                
            while(count < anzZutaten )
            {
                String menge = "";
                String einheit = ""; 
                String new_ingredient = ""; 
        
                if(request.getParameter("txt_menge"+idCount)!= null) 
                { 
                   menge = request.getParameter("txt_menge"+idCount);
                }
                if(request.getParameter("txt_einheit"+idCount)!= null) 
                { 
                   einheit = request.getParameter("txt_einheit"+idCount);
                }
                if(request.getParameter("txt_ingredient"+idCount)!= null) 
                { 
                   new_ingredient = request.getParameter("txt_ingredient"+idCount);
                }
            
                idCount++; 
                
                
            
         %>    
         <div style="width: 50%">
         <table border="0">
              <tr>
                  
                 
                <td>
                    <div class="input-field col s6">
                    <input id ="txt_menge<%=idCount%>" name ="txt_menge<%=idCount%>" type="text" value = <%=menge%>>
                    <label for="txt_menge<%=idCount%>">Amount</label>
                    </div>
                    
                </td>
                <td>
                    <div class="input-field col s6">
                    <input id ="txt_einheit<%=idCount%>" name ="txt_einheit<%=idCount%>" type="text" value = <%=einheit%>>
                    <label for="txt_einheit<%=idCount%>">Unit</label>
                    </div>
                    
                </td>
                <td>
                    <div class="input-field col s6">
                    <input id ="txt_ingredient<%=idCount%>" name ="txt_ingredient<%=idCount%>" type="text" value = <%=new_ingredient%>>
                     <label for="txt_ingredient<%=idCount%>">Ingredient</label>
                    </div>
                    
                </td>
                 <% if(count == 0) {%>
                <td>
                    <button id ="bt_newInsertNewIngredient" name ="bt_newInsertNewIngredient" class="btn waves-effect waves-light" onclick="this.form.submit()">+</button>  
                   
                </td><%}%>
            </tr>
             
         </table>
         </div>
         <% count++; } %>
         </form>
        
         <form action="#" method="POST">
             
            <button style = "width:50% " id ="bt_submitRecipe" name ="bt_submitRecipe" class="btn waves-effect waves-light" onclick="this.form.submit()">Add recipe</button>  
 
         </form>
    </center>
        
    </body>
</html>
