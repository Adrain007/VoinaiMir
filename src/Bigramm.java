class Bigramm {

    private float repeat;
    private String name;

    Bigramm(int repeat, String name){
        this.name = name;
        this.repeat = repeat;
    }
    float getRepeat(){
        return repeat;
    }

    String getName() {
        return name;
    }

    void setRepeat(float repeat) {
        this.repeat = repeat;
    }
}
