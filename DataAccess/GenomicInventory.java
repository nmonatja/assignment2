import java.util.ArrayList;
import java.sql.Connection;
import java.sql.ResultSet;


/***********************************************
 *
 *
 *
 *
 ***********************************************/
 
public class GenomicInventory extends DbItem implements Inventory<GenomicItem> {
	
	static final String database = "leaftech";
	
	public GenomicInventory() {
		
	}

	/* return a list of all items of this type */
	public ArrayList<GenomicItem> getAll() throws SelectException {
		
		ArrayList<GenomicItem> allGenomics = new ArrayList<GenomicItem>();
		String sql = "select * from Genomics";
		
		try {
			Connection conn = openDbConnection(database);
			ResultSet rs = runResult(conn, sql);
		
			if (rs != null) {
				while (rs.next()) {
					GenomicItem si = new GenomicItem(
						rs.getString("productid"), 
						rs.getString("productdescription"), 
						rs.getInt("productquantity"),
						rs.getFloat("productprice")
					);
					allGenomics.add(si);
				}
			}
			closeDbConnection(conn);
			
		} catch (Exception e) {
			throw new SelectException("database: "+database+" sql:"+sql+" : "+e);
		}
		
		return allGenomics;
	}
	
	/* get a specific entry */
	public GenomicItem getById(String id) throws SelectException {
		
		String sql = "select * from Genomics where productid = '"+id+"'";
		
		try {
			Connection conn = openDbConnection(database);
			ResultSet rs = runResult(conn, sql);
			
			if (rs != null) {
				rs.first();
				GenomicItem si = new GenomicItem(
					rs.getString("productid"), 
					rs.getString("productdescription"), 
					rs.getInt("productquantity"),
					rs.getFloat("productprice")
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
	public ArrayList<GenomicItem> search(String product_code, String description, Integer quantity, Float price) throws SelectException {
		ArrayList<GenomicItem> searchGenomics = new ArrayList<GenomicItem>();
		
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
		sql = "SELECT * from Genomics WHERE "+sql;
		
		//System.out.println(sql);
		
		try {
			Connection conn = openDbConnection(database);
			ResultSet rs = runResult(conn, sql);
			
			if (rs != null) {
				while (rs.next()) {
					GenomicItem si = new GenomicItem(
						rs.getString("productid"), 
						rs.getString("productdescription"), 
						rs.getInt("productquantity"),
						rs.getFloat("productprice")
					);
					searchGenomics.add(si);
				}
			}
			closeDbConnection(conn);
			
		} catch (Exception e) {
			throw new SelectException("database: "+database+" sql:"+sql+" : "+e);
		}
		
		return searchGenomics;
	}
	
	/* add a new type of inventory, if successful returns true */
	public Boolean add(String product_code, String description, int quantity, float price) throws InsertException {
		
		int retVal = 0;
		
		String sql = "INSERT into Genomics (productid, productdescription, productquantity, productprice) VALUES ('"+
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
	
	public Boolean add(GenomicItem item) throws InsertException {
		//public <T extends InventoryItem> Boolean add(T item);
		
		int retVal = 0;
		
		String sql = "INSERT into Genomics (productid, productdescription, productquantity, productprice) VALUES ('"+
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