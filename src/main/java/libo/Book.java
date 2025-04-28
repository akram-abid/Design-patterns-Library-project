package main.java.libo;

interface BookState {
    void handleRequest(Book book);
    String getStateName();
}

class AvailableState implements BookState {
    @Override
    public void handleRequest(Book book) {
        if (book.getQuntity() <= 0) {
            book.setState(new OutOfStockState());
        }
    }
    
    @Override
    public String getStateName() {
        return "AVAILABLE";
    }
}

class OutOfStockState implements BookState {
    @Override
    public void handleRequest(Book book) {
        if (book.getQuntity() > 0) {
            book.setState(new AvailableState());
        }
    }
    
    @Override
    public String getStateName() {
        return "OUT_OF_STOCK";
    }
}

public class Book {
    private String id;
    private String title;
    private String author;
    private int yearPublished;
    private int quntity;
    private BookState state;
    
    public Book(String id, String title, String author, int yearPublished, int quntity) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.yearPublished = yearPublished;
        this.quntity = quntity;
        
        if (this.quntity > 0) {
            this.state = new AvailableState();
        } else {
            this.state = new OutOfStockState();
        }
    }
    
    public void setState(BookState state) {
        this.state = state;
    }
    
    public BookState getState() {
        return state;
    }
    
    public String getStateAsString() {
        return state.getStateName();
    }
    
    public void updateState() {
        state.handleRequest(this);
    }
    
    public String getTitle() {
        return title;
    }
    
    public int getQuntity() {
        return quntity;
    }
    
    public void setQuntity(int quntity) {
        this.quntity = quntity;
        updateState(); 
    }
    
    public String getAuthor() {
        return author;
    }
    
    public String getId() {
        return id;
    }
    
    public int getYearPublished() {
        return yearPublished;
    }
    
    @Override
    public String toString() {
        return "Book{" +
               "Id = " + this.id +
               "title='" + title + '\'' +
               ", author='" + author + '\'' +
               ", yearPublished=" + yearPublished +
               ", quantity=" + quntity +
               ", state=" + state.getStateName() +
               '}';
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (!(obj instanceof Book)) return false;
        Book book = (Book) obj;
        return yearPublished == book.yearPublished &&
               title.equals(book.title) &&
               author.equals(book.author);
    }
}