import java.util.ArrayList;
import java.sql.Connection;
import java.sql.ResultSet;


/***********************************************
 *
 *
 *
 *
 ***********************************************/
 
public class ShrubInventory extends DbItem implements Inventory<ShrubItem> {
	
	static final String database = "inventory";
	
	public ShrubInventory() {
		
	}

	/* return a list of all items of this type */
	public ArrayList<ShrubItem> getAll() {
		
		ArrayList<ShrubItem> allShrubs = new ArrayList<ShrubItem>();
		
		try {
			Connection conn = openDbConnection(database);
			ResultSet rs = runResult(conn, "select * from Shrubs");
		
			if (rs != null) {
				while (rs.next()) {
					ShrubItem si = new ShrubItem(
						rs.getString("product_code"), 
						rs.getString("description"), 
						rs.getInt("quantity"),
						rs.getFloat("price")
					);
					allShrubs.add(si);
				}
			}
			closeDbConnection(conn);
			
		} catch (Exception e) {
			System.out.println("Problem connecting to database:: " + e);
		}
		
		return allShrubs;
	}
	
	/* get a specific entry */
	public ShrubItem getById(String id) {
		
		try {
			Connection conn = openDbConnection(database);
			ResultSet rs = runResult(conn, "select * from Shrubs where product_code = '"+id+"'");
			
			if (rs != null) {
				rs.first();
				ShrubItem si = new ShrubItem(
					rs.getString("product_code"), 
					rs.getString("description"), 
					rs.getInt("quantity"),
					rs.getFloat("price")
				);
				
				closeDbConnection(conn);
				return si;
				
			}
		} catch (Exception e) {
			System.out.println("Problem connecting to database:: " + e);
		}
		
		return null;
		
	}
	
	/* search for an entry */
	public ArrayList<ShrubItem> search(String product_code, String description, Integer quantity, Float price) {
		ArrayList<ShrubItem> searchShrubs = new ArrayList<ShrubItem>();
		
		ArrayList<String> clauseBuilder = new ArrayList<String>();
		if (product_code != null) {
			clauseBuilder.add("product_code = '"+product_code+"'");
		}
		if (description != null) {
			clauseBuilder.add("description like '%"+description+"%'");
		}
		if (quantity != null) {
			// TODO: add a greater or less than feature
			clauseBuilder.add("quantity = "+quantity);
		}
		if (price != null) {
			// TODO: add a greater or less than feature
			clauseBuilder.add("CAST(price AS DECIMAL) = CAST("+Float.toString(price)+" AS DECIMAL)");
		}
		
		String sql = String.join(" AND ", clauseBuilder);
		sql = "SELECT * from Shrubs WHERE "+sql;
		
		//System.out.println(sql);
		
		try {
			Connection conn = openDbConnection(database);
			ResultSet rs = runResult(conn, sql);
			
			if (rs != null) {
				while (rs.next()) {
					ShrubItem si = new ShrubItem(
						rs.getString("product_code"), 
						rs.getString("description"), 
						rs.getInt("quantity"),
						rs.getFloat("price")
					);
					searchShrubs.add(si);
				}
			}
			closeDbConnection(conn);
			
		} catch (Exception e) {
			System.out.println("Problem connecting to database:: " + e);
		}
		
		return searchShrubs;
	}
	
	/* add a new type of inventory, if successful returns true */
	public Boolean add(String product_code, String description, int quantity, float price) {
		
		int retVal = 0;
		
		String sql = "INSERT into Shrubs (product_code, description, quantity, price) VALUES ('"+
			product_code+"', '"+description+"', "+quantity+", "+price+")";
		
		try {
			
			Connection conn = openDbConnection(database);
			retVal = run(conn, sql);
			
			closeDbConnection(conn);
			
		} catch (Exception e) {
			System.out.println("Problem connecting to database:: " + e);
		}
		
		if (retVal == 1) {
			return true;
		}
		return false;
		
	}
	
	public Boolean add(ShrubItem item) {
		//public <T extends InventoryItem> Boolean add(T item);
		
		int retVal = 0;
		
		String sql = "INSERT into Shrubs (product_code, description, quantity, price) VALUES ('"+
			item.product_code+"', '"+item.description+"', "+item.quantity+", "+item.price+")";
		
		try {
			Connection conn = openDbConnection(database);
			retVal = run(conn, sql);
			
			closeDbConnection(conn);
			
		} catch (Exception e) {
			System.out.println("Problem connecting to database:: " + e);
		}
		
		if (retVal == 1) {
			return true;
		}
		
		return false;
	}
	
}