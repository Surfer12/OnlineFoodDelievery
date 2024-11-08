package menu;

public enum Size {
   SMALL(0.8),
   MEDIUM(1.0),
   LARGE(1.2);

   private final double priceMultiplier;

   Size(double priceMultiplier) {
      this.priceMultiplier = priceMultiplier;
   }

   public double getPriceMultiplier() {
      return priceMultiplier;
   }
}