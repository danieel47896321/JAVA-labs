package lab12;

public class ProxyMealBuilder {
	private MealBuilder builder = new MealBuilder();
	public Meal prepareVegMeal() { return builder.prepareVegMeal(); }
	public Meal prepareNonVegMeal() { return builder.prepareNonVegMeal(); }
}