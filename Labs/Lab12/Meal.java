package lab12;

import java.util.ArrayList;
public class Meal extends CostBridge{
	private ArrayList<Item> lst = new ArrayList<Item>();
	public void addItem(Item item) { lst.add(item); }
	public float getCost() { return super.getCost(lst); }
	public void showItems() { 
		  for (Item item : lst) {
		         System.out.print("\tItem : "+item.name());
		         System.out.println(", Price : "+item.price());
		      }		
	}
}