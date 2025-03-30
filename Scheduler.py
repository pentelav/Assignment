import random

# Data structures to store employee information and schedule
employees = {}
schedule = {
    'Monday': {'morning': [], 'afternoon': [], 'evening': []},
    'Tuesday': {'morning': [], 'afternoon': [], 'evening': []},
    'Wednesday': {'morning': [], 'afternoon': [], 'evening': []},
    'Thursday': {'morning': [], 'afternoon': [], 'evening': []},
    'Friday': {'morning': [], 'afternoon': [], 'evening': []},
    'Saturday': {'morning': [], 'afternoon': [], 'evening': []},
    'Sunday': {'morning': [], 'afternoon': [], 'evening': []},
}

# Function to collect employee preferences
def collect_preferences():
    num_employees = int(input("Enter the number of employees: "))
    for i in range(num_employees):
        name = input(f"Enter employee {i+1}'s name: ")
        preferences = {}
        for day in schedule.keys():
            shift = input(f"Enter {name}'s preferred shift for {day} (morning/afternoon/evening): ").lower()
            preferences[day] = shift
        employees[name] = {'preferences': preferences, 'days_worked': 0}

# Function to assign shifts
def assign_shifts():
    for day in schedule.keys():
        for shift in schedule[day].keys():
            # Assign employees based on preferences
            for employee, data in employees.items():
                if data['preferences'][day] == shift and data['days_worked'] < 5 and len(schedule[day][shift]) < 2:
                    schedule[day][shift].append(employee)
                    data['days_worked'] += 1

            # Ensure at least 2 employees per shift
            while len(schedule[day][shift]) < 2:
                available_employees = [emp for emp, data in employees.items() if data['days_worked'] < 5 and emp not in schedule[day][shift]]
                if available_employees:
                    chosen = random.choice(available_employees)
                    schedule[day][shift].append(chosen)
                    employees[chosen]['days_worked'] += 1
                else:
                    break

# Function to display the schedule
def display_schedule():
    print("\nFinal Employee Schedule:")
    for day, shifts in schedule.items():
        print(f"\n{day}:")
        for shift, employees in shifts.items():
            print(f"  {shift.capitalize()}: {', '.join(employees) if employees else 'No employees assigned'}")

# Main function
def main():
    collect_preferences()
    assign_shifts()
    display_schedule()

if __name__ == "__main__":
    main()