
package compilerconstruction;

public class FiniteAutomata {
    public int arrayID[][] = {{1, 2, 3}, {1, 2, 3}, {3, 2, 2}, {3, 3, 3}};
    public int arrayNum[][] = {{1, 1, 2}, {3, 3, 2}, {3, 3, 2}, {3, 3, 3}};
    public int arrayFloat[][] = {{1, 5, 4}, {6, 5, 2}, {6, 3, 2}, {6, 6, 3}, {6, 5, 4}, {6, 6, 3}, {6, 6, 6}};
    public int arrayST[][] = {{1,5,5,5,5,5,5},{3,2,4,2,3,5,1},{3,2,4,2,3,5,2},{5,5,5,5,5,5,5},{5,5,2,5,5,2,5},{5,5,5,5,5,5,5}};
    public int arrayCh[][] = {{1,5,5,5,5,5},{5,2,2,4,5,5},{3,5,5,5,3,5},{5,5,5,5,5,5},{5,5,5,5,5,2},{5,5,5,5,5,5}};

    public String input;
    public int state = 0;
    public static String STinput;
    static int s1 = 0, m = 0;
    static int s = 0, mCh = 0;
    boolean flag = false;

    
    //DFA for Identifier
    public boolean checkID(String ip) {
        int s = 0;
        int j = ip.length();
        while (j > 0) {
            if (state == -1) {
                break;
            }
            state = transitionID(state, ip.charAt(s));
            s++;

            j--;
        }
         if(state == 2){
            return true;
        }
        else {
            return false;
        }

    }//identifier DFA ending

    public int transitionID(int st, char ch) {
        if (Character.isLetter(ch)) {
            return arrayID[st][1];
        }
        if (Character.isDigit(ch)) {
            return arrayID[st][2];
        }
        if (ch == '@') {
            return arrayID[st][0];
        } else {
            return -1;
        }
    }
    
    //Integer constant DFA
    public boolean checkNumebr(String ip) {
        int s = 0;
        int j = ip.length();
        while (j > 0) {
            if (state == -100) {
                break;
            }
            state = transitionNum(state, ip.charAt(s));
            s++;

            j--;
        }
        if (state == 2 ) {
            return true;
        } else {
            return false;
        }

    }

    public int transitionNum(int st, char ch) {
        if (ch == '+') {
            return arrayNum[st][0];
        }
        if(ch == '-'){
            return arrayNum[st][1];
        }
        if (Character.isDigit(ch)) {
            return arrayNum[st][2];
        }
       else {
            return -100;

        }
    }//end of Int_Constant DFA
    
 //Float Constant DFA
    
    public boolean checkFloat(String ip) {
        int s = 0;
        int j = ip.length();
        while (j > 0) {
            if (state == -100) {
                break;
            }
            state = transitionFloat(state, ip.charAt(s));
            s++;

            j--;
        }
        if ( state == 3 || state == 4) {
            return true;
        } else {
            return false;
        }

    }

    public int transitionFloat(int st, char ch) {
        if (ch == '+' || ch == '-') {
            return arrayFloat[st][0];
        }
        
        if (Character.isDigit(ch)) {
            return arrayFloat[st][2];
        }
        if (ch == '.') {
            return arrayFloat[st][1];
        } else {
            return -100;

        }
    }
    
    
   //End of Float Constant DFA
    
   //String Constant DFA
   public boolean checkString(String ip) {
        STinput = ip;
        int j = ip.length();
        while (j > 0) {
                if (state == -100) {
                break;
            }
            try {
                state = transitionST(state, ip.charAt(s1));
            } catch (Exception e) {
            }
            s1++;
            j--;

        }
        if (state == 3) {
            s1 = 0;
            m = 0;
            return true;
        } else {
            s1 = 0;
        }
        m = 0;
        return false;

    }

    public int transitionST(int st, char ch) {
        if (s1 == 0) {
            if (ch == '"') {
                return arrayST[st][0];
            }
        }

        if(ch == ' ')
        {
        return arrayST[st][6];
        }
        
        if (ch == '@' || ch == '!' || ch == '#' || ch == '$' || ch == '%' || ch == '^' || ch == '&' || ch == '*' || ch == '(' || ch == ')' || ch == '_' || ch == '+' || ch == '-' || ch == '='||ch=='?'||ch=='<'|| ch=='>'
               || ch=='/'||ch==':'||ch=='.'||ch==','||ch==';'||ch==']'||ch=='['||ch=='{'||ch=='}'||ch=='|'||ch=='~'||ch=='`') {
            return arrayST[st][3];
        }
        if (m % 2 == 0) {
            if (ch == '"') {
                return arrayST[st][4];
            }
        }
        if (STinput.charAt(s1 - 1) == '\\' && m % 2 != 0) {
            if (ch == 'n' || ch == 'r' || ch == 't' || ch == '"' || ch == '\'' || ch == '\\') {
                m += 1;
            }
            return arrayST[st][5];
        }
        if (ch == '\\') {
            m += 1;
            return arrayST[st][2];
        }
        if (Character.isAlphabetic(ch) || Character.isDigit(ch)) {
            return arrayST[st][1];
        }
         else {
            return -100;
        }
    }
 //End of String Constant DFA
    
    // Char Constant DFA
    public boolean checkChar(String ip) {

        input = ip;

        int j = ip.length();
        while (j > 0) {
            if (state == -100) {
                break;
            }
            state = transitionChar(state, ip.charAt(s));
            s++;

            j--;
        }
        if (state == 3) {
            s=0;
            flag=false;
            return true;
            
        } else {
            s=0;
            flag=false;
            return false;
        }
    }

    public int transitionChar(int st, char ch) {
        if (s == 0) {
            if (ch == '\'') {
                return arrayCh[st][0];
                
            }
        }

        if (ch == '@' || ch == '!' || ch == '#' || ch == '$' || ch == '%' || ch == '^' || ch == '&' || ch == '*' || ch == '(' || ch == ')' || ch == '_' || ch == '+' || ch == '-' || ch == '='||ch=='?'||ch=='<'|| ch=='>'
               || ch=='/'||ch==':'||ch=='.'||ch==','||ch==';'||ch==']'||ch=='['||ch=='{'||ch=='}'||ch=='|'||ch=='~'||ch=='`' ||ch==' ') {
            return arrayCh[st][2];
        }

        if (ch == '\'' && flag == false) {
            return arrayCh[st][4];
        }

        if (input.charAt(s - 1) == '\\' && flag == true) {
            if (ch == 'n' || ch == 'r' || ch == 't' || ch == '"' || ch == '\'' || ch == '\\' || ch == 'b' || ch == 'f') {
                flag = false;
                return arrayCh[st][5];
            }
        }

        if (ch == '\\') {
            flag = true;
            return arrayCh[st][3];
        }

        if (Character.isAlphabetic(ch)|| Character.isDigit(ch)) {
            return arrayCh[st][1];
        }
         else {

            return -100;

        }
    }
    
    //End of Char Constant DFA
    
    
    
}
