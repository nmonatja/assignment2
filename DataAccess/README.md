DataAccess is the middleware code for access to the database. It represents database objects as Java objects so as to abstract out the database specific info.

There is now a jar you can import into your project's library in NetBeans: dataaccess.jar

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
	
You can compile this code by doing:
	
	javac Inventory.java SeedInventory.java InventoryItem.java SeedItem.java DbItem.java Test.java

And then run Test via the command line:
	
	java -cp .:[FULL PATH TO MYSQL JAR]/mysql-connector-java-5.1.38/mysql-connector-java-5.1.38-bin.jar Test

I have now added the following classes for orders:

OrderInfo:
	
	getAll() returns ArrayList<Order>
	
	getById() returns Order
	
	search(Integer order_id, String order_date, String first_name, String last_name, String address, String phone, Float total_cost, 
		Integer shipped, String order_table) returns ArrayList<Order>
		only fill out the parameters for things you want to search on, leave the others null
		
	add(String order_date, String first_name, String last_name, String address, String phone, Float total_cost, Integer shipped, 
		String order_table) returns int (id of new order in the db)
		
	add(Order o) returns int (id of new order in the db)
	
Order:
	
	update(String order_date, String first_name, String last_name, String address, String phone, Float total_cost, Integer shipped, 
		String order_table) returns true if successfully updated
		just fill out the parameters you want to update, leave others null and they will not be overwritten
		
	remove() returns true if the item was sucessfully removed
	
	getItems() returns ArrayList<OrderItem> 
	
	addItem(OrderItem item) return Integer (id of new item in the order)
	
OrderItem:
	
	update(String product_id, String description, Float item_price) returns true if successfully updated
	
	remove() returns true if successfully removed


