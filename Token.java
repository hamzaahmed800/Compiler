
package compilerconstruction;

public class Token {

    String value;
    String className;
    int lineNo;

    
    public Token()
    {
    value = "";
    className = "";
    lineNo = 0;
           
    }
    public Token(String v,String Cn,int Line)
    {
    value  = v;
    className = Cn;
     lineNo = Line;
    
    }
    
}
