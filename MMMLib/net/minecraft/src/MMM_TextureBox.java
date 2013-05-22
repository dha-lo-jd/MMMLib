package net.minecraft.src;

import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import net.minecraft.client.model.MMM_ModelMultiBase;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;

public class MMM_TextureBox {

	/**
	 * テクスチャパックの名称
	 */
	public String packegeName;
	public Map<Integer, String> textures;
	public Map<String, Map<Integer, String>> armors;
	public String modelName;
	public MMM_ModelMultiBase[] models;
	/**
	 * pName, pTextureDir, pClassPrefix
	 */
	public String[] textureDir;
	
	public float modelHeight;
	public float modelWidth;
	public float modelYOffset;



	public MMM_TextureBox() {
		textures = new HashMap<Integer, String>();
		armors = new TreeMap<String, Map<Integer, String>>();
		modelHeight = modelWidth = modelYOffset = 0.0F;
	}
	public MMM_TextureBox(String pName, String[] pSearch) {
		this();
		packegeName = pName;
		textureDir = pSearch;
	}

	public void setModels(String pName, MMM_ModelMultiBase[] pModels) {
		modelName = pName;
		models = pModels;
	}

	public void setModelSize(float pHeight, float pWidth, float pYOffset) {
		modelHeight = pHeight;
		modelWidth = pWidth;
		modelYOffset = pYOffset;
	}

	/**
	 * テクスチャのフルパスを返す。
	 * 登録インデックスが無い場合はNULLを返す。
	 */
	public String getTextureName(int pIndex) {
		if (textures.containsKey(pIndex)) {
			if (textureDir != null) {
				return (new StringBuilder()).append(textureDir[1]).append(packegeName.replace('.', '/')).append(textures.get(pIndex)).toString();
			}
		}
		return null;
	}

	public String getArmorTextureName(int index, ItemStack itemstack) {
		// indexは0x40,0x50番台
		if (armors.isEmpty() || itemstack == null) return null;
		if (!(itemstack.getItem() instanceof ItemArmor)) return null;
		
		Map<Integer, String> m = armors.get(MMM_TextureManager.armorFilenamePrefix[((ItemArmor)itemstack.getItem()).renderIndex]);
		if (m == null) {
			m = armors.get("default");
			if (m == null) return null;
		}
		int l = 0;
		if (itemstack.getMaxDamage() > 0) {
			l = (10 * itemstack.getItemDamage() / itemstack.getMaxDamage());
		}
		String s = null;
		for (int i = index + l; i >= index; i--) {
			s = m.get(i);
			if (s != null) break;
		}
		if (s == null) {
			return null;
		} else {
			return (new StringBuilder()).append(textureDir[1]).append(packegeName.replace('.', '/')).append(s).toString();
		}
	}

	/**
	 * 契約色の有無をビット配列にして返す
	 */
	public int getContractColorBits() {
		int li = 0;
		for (Integer i : textures.keySet()) {
			if (i >= 0x00 && i <= 0x0f) {
				li |= 1 << (i & 0x0f);
			}
		}
		return li;
	}
	/**
	 * 野生色の有無をビット配列にして返す
	 */
	public int getWildColorBits() {
		int li = 0;
		for (Integer i : textures.keySet()) {
			if (i >= MMM_TextureManager.tx_wild && i <= (MMM_TextureManager.tx_wild + 0x0f)) {
				li |= 1 << (i & 0x0f);
			}
		}
		return li;
	}
	
	public boolean hasColor(int pIndex) {
		return textures.containsKey(pIndex);
	}

	public boolean hasColor(int pIndex, boolean pContract) {
		return textures.containsKey(pIndex + (pContract ? 0 : MMM_TextureManager.tx_wild));
	}

	public boolean hasArmor() {
		return !armors.isEmpty();
	}

	public float getHeight() {
		return modelHeight == 0 ? models[0].getHeight() : modelHeight;
	}

	public float getWidth() {
		return modelWidth == 0 ? models[0].getWidth() : modelWidth;
	}

	public float getYOffset() {
		return modelYOffset == 0 ? models[0].getyOffset() : modelYOffset;
	}

}
