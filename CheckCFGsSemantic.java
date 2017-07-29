package compilerconstruction;

import java.util.*;

public class CheckCFGsSemantic {

    private static int index;
    ArrayList<Token> allTokens = new ArrayList<Token>();
    ArrayList<ClassTable> classTable = new ArrayList<ClassTable>();
    ArrayList<Integer> currentScopeStack = new ArrayList<>();
    Integer currentScope = 0;

    String Errors = "";
    String className = "";
    Integer classScope = 0;
    String cAM = "";
    String cTM = "";
    String cType = "";
    String cType1 = "";
    String cName = "";

    String variableName = "";
    String varName = "";
    String argName = "";
    String variableType = "";
    String AM = "";
    String TM = "";

    Integer variableScope = 0;
    String type;
    String fName = "";
    String fType = "";
    Integer fScope = 0;

    String t1 = "";
    String t2 = "";
    String t3 = "";
    String t4 = "";
    String op = "";
    String ope = "";
    String eType = "";
    String eType1 = "";
    String eType2 = "";

    public CheckCFGsSemantic(ArrayList<Token> inputToken) {
        index = 0;
        Token addLast = new Token("$", "$", 0);
        inputToken.add(addLast);
        allTokens = inputToken;
    }

    public String startCheck() {
        if (class_defs()) {
            return Errors + "" + ("");

        } else {
            return Errors + "" + ("Error At Line no : " + allTokens.get(index).lineNo);
        }

    }

    public boolean class_defs() {
        if (allTokens.get(index).className.equals("class") || allTokens.get(index).className.equals("access_mod") || allTokens.get(index).className.equals("public")) {
            if (allTokens.get(index).className.equals("public")) {
                cAM = allTokens.get(index).value;
                index++;
                if (allTokens.get(index).className.equals("class")) {
                    index++;

                    this.className = allTokens.get(index).value;

                    if (allTokens.get(index).className.equals("Identifiers")) {

                        if (lookUpClass(this.className).equals("")) {
                            // not declared
                            insertClass(this.className, cAM);
                        } else {
                            // declared
                            System.out.println("'" + this.className + "' Class Redeclared error at = " + allTokens.get(index).lineNo);
                            // return false;
                        }

                        index++;

                        if (allTokens.get(index).className.equals("codeOpen")) {
                            index++;
                            this.currentScope++;
                            pushCurrentScopeStack();
                            this.classScope = this.currentScope;
                            if (class_body()) {
                                if (start_finish()) {
                                    if (class_body()) {
                                        if (allTokens.get(index).className.equals("codeClose")) {
                                            index++;
                                            popCurrentScopeStack();
                                            if (class_defs()) {
                                                return true;
                                            }
                                        }

                                    }
                                }
                            }
                        }

                    }
                }
            } else if (allTokens.get(index).className.equals("access_mod")) {
                cAM = allTokens.get(index).value;
                index++;
                if (allTokens.get(index).className.equals("class")) {
                    index++;

                    this.className = allTokens.get(index).value;

                    if (allTokens.get(index).className.equals("Identifiers")) {

                        if (lookUpClass(this.className).equals("")) {
                            // not declared
                            insertClass(this.className, cAM);
                        } else {
                            // declared
                            System.out.println("'" + this.className + "' Class Redeclared error at = " + allTokens.get(index).lineNo);
                            //  return;
                        }

                        index++;
                        if (allTokens.get(index).className.equals("codeOpen")) {
                            index++;
                            this.currentScope++;
                            pushCurrentScopeStack();
                            this.classScope = this.currentScope;
                            if (class_body()) {
                                if (allTokens.get(index).className.equals("codeClose")) {
                                    index++;
                                    popCurrentScopeStack();

                                    if (class_defs()) {
                                        return true;
                                    }
                                }

                            }
                        }

                    }
                }

            } else if (allTokens.get(index).className.equals("class")) {
                index++;

                this.className = allTokens.get(index).value;

                if (allTokens.get(index).className.equals("Identifiers")) {

                    if (lookUpClass(this.className).equals("")) {
                        // not declared
                        insertClass(this.className, cAM);
                    } else {
                        // declared
                        System.out.println("'" + this.className + "' Class Redeclared error at = " + allTokens.get(index).lineNo);
                        //  return;
                    }

                    index++;
                    if (allTokens.get(index).className.equals("codeOpen")) {
                        index++;
                        this.currentScope++;
                        pushCurrentScopeStack();
                        this.classScope = this.currentScope;
                        if (class_body()) {
                            if (allTokens.get(index).className.equals("codeClose")) {
                                index++;
                                popCurrentScopeStack();

                                if (class_defs()) {
                                    return true;
                                }
                            }

                        }
                    }

                }
            }
        }
        if (allTokens.get(index).className.equals("$")) {
            return true;
        }
        return false;

    }

    public boolean start_finish() {
        if (allTokens.get(index).className.equals("static")) {
            if (allTokens.get(index).className.equals("static")) {
                index++;

                if (allTokens.get(index).className.equals("void")) {
                    index++;
                    if (allTokens.get(index).className.equals("start")) {
                        index++;
                        if (allTokens.get(index).className.equals("conditionOpen")) {
                            index++;
                            if (allTokens.get(index).className.equals("datatype"))//check it
                            {
                                index++;
                                if (allTokens.get(index).className.equals("arrOp")) {
                                    index++;
                                    if (allTokens.get(index).className.equals("arrCl")) {
                                        index++;
                                        if (allTokens.get(index).className.equals("Identifiers")) {
                                            index++;
                                            if (allTokens.get(index).className.equals("conditionClose")) {
                                                index++;
                                                if (allTokens.get(index).className.equals("codeOpen")) {
                                                    index++;
                                                    this.currentScope++;
                                                    pushCurrentScopeStack();

                                                    //if()
                                                    if (body()) {
                                                        if (allTokens.get(index).className.equals("codeClose")) {
                                                            index++;
                                                            popCurrentScopeStack();

                                                            if (allTokens.get(index).className.equals("finish")) {
                                                                index++;
                                                                return true;
                                                            }

                                                        }

                                                    }

                                                }
                                            }
                                        }

                                    }
                                }

                            }

                        }

                    }

                }

            }

        }
        return false;
    }

    //Starting of class bodies
    public boolean class_body() {
        if (allTokens.get(index).className.equals("access_mod") || allTokens.get(index).className.equals("datatype") || allTokens.get(index).className.equals("Identifiers") || allTokens.get(index).className.equals("constructor")) {
            if (allTokens.get(index).className.equals("access_mod")) {
                this.cAM = allTokens.get(index).value;
                index++;
                if (class_body1()) {//
                    if (class_body()) {
                        return true;
                    }
                }

            } else if (allTokens.get(index).className.equals("datatype")) {
                this.cType = allTokens.get(index).value;
                index++;
                if (allTokens.get(index).className.equals("Identifiers")) {
                    this.cName = allTokens.get(index).value;
                    this.cAM = "";
                    this.cTM = "";
                    //   this.classScope = this.currentScope;
                    index++;
                    if (class_body2()) {
                        if (class_body()) {
                            return true;
                        }
                    }

                }
            } else if (allTokens.get(index).className.equals("Identifiers")) {
                this.cType = allTokens.get(index).value;
                //  this.cName = allTokens.get(index).value;

                type = lookUpClass(cType);
                if (type.equals("")) {
                    System.out.println("'" + this.cType + "' Class Not Found at Line Number = " + allTokens.get(index).lineNo);

                }
                index++;
                if (allTokens.get(index).className.equals("Identifiers")) {

                    this.cName = allTokens.get(index).value;
                    cAM = "";
                    cTM = "";

                    index++;
                    if (class_body3()) {
                        if (class_body()) {
                            return true;
                        }
                    }
                }

            } else if (allTokens.get(index).className.equals("constructor")) {
                index++;
                if (allTokens.get(index).className.equals("Identifiers")) {
                    this.cName = allTokens.get(index).value;
                    index++;
                    if (!this.cName.equals(className)) {
                        System.out.println("'" + this.cName + "' Class Not Found at Line Number = " + allTokens.get(index).lineNo);
                    }
                    cType = "";
                    cAM = "";
                    cTM = "";
                    if (params()) {//
                        if (allTokens.get(index).className.equals("codeOpen")) {
                            index++;
                            if (body()) {
                                if (allTokens.get(index).className.equals("codeClose")) {
                                    index++;

                                    popCurrentScopeStack();
                                    if (class_body()) {
                                        return true;
                                    }

                                }
                            }

                        }

                    }
                }

            }

        } else if (allTokens.get(index).className.equals("codeClose") || allTokens.get(index).className.equals("static")) {
            return true;
        }

        return false;
    }

    public boolean class_body1() {
        if (allTokens.get(index).className.equals("static") || allTokens.get(index).className.equals("datatype") || allTokens.get(index).className.equals("void")) {
            if (allTokens.get(index).className.equals("static")) {
                this.cTM = allTokens.get(index).value;
                index++;
                if (class_body7()) {

                    return true;

                }
            } else if (allTokens.get(index).className.equals("datatype")) {
                this.cType = allTokens.get(index).value;
                index++;
                if (allTokens.get(index).className.equals("Identifiers")) {
                    this.cName = allTokens.get(index).value;
                    //    this.classScope = this.currentScope;
                    index++;
                    if (class_body5()) {
                        return true;
                    }
                }

            } else if (allTokens.get(index).className.equals("void")) {
                this.cType = allTokens.get(index).value;
                index++;
                if (allTokens.get(index).className.equals("Identifiers")) {
                    this.cName = allTokens.get(index).value;
                    index++;
                    if (class_body6()) {
                        return true;
                    }
                }
            }

        }

        return false;
    }

    public boolean class_body2() {
        if (allTokens.get(index).className.equals("equalOp") || allTokens.get(index).className.equals("terminator") || allTokens.get(index).className.equals("paraSaperator") || allTokens.get(index).className.equals("arrOp")) {
            if (allTokens.get(index).className.equals("equalOp") || allTokens.get(index).className.equals("terminator") || allTokens.get(index).className.equals("paraSaperator")) {

                this.type = lookUpVariable(this.cName, this.classScope, this.className);

                if (type.equals("")) {

                    insertVariable(cName, cType, cAM, cTM, classScope, className);

                } else {

                    System.out.println("'" + this.cName + "' Variable Redeclaration at Line Number = " + allTokens.get(index).lineNo);

                }

                if (init()) {
                    if (list()) {
                        return true;
                    }

                }
            } else if (allTokens.get(index).value.equals("{")) {
                this.cType = this.cType.concat(allTokens.get(index).value);
                // System.out.println(cType);
                index++;
                if (allTokens.get(index).value.equals("}")) {
                    index++;
                    if (array1()) {
                        if (array2()) {
                            return true;
                        }
                    }

                }
            }
        }
        return false;
    }

    public boolean class_body3() {
        if (allTokens.get(index).className.equals("equalOp") || allTokens.get(index).className.equals("arrOp")) {

            this.type = lookUpVariable(this.cName, this.classScope, this.className);

            if (type.equals("")) {

                insertVariable(cName, cType, cAM, cTM, classScope, className);

            } else {

                System.out.println("'" + this.cName + "' Variable Redeclaration at Line Number = " + allTokens.get(index).lineNo);

            }

            if (allTokens.get(index).className.equals("equalOp")) {
                index++;
                if (allTokens.get(index).className.equals("make")) {
                    index++;
                    if (allTokens.get(index).className.equals("Identifiers")) {
                        this.cType1 = allTokens.get(index).value;
                        index++;
                        if (!cType.equals(cType1)) {
                            System.out.println("Type Mis-matched! at Line No: " + allTokens.get(index).lineNo);
                        }
                        if (allTokens.get(index).className.equals("conditionOpen")) {
                            index++;

                            if (args()) {
                                if (allTokens.get(index).className.equals("conditionClose")) {
                                    index++;

                                    if (allTokens.get(index).className.equals("terminator")) {
                                        index++;
                                        return true;
                                    }
                                }

                            }
                        }
                    }

                }
            } else if (allTokens.get(index).className.equals("arrOp")) {
                index++;
                if (allTokens.get(index).className.equals("arrCl")) {
                    index++;
                    if (obj_array1()) {
                        if (obj_array2()) {
                            return true;
                        }
                    }

                }
            }

        }
        return false;
    }

    public boolean class_body4() {//Verify selection set
        if (allTokens.get(index).className.equals("equalOp") || allTokens.get(index).className.equals("terminator") || (allTokens.get(index).className.equals("conditionOpen")) || (allTokens.get(index).className.equals("paraSaperator"))) {
            if (allTokens.get(index).className.equals("equalOp") || allTokens.get(index).className.equals("terminator") || (allTokens.get(index).className.equals("paraSaperator"))) {
                this.type = lookUpVariable(this.cName, this.classScope, this.className);

                if (type.equals("")) {

                    insertVariable(cName, cType, cAM, cTM, classScope, className);

                } else {

                    System.out.println("'" + this.cName + "' Variable Redeclaration at Line Number = " + allTokens.get(index).lineNo);

                }

                if (init()) {
                    if (list()) {
                        return true;
                    }
                }
            } else if ((allTokens.get(index).className.equals("conditionOpen"))) {
                if (params()) {
                    if (allTokens.get(index).className.equals("codeOpen")) {
                        index++;
                        if (body()) {
                            if (allTokens.get(index).className.equals("codeClose")) {
                                index++;
                                popCurrentScopeStack();
                                return true;
                            }

                        }

                    }
                }
            }

        }
        return false;
    }

    public boolean class_body5() {
        if ((allTokens.get(index).className.equals("equalOp")) || allTokens.get(index).className.equals("conditionOpen") || (allTokens.get(index).className.equals("arrOp")) || (allTokens.get(index).className.equals("terminator")) || (allTokens.get(index).className.equals("paraSaperator"))) {

            if (allTokens.get(index).className.equals("conditionOpen")) {
                if (class_body4()) {
                    return true;
                }
            } else if (allTokens.get(index).className.equals("arrOp")) {
                index++;
                if (allTokens.get(index).className.equals("arrCl")) {
                    index++;
                    if (array1()) {
                        if (array2()) {
                            return true;
                        }
                    }

                }
            }
        }
        return false;
    }

    public boolean class_body6() {
        if (params()) {

            if (allTokens.get(index).className.equals("codeOpen")) {
                index++;
                if (body()) {
                    if (allTokens.get(index).className.equals("codeClose")) {
                        index++;//
                        popCurrentScopeStack();
                        return true;

                    }
                }
            }
        }
        return false;
    }

