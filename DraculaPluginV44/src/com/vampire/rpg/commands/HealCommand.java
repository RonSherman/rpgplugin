package com.vampire.rpg.commands;

import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.vampire.rpg.PlayerData;
import com.vampire.rpg.Pluginc;

public class HealCommand implements CommandExecutor {

	@Override
	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] arg3) {
		if(!(sender instanceof Player))
			return true;
		Player p = (Player)sender;
		PlayerData pd=Pluginc.getPD(p);
		if (arg3.length > 0) {
            if (Pluginc.getInstance().getServer().getPlayer(arg3[0]) != null) {
            	Player target= Pluginc.getInstance().getServer().getPlayerExact(arg3[0]);
                PlayerData pd2 = Pluginc.getInstance().getPD(target);
                if (pd2 != null && pd2.isValid()) {
                    pd2.heal(pd2.getCurrentMaxHP());
                    pd.sendMessage(ChatColor.RED + "Healed " + pd2.getName() + " to full HP.");
                }
            }
        } else {
            pd.heal(pd.getCurrentMaxHP());
            pd.sendMessage(ChatColor.RED + "Healed self to full HP.");
        }
		return true;
	}

}
