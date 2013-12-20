package net.minecraft.src;

import static net.minecraft.src.LMM_Statics.*;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.gui.LMM_GuiInventory;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.model.MMM_ModelLittleMaid;
import net.minecraft.client.model.MMM_ModelMultiBase;
import net.minecraft.client.multiplayer.NetClientHandler;
import net.minecraft.client.particle.EntityCrit2FX;
import net.minecraft.client.particle.EntityPickupFX;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LMM_EntityLittleMaid;
import net.minecraft.entity.LMM_EntityLittleMaidAvatar;
import net.minecraft.entity.LMM_EntityModeBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet250CustomPayload;

/**
<<<<<<< HEAD
 * クライアント専用処理。
 * マルチ用に分離。
 * 分離しとかないとNoSuchMethodで落ちる。
 */
public class LMM_Client {

	public static void init() {
=======
 * 繝槭Ν繝∫畑縺ｫ蛻�髮｢縲�
 * 蛻�髮｢縺励→縺九↑縺�縺ｨNoSuchMethod縺ｧ關ｽ縺｡繧九��
 *
 */
public class LMM_Client {

	public static void addRenderer(Map map) {
		map.put(LMM_EntityLittleMaid.class, new LMM_RenderLittleMaid(0.3F));
>>>>>>> 3c9267ee863704790532f2c9b8ddc171642033f0
	}

	public static void clientCustomPayload(NetClientHandler var1, Packet250CustomPayload var2) {
		// 繧ｯ繝ｩ繧､繧｢繝ｳ繝亥�ｴ縺ｮ迚ｹ谿翫ヱ繧ｱ繝�繝亥女菫｡蜍穂ｽ�
		byte lmode = var2.data[0];
		int leid = 0;
		LMM_EntityLittleMaid lemaid = null;
		if ((lmode & 0x80) != 0) {
			leid = MMM_Helper.getInt(var2.data, 1);
			lemaid = LMM_Net.getLittleMaid(var2.data, 1, MMM_Helper.mc.theWorld);
			if (lemaid == null) {
				return;
			}
		}
		mod_LMM_littleMaidMob.Debug(String.format("LMM|Upd Clt Call[%2x:%d].", lmode, leid));

		switch (lmode) {
		case LMM_Net.LMN_Client_SwingArm:
			// 閻墓険繧�
			byte larm = var2.data[5];
			LMM_EnumSound lsound = LMM_EnumSound.getEnumSound(MMM_Helper.getInt(var2.data, 6));
			lemaid.setSwinging(larm, lsound);
			//			mod_LMM_littleMaidMob.Debug(String.format("SwingSound:%s", lsound.name()));
			break;

		case LMM_Net.LMN_Client_UpdateTexture:
			// 縺顔捩譖ｿ縺�
			LMM_Client.setTextureValue(lemaid);
			break;

		case LMM_Net.LMN_Client_SetIFFValue:
			// IFF縺ｮ險ｭ螳壼�､繧貞女菫｡
			int lval = var2.data[1];
			String lname = "";
			for (int li = 6; li < var2.data.length; li++) {
				lname += (char) var2.data[li];
			}

			// TODO:GUI縺ｧ菴ｿ逕ｨ縺吶ｋ蛟､繧定ｨｭ螳壹☆繧九ｈ縺�縺ｫ
			LMM_IFF.setIFFValue(null, lname, lval);
			break;

		case LMM_Net.LMN_Client_PlaySound:
			// 髻ｳ螢ｰ蜀咲函
			LMM_EnumSound lsound9 = LMM_EnumSound.getEnumSound(MMM_Helper.getInt(var2.data, 5));
			lemaid.playLittleMaidSound(lsound9, true);
			//			mod_LMM_littleMaidMob.Debug(String.format("playSound:%s", lsound9.name()));
			break;

		}
	}

	public static GuiContainer getContainerGUI(EntityClientPlayerMP var1, int var2,
			int var3, int var4, int var5) {
		Entity lentity = var1.worldObj.getEntityByID(var3);
		if (lentity instanceof LMM_EntityLittleMaid) {
			for (LMM_EntityModeBase mode : ((LMM_EntityLittleMaid) lentity).maidEntityModeList) {
				LMM_GuiInventory guiInventory = mode.getOpenGuiInventory(var1, (LMM_EntityLittleMaid) lentity);
				if (guiInventory != null) {
					return guiInventory;
				}
			}
			LMM_GuiInventory lgui = new LMM_GuiInventory(var1, (LMM_EntityLittleMaid) lentity);
			//			var1.openContainer = lgui.inventorySlots;
			return lgui;
		} else {
			return null;
		}
	}

	public static void init() {
		// 繝�繝輔か繝ｫ繝医Δ繝�繝ｫ縺ｮ險ｭ螳�
		MMM_ModelMultiBase lmodel = new MMM_ModelLittleMaid(0.0F);
		MMM_TextureManager.defaultModel = new MMM_ModelMultiBase[] {
				lmodel,
				new MMM_ModelLittleMaid(0.1F),
				new MMM_ModelLittleMaid(0.5F)
		};
	}

	public static void onCriticalHit(LMM_EntityLittleMaidAvatar pAvatar, Entity par1Entity) {
		pAvatar.mc.effectRenderer.addEffect(new EntityCrit2FX(pAvatar.mc.theWorld, par1Entity));
	}

	public static void onEnchantmentCritical(LMM_EntityLittleMaidAvatar pAvatar, Entity par1Entity) {
		EntityCrit2FX entitycrit2fx = new EntityCrit2FX(pAvatar.mc.theWorld, par1Entity, "magicCrit");
		pAvatar.mc.effectRenderer.addEffect(entitycrit2fx);
	}

	public static void onItemPickup(LMM_EntityLittleMaidAvatar pAvatar, Entity entity, int i) {
		// 繧｢繧､繝�繝�蝗槫庶縺ｮ繧ｨ繝輔ぉ繧ｯ繝�
		// TODO:縺薙▲縺｡繧剃ｽｿ縺�縺具ｼ�
		//        mc.effectRenderer.addEffect(new EntityPickupFX(mc.theWorld, entity, avatar, -0.5F));
		pAvatar.mc.effectRenderer.addEffect(new EntityPickupFX(pAvatar.mc.theWorld, entity, pAvatar.avatar, 0.1F));
	}

	public static void OpenIFF(LMM_EntityLittleMaid pLittleMaid, EntityPlayer pPlayer) {
		ModLoader.openGUI(pPlayer, new LMM_GuiIFF(pLittleMaid.worldObj, pLittleMaid));

<<<<<<< HEAD
// Avatarr
	
	public static void onItemPickup(LMM_EntityLittleMaidAvatar pAvatar, Entity entity, int i) {
		// アイテム回収のエフェクト
		// TODO:こっちを使うか？
//        mc.effectRenderer.addEffect(new EntityPickupFX(mc.theWorld, entity, avatar, -0.5F));
		MMM_Helper.mc.effectRenderer.addEffect(new EntityPickupFX(MMM_Helper.mc.theWorld, entity, pAvatar.avatar, 0.1F));
	}

	public static void onCriticalHit(LMM_EntityLittleMaidAvatar pAvatar, Entity par1Entity) {
		MMM_Helper.mc.effectRenderer.addEffect(new EntityCrit2FX(MMM_Helper.mc.theWorld, par1Entity));
	}

	public static void onEnchantmentCritical(LMM_EntityLittleMaidAvatar pAvatar, Entity par1Entity) {
		EntityCrit2FX entitycrit2fx = new EntityCrit2FX(MMM_Helper.mc.theWorld, par1Entity, "magicCrit");
		MMM_Helper.mc.effectRenderer.addEffect(entitycrit2fx);
	}
=======
	}

	// Avatarr

	public static void setArmorTextureValue(LMM_EntityLittleMaid pEntity) {
		if (pEntity.textureArmorName == null) {
			return;
		}
		// 繧｢繝ｼ繝槭�ｼ繝｢繝�繝ｫ
		MMM_TextureBox ltb = MMM_TextureManager.getTextureBox(pEntity.textureArmorName);
		pEntity.textureModel1 = ltb.models[1];
		pEntity.textureModel2 = ltb.models[2];
		//		mod_LMM_littleMaidMob.Debug(String.format("Model:%s / %s", pEntity.textureModel0.getClass().getSimpleName(), pEntity.textureModel1.getClass().getSimpleName()));

		for (int i = 0; i < 4; i++) {
			ItemStack is = pEntity.maidInventory.armorItemInSlot(i);
			pEntity.textureArmor1[i] = MMM_TextureManager.getArmorTextureName(pEntity.textureArmorName,
					MMM_TextureManager.tx_armor1, is);
			pEntity.textureArmor2[i] = MMM_TextureManager.getArmorTextureName(pEntity.textureArmorName,
					MMM_TextureManager.tx_armor2, is);
		}
	}

	public static void setNextTexturePackege(LMM_EntityLittleMaid pEntity, int pTargetTexture) {
		if (pTargetTexture == 0) {
			if (pEntity.maidContract) {
				pEntity.textureName = MMM_TextureManager.getNextPackege(pEntity.textureName,
						(pEntity.maidColor & 0x00ff));
			} else {
				pEntity.textureName = MMM_TextureManager.getNextPackege(pEntity.textureName,
						(pEntity.maidColor & 0x00ff) + MMM_TextureManager.tx_wild);
			}
			if (pEntity.textureName == null) {
				// 謖�螳夊牡縺檎┌縺�蝣ｴ蜷医�ｯ讓呎ｺ悶Δ繝�繝ｫ縺ｫ
				pEntity.textureName = pEntity.textureArmorName = "default";
				pEntity.maidColor = 12;
			} else {
				pEntity.textureArmorName = pEntity.textureName;
			}
			if (!MMM_TextureManager.getTextureBox(pEntity.textureArmorName).hasArmor()) {
				pTargetTexture = 1;
			}
		}
		if (pTargetTexture == 1) {
			pEntity.textureArmorName = MMM_TextureManager.getNextArmorPackege(pEntity.textureArmorName);
		}
		//		pEntity.sendTextureToServer();
	}

	public static void setPrevTexturePackege(LMM_EntityLittleMaid pEntity, int pTargetTexture) {
		if (pTargetTexture == 0) {
			if (pEntity.maidContract) {
				pEntity.textureName = MMM_TextureManager.getPrevPackege(pEntity.textureName,
						(pEntity.maidColor & 0x00ff));
			} else {
				pEntity.textureName = MMM_TextureManager.getPrevPackege(pEntity.textureName,
						(pEntity.maidColor & 0x00ff) + MMM_TextureManager.tx_wild);
			}
			pEntity.textureArmorName = pEntity.textureName;
			if (!MMM_TextureManager.getTextureBox(pEntity.textureArmorName).hasArmor()) {
				pTargetTexture = 1;
			}
		}
		if (pTargetTexture == 1) {
			pEntity.textureArmorName = MMM_TextureManager.getPrevArmorPackege(pEntity.textureArmorName);
		}
		//		pEntity.sendTextureToServer();
	}

	// Network
>>>>>>> 3c9267ee863704790532f2c9b8ddc171642033f0

	// 繝�繧ｯ繧ｹ繝√Ε縲√Δ繝�繝ｫ縲∬牡
	public static void setTextureValue(LMM_EntityLittleMaid pEntity) {
		if (pEntity.textureName == null) {
			return;
		}
		int i = pEntity.maidColor & 0x00ff;
		if (!pEntity.maidContract) {
			i += MMM_TextureManager.tx_wild;
		}

		pEntity.texture = MMM_TextureManager.getTextureName(pEntity.textureName, i);
		//		mod_littleMaidMob.Debug(String.format("id:%d, tex:%s", entityId, texture));
		if (pEntity.texture == null) {
			mod_LMM_littleMaidMob.Debug("tex-null");
			setNextTexturePackege(pEntity, 0);
			i = pEntity.maidColor;
			if (!pEntity.maidContract) {
				i += MMM_TextureManager.tx_wild;
			}
			pEntity.texture = MMM_TextureManager.getTextureName(pEntity.textureName, i);
		}
<<<<<<< HEAD
		mod_LMM_littleMaidMob.Debug(String.format("LMM|Upd Clt Call[%2x:%d].", lmode, leid));
		
		switch (lmode) {
		case LMN_Client_SwingArm : 
			// 腕振り
			byte larm = var2.data[5];
			LMM_EnumSound lsound = LMM_EnumSound.getEnumSound(MMM_Helper.getInt(var2.data, 6));
			lemaid.setSwinging(larm, lsound);
//			mod_LMM_littleMaidMob.Debug(String.format("SwingSound:%s", lsound.name()));
			break;
			
		case LMN_Client_SetIFFValue:
			// IFFの設定値を受信
			int lval = var2.data[1];
			int lindex = MMM_Helper.getInt(var2.data, 2);
			String lname = (String)LMM_IFF.DefaultIFF.keySet().toArray()[lindex];
			mod_LMM_littleMaidMob.Debug("setIFF-CL %s(%d)=%d", lname, lindex, lval);
			LMM_IFF.setIFFValue(null, lname, lval);
			break;
			
		case LMN_Client_PlaySound : 
			// 音声再生
			LMM_EnumSound lsound9 = LMM_EnumSound.getEnumSound(MMM_Helper.getInt(var2.data, 5));
			lemaid.playLittleMaidSound(lsound9, true);
			mod_LMM_littleMaidMob.Debug(String.format("playSound:%s", lsound9.name()));
			break;
			
=======
		// 繝｢繝�繝ｫ縺ｮ險ｭ螳�
		MMM_TextureBox ltb = MMM_TextureManager.getTextureBox(pEntity.textureName);
		pEntity.textureModel0 = ltb.models[0];
		// 霄ｫ髟ｷ螟画峩逕ｨ
		pEntity.setSize(-1F, 0F);
		pEntity.setSize(pEntity.textureModel0.getWidth(), pEntity.textureModel0.getHeight());
		pEntity.setPosition(pEntity.posX, pEntity.posY, pEntity.posZ);
		mod_LMM_littleMaidMob.Debug(String.format("ID:%d, TextureModel:%s", pEntity.entityId, ltb.modelName));
		// 繝｢繝�繝ｫ縺ｮ蛻晄悄蛹�
		pEntity.textureModel0.changeModel(pEntity.maidCaps);
		// 繧ｹ繧ｿ繝薙�ｮ莉倥¢譖ｿ縺�
		for (Entry<String, MMM_EquippedStabilizer> le : pEntity.maidStabilizer.entrySet()) {
			if (le.getValue() != null) {
				le.getValue().updateEquippedPoint(pEntity.textureModel0);
			}
>>>>>>> 3c9267ee863704790532f2c9b8ddc171642033f0
		}
		// 繧｢繝ｼ繝槭�ｼ
		setArmorTextureValue(pEntity);
		pEntity.maidSoundRate = LMM_SoundManager.getSoundRate(pEntity.textureName, pEntity.maidColor);
	}

<<<<<<< HEAD
	public static void setAchievement() {
		MMM_Helper.mc.statFileWriter.readStat(mod_LMM_littleMaidMob.ac_Contract, 1);
	}

=======
>>>>>>> 3c9267ee863704790532f2c9b8ddc171642033f0
}
