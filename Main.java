import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Main {
    private static UserService userService = new UserService();
    private static ExpenseService expenseService = new ExpenseService();
    private static final Scanner scanner = new Scanner(System.in);
    private static User currentUser = null;

    public static void main(String[] args) {
        // Load data
        try {
            userService = new UserService(FileUtil.loadUsers("resources/users.dat"));
            expenseService.setExpenses(FileUtil.loadExpenses("resources/expenses.dat"));
        } catch (IOException | ClassNotFoundException e) {
            System.out.println("No previous data found. Starting fresh.");
        }

        // User interface
        while (true) {
            System.out.println("1. Register");
            System.out.println("2. Login");
            System.out.println("3. Add Expense");
            System.out.println("4. List Expenses");
            System.out.println("5. Summarize Expenses by Category");
            System.out.println("6. Exit");
            System.out.print("Choose an option: ");
            int choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    register();
                    break;
                case 2:
                    login();
                    break;
                case 3:
                    if (currentUser != null) {
                        addExpense();
                    } else {
                        System.out.println("Please login first.");
                    }
                    break;
                case 4:
                    if (currentUser != null) {
                        listExpenses();
                    } else {
                        System.out.println("Please login first.");
                    }
                    break;
                case 5:
                    if (currentUser != null) {
                        summarizeExpenses();
                    } else {
                        System.out.println("Please login first.");
                    }
                    break;
                case 6:
                    saveData();
                    System.exit(0);
            }
        }
    }

    private static void register() {
        System.out.print("Enter username: ");
        String username = scanner.next();
        System.out.print("Enter password: ");
        String password = scanner.next();
        try {
            userService.registerUser(username, password);
            System.out.println("User registered successfully.");
        } catch (UserAlreadyExistsException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void login() {
        System.out.print("Enter username: ");
        String username = scanner.next();
        System.out.print("Enter password: ");
        String password = scanner.next();
        try {
            currentUser = userService.authenticateUser(username, password);
            System.out.println("User authenticated successfully.");
        } catch (AuthenticationException e) {
            System.out.println(e.getMessage());
        }
    }

    private static void addExpense() {
        System.out.print("Enter date (YYYY-MM-DD): ");
        String dateString = scanner.next();
        Date date = null;
        try {
            date = new SimpleDateFormat("yyyy-MM-dd").parse(dateString);
        } catch (ParseException e) {
            System.out.println("Invalid date format. Please try again.");
            return;
        }
        System.out.print("Enter category: ");
        String category = scanner.next();
        System.out.print("Enter amount: ");
        double amount = scanner.nextDouble();
        expenseService.addExpense(new Expense(date, category, amount));
        System.out.println("Expense added successfully.");
    }

    private static void listExpenses() {
        List<Expense> expenses = expenseService.listExpenses();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy EEEE");
        for (Expense expense : expenses) {
            String dateFormatted = dateFormat.format(expense.getDate());
            System.out.println(dateFormatted + " - Category: " + expense.getCategory() + ", Amount: " + expense.getAmount());
        }
    }

    private static void summarizeExpenses() {
        Map<String, Double> summary = expenseService.summarizeExpensesByCategory();
        for (Map.Entry<String, Double> entry : summary.entrySet()) {
            System.out.println(entry.getKey() + ": " + entry.getValue());
        }
    }

    private static void saveData() {
        try {
            FileUtil.saveUsers(userService.getUsers(), "resources/users.dat");
            FileUtil.saveExpenses(expenseService.getExpenses(), "resources/expenses.dat");

System.out.println("Failed to save data");
        } catch (IOException e) {

            System.out.println("Data saved successfully.");

        }
    }	
}
