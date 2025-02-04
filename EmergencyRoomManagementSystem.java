import java.util.PriorityQueue;
import java.util.Scanner;

class Patient {
    String name;
    String illnessReason;
    int priority;

    public Patient(String name, String illnessReason, int priority) {
        this.name = name;
        this.illnessReason = illnessReason;
        this.priority = priority;
    }

    @Override
    public String toString() {
        return "Patient: " + name +
                ", Priority: " + priority +
                ", Illness Reason: " + illnessReason;
    }
}

class EmergencyRoom {
    PriorityQueue<Patient> priorityQueue = new PriorityQueue<>((p1, p2) -> Integer.compare(p2.priority, p1.priority));

    public void addPatient(String name, String illnessReason, int priority) {
        priorityQueue.add(new Patient(name, illnessReason, priority));
    }

    public Patient treatNextPatient() {
        return priorityQueue.poll();
    }

    public PriorityQueue<Patient> getEmergencyRoomStatus() {
        return priorityQueue;
    }

    public boolean isEmpty() {
        return priorityQueue.isEmpty();
    }
}

public class EmergencyRoomManagementSystem {
    private EmergencyRoom emergencyRoom = new EmergencyRoom();
    private Scanner scanner = new Scanner(System.in);
    private boolean isAdminLoggedIn = false;
    private boolean isDoctorLoggedIn = false;

    public static void main(String[] args) {
        EmergencyRoomManagementSystem system = new EmergencyRoomManagementSystem();
        system.run();
    }

    public void run() {
        while (true) {
            showOptions();

            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            if (!isAdminLoggedIn && !isDoctorLoggedIn) {
                processUserChoice(choice);
            } else if (isAdminLoggedIn) {
                processAdminChoice(choice);
            } else if (isDoctorLoggedIn) {
                processDoctorChoice(choice);
            }
        }
    }

    private void showOptions() {
        System.out.println("\nOptions:");

        if (!isAdminLoggedIn && !isDoctorLoggedIn) {
            System.out.println("1. Login as Admin");
            System.out.println("2. Login as Doctor");
            System.out.println("3. View Emergency Room Status");
            System.out.println("4. Exit");
        } else if (isAdminLoggedIn) {
            System.out.println("1. Add Patient");
            System.out.println("2. View Emergency Room Status");
            System.out.println("3. Logout");
            System.out.println("4. Exit");
        } else if (isDoctorLoggedIn) {
            System.out.println("1. Treat Next Patient");
            System.out.println("2. View Emergency Room Status");
            System.out.println("3. Logout");
        }
    }

    private void processUserChoice(int choice) {
        switch (choice) {
            case 1:
                loginAsAdmin();
                break;
            case 2:
                loginAsDoctor();
                break;
            case 3:
                displayEmergencyRoomStatus();
                break;
            case 4:
                System.out.println("Exiting Emergency Room Management System.");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    private void processAdminChoice(int choice) {
        switch (choice) {
            case 1:
                addPatient();
                break;
            case 2:
                displayEmergencyRoomStatus();
                break;
            case 3:
                logout();
                System.out.println("Logout successful");
                break;
            case 4:
                System.out.println("Exiting Emergency Room Management System.");
                System.exit(0);
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    private void processDoctorChoice(int choice) {
        switch (choice) {
            case 1:
                treatNextPatient();
                break;
            case 2:
                displayEmergencyRoomStatus();
                break;
            case 3:
                logout();
                break;
            default:
                System.out.println("Invalid choice. Please try again.");
        }
    }

    private void loginAsAdmin() {
        System.out.print("Enter admin password: ");
        String password = scanner.nextLine();

        // For simplicity, checking if the entered password is "adminpassword"
        if (password.equals("adminpassword")) {
            isAdminLoggedIn = true;
            System.out.println("Admin login successful.");
        } else {
            System.out.println("Invalid password. Admin login failed.");
        }
    }

    private void loginAsDoctor() {
        System.out.print("Enter doctor password: ");
        String password = scanner.nextLine();

        // For simplicity, checking if the entered password is "doctorpassword"
        if (password.equals("doctorpassword")) {
            isDoctorLoggedIn = true;
            System.out.println("Doctor login successful.");
        } else {
            System.out.println("Invalid password. Doctor login failed.");
        }
    }

    private void addPatient() {
        scanner.nextLine(); // Consume newline
        System.out.print("Enter patient name: ");
        String name = scanner.nextLine();
        System.out.print("Enter patient illness reason: ");
        String illnessReason = scanner.nextLine();
        System.out.print("Enter patient priority: ");
        int priority = scanner.nextInt();
        emergencyRoom.addPatient(name, illnessReason, priority);
        System.out.println("Patient added successfully.");
    }

    private void displayEmergencyRoomStatus() {
        PriorityQueue<Patient> emergencyRoomStatus = emergencyRoom.getEmergencyRoomStatus();
        if (emergencyRoom.isEmpty()) {
            System.out.println("Emergency Room is empty.");
        } else {
            System.out.println("Emergency Room Status:");
            for (Patient patient : emergencyRoomStatus) {
                System.out.println(patient);
            }
        }
    }

    private void treatNextPatient() {
        Patient treatedPatient = emergencyRoom.treatNextPatient();
        if (treatedPatient != null) {
            System.out.println("Treating patient: " + treatedPatient.name +
                    ", Priority: " + treatedPatient.priority +
                    ", Illness Reason: " + treatedPatient.illnessReason);
        } else {
            System.out.println("No patients in the emergency room.");
        }
    }

    private void logout() {
        isAdminLoggedIn = false;
        isDoctorLoggedIn = false;
        System.out.println("Logged out successfully.");
    }
}