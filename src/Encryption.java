import java.io.*;
import java.util.ArrayList;

final class Encryption {
    private File file ;
    private ArrayList<Character> alphabet = new ArrayList<>();
    private int shift;
    Encryption(File file, int shift){
        this.file = file;
        this.shift = shift;
        for(char c = 'а';c <= 'я'; c++){
            alphabet.add(c);
        }
        for(char c = 'А';c <= 'Я'; c++){
            alphabet.add(c);
        }
    }
    void encrypt(){
        try {
            FileReader fReader = new FileReader(file);
            BufferedReader buffer = new BufferedReader(fReader);
            String inputLine;
            FileWriter fileWriter = new FileWriter("encrypt.txt");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            StringBuilder enc = new StringBuilder();
            while((inputLine = buffer.readLine())!=null) {
                for (int h = 0; h < inputLine.length(); h++) {
                    char c = inputLine.charAt(h);
                    if(alphabet.contains(c)) {
                        int index = alphabet.indexOf(c);
                        index = (index + shift) % alphabet.size();
                        enc.append(alphabet.get(index));
                    } else {
                        enc.append(c);
                    }
                }
                bufferedWriter.write(enc.toString()+"\r\n");
                enc.delete(0,inputLine.length());
            }
            bufferedWriter.close();
            buffer.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
    void procent(File file,File file1){
        ArrayList<Character> alphabet = new ArrayList<>();
        for(char c = 'а';c <= 'я'; c++){
            alphabet.add(c);
        }
        try {
            FileReader fReader = new FileReader(file);
            BufferedReader buffer = new BufferedReader(fReader);
            FileReader fReader1 = new FileReader(file1);
            BufferedReader buffer1 = new BufferedReader(fReader1);
            String inputLine;
            String inputLine1;
            float count = 0;
            float countAll = 0;
            while((inputLine = buffer.readLine())!=null) {
                inputLine = inputLine.toLowerCase();
                inputLine1 = buffer1.readLine();
                for (int h = 0; h < inputLine.length(); h++) {
                    char c = inputLine.charAt(h);
                    char d = inputLine1.charAt(h);
                    if(alphabet.contains(c)) {
                        if(c==d) {
                            count++;
                        }
                        countAll++;
                    }
                }
            }
            buffer1.close();
            buffer.close();
            System.out.println("Процент = "+count/countAll*100+" %");
        }catch (IOException e){
            e.printStackTrace();
        }
    }

}
