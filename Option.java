public class Option {
    boolean correct;
    String option;

    public void setOption(String option) {
        this.option = option;
    }

    public String getOption(){
        return this.option;
    }

    public void setCorrect(boolean x){
        this.correct = x;
    }

    public boolean isCorrect(){
        return correct;
    }
}
