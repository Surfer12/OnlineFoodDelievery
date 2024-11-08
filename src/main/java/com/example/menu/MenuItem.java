public abstract class MenuItem {
   protected Long id;
   protected String name;
   protected String description;
   protected double price;
   protected String category;
   protected int preparationTime;
   protected boolean available;

   public MenuItem(Long id, String name, String description, double price, int preparationTime) {
      this.id = id;
      this.name = name;
      this.description = description;
      this.price = price;
      this.preparationTime = preparationTime;
      this.available = true;
   }

   public abstract double calculateTotal();

   // Existing getters and basic methods from the original MenuItem class
   // Reference to original MenuItem.java
}