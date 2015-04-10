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
        <div class="input-field col s6" style="width: 30%">
            <form>
                <a class="waves-effect waves-light btn">Stuff</a>
                <a class="waves-effect waves-light btn"><i class="mdi-file-cloud left"></i>button</a>
                <a class="waves-effect waves-light btn"><i class="mdi-file-cloud right"></i>button</a>
                <a class="btn-floating btn-large waves-effect waves-light red"><i class="mdi-content-add"></i></a>
                <input id="ingredient" type="text" class="validate">
                <label for="ingredient">Ingredient</label>
                <a class="btn-floating btn-large waves-effect waves-light red" onclick="Materialize.toast('I am a toast')"><i class="mdi-content-add"></i></a>
            </form>
        </div>
        <div style="width: 50%">
            <input type="checkbox" id="include" />
            <label for="include">Do you want to include ingredients you did not mention?</label>
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
