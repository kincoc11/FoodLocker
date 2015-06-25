package beans;

import java.io.Serializable;
import java.util.Objects;
import java.util.logging.Logger;

/**
 *
 * @author Yvonne
 */
public class Recipe implements Serializable
{

    private int recipe_id;
    private String description;
    private String title;
    private int category_id;

    /**
     * @param recipe_id
     * @param description
     * @param title
     * @param category_id
     */
    public Recipe(int recipe_id, String description, String title, int category_id)
    {
        this.recipe_id = recipe_id;
        this.description = description;
        this.title = title;
        this.category_id = category_id;
    }

    /**
     * @return
     */
    public int getCategory_id()
    {
        return category_id;
    }

    /**
     * @param category_id
     */
    public void setCategory_id(int category_id)
    {
        this.category_id = category_id;
    }

    /**
     * @return
     */
    public int getRecipe_id()
    {
        return recipe_id;
    }

    /**
     * @param recipe_id
     */
    public void setRecipe_id(int recipe_id)
    {
        this.recipe_id = recipe_id;
    }

    /**
     * @return
     */
    public String getDescription()
    {
        return description;
    }

    /**
     * @param description
     */
    public void setDescription(String description)
    {
        this.description = description;
    }

    /**
     * @return
     */
    public String getTitle()
    {
        return title;
    }

    /**
     * @param title
     */
    public void setTitle(String title)
    {
        this.title = title;
    }

    /**
     * @return
     */
    @Override
    public int hashCode()
    {
        int hash = 7;
        return hash;
    }

    /**
     * @param obj
     * @return
     */
    @Override
    public boolean equals(Object obj)
    {
        if (obj == null)
        {
            return false;
        }
        if (getClass() != obj.getClass())
        {
            return false;
        }
        final Recipe other = (Recipe) obj;
        if (this.recipe_id != other.recipe_id)
        {
            return false;
        }
        if (!Objects.equals(this.description, other.description))
        {
            return false;
        }
        if (!Objects.equals(this.title, other.title))
        {
            return false;
        }
        if (this.category_id != other.category_id)
        {
            return false;
        }
        return true;
    }

    /**
     * @return
     */
    @Override
    public String toString()
    {
        return "Recipe{" + "recipe_id=" + recipe_id + ", description=" + description + ", title=" + title + '}';
    }

    /**
     * @return
     */
    public String toHTMLString()
    {
        return "<li>"
                + "<div class='collapsible-header'>" + title + "</div>"
                + "<div class='collapsible-body'><p>" + description + "</p></div>"
                + "</li>";
    }

}
