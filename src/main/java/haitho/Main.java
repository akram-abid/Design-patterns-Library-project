package main.java.haitho;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    private static Scanner scanner = new Scanner(System.in);
    private static Library library = Library.getInstance();
    private static ArrayList<Client> clients = new ArrayList<>();
    private static ArrayList<Admin> admins = new ArrayList<>();
    private static UserI currentUser = null;
    private static boolean isRunning = true;

    public static void main(String[] args) {
        initializeSomeData(); 
        
        while (isRunning) {
            if (currentUser == null) {
                displayMainMenu();
            } else if (currentUser instanceof Admin) {
                displayAdminMenu();
            } else if (currentUser instanceof Client) {
                displayClientMenu();
            }
        }
        
        scanner.close();
        System.out.println("Thank you for using the Library Management System. Goodbye!");
    }

    private static void initializeSomeData() {
        library.addBook(new Book("B001", "Java Programming", "John Doe", 2020, 5));
        library.addBook(new Book("B002", "Python Basics", "Jane Smith", 2019, 3));
        library.addBook(new Book("B003", "Data Structures", "Robert Johnson", 2021, 2));
        
        Admin admin = new Admin("Admin", "User", "admin@library.com", "admin123", 1);
        library.registerAdmin(admin);
        admins.add(admin);
        
        Client client = new Client("Client", "User", "client@example.com", "client123", 101);
        library.registerClient(client);
        clients.add(client);
    }

    private static void displayMainMenu() {
        System.out.println("\n===== Library Management System =====");
        System.out.println("1. Login");
        System.out.println("2. Register as Client");
        System.out.println("3. Register as Admin");
        System.out.println("4. View Available Books");
        System.out.println("5. Exit");
        System.out.print("Enter your choice: ");
        
        try {
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    login();
                    break;
                case 2:
                    registerClient();
                    break;
                case 3:
                    registerAdmin();
                    break;
                case 4:
                    library.viewAvailableBooks();
                    break;
                case 5:
                    isRunning = false;
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        }
    }

    private static void displayClientMenu() {
        Client client = (Client) currentUser;
        System.out.println("\n===== Client Menu =====");
        System.out.println("Welcome, " + client.getEmail());
        System.out.println("1. View All Books");
        System.out.println("2. View Available Books");
        System.out.println("3. Borrow a Book");
        System.out.println("4. Return a Book");
        System.out.println("5. View My Borrowed Books");
        System.out.println("6. Logout");
        System.out.print("Enter your choice: ");
        
        try {
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    library.viewAllBooks();
                    break;
                case 2:
                    library.viewAvailableBooks();
                    break;
                case 3:
                    borrowBook(client);
                    break;
                case 4:
                    returnBook();
                    break;
                case 5:
                    client.printBorrowedBooks();
                    break;
                case 6:
                    logout();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        }
    }

    private static void displayAdminMenu() {
        Admin admin = (Admin) currentUser;
        System.out.println("\n===== Admin Menu =====");
        System.out.println("Welcome, " + admin.getEmail());
        System.out.println("1. View All Books");
        System.out.println("2. Add New Book");
        System.out.println("3. View All Users");
        System.out.println("4. View All Borrows");
        System.out.println("5. Return a Book");
        System.out.println("6. Logout");
        System.out.print("Enter your choice: ");
        
        try {
            int choice = Integer.parseInt(scanner.nextLine());
            switch (choice) {
                case 1:
                    library.viewAllBooks();
                    break;
                case 2:
                    addBook(admin);
                    break;
                case 3:
                    admin.viewAllUsers();
                    break;
                case 4:
                    library.viewAllBorrows();
                    break;
                case 5:
                    returnBook();
                    break;
                case 6:
                    logout();
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number.");
        }
    }

    private static void login() {
        System.out.println("\n===== Login =====");
        System.out.print("Enter your email: ");
        String email = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();
        
        // Check admins first
        for (Admin admin : admins) {
            if (admin.getEmail().equals(email)) {
                currentUser = admin;
                System.out.println("Admin login successful!");
                return;
            }
        }
        
        for (Client client : clients) {
            if (client.getEmail().equals(email)) {
                currentUser = client;
                System.out.println("Client login successful!");
                return;
            }
        }
        
        System.out.println("Login failed. Invalid email or password.");
    }

    private static void registerClient() {
        System.out.println("\n===== Register as Client =====");
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        System.out.print("Enter your surname: ");
        String surname = scanner.nextLine();
        System.out.print("Enter your email: ");
        String email = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();
        
        int id = clients.size() + 101; 
        
        ClientFactory clientFactory = new ClientFactory();
        Client newClient = (Client) clientFactory.createUser(name, surname, email, password, id);
        
        if (library.registerClient(newClient)) {
            clients.add(newClient);
            System.out.println("Client registered successfully!");
        } else {
            System.out.println("Registration failed. Email already exists.");
        }
    }

    private static void registerAdmin() {
        System.out.println("\n===== Register as Admin =====");
        System.out.print("Enter your name: ");
        String name = scanner.nextLine();
        System.out.print("Enter your surname: ");
        String surname = scanner.nextLine();
        System.out.print("Enter your email: ");
        String email = scanner.nextLine();
        System.out.print("Enter your password: ");
        String password = scanner.nextLine();
        
        int id = admins.size() + 1; 
        
        AdminFactory adminFactory = new AdminFactory();
        Admin newAdmin = (Admin) adminFactory.createUser(name, surname, email, password, id);
        
        if (library.registerAdmin(newAdmin)) {
            admins.add(newAdmin);
            System.out.println("Admin registered successfully!");
        } else {
            System.out.println("Registration failed. Email already exists.");
        }
    }

    private static void borrowBook(Client client) {
        library.viewAvailableBooks();
        System.out.print("Enter the book ID you want to borrow: ");
        String bookId = scanner.nextLine();
        
        for (Book book : library.getBooks()) {
            if (book.getId().equals(bookId) && book.getQuntity() > 0) {
                if (client.borrow(book)) {
                    System.out.println("Book borrowed successfully!");
                }
                return;
            }
        }
        
        System.out.println("Book not found or not available.");
    }

    private static void returnBook() {
        System.out.print("Enter the book ID you want to return: ");
        String bookId = scanner.nextLine();
        currentUser.returnBook(bookId);
    }

    private static void addBook(Admin admin) {
        System.out.println("\n===== Add New Book =====");
        System.out.print("Enter book ID: ");
        String id = scanner.nextLine();
        System.out.print("Enter book title: ");
        String title = scanner.nextLine();
        System.out.print("Enter author: ");
        String author = scanner.nextLine();
        System.out.print("Enter year published: ");
        int yearPublished;
        try {
            yearPublished = Integer.parseInt(scanner.nextLine());
        } catch (NumberFormatException e) {
            System.out.println("Invalid year. Using current year as default.");
            yearPublished = 2025;
        }
        
        System.out.print("Enter quantity: ");
        try {
            int quantity = Integer.parseInt(scanner.nextLine());
            Book newBook = new Book(id, title, author, yearPublished, quantity);
            admin.addBook(newBook);
            System.out.println("Book added successfully!");
        } catch (NumberFormatException e) {
            System.out.println("Please enter a valid number for quantity.");
        }
    }

    private static void logout() {
        currentUser = null;
        System.out.println("Logged out successfully!");
    }
}