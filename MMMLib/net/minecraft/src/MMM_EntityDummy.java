package net.minecraft.src;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.multiplayer.WorldClient;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

/**
 * マーカーを表示します。
 */
public class MMM_EntityDummy extends Entity {

	/**
	 * 指定されたオーナーに対応するマーカーを削除します。
	 */
	public static void clearDummyEntity(Entity entity) {
		if (!isEnable) {
			return;
		}

		List<Entity> liste = entity.worldObj.getLoadedEntityList();
		for (Entity entity1 : liste) {
			if (entity1 instanceof MMM_EntityDummy) {
				((MMM_EntityDummy) entity1).setOwnerdEntityDead(entity);
			}
		}
	}

	/**
	 * マーカーを表示する
	 */
	public static void setDummyEntity(Entity owner, int color, double posx, double posy, double posz) {
		if (!isEnable) {
			return;
		}

		// サーバー側でしか呼ばれないっぽい
		if (owner.worldObj.isRemote) {
			mod_MMM_MMMLib.Debug("L");
		}

		WorldClient lworld = MMM_Helper.mc.theWorld;
		MMM_EntityDummy ed = new MMM_EntityDummy(lworld, color, owner);
		ed.setPosition(posx, posy, posz);
		appendList.add(ed);
		//    	lworld.spawnEntityInWorld(ed);
		//joinEntityInSurroundings(ed);
	}

	private int livecount;
	private final int maxlivecount = 16;
	private int entityColor;

	public Entity entityOwner;

	/**
	 * 有効判定
	 */
	public static boolean isEnable = false;

	public static List<MMM_EntityDummy> appendList = new ArrayList<MMM_EntityDummy>();

	public MMM_EntityDummy(World world, int color, Entity owner) {
		super(world);
		livecount = maxlivecount;
		entityColor = color;
		//		setSize(1F, 1F);
		entityOwner = owner;
	}

	@Override
	public void entityInit() {
	}

	public float getAlpha(float max) {
		if (livecount >= 0) {
			return max * livecount / maxlivecount;
		} else {
			return 0F;
		}
	}

	public int getColor() {
		return entityColor;
	}

	@Override
	public void onUpdate() {
		//		super.onUpdate();

		if (--livecount < 0 || !isEnable) {
			setDead();
		}
	}

	@Override
	public void readEntityFromNBT(NBTTagCompound nbttagcompound) {
	}

	public boolean setOwnerdEntityDead(Entity entity) {
		if (entityOwner == entity) {
			setDead();
			return true;
		}
		return false;
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbttagcompound) {
	}

}
