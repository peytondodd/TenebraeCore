package fr.tenebrae.MMOCore.Items.Archer;

import net.minecraft.server.v1_9_R1.NBTTagCompound;

import org.bukkit.Material;

import fr.tenebrae.MMOCore.Items.Item;
import fr.tenebrae.MMOCore.ItemsComponents.ItemQuality;
import fr.tenebrae.MMOCore.ItemsComponents.ItemType;
import fr.tenebrae.MMOCore.Mechanics.Stats;

public class L01RecruitTunic extends Item {

	public L01RecruitTunic(NBTTagCompound nbt) {
		super(nbt);
	}

	public L01RecruitTunic() {
		this.setAmount(1);
		this.setId(4);
		this.setItemLevel(1);
		this.setMaxDurability(38);
		this.setDurability(this.getMaxDurability());
		this.setLevelRequired(0);
		this.setType(ItemType.CHESTPLATE);
		this.setQuality(ItemQuality.COMMUN);
		this.setNameId(4);
		this.setLoreId(0);
		this.setMaterial(Material.LEATHER_CHESTPLATE);
		this.setDamageData(0);
		this.addStat(Stats.ARMOR, 2);
		
		createItemStack();
	}
}