public class Token {

    private float repeat;
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

    public void setRepeat(float repeat) {
        this.repeat = repeat;
    }
}
