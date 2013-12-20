package net.minecraft.client.gui;

import java.util.Collection;
import java.util.Iterator;
import java.util.Random;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.LMM_EntityLittleMaid;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.LMM_ContainerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.src.LMM_Client;
import net.minecraft.src.LMM_GuiTextureSelect;
import net.minecraft.src.MMM_TextureManager;
import net.minecraft.util.StatCollector;

import org.lwjgl.opengl.EXTRescaleNormal;
import org.lwjgl.opengl.GL11;

public class LMM_GuiInventory extends GuiContainer {
	public LMM_EntityLittleMaid entitylittlemaid;
	private IInventory lowerChestInventory;
	// Field
	private Random rand;
	public GuiButton selectbutton;
	public GuiButtonNextPage txbutton[] = new GuiButtonNextPage[4];
	private int updateCounter;
	private IInventory upperChestInventory;
	private float xSize_lo;

	private float ySize_lo;
	private int ySizebk;
<<<<<<< HEAD:littleMaidMob/net/minecraft/src/LMM_GuiInventory.java
	private int updateCounter;
	public LMM_EntityLittleMaid entitylittlemaid;
	
	public GuiButtonNextPage txbutton[] = new GuiButtonNextPage[4];
	public GuiButton selectbutton;
	public boolean isChangeTexture;
	
	protected static ResourceLocation fguiTex = new ResourceLocation("textures/gui/container/littlemaidinventory.png");
=======
>>>>>>> 3c9267ee863704790532f2c9b8ddc171642033f0:littleMaidMob/net/minecraft/client/gui/LMM_GuiInventory.java

	// Method
	public LMM_GuiInventory(EntityPlayer pPlayer, LMM_EntityLittleMaid elmaid) {
		super(new LMM_ContainerInventory(pPlayer.inventory, elmaid));
		rand = new Random();
		upperChestInventory = pPlayer.inventory;
		lowerChestInventory = elmaid.maidInventory;
		allowUserInput = false;
		updateCounter = 0;
		ySizebk = ySize;
		ySize = 207;
		isChangeTexture = true;

		entitylittlemaid = elmaid;
		// entitylittlemaid.setOpenInventory(true);
	}

