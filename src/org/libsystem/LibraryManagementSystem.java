package org.libsystem;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.List;
import java.util.Scanner;

public class LibraryManagementSystem  {
	private static Library library= new Library();
	private static Scanner scanner = new Scanner(System.in);
	private static Connection conn = DatabaseConnection.getconnection();
	public static void main(String[] args) {
		
		int choice;
		do {
			System.out.println("------------Library Management System-------------");
			System.out.println("1. Add Book/Add Member");
			System.out.println("2. Book issue/Book return");
			System.out.println("3. Display Book details/Display Member details");
			System.out.println("4. Search Book/Member");
			System.out.println("5. View Transaction Details");
			System.out.println("6. Exit");
			System.out.println("Enter the number: ");
			choice = scanner.nextInt();
			switch(choice) {
			case 1:
				System.out.println("Choose a number: ");
				System.out.println("1.Add Book");
				System.out.println("2.Add Member");
				System.out.println("Enter a number: ");
				int number1 = scanner.nextInt();
				switch(number1) {
				case 1:
					addBook();
					break;
				case 2:
					addMember();
					break;
					default:
						System.out.println("Invalid number.Please try again..");
				}
				break;
			case 2:
				System.out.println("Choose a number: ");
				System.out.println("1.Book Issue");
				System.out.println("2.Book Return");
				System.out.println("Enter the number: ");
				int number2 = scanner.nextInt();
				switch(number2) {
				case 1:
					bookIssue();
					break;
				case 2:
					bookReturn();
					break;
				}
				break;
			case 3:
				System.out.println("Choose the number");
				System.out.println("1.Display Book Details");
				System.out.println("2.Display Member Details");
				int number3 =scanner.nextInt();
				switch(number3) {
				case 1:
					displayAllBooks();
					break;
				case 2:
					displayAllMembers();
					break;
				}
				
				break;
			case 4:
				System.out.println("Choose a number:");
				System.out.println("1.Search Book");
				System.out.println("2.Search Member");
				System.out.println("Enter the number");
				int number4= scanner.nextInt();
				switch(number4){
					case 1:
						searchBookByTitle();
						break;
					case 2:
						searchMemberByName();
						break;
				}
				break;
			case 5:
				viewTransaction();
				break;
			case 6:
				break;
				default:
					System.out.println("Invalid choice.Please try again...");
			}
		}
		while(choice!=6);
	}
	public static void  addBook() {
		scanner.nextLine();
		String query = "insert into Books(Title,Author,Publisher,Year,Quantity) values(?,?,?,?,?);";
		System.out.println("Enter book title");
		String title = scanner.nextLine();
		System.out.println("Enter book author");
		String author = scanner.nextLine();
		System.out.println("Enter book publisher");
		String publisher = scanner.nextLine();
		System.out.println("Enter book published year");
		int year = scanner.nextInt();
		System.out.println("Enter number of books: ");
	    int bookCount = scanner.nextInt();
	    //Book book = new Book(0,title,author,publisher,year,bookCount);
	    try {
	    	Connection conn = DatabaseConnection.getconnection();
	    	PreparedStatement st = conn.prepareStatement(query);    
	    	st.setString(1, title);
	    	st.setString(2, author);
	    	st.setString(3, publisher);
	    	st.setInt(4, year);
	    	st.setInt(5, bookCount);
	    	int n=st.executeUpdate();
	    	if(n>0) {
	    		System.out.println(n+" "+"Book added successfully");
	    	}
	    	else
	    	{
	    		System.out.println("books are not added");
	    	}
			conn.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
	    
	    
	    //library.addBook(book);
	    //System.out.println("Book added successfully.");
	}
	public static void addMember() { 
		String query = "insert into Members(Member_name,Address,Phone) values(?,?,?);";
		scanner.nextLine();
		System.out.println("Enter member name");
		String name = scanner.nextLine();
		System.out.println("Enter member address");
		String address = scanner.nextLine();
		System.out.println("Enter member phone");
		String phone = scanner.nextLine();
		int MemberId=0;
		//int memberId = Member.createMemberId();
		//Member member = new Member(0,name,address,phone);
		try {
			Connection conn = DatabaseConnection.getconnection();
	    	PreparedStatement st = conn.prepareStatement(query);
	    	st.setString(1, name);
	    	st.setString(2, address);
	    	st.setString(3, phone);
	    	int n= st.executeUpdate();
	    	if(n>0) {
	    		System.out.println(n+" "+"member added successfully");
	    	}
	    	else
	    	{
	    		System.out.println("Members are not added");
	    	}
	    	String memberIDQuery ="select Member_id from Members where Member_name=?;";
	    	PreparedStatement memberIDStmt = conn.prepareStatement(memberIDQuery);
	    	memberIDStmt.setString(1, name);
	    	ResultSet memberIDresultset= memberIDStmt.executeQuery();
	    	while(memberIDresultset.next()) {
	    		MemberId= memberIDresultset.getInt("Member_id");
	    	}
	    	System.out.println("Your MemberId is:"+" "+MemberId);
	    	conn.close();
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
		
		
		//library.addMember(member);
		//System.out.println("MemberId is:  "+ memberId);
		//System.out.println("Member added Successfully.");
	}
	public static void bookIssue() {
		System.out.println("Enter Member ID: ");
		int memberId = scanner.nextInt();
		System.out.println("Enter Book Id: ");
		int bookId = scanner.nextInt();
		library.issueBook(bookId,memberId);
		
	}
	public static void bookReturn() {
		System.out.println("Enter Book ID: ");
		int bookId = scanner.nextInt();
		System.out.println("Enter member ID: ");
		int memberId = scanner.nextInt();
		library.returnBook(bookId, memberId);
	}
	public static void displayAllBooks() {
		
		String query = "select *from Books;";
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			while(rs.next()) {
				Book book = new Book();
				book.setId(rs.getInt("Book_id")); 
				book.setTitle(rs.getString("Title"));
				book.setAuthor(rs.getString("Author")); 
				book.setPublisher(rs.getString("Publisher"));
				book.setYearPublished(rs.getInt("Year")); 
				book.setBookCount(rs.getInt("Quantity")); 
				library.addBook(book);
				List<Book> books = library.getAllBooks();
				if(books.isEmpty())
					System.out.println("No Books in the library");
				else
					System.out.println("The Books are:");
				for(Book book1:books) {
					System.out.println(book1);
				}
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
		
		
		}
	public static void displayAllMembers() {
	
		String query = " select m.Member_id,m.Member_name,m.Address,m.Phone,"
				+ "b.Book_id,b.Title,"+ "b.Author,b.Publisher,b.Year from Members m "
						+ "join Books b on m.Book_id=b.Book_id;;";
		try {
			Statement st = conn.createStatement();
			ResultSet rs = st.executeQuery(query);
			while(rs.next()) {
				Member member = new Member();
				Book book = new Book();
				member.setId(rs.getInt("Member_id")); 
				member.setName(rs.getString("Member_name"));
				member.setAddress(rs.getString("Address")); 
				member.setPhone(rs.getString("Phone"));	
				book.setId(rs.getInt("Book_id"));
				book.setTitle(rs.getString("Title"));
				book.setAuthor(rs.getString("Author"));
				book.setPublisher(rs.getString("Publisher"));
				book.setYearPublished(rs.getInt("year"));
				member.setBorrowedBook(book);
				library.addMember(member);
				List<Member> members = library.getAllMembers();
				if(members.isEmpty())
					System.out.println("No Members in the library");
				else 
					System.out.println("The Members are: ");
				for(Member member1:members) {
					System.out.println(member1);
				}
			}
			
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
		
	}
	public static void searchBookByTitle() {
		scanner.nextLine();
		System.out.println("Enter the book title");
		String title = scanner.nextLine();
		library.searchBookByTitle(title);
		/*if(book!=null)
			System.out.println("Book found");
		else
			System.out.println("Book not found");*/
	}
	public static void searchMemberByName() {
		scanner.nextLine();
		
		System.out.println("Enter member name to search");
		String name = scanner.nextLine();
		library.searchMemberByName(name);
		/*if(member != null)
			System.out.println("Member found");
		else
			System.out.println("Member not found");*/
	}
	public static void viewTransaction() {
		System.out.println("Transaction History");
		List<Transaction> transactions = library.getAllTransaction();
		for(Transaction transaction : transactions) {
			System.out.println(transaction);
		}
	}
	


}

