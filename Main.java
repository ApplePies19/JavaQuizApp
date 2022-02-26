import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

public class Main extends Application {
    Stage window;
    GridPane grid1, grid2;
    Scene scene1, scene2;

    final int WINDOW_WIDTH = 750;
    final int WINDOW_HEIGHT = 425;
    final int X_PADDING = 8;
    final int Y_PADDING = 10;
    final int MAX_QUESTIONS = 2;

    int questionCount = 1;
    int points = 0;
    int index = 0;

    //Scene 1 Objects
    Label[] labelOption = new Label[4];
    TextField[] fieldAnswer = new TextField[4];
    Label labelTitle, labelCorrect;
    TextField fieldQuestion, fieldCorrect;
    Button next_S1, finish_S1;

    //Scene 2 Objects
    TextArea fieldAsk = new TextArea();
    Button[] btnChoice = new Button[4];

    Question [] Questions = new Question[20];
    MyQueue<Question> Queue= new MyQueue<>();

    @Override
    public void start(Stage primaryStage) throws Exception{
        window = primaryStage;
        window.setTitle("Questionnaire Input");

        loadScene_1();

        next_S1.setOnAction(e -> {
            storeInfo();
            if(questionCount == MAX_QUESTIONS){
                next_S1.setVisible(false);
                finish_S1.setVisible(true);
            }
        });

        finish_S1.setOnAction(e->{
            storeInfo();
            queueQuestions();
            loadScene_2();

            btnChoice[0].setOnAction(j->{
                if(checkPressed(btnChoice[0].getText(), Queue.front().getCorrect())){
                    if(!Queue.front().isAnswered){
                        points++;
                    }
                }
                else{
                    Queue.push(Queue.front());
                }
                updateQuestion();
            });
            btnChoice[1].setOnAction(j->{
                if(checkPressed(btnChoice[1].getText(), Queue.front().getCorrect())){
                    if(!Queue.front().isAnswered){
                        points++;
                    }
                }
                else{
                    Queue.push(Queue.front());
                }
                updateQuestion();
            });
            btnChoice[2].setOnAction(j->{
                if(checkPressed(btnChoice[2].getText(), Queue.front().getCorrect())){
                    if(!Queue.front().isAnswered){
                        points++;
                    }
                }
                else{
                    Queue.push(Queue.front());
                }
                updateQuestion();
            });
            btnChoice[3].setOnAction(j->{
                if(checkPressed(btnChoice[3].getText(), Queue.front().getCorrect())){
                    if(!Queue.front().isAnswered){
                        points++;
                    }
                }
                else{
                    Queue.push(Queue.front());
                }
                updateQuestion();
            });

            window.setScene(scene2);
        });

        scene1 = new Scene(grid1, WINDOW_WIDTH, WINDOW_HEIGHT);

        window.setScene(scene1);
        window.show();
    }

    public void storeInfo(){
        boolean complete = true;

        //Check inputs by checking if all boxes are filled
        if (fieldQuestion.getText().equals("")) {
            System.out.println("Please enter a Question");
            complete = false;
        }
        for (int i = 0; i < 4; i++) {
            if (fieldAnswer[i].getText().equals("")) {
                System.out.println("Please enter an input for Option " + (i + 1));
                complete = false;
            }
        }

        //Checks if Correct Answer if Filled
        if (fieldCorrect.getText().equals("")) {
            System.out.println("Please enter a Correct Answer");
            complete = false;
        } else {
            for (int i = 0; i < 4; i++) {
                if (fieldCorrect.getText().equals(fieldAnswer[i].getText())) {
                    complete = true;
                    break;
                } else {
                    complete = false;
                }
            }
            if (!complete) {
                System.out.println("Correct answer not in choices");
            }
        }

        if (complete) {
            //Store box inputs in the Question class
            Questions[index] = new Question();
            Questions[index].setQuestion(fieldQuestion.getText());
            for (int i = 0; i < 4; i++) {
                Questions[index].setOption(i, fieldAnswer[i].getText());
            }
            Questions[index].setCorrect(fieldCorrect.getText());

            //Clear the boxes
            fieldQuestion.clear();
            fieldAnswer[0].clear();
            fieldAnswer[1].clear();
            fieldAnswer[2].clear();
            fieldAnswer[3].clear();
            fieldCorrect.clear();


            System.out.println("Question " + questionCount + ": " + Questions[index].getQuestion());
            for (int i = 0; i < 4; i++) {
                System.out.println("Option " + (i + 1) + ": " + Questions[index].getOption(i));
            }
            System.out.println("------------------------------------");

            questionCount++;
            index++;

            labelTitle.setText("Question " + (questionCount) + ":");
        }

    }

