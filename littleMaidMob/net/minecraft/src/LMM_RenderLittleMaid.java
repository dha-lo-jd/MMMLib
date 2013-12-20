package net.minecraft.src;

import net.minecraft.client.model.MMM_ModelDuo;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.LMM_EntityLittleMaid;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraft.world.WorldServer;

import org.lwjgl.opengl.GL11;

public class LMM_RenderLittleMaid extends RenderLiving {

	public MMM_ModelDuo modelFATT;
	// Feilds
	public MMM_ModelDuo modelMain;

	// Method
	public LMM_RenderLittleMaid(float f) {
		super(null, f);
		modelFATT = new MMM_ModelDuo(this);
		modelFATT.isModelAlphablend = mod_LMM_littleMaidMob.AlphaBlend;
		modelMain = new MMM_ModelDuo(this);
		modelMain.isModelAlphablend = mod_LMM_littleMaidMob.AlphaBlend;
		modelMain.capsLink = modelFATT;
		mainModel = modelMain;
		setRenderPassModel(modelFATT);
	}

	@Override
	public void doRender(Entity entity, double d, double d1, double d2, float f, float f1) {
		doRenderLitlleMaid((LMM_EntityLittleMaid) entity, d, d1, d2, f, f1);
	}

	public void doRenderLitlleMaid(LMM_EntityLittleMaid plittleMaid, double px, double py, double pz, float f, float f1) {
		// いくつか重複してるのであとで確認
		// 姿勢による高さ調整
		double lay = py;
		if (plittleMaid.isSneaking()) {
			// しゃがみ
			lay -= 0.06D;
		} else if (plittleMaid.isRiding() && plittleMaid.ridingEntity == null) {
			// 座り込み
			lay -= 0.25D;
		}

		//		plittleMaid.textureModel0.render = this;
		modelMain.modelArmorInner = plittleMaid.textureModel0;
		modelMain.setRender(this);
		modelMain.isAlphablend = true;
		modelFATT.modelArmorInner = plittleMaid.textureModel1;
		modelFATT.modelArmorOuter = plittleMaid.textureModel2;
		modelFATT.textureOuter = plittleMaid.textureArmor2;
		modelFATT.textureInner = plittleMaid.textureArmor1;
		//		modelFATT.setRender(this);
		modelFATT.isAlphablend = true;
		//		if (modelMain.modelArmorInner == null) {
		//			modelMain.modelArmorInner = MMM_TextureManager.defaultModel[0];
		//		}
		modelMain.setEntityCaps(plittleMaid.maidCaps);

		modelMain.setCapsValue(MMM_IModelCaps.caps_heldItemLeft, (Integer) 0);
		modelMain.setCapsValue(MMM_IModelCaps.caps_heldItemRight, (Integer) 0);
		modelMain.setCapsValue(MMM_IModelCaps.caps_onGround, renderSwingProgress(plittleMaid, f1));
		modelMain.setCapsValue(MMM_IModelCaps.caps_isRiding, plittleMaid.isRiding());
		modelMain.setCapsValue(MMM_IModelCaps.caps_isSneak, plittleMaid.isSneaking());
		modelMain.setCapsValue(MMM_IModelCaps.caps_aimedBow, plittleMaid.isAimebow());
		modelMain.setCapsValue(MMM_IModelCaps.caps_isWait, plittleMaid.isMaidWait());
		modelMain.setCapsValue(MMM_IModelCaps.caps_isChild, plittleMaid.isChild());
		modelMain.setCapsValue(MMM_IModelCaps.caps_entityIdFactor, plittleMaid.entityIdFactor);
		modelMain.setCapsValue(MMM_IModelCaps.caps_ticksExisted, plittleMaid.ticksExisted);
		// だが無意味だ
		//		plittleMaid.textureModel0.isChild = plittleMaid.textureModel1.isChild = plittleMaid.textureModel2.isChild = plittleMaid.isChild();

		if (plittleMaid.worldObj instanceof WorldServer) {
			Entity le = MMM_Helper.mc.theWorld.getEntityByID(plittleMaid.entityId);
			if (le instanceof LMM_EntityLittleMaid) {
				LMM_EntityLittleMaid lel = (LMM_EntityLittleMaid) le;
				modelMain.modelArmorInner = lel.textureModel0;
				modelFATT.modelArmorInner = lel.textureModel1;
				modelFATT.modelArmorOuter = lel.textureModel2;
				modelFATT.textureOuter = lel.textureArmor2;
				modelFATT.textureInner = lel.textureArmor1;
				plittleMaid.texture = lel.texture;
			}
		} else {
		}
		doRenderLiving(plittleMaid, px, lay, pz, f, f1);

		// ひも
		if (plittleMaid.mstatgotcha != null && plittleMaid.mstatgotcha instanceof EntityLiving) {
			EntityLiving lel = (EntityLiving) plittleMaid.mstatgotcha;
			py -= 0.5D;
			Tessellator tessellator = Tessellator.instance;
			float f9 = ((lel.prevRotationYaw + (lel.rotationYaw - lel.prevRotationYaw) * f1 * 0.5F) * 3.141593F) / 180F;
			float f3 = ((lel.prevRotationPitch + (lel.rotationPitch - lel.prevRotationPitch) * f1 * 0.5F) * 3.141593F) / 180F;
			double d3 = MathHelper.sin(f9);
			double d5 = MathHelper.cos(f9);
			float f11 = lel.getSwingProgress(f1);
			float f12 = MathHelper.sin(MathHelper.sqrt_float(f11) * 3.141593F);
			Vec3 vec3d = Vec3.createVectorHelper(-0.5D, 0.029999999999999999D, 0.55D);
			vec3d.rotateAroundX((-(lel.prevRotationPitch + (lel.rotationPitch - lel.prevRotationPitch) * f1) * 3.141593F) / 180F);
			vec3d.rotateAroundY((-(lel.prevRotationYaw + (lel.rotationYaw - lel.prevRotationYaw) * f1) * 3.141593F) / 180F);
			vec3d.rotateAroundY(f12 * 0.5F);
			vec3d.rotateAroundX(-f12 * 0.7F);
			double d7 = lel.prevPosX + (lel.posX - lel.prevPosX) * f1 + vec3d.xCoord;
			double d8 = lel.prevPosY + (lel.posY - lel.prevPosY) * f1 + vec3d.yCoord;
			double d9 = lel.prevPosZ + (lel.posZ - lel.prevPosZ) * f1 + vec3d.zCoord;
			if (renderManager.options.thirdPersonView > 0) {
				float f4 = ((lel.prevRenderYawOffset + (lel.renderYawOffset - lel.prevRenderYawOffset) * f1) * 3.141593F) / 180F;
				double d11 = MathHelper.sin(f4);
				double d13 = MathHelper.cos(f4);
				d7 = (lel.prevPosX + (lel.posX - lel.prevPosX) * f1) - d13 * 0.34999999999999998D - d11
						* 0.54999999999999998D;
				d8 = (lel.prevPosY + (lel.posY - lel.prevPosY) * f1) - 0.45000000000000001D;
				d9 = ((lel.prevPosZ + (lel.posZ - lel.prevPosZ) * f1) - d11 * 0.34999999999999998D) + d13
						* 0.54999999999999998D;
			}
			double d10 = plittleMaid.prevPosX + (plittleMaid.posX - plittleMaid.prevPosX) * f1;
			double d12 = plittleMaid.prevPosY + (plittleMaid.posY - plittleMaid.prevPosY) * f1 + 0.25D - 0.5D;//+ 0.75D;
			double d14 = plittleMaid.prevPosZ + (plittleMaid.posZ - plittleMaid.prevPosZ) * f1;
			double d15 = (float) (d7 - d10);
			double d16 = (float) (d8 - d12);
			double d17 = (float) (d9 - d14);
			GL11.glDisable(3553 /*GL_TEXTURE_2D*/);
			GL11.glDisable(2896 /*GL_LIGHTING*/);
			tessellator.startDrawing(3);
			tessellator.setColorOpaque_I(0);
			int i = 16;
			for (int j = 0; j <= i; j++)
			{
				float f5 = (float) j / (float) i;
				tessellator.addVertex(px + d15 * f5, py + d16 * (f5 * f5 + f5) * 0.5D
						+ (((float) i - (float) j) / (i * 0.75F) + 0.125F), pz + d17 * f5);
			}

			tessellator.draw();
			GL11.glEnable(2896 /*GL_LIGHTING*/);
			GL11.glEnable(3553 /*GL_TEXTURE_2D*/);
		}
	}

