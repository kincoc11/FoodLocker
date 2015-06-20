package beans;

import java.io.Serializable;
import java.util.Objects;

/**
 * @author Yvonne
 */
public class Ingredient implements Serializable 
{
    private int ingredient_id;
    private String name; 

    /**
     * @param ingredient_id
     * @param name 
     */
    public Ingredient(int ingredient_id, String name) {
        this.ingredient_id = ingredient_id;
        this.name = name;
    }

    /**
     * @return 
     */
    public int getIngredient_id() {
        return ingredient_id;
    }

    /**
     * @param ingredient_id 
     */
    public void setIngredient_id(int ingredient_id) {
        this.ingredient_id = ingredient_id;
    }

    /**
     * @return 
     */
    public String getName() {
        return name;
    }

    /**
     * @param name 
     */
    public void setName(String name) {
        this.name = name;
    }   
    
    /**
     * @return 
     */
    @Override
    public int hashCode() {
        int hash = 3;
        return hash;
    }

    /**
     * @param obj
     * @return 
     */
    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final Ingredient other = (Ingredient) obj;
        if (this.ingredient_id != other.ingredient_id) {
            return false;
        }
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }

    
    /**
     * @return 
     */
    @Override
    public String toString() {
        return "Ingredient{" + "ingredient_id=" + ingredient_id + ", name=" + name + '}';
    }
    
    
}
