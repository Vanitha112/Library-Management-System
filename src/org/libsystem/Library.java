package org.libsystem;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
//import java.sql.Statement;
//import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class Library {
	private List<Book> books = new ArrayList<>();
	private List<Member> members = new ArrayList<>();
	private List<Transaction> transactions = new ArrayList<>();
	private static Connection conn = DatabaseConnection.getconnection();
	public void addBook(Book book) {
		books.add(book);
	}
	public void addMember(Member member) {
		members.add(member);
	}
	public List<Book> getAllBooks(){
		return books;
	}
	/*public void issueBook(int memberId,int bookId) {
		Book book = findBookByBookId(bookId);
		Member member = findMemberByMemberId(memberId);
		if(book!=null && member !=null && !book.isBorrowed()) {
			int booksAvailable = book.getBookCount();
			--booksAvailable;
			book.setBookCount(booksAvailable);
			if(book.getBookCount()>0) {
				book.setBorrowed(false);
			}
			else
			{book.setBorrowed(true);}
				member.isBorrow();
				member.setBorrowedBook(book);
				transactions.add(new Transaction(book,member,new Date(),"borrow"));
				System.out.println("Book Borrowed:"+" "+book);
				
		}
			else {
				System.out.println("Books are already issued or other issue");
			
		}
	}*/
	/*public void returnBook(int bookId,int memberId) {
		Book book = findBookByBookId(bookId);
		Member member=findMemberByMemberId(memberId);
	    Book BorrowedBook =  member.getBorrowedBook();
		if(book!=null && member !=null && BorrowedBook !=null) {
			int bookAvailable=book.getBookCount();
			++bookAvailable;
			book.setBookCount(bookAvailable);
			book.setBorrowed(false);
			member.isReturn();
			member.setBorrowedBook(null);
			transactions.add(new Transaction(book,member,new Date(),"return"));
			System.out.println("Book Returned"+ " "+book);}
		else
			System.out.println("Book is not Borrowed or member not found");
		
	}*/
	public List<Member> getAllMembers(){
		return members;
	}
	public List<Transaction> getAllTransaction(){
		return transactions;
	}
	public void searchBookByTitle(String title) {
		String query = "select *from Books where Title=?;";
		try {
			PreparedStatement querystmt = conn.prepareStatement(query);
			querystmt.setString(1, title);
			ResultSet rsquerystmt = querystmt.executeQuery();
			while(rsquerystmt.next()) {
				Book book = new Book();
				book.setId(rsquerystmt.getInt("Book_id"));
				book.setTitle(rsquerystmt.getString("Title"));
				book.setAuthor(rsquerystmt.getString("Author"));
				book.setPublisher(rsquerystmt.getString("Publisher"));
				book.setYearPublished(rsquerystmt.getInt("year"));
				book.setBookCount(rsquerystmt.getInt("Quantity"));
				books.add(book);
			}
			List<Book> books = getAllBooks();
			if(books== null) {
				System.out.println("Book is not available");
			}
			else {
				System.out.println("The Book is:");
			}
			for(Book book:books) {
				System.out.println(book);
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}
	/*	for(Book book:books) {
			if(book.getTitle().equalsIgnoreCase(title))
				return book;
		}
		return null;*/
	}
	public void searchMemberByName(String name) {
		String Query = "select *from Members where Member_name=?;";
		try {
			PreparedStatement Querystmt = conn.prepareStatement(Query);
			Querystmt.setString(1, name);
			ResultSet rsQuerystmt = Querystmt.executeQuery();
			while(rsQuerystmt.next()) {
				Member member = new Member();
				member.setId(rsQuerystmt.getInt("Member_id"));
				member.setName(rsQuerystmt.getString("Member_name"));
				member.setAddress(rsQuerystmt.getString("Address"));
				member.setPhone(rsQuerystmt.getString("Phone"));
				Book book = new Book();
				book.setId(rsQuerystmt.getInt("Book_id"));
			}
			
		} catch (Exception e) {
			// TODO: handle exception
		}
		/*for(Member member:members) {
			if(member.getName().equalsIgnoreCase(name))
				return member;
			}
		return null;*/
	}
	/*public Book findBookByBookId(int bookId) {
		for(Book book:books) {
			if(book.getId() == bookId)
				return book;
		}
		System.out.println("Book ID "+bookId+" not found in the library");
		return null;
	}
	public Member findMemberByMemberId(int memberId) {
		for(Member member:members) {
			if(member.getId()==memberId)
				return member;
		}
		System.out.println("Member ID "+memberId+" not found in the library");
		return null;
	}*/
	/*public void display(Member member) {
		List<Transaction> transaction = getAllTransaction()
		if(transaction.member.i)
	}*/
	public   void issueBook(int bookId, int memberId) {
		//LocalDate issueDate = LocalDate.now();
		
		//LocalDate returnDate = issueDate.plusMonths(1);
		Date issueDate = new Date(System.currentTimeMillis());
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(issueDate);
		calendar.add(Calendar.MONTH, 1);
		Date returnDate = new Date(calendar.getTimeInMillis());
		boolean book = searchBookByBookId(bookId);
		boolean member = searchByMemberId(memberId);
		int BorrowedBookCount=0;
		if(book==true && member==true) {
		String query= "select Quantity from Books where Book_id=?;";
		try {
			PreparedStatement st = conn.prepareStatement(query);
			st.setInt(1, bookId);
			ResultSet rs = st.executeQuery();
			while(rs.next()) {
				int bookCount = rs.getInt("Quantity");
			
			if(bookCount>0) {
				// Book issue table updation
				String issue_query = "insert into Issued_books(Book_id,Member_id,issue_date,return_date) values(?,?,?,?);";
				PreparedStatement issueStmt = conn.prepareStatement(issue_query);
				issueStmt.setInt(1, bookId);
				issueStmt.setInt(2, memberId);
				issueStmt.setDate(3, new java.sql.Date(issueDate.getTime()));
				issueStmt.setDate(4, new java.sql.Date(returnDate.getTime()));
				int insertRow=issueStmt.executeUpdate();
				bookCount--;
				//Book count updation
				String update_query = "update Books set Quantity=? where Book_id=?;";
				PreparedStatement updateStmt = conn.prepareStatement(update_query);
				updateStmt.setInt(1, bookCount);
				updateStmt.setInt(2, bookId);
				int updateRow=updateStmt.executeUpdate();
				//Taking Borrowed Book Count
				String BorrowedBookCountQuery=" select BorrowedBookCount from Members where Member_Id = ?;";
				PreparedStatement stmt = conn.prepareStatement(BorrowedBookCountQuery);
				stmt.setInt(1, memberId);
				ResultSet rsstmt = stmt.executeQuery(BorrowedBookCountQuery);
				while(rsstmt.next()) {
					BorrowedBookCount=rsstmt.getInt("BorrowedBookCount");
				}
				BorrowedBookCount++;
				//Book updation in member table
				String updateMember_query=" update Members set Book_id=?,BorrowedBookCount=?  where Member_id=?;";
				PreparedStatement memberUpdateStmt = conn.prepareStatement(updateMember_query);
				memberUpdateStmt.setInt(1, bookId);
				memberUpdateStmt.setInt(2, BorrowedBookCount);
				memberUpdateStmt.setInt(3, memberId);
				int memberUpdateRow = memberUpdateStmt.executeUpdate();
				if(insertRow>0 && updateRow>0 && memberUpdateRow>0) {
					System.out.println("Book issued successfully with issue date"+" "+issueDate +
							"and return date wiil be"+" "+returnDate  );
				}
			}
			else {
				System.out.println("Books are not available");
			}
			
			}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);
		}}
		else {
			System.out.println("Book and member are not found");
		}
	}
	public void returnBook(int bookId,int memberId) {
		boolean book = searchBookByBookId(bookId);
		boolean member = searchByMemberId(memberId);
		boolean isReturned=true;
		int ReturnedBookCount = 0;
		String findQuery = "select isReturned from issued_books where (book_id=? && member_id=?);";
		try {
			PreparedStatement FindQueryStmt = conn.prepareStatement(findQuery);
			FindQueryStmt.setInt(1, bookId);
			FindQueryStmt.setInt(2, memberId);
			ResultSet rs = FindQueryStmt.executeQuery();
			while(rs.next()) {
					isReturned = rs.getBoolean("isReturned");
				}
			if(book==true && member==true && isReturned==false) {
					String updateReturnQuery="update issued_books set isReturned=True where (Book_id =? && Member_id=?);";
					PreparedStatement updateReturnQueryStmt = conn.prepareStatement(updateReturnQuery);
					updateReturnQueryStmt.setInt(1, bookId);
					updateReturnQueryStmt.setInt(2, memberId);
					int updateReturnRow = updateReturnQueryStmt.executeUpdate();
					String bookCountQuery= "select Quantity from Books where Book_id=?;"; 
					PreparedStatement bookCountQueryStmt = conn.prepareStatement(bookCountQuery);
					bookCountQueryStmt.setInt(1, bookId);
					ResultSet resultset = bookCountQueryStmt.executeQuery();
					while(resultset.next()) {
					int bookCount = resultset.getInt("Quantity");
					bookCount++;
					String update_query = "update Books set Quantity=? where Book_id=?;";
					PreparedStatement updateStmt = conn.prepareStatement(update_query);
					updateStmt.setInt(1, bookCount);
					updateStmt.setInt(2, bookId);
					int updateQuantityRow=updateStmt.executeUpdate();
					String ReturnedBookCountQuery = " select ReturnedBookCount from Members where Member_Id = ?;";
					PreparedStatement ReturnedBookCountStmt = conn.prepareStatement(ReturnedBookCountQuery);
					ReturnedBookCountStmt.setInt(1, memberId);
					ResultSet resultsetReturnedBookCount = ReturnedBookCountStmt.executeQuery();
					while(resultsetReturnedBookCount.next()) {
						ReturnedBookCount= resultsetReturnedBookCount.getInt(ReturnedBookCount);
					}
					ReturnedBookCount++;
					String UpdateMemberQuery= "update Members set Book_id=?,ReturnedBookCount=? where Member_id=?; ";
					PreparedStatement  UpdateMemberStmt = conn.prepareStatement(UpdateMemberQuery);
					UpdateMemberStmt.setInt(1, 0);
					UpdateMemberStmt.setInt(2, ReturnedBookCount);
					UpdateMemberStmt.setInt(3, memberId);
					int updateMemberRow= UpdateMemberStmt.executeUpdate();
					if(updateReturnRow>0 && updateQuantityRow>0 && updateMemberRow>0) {
						System.out.println("Book returned successfully...");
					}
				}}
				else {
					System.out.println("Book and member are not found or Book not borrowed");
				}
		} catch (Exception e) {
			// TODO: handle exception
			System.out.println(e);}
		
	}
	public boolean searchBookByBookId(int bookId) {
		String query =  "select *from Books where Book_id=?;";
		try {
			PreparedStatement st = conn.prepareStatement(query);
			st.setInt(1, bookId);
			ResultSet rs = st.executeQuery();
			return rs.next();
			
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
	
		
		
	}
	public boolean searchByMemberId(int memberId) {
		String query = "select *from Members where Member_id=?;";
		try {
			PreparedStatement st = conn.prepareStatement(query);
			st.setInt(1, memberId);
			ResultSet rs = st.executeQuery();
			return rs.next();
		} catch (Exception e) {
			// TODO: handle exception
			return false;
		}
		
	}


}
