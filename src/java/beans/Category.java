package beans;

import java.io.Serializable;
import java.util.Objects;

/**
 *
 * @author Yvonne
 */
public class Category implements Serializable
{

    String cat_name;
    int cat_id;

    /**
     *
     * @param cat_name
     * @param cat_id
     */
    public Category(String cat_name, int cat_id)
    {
        this.cat_name = cat_name;
        this.cat_id = cat_id;
    }

    /**
     * 
     * @return 
     */
    public String getCat_name()
    {
        return cat_name;
    }

    /**
     * 
     * @param cat_name 
     */
    public void setCat_name(String cat_name)
    {
        this.cat_name = cat_name;
    }

    /**
     * 
     * @return 
     */
    public int getCat_id()
    {
        return cat_id;
    }

    /**
     * 
     * @param cat_id 
     */
    public void setCat_id(int cat_id)
    {
        this.cat_id = cat_id;
    }

    /**
     * 
     * @return 
     */
    @Override
    public int hashCode()
    {
        int hash = 7;
        return hash;
    }

    /**
     * 
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
        final Category other = (Category) obj;
        if (!Objects.equals(this.cat_name, other.cat_name))
        {
            return false;
        }
        if (this.cat_id != other.cat_id)
        {
            return false;
        }
        return true;
    }

}
