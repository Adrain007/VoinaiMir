public class Token {

    private int  repeat;
    private char name;

    Token(int repeat, char name){
        this.name = name;
        this.repeat = repeat;
    }
    public int getRepeat(){
        return repeat;
    }

    public char getName() {
        return name;
    }

    public void setRepeat(int repeat) {
        this.repeat = repeat;
    }
}
