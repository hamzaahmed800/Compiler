
package compilerconstruction;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

/**
 *
 * @author HAMI
 */
public class LexicalAnalyzer {
    
    
    HashMap<String, String> Keywords = new HashMap<>();
    HashMap<String, String> Punctuators = new HashMap<>();
    HashMap<String, String> Operators = new HashMap<>();
    ArrayList<String> invalid = new ArrayList<>();
    ArrayList<Token> token = new ArrayList<>();
    //Token token[];
    static int flag = 0;
    static int strFlag = 0;
    static int stMon = 0;
    static int mcomFlag = 0;
    static int dCount = 0;

    //initializing invalid characters 
    public void initinvalid() {
        invalid.add("$");
        invalid.add("^");
        invalid.add("/");
        invalid.add(";");
        
        
     }
    //initalizing punctuators
    public void initpunctuators() {

        Punctuators.put("!", "terminator");
        Punctuators.put("[", "codeOpen");
        Punctuators.put("]", "codeClose");
        Punctuators.put("(", "conditionOpen");
        Punctuators.put(")", "conditionClose");
        Punctuators.put(".", "classSeperator");
        Punctuators.put(",", "paraSaperator");
        Punctuators.put(":", "loopCase");
        Punctuators.put("{", "arrOp");
        Punctuators.put("}", "arrCl");
        Punctuators.put("to", "loopPunc");
        
    }
    
    //intialzing keywords
    public void initkeyword() {
        Keywords.put("start", "start");
        Keywords.put("public", "public");
        Keywords.put("program", "program");
        Keywords.put("finish", "finish");
        Keywords.put("static", "static");
        Keywords.put("common", "access_mod");
        Keywords.put("secrete", "access_mod");
        Keywords.put("letter","datatype");
        Keywords.put("num", "datatype");
        Keywords.put("word", "datatype");
        Keywords.put("deci","datatype");
        Keywords.put("return", "return");
        Keywords.put("iterate", "iterate");
        Keywords.put("repeat", "repeat");
        Keywords.put("check", "check");
        Keywords.put("then", "then");
        Keywords.put("switch", "switch");
        Keywords.put("case", "case");
        Keywords.put("default", "default");
        Keywords.put("break", "break");
        Keywords.put("continue", "continue");
        Keywords.put("void", "void");
        Keywords.put("class", "class");
        Keywords.put("make", "make");
        Keywords.put("array", "array");
        Keywords.put("constructor", "constructor");
    
    }
    
    //initializing operators with their respect class name
    public void initoperators() {
        Operators.put("+", "addSub");
        Operators.put("-", "addSub");
        Operators.put("*", "mulDivMod");
        Operators.put("/", "mulDivMod");
        Operators.put("%", "mulDivMod");
        Operators.put("==", "relationalOp");
        Operators.put("?=", "relationalOp");
        Operators.put("<=", "relationalOp");
        Operators.put(">=", "relationalOp");
        Operators.put("<", "relationalOp");
        Operators.put(">", "relationalOp");
        
        Operators.put("?", "notOp");
        Operators.put("&&", "andOp");
        Operators.put("||", "orOp");
        Operators.put("++", "incrementOp");
        Operators.put("--", "decrementOp");
        Operators.put("+=", "assignmentOp");
        Operators.put("-=", "assignmentOp");
        Operators.put("*=", "assignmentOp");
        Operators.put("/=", "assignmentOp");
        Operators.put("%=", "assignmentOp");
        Operators.put("=", "equalOp");
     //   Operators.put("|", "arrayBracket");
    //    Operators.put(".","dotOP");
        
    }
    
