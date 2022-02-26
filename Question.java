public class Question {
    final int MAX_QUESTIONS = 20;
    String question;
    Option[] option = new Option[4];
    String correct;
    boolean isAnswered = false;

    public void setQuestion(String question){
        this.question = question;
    }

    public String getQuestion() {
        return question;
    }

    public void setCorrect(String x){
        this.correct = x;
    }

    public Option setOption(int x, String y){
        option[x] = new Option();
        option[x].setOption(y);

        if (option[x].getOption().equals(correct)) {
            option[x].setCorrect(true);
        }

        return option[x];
    }

    public String getOption(int x){
        return option[x].getOption();
    }

    public String getCorrect(){
        return correct;
    }

    public void setAnswered(boolean x){
        this.isAnswered = x;
    }

    public boolean isAnswered(){
        return isAnswered;
    }
}
