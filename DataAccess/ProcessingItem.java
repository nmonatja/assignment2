import java.util.ArrayList;
import java.sql.Connection;
import java.sql.ResultSet;

/***********************************************
 *
 *
 *
 *
 ***********************************************/


public class ProcessingItem extends DbItem implements InventoryItem {
	
	
	String product_code;  // leaftech refers to this as productid
	String description;
	int quantity;
	float price; 
	int deleted = 0;
	
	static final String database = "leaftech";

	public ProcessingItem(String code, String desc, int quant, float cost) {
		product_code = code;
		description = desc;
		quantity = quant;
		price = cost;
	}
	
	/* update an existing inventory record based on the product code, if successful returns true */
	public Boolean update(String product_code, String description, Integer quantity, Float price) throws UpdateException {
		
		if (deleted != 0) {
			System.out.println("This item has been deleted from the db");
			return false;
		}
		
		int retVal = 0;
		ArrayList<String> clauseBuilder = new ArrayList<String>();
		
		if (product_code != null ) {
			clauseBuilder.add("productid = '"+product_code+"'");
		}
		if (description != null) {
			clauseBuilder.add("productdescription = '"+description+"'");
		}
		if (quantity != null) {
			clauseBuilder.add("productquantity = "+quantity);
		}
		if (price != null) {
			clauseBuilder.add("productprice = "+price);
		}
		String sql = String.join(", ", clauseBuilder);
		sql = "UPDATE Processing SET "+sql+" WHERE productid = '"+this.product_code+"'";
		
		//System.out.println(sql);
		
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
			System.out.println("This item has been already deleted from the db");
			return false;
		}
		
		int retVal = 0;
		String sql = "DELETE from Processing WHERE productid = '"+this.product_code+"'";
		
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
		return "Id: "+product_code+" desc: "+description+" quantity: "+quantity+" price: "+Float.toString(price);
	}
	
	
}




