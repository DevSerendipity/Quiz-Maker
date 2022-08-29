import java.io.*;
import java.nio.file.FileSystemNotFoundException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

enum QuizRoles {
    QUESTIONS("questions"),
    ANSWER("answers");

    private final String role;

    QuizRoles(String role) {
        this.role = role;
    }

    String getRole() {
        return role;
    }
}

public class QuizMaker {
    private final ArrayList<String> questions = new ArrayList<>();
    private final ArrayList<String> answers = new ArrayList<>();
    private final Scanner input = new Scanner(System.in);
    private int numberOfCorrectAnswers = 0;

    public void initializeQuiz() {
        retrieveQuestions();
        retrieveAnswers();
        startQuiz();
    }

    private void startQuiz() {
        for ( int i = 0; i < 10; i++ ) {
            int randomIndex = (int) (Math.random() * questions.size());
            getQuestion(randomIndex);
            getAnswers(randomIndex);
        }
        getQuizResults();
    }

    private void getQuizResults() {
        if ( numberOfCorrectAnswers < 5 ) {
            System.out.println("You got " + numberOfCorrectAnswers + "0% answered. Better luck next time.");
        } else {
            System.out.println("You got " + numberOfCorrectAnswers + "0% answered. Good Job buddy.");
        }
    }

    private void getAnswers(int randomQuestionIndex) {
        String answer = input.nextLine().toLowerCase();
        if ( answer.equals(getAnswer(randomQuestionIndex)) ) {
            System.out.println("Correct answer");
            numberOfCorrectAnswers++;
        }
    }

    private void getQuestion(int randomQuestionIndex) {
        System.out.println(questions.get(randomQuestionIndex));
    }

    private String getAnswer(int questionIndex) {
        return answers.get(questionIndex);
    }

    private void retrieveQuestions() {
        try {
            BufferedReader bufferedReader = getBufferedReader(QuizRoles.QUESTIONS.getRole());
            addQuizValues(bufferedReader, questions);
        } catch ( IOException e ) {
            throw new FileSystemNotFoundException("The file could not be found, check the file or adjust the location");
        }
    }

    private void retrieveAnswers() {
        try {
            BufferedReader bufferedReader = getBufferedReader(QuizRoles.ANSWER.getRole());
            addQuizValues(bufferedReader, answers);
        } catch ( IOException e ) {
            throw new FileSystemNotFoundException("The file could not be found, check the file or adjust the location");
        }
    }


    private BufferedReader getBufferedReader(String location) throws FileNotFoundException {
        File file = new File("src/main/resources/" + location + ".txt");
        FileReader fileReader = new FileReader(file);
        return new BufferedReader(fileReader);
    }

    private void addQuizValues(BufferedReader bufferedReader, ArrayList<String> arrayList) throws IOException {
        String line;
        int MAX_LINE = 100;
        for ( int i = 0; i < MAX_LINE; i++ ) {
            line = bufferedReader.readLine().toLowerCase(Locale.ROOT);
            arrayList.add(line);
        }
    }
}
