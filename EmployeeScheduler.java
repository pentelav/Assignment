import java.util.*;

public class EmployeeScheduler {
    static class Employee {
        String name;
        Map<String, Map<String, Integer>> preferences;
        int daysWorked;

        Employee(String name) {
            this.name = name;
            this.preferences = new HashMap<>();
            this.daysWorked = 0;
        }
    }

    static final Map<String, Map<String, List<String>>> schedule = new HashMap<>();
    static final List<Employee> employees = new ArrayList<>();
    static final String[] DAYS = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday", "Sunday"};

    public static void main(String[] args) {
        collectPreferences();
        assignShifts();
        displaySchedule();
    }

    // Collect employee preferences with priority
    static void collectPreferences() {
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter the number of employees: ");
            int numEmployees = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            for (int i = 0; i < numEmployees; i++) {
                System.out.print("Enter employee " + (i + 1) + "'s name: ");
                String name = scanner.nextLine();
                Employee employee = new Employee(name);

                for (String day : DAYS) {
                    System.out.println("Enter " + name + "'s shift priorities for " + day + " (1 = highest, 3 = lowest):");
                    System.out.print("  Morning priority: ");
                    int morning = scanner.nextInt();
                    System.out.print("  Afternoon priority: ");
                    int afternoon = scanner.nextInt();
                    System.out.print("  Evening priority: ");
                    int evening = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    Map<String, Integer> priorities = new HashMap<>();
                    priorities.put("morning", morning);
                    priorities.put("afternoon", afternoon);
                    priorities.put("evening", evening);
                    employee.preferences.put(day, priorities);
                }
                employees.add(employee);
            }
        }
    }

    // Assign shifts based on priority
    static void assignShifts() {
        Random random = new Random();

        for (String day : DAYS) {
            for (String shift : schedule.get(day).keySet()) {
                // Assign employees based on priority
                for (int priority = 1; priority <= 3; priority++) {
                    for (Employee employee : employees) {
                        if (employee.preferences.get(day).get(shift) == priority && 
                            employee.daysWorked < 5 && 
                            schedule.get(day).get(shift).size() < 2) {
                            schedule.get(day).get(shift).add(employee.name);
                            employee.daysWorked++;
                        }
                    }
                }

                // Ensure at least 2 employees per shift
                while (schedule.get(day).get(shift).size() < 2) {
                    List<Employee> availableEmployees = new ArrayList<>();
                    for (Employee employee : employees) {
                        if (employee.daysWorked < 5 && !schedule.get(day).get(shift).contains(employee.name)) {
                            availableEmployees.add(employee);
                        }
                    }
                    if (!availableEmployees.isEmpty()) {
                        Employee chosen = availableEmployees.get(random.nextInt(availableEmployees.size()));
                        schedule.get(day).get(shift).add(chosen.name);
                        chosen.daysWorked++;
                    } else {
                        break;
                    }
                }
            }
        }
    }

    // Display the schedule
    static void displaySchedule() {
        System.out.println("\nFinal Employee Schedule:");
        for (String day : DAYS) {
            System.out.println("\n" + day + ":");
            for (String shift : schedule.get(day).keySet()) {
                String employeesAssigned = schedule.get(day).get(shift).isEmpty() ? 
                    "No employees assigned" : 
                    String.join(", ", schedule.get(day).get(shift));
                System.out.println("  " + shift.substring(0, 1).toUpperCase() + shift.substring(1) + ": " + employeesAssigned);
            }
        }
    }

    // Initialize schedule
    static {
        for (String day : DAYS) {
            Map<String, List<String>> shifts = new HashMap<>();
            shifts.put("morning", new ArrayList<>());
            shifts.put("afternoon", new ArrayList<>());
            shifts.put("evening", new ArrayList<>());
            schedule.put(day, shifts);
        }
    }
}