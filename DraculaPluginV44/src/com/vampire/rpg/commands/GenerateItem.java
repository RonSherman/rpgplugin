package com.vampire.rpg.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import com.vampire.rpg.Pluginc;
import com.vampire.rpg.items.EquipType;
import com.vampire.rpg.items.ItemBalance;
import com.vampire.rpg.items.RandomItemGenerator;

public class GenerateItem implements CommandExecutor {

	public boolean onCommand(CommandSender sender, Command arg1, String arg2, String[] arg3) {
		if(!(sender instanceof Player)){
			return true;
		}
		Player p=(Player)sender;
		if(!p.getName().equals(Pluginc.Owner_Name) && !p.getName().equals(Pluginc.Host_Name)){
			p.sendMessage("no perms");
			return true;
		}
		if (arg3.length<4) {
            p.sendMessage("Use as /generateitems [type] [level] [amount] [rarity]");
            return true;
        }
		try {
            int amount = arg3.length == 3 ? Integer.parseInt(arg3[2]) : 1;
            int level = Integer.parseInt(arg3[1]);
            String rarity = arg3[3];
            if (arg3[0].equalsIgnoreCase("set")) {
            	int rarityVal = 0;
                for (int k = 0; k < ItemBalance.RARITY_NAMES.length; k++) {
                    if (ItemBalance.RARITY_NAMES[k].equalsIgnoreCase(rarity)) {
                        rarityVal = k;
                        break;
                    }
                }
                for (int k = 0; k < amount; k++) {
                	p.getInventory().addItem(RandomItemGenerator.generateEquip(EquipType.HELMET, level, 0, rarityVal));
                    p.getInventory().addItem(RandomItemGenerator.generateEquip(EquipType.CHESTPLATE, level, 0, rarityVal));
                    p.getInventory().addItem(RandomItemGenerator.generateEquip(EquipType.LEGGINGS, level, 0, rarityVal));
                    p.getInventory().addItem(RandomItemGenerator.generateEquip(EquipType.BOOTS, level, 0, rarityVal));
                }
            } else {
                EquipType et = EquipType.valueOf(arg3[0].toUpperCase());
                int rarityVal = 0;
                for (int k = 0; k < ItemBalance.RARITY_NAMES.length; k++) {
                    if (ItemBalance.RARITY_NAMES[k].equalsIgnoreCase(rarity)) {
                        rarityVal = k;
                        break;
                    }
                }
                if (amount > p.getInventory().getSize())
                    amount = p.getInventory().getSize();
                for (int k = 0; k < amount; k++)
                    p.getInventory().addItem(RandomItemGenerator.generateEquip(et, level,0,rarityVal));
            }
        } catch (Exception e) {
            e.printStackTrace();
            p.sendMessage("Invalid type. Possible types are: ");
            StringBuilder sb = new StringBuilder();
            for (EquipType et : EquipType.values())
                sb.append(et + " ");
            p.sendMessage(sb.toString().trim().replace(" ", ", "));
        }
		return false;
	}

}
