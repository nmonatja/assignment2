import java.util.ArrayList;
import java.sql.Connection;
import java.sql.ResultSet;

/***********************************************
 *
 *
 *
 *
 ***********************************************/

public class Order extends DbItem {
	
	int order_id;  
	String order_date;
	String first_name;
	String last_name;
	String address;
	String phone;
	float total_cost;
	int shipped;
	String order_table;
	int deleted = 0;
	
	static final String database = "orderinfo";
	
	public Order(Integer order_id, String order_date, String first_name, String last_name, String address, String phone, Float total_cost, Integer shipped, String order_table) {
		if (order_id != null) {
			this.order_id = order_id; 
		}
		this.order_date = order_date;
		this.first_name = first_name;
		this.last_name = last_name;
		this.address = address;
		this.phone = phone;
		this.total_cost = total_cost;
		this.shipped = shipped;
		this.order_table = order_table;
	}
	
	public Boolean update(String order_date, String first_name, String last_name, String address, String phone, Float total_cost, Integer shipped, String order_table) {
		
		if (deleted != 0) {
			System.out.println("This order has been deleted from the db");
			return false;
		}
		
		int retVal = 0;
		
		ArrayList<String> clauseBuilder = new ArrayList<String>();
		if (order_date != null) {
			clauseBuilder.add("order_date = '"+order_date+"'");
		}
		if (first_name != null) {
			clauseBuilder.add("first_name = '"+first_name+"'");
		}
		if (last_name != null) {
			clauseBuilder.add("last_name = '"+last_name+"'");
		}
		if (address != null) {
			clauseBuilder.add("address = '"+address+"'");
		}
		if (phone != null) {
			clauseBuilder.add("phone = '"+phone+"'");
		}
		if (total_cost != null) {
			clauseBuilder.add("total_cost = "+total_cost);
		}
		if (shipped != null) {
			clauseBuilder.add("shipped = "+shipped);
		}
		
		String sql = String.join(", ", clauseBuilder);
		sql = "UPDATE orders SET "+sql+" WHERE order_id = "+this.order_id;
		
		try {
			Connection conn = openDbConnection(database);
			retVal = run(conn, sql);
			closeDbConnection(conn);
		} catch (Exception e) {
			System.out.println("Problem connecting to database:: " + e);
		}
		
		if (retVal==1) {
			return true;
		} 
		
		return false;
	}
	
	public Boolean remove() {
		
		if (deleted != 0) {
			System.out.println("This order has been deleted from the db");
			return false;
		}
		
		int retVal = 0;
		String sql = "DELETE from orders WHERE order_id = "+this.order_id;
		
		try {
			Connection conn = openDbConnection(database);
			retVal = run(conn, sql);
			closeDbConnection(conn);
		} catch (Exception e) {
			System.out.println("Problem connecting to database:: " + e);
		}
		
		if (retVal==1) {
			this.deleted = 1;
			return true;
		} 
		
		return false;
		
	}
	
	public ArrayList<OrderItem> getItems() {
		
		if (deleted != 0) {
			System.out.println("This order has been deleted from the db");
			return null;
		}
			
		ArrayList<OrderItem> items = new ArrayList<OrderItem>();
		
		try {
			Connection conn = openDbConnection(database);
			ResultSet rs = runResult(conn, "select * from "+order_table);
			
			if (rs != null) {
				while (rs.next()) {
					OrderItem item = new OrderItem(
						rs.getInt("item_id"), 
						rs.getString("product_id"),
						rs.getString("description"),
						rs.getFloat("item_price"),
						this.order_table
					);
					items.add(item);
				}
			}
			closeDbConnection(conn);
			
		} catch (Exception e) {
			System.out.println("Problem connecting to database:: " + e);
		}
		
		return items;
	}
	
	public Integer addItem(OrderItem item) {
		
		if (deleted != 0) {
			System.out.println("This order has been deleted from the db");
			return null;
		}
		
		int retVal = 0;
		
		String sql = "INSERT into "+this.order_table+" (product_id, description, item_price) VALUES ('"+item.product_id+"', '"+item.description+"', "+item.item_price+")";
		
		try {
			Connection conn = openDbConnection(database);
			retVal = run(conn, sql);
			
			if (retVal == 1) {
				ResultSet rs = runResult(conn, "SELECT LAST_INSERT_ID()");
				if (rs != null) {
					rs.first();
					retVal = rs.getInt(1);
				}
			}
			
			closeDbConnection(conn);
			
		} catch (Exception e) {
			System.out.println("Problem connecting to database:: " + e);
		}
		
		return retVal;
	}
	
	
	public String toString() {
		/* handy for printing out for testing */
		return "Id: "+order_id+" order_date: "+order_date+" first: "+first_name+" last: "+last_name+" address: "+address+" phone: "+phone+" total cost: "+Float.toString(total_cost)+" shipped: "+shipped+" order table: "+order_table;

	}
	
}