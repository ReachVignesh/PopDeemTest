package jsoft.projects.photoclick.cart;

public class OrderDetails {

	private long id;
	private int uid;
	private int orderItemId;
	private String size;
	private int qty;
	private double price;
	private String mugImg;
	
	public long getId(){
		return id;
	}
	
	public int getUid(){
		return uid;
	}

	public int getOrderItemId(){
		return orderItemId;
	}
	
	public String getSize(){
		return size;
	}
	
	public int getQty(){
		return qty;
	}
	
	public double getPrice(){
		return price;
	}
	
	public String getMugImg(){
		return mugImg;
	}
	
	public void setId(int id){
		this.id = id;
	}
	
	public void setUid(int uid){
		this.uid = uid;
	}
	
	public void setOrderItemId(int orderItemId){
		this.orderItemId = orderItemId;
	}
	
	public void setSize(String size){
		this.size = size;
	}
	
	public void setQty(int qty){
		this.qty = qty;
	}
	
	public void setPrice(double price){
		this.price = price;
	}
	
	public void setMugImg(String mugImg){
		this.mugImg = mugImg;
	}
	
}
