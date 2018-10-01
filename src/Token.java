public class Token {

    float repeat;
    private char name;

    Token(int repeat, char name){
        this.name = name;
        this.repeat = repeat;
    }
    public float getRepeat(){
        return repeat;
    }

    public char getName() {
        return name;
    }
}
