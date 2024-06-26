import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ExpenseService implements Serializable {
    private static final long serialVersionUID = 1L;
    private List<Expense> expenses = new ArrayList<>();

    public void addExpense(Expense expense) {
        expenses.add(expense);
    }

    public List<Expense> listExpenses() {
        return expenses;
    }

    public Map<String, Double> summarizeExpensesByCategory() {
        Map<String, Double> summary = new HashMap<>();
        for (Expense expense : expenses) {
            summary.put(expense.getCategory(),
                    summary.getOrDefault(expense.getCategory(), 0.0) + expense.getAmount());
        }
        return summary;
    }

    public List<Expense> getExpenses() {
        return expenses;
    }

    public void setExpenses(List<Expense> expenses) {
        this.expenses = expenses;
    }
}