    public boolean class_body7() {

        if (allTokens.get(index).className.equals("datatype") || allTokens.get(index).className.equals("void")) {
            if (allTokens.get(index).className.equals("datatype")) {
                this.cType = allTokens.get(index).value;
                index++;
                if (allTokens.get(index).className.equals("Identifiers")) {
                    this.cName = allTokens.get(index).value;
                    //     this.classScope = this.currentScope;

                    /*   this.type = lookUpVariable(this.cName, this.classScope, this.className);

                     if (type.equals("")) {

                     insertVariable(cName, cType, cAM, cTM, classScope, className);

                     } else {

                     System.out.println("'" + this.cName + "' Variable Redeclaration at Line Number = " + allTokens.get(index).lineNo);

                     }*/
                    index++;
                    if (class_body4()) {
                        return true;

                    }
                }
            } else if (allTokens.get(index).className.equals("void")) {
                cType = allTokens.get(index).value;
                index++;

                if (allTokens.get(index).className.equals("Identifiers")) {
                    cName = allTokens.get(index).value;
                    index++;
                    if (class_body6()) {
                        return true;
                    }
                }
            }
        }

        return false;
    }

// Starting of Body CFG
    public boolean body() {
        if ((allTokens.get(index).className.equals("terminator")) || (allTokens.get(index).className.equals("datatype")) || (allTokens.get(index).className.equals("codeClose")) || (allTokens.get(index).className.equals("Identifiers")) || (allTokens.get(index).className.equals("iterate")) || (allTokens.get(index).className.equals("check")) || (allTokens.get(index).className.equals("switch")) || (allTokens.get(index).className.equals("repeat")) || (allTokens.get(index).className.equals("break")) || (allTokens.get(index).className.equals("continue")) || (allTokens.get(index).className.equals("return")) || (allTokens.get(index).className.equals("decrementOp")) || (allTokens.get(index).className.equals("incrementOp"))) {
            if ((allTokens.get(index).className.equals("terminator"))) {
                index++;
                return true;
            } else if (mlst()) {
                return true;
            }

        }

        return false;
    }

    public boolean mlst() {
        if ((allTokens.get(index).className.equals("Identifiers")) || (allTokens.get(index).className.equals("datatype")) || (allTokens.get(index).className.equals("iterate")) || (allTokens.get(index).className.equals("check")) || (allTokens.get(index).className.equals("switch")) || (allTokens.get(index).className.equals("case")) || (allTokens.get(index).className.equals("default")) || (allTokens.get(index).className.equals("repeat")) || (allTokens.get(index).className.equals("break")) || (allTokens.get(index).className.equals("continue")) || (allTokens.get(index).className.equals("return")) || (allTokens.get(index).className.equals("incrementOp")) || (allTokens.get(index).className.equals("decrementOp"))) {
            if (sst()) {
                if (mlst()) {
                    return true;
                }
            }
        } else if (allTokens.get(index).className.equals("codeClose")) {
            return true;
        }

        return false;
    }

    // starting of M_ST and  S_ST
    //Parameters + Method Parameters Starting
    public boolean params() {
        if (allTokens.get(index).className.equals("conditionOpen")) {
            if (allTokens.get(index).className.equals("conditionOpen")) {
                index++;
                this.currentScope++;
                pushCurrentScopeStack();
                if (methodparam()) {
                    if (allTokens.get(index).className.equals("conditionClose")) {//
                        index++;
                        //  System.out.println(fType);
                        type = lookUpFunc(cName, fType, cType, className);
                        // System.out.println(type);
                        if (type.equals("")) {
                            insertFunc(cName, fType, cType, cAM, cTM, classScope, className);
                            //       System.out.println(cName+","+cType+"->"+fType+","+cAM+","+cTM+","+classScope+","+className);
                        } else {
                            System.out.println("'" + this.cName + "' Function Redeclaration at Line Number = " + allTokens.get(index).lineNo);
                        }
                        return true;

                    }
                }
            }
        }
        return false;
    }

    public boolean methodparam() {
        if (allTokens.get(index).className.equals("datatype") || allTokens.get(index).className.equals("Identifiers")) {
            if (allTokens.get(index).className.equals("datatype")) {
                this.variableType = allTokens.get(index).value;
                fType = allTokens.get(index).value;

                index++;
                if (allTokens.get(index).className.equals("Identifiers")) {
                    this.variableName = allTokens.get(index).value;
                    this.variableScope = this.currentScope;

                    this.type = lookUpVariable(this.variableName, this.variableScope, this.className);

                    if (type.equals("")) {

                        insertVariable(variableName, variableType, AM, TM, variableScope, className);

                    } else {

                        System.out.println("'" + this.variableName + "' Variable Redeclaration at Line Number = " + allTokens.get(index).lineNo);

                    }

                    index++;

                    if (methodparam1()) {

                        return true;
                    }
                }
            } else if (allTokens.get(index).className.equals("Identifiers")) {
                this.variableType = allTokens.get(index).value;
                fType = allTokens.get(index).value;

                index++;
                if (allTokens.get(index).className.equals("Identifiers")) {
                    this.variableName = allTokens.get(index).value;
                    this.variableScope = this.currentScope;

                    this.type = lookUpVariable(this.variableName, this.variableScope, this.className);

                    if (type.equals("")) {

                        insertVariable(variableName, variableType, AM, TM, variableScope, className);

                    } else {

                        System.out.println("'" + this.variableName + "' Variable Redeclaration at Line Number = " + allTokens.get(index).lineNo);

                    }

                    index++;
                    if (methodparam1()) {

                        return true;
                    }
                }
            }

        } else if (allTokens.get(index).className.equals("conditionClose")) {
            return true;
        }
        return false;
    }

    public boolean methodparam1() {
        if (allTokens.get(index).className.equals("paraSaperator") || allTokens.get(index).className.equals("arrOp")) {
            if (allTokens.get(index).className.equals("paraSaperator")) {
                index++;
                if (allTokens.get(index).className.equals("datatype")) {
                    this.variableType = allTokens.get(index).value;
                    fType = fType + "," + allTokens.get(index).value;
                    index++;
                    if (allTokens.get(index).className.equals("Identifiers")) {
                        this.variableName = allTokens.get(index).value;
                        this.variableScope = this.currentScope;

                        this.type = lookUpVariable(this.variableName, this.variableScope, this.className);

                        if (type.equals("")) {

                            insertVariable(variableName, variableType, AM, TM, variableScope, className);

                        } else {

                            System.out.println("'" + this.variableName + "' Variable Redeclaration at Line Number = " + allTokens.get(index).lineNo);

                        }
                        index++;
                        if (methodparam1()) {
                            return true;
                        }
                    }
                } else if (allTokens.get(index).className.equals("Identifiers")) {
                    this.variableType = allTokens.get(index).value;
                    fType = fType + "," + allTokens.get(index).value;

                    index++;
                    if (allTokens.get(index).className.equals("Identifiers")) {
                        this.variableName = allTokens.get(index).value;
                        this.variableScope = this.currentScope;

                        this.type = lookUpVariable(this.variableName, this.variableScope, this.className);

                        if (type.equals("")) {

                            insertVariable(variableName, variableType, AM, TM, variableScope, className);

                        } else {

                            System.out.println("'" + this.variableName + "' Variable Redeclaration at Line Number = " + allTokens.get(index).lineNo);

                        }

                        index++;
                        if (methodparam1()) {

                            return true;
                        }
                    }
                }

            } else if (allTokens.get(index).className.equals("arrOp")) {
                index++;
                if (allTokens.get(index).className.equals("arrCl")) {
                    index++;
                    if (methodparam1()) {
                        return true;
                    }
                }
            }
        } else if (allTokens.get(index).className.contains("conditionClose")) {
            return true;
        }
        return false;
    }

    //ENDING OF PARAMETERS + METHOD PARAMETERS
    //STARTING OF FUNCTION CALLING CFG
    public boolean func_call() {
        if (allTokens.get(index).className.equals("Identifiers")) {

            this.variableName = allTokens.get(index).value;
            this.variableType = allTokens.get(index).value;
            this.variableScope = this.currentScope;

            index++;
            if (calling()) {
                return true;
            }
        }

        return false;
    }

    public boolean calling() {
        if (allTokens.get(index).className.equals("classSeperator") || allTokens.get(index).className.equals("conditionOpen")) {
            if (allTokens.get(index).className.equals("classSeperator")) {
                index++;
                
                if (allTokens.get(index).className.equals("Identifiers")) {
                    this.varName = allTokens.get(index).value;
                    
                    index++;
                    if (allTokens.get(index).className.equals("conditionOpen")) {
                        index++;
                        if (args()) {
                            if (allTokens.get(index).className.equals("conditionClose")) {
                                index++;

                                if (allTokens.get(index).className.equals("terminator")) {
                                    index++;
                                    return true;
                                }
                            }
                        }
                    }
                }
            } else if (allTokens.get(index).className.equals("conditionOpen")) {
                index++;
                if (args()) {
                    if (allTokens.get(index).className.equals("conditionClose")) {
                        index++;
                        do {
                            this.cType = lookUpVariable(this.variableName, classScope, this.className);
                            type = lookUpFunc1(this.variableName, cType, fType, className);

                            if (type.equals("")) {
                                System.out.println("'" + this.variableName + "' Function Undefined at Line Number = " + allTokens.get(index).lineNo);
                                //       System.out.println(cName+","+cType+"->"+fType+","+cAM+","+cTM+","+classScope+","+className);
                            }
                        } while (cType.equals(""));

                        if (allTokens.get(index).className.equals("terminator")) {
                            index++;
                            return true;
                        }
                    }
                }

            }
        }

        return false;
    }

    //ENDING OF FUNCTION CALLING CFG
    public boolean sst() {//
        if ((allTokens.get(index).className.equals("Identifiers")) || (allTokens.get(index).className.equals("datatype")) || (allTokens.get(index).className.equals("iterate")) || (allTokens.get(index).className.equals("check")) || (allTokens.get(index).className.equals("switch")) || (allTokens.get(index).className.equals("case")) || (allTokens.get(index).className.equals("default")) || (allTokens.get(index).className.equals("repeat")) || (allTokens.get(index).className.equals("break")) || (allTokens.get(index).className.equals("continue")) || (allTokens.get(index).className.equals("return")) || (allTokens.get(index).className.equals("incrementOp")) || (allTokens.get(index).className.equals("decrementOp"))) {

            if (check_then()) {
                return true;
            } else if (switch_case()) {
                return true;
            } else if (repeat()) {
                return true;
            } else if (iterate()) {
                return true;

            } else if (func_call()) {
                return true;

            } else if (break_st()) {
                return true;

            } else if (return_st()) {
                return true;
            } else if ((allTokens.get(index).className.equals("datatype"))) {

                this.variableType = allTokens.get(index).value;
                this.cType = allTokens.get(index).value;
                t1 = allTokens.get(index).value;
                index++;
                if ((allTokens.get(index).className.equals("Identifiers"))) {

                    this.variableName = allTokens.get(index).value;
                    this.AM = "";
                    this.TM = "";
                    this.variableScope = this.currentScope;

                    this.type = lookUpVariable(this.variableName, this.variableScope, this.className);

                    //  System.out.println(t1+" "+allTokens.get(index).lineNo);
                    index++;
                    if (sst1()) {
                        return true;
                    }

                }
            } //END OF INC DEC ID TERMINATOR
            else if (allTokens.get(index).className.equals("Identifiers") || allTokens.get(index).className.equals("equalOp") || allTokens.get(index).className.equals("assignmentOp") || allTokens.get(index).className.equals("incrementOp") || allTokens.get(index).className.equals("decrementOp") || allTokens.get(index).className.equals("arrOp") || allTokens.get(index).className.equals("conditionOpen")) {
                //  this.className = allTokens.get(index).value;
                this.type = lookUpVariable(this.variableName, this.variableScope, this.className);
                if (type.equals("")) {
                    this.type = lookUpVariable(this.variableName, this.classScope, this.className);
                }
                t1 = type;
                t3 = type;
                if (type.equals("")) {
                    // not declared
                    if (lookUpClass(this.variableName).equals(this.className)) {

                    } else {
                        System.out.println("'" + this.variableName + "' Undeclerad at Line Number = " + allTokens.get(index).lineNo);

                    }

                }
                if (sst0()) {
                    return true;
                }

                if (allTokens.get(index).className.equals("Identifiers")) {

                    this.variableName = allTokens.get(index).value;
                    this.variableScope = this.currentScope;
                    type = lookUpVariable(this.variableName, this.variableScope, this.className);
                    if (type.equals("")) {
                        // not declared
                        System.out.println("'" + this.variableName + "' Variable Undeclerad at Line Number = " + allTokens.get(index).lineNo);

                    }
                    index++;
                    if ((allTokens.get(index).className.equals("terminator"))) {
                        index++;
                        return true;
                    }
                }
            }

        }
        return false;
    }

    public boolean sst0() {

        if ((allTokens.get(index).className.equals("Identifiers")) || (allTokens.get(index).className.equals("classSeperator")) || (allTokens.get(index).className.equals("equalOp")) || (allTokens.get(index).className.equals("assignmentOp")) || (allTokens.get(index).className.equals("arrOp")) || (allTokens.get(index).className.equals("conditionOpen")) || (allTokens.get(index).className.equals("incrementOp")) || (allTokens.get(index).className.equals("decrementOp"))) {
            if ((allTokens.get(index).className.equals("Identifiers"))) {

                this.variableName = allTokens.get(index).value;
                this.AM = "";
                this.TM = "";
                this.variableScope = this.currentScope;

                this.type = lookUpVariable(this.variableName, this.variableScope, this.className);
                //  System.out.println(type);
                if (type.equals("")) {

                    insertVariable(variableName, variableType, AM, TM, variableScope, className);

                } else {

                    System.out.println("'" + this.variableName + "' Variable Redeclaration at Line Number = " + allTokens.get(index).lineNo);

                }

                index++;
                if (sst2()) {
                    return true;
                }
            } else if (sst3()) {
                return true;
            } else if (allTokens.get(index).className.equals("incrementOp") || allTokens.get(index).className.equals("decrementOp")) {
                op = allTokens.get(index).value;

                eType = Comp1(t1, op);
                if (eType.equals("")) {
                    System.out.println("Type Mis-matched at Line No: " + allTokens.get(index).lineNo);
                }
                index++;

                if (allTokens.get(index).className.equals("terminator")) {
                    index++;
                    return true;
                }

            }

        }
        return false;

    }