    public void printQuestions(){
        do{
            System.out.println("Question: " + Queue.front().getQuestion());
            for (int i = 0; i < 4; i++) {
                System.out.println("Option " + (i + 1) + Queue.front().getOption(i) + ":");
            }
            System.out.println("Correct Answer: " + Queue.front().getCorrect());
            Queue.pop();
        }while(!Queue.empty());
    }

    public void queueQuestions(){
        for(int i = 0; i<MAX_QUESTIONS; i++){
            Queue.push(Questions[i]);
        }
    }

    public void loadScene_1(){
        grid1 = new GridPane();
        grid1.setPadding(new Insets(10,10,10,10));
        grid1.setStyle("-fx-background-color: #3d3d40");

        //Individual cell properties
        grid1.setVgap(X_PADDING);
        grid1.setHgap(Y_PADDING);

        //0A. Title Label
        labelTitle = new Label("Question " + questionCount + ":");
        labelTitle.setFont(Font.font("helvetica",FontWeight.LIGHT, 30));
        labelTitle.setStyle("-fx-text-fill: #dbdbd9");
        GridPane.setConstraints(labelTitle,0,0,2,1);
        grid1.getChildren().add(labelTitle);

        //0B. Question Input Box
        fieldQuestion = new TextField();
        fieldQuestion.setFont(Font.font("helvetica", 28));
        fieldQuestion.setStyle("-fx-background-radius: 0;" +
                "-fx-focus-color: transparent;" +
                "-fx-border-color: #202021;" +
                "-fx-control-inner-background: #202021;" +
                "-fx-prompt-text-fill: #7d7d7a;");

        fieldQuestion.setPromptText("Enter a Question");
        fieldQuestion.setMinSize(WINDOW_WIDTH-(X_PADDING*3), 75);

        GridPane.setConstraints(fieldQuestion, 0,1,2,1);
        grid1.getChildren().add(fieldQuestion);

        //Option labels
        for (int i = 0; i<4;i++){
            labelOption[i] = new Label("Option " +(i+1)+":");
            labelOption[i].setFont(Font.font("helvetica", FontWeight.LIGHT,24));
            labelOption[i].setStyle("-fx-text-fill: #dbdbd9");


            GridPane.setConstraints(labelOption[i], 0,i+2);
            GridPane.setHalignment(labelOption[i], HPos.CENTER);
            grid1.getChildren().add(labelOption[i]);

            fieldAnswer[i] = new TextField();
            fieldAnswer[i].setPromptText("Enter Option " + (i+1));
            fieldAnswer[i].setMinSize(50,24);
            fieldAnswer[i].setStyle("-fx-background-radius: 0;" +
                    "-fx-focus-color: transparent;" +
                    "-fx-border-color: #202021;" +
                    "-fx-control-inner-background: #202021;" +
                    "-fx-prompt-text-fill: #7d7d7a;");

            GridPane.setConstraints(fieldAnswer[i], 1,i+2);
            grid1.getChildren().add(fieldAnswer[i]);
        }

        labelCorrect = new Label("  Answer:");
        labelCorrect.setFont(Font.font("helvetica", FontWeight.LIGHT,24));
        labelCorrect.setStyle("-fx-text-fill: #dbdbd9");

        GridPane.setConstraints(labelCorrect, 0, 6);
        GridPane.setHalignment(labelCorrect, HPos.CENTER);
        grid1.getChildren().add(labelCorrect);

        fieldCorrect = new TextField();
        fieldCorrect.setPromptText("Enter the Correct Answer: ");
        fieldCorrect.setMinSize(50,24);
        fieldCorrect.setStyle("-fx-background-radius: 0;" +
                "-fx-focus-color: transparent;" +
                "-fx-border-color: #202021;" +
                "-fx-control-inner-background: #202021;" +
                "-fx-prompt-text-fill: #7d7d7a;");

        GridPane.setConstraints(fieldCorrect,1,6);
        grid1.getChildren().add(fieldCorrect);

        next_S1 = new Button("Next");
        next_S1.setFont(Font.font("helvetica", 24));
        next_S1.setStyle("-fx-background-radius: 0;" +
                        "-fx-focus-color: transparent;");

        GridPane.setConstraints(next_S1,0,7,2,1);
        GridPane.setHalignment(next_S1, HPos.RIGHT);
        GridPane.setValignment(next_S1, VPos.BOTTOM);
        grid1.getChildren().add(next_S1);

        finish_S1 = new Button("Finish");
        finish_S1.setFont(Font.font("helvetica", 24));
        finish_S1.setStyle("-fx-background-radius: 0;" +
                "-fx-focus-color: transparent;");

        GridPane.setConstraints(finish_S1,0,7,2,1);
        GridPane.setHalignment(finish_S1, HPos.RIGHT);
        GridPane.setValignment(finish_S1, VPos.BOTTOM);
        finish_S1.setVisible(false);
        grid1.getChildren().add(finish_S1);
    }

