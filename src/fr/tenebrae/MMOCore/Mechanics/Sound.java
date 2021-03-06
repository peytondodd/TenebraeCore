package fr.tenebrae.MMOCore.Mechanics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import net.minecraft.server.v1_9_R1.EntityArmorStand;
import net.minecraft.server.v1_9_R1.EntityHuman;
import net.minecraft.server.v1_9_R1.EnumHand;
import net.minecraft.server.v1_9_R1.EnumInteractionResult;
import net.minecraft.server.v1_9_R1.ItemStack;
import net.minecraft.server.v1_9_R1.MinecraftKey;
import net.minecraft.server.v1_9_R1.PacketPlayOutNamedSoundEffect;
import net.minecraft.server.v1_9_R1.SoundCategory;
import net.minecraft.server.v1_9_R1.SoundEffect;
import net.minecraft.server.v1_9_R1.Vec3D;
import net.minecraft.server.v1_9_R1.World;

import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_9_R1.CraftWorld;
import org.bukkit.craftbukkit.v1_9_R1.entity.CraftPlayer;
import org.bukkit.entity.ArmorStand;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.CreatureSpawnEvent.SpawnReason;

import fr.tenebrae.MMOCore.Characters.Character;

public class Sound {

	public List<Character> characters = new ArrayList<Character>();
	public SoundCategory category = SoundCategory.MASTER;
	public double x = 0;
	public double y = 0;
	public double z = 0;
	public float volume = 1F;
	public float pitch = 1F;
	public double radius = 10;
	public Location loc = null;
	public String sound = "none";
	public boolean doesFollow = false;

	public Sound(String name, Character... characters) {
		this.sound = name;
		this.characters.addAll(Arrays.asList(characters));
	}
	
	public Sound(String name) {
		this.sound = name;
	}
	
	public Sound(String name, SoundCategory category) {
		this.sound = name;
		this.category = category;
	}
	
	public Sound(String name, SoundCategory category, Location loc, double radius, float volume, float pitch) {
		this.sound = name;
		this.category = category;
		this.loc = loc;
		this.radius = radius;
		this.volume = volume;
		this.pitch = pitch*63F;
	}
	
	public Sound(String name, SoundCategory category, double x, double y, double z, float volume, float pitch, Character... characters) {
		this.sound = name;
		this.category = category;
		this.x = x;
		this.y = y;
		this.z = z;
		this.volume = volume;
		this.pitch = pitch*63F;
		this.characters.addAll(Arrays.asList(characters));
	}

	public Sound(String name, SoundCategory category, Character... characters) {
		this.sound = name;
		this.category = category;
		this.characters.addAll(Arrays.asList(characters));
	}
	
	public Sound setX(double x) {
		this.x = x;
		return this;
	}
	
	public Sound setY(double y) {
		this.y = y;
		return this;
	}
	
	public Sound setZ(double z) {
		this.z = z;
		return this;
	}
	
	public Sound setPitch(float pitch) {
		this.pitch = pitch*63;
		return this;
	}
	
	public Sound setVolume(float volume) {
		this.volume = volume;
		return this;
	}
	
	public Sound setRadius(double radius) {
		this.radius = radius;
		return this;
	}
	
	public Sound setLocation(Location loc) {
		this.loc = loc;
		this.x = loc.getX();
		this.y = loc.getY();
		this.z = loc.getZ();
		return this;
	}
	
	public Sound setLoc(Location loc) {
		return setLocation(loc);
	}
	
	public Sound setSound(String sound) {
		this.sound = sound;
		return this;
	}
	
	public Sound setCategory(SoundCategory category) {
		this.category = category;
		return this;
	}
	
	public Sound doesFollow(boolean doesFollow) {
		this.doesFollow = doesFollow;
		return this;
	}
	
	public void play() {
		PacketPlayOutNamedSoundEffect packet = new PacketPlayOutNamedSoundEffect(new SoundEffect(new MinecraftKey(sound)),
													category, x, y, z, doesFollow ? 100000F : volume, pitch*63F);
		if (!characters.isEmpty()) {
			for (Character c : characters) c.getNMSAccount().playerConnection.sendPacket(packet);
		} else {
			if (loc != null) {
				ArmorStand as = new SoundHolder(loc).spawn();
				for (Entity e : as.getNearbyEntities(radius, radius, radius)) {
					if (e instanceof Player) ((CraftPlayer)((Player)e)).getHandle().playerConnection.sendPacket(packet);
				}
				as.remove();
			}
		}
	}
}

class SoundHolder extends EntityArmorStand {
	
	public SoundHolder(World world) {
		super(world);
	}

	public SoundHolder(org.bukkit.World world) {
		super(((CraftWorld)world).getHandle());
	}

	public SoundHolder(Location loc) {
		super(((CraftWorld)loc.getWorld()).getHandle());
		this.setPosition(loc.getX(), loc.getY(), loc.getZ());
		this.setInvisible(true);
		this.setGravity(false);
		this.setMarker(true);
	}
	
	public ArmorStand spawn() {
		this.world.addEntity(this, SpawnReason.CUSTOM);
		return (ArmorStand) this.getBukkitEntity();
	}

	@Override
	protected void cn() {}

	@Override
	public EnumInteractionResult a(EntityHuman entityhuman, Vec3D vec3d, ItemStack itemstack, EnumHand enumhand) {
		return EnumInteractionResult.PASS;
	}

	@Override
	public void g(float f, float f1) {}

	@Override
	public void U() {
		this.world.methodProfiler.a("entityBaseTick");
		
		if (justCreated) { setFlag(0, false); }
		
		this.justCreated = false;
		this.world.methodProfiler.b();
	}
}
