import java.util.ArrayList;
import java.sql.Connection;
import java.sql.ResultSet;


/***********************************************
 *
 *
 *
 *
 ***********************************************/
 
public class SeedInventory extends DbItem implements Inventory<SeedItem> {
	
	static final String database = "inventory";
	
	public SeedInventory() {
		
	}

	/* return a list of all items of this type */
	public ArrayList<SeedItem> getAll() throws SelectException {
		
		ArrayList<SeedItem> allSeeds = new ArrayList<SeedItem>();
		String sql = "select * from seeds";
		
		try {
			Connection conn = openDbConnection(database);
			ResultSet rs = runResult(conn, sql);
		
			if (rs != null) {
				while (rs.next()) {
					SeedItem si = new SeedItem(
						rs.getString("product_code"), 
						rs.getString("description"), 
						rs.getInt("quantity"),
						rs.getFloat("price")
					);
					allSeeds.add(si);
				}
			}
			closeDbConnection(conn);
			
		} catch (Exception e) {
			throw new SelectException("database: "+database+" sql:"+sql+" : "+e);
		}
		
		return allSeeds;
	}
	
	/* get a specific entry */
	public SeedItem getById(String id) throws SelectException {
		
		String sql = "select * from seeds where product_code = '"+id+"'";
		
		try {
			Connection conn = openDbConnection(database);
			ResultSet rs = runResult(conn, sql);
			
			if (rs != null) {
				rs.first();
				SeedItem si = new SeedItem(
					rs.getString("product_code"), 
					rs.getString("description"), 
					rs.getInt("quantity"),
					rs.getFloat("price")
				);
				
				closeDbConnection(conn);
				return si;
				
			}
		} catch (Exception e) {
			throw new SelectException("database: "+database+" sql:"+sql+" : "+e);
		}
		
		return null;
		
	}
	
	/* search for an entry */
	public ArrayList<SeedItem> search(String product_code, String description, Integer quantity, Float price) throws SelectException {
		ArrayList<SeedItem> searchSeeds = new ArrayList<SeedItem>();
		
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
		sql = "SELECT * from seeds WHERE "+sql;
		
		//System.out.println(sql);
		
		try {
			Connection conn = openDbConnection(database);
			ResultSet rs = runResult(conn, sql);
			
			if (rs != null) {
				while (rs.next()) {
					SeedItem si = new SeedItem(
						rs.getString("product_code"), 
						rs.getString("description"), 
						rs.getInt("quantity"),
						rs.getFloat("price")
					);
					searchSeeds.add(si);
				}
			}
			closeDbConnection(conn);
			
		} catch (Exception e) {
			throw new SelectException("database: "+database+" sql:"+sql+" : "+e);
		}
		
		return searchSeeds;
	}
	
	/* add a new type of inventory, if successful returns true */
	public Boolean add(String product_code, String description, int quantity, float price) throws InsertException {
		
		int retVal = 0;
		
		String sql = "INSERT into seeds (product_code, description, quantity, price) VALUES ('"+
			product_code+"', '"+description+"', "+quantity+", "+price+")";
		
		try {
			
			Connection conn = openDbConnection(database);
			retVal = run(conn, sql);
			
			closeDbConnection(conn);
			
		} catch (Exception e) {
			throw new InsertException("database: "+database+" sql:"+sql+" : "+e);
		}
		
		if (retVal == 1) {
			return true;
		}
		return false;
		
	}
	
	public Boolean add(SeedItem item) throws InsertException {
		//public <T extends InventoryItem> Boolean add(T item);
		
		int retVal = 0;
		
		String sql = "INSERT into seeds (product_code, description, quantity, price) VALUES ('"+
			item.product_code+"', '"+item.description+"', "+item.quantity+", "+item.price+")";
		
		try {
			Connection conn = openDbConnection(database);
			retVal = run(conn, sql);
			
			closeDbConnection(conn);
			
		} catch (Exception e) {
			throw new InsertException("database: "+database+" sql:"+sql+" : "+e);
		}
		
		if (retVal == 1) {
			return true;
		}
		
		return false;
	}
	
}