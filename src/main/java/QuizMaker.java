import java.io.*;
import java.nio.file.FileSystemNotFoundException;
import java.util.ArrayList;
import java.util.Locale;
import java.util.Scanner;

enum QuizRoles{
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
    private int count = 0;

    public void quiz() {
        retrieveQuizData(QuizRoles.QUESTIONS);
        retrieveQuizData(QuizRoles.ANSWER);
        quizGame();
    }

    private void quizGame() {
        for (int i = 0; i < 10; i++) {
            int randomIndex = (int) (Math.random() * questions.size());
            getQuestion(randomIndex);
            checkInputMatch(randomIndex);
        }
        questionGrading();
    }

    private void questionGrading() {
        if (count < 5) {
            System.out.println("You got " + count + "0% answered. Better luck next time.");
        } else {
            System.out.println("You got " + count + "0% answered. Good Job buddy.");
        }
    }

    private void checkInputMatch(int randomQuestionIndex) {
        String answer = input.nextLine().toLowerCase();
        if (answer.equals(getAnswer(randomQuestionIndex))) {
            System.out.println("Correct answer");
            count++;
        }
    }

    private void getQuestion(int randomQuestionIndex) {
        System.out.println(questions.get(randomQuestionIndex));
    }

    private String getAnswer(int questionIndex) {
        return answers.get(questionIndex);
    }

    private void retrieveQuizData(QuizRoles location) {
        try {
            BufferedReader bufferedReader = getBufferedReader(location.getRole());
            if (location.equals(QuizRoles.QUESTIONS)) {
                addQuizValues(bufferedReader,questions);
            } else {
                addQuizValues(bufferedReader,answers);
            }
        } catch (IOException e) {
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
        for (int i = 0; i < MAX_LINE; i++) {
            line = bufferedReader.readLine().toLowerCase(Locale.ROOT);
            arrayList.add(line);
        }
    }
}