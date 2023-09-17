package me.minermanmk.testplugin;

import org.bukkit.entity.*;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileLaunchEvent;
import org.bukkit.potion.Potion;
import org.bukkit.projectiles.ProjectileSource;
import org.bukkit.util.Vector;

public class CustomProjectileSpeeds implements Listener {

    public double MultiplierPotions = 2.5;
    public double PotionHeightAdjust = 0.1;
    public double MultiplierTridents = 1.0;
    public double BowDamageMod = 0.4;
    public double XBowDamageMod = 0.5;
    public double BowAccuracyMod = 0.01;
    public double XBowAccuracyMod = 0.08;
    public double BowSpeedMod = 2.5;
    public double XBowSpeedMod = 2.0;

    @EventHandler
    public void onFire(ProjectileLaunchEvent event) {
        if (Main.normalProjectiles) {
            return;
        }
        Entity target = event.getEntity();

        ProjectileSource shooter;
        if (target instanceof org.bukkit.entity.Arrow) {
            double damageMultiplier;
            double accuracyMultiplier;
            double speedMultiplier;
            try{shooter = ((Arrow) target).getShooter();} catch(NullPointerException e){return;}
            if (!((Arrow) target).isShotFromCrossbow()) {
                damageMultiplier = BowDamageMod;
                accuracyMultiplier = BowAccuracyMod;
                speedMultiplier = BowSpeedMod;
            } else {
                damageMultiplier = XBowDamageMod;
                accuracyMultiplier = XBowAccuracyMod;
                speedMultiplier = BowSpeedMod;
            }
            if (shooter instanceof Player) {
                Vector normalized = target.getVelocity().normalize();
                double length = target.getVelocity().length();
                Vector playerNormalized = ((Player) shooter).getEyeLocation().getDirection().normalize();
                Vector normalizedAdjusted = normalized.multiply(accuracyMultiplier);
                Vector playerNormalizedAdj = playerNormalized.multiply(1 - accuracyMultiplier);
                Vector newVelocity = normalizedAdjusted.midpoint(playerNormalizedAdj).normalize().multiply(length);
                target.setVelocity(newVelocity.multiply(speedMultiplier));
                ((Arrow) target).setDamage(((Arrow) target).getDamage() * damageMultiplier);
            }

        } else if (target instanceof org.bukkit.entity.ThrownPotion) {
            try{shooter = ((ThrownPotion) target).getShooter();} catch(NullPointerException e){return;}
            //if (shooter instanceof Player) {
                //double length = target.getVelocity().length();
                //Vector playerNormalized = ((Player) shooter).getEyeLocation().getDirection().normalize();
                //Vector newVelocity = playerNormalized.multiply(length);
                //target.setVelocity(newVelocity);
            //}
            Vector ov = target.getVelocity();
            double newX = ov.getX() * MultiplierPotions;
            double yVal = ov.getY();
            double newY = ((1 - PotionHeightAdjust) * yVal + PotionHeightAdjust) * MultiplierPotions;
            double newZ = ov.getZ() * MultiplierPotions;
            target.setVelocity(new Vector(newX, newY, newZ));
        } else if (target instanceof org.bukkit.entity.Trident) {
            try{shooter = ((Trident) target).getShooter();} catch(NullPointerException e){return;}
            if (shooter instanceof Player) {
                double length = target.getVelocity().length();
                Vector playerNormalized = ((Player) shooter).getEyeLocation().getDirection().normalize();
                Vector newVelocity = playerNormalized.multiply(length);
                target.setVelocity(newVelocity.multiply(MultiplierTridents));
            }
        }
    }

}
