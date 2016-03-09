import java.util.ArrayList;
import java.sql.Connection;
import java.sql.ResultSet;


/***********************************************
 *
 *
 *
 *
 ***********************************************/
 
public class TreeInventory extends DbItem implements Inventory<TreeItem> {
	
	static final String database = "inventory";
	
	public TreeInventory() {
		
	}

	/* return a list of all items of this type */
	public ArrayList<TreeItem> getAll() throws SelectException {
		
		ArrayList<TreeItem> allTrees = new ArrayList<TreeItem>();
		String sql = "select * from Trees";
		
		try {
			Connection conn = openDbConnection(database);
			ResultSet rs = runResult(conn, sql);
		
			if (rs != null) {
				while (rs.next()) {
					TreeItem si = new TreeItem(
						rs.getString("product_code"), 
						rs.getString("description"), 
						rs.getInt("quantity"),
						rs.getFloat("price")
					);
					allTrees.add(si);
				}
			}
			closeDbConnection(conn);
			
		} catch (Exception e) {
			throw new SelectException("database: "+database+" sql:"+sql+" : "+e);
		}
		
		return allTrees;
	}
	
	/* get a specific entry */
	public TreeItem getById(String id) throws SelectException {
		
		String sql = "select * from Trees where product_code = '"+id+"'";
		
		try {
			Connection conn = openDbConnection(database);
			ResultSet rs = runResult(conn, sql);
			
			if (rs != null) {
				rs.first();
				TreeItem si = new TreeItem(
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
	public ArrayList<TreeItem> search(String product_code, String description, Integer quantity, Float price) throws SelectException {
		ArrayList<TreeItem> searchTrees = new ArrayList<TreeItem>();
		
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
		sql = "SELECT * from Trees WHERE "+sql;
		
		//System.out.println(sql);
		
		try {
			Connection conn = openDbConnection(database);
			ResultSet rs = runResult(conn, sql);
			
			if (rs != null) {
				while (rs.next()) {
					TreeItem si = new TreeItem(
						rs.getString("product_code"), 
						rs.getString("description"), 
						rs.getInt("quantity"),
						rs.getFloat("price")
					);
					searchTrees.add(si);
				}
			}
			closeDbConnection(conn);
			
		} catch (Exception e) {
			throw new SelectException("database: "+database+" sql:"+sql+" : "+e);
		}
		
		return searchTrees;
	}
	
	/* add a new type of inventory, if successful returns true */
	public Boolean add(String product_code, String description, int quantity, float price) throws InsertException {
		
		int retVal = 0;
		
		String sql = "INSERT into Trees (product_code, description, quantity, price) VALUES ('"+
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
	
	public Boolean add(TreeItem item) throws InsertException {
		//public <T extends InventoryItem> Boolean add(T item);
		
		int retVal = 0;
		
		String sql = "INSERT into Trees (product_code, description, quantity, price) VALUES ('"+
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