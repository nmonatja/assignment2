import java.util.ArrayList;
import java.sql.Connection;
import java.sql.ResultSet;

/***********************************************
 *
 *
 *
 *
 ***********************************************/

public class OrderItem extends DbItem { 
	
	int item_id;
	String product_id;
	String description;
	float item_price;
	String order_table;
	int deleted = 0;
	
	static final String database = "orderinfo";
	
	public OrderItem(Integer item_id, String product_id, String description, Float item_price, String order_table) {
		if (item_id != null) {
			this.item_id = item_id;
		}
		this.product_id = product_id;
		this.description = description;
		this.item_price = item_price;
		this.order_table = order_table;
	}
	
	public Boolean update(String product_id, String description, Float item_price) throws UpdateException {
		
		if (deleted != 0) {
			System.out.println("This item has been deleted from the order");
			return false;
		}
		
		int retVal = 0;
		
		ArrayList<String> clauseBuilder = new ArrayList<String>();
		if (product_id != null) {
			clauseBuilder.add("product_id = '"+product_id+"'");
		}
		if (description != null) {
			clauseBuilder.add("description = '"+description+"'");
		}
		if (item_price != null) {
			clauseBuilder.add("item_price = "+item_price);
		}
		
		String sql = String.join(", ", clauseBuilder);
		sql = "UPDATE "+this.order_table+" SET "+sql+" WHERE item_id = "+this.item_id;
		
		try {
			Connection conn = openDbConnection(database);
			retVal = run(conn, sql);
			closeDbConnection(conn);
		} catch (Exception e) {
			throw new UpdateException("database: "+database+" sql:"+sql+" : "+e);
		}
		
		if (retVal==1) {
			return true;
		} 
		
		return false;
	}
	
	public Boolean remove() throws DeleteException {
		if (deleted != 0) {
			System.out.println("This item has been deleted from the order");
			return false;
		}
		
		int retVal = 0;
		String sql = "DELETE from "+this.order_table+" WHERE item_id = "+this.item_id;
		
		try {
			Connection conn = openDbConnection(database);
			retVal = run(conn, sql);
			closeDbConnection(conn);
		} catch (Exception e) {
			throw new DeleteException("database: "+database+" sql:"+sql+" : "+e);
		}
		
		if (retVal==1) {
			this.deleted = 1;
			return true;
		} 
		
		return false;
	}
	
	public String toString() {
		/* handy for printing out for testing */
		return "Id: "+item_id+" product id: "+product_id+" desc: "+description+" price: "+Float.toString(item_price)+" order table: "+order_table;
	}

}