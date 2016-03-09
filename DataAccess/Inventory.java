
import java.util.ArrayList;

/***********************************************
 *
 *
 *
 *
 ***********************************************/
 
public interface Inventory<T extends InventoryItem> {

	/* return a list of all items of this type */
	public <T extends InventoryItem> ArrayList<T> getAll() throws SelectException;
	
	/* get a specific entry */
	public <T extends InventoryItem> T getById(String id) throws SelectException;
	
	/* search for an entry */
	public <T extends InventoryItem> ArrayList<T> search(String product_code, String description, Integer quantity, Float price) throws SelectException;
	
	/* add a new type of inventory, if successful returns true */
	public Boolean add(String product_code, String description, int quantity, float price) throws InsertException;
	
	public Boolean add(T item) throws InsertException;
		
}