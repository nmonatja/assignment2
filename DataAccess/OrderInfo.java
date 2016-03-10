import java.util.ArrayList;
import java.sql.Connection;
import java.sql.ResultSet;

/***********************************************
 *
 *
 *
 *
 ***********************************************/

public class OrderInfo extends DbItem {
	
	static final String database = "orderinfo";
	
	public OrderInfo() {
		
	}
	
	/* get all orders */
	public ArrayList<Order> getAll() throws SelectException {
		
		ArrayList<Order> allOrders = new ArrayList<Order>();
		String sql = "select * from orders";
		
		try {
			Connection conn = openDbConnection(database);
			ResultSet rs = runResult(conn, sql);
		
			if (rs != null) {
				while (rs.next()) {
					Order order = new Order(
						rs.getInt("order_id"), 
						rs.getString("order_date"),
						rs.getString("first_name"),
						rs.getString("last_name"),
						rs.getString("address"),
						rs.getString("phone"),
						rs.getFloat("total_cost"), 
						rs.getInt("shipped"),
						rs.getString("ordertable")
					);
					allOrders.add(order);
				}
			}
			closeDbConnection(conn);
			
		} catch (Exception e) {
			throw new SelectException("database: "+database+" sql:"+sql+" : "+e);
		}
		
		return allOrders;
	}
	
	public Order getById(int id) throws SelectException {
		
		String sql = "select * from orders where order_id = '"+id+"'";
		
		try {
			Connection conn = openDbConnection(database);
			ResultSet rs = runResult(conn, sql);
			
			if (rs != null) {
				rs.first();
				Order order = new Order(
					rs.getInt("order_id"), 
					rs.getString("order_date"),
					rs.getString("first_name"),
					rs.getString("last_name"),
					rs.getString("address"),
					rs.getString("phone"),
					rs.getFloat("total_cost"), 
					rs.getInt("shipped"),
					rs.getString("ordertable")
				);
				
				closeDbConnection(conn);
				return order;
				
			}
		} catch (Exception e) {
			throw new SelectException("database: "+database+" sql:"+sql+" : "+e);
		}
		
		return null;
	}
	
	public ArrayList<Order> search(Integer order_id, String order_date, String first_name, String last_name, String address, String phone, Float total_cost, Integer shipped, String order_table) throws SelectException {
		
		ArrayList<Order> searchOrders = new ArrayList<Order>();
		
		ArrayList<String> clauseBuilder = new ArrayList<String>();
		if (order_id != null) {
			clauseBuilder.add("order_id = "+order_id);
		}
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
			clauseBuilder.add("CAST(total_cost AS DECIMAL) = CAST("+Float.toString(total_cost)+" AS DECIMAL)");
		}
		if (shipped != null) {
			clauseBuilder.add("shipped = "+shipped);
		}
		if (order_table != null) {
			clauseBuilder.add("order_table = '"+order_table+"'");
		}
		
		String sql = String.join(" AND ", clauseBuilder);
		sql = "SELECT * from orders WHERE "+sql;
		
		//System.out.println(sql);
		
		try {
			Connection conn = openDbConnection(database);
			ResultSet rs = runResult(conn, sql);
			
			if (rs != null) {
				while (rs.next()) {
					Order order = new Order(
						rs.getInt("order_id"), 
						rs.getString("order_date"),
						rs.getString("first_name"),
						rs.getString("last_name"),
						rs.getString("address"),
						rs.getString("phone"),
						rs.getFloat("total_cost"), 
						rs.getInt("shipped"),
						rs.getString("ordertable")
					);
					searchOrders.add(order);
				}
			}
			closeDbConnection(conn);
			
		} catch (Exception e) {
			throw new SelectException("database: "+database+" sql:"+sql+" : "+e);
		}
		
		return searchOrders;
	}
	
	public int add(String order_date, String first_name, String last_name, String address, String phone, Float total_cost, Integer shipped, String order_table) throws InsertException {
		
		int retVal = 0;
		
		String sql = "INSERT into Orders (order_date, first_name, last_name, address, phone, total_cost, shipped, order_table) VALUES ('"+
			order_date+"', '"+first_name+"', '"+last_name+"', '"+address+"', '"+phone+"', "+total_cost+", "+shipped+", '"+order_table+"')";
		
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
			throw new InsertException("database: "+database+" sql:"+sql+" : "+e);
		}
		
		sql = "CREATE TABLE " + order_table +
                            "(item_id int unsigned not null auto_increment primary key, " +
                            "product_id varchar(20), description varchar(80), " +
                            "item_price float(7,2) );";
		try {
			Connection conn = openDbConnection(database);
			int retVal2 = run(conn, sql);
			
			closeDbConnection(conn);
		} catch (Exception e) {
			throw new InsertException("database: "+database+" sql:"+sql+" : "+e);
		}
		
		return retVal;
	} 
	
	public int add(Order o) throws InsertException {
		
		int retVal = 0;
		
		String sql = "INSERT into Orders (order_date, first_name, last_name, address, phone, total_cost, shipped, order_table) VALUES ('"+
			o.order_date+"', '"+o.first_name+"', '"+o.last_name+"', '"+o.address+"', '"+o.phone+"', "+o.total_cost+", "+o.shipped+", '"+o.order_table+"')";
		
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
			throw new InsertException("database: "+database+" sql:"+sql+" : "+e);
		}
		
		return retVal;
	} 
	
	
	
}