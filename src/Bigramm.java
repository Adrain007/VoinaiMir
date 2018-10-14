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


    void bigramDecrypt(ArrayList<Bigramm> token1, ArrayList<Bigramm> token2,File input){
        HashMap<String,String> zamena=new HashMap<>();
        ArrayList<Character> alph = new ArrayList<>();
        for(char c = 'а';c<='я';c++){
            alph.add(c);
        }
        for(int i=0;i<5;i++){
            zamena.put(token2.get(i).getName(),token1.get(i).getName());
            System.out.println(token2.get(i).getName()+" - "+token1.get(i).getName());
        }
        try {
            FileReader fileReader = new FileReader(input);
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
                            out.deleteCharAt(out.length()-1);
                        }else{
                            out.append(cur);
                        }
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
    }
}
