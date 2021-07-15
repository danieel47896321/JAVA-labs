package lab12;

import java.util.Random;

public class MealBuilder implements Runnable{
	public Meal prepareVegMeal() {
		Meal meal = new Meal();
		meal.addItem(new VegBurger());
		meal.addItem(new Pepsi());
		return meal;
	}
	public Meal prepareNonVegMeal() {
		Meal meal = new Meal();
		meal.addItem(new ChickenBurger());
		meal.addItem(new Coke());
		return meal;
	}
	@Override
	public void run() {
		try { Thread.sleep(300);} 
		catch (InterruptedException e) {}
		Random random = new Random();
		Meal meal = new Meal();
		if(random.nextInt(2) ==0)
			meal = prepareVegMeal();
		else
			meal = prepareNonVegMeal();
		meal.showItems();
		System.out.println("Total Price: " +meal.getCost());
		}
}