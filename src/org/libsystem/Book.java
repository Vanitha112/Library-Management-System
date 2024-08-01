package org.libsystem;

public class Book {
	private int id;
	private String title;
	private String author;
	private String publisher;
	private int yearPublished;
	private int bookCount;
	private boolean isBorrowed= false;
	
	
	

	public int getId() {
		return id;
	}


	public void setId(int id) {
		this.id = id;
	}


	public String getTitle() {
		return title;
	}


	public void setTitle(String title) {
		this.title = title;
	}


	public String getAuthor() {
		return author;
	}


	public void setAuthor(String author) {
		this.author = author;
	}


	public String getPublisher() {
		return publisher;
	}


	public void setPublisher(String publisher) {
		this.publisher = publisher;
	}


	public int getYearPublished() {
		return yearPublished;
	}


	public void setYearPublished(int yearPublished) {
		this.yearPublished = yearPublished;
	}


	public int getBookCount() {
		return bookCount;
	}


	public void setBookCount(int bookCount) {
		this.bookCount = bookCount;
	}
	public Book() {
		
	}


	public Book(int id, String title, String author, String publisher, int yearPublished, int bookCount) {
		this.id = id;
		this.title = title;
		this.author = author;
		this.publisher = publisher;
		this.yearPublished = yearPublished;
		this.bookCount = bookCount;
	}
	
	
	public boolean isBorrowed() {
		return isBorrowed;
	}
	
	
	public void setBorrowed(boolean isBorrowed) {
		this.isBorrowed = isBorrowed;
	}

	@Override
	public String toString() {
		return "\nBook Id is:"+id+"\nTitle is:"+title+"\nAuthor is:"+author+"\nPublisher is:"+publisher+"\nPublished year:"+yearPublished+"\nNumber of books available:"+bookCount+"\n";
		
	}

	

	

}
