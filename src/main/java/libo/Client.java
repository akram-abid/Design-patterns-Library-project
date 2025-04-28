package main.java.libo;
import java.util.ArrayList;

interface UserI {
    //boolean borrow (Book book);
    void returnBook (String id);
    void printBorrowedBooks();
}


public class Client implements UserI{
    private String name;
    private String surname;
    private String email;
    private String password;
    private int id;
    private boolean isAdmin;


    //private ArrayList<Book> borrowedBooks = new ArrayList<>();


    public Client(String name, String surname, String email, String password, int id) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.id = id;
        this.isAdmin = false;
    }

    public boolean borrow(Book book) {
        if(Library.getInstance().borrowBook(book.getId() , this)) {
            System.out.println("Book borrowed: " + book.getTitle());
        } else {
            System.out.println("Book not available for borrowing.");
            return false;
        }
        return true; 
    }

    @Override
    public void returnBook(String id) {
        if(Library.getInstance().returnBook(id)) {
            System.out.println("Book returned successfully.");
        } else {
            System.out.println("Book not found or not borrowed.");
        }
    }

    @Override
    public void printBorrowedBooks() {
        System.out.println("Borrowed books:");
        for (Book book : Library.getInstance().getBorrowedBooksByClient(this.email)) {
            System.out.println(book);
        }
    }

    public String getEmail() {
        return email;
    }

    @Override
    public String toString(){
        return "Name = " + this.name + " Email = " + this.email ;
    }
}



class Admin implements UserI {
    private String name;
    private String surname;
    private String email;
    private String password;
    private int id;
    private boolean isAdmin;
    private ArrayList<Book> borrowedBooks = new ArrayList<>();
    public Admin(String name, String surname, String email, String password, int id) {
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.id = id;
        this.isAdmin = true;
    }

    @Override
    public void returnBook(String id) {
        if(Library.getInstance().returnBook(id)) {
            System.out.println("Book returned successfully.");
        } else {
            System.out.println("Book not found or not borrowed.");
        }
    }

    @Override
    public void printBorrowedBooks() {
        System.out.println("Borrowed books:");
        for (Book book : Library.getInstance().getBorrowedBooksByClient(this.email)) {
            System.out.println(book);
        }
    }


    public void addBook(Book book) {
        Library.getInstance().addBook(book);
    }
    public void viewAllUsers() {
        Library.getInstance().viewAllUsers();
    }
    public String getEmail() {
        return email;
    }
    @Override
    public String toString(){
        return "Name = " + this.name + " Email = " + this.email ;
    }
}



interface UserFactoryI {
    UserI createUser(String name, String surname, String email, String password, int id);
}
class ClientFactory implements UserFactoryI {
    @Override
    public UserI createUser(String name, String surname, String email, String password, int id) {
        return new Client(name, surname, email, password, id);
    }
}

class AdminFactory implements UserFactoryI {
    @Override
    public UserI createUser(String name, String surname, String email, String password, int id) {
        return new Admin(name, surname, email, password, id);
    }
}
