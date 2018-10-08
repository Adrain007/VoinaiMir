import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {
    public static void main(String [] args){
        Main main = new Main();
        Encryption encryption = new Encryption();
        encryption.encrypt();
        encryption.decrypt();
        /*main.numOfRepeat(new File("VOINAiMIR.txt"),new File("result_voina.txt"));
        main.numOfRepeat(new File("encrypt.txt"),new File("result_encr.txt"));*/
        main.bigramRepeat(new File("VOINAiMIR.txt"),new File("resBigramm.txt"));
        main.bigramRepeat(new File("encrypt.txt"),new File("resBigEncr.txt"));

    }
    private void numOfRepeat(File input,File output){
        HashMap<Character,Token> NumOfRepeat = new HashMap<>();
        initAlph(NumOfRepeat);
        try {
            FileReader fileReader = new FileReader(input);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line ;
            long start = System.nanoTime();
            while ((line = bufferedReader.readLine())!= null){
                line = line.toLowerCase();
                char [] inputLine = line.toCharArray();
                for (char anInputLine : inputLine) {
                    Token token = NumOfRepeat.get(anInputLine);
                    if (NumOfRepeat.containsValue(token)&&token.getName()==anInputLine) {
                        token.setRepeat(token.getRepeat()+1);
                    }
                }
            }
            long finish = System.nanoTime();
            FileWriter writer = new FileWriter(output);
            BufferedWriter writer1 = new BufferedWriter(writer);

            float count = counter(NumOfRepeat);

            System.out.println("Всего букв в тексте: "+count);
            for(Token token: NumOfRepeat.values()){
                System.out.println(token.getName() + " : " + (token.getRepeat()/count*100)+" %");
                writer1.write(token.getName() + " : " + (token.getRepeat()/count*100)+" %\r\n");
            }
            writer1.close();
            bufferedReader.close();
            System.out.println("Execution time = "+(finish-start)/Math.pow(10,6)+" ms");
        } catch (IOException e){
            e.printStackTrace();
        }
    }
    private void initAlph(HashMap<Character, Token> map) {
        for (char c = 'а'; c <= 'я'; c++) {
            map.put(c, new Token(0, c));
        }
    }

    private float counter(HashMap<Character,Token> map){
        float n = 0;
        for(Token token: map.values()){
            n = n + token.getRepeat();
        }
        return n;
    }
    private void bigramRepeat(File input,File output){
        HashMap<String,Bigramm> bigramRepeat = new HashMap<>();
        ArrayList<Character> alph = new ArrayList<>();
        float count = 0;
        for(char c = 'а';c<='я';c++){
            alph.add(c);
        }
        try {
            FileReader fileReader = new FileReader(input);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line ;
            StringBuilder builder = new StringBuilder();
            long start = System.nanoTime();
            while ((line = bufferedReader.readLine())!= null){
                line = line.toLowerCase();
                char [] inputLine = line.toCharArray();
                for (int j = 0;j<inputLine.length-1;j++) {
                    if(alph.contains(inputLine[j])&&alph.contains(inputLine[j+1])){
                        builder.append(inputLine[j]);
                        builder.append(inputLine[j+1]);
                        String key = builder.toString();
                        if(bigramRepeat.containsKey(key)){
                            Bigramm bigramm = bigramRepeat.get(key);
                            bigramm.setRepeat(bigramm.getRepeat()+1);
                            count++;
                        }
                        else{
                            bigramRepeat.put(key,new Bigramm(1,builder.toString()));
                            count++;
                        }
                        builder.delete(0,builder.length());
                    }
                }
            }
            long finish = System.nanoTime();
            FileWriter writer = new FileWriter(output);
            BufferedWriter writer1 = new BufferedWriter(writer);
            System.out.println("Всего биграмм = "+count);
            for(Bigramm bigramm: bigramRepeat.values()){
                System.out.println(bigramm.getName()+" : "+(bigramm.getRepeat()/count)*100+" %");
                writer1.write(bigramm.getName()+" : "+(bigramm.getRepeat()/count)*100+"% \r\n");
            }
            writer1.close();
            bufferedReader.close();
            System.out.println("Execution time = "+(finish-start)/Math.pow(10,6)+" ms");
        } catch (IOException e){
            e.printStackTrace();
        }
    }

}

