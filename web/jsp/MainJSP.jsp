<%-- 
    Document   : MainJSP
    Created on : 10.04.2015, 08:19:23
    Author     : Corinna
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <title>TODO supply a title</title>
        <link type="text/css" rel="stylesheet" href="../css/materialize.min.css"  media="screen,projection"/>
        <meta name="viewport" content="width=device-width, initial-scale=1.0, maximum-scale=1.0, user-scalable=no"/>
        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body style="text-align: center">
    <center>
        <script type="text/javascript" src="https://code.jquery.com/jquery-2.1.1.min.js"></script>
        <script type="text/javascript" src="../js/materialize.min.js"></script>
        <h1>Foodlocker</h1><br/>
        <div class="input-field col s6" style="width: 25%">
            <form>
                <input id="ingredient" type="text" class="validate" onkeydown="if (event.keyCode == 13) {
                            Materialize.toast('I am a toast');
                            this.form.submit();
                            return false;
                        }">
                <label for="ingredient">Ingredient</label>
        </div>
        <input type="checkbox" id="include" />
        <label for="include">Do you want to include ingredients you did not mention?</label>
        <div style="float:right; margin-right: 10%; margin-top: -7%" id="listIngredients" >
            <table>
            <tr><td style="background-color: #000000; color: #FFFFFF; border-bottom:  10px solid #FFFFFF;
border-top:  10px solid #FFFFFF; border-radius: 30%">herbert</td></tr>
            <tr><td style="background-color: #000000; color: #FFFFFF; border-bottom:  10px solid #FFFFFF;
border-top:  10px solid #FFFFFF; border-radius: 30%; ">herbert</td></tr>
            <tr><td style="background-color: #000000; color: #FFFFFF; border-bottom:  10px solid #FFFFFF;
border-top:  10px solid #FFFFFF; border-radius: 30%; ">herbert</td></tr>
            <tr><td style="background-color: #000000; color: #FFFFFF; border-bottom:  10px solid #FFFFFF;
border-top:  10px solid #FFFFFF; border-radius: 30%; ">herbert</td></tr>
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
