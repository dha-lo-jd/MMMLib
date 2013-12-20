package net.minecraft.src;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.LMM_EntityLittleMaid;
import net.minecraft.entity.LMM_EntityModeBase;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.pathfinding.PathEntity;
import net.minecraft.pathfinding.PathPoint;
import net.minecraft.util.MathHelper;

public class LMM_EntityAIHurtByTarget extends EntityAIHurtByTarget {

	public LMM_EntityLittleMaid theMaid;
	private boolean field_75303_a;
	private int field_75301_b;
	private int field_75302_c;


	public LMM_EntityAIHurtByTarget(LMM_EntityLittleMaid par1EntityLiving, boolean par2) {
		super(par1EntityLiving, par2);
		
		theMaid = par1EntityLiving;
		field_75303_a = false;
		field_75301_b = 0;
		field_75302_c = 0;
	}

	@Override
	public boolean shouldExecute() {
<<<<<<< HEAD
		if (theMaid.isContract() && !theMaid.isBlocking() && theMaid.mstatMasterEntity != null) {
			// tFT[nÍåÉÎ·éUÉ½
			EntityLivingBase lentity = theMaid.mstatMasterEntity.getAITarget();
=======
		if (theMaid.isMaidContract() && !theMaid.isBlocking() && theMaid.mstatMasterEntity != null) {
			// ãã§ã³ãµã¼ç³»ã¯ä¸»ã«å¯¾ããæ»æã«åå¿
			EntityLiving lentity = theMaid.mstatMasterEntity.getAITarget();
>>>>>>> 3c9267ee863704790532f2c9b8ddc171642033f0
			if (isSuitableTarget(lentity, false)) {
				theMaid.setRevengeTarget(lentity);
				return true;
			}
		}
		return super.shouldExecute();
	}

	@Override
	public void startExecuting() {
		super.startExecuting();
	}

	@Override
	public void updateTask() {
		super.updateTask();
		String s1 = taskOwner.getAITarget() == null ? "Null" : taskOwner.getAITarget().getClass().toString();
		String s2 = taskOwner.getAttackTarget() == null ? "Null" : taskOwner.getAttackTarget().getClass().toString();
//		System.out.println(String.format("ID:%d, target:%s, attack:%s", taskOwner.entityId, s1, s2));
		
<<<<<<< HEAD
		// £çê½dÔµ
		EntityLivingBase leliving = taskOwner.getAITarget();
=======
		// æ®´ãããä»è¿ã
		EntityLiving leliving = taskOwner.getAITarget();
>>>>>>> 3c9267ee863704790532f2c9b8ddc171642033f0
		if (leliving != null && leliving != taskOwner.getAttackTarget()) {
			taskOwner.setAttackTarget(null);
			System.out.println(String.format("ID:%d, ChangeTarget.", taskOwner.entityId));
		}
		
	}
	
	@Override
<<<<<<< HEAD
	protected boolean isSuitableTarget(EntityLivingBase par1EntityLiving, boolean par2) {
		// LMMpÉJX^
=======
	public boolean isSuitableTarget(EntityLiving par1EntityLiving, boolean par2) {
		// LMMç¨ã«ã«ã¹ã¿ã 
>>>>>>> 3c9267ee863704790532f2c9b8ddc171642033f0
		if (par1EntityLiving == null) {
			return false;
		}
		if (par1EntityLiving == taskOwner) {
			return false;
		}
		if (par1EntityLiving == theMaid.mstatMasterEntity) {
			return false;
		}
		if (!par1EntityLiving.isEntityAlive()) {
			return false;
		}
		
		LMM_EntityModeBase lailm = theMaid.getActiveModeClass(); 
		if (lailm != null && lailm.isSearchEntity()) {
			if (!lailm.checkEntity(theMaid.getMaidModeInt(), par1EntityLiving)) {
				return false;
			}
		} else {
			if (theMaid.getIFF(par1EntityLiving)) {
				return false;
			}
		}
		
<<<<<<< HEAD
		// î_©çêè££êÄ¢éêàUµÈ¢
		if (!taskOwner.func_110176_b(MathHelper.floor_double(par1EntityLiving.posX), MathHelper.floor_double(par1EntityLiving.posY), MathHelper.floor_double(par1EntityLiving.posZ))) {
//		if (!taskOwner.isWithinHomeDistance(MathHelper.floor_double(par1EntityLiving.posX), MathHelper.floor_double(par1EntityLiving.posY), MathHelper.floor_double(par1EntityLiving.posZ))) {
=======
		// åºç¹ããä¸å®è·é¢é¢ãã¦ããå ´åãæ»æããªã
		if (!taskOwner.isWithinHomeDistance(MathHelper.floor_double(par1EntityLiving.posX), MathHelper.floor_double(par1EntityLiving.posY), MathHelper.floor_double(par1EntityLiving.posZ))) {
>>>>>>> 3c9267ee863704790532f2c9b8ddc171642033f0
			return false;
		}
		
		// ã¿ã¼ã²ãããè¦ããªã
		if (shouldCheckSight && !taskOwner.getEntitySenses().canSee(par1EntityLiving)) {
			return false;
		}
		
		// æ»æä¸­æ­¢å¤å®ï¼
		if (this.field_75303_a) {
			if (--this.field_75302_c <= 0) {
				this.field_75301_b = 0;
			}
			
			if (this.field_75301_b == 0) {
				this.field_75301_b = this.func_75295_a(par1EntityLiving) ? 1 : 2;
			}
			
			if (this.field_75301_b == 2) {
				return false;
			}
		}
		
		return true;
	}

	private boolean func_75295_a(Entity par1EntityLiving) {
		this.field_75302_c = 10 + this.taskOwner.getRNG().nextInt(5);
		PathEntity var2 = taskOwner.getNavigator().getPathToXYZ(par1EntityLiving.posX, par1EntityLiving.posY, par1EntityLiving.posZ);
//		PathEntity var2 = this.taskOwner.getNavigator().getPathToEntityLiving(par1EntityLiving);
		
		if (var2 == null) {
			return false;
		} else {
			PathPoint var3 = var2.getFinalPathPoint();
			
			if (var3 == null) {
				return false;
			} else {
				int var4 = var3.xCoord - MathHelper.floor_double(par1EntityLiving.posX);
				int var5 = var3.zCoord - MathHelper.floor_double(par1EntityLiving.posZ);
				return (double)(var4 * var4 + var5 * var5) <= 2.25D;
			}
		}
	}

}
