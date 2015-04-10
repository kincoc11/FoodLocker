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
            <input id="ingredient" type="text" class="validate" >
            <label for="ingredient">Ingredient</label>
                <input type="checkbox" id="test5" />
                <label for="test5">Do you want to include ingredients you did not mention?</label>
        </div>
        <div style="float: left; margin-left: 2%">
            <ul class="collection">
                <li class="collection-item">a</li>
                <li class="collection-item">b</li>
                <li class="collection-item">c</li>
                <li class="collection-item">d</li>
            </ul>

        </div>
    </center>
</body>
</html>
