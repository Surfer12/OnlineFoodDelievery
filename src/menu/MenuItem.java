package menu;

public abstract class MenuItem {
   private Long id;
   private String name;
   private String description;
   private double price;
   private String category;
   private int preparationTime;
   private boolean available;

   public MenuItem(Long id, String name, String description, double price, String category, int preparationTime) {
      this.id = id;
      this.name = name;
      this.description = description;
      this.price = price;
      this.category = category;
      this.preparationTime = preparationTime;
      this.available = true;
   }

   public void updatePrice(double price) {
      this.price = price;
   }

   public boolean isAvailable() {
      return available;
   }

   public String getDetails() {
      return String.format("%s - %s ($%.2f)", name, description, price);
   }

   // Getters
   public Long getId() {
      return id;
   }

   public String getName() {
      return name;
   }

   public void setAvailable(boolean available) {
      this.available = available;
   }

   public String getDescription() {
      return description;
   }

   public double getPrice() {
      return price;
   }

   public String getCategory() {
      return category;
   }

   public int getPreparationTime() {
      return preparationTime;
   }

   public double calculateTotal() {
      return price;
   }
}