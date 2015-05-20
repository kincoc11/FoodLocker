package beans;

import java.util.Objects;
import java.util.logging.Logger;

public class Recipe 
{
    private int recipe_id;
    private String description; 
    private String title; 

    public Recipe(int recipe_id, String description, String title) {
        this.recipe_id = recipe_id;
        this.description = description;
        this.title = title;
    }

    public int getRecipe_id() {
        return recipe_id;
    }

    public void setRecipe_id(int recipe_id) {
        this.recipe_id = recipe_id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Recipe other = (Recipe) obj;
        if (this.recipe_id != other.recipe_id) {
            return false;
        }
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        if (!Objects.equals(this.title, other.title)) {
            return false;
        }
        return true;
    }

    
    
    @Override
    public String toString() {
        return "Recipe{" + "recipe_id=" + recipe_id + ", description=" + description + ", title=" + title + '}';
    }
    
    
    
    public String toHTMLString()
    {
        return 
            
            "<li>"
            +    "<div class='collapsible-header'>"+title+"</div>"
            +    "<div class='collapsible-body'><p>"+description+"</p></div>"
            +"</li>";
    }
    
    
}
