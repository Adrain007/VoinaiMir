import java.io.*;
import java.util.ArrayList;

final class Codirovka {
    private File file = new File("sosi.txt");
    private ArrayList<Character> alphabet = new ArrayList<>();
    private final static char[] PUNCTUATION = {'.', ',', ';', ':', '!', '?', '-','"',')','(',' '};
    private int sdvig = 1;
    Codirovka(){ 
        for(char c = 'а';c <= 'я'; c++){
            alphabet.add(c);
        }
        for(char c = 'А';c <= 'Я'; c++){
            alphabet.add(c);
        }
        for(char c: PUNCTUATION){
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
                    int index = alphabet.indexOf(c);
                    index = (index + sdvig) % alphabet.size();
                    enc.append(alphabet.get(index));
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

    void decrypt(){
        try {
            FileReader fReader = new FileReader("encrypt.txt");
            BufferedReader buffer = new BufferedReader(fReader);
            String inputLine;
            FileWriter fileWriter = new FileWriter("decrypt.txt");
            BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
            StringBuilder dec = new StringBuilder();
            while((inputLine = buffer.readLine())!=null) {
                char[] mas = inputLine.toCharArray();
                for (char c : mas) {
                    int index = alphabet.indexOf(c);
                    index = ((alphabet.size() + index) - sdvig) % alphabet.size();
                    dec.append(alphabet.get(index));
                }
                bufferedWriter.write(dec.toString()+"\r\n");
                dec.delete(0,inputLine.length());
            }
            buffer.close();
            bufferedWriter.close();
        }catch (IOException e){
            e.printStackTrace();
        }
    }
}