    public boolean sst1() {
        if ((allTokens.get(index).className.equals("paraSaperator")) || (allTokens.get(index).className.equals("equalOp")) || (allTokens.get(index).className.equals("arrOp")) || (allTokens.get(index).className.equals("terminator"))) {
            if (allTokens.get(index).className.equals("arrOp")) {
                this.variableType = this.variableType.concat(allTokens.get(index).value);
                //    System.out.println(this.variableType);
                this.cType = this.variableType;
                index++;
                if (allTokens.get(index).className.equals("arrCl")) {
                    index++;
                    if (type.equals("")) {
                        // not declared
                        insertVariable(variableName, variableType, AM, TM, variableScope, className);

                    } else {
                        // declared
                        System.out.println("'" + this.variableName + "' Variable Redecleration at Line Number = " + allTokens.get(index).lineNo);
                        // return;
                    }
                    if (array1()) {
                        if (array2()) {

                            return true;
                        }
                    }

                }
            }
            if ((allTokens.get(index).className.equals("paraSaperator")) || (allTokens.get(index).className.equals("equalOp")) || (allTokens.get(index).className.equals("terminator"))) {
                if (type.equals("")) {
                    // not declared
                    insertVariable(variableName, variableType, AM, TM, variableScope, className);

                } else {
                    // declared
                    System.out.println("'" + this.variableName + "' Variable Redecleration at Line Number = " + allTokens.get(index).lineNo);
                    // return;
                }

                if (init()) {//
                    if (list()) {
                        return true;
                    }
                }
            }

        }

        return false;

    }

    public boolean sst2() {
        if (allTokens.get(index).className.equals("equalOp") || allTokens.get(index).className.equals("arrOp")) {
            if (allTokens.get(index).className.equals("equalOp")) {
                index++;
                if (allTokens.get(index).className.equals("make")) {
                    index++;
                    if (allTokens.get(index).className.equals("Identifiers")) {
                        index++;
                        if (allTokens.get(index).className.equals("conditionOpen")) {
                            index++;
                            if (args()) {
                                if (allTokens.get(index).className.equals("conditionClose")) {
                                    index++;
                                    if (allTokens.get(index).className.equals("terminator")) {
                                        index++;
                                        return true;
                                    }
                                }

                            }
                        }
                    }

                }
            } else if (allTokens.get(index).className.equals("arrOp")) {
                index++;
                if (allTokens.get(index).className.equals("arrCl")) {
                    index++;
                    if (obj_array1()) {
                        if (obj_array2()) {
                            return true;
                        }
                    }

                }
            }

        }

        return false;
    }

    public boolean sst3() {
        if (allTokens.get(index).className.equals("equalOp") || allTokens.get(index).className.equals("arrOp") || allTokens.get(index).className.equals("classSeperator") || allTokens.get(index).className.equals("assignmentOp") || allTokens.get(index).className.equals("conditionOpen")) {
            if (allTokens.get(index).className.equals("classSeperator")) {
                index++;
                if (allTokens.get(index).className.equals("Identifiers")) {
                    index++;
                    if (sst4()) {
                        return true;
                    }

                }
            } else if (allTokens.get(index).className.equals("assignmentOp")) {
                index++;
                if (asg_2()) {
                    return true;
                }

            } else if (allTokens.get(index).className.equals("conditionOpen")) {
                index++;
                if (args()) {
                    if (allTokens.get(index).className.equals("conditionClose")) {
                        index++;

                        if (allTokens.get(index).value.equals("terminator")) {
                            index++;
                            return true;
                        }

                    }

                }
            } else if (allTokens.get(index).className.equals("equalOp")) {
                this.ope = allTokens.get(index).value;
                index++;
                if (sst5()) {
                    return true;
                }
            } else if (allTokens.get(index).className.equals("arrOp")) {
                index++;
                if (constant()) {
                    if (allTokens.get(index).className.equals("arrCl")) {
                        index++;
                        if (sst6()) {
                            return true;
                        }

                    }

                }
            }
        }

        return false;

    }

    public boolean sst4() {

        if (allTokens.get(index).className.equals("equalOp") || allTokens.get(index).className.equals("conditionOpen") || allTokens.get(index).className.equals("arrOp") || allTokens.get(index).className.equals("assignmentOp") || allTokens.get(index).className.equals("incrementOp") || allTokens.get(index).className.equals("decrementOp")) {
            if (assigPp()) {
                return true;
            } else if (allTokens.get(index).className.equals("conditionOpen")) {
                index++;
                if (args()) {
                    if (allTokens.get(index).className.equals("conditionClose")) {
                        index++;
                        if (allTokens.get(index).className.equals("terminator")) {

                            index++;
                            return true;
                        }
                    }
                }

            }

        }

        return false;

    }

    public boolean sst5() {
        if (allTokens.get(index).className.equals("datatype") || allTokens.get(index).className.equals("notOp") || allTokens.get(index).className.equals("conditionOpen") || allTokens.get(index).className.equals("Identifiers") || allTokens.get(index).className.equals("numberConstant") || allTokens.get(index).className.equals("letterConstant") || allTokens.get(index).className.equals("wordConstant") || allTokens.get(index).className.equals("deciConstant") || allTokens.get(index).className.equals("incrementOp") || allTokens.get(index).className.equals("decrementOp")) {
            if (asg_2()) {
                return true;
            } else if (sst8()) {
                return true;
            }

        }
        return false;

    }

    public boolean sst6() {

        if (allTokens.get(index).className.equals("codeClose") || allTokens.get(index).className.equals("Identifiers") || allTokens.get(index).className.equals("datatype") || allTokens.get(index).className.equals("classSeperator") || allTokens.get(index).className.equals("incrementOp") || allTokens.get(index).className.equals("decrementOp") || allTokens.get(index).className.equals("equalOp")) {
            if (assigPpp()) {// goto line 1910
                if (sst7()) {//cmm
                    return true;
                }
            }

        }
        return false;

    }

    public boolean sst7() {
        if (allTokens.get(index).className.equals("incrementOp") || allTokens.get(index).className.equals("decrementOp") || allTokens.get(index).className.equals("equalOp") || allTokens.get(index).className.equals("assignmentOp") || allTokens.get(index).className.equals("numberConstant")) {
            if (allTokens.get(index).className.equals("incrementOp") || allTokens.get(index).className.equals("decrementOp")) {
                index++;
                if (allTokens.get(index).className.equals("terminator")) {
                    index++;
                    return true;
                }
            } else if (asg_1()) {
                return true;
            }
        }

        return false;
    }

    public boolean sst8() {

        if (allTokens.get(index).className.equals("Identifiers") || allTokens.get(index).className.equals("datatype")) {
            if (allTokens.get(index).className.equals("Identifiers")) {
                index++;
                if (allTokens.get(index).className.equals("conditionOpen")) {
                    index++;
                    if (args()) {
                        if (allTokens.get(index).className.equals("conditionClose")) {
                            index++;
                            if (allTokens.get(index).className.equals("terminator")) {
                                index++;
                                return true;
                            }
                        }
                    }
                }
            } else if (allTokens.get(index).className.equals("datatype")) {
                index++;
                if (allTokens.get(index).className.equals("arrOp")) {
                    index++;
                    if (constant()) {
                        if (allTokens.get(index).className.equals("arrCl")) {
                            index++;
                            if (allTokens.get(index).className.equals("terminator")) {
                                index++;
                                return true;
                            }
                        }
                    }
                }
            }
        }

        return false;
    }

    //Ending of M_st and S_St
    //STARTING OF SST_0 ALL INTERNAL FUNTIONALITES
    public CheckCFGsSemantic() {
    }

    public boolean check_then() {
        if (allTokens.get(index).className.equals("check")) {
            if (allTokens.get(index).className.equals("check")) {
                index++;
                if (allTokens.get(index).className.equals("conditionOpen")) {
                    index++;
                    if (cond()) {
                        if (allTokens.get(index).className.equals("conditionClose")) {
                            index++;
                            if (allTokens.get(index).className.equals("codeOpen")) {
                                index++;
                                if (body()) {
                                    if (allTokens.get(index).className.equals("codeClose")) {
                                        index++;
                                        if (Oelse()) {
                                            return true;
                                        }

                                    }
                                }
                            }
                        }
                    }
                }

            }

        }
        return false;
    }

    public boolean Oelse() {

        if (allTokens.get(index).className.equals("then")) {
            index++;

            if (allTokens.get(index).className.equals("codeOpen")) {
                index++;
                if (body()) {
                    if (allTokens.get(index).className.equals("codeClose")) {
                        index++;
                        return true;
                    }

                }

            }

        }

        return true;
    }

    public boolean switch_case() {
        if (allTokens.get(index).className.equals("switch") || (allTokens.get(index).className.equals("case")) || (allTokens.get(index).className.equals("default"))) {

            if (allTokens.get(index).className.equals("switch")) {
                index++;
                if (allTokens.get(index).className.equals("conditionOpen")) {
                    index++;
                    if (E()) {
                        if (allTokens.get(index).className.equals("conditionClose")) {
                            index++;

                            if (allTokens.get(index).className.equals("codeOpen")) {
                                index++;
                                this.currentScope++;
                                pushCurrentScopeStack();
                                if (case1()) {
                                    if (allTokens.get(index).className.equals("codeClose")) {
                                        index++;
                                        popCurrentScopeStack();
                                        return true;

                                    }
                                }
                            }

                        }
                    }
                }

            } else if (case1()) {
                return true;
            }

        }
        return false;
    }

    public boolean case1() {
        if (allTokens.get(index).className.equals("case") || allTokens.get(index).className.equals("default")) {
            if (allTokens.get(index).className.equals("case")) {
                if (allTokens.get(index).className.equals("case")) {
                    index++;
                    if (constant()) {
                        if (allTokens.get(index).className.equals("loopCase")) {
                            index++;
                            if (body()) {
                                if (case1()) {
                                    return true;
                                }
                            }
                        }

                    }
                }
            } else if (allTokens.get(index).className.equals("default")) {
                if (allTokens.get(index).className.equals("default")) {
                    index++;
                    if (allTokens.get(index).className.equals("loopCase")) {
                        index++;
                        if (body()) {
                            //   if (case1()) {
                            return true;
                            //  }

                        }

                    }

                }
            }
        } else if (allTokens.get(index).className.equals("codeClose")) {
            return true;
        }
        return true;
    }

    public boolean cond() {
        if (allTokens.get(index).className.equals("Identifiers") || allTokens.get(index).className.equals("conditionOpen") || allTokens.get(index).className.equals("incrementOp") || allTokens.get(index).className.equals("decrementOp") || allTokens.get(index).className.equals("notOp") || allTokens.get(index).className.equals("numberConstant") || allTokens.get(index).className.equals("letterConstant") || allTokens.get(index).className.equals("wordConstant") || allTokens.get(index).className.equals("deciConstant")) {
            if (ORE()) {//
                return true;
            }

        }
        return false;
    }

    public boolean repeat() {
        if (allTokens.get(index).className.equals("repeat")) {
            if (allTokens.get(index).className.equals("repeat")) {
                index++;
                if (allTokens.get(index).className.equals("conditionOpen")) {
                    index++;
                    if (D2()) {
                        if (allTokens.get(index).className.equals("conditionClose")) {
                            index++;

                            if (allTokens.get(index).className.equals("codeOpen")) {
                                index++;
                                this.currentScope++;
                                pushCurrentScopeStack();

                                if (body()) {
                                    if (allTokens.get(index).className.equals("codeClose")) {
                                        index++;
                                        popCurrentScopeStack();

                                        return true;
                                    }
                                }

                            }

                        }

                    }

                }

            }
        }

        return false;
    }