    //reding text file
    public String readFile(String inputfile) {
        try {
            String code = "";
            Scanner myRead = new Scanner(new File(inputfile));
            while (myRead.hasNext()) {
                code += myRead.nextLine();
                code += "\n";

            }

            return code + "";
        } catch (Exception e) {
            System.out.println("Exception" +e);
        }
        return "";
    }   //end of reading text file method
    
    
    public boolean checkKeywords(String word) {     //keywords checking 
        if (Keywords.containsKey(word)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkPunctuators(String word) { //punctuator checking
        if (Punctuators.containsKey(word)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkOperators(String word) {        //operators checking
        if (Operators.containsKey(word) ) {
            return true;
        } else {
            return false;
        }
    }

    public boolean checkMainOperators(String word) {  
        if (Operators.containsKey(word)) {
            return true;
        } else {
            return false;
        }

    }

    public static boolean checkNextOp(String Temp, String check) { //checking for next operator
        if ((check.equals("+") && Temp.equals("+")) || (check.equals("-") && Temp.equals("-")) || (check.equals("&") && Temp.equals("&")) || (check.equals("|") && Temp.equals("|")) || (check.equals("=") && Temp.equals("-"))|| (check.equals("=") && Temp.equals("?")) || (check.equals("=") && Temp.equals("+")) || (check.equals("=") && Temp.equals("-")) || (check.equals("=") && Temp.equals("*")) || (check.equals("=") && Temp.equals("/")) || (check.equals("=") && Temp.equals("%")) || (check.equals("=") && Temp.equals("=")) || (check.equals("=") && Temp.equals("<")) || (check.equals("=") && Temp.equals(">")) || (check.equals("&") && Temp.equals("&")) || (check.equals("|") && Temp.equals("|"))) {
            return true;
        } else {
            return false;
        }
    }
    
    public static boolean checkID(String Temp) {    //check for Identifier

        FiniteAutomata identiierChecker = new FiniteAutomata();
        if (identiierChecker.checkID(Temp)) {
            return true;

        } else {

            return false;
        }
    }
    
    public void creatToken(String Temp, String className, int line) { // Token creation Method
        Token tokenTemp = new Token();
        tokenTemp.value = Temp;
        tokenTemp.className = className;
        tokenTemp.lineNo = line;
        token.add(tokenTemp);
    }

    public void outPutFile() throws UnsupportedEncodingException { //Writing in File
        try {
            PrintWriter writer = new PrintWriter("C:\\Users\\HAMI\\Desktop\\CodeOutput.txt");
            for (Token t : token) {
                writer.println("( " + t.value + " ," + t.className + " , " + t.lineNo + " )");
            }
            writer.println();
            writer.println("Total Tokens = "+flag);
            
            writer.close();
        } catch (FileNotFoundException ex) {
        }
    }


   public boolean checkString(String Temp) {
        FiniteAutomata stringAutomata = new FiniteAutomata();
        if (stringAutomata.checkString(Temp)) {
            return true;
        } else {
            return false;
        }

    }

    public boolean checkCharConst(String Temp) {
        FiniteAutomata charAutomata = new FiniteAutomata();
        if (charAutomata.checkChar(Temp)) {
            return true;
        } else {
            return false;
        }

    }
    
  
    public boolean checkWordBreaker(String Temp, String check) {
        
        if (!checkOperators(check) && !checkPunctuators(check) && !check.equals("\n") && !check.equals(" ") && !checkOperators(Temp) && !checkPunctuators(Temp) && !check.equals("\'")  ) {
           return true;
        } 
        else {
            return false;
        }
    }

    public boolean checkNextNum(String Temp, String check) {
        if ((( Temp.equals("."))) && ((check.equals(".") && !Temp.equals(".")) || Character.isDigit(check.charAt(0))) && (!checkOperators(check) || checkPunctuators(check))) {
            return true;
        } else {
            return false;
        }

    }


    public boolean checkNumberCons(String Temp) {
        FiniteAutomata numberAutomata = new FiniteAutomata();
        if (numberAutomata.checkNumebr(Temp)) {
            return true;
        } else {
            return false;
        }
    }
    //float constant
    public boolean checkFloatCons(String Temp) {
        FiniteAutomata numberAutomata = new FiniteAutomata();
        if (numberAutomata.checkFloat(Temp)) {
            return true;
        }
        else {
            return false;
        }
    }
    
    public ArrayList<Token> check(String Path) {
        initkeyword();
        initpunctuators();
        initoperators();
        String myCode = readFile(Path);
        String Temp = "";
        String check = "";
        int lineNo = 1;
        int Chcheck=0,Chsize=1;
        
        for (int i = 0; i < myCode.length(); i++) {
            Temp += "" + myCode.charAt(i);
            String current = "" + myCode.charAt(i);
            try {
             String previous = "" + myCode.charAt(i - 1);
            } catch (Exception e) {
            }

            try {
                check = "" + myCode.charAt(i + 1);
            } catch (Exception e) {
            }
            if (Temp.equals("\"")) {
                strFlag = 1;
            }
            if(Temp.equals(".")){
                dCount = 1;
            }
            
            

            
            //singleLine comment
            try {
                if ((Temp.charAt(0) == '#')) {
                    {
                        if (Temp.equals("#") && check.equals("#")) {
                            continue;
                        }
                        if ((Temp.charAt(0) == '#') && (Temp.charAt(1) == '#') && (!check.equals("\n"))) {
                            continue;
                        }
                        if (check.equals("\r") || check.equals("\n")) {
                            Temp = "";
                            continue;
                        }
                    }
                }
            } catch (Exception e) {
            }
            // Multiline Comments
            try {

                String previous = "" + myCode.charAt(i - 1);

                if (current.equals("#") && check.equals("*") && strFlag != 1 && mcomFlag == 0) {
                    mcomFlag = 1;
                }
                if (mcomFlag == 1) {  // Tell that multi-comment has startted!!
                    if (previous.equals("*") && current.equals("#")) {
                        mcomFlag = 0;
                        Temp = "";
                        continue;
                    } else if (current.equals("\n")) {
                        lineNo++;
                        continue;
                    } else {
                        continue;
                    }
                }
            } catch (Exception e) {
            }
            
            
try{    // char constant checking
            if(Temp.charAt(0) == '\'' && Chcheck ==0)
            {
            Chcheck=1;
            Chsize++;
            continue;
            }
            else if(Temp.charAt(0) =='\'' && Temp.charAt(Temp.length()-1) != '\''  &&(!check.equals(" ") && !check.equals("\n")) &&((Temp.contains("\\") && Chsize != 4) || !Temp.contains("\\") && Chsize != 3 ))
            {
            Chsize++;
            continue;
            }
            
            else if (Temp.charAt(Temp.length() - 1) == '\'' && (Chsize==3 || Chsize==4) && checkCharConst(Temp)) {
                String value;
                value = Temp.substring(Temp.length()-(Temp.length()-1),Temp.length()-1);
                   
                    creatToken(value, "letterConstant", lineNo);
                    flag++;
                    Chcheck = 0;
                    Chsize=1;
                    Temp = "";
                    continue;
            }
}catch(Exception e){}
            
            
// Starting String             
            if (strFlag == 1) {
               
                
                if (Temp.charAt(Temp.length() - 1) == '\"' && stMon % 2 == 0 && checkString(Temp)) {
                     String value = "";
                   try
                   {
                   value = Temp.substring(Temp.length()-(Temp.length()-1),Temp.length()-1);
                   }catch(Exception e){}
                   creatToken(value, "wordConstant", lineNo);
                    flag++;
                    strFlag = 0;
                    Temp = "";

                } else if (check.equals("\n")) {
                    strFlag = 0;
                } else {
                    continue;
                }
                }
            
           //String Ending


            if (Temp.equals(" ")) { 
                Temp = "";
                continue;
            } else if (Temp.equals("")) {
                    continue;
            } else if (Temp.equals("\n")) {
                lineNo++;
                Temp = "";
                continue;
            }
            
            try {
                if (invalid.contains(Temp.charAt(0)) && invalid.contains(check)) {
                    continue;
                } else if (checkWordBreaker(Temp,check) && Chcheck== 0) {
                    continue;
                }
            } catch (Exception e) { }

            if (checkNextNum(Temp,check) ) {
                continue;
            } 
         
                else if (checkOperators(Temp)) {
                if (checkNextOp(Temp,check)) {
                    continue;
                }
                
                else if(checkMainOperators(Temp)) {
                   creatToken(Temp,Operators.get(Temp), lineNo);
                    flag++;
                    Temp = "";
                } else {
                    creatToken(Temp, "Invalid Lexeme", lineNo);
                    flag++;
                    Temp = "";
                }
            } else if (checkKeywords(Temp)) {
                creatToken(Temp, Keywords.get(Temp), lineNo);
                flag++;
                Temp = "";
            } else if (checkPunctuators(Temp)) {
                creatToken(Temp, Punctuators.get(Temp), lineNo);
                flag++;
                Temp = "";
            } else if (checkID(Temp)) {  //ID checking

                creatToken(Temp, "Identifiers", lineNo);
                flag++;
                Temp = "";
            } 
            
              // number constant check        
            else if (checkNumberCons(Temp)) {
             if(check.equals(".")){
                        continue;
                    }
                String previous = "" +myCode.charAt(i - 1);
                if ( Character.isDigit(check.charAt(0))) {
                    continue;
                }  
                else {
                    
                    creatToken(Temp, "numberConstant", lineNo);
                    flag++;
                    Temp = "";
                   
                }
                 
                }
             
                //float constant check
           else if (checkFloatCons(Temp)) {
                if (Temp.contains(".") && check.equals(".")) {
                    creatToken(Temp, "deciConstant", lineNo);
                    flag++;
                    Temp = "";
                    
                } else if (check.equals(".") || Character.isDigit(check.charAt(0))) {
                    
                    continue;
                    
                }
                else {
                    creatToken(Temp, "deciConstant", lineNo);
                    flag++;
                    Temp = "";
                    
                }
               
                    
            }
             
            
                else {
                creatToken(Temp, "Invalid Lexeme", lineNo);
                flag++;
                Chsize=1;
                Chcheck=0;
                Temp = "";
            }
        }
        
                if(Chsize > 1  && Chcheck == 1 && !Temp.equals("")&& !Temp.equals(" ") && !Temp.equals("\n"))
        {
                creatToken(Temp, "Invalid Lexeme", lineNo);
                flag++;
                Temp = "";
        }

  
    return token;
    }
    
    
    
   
}
