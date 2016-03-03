import java.util.ArrayList;
import java.sql.Connection;
import java.sql.ResultSet;


/***********************************************
 *
 *
 *
 *
 ***********************************************/
 
public class CultureboxInventory extends DbItem implements Inventory<CultureboxItem> {
	
	static final String database = "leaftech";
	
	public CultureboxInventory() {
		
	}

	/* return a list of all items of this type */
	public ArrayList<CultureboxItem> getAll() {
		
		ArrayList<CultureboxItem> allCultureboxes = new ArrayList<CultureboxItem>();
		
		try {
			Connection conn = openDbConnection(database);
			ResultSet rs = runResult(conn, "select * from Cultureboxes");
		
			if (rs != null) {
				while (rs.next()) {
					CultureboxItem si = new CultureboxItem(
						rs.getString("productid"), 
						rs.getString("productdescription"), 
						rs.getInt("productquantity"),
						rs.getFloat("productprice")
					);
					allCultureboxes.add(si);
				}
			}
			closeDbConnection(conn);
			
		} catch (Exception e) {
			System.out.println("Problem connecting to database:: " + e);
		}
		
		return allCultureboxes;
	}
	
	/* get a specific entry */
	public CultureboxItem getById(String id) {
		
		try {
			Connection conn = openDbConnection(database);
			ResultSet rs = runResult(conn, "select * from Cultureboxes where productid = '"+id+"'");
			
			if (rs != null) {
				rs.first();
				CultureboxItem si = new CultureboxItem(
					rs.getString("productid"), 
					rs.getString("productdescription"), 
					rs.getInt("productquantity"),
					rs.getFloat("productprice")
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
	public ArrayList<CultureboxItem> search(String product_code, String description, Integer quantity, Float price) {
		ArrayList<CultureboxItem> searchCultureboxes = new ArrayList<CultureboxItem>();
		
		ArrayList<String> clauseBuilder = new ArrayList<String>();
		if (product_code != null) {
			clauseBuilder.add("productid = '"+product_code+"'");
		}
		if (description != null) {
			clauseBuilder.add("productdescription like '%"+description+"%'");
		}
		if (quantity != null) {
			// TODO: add a greater or less than feature
			clauseBuilder.add("productquantity = "+quantity);
		}
		if (price != null) {
			// TODO: add a greater or less than feature
			clauseBuilder.add("CAST(productprice AS DECIMAL) = CAST("+Float.toString(price)+" AS DECIMAL)");
		}
		
		String sql = String.join(" AND ", clauseBuilder);
		sql = "SELECT * from Cultureboxes WHERE "+sql;
		
		//System.out.println(sql);
		
		try {
			Connection conn = openDbConnection(database);
			ResultSet rs = runResult(conn, sql);
			
			if (rs != null) {
				while (rs.next()) {
					CultureboxItem si = new CultureboxItem(
						rs.getString("productid"), 
						rs.getString("productdescription"), 
						rs.getInt("productquantity"),
						rs.getFloat("productprice")
					);
					searchCultureboxes.add(si);
				}
			}
			closeDbConnection(conn);
			
		} catch (Exception e) {
			System.out.println("Problem connecting to database:: " + e);
		}
		
		return searchCultureboxes;
	}
	
	/* add a new type of inventory, if successful returns true */
	public Boolean add(String product_code, String description, int quantity, float price) {
		
		int retVal = 0;
		
		String sql = "INSERT into Cultureboxes (productid, productdescription, productquantity, productprice) VALUES ('"+
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
	
	public Boolean add(CultureboxItem item) {
		//public <T extends InventoryItem> Boolean add(T item);
		
		int retVal = 0;
		
		String sql = "INSERT into Cultureboxes (productid, productdescription, productquantity, productprice) VALUES ('"+
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