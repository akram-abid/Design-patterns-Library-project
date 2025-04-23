package main.java.haitho;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;

public class Library {
    private String name;
    private String address;

    private ArrayList<Book> books = new ArrayList<>();
    private ArrayList<Client> members = new ArrayList<>();
    private ArrayList<Admin> admins = new ArrayList<>();
    private ArrayList<Borrow> borrows = new ArrayList<>();

    public ArrayList<Book> getBooks() {
        return books;
    }

    public void addBook(Book book) {
        for (Book b : books) {
            if (b.getId().equals(book.getId())) {
                b.setQuntity(b.getQuntity() + book.getQuntity());
                return;
            }
        }
        books.add(book);
    }

    
    public void viewAllBooks() {
        System.out.println("Books in the library:");
        for (Book book : books) {
            System.out.println(book);
        }
    }
    
    public void viewAvailableBooks() {
        System.out.println("Available books in the library:");
        for (Book book : books) {
            if (book.getStateAsString().equals("AVAILABLE")) {
                System.out.println(book);
            }
        }
    }
    
    public void viewAllUsers() {
        System.out.println("Members of the library:");
        for (Client member : members) {
            System.out.println(member);
        }
        System.out.println("Admins of the library:");
        for (Admin admin : admins) {
            System.out.println(admin);
        }
    }
    
    public void viewAllBorrows() {
        System.out.println("Borrowed books:");
        for (Borrow borrow : borrows) {
            System.out.println(borrow.getBook() + " borrowed by " + borrow.getClient());
        }
    }

    public boolean borrowBook(String id , Client borrower ) {
        for (Book book : books) {
            if (book.getId().equals(id) && book.getQuntity() > 0) {
                book.setQuntity(book.getQuntity() - 1);

                LocalDate now = LocalDate.now();
                LocalDate returnDay = now.plusDays(14); 
        

                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
                String currentDate = now.format(formatter);
                String returnDate = returnDay.format(formatter);

                borrows.add(new Borrow(book , borrower, currentDate, returnDate));
                return true;
            }
        }
        return false; 
    }

    public boolean returnBook(String id) {
        for (Book book : books) {
            if (book.getId().equals(id)) {
                book.setQuntity(book.getQuntity() + 1);
                // The state updates automatically in setQuntity method
                return true;
            }
        }
        return false; 
    }

    public ArrayList<Book> getBorrowedBooksByClient(String client) {
        ArrayList<Book> borrowedBooks = new ArrayList<>();
        for (Borrow borrow : borrows) {
            if (borrow.getClient().getEmail().equals(client)) {
                borrowedBooks.add(borrow.getBook());
            }
        }
        return borrowedBooks;
    }

    public boolean registerClient(Client client) {
        for (Client member : members) {
            if (member.getEmail().equals(client.getEmail())) {
                return false; 
            }
        }
        members.add(client);
        return true; 
    }

    public boolean registerAdmin(Admin admin) {
        for (Admin existingAdmin : admins) {
            if (existingAdmin.getEmail().equals(admin.getEmail())) {
                return false; 
            }
        }
        admins.add(admin);
        return true; 
    }

    private static Library instance = null;
    
    public static Library getInstance() {
        if (instance == null) {
            instance = new Library();
        }
        return instance;
    }

    private Library() {}
}

class Borrow {
    private Book book;
    private Client client;
    private String borrowDate;
    private String returnDate;

    public Borrow(Book book, Client client, String borrowDate, String returnDate) {
        this.book = book;
        this.client = client;
        this.borrowDate = borrowDate;
        this.returnDate = returnDate;
    }

    public Book getBook() {
        return book;
    }

    public Client getClient() {
        return client;
    }

    public String getBorrowDate() {
        return borrowDate;
    }

    public String getReturnDate() {
        return returnDate;
    }
}