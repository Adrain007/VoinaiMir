import java.io.*;

public class Main {
    public static void main(String [] args){
        long start = System.nanoTime();
        File bigDec = new File("decrypt.txt");
        File Voina_i_Mir = new File("VOINAiMIR.txt");
        File tokenDec = new File("encrypt.txt");
        Bigramm bigramm = new Bigramm();
        Token token = new Token();
        Encryption encryption = new Encryption(new File("source.txt"),4);
        encryption.encrypt();
        token.decrypt(tokenDec,bigDec,token.correspondenceTable(Voina_i_Mir,tokenDec));
        bigramm.bigramDecrypt(Voina_i_Mir,tokenDec,tokenDec,new File("decrypt1.txt"));
        encryption.procent(new File("decrypt.txt"),new File("source.txt"));
        encryption.procent(new File("decrypt1.txt"),new File("source.txt"));

        long finish = System.nanoTime();

        System.out.println("Execution time = "+(finish-start)/Math.pow(10,6)+" ms");
    }
}
