package org.libsystem;

public class Member {
	//private static int memberId=1000;
	private int id;
	private String name;
	private String address;
	private String phone;
	private int borrowBookCount=0;
	private int returnBookCount=0;
	private Book borrowedBook;
	
/*	public static int getMemberId() {
		return memberId;
	}

	public static void setMemberId(int memberId) {
		Member.memberId = memberId;
	}*/

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public String getPhone() {
		return phone;
	}

	public void setPhone(String phone) {
		this.phone = phone;
	}

	public void setName(String name) {
		this.name = name;
	}

	public int getBorrowBookCount() {
		return borrowBookCount;
	}

	public void setBorrowBookCount(int borrowBookCount) {
		this.borrowBookCount = borrowBookCount;
	}

	public int getReturnBookCount() {
		return returnBookCount;
	}

	public void setReturnBookCount(int returnBookCount) {
		this.returnBookCount = returnBookCount;
	}
	
	
	public Book getBorrowedBook() {
		return borrowedBook;
	}

	public void setBorrowedBook(Book borrowedBook) {
		this.borrowedBook = borrowedBook;
	}

	public void isBorrow() {
		borrowBookCount++;
		
	}
	public void isReturn() {
		returnBookCount++;
	}

/*	public static int createMemberId() {
	     memberId++;
		int memberid = memberId;
		return memberid;
	}*/

	public Member(int id, String name, String address, String phone) {
		
		this.id = id;
		this.name = name;
		this.address = address;
		this.phone = phone;
	}
	public Member() {
		
	}

	@Override
	public String toString() {
		return "\nMember Id is:"+id+"\nName is:"+name+"\nAddress is:"+address+"\nPhone number is:"+phone
				+"\nBorrowedBookCount:"+borrowBookCount+"\nreturnedBookCount:"+returnBookCount+"\nBorrowedBook:"+borrowedBook+"\n";
	}
	

	
	
	

}
