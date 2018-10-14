import com.sun.istack.internal.NotNull;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Main {

    public static void main(String [] args){
        ArrayList<Token> token1,token2;
        ArrayList<Bigramm> bigram1,bigram2;
        Main main = new Main();
        Encryption encryption = new Encryption();
        encryption.encrypt();
        bigram1 = main.bigramRepeat(new File("VOINAiMIR.txt"),new File("resBigramm.txt"));
        bigram2 = main.bigramRepeat(new File("decrypt.txt"),new File("resBigEncr.txt"));
        token1 = main.numOfRepeat(new File("VOINAiMIR.txt"),new File("result_voina.txt"));
        token2 = main.numOfRepeat(new File("encrypt.txt"),new File("result_encr.txt"));
        main.decrypt(token1,token2);
        main.bigramDecrypt(bigram1,bigram2);
        encryption.procent(new File("decrypt12.txt"),new File("source.txt"));
    }

    private ArrayList<Token> sortToken (ArrayList<Token> token){
        token.sort(new Sort());
        for(Token tokens:token){
            System.out.println(tokens.getRepeat()+"-" +tokens.getName());
        }
        return token;
    }

    private ArrayList<Bigramm> bigrammsSort(ArrayList<Bigramm> bigramms){
        bigramms.sort(new Sort1());
        for(Bigramm bigramm: bigramms){
            System.out.println(bigramm.getRepeat()+"-"+bigramm.getName());
        }
        return bigramms;
    }

    private void bigramDecrypt(ArrayList<Bigramm> token1, ArrayList<Bigramm> token2){
        HashMap<String,String> zamena=new HashMap<>();
        ArrayList<Character> alph = new ArrayList<>();
        for(char c = 'а';c<='я';c++){
            alph.add(c);
        }
        for(int i=0;i<10;i++){
            zamena.put(token2.get(i).getName(),token1.get(i).getName());
            System.out.println(token2.get(i).getName()+" - "+token1.get(i).getName());
        }
        try {
            FileReader fileReader = new FileReader(new File("decrypt.txt"));
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            FileWriter fileWriter=new FileWriter(new File("decrypt1.txt"));
            BufferedWriter bufferedWriter=new BufferedWriter(fileWriter);
            String line ;
            StringBuilder builder=new StringBuilder();
            StringBuilder out=new StringBuilder();
            while ((line = bufferedReader.readLine())!=null) {
                line = line.toLowerCase();
                char[] inputLine = line.toCharArray();
                for(int h=0;h<line.length();h++){
                    char cur = inputLine[h];
                    char next;
                    if(h<line.length()-1) {
                        next = inputLine[h + 1];
                    }else {
                        out.append(cur);
                        break;
                    }
                    if(alph.contains(cur)&&alph.contains(next)){
                        builder.append(cur);
                        builder.append(next);
                        if(zamena.containsKey(builder.toString())){
                            out.append(zamena.get(builder.toString()));
                            if(h!=line.length()-2){
                                out.deleteCharAt(out.length()-1);
                            }
                        }else{
                            out.append(cur);
                        }
                    }else {
                        out.append(cur);
                    }
                    builder.delete(0,builder.length());
                }
                bufferedWriter.write(out.toString()+"\r\n");
                out.delete(0,out.length());
            }
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }

    private void decrypt (@NotNull ArrayList<Token> token1, ArrayList<Token> token2){
        HashMap<Character,Character> zamena=new HashMap<>();
        for(int i=0;i<token1.size();i++){
            zamena.put(token2.get(i).getName(),token1.get(i).getName());
            System.out.println(token2.get(i).getName()+" - "+token1.get(i).getName());
        }
        try {
            FileReader fileReader = new FileReader(new File("encrypt.txt"));
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            FileWriter fileWriter=new FileWriter(new File("decrypt.txt"));
            BufferedWriter bufferedWriter=new BufferedWriter(fileWriter);
            String line ;
            StringBuilder builder=new StringBuilder();
            while ((line = bufferedReader.readLine())!= null) {
                line = line.toLowerCase();
                char[] inputLine = line.toCharArray();
                for (char anInputLine : inputLine) {
                    if(zamena.containsKey(anInputLine)){
                        builder.append(zamena.get(anInputLine));
                    }
                    else{
                        builder.append(anInputLine);
                    }
                }
                bufferedWriter.write(builder.toString()+"\r\n");
                builder.delete(0,line.length());
            }
            bufferedReader.close();
            bufferedWriter.close();
        }
        catch (IOException e){
            e.printStackTrace();
        }

    }

    private ArrayList <Token> numOfRepeat(File input,File output){
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
                writer1.write(token.getName() + " : " + (token.getRepeat()/count*100)+" %\r\n");
            }
            writer1.close();
            bufferedReader.close();
            System.out.println("Execution time = "+(finish-start)/Math.pow(10,6)+" ms");
        } catch (IOException e){
            e.printStackTrace();
        }
        return sortToken(new ArrayList<>(NumOfRepeat.values()));
    }
    private void initAlph(HashMap<Character, Token> map) {
        for (char c = 'а'; c <= 'я'; c++) {
            map.put(c, new Token(0, c));
        }
    }

    private float counter(@NotNull HashMap<Character,Token> map){
        float n = 0;
        for(Token token: map.values()){
            n = n + token.getRepeat();
        }
        return n;
    }
    private ArrayList<Bigramm> bigramRepeat(File input,File output){
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
                writer1.write(bigramm.getName()+" : "+(bigramm.getRepeat()/count)*100+"% \r\n");
            }
            writer1.close();
            bufferedReader.close();
            System.out.println("Execution time = "+(finish-start)/Math.pow(10,6)+" ms");
        } catch (IOException e){
            e.printStackTrace();
        }
        return bigrammsSort(new ArrayList<>(bigramRepeat.values()));
    }

}
