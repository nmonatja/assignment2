
/***********************************************
 *
 *
 *
 *
 ***********************************************/


public interface InventoryItem {
	
	/* update an existing inventory record based on the product code, if successful returns true */
	public Boolean update(String product_code, String description, Integer quantity, Float price);
	
	/* remove an existing inventory record */
	public Boolean remove();
	
}