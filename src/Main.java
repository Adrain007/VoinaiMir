import java.io.*;
import java.util.ArrayList;

public class Main {

    public static void main(String [] args){
        Main main = new Main();
        Codirovka codirovka = new Codirovka();
        codirovka.encrypt();
        codirovka.decrypt();
        main.numOfHits(new File("decrypt.txt"),new File("result_voina.txt"));
        main.numOfHits(new File("encrypt.txt"),new File("result_encr.txt"));
    }
    private void numOfHits(File input,File output){
        ArrayList<Token> NumOfHits = new ArrayList<>();
        for(char c = 'а'; c<='я';c++){
            NumOfHits.add(new Token(0,c));
        }
        try {
            FileReader fileReader = new FileReader(input);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line ;
            long start = System.nanoTime();
            while ((line = bufferedReader.readLine())!= null){
                line = line.toLowerCase();
                char [] inputLine = line.toCharArray();
                for (char anInputLine : inputLine) {
                    for(Token token: NumOfHits) {
                        if (token.getName()==anInputLine) {
                            token.repeat++;
                            break;
                        }
                    }
                }
            }
            long finish = System.nanoTime();
            FileWriter writer = new FileWriter(output);
            BufferedWriter writer1 = new BufferedWriter(writer);
            float n = 0;
            for(Token token: NumOfHits){
                n = n + token.getRepeat();
            }
            System.out.println(n);
            for(Token token: NumOfHits){
                System.out.println(token.getName() + " : " + Math.round(token.getRepeat()/n*100)+" %");
                writer1.write(token.getName() + " : " + Math.round(token.getRepeat()/n*100)+" %\n");
            }
            writer1.close();
            bufferedReader.close();
            System.out.println("Execution time = "+(finish-start)/Math.pow(10,6)+" ms");
        } catch (IOException e){
            e.printStackTrace();
        }

    }
}

