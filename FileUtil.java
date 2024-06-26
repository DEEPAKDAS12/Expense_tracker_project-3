import java.io.*;
import java.util.List;
import java.util.Map;

public class FileUtil {

    public static void saveUsers(Map<String, User> users, String fileName) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))) {
            out.writeObject(users);
        }
    }

    public static Map<String, User> loadUsers(String fileName) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))) {
            return (Map<String, User>) in.readObject();
        }
    }

    public static void saveExpenses(List<Expense> expenses, String fileName) throws IOException {
        try (ObjectOutputStream out = new ObjectOutputStream(new FileOutputStream(fileName))) {
            out.writeObject(expenses);
        }
    }

    public static List<Expense> loadExpenses(String fileName) throws IOException, ClassNotFoundException {
        try (ObjectInputStream in = new ObjectInputStream(new FileInputStream(fileName))) {
            return (List<Expense>) in.readObject();
        }
    }
}
