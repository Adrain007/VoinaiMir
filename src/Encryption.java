import java.io.*;
import java.util.ArrayList;

final class Encryption {
    private File file = new File("source.txt");
    private ArrayList<Character> alphabet = new ArrayList<>();
    private int shift = 15;
    Encryption(){
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
}
