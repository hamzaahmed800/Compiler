
package compilerconstruction;


public class MemberTable {
    
    public String name;
    public String type;
    String AM;
    String TM;
    public int scope;

    public MemberTable(String name, String type,String AM,String TM, int scope) {
        
        this.name = name;
        this.type = type;
        this.AM = AM;
        this.TM = TM;
        this.scope = scope;
        
    }
    
    
    
    
}
