package com.vampire.rpg.stealth;

import java.util.HashSet;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

import com.vampire.rpg.AbstractManager;
import com.vampire.rpg.Pluginc;
import com.vampire.rpg.spells.Spell;
import com.vampire.rpg.utils.VamParticles;
import com.vampire.rpg.utils.VamTicks;

import de.slikey.effectlib.util.ParticleEffect;

public class StealthManager extends AbstractManager {
    public static HashSet<Player> invis;

    public StealthManager(Pluginc plugin) {
        super(plugin);
    }

    @Override
    public void initialize() {
        if (invis != null)
            invis.clear();
        invis = new HashSet<Player>();
    }

    @EventHandler
    public void onLogin(PlayerJoinEvent event) {
        Player p = event.getPlayer();
        invis.remove(p);
        for (Player p2 : plugin.getServer().getOnlinePlayers()) {
            p2.showPlayer(p);
            p.showPlayer(p2);
        }
        for (Player p2 : invis)
            p.hidePlayer(p2);
    }

    @EventHandler
    public void onLogout(PlayerQuitEvent event) {
        Player p = event.getPlayer();
        if (invis.contains(p))
            invis.remove(p);
    }

    public static void giveStealth(Player p, int seconds) {
        invis.add(p);
        for(Player p2 : plugin.getServer().getOnlinePlayers())
            p2.hidePlayer(p);
        p.addPotionEffect(new PotionEffect(PotionEffectType.INVISIBILITY, VamTicks.seconds(seconds), 1, true, false));
       VamParticles.showWithOffset(ParticleEffect.SMOKE_LARGE, p.getLocation().add(0, p.getEyeHeight() * 0.7, 0), 1.0, 15);
    }

    public static void removeStealth(Player p) {
        Spell.notify(p, "You are no longer stealthed.");
        invis.remove(p);
        for(Player p2 : plugin.getServer().getOnlinePlayers())
            p2.showPlayer(p);
        p.removePotionEffect(PotionEffectType.INVISIBILITY);
        VamParticles.showWithOffset(ParticleEffect.CLOUD, p.getLocation().add(0, p.getEyeHeight() * 0.7, 0), 1.0, 15);
    }

}
