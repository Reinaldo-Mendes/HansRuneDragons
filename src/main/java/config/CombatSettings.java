package config;

import org.dreambot.api.methods.combat.CombatStyle;

public class CombatSettings {
   private CombatStyle combatStyle;
   private boolean enableAutoRetaliate;
   private boolean alwaysUseSpecialWhenReady;
   private int minHpToEAt = 55;
   private int maxHpToEat = 83;
   private int emergencyTeleportHp = 20;

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

   public boolean isAlwaysUseSpecialWhenReady() {
      return alwaysUseSpecialWhenReady;
   }

   public void setAlwaysUseSpecialWhenReady(boolean alwaysUseSpecialWhenReady) {
      this.alwaysUseSpecialWhenReady = alwaysUseSpecialWhenReady;
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