	@Override
	public int getColorMultiplier(EntityLiving par1EntityLiving, float par2, float par3) {
		return ((LMM_EntityLittleMaid) par1EntityLiving).colorMultiplier(par2, par3);
	}

	@Override
	public void passSpecialRender(EntityLiving par1EntityLiving, double par2, double par4, double par6) {
		super.passSpecialRender(par1EntityLiving, par2, par4, par6);

		if (par1EntityLiving instanceof LMM_EntityLittleMaid) {
			LMM_EntityLittleMaid llmm = (LMM_EntityLittleMaid) par1EntityLiving;

			// 追加分
			for (int li = 0; li < llmm.maidEntityModeList.size(); li++) {
				llmm.maidEntityModeList.get(li).showSpecial(this, par2, par4, par6);
			}
		}
	}

	@Override
	public void preRenderCallback(EntityLiving entityliving, float f) {
		Float lscale = (Float) modelMain.getCapsValue(MMM_IModelCaps.caps_ScaleFactor);
		if (lscale != null) {
			GL11.glScalef(lscale, lscale, lscale);
		}
	}

	@Override
	public void renderEquippedItems(EntityLiving entityliving, float f) {
		renderSpecials((LMM_EntityLittleMaid) entityliving, f);
	}

	@Override
	public void renderModel(EntityLiving par1EntityLiving, float par2,
			float par3, float par4, float par5, float par6, float par7) {
		if (!par1EntityLiving.getHasActivePotion()) {

			loadDownloadableImageTexture(par1EntityLiving.skinUrl, par1EntityLiving.getTexture());
			modelMain.setArmorRendering(true);
		} else {
			modelMain.setArmorRendering(false);
		}
		// アイテムのレンダリング位置を獲得するためrenderを呼ぶ必要がある
		mainModel.render(par1EntityLiving, par2, par3, par4, par5, par6, par7);
	}

	public void renderSpecials(LMM_EntityLittleMaid entitylittlemaid, float f) {
		// ハードポイントの描画
		modelMain.renderItems(entitylittlemaid, this);
		renderArrowsStuckInEntity(entitylittlemaid, f);
	}

	public int setArmorModelEx(LMM_EntityLittleMaid entitylmaid, int i, float f) {
		// アーマーの表示設定
		modelFATT.renderParts = i;
		ItemStack is = entitylmaid.maidInventory.armorItemInSlot(i);
		if (is != null && is.stackSize > 0) {
			//			mod_littleMaidMob.Debug(String.format("en:%d-%d", i, ret));
			modelFATT.showArmorParts(i);
			return is.isItemEnchanted() ? 15 : 1;
		}

		return -1;
	}

	@Override
	public int shouldRenderPass(EntityLiving entityliving, int i, float f) {
		//littlemaidEX
		return setArmorModelEx((LMM_EntityLittleMaid) entityliving, i, f);
	}

}
