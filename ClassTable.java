
package compilerconstruction;

import java.util.ArrayList;

public class ClassTable {
    
    String className;
    String AM;
    ArrayList<MemberTable> memberTable = new ArrayList<>();

    public ClassTable(String className,String AM) {
        
        this.className = className;
        this.AM = AM;
    }
    public ClassTable(){
        
    }
    
}
