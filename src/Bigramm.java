import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;

class Bigramm {

    private int repeat;
    private String name;

    Bigramm(int repeat, String name){
        this.name = name;
        this.repeat = repeat;
    }
    Bigramm(){
    }
    int getRepeat(){
        return repeat;
    }

    String getName() {
        return name;
    }

    void setRepeat(int  repeat) {
        this.repeat = repeat;
    }

    private ArrayList<Bigramm> bigrammsSort(ArrayList<Bigramm> bigramms){
        bigramms.sort(new Sort1());
        /*for(Bigramm bigramm: bigramms){
            System.out.println(bigramm.getName()+" - "+bigramm.getRepeat());
        }*/
        return bigramms;
    }

    ArrayList<Bigramm> bigramRepeat(File input){
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
            System.out.println("Всего биграмм = "+count);
            bufferedReader.close();
        } catch (IOException e){
            e.printStackTrace();
        }
        return bigrammsSort(new ArrayList<>(bigramRepeat.values()));
    }

    /*void bigramDecrypt1(File token1, File token2,File input, File output){
        HashMap<String,String> zamena = correspondenceTable(token1,token2);
        Token token = new Token();
        HashMap<Character,Character> replace = token.correspondenceTable(new File("VOINAiMIR.txt"),new File("encrypt.txt"));
        ArrayList<Character> alph = new ArrayList<>();
        for(char c = 'а';c<='я';c++){
            alph.add(c);
        }
        try {
            FileReader fileReader = new FileReader(input);
            BufferedReader bufferedReader = new BufferedReader(fileReader);
            FileWriter fileWriter=new FileWrit er(output);
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
                            out.deleteCharAt(out.length()-1);
                        }else{
                            out.append(cur);
                        }
                    }else if(alph.contains(cur)&&!alph.contains(next)) {
                        out.append(replace.get(cur));
                    }else {
                        out.append(cur);
                    }
                    builder.delete(0,builder.length());
                }
                bufferedWriter.write(out.toString()+"\n");
                out.delete(0,out.length());
            }
            bufferedReader.close();
            bufferedWriter.close();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }*/

    public HashMap<String,String> correspondenceTable (File Voina_i_Mir,File bigDec){
        ArrayList<Bigramm> bigram1,bigram2;
        bigram1 = bigramRepeat(Voina_i_Mir);
        bigram2 = bigramRepeat(bigDec);
        HashMap<String,String> zamena = new HashMap<>();
        for(int i=0;i<bigram2.size();i++){
            zamena.put(bigram2.get(i).getName(),bigram1.get(i).getName());
            System.out.println(bigram2.get(i).getName()+" - "+bigram1.get(i).getName());
        }
        return zamena;
    }

    void bigramDecrypt(File token1, File token2, File input, File output){
        HashMap<String,String> zamena = correspondenceTable(token1,token2);
        ArrayList<Character> alph = new ArrayList<>();
        for(char c = 'а';c<='я';c++){
            alph.add(c);
        }
        Token token = new Token();

        HashMap<Character,Character> replace = token.correspondenceTable(token1,token2);
        HashMap<String,Integer> map = new HashMap<>();
        StringBuilder builder = new StringBuilder();

        for(String in: zamena.keySet()){
            char first = in.charAt(0);
            char second = in.charAt(1);
            String z = zamena.get(in);
            char [] mas = z.toCharArray();
            builder.append(first);
            builder.append(mas[0]);
            if(!map.containsKey(builder.toString())){
                map.put(builder.toString(),1);
            }else {
                map.put(builder.toString(),map.get(builder.toString())+1);
            }
            builder.delete(0,builder.length());
            builder.append(second);
            builder.append(mas[1]);
            if(!map.containsKey(builder.toString())){
                map.put(builder.toString(),1);
            }else {
                map.put(builder.toString(),map.get(builder.toString())+1);
            }
            builder.delete(0,builder.length());
        }
        /*int max = 0;
        String maximum=null;
        ArrayList<String> masEl = new ArrayList<>();

        for (char v ='а';v<='я';v++) {
            for(char c ='а';c<='я';c++) {
                builder.append(v);
                builder.append(c);
                if(map.containsKey(builder.toString())){
                    if(map.get(builder.toString())>max) {
                        max = map.get(builder.toString());
                        maximum = builder.toString();

                    }
                }
                builder.delete(0, builder.length());
            }
            max=0;
            masEl.add(maximum);
        }*/
        for(String in: map.keySet()){
            System.out.println(in+" -- "+map.get(in));
            char first = in.charAt(0);
            char second = in.charAt(1);
            if(map.get(in)>=6){
                replace.put(first,second);
            }
        }

        System.out.println("\n///////////////////////////\n");
        for(Character char1: replace.keySet()){
            System.out.println(char1.toString()+" - "+replace.get(char1));
        }
        token.decrypt(input,output,replace);
    }
}
