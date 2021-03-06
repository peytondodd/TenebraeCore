package fr.tenebrae.MMOCore.Items;

import net.minecraft.server.v1_9_R1.NBTTagCompound;

import org.bukkit.Material;
import org.bukkit.entity.Player;

import fr.tenebrae.MMOCore.Items.Components.ItemQuality;
import fr.tenebrae.MMOCore.Items.Components.ItemType;
import fr.tenebrae.MMOCore.Mechanics.Stats;

public class L02TatteredLeatherLeggings extends Item implements IEquipableItem {

	public L02TatteredLeatherLeggings(NBTTagCompound nbt) {
		super(nbt);
	}

	public L02TatteredLeatherLeggings() {
		this.setAmount(1);
		this.setId(10);
		this.setItemLevel(3);
		this.setMaxDurability(46);
		this.setDurability(this.getMaxDurability());
		this.setLevelRequired(2);
		this.setType(ItemType.LEGGINGS);
		this.setQuality(ItemQuality.COMMUN);
		this.setNameId(10);
		this.setLoreId(0);
		this.setMaterial(Material.LEATHER_LEGGINGS);
		this.setDamageData(0);
		this.addStat(Stats.ARMOR, 4.0);
		this.setSellPrice(4);
		
		createItemStack();
	}

	@Override
	public void onEquip(Player player) {
		// TODO Auto-generated method stub
		
	}
}