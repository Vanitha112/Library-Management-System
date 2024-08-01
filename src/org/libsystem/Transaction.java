package org.libsystem;

import java.util.Date;

public class Transaction {
	private Book book;
	private Member member;
	private Date date;
	private String type;
	public Book getBook() {
		return book;
	}
	public Member getMember() {
		return member;
	}
	public Date getDate() {
		return date;
	}
	public String getType() {
		return type;
	}
	public Transaction(Book book, Member member, Date date, String type) {
		this.book = book;
		this.member = member;
		this.date = date;
		this.type = type;
	}
	public Book getBooks() {
		return book;
	}
	public Member getMembers() {
		return member;
	}
	@Override
	public String toString() {
		return "\nBook is:\n"+book +
				"\nMember is:\n"+member+"Date is:\n"+date+"\nType is:\n"+type;
	}
	
	
		

}
