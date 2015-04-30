function displaydata(element)
{
    text = element.getElementsByTagName("div")[0].innerHTML; 
    document.getElementById("outerRecipeDiv").innerHTML = text;
}

