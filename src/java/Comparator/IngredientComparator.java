/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Comparator;

import beans.Ingredient;
import java.util.Comparator;

/**
 *
 * @author Corinna
 */
public class IngredientComparator implements Comparator<Ingredient>{

    @Override
    public int compare(Ingredient o1, Ingredient o2) {
        return o1.getName().compareTo(o2.getName());
    }

  
    
}
