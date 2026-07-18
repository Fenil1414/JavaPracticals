public class Thermostat {
   private String location;
   private int temperature;
   private static final int MIN = 16;
   private static final int MAX = 30;
   private static int activeCount = 0;

   public Thermostat(String var1, int var2) {
      this.location = var1;
      if (var2 >= 16 && var2 <= 30) {
         this.temperature = var2;
      } else {
         this.temperature = 22;
      }

      ++activeCount;
   }

   public Thermostat(String var1) {
      this(var1, 22);
   }

   public void raise() {
      if (this.temperature < 30) {
         ++this.temperature;
      } else {
         System.out.println("Already at maximum (30)");
      }

   }

   public void lower() {
      if (this.temperature > 16) {
         --this.temperature;
      } else {
         System.out.println("Already at minimum (16)");
      }

   }

   public int getTemperature() {
      return this.temperature;
   }

   public static int getActiveCount() {
      return activeCount;
   }

   public static void main(String[] args) {
      Thermostat var1 = new Thermostat("Living Room");
      new Thermostat("Bedroom", 25);

      for(int var3 = 0; var3 < 10; ++var3) {
         var1.raise();
         System.out.println(var1.getTemperature());
      }

      for(int var4 = 0; var4 < 20; ++var4) {
         var1.lower();
         System.out.println(var1.getTemperature());
      }

      System.out.println(getActiveCount());
   }
}