    public void loadScene_2(){

        window.setTitle("Quiz Review");

        grid2 = new GridPane();
        grid2.setPadding(new Insets(10,10,10,10));
        grid2.setStyle("-fx-background-color: #3d3d40");

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPrefWidth(359);
        grid2.getColumnConstraints().add(col1);

        grid2.setVgap(X_PADDING);
        grid2.setHgap(Y_PADDING);

        fieldAsk = new TextArea();
        fieldAsk.setText(Queue.front().getQuestion());
        fieldAsk.setFont(Font.font("helvetica", 36));
        fieldAsk.setStyle("-fx-background-radius: 0;" +
                "-fx-focus-color: #202021;" +
                "-fx-text-box-border: #202021;" +
                "-fx-control-inner-background: #202021;");

        fieldAsk.setWrapText(true);

        fieldAsk.setPrefWidth(WINDOW_WIDTH-(X_PADDING*3));
        fieldAsk.setPrefHeight(350);
        fieldAsk.setEditable(false);
        GridPane.setConstraints(fieldAsk, 0,0,2,1);
        grid2.getChildren().add(fieldAsk);

        for(int i = 0; i < 4; i++){
            btnChoice[i] = new Button(Queue.front().getOption(i));
            btnChoice[i].setFont(Font.font("helvetica", 24));
            btnChoice[i].setPrefWidth(359);
            btnChoice[i].setPrefHeight(250);
            btnChoice[i].wrapTextProperty().setValue(true);
            btnChoice[i].setStyle("-fx-background-radius: 0;" +
                    "-fx-border-color: #202021;" +
                    "-fx-background-color: #202021;" +
                    "-fx-shadow-highlight-color: white;" +
                    "-fx-text-fill: #dbdbd9;");
            if (i<2){
                GridPane.setConstraints(btnChoice[i], 0, i+1);
            }
            else{
                GridPane.setConstraints(btnChoice[i], 1, i-1);
            }
            GridPane.setHalignment(btnChoice[i], HPos.CENTER);
            GridPane.setValignment(btnChoice[i], VPos.BOTTOM);

            grid2.getChildren().add(btnChoice[i]);
        }

        scene2 = new Scene(grid2, WINDOW_WIDTH, WINDOW_HEIGHT);
    }

    public boolean checkPressed(String x, String y){
        return x.equals(y);
    }

    public void updateQuestion(){
        Queue.front().setAnswered(true);
        Queue.pop();
        if(!Queue.empty()) {
            fieldAsk.setText(Queue.front().getQuestion());
            for (int i = 0; i < 4; i++) {
                btnChoice[i].setText(Queue.front().getOption(i));
            }
        }
        else{
            System.out.println("Score: " + points);
            System.exit(1);
        }
    }
}

