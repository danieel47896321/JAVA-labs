package lab12;

import java.util.List;

public class CostBridge {
	public static float getCost(List<Item> lst) {
	      float cost = 0;
	      for (Item item : lst) {
	         cost += item.price();
	      }		
	      return cost;
	}
}
