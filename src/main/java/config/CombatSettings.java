package config;

import org.dreambot.api.methods.combat.CombatStyle;

public class CombatSettings {
   private static CombatSettings combatSettings = new CombatSettings();

   public static CombatSettings getCombatSettings(){
      return combatSettings;
   }
   private CombatSettings(){

   }
   private CombatStyle combatStyle;
   private boolean enableAutoRetaliate = true;
   private boolean useSpecialAttack;
   private int minPercentageToSpecialAttack;
   private int maxPercentageToSpecialAttack;
   private int minHpToEAt = 50;
   private int maxHpToEat = 68;
   private int emergencyTeleportHp = 25;

   public CombatStyle getCombatStyle() {
      return combatStyle;
   }

   public void setCombatStyle(CombatStyle combatStyle) {
      this.combatStyle = combatStyle;
   }

   public boolean isEnableAutoRetaliate() {
      return enableAutoRetaliate;
   }

   public void setEnableAutoRetaliate(boolean enableAutoRetaliate) {
      this.enableAutoRetaliate = enableAutoRetaliate;
   }

   public boolean isUseSpecialAttack() {
      return useSpecialAttack;
   }

   public void setUseSpecialAttack(boolean useSpecialAttack) {
      this.useSpecialAttack = useSpecialAttack;
   }

   public int getMinPercentageToSpecialAttack() {
      return minPercentageToSpecialAttack;
   }

   public void setMinPercentageToSpecialAttack(int minPercentageToSpecialAttack) {
      this.minPercentageToSpecialAttack = minPercentageToSpecialAttack;
   }

   public int getMaxPercentageToSpecialAttack() {
      return maxPercentageToSpecialAttack;
   }

   public void setMaxPercentageToSpecialAttack(int maxPercentageToSpecialAttack) {
      this.maxPercentageToSpecialAttack = maxPercentageToSpecialAttack;
   }

   public int getMinHpToEAt() {
      return minHpToEAt;
   }

   public void setMinHpToEAt(int minHpToEAt) {
      this.minHpToEAt = minHpToEAt;
   }

   public int getMaxHpToEat() {
      return maxHpToEat;
   }

   public void setMaxHpToEat(int maxHpToEat) {
      this.maxHpToEat = maxHpToEat;
   }

   public int getEmergencyTeleportHp() {
      return emergencyTeleportHp;
   }

   public void setEmergencyTeleportHp(int emergencyTeleportHp) {
      this.emergencyTeleportHp = emergencyTeleportHp;
   }
}
