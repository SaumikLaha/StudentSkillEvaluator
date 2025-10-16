import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class StudentSkillEvaluator {

    static class Student {
        String name;
        String id;
        List<Double> scores;
        double total;
        double average;
        String grade;
        String feedback;

        Student(String name, String id, List<Double> scores) {
            this.name = name;
            this.id = id;
            this.scores = scores;
            calculate();
        }

        void calculate() {
            total = 0;
            for (double s : scores) total += s;
            average = scores.size() > 0 ? total / scores.size() : 0;
            grade = calculateGrade(average);
            feedback = generateFeedback(average);
        }

        static String calculateGrade(double avg) {
            if (avg >= 90) return "A (Excellent)";
            if (avg >= 75) return "B (Good)";
            if (avg >= 60) return "C (Average)";
            if (avg >= 40) return "D (Needs Improvement)";
            return "F (Fail)";
        }

        static String generateFeedback(double avg) {
            if (avg >= 90) return "Outstanding performance. Keep up the excellent work!";
            if (avg >= 75) return "Good job. You can improve further with consistent practice.";
            if (avg >= 60) return "Fair performance. Focus on weak areas to improve.";
            if (avg >= 40) return "Needs improvement. Spend more time on fundamentals.";
            return "Failing. Consider remedial practice and extra help.";
        }
    }

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        printHeader();

        List<Student> students = new ArrayList<>();
        boolean addMore = true;

        while (addMore) {
            Student s = inputStudent();
            students.add(s);
            System.out.println("\n--- Student Report ---");
            printStudentReport(s);

            System.out.print("\nDo you want to evaluate another student? (yes/no): ");
            addMore = readYesNo();
        }

        if (!students.isEmpty()) {
            System.out.println("\n=== Session Summary ===");
            printSessionSummary(students);
        } else {
            System.out.println("\nNo students evaluated. Bye!");
        }

        scanner.close();
    }

    private static void printHeader() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        System.out.println("============================================");
        System.out.println("   Student Skill Evaluator — Assess Skills");
        System.out.println("   Session start: " + LocalDateTime.now().format(dtf));
        System.out.println("============================================\n");
    }

    private static Student inputStudent() {
        System.out.print("Enter student name: ");
        String name = scanner.nextLine().trim();

        System.out.print("Enter student ID: ");
        String id = scanner.nextLine().trim();

        // Define skill areas (you can change these)
        String[] skills = {"Programming", "Communication", "Problem-Solving", "Teamwork", "Creativity"};
        List<Double> scores = new ArrayList<>();

        System.out.println("\nEnter scores for the following skill areas (0-100):");
        for (String skill : skills) {
            double score = readScoreForSkill(skill);
            scores.add(score);
        }

        return new Student(name, id, scores);
    }

    private static double readScoreForSkill(String skill) {
        while (true) {
            System.out.print(" - " + skill + ": ");
            String line = scanner.nextLine().trim();
            try {
                double v = Double.parseDouble(line);
                if (v < 0 || v > 100) {
                    System.out.println("Please enter a score between 0 and 100.");
                    continue;
                }
                return v;
            } catch (NumberFormatException e) {
                System.out.println("Invalid input. Enter a numeric score (e.g., 85 or 78.5).");
            }
        }
    }

    private static boolean readYesNo() {
        while (true) {
            String ans = scanner.nextLine().trim().toLowerCase();
            if (ans.equals("yes") || ans.equals("y")) return true;
            if (ans.equals("no") || ans.equals("n")) return false;
            System.out.print("Please type 'yes' or 'no': ");
        }
    }

    private static void printStudentReport(Student s) {
        System.out.println("--------------------------------------------");
        System.out.println("Name : " + s.name);
        System.out.println("ID   : " + s.id);
        System.out.println("--------------------------------------------");
        System.out.printf("%-20s %8s%n", "Skill", "Score");
        System.out.println("--------------------------------------------");

        String[] skills = {"Programming", "Communication", "Problem-Solving", "Teamwork", "Creativity"};
        for (int i = 0; i < skills.length; i++) {
            System.out.printf("%-20s %8.2f%n", skills[i], s.scores.get(i));
        }

        System.out.println("--------------------------------------------");
        System.out.printf("%-20s %8.2f%n", "Total", s.total);
        System.out.printf("%-20s %8.2f%n", "Average", s.average);
        System.out.printf("%-20s %8s%n", "Grade", s.grade);
        System.out.println("--------------------------------------------");
        System.out.println("Feedback: " + s.feedback);
    }

    private static void printSessionSummary(List<Student> students) {
        // Print top performer (highest average)
        Student top = students.get(0);
        for (Student s : students) {
            if (s.average > top.average) top = s;
        }

        System.out.println("\nNumber of students evaluated: " + students.size());
        System.out.println("\nTop performer:");
        printStudentReport(top);

        // Optionally show all students brief table
        System.out.println("\nAll students (brief):");
        System.out.printf("%-5s %-20s %-10s %-8s %-8s%n", "No.", "Name", "ID", "Avg", "Grade");
        System.out.println("----------------------------------------------------------");
        int i = 1;
        for (Student s : students) {
            System.out.printf("%-5d %-20s %-10s %8.2f %8s%n", i++, s.name, s.id, s.average, s.grade);
        }
    }
}
