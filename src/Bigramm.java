class Bigramm {

    private int repeat;
    private String name;

    Bigramm(int repeat, String name){
        this.name = name;
        this.repeat = repeat;
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
}
