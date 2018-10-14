import com.sun.istack.internal.NotNull;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

public class Token {

    private int  repeat;
    private char name;

    Token(int repeat, char name){
        this.name = name;
        this.repeat = repeat;
    }

    Token(){}
    public int getRepeat(){
        return repeat;
    }

    public char getName() {
        return name;
    }

    public void setRepeat(int repeat) {
        this.repeat = repeat;
    }

    private ArrayList<Token> sortToken (ArrayList<Token> token){
        token.sort(new Sort());
        return token;
    }

    public HashMap<Character,Character> correspondenceTable (File Voina_i_Mir,File tokenDec){
        ArrayList<Token> token1,token2;
        token1 = numOfRepeat(Voina_i_Mir);
        token2 = numOfRepeat(tokenDec);
        HashMap<Character,Character> zamena = new HashMap<>();
        for(int i=0;i<token1.size();i++){
            zamena.put(token2.get(i).getName(),token1.get(i).getName());
            System.out.println(token2.get(i).getName()+" - "+token1.get(i).getName());
        }
        return zamena;
    }

    public void decrypt (File input, File output, HashMap zamena1){
        HashMap<Character,Character> zamena = zamena1;
        try {
            FileReader fileReader = new FileReader(input);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            FileWriter fileWriter=new FileWriter(output);
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

    public ArrayList <Token> numOfRepeat(File input){
        HashMap<Character,Token> NumOfRepeat = new HashMap<>();
        initAlph(NumOfRepeat);
        try {
            FileReader fileReader = new FileReader(input);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            String line ;

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
            bufferedReader.close();

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

}
