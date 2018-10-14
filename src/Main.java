import java.io.*;
import java.util.ArrayList;

public class Main {

    public static void main(String [] args){
        long start = System.nanoTime();
        ArrayList<Token> token1,token2;

        File bigDec = new File("decrypt.txt");
        File Voina_i_Mir = new File("VOINAiMIR.txt");
        File tokenDec = new File("encrypt.txt");

        ArrayList<Bigramm> bigram1,bigram2;

        Bigramm bigramm = new Bigramm();
        Token token = new Token();

        Encryption encryption = new Encryption(new File("source.txt"),18);
        encryption.encrypt();

        token1 = token.numOfRepeat(Voina_i_Mir);
        token2 = token.numOfRepeat(tokenDec);
        token.decrypt(token1,token2);

        bigram1 = bigramm.bigramRepeat(Voina_i_Mir);
        bigram2 = bigramm.bigramRepeat(bigDec);
        bigramm.bigramDecrypt(bigram1,bigram2,bigDec);

        encryption.procent(new File("decrypt.txt"),new File("source.txt"));

        long finish = System.nanoTime();

        System.out.println("Execution time = "+(finish-start)/Math.pow(10,6)+" ms");
    }
}
