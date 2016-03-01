import java.util.ArrayList;

public class Test {
	
	public static void main(String[] args) {
		
		SeedInventory seed = new SeedInventory();
		
		ArrayList<SeedItem> seeds = seed.getAll();
		
		for (SeedItem s : seeds) {
			System.out.println(s);
		}
		
		System.out.println("----------------------");
		
		SeedItem sid = seed.getById("BP001");
		System.out.println(sid);
		
		System.out.println("----------------------");
		
		/* get all the seeds where quanitity is 200 */
		ArrayList<SeedItem> seeds2 = seed.search(null, null, 200, null);
		
		for (SeedItem s : seeds2) {
			System.out.println(s);
		}
		
		System.out.println("----------------------");
		
		/* Test to add seeds two different ways */
		/* Boolean result = seed.add("BC004", "Black Maui Flip-flops", 10, 50.0F); */
		/* SeedItem sid2 = new SeedItem("MB002", "Miniature Blue Candycorn", 10, 20.0F); */
		/* Boolean result = seed.add(sid2); */

		/* System.out.println(result); */
		
		SeedItem sid2 = new SeedItem("MB002", "Miniature Blue Candycorn", 10, 20.0F);
		Boolean result = sid2.update("MB222", null, null, null);
		System.out.println(result);
		
		SeedItem sid3 = new SeedItem("BC004", "Black Maui Flip-flops", 10, 50.0F);
		Boolean result2 = sid3.remove();
		System.out.println(result2);
		
		
		ArrayList<SeedItem> seeds3 = seed.getAll();
		
		for (SeedItem s : seeds3) {
			System.out.println(s);
		}
		
		
	}
}