	@Override
	public void actionPerformed(GuiButton par1GuiButton) {
		switch (par1GuiButton.id) {
		case 100:
			LMM_Client.setNextTexturePackege(entitylittlemaid, 0);
			LMM_Client.setTextureValue(entitylittlemaid);
			break;
		case 101:
			LMM_Client.setPrevTexturePackege(entitylittlemaid, 0);
			LMM_Client.setTextureValue(entitylittlemaid);
			break;
		case 110:
			LMM_Client.setNextTexturePackege(entitylittlemaid, 1);
			LMM_Client.setTextureValue(entitylittlemaid);
			break;
		case 111:
			LMM_Client.setPrevTexturePackege(entitylittlemaid, 1);
			LMM_Client.setTextureValue(entitylittlemaid);
			break;
		case 200:
			int ldye = 0;
			if (mc.thePlayer.capabilities.isCreativeMode) {
				ldye = 0xffff;
			} else {
				for (ItemStack lis : mc.thePlayer.inventory.mainInventory) {
					if (lis != null && lis.itemID == Item.dyePowder.itemID) {
						ldye |= (1 << (15 - lis.getItemDamage()));
					}
				}
			}
			mc.displayGuiScreen(new LMM_GuiTextureSelect(this, entitylittlemaid, ldye, true));
		}
	}

<<<<<<< HEAD:littleMaidMob/net/minecraft/src/LMM_GuiInventory.java
	@Override
	protected void drawGuiContainerForegroundLayer(int par1, int par2) {
		String ls;
		fontRenderer.drawString(StatCollector.translateToLocal(
				lowerChestInventory.getInvName()), 8, 64, 0x404040);
		fontRenderer.drawString(StatCollector.translateToLocal(
				upperChestInventory.getInvName()), 8, 114, 0x404040);
		fontRenderer.drawString(StatCollector.translateToLocal(
				"littleMaidMob.text.Health"), 86, 8, 0x404040);
		fontRenderer.drawString(StatCollector.translateToLocal(
				"littleMaidMob.text.AP"), 86, 32, 0x404040);
		
		fontRenderer.drawString(StatCollector.translateToLocal(
				"littleMaidMob.mode.".concat(entitylittlemaid.getMaidModeString())), 86, 56, 0x404040);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		
		// �L����
		int lj = 0;
		int lk = 0;
		GL11.glEnable(EXTRescaleNormal.GL_RESCALE_NORMAL_EXT);
		GL11.glEnable(GL11.GL_COLOR_MATERIAL);
		GL11.glPushMatrix();
		GL11.glTranslatef(lj + 51, lk + 57, 50F);
		float f1 = 30F;
		GL11.glScalef(-f1, f1, f1);
		GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
		float f2 = entitylittlemaid.renderYawOffset;
		float f3 = entitylittlemaid.rotationYaw;
		float f4 = entitylittlemaid.rotationYawHead;
		float f5 = entitylittlemaid.rotationPitch;
//		float f8 = (float) (lj + 51) - xSize_lo;
//		float f9 = (float) (lk + 75) - 50 - ySize_lo;
		float f8 = (float)(guiLeft + 51 - par1);
		float f9 = (float)(guiTop + 22 - par2);
		GL11.glRotatef(135F, 0.0F, 1.0F, 0.0F);
		RenderHelper.enableStandardItemLighting();
		GL11.glRotatef(-135F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(-(float) Math.atan(f9 / 40F) * 20F, 1.0F, 0.0F, 0.0F);
		entitylittlemaid.renderYawOffset = (float) Math.atan(f8 / 40F) * 20F;
		entitylittlemaid.rotationYawHead = entitylittlemaid.rotationYaw = (float) Math.atan(f8 / 40F) * 40F;
		entitylittlemaid.rotationPitch = -(float) Math.atan(f9 / 40F) * 20F;
		GL11.glTranslatef(0.0F, entitylittlemaid.yOffset, 0.0F);
		RenderManager.instance.playerViewY = 180F;
		RenderManager.instance.renderEntityWithPosYaw(entitylittlemaid, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
		entitylittlemaid.renderYawOffset = f2;
		entitylittlemaid.rotationYaw = f3;
		entitylittlemaid.rotationYawHead = f4;
		entitylittlemaid.rotationPitch = f5;
		GL11.glPopMatrix();
		RenderHelper.disableStandardItemLighting();
		GL11.glDisable(EXTRescaleNormal.GL_RESCALE_NORMAL_EXT);
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240F / 1.0F, 240F / 1.0F);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		// �w�i
		ResourceLocation lrl = ((MMM_TextureBox)entitylittlemaid.textureBox[0]).getTextureName(MMM_TextureManager.tx_gui);
		if (lrl == null) {
			lrl = fguiTex;
=======
	private void displayDebuffEffects() {
		// ポーションエフェクトの表示
		int lx = guiLeft - 124;
		int ly = guiTop;
		Collection collection = entitylittlemaid.getActivePotionEffects();
		if (collection.isEmpty()) {
			return;
		}
		int lh = 33;
		if (collection.size() > 5) {
			lh = 132 / (collection.size() - 1);
		}
		for (Iterator iterator = entitylittlemaid.getActivePotionEffects().iterator(); iterator.hasNext();) {
			PotionEffect potioneffect = (PotionEffect) iterator.next();
			Potion potion = Potion.potionTypes[potioneffect.getPotionID()];
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			mc.renderEngine.bindTexture("/gui/inventory.png");
			drawTexturedModalRect(lx, ly, 0, ySizebk, 140, 32);
			if (potion.hasStatusIcon()) {
				int i1 = potion.getStatusIconIndex();
				drawTexturedModalRect(lx + 6, ly + 7, 0 + (i1 % 8) * 18,
						ySizebk + 32 + (i1 / 8) * 18, 18, 18);
			}
			String ls = StatCollector.translateToLocal(potion.getName());
			if (potioneffect.getAmplifier() > 0) {
				ls = (new StringBuilder()).append(ls).append(" ")
						.append(StatCollector.translateToLocal((new StringBuilder())
								.append("potion.potency.")
								.append(potioneffect.getAmplifier())
								.toString())).toString();
			}
			fontRenderer.drawStringWithShadow(ls, lx + 10 + 18, ly + 6, 0xffffff);
			String s1 = Potion.getDurationString(potioneffect);
			fontRenderer.drawStringWithShadow(s1, lx + 10 + 18, ly + 6 + 10, 0x7f7f7f);
			ly += lh;
		}
	}

	@Override
	public void drawGuiContainerBackgroundLayer(float f, int i, int j) {
		// 背景
		String s = MMM_TextureManager.getTextureName(entitylittlemaid.textureName, MMM_TextureManager.tx_gui);
		if (s == null) {
			s = "/gui/littlemaidinventory.png";
>>>>>>> 3c9267ee863704790532f2c9b8ddc171642033f0:littleMaidMob/net/minecraft/client/gui/LMM_GuiInventory.java
		}
		MMM_Client.setTexture(lrl);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
		int lj = guiLeft;
		int lk = guiTop;
		drawTexturedModalRect(lj, lk, 0, 0, xSize, ySize);

		// PotionEffect
		displayDebuffEffects();

		// LP/AP
		drawHeathArmor(0, 0);
/*		
		MMM_Client.setTexture(field_110324_m);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		boolean flag1 = (entitylittlemaid.hurtResistantTime / 3) % 2 == 1;
		if (entitylittlemaid.hurtResistantTime < 10) {
			flag1 = false;
		}
		int i1 = entitylittlemaid.health;
		int j1 = entitylittlemaid.prevHealth;
		rand.setSeed(updateCounter * 0x4c627);

		// AP
		int k1 = entitylittlemaid.getTotalArmorValue();
		for (int j2 = 0; j2 < 10; j2++) {
			int k3 = 43 + lk;
			if (k1 > 0) {
				// int j5 = j + 158 - j2 * 8;
				int j5 = lj + 86 + j2 * 8;
				if (j2 * 2 + 1 < k1) {
					drawTexturedModalRect(j5, k3, 34, 9, 9, 9);
				}
				if (j2 * 2 + 1 == k1) {
					drawTexturedModalRect(j5, k3, 25, 9, 9, 9);
				}
				if (j2 * 2 + 1 > k1) {
					drawTexturedModalRect(j5, k3, 16, 9, 9, 9);
				}
			}

			// LP
			int k5 = 0;
			if (flag1) {
				k5 = 1;
			}
			int i6 = lj + 86 + j2 * 8;
			k3 = 19 + lk;
			if (i1 <= 4) {
				k3 += rand.nextInt(2);
			}
			drawTexturedModalRect(i6, k3, 16 + k5 * 9, 0, 9, 9);
			if (flag1) {
				if (j2 * 2 + 1 < j1) {
					drawTexturedModalRect(i6, k3, 70, 0, 9, 9);
				}
				if (j2 * 2 + 1 == j1) {
					drawTexturedModalRect(i6, k3, 79, 0, 9, 9);
				}
			}
			if (j2 * 2 + 1 < i1) {
				drawTexturedModalRect(i6, k3, 52, 0, 9, 9);
			}
			if (j2 * 2 + 1 == i1) {
				drawTexturedModalRect(i6, k3, 61, 0, 9, 9);
			}
		}
*/
	}

	protected void drawHeathArmor(int par1, int par2) {
		boolean var3 = entitylittlemaid.hurtResistantTime / 3 % 2 == 1;
		
		if (entitylittlemaid.hurtResistantTime < 10) {
			var3 = false;
		}
		
		int var4 = MathHelper.ceiling_float_int(entitylittlemaid.func_110143_aJ());
		int var5 = MathHelper.ceiling_float_int(entitylittlemaid.prevHealth);
		this.rand.setSeed((long) (updateCounter * 312871));
		boolean var6 = false;
//		FoodStats var7 = entitylittlemaid.getFoodStats();
//		int var8 = var7.getFoodLevel();
//		int var9 = var7.getPrevFoodLevel();
		AttributeInstance var10 = entitylittlemaid.func_110148_a(SharedMonsterAttributes.field_111267_a);
		int var11 = par1 / 2 - 91;
		int var12 = par1 / 2 + 91;
		int var13 = par2 - 39;
		float var14 = (float) var10.func_111126_e();
		float var15 = entitylittlemaid.func_110139_bj();
		int var16 = MathHelper.ceiling_float_int((var14 + var15) / 2.0F / 10.0F);
		int var17 = Math.max(10 - (var16 - 2), 3);
		int var18 = var13 - (var16 - 1) * var17 - 10;
		float var19 = var15;
		int var20 = entitylittlemaid.getTotalArmorValue();
		int var21 = -1;
		
		if (entitylittlemaid.isPotionActive(Potion.regeneration)) {
			var21 = updateCounter % MathHelper.ceiling_float_int(var14 + 5.0F);
		}
		
		// AP
		int var23;
		int var22;
		
		for (var22 = 0; var22 < 10; ++var22) {
			if (var20 > 0) {
				var23 = var11 + var22 * 8;
				
				if (var22 * 2 + 1 < var20) {
					this.drawTexturedModalRect(var23, var18, 34, 9, 9, 9);
				}
				
				if (var22 * 2 + 1 == var20) {
					this.drawTexturedModalRect(var23, var18, 25, 9, 9, 9);
				}
				
				if (var22 * 2 + 1 > var20) {
					this.drawTexturedModalRect(var23, var18, 16, 9, 9, 9);
				}
			}
		}
		
		// LP
		int var25;
		int var27;
		int var26;
		
		for (var22 = MathHelper.ceiling_float_int((var14 + var15) / 2.0F) - 1; var22 >= 0; --var22) {
			var23 = 16;
			
			if (entitylittlemaid.isPotionActive(Potion.poison)) {
				var23 += 36;
			} else if (entitylittlemaid.isPotionActive(Potion.wither)) {
				var23 += 72;
			}
			
			byte var24 = 0;
			
			if (var3) {
				var24 = 1;
			}
			
			var25 = MathHelper.ceiling_float_int((float) (var22 + 1) / 10.0F) - 1;
			var26 = var11 + var22 % 10 * 8;
			var27 = var13 - var25 * var17;
			
			if (var4 <= 4) {
				var27 += this.rand.nextInt(2);
			}
			
			if (var22 == var21) {
				var27 -= 2;
			}
			
			this.drawTexturedModalRect(var26, var27, 16 + var24 * 9, 9, 9, 9);
			
			if (var3) {
				if (var22 * 2 + 1 < var5) {
					this.drawTexturedModalRect(var26, var27, var23 + 54, 9, 9, 9);
				}
				
				if (var22 * 2 + 1 == var5) {
					this.drawTexturedModalRect(var26, var27, var23 + 63, 9, 9, 9);
				}
			}
			
			if (var19 > 0.0F) {
				if (var19 == var15 && var15 % 2.0F == 1.0F) {
					this.drawTexturedModalRect(var26, var27, var23 + 153, 9, 9, 9);
				} else {
					this.drawTexturedModalRect(var26, var27, var23 + 144, 9, 9, 9);
				}
				
				var19 -= 2.0F;
			} else {
				if (var22 * 2 + 1 < var4) {
					this.drawTexturedModalRect(var26, var27, var23 + 36, 9, 9, 9);
				}
				
				if (var22 * 2 + 1 == var4) {
					this.drawTexturedModalRect(var26, var27, var23 + 45, 9, 9, 9);
				}
			}
		}
	}

	@Override
	public void drawGuiContainerForegroundLayer(int par1, int par2) {
		String ls;
		fontRenderer.drawString(StatCollector.translateToLocal(
				lowerChestInventory.getInvName()), 8, 64, 0x404040);
		fontRenderer.drawString(StatCollector.translateToLocal(
				upperChestInventory.getInvName()), 8, 114, 0x404040);
		fontRenderer.drawString(StatCollector.translateToLocal(
				"littleMaidMob.text.Health"), 86, 8, 0x404040);
		fontRenderer.drawString(StatCollector.translateToLocal(
				"littleMaidMob.text.AP"), 86, 32, 0x404040);

		fontRenderer.drawString(StatCollector.translateToLocal(
				"littleMaidMob.mode.".concat(entitylittlemaid.getMaidModeString())), 86, 56, 0x404040);
		GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);

		// キャラ
		int lj = 0;
		int lk = 0;
		GL11.glEnable(EXTRescaleNormal.GL_RESCALE_NORMAL_EXT);
		GL11.glEnable(GL11.GL_COLOR_MATERIAL);
		GL11.glPushMatrix();
		GL11.glTranslatef(lj + 51, lk + 57, 50F);
		float f1 = 30F;
		GL11.glScalef(-f1, f1, f1);
		GL11.glRotatef(180F, 0.0F, 0.0F, 1.0F);
		float f2 = entitylittlemaid.renderYawOffset;
		float f3 = entitylittlemaid.rotationYaw;
		float f4 = entitylittlemaid.rotationYawHead;
		float f5 = entitylittlemaid.rotationPitch;
		//		float f8 = (float) (lj + 51) - xSize_lo;
		//		float f9 = (float) (lk + 75) - 50 - ySize_lo;
		float f8 = guiLeft + 51 - par1;
		float f9 = guiTop + 22 - par2;
		GL11.glRotatef(135F, 0.0F, 1.0F, 0.0F);
		RenderHelper.enableStandardItemLighting();
		GL11.glRotatef(-135F, 0.0F, 1.0F, 0.0F);
		GL11.glRotatef(-(float) Math.atan(f9 / 40F) * 20F, 1.0F, 0.0F, 0.0F);
		entitylittlemaid.renderYawOffset = (float) Math.atan(f8 / 40F) * 20F;
		entitylittlemaid.rotationYawHead = entitylittlemaid.rotationYaw = (float) Math.atan(f8 / 40F) * 40F;
		entitylittlemaid.rotationPitch = -(float) Math.atan(f9 / 40F) * 20F;
		GL11.glTranslatef(0.0F, entitylittlemaid.yOffset, 0.0F);
		RenderManager.instance.playerViewY = 180F;
		RenderManager.instance.renderEntityWithPosYaw(entitylittlemaid, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
		entitylittlemaid.renderYawOffset = f2;
		entitylittlemaid.rotationYaw = f3;
		entitylittlemaid.rotationYawHead = f4;
		entitylittlemaid.rotationPitch = f5;
		GL11.glPopMatrix();
		RenderHelper.disableStandardItemLighting();
		GL11.glDisable(EXTRescaleNormal.GL_RESCALE_NORMAL_EXT);

	}

	@Override
	public void drawScreen(int i, int j, float f) {
		super.drawScreen(i, j, f);
		xSize_lo = i;
		ySize_lo = j;

		int ii = i - guiLeft;
		int jj = j - guiTop;
		if (ii > 25 && ii < 78 && jj > 7 && jj < 60) {
			// ボタンの表示
			txbutton[0].drawButton = true;
			txbutton[1].drawButton = true;
			txbutton[2].drawButton = true;
			txbutton[3].drawButton = true;
			selectbutton.drawButton = true;

			// テクスチャ名称の表示
			GL11.glPushMatrix();
			GL11.glTranslatef(i - ii, j - jj, 0.0F);
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			RenderHelper.disableStandardItemLighting();
			GL11.glDisable(GL11.GL_LIGHTING);
			GL11.glDisable(GL11.GL_DEPTH_TEST);
<<<<<<< HEAD:littleMaidMob/net/minecraft/src/LMM_GuiInventory.java
			
			if (entitylittlemaid.textureBox[0] != null) {
				int ltw1 = fontRenderer.getStringWidth(entitylittlemaid.textureBox[0].textureName);
				int ltw2 = fontRenderer.getStringWidth(entitylittlemaid.textureBox[1].textureName);
=======

			if (entitylittlemaid.textureName != null) {
				int ltw1 = fontRenderer.getStringWidth(entitylittlemaid.textureName);
				int ltw2 = fontRenderer.getStringWidth(entitylittlemaid.textureArmorName);
>>>>>>> 3c9267ee863704790532f2c9b8ddc171642033f0:littleMaidMob/net/minecraft/client/gui/LMM_GuiInventory.java
				int ltwmax = (ltw1 > ltw2) ? ltw1 : ltw2;
				int lbx = 52 - ltwmax / 2;
				int lby = 68;
				int lcolor;
				lcolor = jj < 20 ? 0xc0882222 : 0xc0000000;
				drawGradientRect(lbx - 3, lby - 4, lbx + ltwmax + 3, lby + 8, lcolor, lcolor);
				fontRenderer.drawStringWithShadow(
						entitylittlemaid.textureBox[0].textureName, 52 - ltw1 / 2, lby - 2, -1);
				lcolor = jj > 46 ? 0xc0882222 : 0xc0000000;
				drawGradientRect(lbx - 3, lby + 8, lbx + ltwmax + 3, lby + 16 + 4, lcolor, lcolor);
				fontRenderer.drawStringWithShadow(
						entitylittlemaid.textureBox[1].textureName, 52 - ltw2 / 2, lby + 10, -1);
			}

			GL11.glPopMatrix();
			GL11.glEnable(GL11.GL_LIGHTING);
			GL11.glEnable(GL11.GL_DEPTH_TEST);
		} else {
			txbutton[0].drawButton = false;
			txbutton[1].drawButton = false;
			txbutton[2].drawButton = false;
			txbutton[3].drawButton = false;
			selectbutton.drawButton = false;
		}

	}

	@Override
	public void initGui() {
		super.initGui();
		if (!entitylittlemaid.getActivePotionEffects().isEmpty()) {
			guiLeft = 160 + (width - xSize - 200) / 2;
		}
		buttonList.add(txbutton[0] = new GuiButtonNextPage(100, guiLeft + 25, guiTop + 7, false));
		buttonList.add(txbutton[1] = new GuiButtonNextPage(101, guiLeft + 55, guiTop + 7, true));
		buttonList.add(txbutton[2] = new GuiButtonNextPage(110, guiLeft + 25, guiTop + 47, false));
		buttonList.add(txbutton[3] = new GuiButtonNextPage(111, guiLeft + 55, guiTop + 47, true));
		buttonList.add(selectbutton = new GuiButton(200, guiLeft + 25, guiTop + 25, 53, 17, "select"));
	}

	@Override
<<<<<<< HEAD:littleMaidMob/net/minecraft/src/LMM_GuiInventory.java
	protected void actionPerformed(GuiButton par1GuiButton) {
		switch (par1GuiButton.id) {
		case 100:
			entitylittlemaid.setNextTexturePackege(0);
			entitylittlemaid.setTextureNames();
			break;
		case 101:
			entitylittlemaid.setPrevTexturePackege(0);
			entitylittlemaid.setTextureNames();
			break;
		case 110:
			entitylittlemaid.setNextTexturePackege(1);
			entitylittlemaid.setTextureNames();
			break;
		case 111:
			entitylittlemaid.setPrevTexturePackege(1);
			entitylittlemaid.setTextureNames();
			break;
		case 200:
			int ldye = 0;
			if (mc.thePlayer.capabilities.isCreativeMode) {
				ldye = 0xffff;
			} else {
				for (ItemStack lis : mc.thePlayer.inventory.mainInventory) {
					if (lis != null && lis.itemID == Item.dyePowder.itemID) {
						ldye |= (1 << (15 - lis.getItemDamage()));
=======
	public void mouseClicked(int i, int j, int k) {
		super.mouseClicked(i, j, k);
		/*
				// 26,8-77,59
				int ii = i - guiLeft;
				int jj = j - guiTop;

				// TODO:メイドアセンブル画面を作る
				if (ii > 25 && ii < 78 && jj > 7 && jj < 60) {
					// 伽羅表示領域
					if (Keyboard.isKeyDown(42) || Keyboard.isKeyDown(54)) {
						// Shift+で逆周り
						LMM_Client.setPrevTexturePackege(entitylittlemaid, k);
					} else {
						LMM_Client.setNextTexturePackege(entitylittlemaid, k);
>>>>>>> 3c9267ee863704790532f2c9b8ddc171642033f0:littleMaidMob/net/minecraft/client/gui/LMM_GuiInventory.java
					}
					LMM_Client.setTextureValue(entitylittlemaid);
				}
<<<<<<< HEAD:littleMaidMob/net/minecraft/src/LMM_GuiInventory.java
			}
			isChangeTexture = false;
			mc.displayGuiScreen(new LMM_GuiTextureSelect(this, entitylittlemaid, ldye, true));
		}
=======
		*/
>>>>>>> 3c9267ee863704790532f2c9b8ddc171642033f0:littleMaidMob/net/minecraft/client/gui/LMM_GuiInventory.java
	}

	@Override
	public void onGuiClosed() {
		super.onGuiClosed();
		// entitylittlemaid.onGuiClosed();
		if (isChangeTexture) {
			entitylittlemaid.sendTextureToServer();
		}
	}

<<<<<<< HEAD:littleMaidMob/net/minecraft/src/LMM_GuiInventory.java
	private void displayDebuffEffects() {
		// �|�[�V�����G�t�F�N�g�̕\��
		int lx = guiLeft - 124;
		int ly = guiTop;
		Collection collection = entitylittlemaid.getActivePotionEffects();
		if (collection.isEmpty()) {
			return;
		}
		int lh = 33;
		if (collection.size() > 5) {
			lh = 132 / (collection.size() - 1);
		}
		for (Iterator iterator = entitylittlemaid.getActivePotionEffects().iterator(); iterator.hasNext();) {
			PotionEffect potioneffect = (PotionEffect) iterator.next();
			Potion potion = Potion.potionTypes[potioneffect.getPotionID()];
			GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
			MMM_Client.setTexture(field_110408_a);
			drawTexturedModalRect(lx, ly, 0, ySizebk, 140, 32);
			if (potion.hasStatusIcon()) {
				int i1 = potion.getStatusIconIndex();
				drawTexturedModalRect(lx + 6, ly + 7, 0 + (i1 % 8) * 18,
						ySizebk + 32 + (i1 / 8) * 18, 18, 18);
			}
			String ls = StatCollector.translateToLocal(potion.getName());
			if (potioneffect.getAmplifier() > 0) {
				ls = (new StringBuilder()).append(ls).append(" ")
						.append(StatCollector.translateToLocal((new StringBuilder())
								.append("potion.potency.")
								.append(potioneffect.getAmplifier())
								.toString())).toString();
			}
			fontRenderer.drawStringWithShadow(ls, lx + 10 + 18, ly + 6, 0xffffff);
			String s1 = Potion.getDurationString(potioneffect);
			fontRenderer.drawStringWithShadow(s1, lx + 10 + 18, ly + 6 + 10, 0x7f7f7f);
			ly += lh;
		}
=======
	@Override
	public void updateScreen() {
		super.updateScreen();
		updateCounter++;
>>>>>>> 3c9267ee863704790532f2c9b8ddc171642033f0:littleMaidMob/net/minecraft/client/gui/LMM_GuiInventory.java
	}

}
