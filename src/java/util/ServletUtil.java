package util;

/**
 *
 * @author Yvonne
 */
public class ServletUtil 
{
    /**
     * replaces greather than, smaller than sign,
     * AND operator and double quotes with the appropriate HTML entities
     * @param input
     * @return 
     */
    public static String filter(String input)
    {
        StringBuffer output = new StringBuffer(input.length());
        
        //<>& ersetzen
        //&lt; &gt; &amp; &quot;

        for(int i = 0; i < input.length(); i++)
        {
            switch(input.charAt(i))
            {
                case '<': output.append("&lt"); break; 
                case '>': output.append("&gt"); break; 
                case '&': output.append("&amp"); break; 
                case '"': output.append("&quot"); break; 
                default: output.append(input.charAt(i)); 
            }
        }
       
        
        return output.toString(); 
    }
}
