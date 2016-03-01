DataAccess is the middleware code for access to the database. It represents database objects as Java objects so as to abstract out the database specific info.

Each inventory type implements Inventory and InventoryItem. The inventory types are:

Seed
Shrub
Tree
Culturebox
Genomic
Processing
ReferenceMaterial

For each of them, you can call the following methods:

Inventory:

	getAll() returns ArrayList<Item>
	
	getById(String id) returns Item
	
	search(String product_code, String description, Integer quantity, Float price) returns returns ArrayList<Item>
	only fill out parameters for things you want to search on, leave the others null
	
	add(String product_code, String description, int quantity, float price) returns true if successfully added
	
	add(T item) returns true if successfully added
	
InventoryItem:

	update(String product_code, String description, Integer quantity, Float price) returns true if successfully updated
	just fill out the parameters you want to update, leave others null and they will not be overwritten
	
	remove() returns true if the item was sucessfully removed