    public boolean iterate() {
        if (allTokens.get(index).className.equals("iterate")) {

            if (allTokens.get(index).className.equals("iterate")) {
                index++;
                if (allTokens.get(index).className.equals("conditionOpen")) {
                    index++;
                    this.currentScope++;
                    pushCurrentScopeStack();
                    if (D1()) {//
                        if (allTokens.get(index).className.equals("loopPunc")) {
                            index++;
                            if (D2()) {
                                if (allTokens.get(index).className.equals("paraSaperator")) {
                                    index++;
                                    if (inc()) {
                                        if (allTokens.get(index).className.equals("conditionClose")) {
                                            index++;
                                            if (allTokens.get(index).className.equals("codeOpen")) {
                                                index++;
                                                if (body()) {
                                                    if (allTokens.get(index).className.equals("codeClose")) {
                                                        index++;
                                                        popCurrentScopeStack();

                                                        return true;
                                                    }
                                                }

                                            }
                                        }

                                    }

                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean D1() {
        if (allTokens.get(index).className.equals("datatype") || allTokens.get(index).className.equals("Identifiers")) {
            if (dec2()) {//
                return true;
            }
            if (assign_st2()) {
                return true;//
            }
        } else if (allTokens.get(index).className.equals("loopPunc")) {
            return true;

        }
        return false;
    }

    public boolean D2() {
        if (allTokens.get(index).className.equals("conditionOpen") || allTokens.get(index).className.equals("notOp") || allTokens.get(index).className.equals("Identifiers") || allTokens.get(index).className.equals("incrementOp") || allTokens.get(index).className.equals("decrementOp") || allTokens.get(index).className.equals("letterConstant") || allTokens.get(index).className.equals("numberConstant") || allTokens.get(index).className.equals("wordConstant") || allTokens.get(index).className.equals("deciConstant")) {
            if (cond()) {
                return true;
            }
        }
        return true;
    }

    public boolean inc() {
        if (allTokens.get(index).className.equals("incrementOp") || allTokens.get(index).className.equals("decrementOp") || allTokens.get(index).className.equals("Identifiers")) {
            if (allTokens.get(index).className.equals("incrementOp") || allTokens.get(index).className.equals("decrementOp")) {
                index++;
                if (allTokens.get(index).className.equals("Identifiers")) {
                    index++;
                    return true;
                }
            } else if (allTokens.get(index).className.equals("Identifiers")) {
                index++;
                if (inc2()) {
                    return true;
                }
            }
        } else if (allTokens.get(index).className.equals("conditionClose")) {
            return true;
        }
        return false;
    }

    public boolean inc2() {
        if (allTokens.get(index).className.equals("incrementOp") || allTokens.get(index).className.equals("decrementOp") || allTokens.get(index).className.equals("classSeperator") || allTokens.get(index).className.equals("arrCl") || allTokens.get(index).className.equals("assignmentOp") || allTokens.get(index).className.equals("equalOp")) {
            if (allTokens.get(index).className.equals("incrementOp") || allTokens.get(index).className.equals("decrementOp")) {
                index++;
                return true;
            } else if (assigP()) {
                if (asg_12()) {
                    return true;
                }
            }
        }
        return true;
    }

    public boolean break_st() {
        if (allTokens.get(index).className.equals("continue") || allTokens.get(index).className.equals("break")) {
            if (allTokens.get(index).className.equals("continue")) {
                index++;
                if (allTokens.get(index).className.equals("terminator")) {
                    index++;
                    return true;

                }
            } else if (allTokens.get(index).className.equals("break")) {
                index++;
                if (allTokens.get(index).className.equals("terminator")) {
                    index++;
                    return true;

                }
            }

        }

        return false;

    }

    public boolean return_st() {
        if (allTokens.get(index).className.equals("return")) {
            if (allTokens.get(index).className.equals("return")) {
                index++;
                if (value()) {
                    return true;
                }
            }

        }
        return false;
    }

    public boolean value() {
        if (true)//First of ORE
        {
            if (ORE()) {
                if (allTokens.get(index).className.equals("terminator")) {
                    index++;
                    return true;
                }

            }

        }
        return false;
    }

    //ENDING OF SST ALL INTERNAL FUNTIONALITIES
    //Starting of Declaration 
    public boolean dec() {
        if (allTokens.get(index).className.equals("access_mod") || allTokens.get(index).className.equals("datatype")) {
            if (A_M()) {
                if (allTokens.get(index).className.equals("datatype")) {
                    index++;
                    if ((allTokens.get(index).className.equals("Identifiers"))) {
                        index++;
                        if (init()) {
                            if (list()) {
                                return true;
                            }
                        }

                    }
                }
            }

        }
        return false;
    }

    public boolean init() {
        if (allTokens.get(index).className.equals("equalOp")) {
            if (allTokens.get(index).className.equals("equalOp")) {
                this.op = allTokens.get(index).value;
                index++;
                if (init2()) {
                    return true;
                }

            }

        }
        return true;

    }

    public boolean init2() {
        if (allTokens.get(index).className.equals("Identifiers") || allTokens.get(index).className.equals("notOp") || allTokens.get(index).className.equals("incrementOp") || allTokens.get(index).className.equals("decrementOp") || allTokens.get(index).className.equals("numberConstant") || allTokens.get(index).className.equals("letterConstant") || allTokens.get(index).className.equals("wordConstant") || allTokens.get(index).className.equals("deciConstant")) {
            if (allTokens.get(index).className.equals("Identifiers")) {

                this.variableName = allTokens.get(index).value;
                this.variableScope = this.currentScope;

                if (lookUpVariable(this.variableName, this.variableScope, this.className).equals("")) {
                    // not declared
                    System.out.println("'" + this.variableName + "' Variable Undeclared at Line Number = " + allTokens.get(index).lineNo);

                }

                index++;
                if (init3()) {
                    return true;
                }
            } else if (constant()) {

                this.eType = Comp(t1, t2, op);
                if (eType.equals("")) {
                    System.out.println("Type Mis-matched at Line No: " + allTokens.get(index).lineNo);
                } else {
                    this.eType1 = Comp(t1, t2, op);
                    //System.out.println(eType1+"  newType");
                }

                if (init4()) {
                    return true;
                }
            } else if (allTokens.get(index).className.equals("incrementOp") || allTokens.get(index).className.equals("decrementOp")) {
                index++;
                if (allTokens.get(index).className.equals("Identifiers")) {
                    index++;
                    if (init4()) {
                        return true;
                    }

                }
            } else if (allTokens.get(index).className.equals("notOp")) {
                index++;
                if (F2()) {
                    if (init4()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    public boolean init3() {
        if (allTokens.get(index).className.equals("equalOp") || allTokens.get(index).className.equals("classSeperator") || allTokens.get(index).className.equals("arrOp") || allTokens.get(index).className.equals("decrementOp") || allTokens.get(index).className.equals("arrOp") || allTokens.get(index).className.equals("relationalOp") || allTokens.get(index).className.equals("andOp") || allTokens.get(index).value.equals("orOp") || allTokens.get(index).value.equals("incrementOp") || allTokens.get(index).value.equals("paraSaperator") || allTokens.get(index).value.equals("addSub") || allTokens.get(index).value.equals("mulDivMod") || allTokens.get(index).value.equals("terminator")) {
            if (init()) {
                return true;
            } else if (ob1()) {
                return true;
            } else if (allTokens.get(index).className.equals("incrementOp") || allTokens.get(index).className.equals("decrementOp")) {
                index++;
                if (init4()) {
                    return true;
                }
            } else if (Tp()) {
                if (Ep()) {
                    if (REp()) {
                        if (ANDEp()) {
                            if (OREp()) {
                                return true;
                            }
                        }
                    }

                }
            }
        }

        return false;
    }

    public boolean init4() {
        if (allTokens.get(index).className.equals("mulDivMod") || allTokens.get(index).className.equals("addSub") || allTokens.get(index).className.equals("terminator") || allTokens.get(index).className.equals("andOp") || allTokens.get(index).className.equals("orOp") || allTokens.get(index).className.equals("relationalOp") || allTokens.get(index).className.equals("paraSaperator")) {
            if (Tp()) {
                if (Ep()) {
                    if (REp()) {
                        if (ANDEp()) {
                            if (OREp()) {
                                return true;
                            }
                        }
                    }

                }
            }

        }

        return false;
    }

    public boolean list() {
        if (allTokens.get(index).className.equals("terminator") || allTokens.get(index).className.equals("paraSaperator")) {
            if (allTokens.get(index).className.equals("terminator")) {
                index++;
                return true;
            } else if (allTokens.get(index).className.equals("paraSaperator")) {
                index++;
                if (allTokens.get(index).className.equals("Identifiers")) {

                    this.variableName = allTokens.get(index).value;

                    if (lookUpVariable(this.variableName, this.variableScope, this.className).equals("")) {
                        insertVariable(variableName, variableType, AM, TM, variableScope, className);

                    } else {
                        // declared
                        System.out.println("'" + this.variableName + "' Variable Redecleration at line Number = " + allTokens.get(index).lineNo);

                    }

                    index++;
                    if (init()) {
                        if (list()) {
                            return true;
                        }
                    }
                }
            }
        }

        return false;
    }

    public boolean A_M() {

        if (allTokens.get(index).className.equals("access_mod")) {
            if (allTokens.get(index).className.equals("access_mod")) {
                index++;
                if (reserve()) {
                    return true;
                }

            }
        }

        return true;
    }

    public boolean reserve() {
        if (allTokens.get(index).className.equals("static")) {
            if (allTokens.get(index).className.equals("static")) {
                index++;
                return true;

            }

        }

        return true;
    }

    //Starting of Argumnets
    public boolean args() { //check Not OP
        if ((allTokens.get(index).className.equals("Identifiers")) || (allTokens.get(index).className.equals("wordConstant")) || (allTokens.get(index).className.equals("letterConstant")) || (allTokens.get(index).className.equals("numberConstant")) || (allTokens.get(index).className.equals("deciConstant")) || (allTokens.get(index).className.equals("incrementOp")) || (allTokens.get(index).className.equals("decrementOp")) || (allTokens.get(index).className.equals("notOp"))) {
            fType = "";
            if (arg1()) {//
                return true;
            }

        } else if (allTokens.get(index).className.equals("conditionClose")) {
            return true;
        }
        return false;
    }

    public boolean arg1() {
        if ((allTokens.get(index).className.equals("Identifiers")) || (allTokens.get(index).className.equals("wordConstant")) || (allTokens.get(index).className.equals("letterConstant")) || (allTokens.get(index).className.equals("numberConstant")) || (allTokens.get(index).className.equals("deciConstant")) || (allTokens.get(index).className.equals("incrementOp")) || (allTokens.get(index).className.equals("decrementOp")) || (allTokens.get(index).className.equals("notOp"))) {

            if ((allTokens.get(index).className.equals("Identifiers"))) {

                if (id_arg()) {
                    if (arg2()) {
                        return true;
                    }
                }
            } else if (const_arg()) {
                if (arg2()) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean arg2() {
        if ((allTokens.get(index).className.equals("paraSaperator"))) {
            if ((allTokens.get(index).className.equals("paraSaperator"))) {
                index++;
                fType += ",";
                if (arg1()) {
                    return true;
                }

            }
        } else if (allTokens.get(index).className.equals("conditionClose")) {
            return true;
        }
        return false;

    }

    public boolean id_arg() {
        if ((allTokens.get(index).className.equals("Identifiers"))) {

            if ((allTokens.get(index).className.equals("Identifiers"))) {
                this.argName = allTokens.get(index).value;

                this.type = lookUpVariable(this.argName, this.variableScope, this.className);
                if (type.equals("")) {
                    this.type = lookUpVariable(this.argName, this.classScope, this.className);

                }

                if (type.equals("")) {
                    System.out.println("'" + this.argName + "' Variable Undeclared at Line Number = " + allTokens.get(index).lineNo);

                } else {
                    fType = fType + type;
                    //   System.out.println(fType);//
                }

                index++;
                if (id_arg1()) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean id_arg1() {

        if (allTokens.get(index).className.equals("incrementOp") || allTokens.get(index).className.equals("decrementOp") || allTokens.get(index).className.equals("classSeperator") || allTokens.get(index).className.equals("arrOp") || allTokens.get(index).className.equals("mulDivMod") || allTokens.get(index).className.equals("addSub") || allTokens.get(index).className.equals("relationalOp") || allTokens.get(index).className.equals("andOp") || allTokens.get(index).className.equals("orOp") || allTokens.get(index).className.equals("conditionOpen")) {
            if (F1()) {
                if (Tp()) {
                    if (Ep()) {
                        if (REp()) {
                            if (ANDEp()) {
                                if (OREp()) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            } else if (F2()) {
                if (Tp()) {
                    if (Ep()) {
                        if (REp()) {
                            if (ANDEp()) {
                                if (OREp()) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        } else if (allTokens.get(index).className.equals("paraSaperator") || allTokens.get(index).className.equals("conditionClose")) {
            return true;
        }
        return false;

    }

    public boolean const_arg() {
        if (allTokens.get(index).className.equals("incrementOp") || allTokens.get(index).className.equals("decrementOp") || allTokens.get(index).className.equals("letterConstant") || allTokens.get(index).className.equals("numberConstant") || allTokens.get(index).className.equals("wordConstant") || allTokens.get(index).className.equals("deciConstant") || allTokens.get(index).className.equals("notOp")) {

            if (constant()) {
                if (Tp()) {
                    if (Ep()) {
                        if (REp()) {
                            if (ANDEp()) {
                                if (OREp()) {
                                    return true;
                                }
                            }
                        }
                    }
                }

            } else if ((allTokens.get(index).className.equals("decrementOp")) || (allTokens.get(index).className.equals("incrementOp"))) {
                index++;
                if (allTokens.get(index).className.equals("Identifiers")) {
                    index++;
                    if (Tp()) {
                        if (Ep()) {
                            if (REp()) {
                                if (ANDEp()) {
                                    if (OREp()) {
                                        return true;
                                    }
                                }
                            }
                        }
                    }

                }

            } else if (allTokens.get(index).className.equals("notOp")) {
                index++;
                if (F2()) {
                    if (Tp()) {
                        if (Ep()) {
                            if (REp()) {
                                if (ANDEp()) {
                                    if (OREp()) {
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }

            }
        }

        return true;
    }

    //Ending of Argumnets 
    //ID Constants CFGS
    public boolean id_const() {
        if (allTokens.get(index).className.equals("Identifiers") || allTokens.get(index).className.equals("numberConstant") || allTokens.get(index).className.equals("letterConstant") || allTokens.get(index).className.equals("wordConstant") || allTokens.get(index).className.equals("deciConstant")) {
            if (allTokens.get(index).className.equals("Identifiers")) {
                index++;
                return true;
            } else if (constant()) {
                return true;
            }
        }
        return false;
    }

    //Starting of Object_Creation
    public boolean obj_main() {
        if (allTokens.get(index).className.equals("Identifiers")) {
            if (allTokens.get(index).className.equals("Identifiers")) {
                index++;
                if (ob1()) {
                    return true;
                }

            }

        }

        return false;
    }

    public boolean ob1() {
        if (allTokens.get(index).className.equals("arrOp") || allTokens.get(index).className.equals("classSeperator")) {
            if (allTokens.get(index).className.equals("classSeperator")) {
                index++;
                if (allTokens.get(index).className.equals("Identifiers")) {
                    index++;
                    if (ob2()) {
                        return true;
                    }
                }
            } else if (allTokens.get(index).className.equals("arrOp")) {
                index++;
                if (id_const()) {
                    if (allTokens.get(index).className.equals("arrCl")) {
                        index++;
                        if (ob8()) {
                            return true;
                        }

                    }
                }
            }
        }

        return false;
    }

    public boolean ob2() {
        if (allTokens.get(index).className.equals("conditionOpen") || allTokens.get(index).className.equals("arrOp") || allTokens.get(index).className.equals("equalOp") || allTokens.get(index).className.equals("assignmentOp") || allTokens.get(index).className.equals("mulDivMod") || allTokens.get(index).className.equals("addSub") || allTokens.get(index).className.equals("relationalOp") || allTokens.get(index).className.equals("orOp") || allTokens.get(index).className.equals("andOp") || allTokens.get(index).className.equals("terminator") || allTokens.get(index).className.equals("paraSaperator") || allTokens.get(index).className.equals("incrementOp") || allTokens.get(index).className.equals("decrementOp")) {
            if (allTokens.get(index).className.equals("conditionOpen")) {
                index++;
                if (args()) {
                    if (allTokens.get(index).className.equals("conditionClose")) {
                        index++;
                        if (ob7()) {
                            return true;
                        }
                    }
                }

            } else if (ob3()) {
                return true;
            } else if (allTokens.get(index).className.equals("arrOp")) {
                index++;
                if (id_const()) {
                    if (allTokens.get(index).className.equals("arrCl")) {
                        index++;
                        if (ob9()) {
                            return true;
                        }
                    }
                }

            }

        }
        return true;
    }

    public boolean ob3() {
        if (allTokens.get(index).className.equals("equalOp") || allTokens.get(index).className.equals("assignmentOp") || allTokens.get(index).className.equals("mulDivMod") || allTokens.get(index).className.equals("addSub") || allTokens.get(index).className.equals("relationalOp") || allTokens.get(index).className.equals("orOp") || allTokens.get(index).className.equals("andOp") || allTokens.get(index).className.equals("paraSaperator") || allTokens.get(index).className.equals("incrementOp") || allTokens.get(index).className.equals("decrementOp")) {
            if (allTokens.get(index).className.equals("equalOp")) {
                index++;
                if (ob4()) {
                    return true;
                }
            } else if (allTokens.get(index).className.equals("assignmentOp")) {
                index++;
                if (ob4()) {
                    return true;
                }
            } else if (allTokens.get(index).className.equals("decrementOp") || allTokens.get(index).className.equals("incrementOp")) {
                index++;
                if (Tp()) {
                    if (Ep()) {
                        if (REp()) {
                            if (ANDEp()) {
                                if (OREp()) {
                                    return true;
                                }
                            }
                        }
                    }

                }
            } else if (Tp()) {
                if (Ep()) {
                    if (REp()) {
                        if (ANDEp()) {
                            if (OREp()) {
                                return true;
                            }

                        }
                    }

                }
            }
        }
        return false;
    }

    public boolean ob4() {
        if (allTokens.get(index).className.equals("Identifiers") || allTokens.get(index).className.equals("conditionOpen") || allTokens.get(index).className.equals("numberConstant") || allTokens.get(index).className.equals("wordConstant") || allTokens.get(index).className.equals("letterConstant") || allTokens.get(index).className.equals("deciConstant") || allTokens.get(index).className.equals("incrementOp") || allTokens.get(index).className.equals("decrementOp") || allTokens.get(index).className.equals("notOp")) {
            if (constant()) {
                if (ob7()) {
                    return true;
                }
            } else if (allTokens.get(index).className.equals("Identifiers")) {
                index++;
                if (ob6()) {
                    return true;
                }
            } else if (allTokens.get(index).className.equals("conditionOpen")) {
                index++;
                if (ORE()) {
                    if (allTokens.get(index).className.equals("conditionClose")) {
                        index++;
                        if (Tp()) {
                            if (Ep()) {
                                if (REp()) {
                                    if (ANDEp()) {
                                        if (OREp()) {
                                            return true;
                                        }
                                    }
                                }
                            }

                        }
                    }
                }
            } else if (allTokens.get(index).className.equals("incrementOp") || allTokens.get(index).className.equals("decrementOp")) {
                index++;
                if (allTokens.get(index).className.equals("Identifiers")) {
                    index++;

                    if (Tp()) {
                        if (Ep()) {
                            if (REp()) {
                                if (ANDEp()) {
                                    if (OREp()) {
                                        return true;
                                    }
                                }

                            }

                        }
                    }
                }
            } else if (allTokens.get(index).className.equals("notOp")) {
                if (F2()) {
                    if (ob7()) {
                        return true;
                    }
                }
            }

        }
        return false;
    }

    public boolean ob5() {
        if (allTokens.get(index).className.equals("classSeperator") || allTokens.get(index).className.equals("arrOp") || allTokens.get(index).className.equals("equalOp") || allTokens.get(index).className.equals("assignmentOp") || allTokens.get(index).className.equals("mulDivMod") || allTokens.get(index).className.equals("addSub") || allTokens.get(index).className.equals("relationalOp") || allTokens.get(index).className.equals("orOp") || allTokens.get(index).className.equals("andOp") || allTokens.get(index).className.equals("terminator") || allTokens.get(index).className.equals("paraSaperator") || allTokens.get(index).className.equals("incrementOp") || allTokens.get(index).className.equals("decrementOp")) {
            if (ob1()) {
                return true;
            } else if (ob3()) {
                return true;
            }
        }
        return true;
    }

    public boolean ob6() {
        if (allTokens.get(index).className.equals("classSeperator") || allTokens.get(index).className.equals("arrOp") || allTokens.get(index).className.equals("equalOp") || allTokens.get(index).className.equals("assignmentOp") || allTokens.get(index).className.equals("mulDivMod") || allTokens.get(index).className.equals("addSub") || allTokens.get(index).className.equals("relationalOp") || allTokens.get(index).className.equals("orOp") || allTokens.get(index).className.equals("andOp") || allTokens.get(index).className.equals("paraSaperator") || allTokens.get(index).className.equals("incrementOp") || allTokens.get(index).className.equals("decrementOp")) {
            if (ob5()) {
                return true;
            }
        }
        return false;
    }

    public boolean ob7() {
        if (allTokens.get(index).className.equals("mulDivMod") || allTokens.get(index).className.equals("addSub") || allTokens.get(index).className.equals("relationalOp") || allTokens.get(index).className.equals("orOp") || allTokens.get(index).className.equals("andOp") || allTokens.get(index).className.equals("paraSaperator")) {
            if (Tp()) {
                if (Ep()) {
                    if (REp()) {
                        if (ANDEp()) {
                            if (OREp()) {
                                return true;
                            }
                        }
                    }
                }

            }
        }
        return true;
    }

    public boolean ob8() {
        if (allTokens.get(index).className.equals("classSeperator") || allTokens.get(index).className.equals("equalOp") || allTokens.get(index).className.equals("assignmentOp") || allTokens.get(index).className.equals("mulDivMod") || allTokens.get(index).className.equals("addSub") || allTokens.get(index).className.equals("relationalOp") || allTokens.get(index).className.equals("orOp") || allTokens.get(index).className.equals("andOp") || allTokens.get(index).className.equals("paraSaperator") || allTokens.get(index).className.equals("incrementOp") || allTokens.get(index).className.equals("decrementOp")) {
            if (allTokens.get(index).className.equals("classSeperator")) {
                index++;
                if (allTokens.get(index).className.equals("Identifiers")) {
                    index++;
                    if (ob2()) {
                        return true;
                    }
                }

            } else if (ob3()) {
                return true;
            }

        }
        return true;
    }

    public boolean ob9() {
        if (allTokens.get(index).className.equals("equalOp") || allTokens.get(index).className.equals("assignmentOp") || allTokens.get(index).className.equals("mulDivMod") || allTokens.get(index).className.equals("addSub") || allTokens.get(index).className.equals("relationalOp") || allTokens.get(index).className.equals("orOp") || allTokens.get(index).className.equals("andOp") || allTokens.get(index).className.equals("paraSaperator") || allTokens.get(index).className.equals("incrementOp") || allTokens.get(index).className.equals("decrementOp")) {
            if (ob3()) {
                return true;

            }

        }
        return true;
    }

    //Ending of Object_Creation
    //Starting of Assignment Statement
    public boolean assign_st() {
        if (allTokens.get(index).className.equals("Identifiers")) {
            index++;
            if (assigP()) {
                if (asg_1()) {
                    return true;
                }
            }
        }

        return false;
    }

    public boolean asg_1() {
        if (allTokens.get(index).className.equals("equalOp") || allTokens.get(index).className.equals("assignmentOp")) {
            if (allTokens.get(index).className.equals("equalOp") || allTokens.get(index).className.equals("assignmentOp")) {
                if (allTokens.get(index).className.equals("equalOp")) {
                    index++;
                    if (asg_2()) {
                        return true;
                    }
                } else if (allTokens.get(index).className.equals("assignmentOp")) {
                    index++;
                    if (asg_2()) {
                        return true;
                    }
                }

            }
        }
        return false;
    }

    public boolean asg_2() {
        if (allTokens.get(index).className.equals("Identifiers") || allTokens.get(index).className.equals("conditionOpen") || allTokens.get(index).className.equals("incrementOp") || allTokens.get(index).className.equals("decrementOp") || allTokens.get(index).className.equals("deciConstant") || allTokens.get(index).className.equals("letterConstant") || allTokens.get(index).className.equals("wordConstant") || allTokens.get(index).className.equals("numberConstant") || allTokens.get(index).className.equals("notOp")) {

            if (allTokens.get(index).className.equals("Identifiers")) {

                this.variableName = allTokens.get(index).value;
                this.variableScope = this.currentScope;
                type = lookUpVariable(this.variableName, this.variableScope, this.className);
                t2 = type;
                if (type.equals("")) {
                    System.out.println("'" + this.variableName + "' Variable Undeclarad at Line Number = " + allTokens.get(index).lineNo);
                }
                eType = Comp(t1, t2, op);
                if (eType.equals("")) {
                    System.out.println("Type Mis-matched at Line No: " + allTokens.get(index).lineNo);
                } else if (Comp(t1, eType, ope).equals("")) {
                    System.out.println("Type Mis-matched at Line No: " + allTokens.get(index).lineNo);

                } else {
                    this.t1 = Comp(t1, t2, op);
                }
                index++;
                if (asg_4()) {
                    return true;
                }
            } else if (allTokens.get(index).className.equals("incrementOp") || allTokens.get(index).className.equals("decrementOp")) {
                index++;
                if (allTokens.get(index).className.equals("Identifiers")) {
                    index++;
                    if (asg_5()) {
                        return true;
                    }

                }

            } else if (constant()) {
                //   System.out.println("HI1");
                this.eType = Comp(t1, t2, ope);
                if (eType.equals("")) {
                    System.out.println("Type Mis-matched at Line No: " + allTokens.get(index).lineNo);
                } else {
                    this.eType1 = Comp(t1, t2, ope);
                    t1 = eType1;
                }

                if (Tp()) {
                    if (Ep()) {
                        if (REp()) {
                            if (ANDEp()) {
                                if (OREp()) {

                                    if (allTokens.get(index).className.equals("terminator")) {

                                        index++;
                                        return true;
                                    }
                                }

                            }

                        }
                    }
                }
            }// else if (allTokens.get(index).className.equals("Identifiers")) { //check this one
            //  index++;
            else if (Tp()) {
                if (Ep()) {
                    if (REp()) {
                        if (ANDEp()) {
                            if (OREp()) {
                                if (allTokens.get(index).className.equals("terminator")) {
                                    index++;
                                    return true;
                                }
                            }

                        }

                    }
                }
            } //  } 
            else if (allTokens.get(index).className.equals("conditionOpen")) {
                index++;
                if (ORE()) {
                    if (allTokens.get(index).className.equals("conditionClose")) {
                        index++;
                        if (Tp()) {
                            if (Ep()) {
                                if (REp()) {
                                    if (ANDEp()) {
                                        if (OREp()) {
                                            if (allTokens.get(index).className.equals("terminator")) {
                                                index++;
                                                return true;
                                            }

                                        }

                                    }

                                }
                            }
                        }

                    }

                }
            } else if (allTokens.get(index).className.equals("notOp")) {
                index++;
                if (F2()) {
                    if (asg_5()) {
                        return true;
                    }
                }

            }

        }

        return false;
    }

    public boolean asg_3() {
        if (allTokens.get(index).className.equals("terminator") || allTokens.get(index).className.equals("EqualOp") || allTokens.get(index).className.equals("assignmentOp")) {
            if (allTokens.get(index).className.equals("terminator")) {
                index++;
                return true;
            } else if (asg_1()) {
                {
                    return true;
                }

            }
        }
        return false;
    }

    public boolean asg_4() {
        if (allTokens.get(index).className.equals("classSeperator") || allTokens.get(index).className.equals("conditionOpen") || allTokens.get(index).className.equals("arrOp") || allTokens.get(index).className.equals("equalOp") || allTokens.get(index).className.equals("assignmentOp") || allTokens.get(index).className.equals("incrementOp") || allTokens.get(index).className.equals("decrementOp") || allTokens.get(index).className.equals("addSub") || allTokens.get(index).className.equals("mulDivMod") || allTokens.get(index).className.equals("relationalOp") || allTokens.get(index).className.equals("andOp") || allTokens.get(index).className.equals("orOp") || allTokens.get(index).className.equals("terminator")) {
            if (ob1()) {
                if (allTokens.get(index).className.equals("terminator")) {
                    index++;
                    return true;
                }
            } else if (allTokens.get(index).className.equals("terminator")) {
                index++;
                return true;
            } else if (asg_1()) {
                {
                    return true;
                }
            } else if (allTokens.get(index).className.equals("conditionOpen")) {//new one is added
                index++;
                if (args()) {
                    if (allTokens.get(index).className.equals("conditionClose")) {
                        index++;
                        if (Tp()) {
                            if (Ep()) {
                                if (REp()) {
                                    if (ANDEp()) {
                                        if (OREp()) {
                                            if (allTokens.get(index).className.equals("terminator")) {
                                                index++;
                                                return true;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }

            } else if (Tp()) {
                if (Ep()) {
                    if (REp()) {
                        if (ANDEp()) {
                            if (OREp()) {
                                if (allTokens.get(index).className.equals("terminator")) {
                                    index++;
                                    return true;
                                }
                            }

                        }

                    }
                }
            } else if (allTokens.get(index).className.equals("decrementOp") || allTokens.get(index).className.equals("incrementOp")) {
                if (Tp()) {
                    if (Ep()) {
                        if (REp()) {
                            if (ANDEp()) {
                                if (OREp()) {
                                    if (allTokens.get(index).className.equals("terminator")) {
                                        index++;
                                        return true;
                                    }
                                }

                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean asg_5() {
        if (allTokens.get(index).className.equals("addSub") || allTokens.get(index).className.equals("mulDivMod") || allTokens.get(index).className.equals("relationalOp") || allTokens.get(index).className.equals("andOp") || allTokens.get(index).className.equals("orOp") || allTokens.get(index).className.equals("terminator")) {
            if (Tp()) {
                if (Ep()) {
                    if (REp()) {
                        if (ANDEp()) {
                            if (OREp()) {
                                if (allTokens.get(index).className.equals("terminator")) {
                                    index++;
                                    return true;
                                }

                            }

                        }
                    }
                }
            }
        }
        return false;
    }

    // Ending of Assignment Statement
    //Constant CFG
    public boolean constant() {
        if (allTokens.get(index).className.equals("numberConstant") || allTokens.get(index).className.equals("deciConstant") || allTokens.get(index).className.equals("letterConstant") || allTokens.get(index).className.equals("wordConstant") || allTokens.get(index).value.equals("true") || allTokens.get(index).value.equals("false")) {
            if (allTokens.get(index).className.equals("numberConstant") || allTokens.get(index).className.equals("letterConstant") || allTokens.get(index).className.equals("deciConstant") || allTokens.get(index).className.equals("wordConstant") || allTokens.get(index).value.equals("true") || allTokens.get(index).value.equals("false")) {
                if (allTokens.get(index).className.equals("numberConstant")) {
                    this.t2 = "num";
                } else if (allTokens.get(index).className.equals("letterConstant")) {
                    this.t2 = "letter";
                } else if (allTokens.get(index).className.equals("deciConstant")) {
                    this.t2 = "deci";
                } else if (allTokens.get(index).className.equals("wordConstant")) {
                    this.t2 = "word";
                }

                index++;
                return true;
            }

        }
        return false;

    }

    //STARTING OF ARRAY CFG
    public boolean array() {//selection set problem

        if (A_M2()) {
            if (allTokens.get(index).className.equals("datatype")) {
                index++;
                if (allTokens.get(index).className.equals("Identifiers")) {
                    index++;
                    if (allTokens.get(index).className.equals("arrOp")) {
                        index++;
                        if (allTokens.get(index).className.equals("arrCl")) {
                            index++;
                            if (array1()) {
                                if (array2()) {
                                    return true;
                                }
                            }

                        }
                    }
                }
            }
        } else if (allTokens.get(index).className.equals("Identifiers")) {
            index++;
            if (allTokens.get(index).className.equals("arrOp")) {
                index++;
                if (constant()) {
                    if (allTokens.get(index).className.equals("arrCl")) {
                        index++;
                        if (array1()) {
                            if (array2()) {
                                return true;
                            }
                        }
                    }
                }
            }
        }

        return false;
    }

    public boolean A_M2() {
        if (allTokens.get(index).className.equals("access_mod")) {
            if (allTokens.get(index).className.equals("access_mod")) {
                index++;
                return true;
            }
        }
        return true;
    }

    public boolean array1() {
        if (allTokens.get(index).className.equals("equalOp")) {
            if (allTokens.get(index).className.equals("equalOp")) {
                index++;
                if (array3()) {
                    return true;
                }
            }
        }
        return true;
    }

    public boolean array2() {
        if (allTokens.get(index).className.equals("paraSaperator") || allTokens.get(index).className.equals("terminator")) {
            if (allTokens.get(index).className.equals("terminator")) {
                index++;
                return true;
            } else if (allTokens.get(index).className.equals("paraSaperator")) {
                index++;
                if (allTokens.get(index).className.equals("Identifiers")) {
                    index++;
                    if (allTokens.get(index).className.equals("arrOp")) {
                        index++;
                        if (allTokens.get(index).className.equals("arrCl")) {
                            index++;
                            if (array1()) {
                                if (array2()) {
                                    return true;
                                }
                            }

                        }
                    }
                }
            }
        }

        return false;
    }

    public boolean array3() {
        if (allTokens.get(index).className.equals("Identifiers") || allTokens.get(index).className.equals("make") || allTokens.get(index).className.equals("arrOp")) {
            if (allTokens.get(index).className.equals("Identifiers")) {
                index++;
                return true;
            } else if (allTokens.get(index).className.equals("make")) {
                index++;
                if (allTokens.get(index).className.equals("datatype")) {
                    cType1 = allTokens.get(index).value;
                    index++;
                    if (allTokens.get(index).className.equals("arrOp")) {
                        this.cType1 = this.cType1.concat(allTokens.get(index).value);
                        if (!cType.equals(cType1)) {
                            System.out.println("Array Type Mis-matched! at Line No: " + allTokens.get(index).lineNo);
                        } else {
                            this.cType = this.cType.concat(allTokens.get(index).value);
                            //   System.out.println(cType);
                        }
                        index++;
                        if (constant()) {
                            if (!t2.equals("num") || t2.equals("")) {
                                System.out.println("Size Undefined at Line No: " + allTokens.get(index).lineNo);
                            }
                            if (allTokens.get(index).className.equals("arrCl")) {
                                index++;

                                return true;
                            }

                        }

                    }

                }
            } else if (array4()) {
                return true;
            }
        }
        return false;
    }

    public boolean array4() {
        if (allTokens.get(index).className.equals("arrOp")) {
            if (allTokens.get(index).className.equals("arrOp")) {
                index++;
                if (arrayconstant()) {
                    if (allTokens.get(index).className.equals("arrCl")) {
                        index++;

                        return true;
                    }

                }
            }

        } else if (constant()) {
            return true;
        }
        return false;
    }

    public boolean arrayconstant() {
        if (allTokens.get(index).className.equals("wordConstant") || allTokens.get(index).className.equals("letterConstant") || allTokens.get(index).className.equals("numberConstant") || allTokens.get(index).className.equals("deciConstant") || allTokens.get(index).className.equals("arrCl")) {
            if (constant()) {
                if (arrayconstant()) {
                    if (multiconstant()) {

                        return true;
                    }

                }
            }

        }
        return true;
    }

    public boolean multiconstant() {
        if (allTokens.get(index).className.equals("paraSaperator")) {

            if (allTokens.get(index).className.equals("paraSaperator")) {
                index++;
                if (constant()) {
                    if (multiconstant()) {
                        return true;
                    }
                }
            }
        }
        return true;
    }

    //Ending of ARRAY CFG
    //OBJECT Array Starting CFG
    public boolean Objectarray() {
        if (allTokens.get(index).className.equals("Identifiers")) {
            if (allTokens.get(index).className.equals("Identifiers")) {
                index++;
                if (allTokens.get(index).className.equals("Identifiers")) {
                    index++;
                    if (allTokens.get(index).className.equals("arrOp")) {
                        index++;
                        if (allTokens.get(index).className.equals("arrCl")) {
                            index++;
                            if (obj_array1()) {
                                if ((obj_array2())) {
                                    return true;
                                }

                            }
                        }
                    }
                }
            }
        }

        return false;
    }

    public boolean obj_array1() {
        if (allTokens.get(index).className.equals("equalOp")) {
            if (allTokens.get(index).className.equals("equalOp")) {
                index++;
                if (obj_array3()) {
                    return true;
                }
            }
        }

        return true;
    }

    public boolean obj_array2() {

        if (allTokens.get(index).className.equals("paraSaperator") || allTokens.get(index).className.equals("terminator")) {
            if (allTokens.get(index).className.equals("terminator")) {
                index++;
                return true;

            } else if (allTokens.get(index).className.equals("paraSaperator")) {
                index++;
                if (allTokens.get(index).className.equals("Identifiers")) {
                    index++;
                    if (allTokens.get(index).className.equals("arrOp")) {
                        index++;
                        if (allTokens.get(index).className.equals("arrCl")) {
                            index++;
                            if (obj_array1()) {
                                if (obj_array2()) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }

        return false;
    }

    public boolean obj_array3() {
        if (allTokens.get(index).className.equals("Identifiers") || allTokens.get(index).className.equals("make")) {
            if (allTokens.get(index).className.equals("Identifiers")) {
                index++;
                return true;

            } else if (allTokens.get(index).className.equals("make")) {
                index++;
                if (allTokens.get(index).className.equals("Identifiers")) {
                    this.cType1 = allTokens.get(index).value;
                    if (!cType.equals(cType1)) {
                        System.out.println("Type Mis-matched at Line No: " + allTokens.get(index).lineNo);
                    }

                    index++;
                    if (allTokens.get(index).className.equals("arrOp")) {
                        index++;
                        if (constant()) {
                            if (allTokens.get(index).className.equals("arrCl")) {
                                index++;
                                return true;

                            }
                        }
                    }
                }
            }

        }

        return false;
    }

    //Starting of Array_Assign CFG
    public boolean array_assign() {

        if (allTokens.get(index).className.equals("Identifiers")) {
            if (allTokens.get(index).className.equals("Identifiers")) {
                index++;
                if (array_assign2()) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean array_assign2() {

        if (allTokens.get(index).className.equals("equalOp") || allTokens.get(index).className.equals("arrOp")) {
            if (allTokens.get(index).className.equals("equalOp")) {
                index++;
                if (allTokens.get(index).className.equals("make")) {
                    index++;
                    if (allTokens.get(index).className.equals("datatype")) {
                        index++;
                        if (allTokens.get(index).className.equals("arrOp")) {
                            index++;
                            if (constant()) {
                                if (allTokens.get(index).className.equals("arrCl")) {
                                    index++;
                                    if (allTokens.get(index).className.equals("terminator")) {
                                        index++;
                                        return true;
                                    }

                                }
                            }
                        }
                    }
                }
            }
        } else if (allTokens.get(index).className.equals("arrOp")) {
            if (constant()) {
                if (allTokens.get(index).className.equals("arrCl")) {
                    index++;
                    if (allTokens.get(index).className.equals("equalOp")) {
                        index++;
                        if (allTokens.get(index).className.equals("make")) {
                            index++;
                            if (allTokens.get(index).className.equals("Identifiers")) {
                                index++;
                                if (allTokens.get(index).className.equals("arrOp")) {
                                    index++;
                                    if (constant()) {
                                        if (allTokens.get(index).className.equals("arrCl")) {
                                            index++;
                                            return true;
                                        }
                                    }
                                }

                            }
                        }
                    }
                }
            }
        }

        return false;
    }

    //Ending of Array_Assign CFG
    //Starting of DEclaration2
    public boolean dec2() {
        if (allTokens.get(index).className.equals("datatype")) {
            if (allTokens.get(index).className.equals("datatype")) {
                this.variableType = allTokens.get(index).value;
                index++;
                if (allTokens.get(index).className.equals("Identifiers")) {
                    this.variableName = allTokens.get(index).value;
                    this.variableScope = this.currentScope;
                    this.AM = "";
                    this.TM = "";

                    this.type = lookUpVariable(this.variableName, this.variableScope, this.className);

                    if (type.equals("")) {

                        insertVariable(variableName, variableType, AM, TM, variableScope, className);

                    } else {

                        System.out.println("'" + this.variableName + "' Variable Redeclaration at Line Number = " + allTokens.get(index).lineNo);

                    }
                    index++;
                    if (init21()) {
                        if (list2()) {
                            return true;
                        }
                    }

                }
            }

        }
        return false;
    }

    public boolean init21() {
        if (allTokens.get(index).className.equals("equalOp")) {
            if (allTokens.get(index).className.equals("equalOp")) {
                index++;
                if (init22()) {//
                    return true;
                }

            }

        }
        return false;

    }

    public boolean init22() {
        if (allTokens.get(index).className.equals("Identifiers") || allTokens.get(index).className.equals("numberConstant")) {
            if (allTokens.get(index).className.equals("Identifiers")) {
                index++;
                if (init23()) {//
                    return true;
                }
            } else if (constant()) {
                if (Tp()) {
                    if (Ep()) {
                        if (REp()) {
                            if (ANDEp()) {
                                if (OREp()) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean init23() {
        if (allTokens.get(index).className.equals("Identifiers") || allTokens.get(index).className.equals("numberConstant") || allTokens.get(index).className.equals("equalOp") || allTokens.get(index).className.equals("classSeperator") || allTokens.get(index).className.equals("decrementOp") || allTokens.get(index).className.equals("arrOp") || allTokens.get(index).className.equals("relationalOp") || allTokens.get(index).className.equals("andOp") || allTokens.get(index).value.equals("orOp") || allTokens.get(index).value.equals("incrementOp") || allTokens.get(index).value.equals("paraSaperator") || allTokens.get(index).value.equals("addSub") || allTokens.get(index).value.equals("mulDivMod")) {
            if (init21()) {
                return true;
            } else if (F1()) {
                if (Tp()) {
                    if (Ep()) {
                        if (REp()) {
                            if (ANDEp()) {
                                if (OREp()) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }

        return false;
    }

    public boolean list2() {
        if (allTokens.get(index).className.equals("paraSaperator")) {
            if (allTokens.get(index).className.equals("paraSaperator")) {
                index++;
                if (allTokens.get(index).className.equals("Identifiers")) {
                    index++;
                    if (init21()) {
                        if (list2()) {
                            return true;
                        }
                    }
                }
            }
        }

        return true;
    }
    //Ending of Declaration2

    //Starting of Assignment_Statement2
    public boolean assign_st2() {
        if (allTokens.get(index).className.equals("Identifiers")) {

            this.variableName = allTokens.get(index).value;
            this.variableScope = this.currentScope;
            type = lookUpVariable(this.variableName, this.variableScope, this.className);
            if (type.equals("")) {
                // not declared
                System.out.println("'" + this.variableName + "' Variable Undeclerad at Line Number = " + allTokens.get(index).lineNo);

            }
            index++;
            if (assigP()) {
                if (asg_12()) {//
                    return true;
                }
            }
        }

        return false;
    }

    public boolean asg_12() {
        if (allTokens.get(index).className.equals("equalOp") || allTokens.get(index).className.equals("assignmentOp")) {
            if (allTokens.get(index).className.equals("equalOp") || allTokens.get(index).className.equals("assignmentOp")) {
                if (allTokens.get(index).className.equals("equalOp")) {
                    index++;
                    if (asg_22()) {//
                        return true;
                    }
                } else if (allTokens.get(index).className.equals("assignmentOp")) {
                    index++;
                    if (asg_22()) {
                        return true;
                    }
                }

            }
        }
        return false;
    }

    public boolean asg_22() {
        if (allTokens.get(index).className.equals("Identifiers") || allTokens.get(index).className.equals("deciConstant") || allTokens.get(index).className.equals("letterConstant") || allTokens.get(index).className.equals("wordConstant") || allTokens.get(index).className.equals("numberConstant")) {
            if (allTokens.get(index).className.equals("Identifiers")) {
                index++;
                if (asg_42()) {
                    return true;
                }
            } else if (constant()) {

                if (Tp()) {
                    if (Ep()) {
                        if (REp()) {
                            if (ANDEp()) {
                                if (OREp()) {

                                    return true;

                                }

                            }

                        }
                    }
                }
            }
        }

        return false;
    }

    public boolean asg_42() {
        if (allTokens.get(index).className.equals("equalOp") || allTokens.get(index).className.equals("assignmentOp") || allTokens.get(index).className.equals("classSeperator") || allTokens.get(index).className.equals("decrementOp") || allTokens.get(index).className.equals("arrOp") || allTokens.get(index).className.equals("relationalOp") || allTokens.get(index).className.equals("andOp") || allTokens.get(index).value.equals("orOp") || allTokens.get(index).value.equals("incrementOp") || allTokens.get(index).value.equals("paraSaperator") || allTokens.get(index).value.equals("addSub") || allTokens.get(index).value.equals("mulDivMod")) {

            if (allTokens.get(index).className.equals("equalOp") || allTokens.get(index).className.equals("assignmentOp")) {
                if (asg_12()) {
                    return true;

                }
            } else if (F1()) {
                if (Tp()) {
                    if (Ep()) {
                        if (REp()) {
                            if (ANDEp()) {
                                if (OREp()) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    //Ending of Assignment_statement2
    // EAssignP CFG
    public boolean assigP() {

        if (allTokens.get(index).className.equals("conditionOpen") || allTokens.get(index).className.equals("arrOp") || allTokens.get(index).className.equals("classSeperator")) {
            if (allTokens.get(index).className.equals("classSeperator")) {
                index++;
                if (allTokens.get(index).className.equals("Identifiers")) {
                    index++;
                    if (assigPp()) {
                        return true;
                    }
                }
            } else if (allTokens.get(index).className.equals("arrOp")) {
                index++;
                if (constant()) {
                    if (allTokens.get(index).className.equals("arrCl")) {
                        index++;
                        if (assigPpp()) {
                            return true;
                        }
                    }
                }
            } else if (allTokens.get(index).className.equals("conditionOpen")) {
                index++;
                if (args()) {
                    if (allTokens.get(index).className.equals("conditionClose")) {
                        index++;
                        return true;
                    }

                }
            }

        }
        return true;
    }

    public boolean assigPp() {

        if (allTokens.get(index).className.equals("arrOp") || allTokens.get(index).className.equals("conditionOpen")) {

            if (allTokens.get(index).className.equals("arrOp")) {
                index++;
                if (constant()) {
                    if (allTokens.get(index).className.equals("arrCl")) {
                        index++;
                        return true;

                    }
                }

            } else if (allTokens.get(index).className.equals("conditionOpen")) {
                index++;
                if (args()) {
                    if (allTokens.get(index).className.equals("conditionClose")) {
                        index++;
                        return true;
                    }
                }
            }
        }
        return true;
    }

    public boolean assigPpp() {

        if (allTokens.get(index).className.equals("classSeperator")) {
            if (allTokens.get(index).className.equals("classSeperator")) {
                index++;
                if (allTokens.get(index).className.equals("Identifiers")) {
                    index++;
                    if (assigPp()) {
                        return true;
                    }
                }
            }
        }
        return true;
    }

    //Starting of Expression
    public boolean F2() {
        if (allTokens.get(index).className.equals("Identifiers") || allTokens.get(index).className.equals("conditionOpen") || allTokens.get(index).className.equals("numberConstant") || allTokens.get(index).className.equals("letterConstant") || allTokens.get(index).className.equals("wordConstant") || allTokens.get(index).className.equals("deciConstant")) {
            if (allTokens.get(index).className.equals("Identifiers")) {
                index++;
                return true;
            } else if (allTokens.get(index).className.equals("conditionOpen")) {
                index++;
                if (ORE()) {
                    if (allTokens.get(index).className.equals("conditionClose")) {
                        index++;
                        return true;
                    }

                }

            } else if (constant()) {
                return true;
            }
        }
        return false;
    }

    public boolean F1() {
        if (allTokens.get(index).className.equals("decrementOp") || allTokens.get(index).className.equals("incrementOp") || allTokens.get(index).className.equals("conditionOpen") || allTokens.get(index).className.equals("arrOp") || allTokens.get(index).className.equals("classSeperator")) {

            if (allTokens.get(index).className.equals("decrementOp") || allTokens.get(index).className.equals("incrementOp")) {
                op = allTokens.get(index).value;
                index++;
                return true;
            } else if (allTokens.get(index).className.equals("conditionOpen") || allTokens.get(index).className.equals("arrOp") || allTokens.get(index).className.equals("classSeperator")) {
                if (assigP()) {//
                    return true;

                }
            }
        } else if (allTokens.get(index).className.equals("conditionClose") || allTokens.get(index).className.equals("mulDivMod") || allTokens.get(index).className.equals("addSub") || allTokens.get(index).className.equals("relationalOp") || allTokens.get(index).className.equals("andOp") || allTokens.get(index).className.equals("orOp") || allTokens.get(index).className.equals("terminator") || allTokens.get(index).className.equals("paraSaperator")) {

            return true;
        }
        return false;
    }

    public boolean F() {

        if (allTokens.get(index).className.equals("Identifiers") || allTokens.get(index).className.equals("incrementOp") || allTokens.get(index).className.equals("decrementOp") || allTokens.get(index).className.equals("conditionOpen") || allTokens.get(index).className.equals("notOp") || allTokens.get(index).className.equals("letterConstant") || allTokens.get(index).className.equals("numberConstant") || allTokens.get(index).className.equals("wordConstant") || allTokens.get(index).className.equals("deciConstant")) {
            if (allTokens.get(index).className.equals("Identifiers")) {
                type = lookUpVariable(this.variableName, this.variableScope, this.className);
                t2 = type;
                if (type.equals("")) {
                    System.out.println("'" + this.variableName + "' Variable Undeclarad at Line Number = " + allTokens.get(index).lineNo);
                }
                eType = Comp(t1, t2, op);
                if (eType.equals("")) {
                    System.out.println("Type Mis-matched at Line No: " + allTokens.get(index).lineNo);
                } else if (Comp(t1, eType, ope).equals("")) {
                    System.out.println("Type Mis-matched at Line No: " + allTokens.get(index).lineNo);

                } else {
                    this.t1 = Comp(t1, t2, op);
                }
                index++;
                if (F1()) {
                    return true;
                }
            } else if (constant()) {//

                eType = Comp(t1, t2, op);
                if (eType.equals("")) {
                    System.out.println("Type Mis-matched at Line No: " + allTokens.get(index).lineNo);
                } else if (Comp(t1, eType, ope).equals("")) {
                    System.out.println("Type Mis-matched at Line No: " + allTokens.get(index).lineNo);

                } else {
                    this.t1 = Comp(t1, t2, op);
                }
                return true;

            } else if (allTokens.get(index).className.equals("conditionOpen")) {
                index++;
                if (ORE()) {
                    if (allTokens.get(index).className.equals("conditionClose")) {
                        index++;
                        return true;
                    }
                }

            } else if (allTokens.get(index).className.equals("notOp")) {
                index++;
                if (F2()) {
                    return true;
                }

            } else if (allTokens.get(index).className.equals("incrementOp") || allTokens.get(index).className.equals("decrementOp")) {
                index++;
                if (allTokens.get(index).className.equals("Identifiers")) {
                    index++;
                    return true;
                }
            }
        }
        return false;
    }

    public boolean Tp() {
//no selection set
        if (allTokens.get(index).className.equals("mulDivMod")) {
            op = allTokens.get(index).value;
            index++;
            if (F()) {//
                if (Tp()) {
                    return true;
                }

            }
        } else if (allTokens.get(index).className.equals("loopPunc") || allTokens.get(index).className.equals("conditionClose") || allTokens.get(index).className.equals("addSub") || allTokens.get(index).className.equals("relationalOp") || allTokens.get(index).className.equals("andOp") || allTokens.get(index).className.equals("orOp") || allTokens.get(index).className.equals("terminator") || allTokens.get(index).className.equals("paraSaperator")) {
            return true;
        }
        return false;
    }

    public boolean T() {
        if (allTokens.get(index).className.equals("Identifiers") || allTokens.get(index).className.equals("incrementOp") || allTokens.get(index).className.equals("decrementOp") || allTokens.get(index).className.equals("conditionOpen") || allTokens.get(index).className.equals("notOp") || allTokens.get(index).className.equals("letterConstant") || allTokens.get(index).className.equals("numberConstant") || allTokens.get(index).className.equals("deciConstant") || allTokens.get(index).className.equals("wordConstant")) {
            if (allTokens.get(index).className.equals("Identifiers")) {
                this.variableName = allTokens.get(index).value;
                this.variableScope = this.currentScope;
                type = lookUpVariable(this.variableName, this.variableScope, this.className);
                t2 = type;
                if (type.equals("")) {
                    System.out.println("'" + this.variableName + "' Variable Undeclarad at Line Number = " + allTokens.get(index).lineNo);
                }
                eType = Comp(t1, t2, op);
                if (eType.equals("")) {
                    System.out.println("Type Mis-matched at Line No: " + allTokens.get(index).lineNo);
                } else if (Comp(t1, eType, ope).equals("")) {
                    System.out.println("Type Mis-matched at Line No: " + allTokens.get(index).lineNo);

                } else {
                    this.t1 = Comp(t1, t2, op);
                }
                // System.out.println("HI3");

                index++;
                if (F1()) {
                    if (Tp()) {
                        return true;
                    }

                }
            } else if (constant()) {
                eType = Comp(t1, t2, op);
                // System.out.println(eType);
                if (eType.equals("")) {
                    System.out.println("Type Mis-matched at Line No: " + allTokens.get(index).lineNo);
                } else if (Comp(t1, eType, ope).equals("")) {
                    System.out.println("Type Mis-matched at Line No: " + allTokens.get(index).lineNo);

                } else {
                    this.t1 = Comp(t1, t2, op);
                }
                if (Tp()) {//

                    return true;
                }
            } else if (allTokens.get(index).className.equals("conditionOpen")) {
                index++;
                if (ORE()) {
                    if (allTokens.get(index).className.equals("conditionClose")) {
                        index++;
                        if (Tp()) {
                            return true;
                        }
                    }
                }

            } else if (allTokens.get(index).className.equals("incrementOp") || allTokens.get(index).className.equals("decrementOp")) {
                index++;
                if (allTokens.get(index).className.equals("Identifiers")) {
                    index++;
                    if (Tp()) {
                        return true;
                    }
                }
            } else if (allTokens.get(index).className.equals("notOp")) {
                index++;
                if (F2()) {
                    if (Tp()) {
                        return true;
                    }
                }
            }

        }
        return false;
    }

    public boolean Ep() {

//no selection set
        if (allTokens.get(index).className.equals("addSub")) {
            //   System.out.println("HI2");
            op = allTokens.get(index).value;
            index++;
            if (T()) {
                if (Ep()) {//
                    return true;
                }

            }
        } else if (allTokens.get(index).className.equals("loopPunc") || allTokens.get(index).className.equals("conditionClose") || allTokens.get(index).className.equals("relationalOp") || allTokens.get(index).className.equals("andOp") || allTokens.get(index).className.equals("orOp") || allTokens.get(index).className.equals("terminator") || allTokens.get(index).className.equals("paraSaperator")) {
            return true;
        }
        return false;
    }

    public boolean E() {
        {
            if (allTokens.get(index).className.equals("Identifiers") || allTokens.get(index).className.equals("incrementOp") || allTokens.get(index).className.equals("decrementOp") || allTokens.get(index).className.equals("conditionOpen") || allTokens.get(index).className.equals("notOp") || allTokens.get(index).className.equals("letterConstant") || allTokens.get(index).className.equals("numberConstant") || allTokens.get(index).className.equals("deciConstant") || allTokens.get(index).className.equals("wordConstant")) {
                if (allTokens.get(index).className.equals("Identifiers")) {
                    index++;
                    if (F1()) {
                        if (Tp()) {
                            if (Ep()) {
                                return true;
                            }
                        }
                    }
                } else if (constant()) {
                    if (Tp()) {
                        if (Ep()) {
                            return true;
                        }
                    }
                } else if (allTokens.get(index).className.equals("conditionOpen")) {
                    index++;
                    if (ORE()) {
                        if (allTokens.get(index).className.equals("conditionClose")) {
                            index++;
                            if (Tp()) {
                                if (Ep()) {
                                    return true;
                                }
                            }
                        }
                    }

                } else if (allTokens.get(index).className.equals("incrementOp") || allTokens.get(index).className.equals("decrementOp")) {
                    index++;
                    if (allTokens.get(index).className.equals("Identifiers")) {
                        index++;
                        if (Tp()) {
                            if (Ep()) {
                                return true;
                            }
                        }
                    }
                } else if (allTokens.get(index).className.equals("notOp")) {
                    index++;
                    if (F2()) {
                        if (Tp()) {
                            if (Ep()) {
                                return true;
                            }
                        }
                    }
                }

            }
            return false;
        }

    }

    public boolean REp() {

//no selection set
        if (allTokens.get(index).className.equals("relationalOp")) {
            index++;
            if (E()) {
                if (REp()) {
                    return true;
                }

            }
        } else if (allTokens.get(index).className.equals("loopPunc") || allTokens.get(index).className.equals("conditionClose") || allTokens.get(index).className.equals("andOp") || allTokens.get(index).className.equals("orOp") || allTokens.get(index).className.equals("terminator") || allTokens.get(index).className.equals("paraSaperator")) {
            return true;
        }
        return false;
    }

    public boolean RE() {
        if (allTokens.get(index).className.equals("Identifiers") || allTokens.get(index).className.equals("incrementOp") || allTokens.get(index).className.equals("decrementOp") || allTokens.get(index).className.equals("conditionOpen") || allTokens.get(index).className.equals("notOp") || allTokens.get(index).className.equals("letterConstant") || allTokens.get(index).className.equals("numberConstant") || allTokens.get(index).className.equals("deciConstant") || allTokens.get(index).className.equals("wordConstant")) {
            if (allTokens.get(index).className.equals("Identifiers")) {
                index++;
                if (F1()) {
                    if (Tp()) {
                        if (Ep()) {
                            if (REp()) {
                                return true;
                            }
                        }
                    }
                }
            } else if (constant()) {
                if (Tp()) {
                    if (Ep()) {
                        if (REp()) {
                            return true;
                        }
                    }
                }
            } else if (allTokens.get(index).className.equals("conditionOpen")) {
                index++;
                if (ORE()) {
                    if (allTokens.get(index).className.equals("conditionClose")) {
                        index++;
                        if (Tp()) {
                            if (Ep()) {
                                if (REp()) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            } else if (allTokens.get(index).className.equals("incrementOp") || allTokens.get(index).className.equals("decrementOp")) {
                index++;
                if (allTokens.get(index).className.equals("Identifiers")) {
                    index++;
                    if (Tp()) {
                        if (Ep()) {
                            if (REp()) {
                                return true;
                            }
                        }
                    }
                }
            } else if (allTokens.get(index).className.equals("notOp")) {
                index++;
                if (F2()) {

                    if (Tp()) {
                        if (Ep()) {
                            if (REp()) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;

    }

    public boolean ANDEp() {

//no selection set
        if (allTokens.get(index).className.equals("andOp")) {
            index++;
            if (RE()) {
                if (ANDEp()) {
                    return true;
                }

            }
        } else if (allTokens.get(index).className.equals("loopPunc") || allTokens.get(index).className.equals("conditionClose") || allTokens.get(index).className.equals("orOp") || allTokens.get(index).className.equals("terminator") || allTokens.get(index).className.equals("paraSaperator")) {
            return true;
        }
        return false;
    }

    public boolean ANDE() {
        if (allTokens.get(index).className.equals("Identifiers") || allTokens.get(index).className.equals("incrementOp") || allTokens.get(index).className.equals("decrementOp") || allTokens.get(index).className.equals("conditionOpen") || allTokens.get(index).className.equals("notOp") || allTokens.get(index).className.equals("letterConstant") || allTokens.get(index).className.equals("numberConstant") || allTokens.get(index).className.equals("deciConstant") || allTokens.get(index).className.equals("wordConstant")) {
            if (allTokens.get(index).className.equals("Identifiers")) {
                index++;
                if (F1()) {
                    if (Tp()) {
                        if (Ep()) {
                            if (REp()) {
                                if (ANDEp()) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            } else if (constant()) {
                if (Tp()) {
                    if (Ep()) {
                        if (REp()) {
                            if (ANDEp()) {
                                return true;
                            }
                        }
                    }
                }
            } else if (allTokens.get(index).className.equals("conditionOpen")) {
                index++;
                if (ORE()) {
                    if (allTokens.get(index).className.equals("conditionClose")) {
                        index++;
                        if (Tp()) {
                            if (Ep()) {
                                if (REp()) {
                                    if (ANDEp()) {
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
            } else if (allTokens.get(index).className.equals("incrementOp") || allTokens.get(index).className.equals("decrementOp")) {
                index++;
                if (allTokens.get(index).className.equals("Identifiers")) {
                    index++;
                    if (Tp()) {
                        if (Ep()) {
                            if (REp()) {
                                if (ANDEp()) {
                                    return true;
                                }

                            }
                        }
                    }
                }
            } else if (allTokens.get(index).className.equals("notOp")) {
                index++;
                if (F2()) {

                    if (Tp()) {
                        if (Ep()) {
                            if (REp()) {
                                if (ANDEp()) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;

    }

    public boolean OREp() {

//no selection set
        if (allTokens.get(index).className.equals("orOp")) {
            index++;
            if (ANDE()) {
                if (OREp()) {
                    return true;
                }

            }
        } else if (allTokens.get(index).className.equals("loopPunc") || allTokens.get(index).className.equals("conditionClose") || allTokens.get(index).className.equals("terminator") || allTokens.get(index).className.equals("paraSaperator")) {
            return true;
        }
        return false;
    }

    public boolean ORE() {
        if (allTokens.get(index).className.equals("Identifiers") || allTokens.get(index).className.equals("incrementOp") || allTokens.get(index).className.equals("decrementOp") || allTokens.get(index).className.equals("conditionOpen") || allTokens.get(index).className.equals("notOp") || allTokens.get(index).className.equals("letterConstant") || allTokens.get(index).className.equals("numberConstant") || allTokens.get(index).className.equals("deciConstant") || allTokens.get(index).className.equals("wordConstant")) {
            if (allTokens.get(index).className.equals("Identifiers")) {
                index++;
                if (F1()) {
                    if (Tp()) {
                        if (Ep()) {
                            if (REp()) {
                                if (ANDEp()) {
                                    if (OREp()) {
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
            } else if (constant()) {
                if (Tp()) {
                    if (Ep()) {
                        if (REp()) {
                            if (ANDEp()) {
                                if (OREp()) {
                                    return true;
                                }
                            }
                        }
                    }
                }
            } else if (allTokens.get(index).className.equals("conditionOpen")) {//
                index++;
                if (ORE()) {
                    if (allTokens.get(index).className.equals("conditionClose")) {
                        index++;
                        if (Tp()) {
                            if (Ep()) {
                                if (REp()) {
                                    if (ANDEp()) {
                                        if (OREp()) {
                                            return true;
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } else if (allTokens.get(index).className.equals("incrementOp") || allTokens.get(index).className.equals("decrementOp")) {
                index++;
                if (allTokens.get(index).className.equals("Identifiers")) {
                    index++;
                    if (Tp()) {
                        if (Ep()) {
                            if (REp()) {
                                if (ANDEp()) {
                                    if (OREp()) {
                                        return true;
                                    }
                                }

                            }
                        }
                    }
                }
            } else if (allTokens.get(index).className.equals("notOp")) {
                index++;
                if (F2()) {//
                    if (Tp()) {
                        if (Ep()) {
                            if (REp()) {
                                if (ANDEp()) {
                                    if (OREp()) {
                                        return true;
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;

    }

    //Ending Of Expression
    //SEMANTIC FUNCTIONS//
    String lookUpFunc(String name, String pl, String rtype, String className) {

        String rType = "";
        pl = rtype + "->" + pl;
        boolean flag = false;

        for (int i = 0; i < this.classTable.size(); i++) {

            if (this.classTable.get(i).className.equals(className)) {

                ArrayList<MemberTable> memTable = this.classTable.get(i).memberTable;

                for (int j = 0; j < memTable.size(); j++) {

                    if (name.equals(memTable.get(j).name) && pl.equals(memTable.get(j).type)) {
                        flag = true;

                        pl = memTable.get(j).type;

                        for (String ret : pl.split("->")) {
                            rType = ret;
                            pl = pl;
                            //  System.out.println(pl);
                            break;
                        }
                    }

                }

            }
        }
        if (flag == true) {
            if (rType.equals("")) {
                return pl;
            }
            return rType;
        } else {
            return rType;
        }

    }

    void insertFunc(String name, String pl, String rtype, String am, String tm, int scope, String classname) {

        for (int i = 0; i < this.classTable.size(); i++) {

            if (this.classTable.get(i).className.equals(className)) {

                // System.out.println(name+","+rtype+"->"+pl+","+am+","+tm+","+scope);
                this.classTable.get(i).memberTable.add(new MemberTable(name, rtype + "->" + pl, am, tm, scope));
            }

        }

    }

    String lookUpFunc1(String name, String pl, String rtype, String className) {

        String rType = "";
        String pl1 = "";
        boolean flag = false;

        for (String ret : pl.split("->")) {
            pl1 = ret;
            //  System.out.println(pl);
            break;
        }

        rtype = pl1 + "->" + rtype;

        for (int i = 0; i < this.classTable.size(); i++) {

            if (this.classTable.get(i).className.equals(className)) {

                ArrayList<MemberTable> memTable = this.classTable.get(i).memberTable;

                for (int j = 0; j < memTable.size(); j++) {

                    if (name.equals(memTable.get(j).name) && rtype.equals(memTable.get(j).type)) {
                        flag = true;

                        pl = memTable.get(j).type;

                        for (String ret : pl.split("->")) {
                            rType = ret;
                            pl = pl;
                            break;
                        }
                    }

                }

            }
        }
        if (flag == true) {
            if (rType.equals("")) {
                return pl;
            }
            return rType;
        } else {
            return rType;
        }

    }

    String lookUpVariable(String variableName, int variableScope, String className) {

        String type = "";

        for (int i = 0; i < this.classTable.size(); i++) {

            if (this.classTable.get(i).className.equals(className)) {

                ArrayList<MemberTable> memTable = this.classTable.get(i).memberTable;

                for (int j = 0; j < memTable.size(); j++) {

                    if (memTable.get(j).name.equals(variableName) && variableScope == memTable.get(j).scope) {

                        //  for (int k = 0; k < this.currentScopeStack.size(); k++) {
                        //    if (this.currentScopeStack.get(k).equals(memTable.get(j).scope)) {
                        type = memTable.get(j).type;
                        return type;

                        //   }
                        //}
                    } else if (memTable.get(j).name.equals(variableName)) {
                        for (int k = 0; k < this.currentScopeStack.size(); k++) {
                            if (this.currentScopeStack.get(k).equals(memTable.get(j).scope) && memTable.get(j).scope != classScope) {

                                type = memTable.get(j).type;
                                return type;

                            }
                        }
                    }

                }

            }

        }
        return type;
    }

    void insertVariable(String variableName, String variableType, String AM, String TM, int variableScope, String className) {

        for (int i = 0; i < this.classTable.size(); i++) {

            if (this.classTable.get(i).className.equals(className)) {

                this.classTable.get(i).memberTable.add(new MemberTable(variableName, variableType, AM, TM, variableScope));
            }

        }

    }

    String lookUpClass(String className) {

        String type = "";

        for (int i = 0; i < this.classTable.size(); i++) {

            if (this.classTable.get(i).className.equals(className)) {

                type = className;
                return type;
            }

        }

        return type;
    }

    void insertClass(String className, String AM) {

        this.classTable.add(new ClassTable(className, AM));
    }

    void popCurrentScopeStack() {

        if (this.currentScopeStack.size() > 0) {

            this.currentScopeStack.remove(this.currentScopeStack.size() - 1);

        }

    }

    void pushCurrentScopeStack() {

        this.currentScopeStack.add(this.currentScope);

    }

    String Comp(String T1, String T2, String Opr) {
        if (Opr.equals("&&") || Opr.equals("||")) {
            if (T1.equals("boolean") && T2.equals("boolean")) {
                return "boolean";
            }
        }
        if (Opr.equals("<") || Opr.equals(">") || Opr.equals("<=") || Opr.equals(">=")) {
            if (T1.equals("num") && (T2.equals("num") || T2.equals("letter") || T2.equals("letter{") || T2.equals("num{"))) {
                return "boolean";
            }
            if (T1.equals("letter") && (T2.equals("num") || T2.equals("letter") || T2.equals("letter{") || T2.equals("num{"))) {
                return "boolean";
            }
            if (T1.equals("num{") && (T2.equals("num") || T2.equals("letter") || T2.equals("letter{") || T2.equals("num{"))) {
                return "boolean";
            }
            if (T1.equals("char{") && (T2.equals("num") || T2.equals("letter") || T2.equals("letter{"))) {
                return "boolean";
            }
            if ((T1.equals("deci")) && (T2.equals("deci") || T2.equals("letter") || T2.equals("num") || T2.equals("deci{") || T2.equals("letter{") || T2.equals("num{"))) {
                return "boolean";
            }
            if ((T1.equals("deci{")) && (T2.equals("letter") || T2.equals("num") || T2.equals("letter{") || T2.equals("num{") || T2.equals("deci{") || T2.equals("deci"))) {
                return "boolean";
            }
        }
        switch (Opr) {
            case "=":
                if (T1.equals("num") && (T2.equals("num") || T2.equals("letter") || T2.equals("letter{") || T2.equals("num{"))) {
                    return "num";
                }
                if (T1.equals("letter") && (T2.equals("num") || T2.equals("letter") || T2.equals("letter{"))) {
                    return "num";
                }
                if (T1.equals("num{") && (T2.equals("num") || T2.equals("letter") || T2.equals("num{") || T2.equals("letter{"))) {
                    return "num";
                }
                if (T1.equals("letter{") && (T2.equals("num") || T2.equals("letter") || T2.equals("letter{"))) {
                    return "num";
                }
                if ((T1.equals("deci")) && (T2.equals("deci") || T2.equals("letter") || T2.equals("num") || T2.equals("deci{") || T2.equals("letter{") || T2.equals("num{"))) {
                    return "deci";
                }
                if ((T1.equals("deci{")) && (T2.equals("letter") || T2.equals("num") || T2.equals("letter{") || T2.equals("num{") || T2.equals("deci{") || T2.equals("deci"))) {
                    return "deci";
                }
                if ((T1.equals("word") || T1.equals("word{")) && (T2.equals("word") || T2.equals("word{"))) {
                    return "word";
                }
                if (T1.equals("boolean") && T2.equals("boolean")) {
                    return "boolean";
                }
                break;
            case "+":
                if ((T1.equals("num") || T1.equals("letter") || T1.equals("num{") || T1.equals("letter{")) && (T2.equals("num") || T2.equals("letter") || T2.equals("num{") || T2.equals("letter{"))) {
                    return "num";
                }
                if ((T1.equals("deci")) && (T2.equals("deci") || T2.equals("num") || T2.equals("num{") || T2.equals("deci{") || T2.equals("letter") || T2.equals("letter{"))) {
                    return "deci";
                }
                if ((T1.equals("deci") || T1.equals("num") || T1.equals("num{") || T1.equals("deci{") || T1.equals("letter") || T1.equals("letter")) && (T2.equals("deci{") || T2.equals("deci"))) {
                    return "deci";
                }
                if ((T1.equals("word") || T1.equals("word{")) && (T2.equals("word") || T2.equals("word{") || T2.equals("deci") || T2.equals("num") || T2.equals("num{") || T2.equals("deci{") || T2.equals("letter") || T2.equals("letter"))) {
                    return "word";
                }
                if ((T1.equals("word") || T1.equals("word{") || T1.equals("deci") || T1.equals("num") || T1.equals("num{") || T1.equals("deci{") || T1.equals("letter") || T1.equals("letter{")) && (T2.equals("word") || T2.equalsIgnoreCase("word{"))) {
                    return "word";
                }
                break;
            case "-":
                if ((T1.equals("deci") || T1.equals("deci{")) && (T2.equals("num") || T2.equals("deci") || T2.equals("letter") || T2.equals("num{") || T2.equals("deci{") || T2.equals("letter{"))) {
                    return "deci";
                }
                if ((T1.equals("num") || T1.equals("deci") || T1.equals("letter") || T1.equals("num{") || T1.equals("deci{") || T1.equals("letter{")) && (T2.equals("deci") || T2.equals("deci{"))) {
                    return "deci";
                }
                if ((T1.equals("num") || T1.equals("letter") || T1.equals("num{") || T1.equals("letter{")) && (T2.equals("num") || T2.equals("letter") || T2.equals("num{") || T2.equals("letter{"))) {
                    return "num";
                }
                break;
            case "*":
                if ((T1.equals("num") || T1.equals("letter") || T1.equals("num{") || T1.equals("letter{")) && (T2.equals("num") || T2.equals("letter") || T2.equals("num{") || T2.equals("letter{"))) {
                    return "num";
                }
                if ((T1.equals("deci") || T1.equals("deci{")) && (T2.equals("num") || T2.equals("deci") || T2.equals("letter") || T2.equals("num{") || T2.equals("deci{") || T2.equals("letter{"))) {
                    return "deci";
                }
                if ((T1.equals("num") || T1.equals("deci") || T1.equals("letter") || T1.equals("num{") || T1.equals("deci{") || T1.equals("letter{")) && (T2.equals("deci") || T2.equals("deci{"))) {
                    return "deci";
                }
                break;
            case "/":
                if ((T1.equals("num") || T1.equals("letter") || T1.equals("num{") || T1.equals("letter{")) && (T2.equals("num") || T2.equals("letter") || T2.equals("num{") || T2.equals("letter{"))) {
                    return "num";
                }
                if ((T1.equals("deci") || T1.equals("deci{")) && (T2.equals("num") || T2.equals("deci") || T2.equals("letter") || T2.equals("num{") || T2.equals("deci{") || T2.equals("letter{"))) {
                    return "deci";
                }
                if ((T1.equals("num") || T1.equals("deci") || T1.equals("letter") || T1.equals("num{") || T1.equals("deci{") || T1.equals("letter{")) && (T2.equals("deci") || T2.equals("deci{"))) {
                    return "deci";
                }
                break;
            case "%":
                if ((T1.equals("num") || T1.equals("letter") || T1.equals("num{") || T1.equals("letter{")) && (T2.equals("num") || T2.equals("letter") || T2.equals("num{") || T2.equals("letter{"))) {
                    return "num";
                }
                if ((T1.equals("deci") || T1.equals("deci{")) && (T2.equals("num") || T2.equals("deci") || T2.equals("letter") || T2.equals("num{") || T2.equals("deci{") || T2.equals("letter{"))) {
                    return "deci";
                }
                if ((T1.equals("num") || T1.equals("deci") || T1.equals("letter") || T1.equals("num{") || T1.equals("deci{") || T1.equals("letter{")) && (T2.equals("deci") || T2.equals("deci{"))) {
                    return "deci";
                }
                break;
            case "==":
                if ((T1.equals("num") || T1.equals("num{") || T1.equals("letter") || T1.equals("letter{") || T1.equals("deci") || T1.equals("deci{")) && (T1.equals("num") || T1.equals("num{") || T1.equals("letter") || T1.equals("letter{") || T1.equals("deci") || T1.equals("deci{"))) {
                    return "boolean";
                }
                if ((T1.equals("word") || T1.equals("word{")) && (T2.equals("word") || T2.equals("word"))) {
                    return "boolean";
                }
        }

        return "";
    }

    String Comp1(String T1, String Opr) {

        String rType = "";

        if (T1.equals("num") && (Opr.equals("++") || Opr.equals("--"))) {
            rType = "num";
        } else if (T1.equals("deci") && (Opr.equals("--")) || Opr.equals("++")) {
            rType = "deci";
        } else if (T1.equals("letter") && (Opr.equals("--")) || Opr.equals("++")) {
            rType = "letter";
        } else {
            rType = "";
        }
        return rType;
    }

}
