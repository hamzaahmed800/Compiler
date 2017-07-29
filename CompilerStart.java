package compilerconstruction;

import java.util.*;

/**
 *
 * @author HAMI
 */
public class CompilerStart {

    public static void main(String[] args) {

        LexicalAnalyzer lexical = new LexicalAnalyzer();
        ArrayList<Token> allTokens = new ArrayList<Token>();

        allTokens = lexical.check("C:\\Users\\HAMI\\Desktop\\new2.txt");
        CheckCFGsSemantic cfgChks = new CheckCFGsSemantic(allTokens);
        try {
            lexical.outPutFile();
        } catch (Exception ex) {
        }

        for (Token T : allTokens) {
            System.out.println("(" + T.value + " ," + T.className + " , " + T.lineNo + " ) \n");
        }
        System.out.println("Total Tokens =  " + lexical.flag);

        System.out.println(cfgChks.startCheck());

    }

}
