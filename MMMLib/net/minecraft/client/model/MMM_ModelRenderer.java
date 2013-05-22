package net.minecraft.client.model;

import static net.minecraft.src.MMM_IModelCaps.*;

import java.lang.reflect.Constructor;
import java.nio.FloatBuffer;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.GLAllocation;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.tileentity.TileEntitySkullRenderer;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemSkull;
import net.minecraft.item.ItemStack;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

public class MMM_ModelRenderer extends ModelRenderer {

	/**
	 * (180F / (float)Math.PI)
	 */
	public static final float radFactor = 57.295779513082320876798154814105F;
	/**
	 * PI / 180
	 */
	public static final float degFactor = 0.01745329251994329576923690768489F;

	public static final int RotXYZ = 0;
	public static final int RotXZY = 1;
	public static final int RotYXZ = 2;
	public static final int RotYZX = 3;
	public static final int RotZXY = 4;
	public static final int RotZYX = 5;
	public static final int ModeEquip = 0x000;
	public static final int ModeInventory = 0x001;
	public static final int ModeItemStack = 0x002;
	public static final int ModeParts = 0x010;

	public int textureOffsetX;
	public int textureOffsetY;
	public boolean compiled;
	public int displayList;
	public MMM_ModelBase baseModel;

	public int rotatePriority;
	public ItemStack itemstack;
	public boolean adjust;
	public FloatBuffer matrix;
	public boolean isInvertX;

	public float scaleX;
	public float scaleY;
	public float scaleZ;

	public ModelRenderer pearent;



	public MMM_ModelRenderer(MMM_ModelBase pModelBase, String pName) {
		super(pModelBase, pName);

		compiled = false;
		displayList = 0;
		baseModel = pModelBase;

		rotatePriority = RotXYZ;
		itemstack = null;
		adjust = true;
		matrix = BufferUtils.createFloatBuffer(16);
		isInvertX = false;

		scaleX = 1.0F;
		scaleY = 1.0F;
		scaleZ = 1.0F;

		pearent = null;
	}

	public MMM_ModelRenderer(MMM_ModelBase pModelBase, int px, int py) {
		this(pModelBase, null);
		setTextureOffsetMM(px, py);
	}

	public MMM_ModelRenderer(MMM_ModelBase pModelBase) {
		this(pModelBase, null);
	}

	public MMM_ModelRenderer(MMM_ModelBase pModelBase, int px, int py, float pScaleX, float pScaleY, float pScaleZ) {
		this(pModelBase, px, py);
		this.scaleX = pScaleX;
		this.scaleY = pScaleY;
		this.scaleZ = pScaleZ;
	}

	public MMM_ModelRenderer(MMM_ModelBase pModelBase, float pScaleX, float pScaleY, float pScaleZ) {
		this(pModelBase);
		this.scaleX = pScaleX;
		this.scaleY = pScaleY;
		this.scaleZ = pScaleZ;
	}


	public void addChildMM(MMM_ModelRenderer pModelRenderer) {
		super.addChild(pModelRenderer);
	}
	@Override
	@Deprecated
	public void addChild(ModelRenderer par1ModelRenderer) {
		addChildMM((MMM_ModelRenderer)par1ModelRenderer);
	}

	public MMM_ModelRenderer setTextureOffsetMM(int pOffsetX, int pOffsetY) {
		super.setTextureOffset(pOffsetX, pOffsetY);
		textureOffsetX = pOffsetX;
		textureOffsetY = pOffsetY;
		return this;
	}
	@Override
	@Deprecated
	public ModelRenderer setTextureOffset(int par1, int par2) {
		return setTextureOffsetMM(par1, par2);
	}

	public MMM_ModelRenderer setTextureSizeMM(int pWidth, int pHeight) {
		super.setTextureSize(pWidth, pHeight);
		return this;
	}
	@Override
	@Deprecated
	public ModelRenderer setTextureSize(int par1, int par2) {
		return setTextureSizeMM(par1, par2);
	}

	public MMM_ModelRenderer setRotationPointMM(float f, float f1, float f2) {
		super.setRotationPoint(f, f1, f2);
		return this;
	}
	@Override
	@Deprecated
	public void setRotationPoint(float par1, float par2, float par3) {
		setRotationPointMM(par1, par2, par3);
	}

	/**
	 * ModelBox継承の独自オブジェクト追加用
	 */
	public MMM_ModelRenderer addCubeList(MMM_ModelBoxBase pModelBoxBase) {
		cubeList.add(pModelBoxBase);
		return this;
	}

	public MMM_ModelBoxBase getModelBoxBase(Class pModelBoxBase, Object ... pArg) {
		try {
			Constructor<MMM_ModelBoxBase> lconstructor =
					pModelBoxBase.getConstructor(MMM_ModelRenderer.class, Object[].class);
			return lconstructor.newInstance(this, pArg);
		} catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	public Object[] getArg(Object ... pArg) {
		Object lobject[] = new Object[pArg.length + 2];
		lobject[0] = textureOffsetX;
		lobject[1] = textureOffsetY;
		for (int li = 0; li < pArg.length; li++) {
			lobject[2 + li] = pArg[li];
		}
		return lobject;
	}

	public MMM_ModelRenderer addParts(Class pModelBoxBase, String pName, Object ... pArg) {
		pName = (new StringBuilder()).append(boxName).append(".").append(pName).toString();
		TextureOffset ltextureoffset = baseModel.getTextureOffset(pName);
		setTextureOffset(ltextureoffset.textureOffsetX, ltextureoffset.textureOffsetY);
		addCubeList(getModelBoxBase(pModelBoxBase, getArg(pArg)).setBoxNameMM(pName));
		return this;
	}

	public MMM_ModelRenderer addParts(Class pModelBoxBase, Object ... pArg) {
		addCubeList(getModelBoxBase(pModelBoxBase, getArg(pArg)));
		return this;
	}

	/**
	 * 自分でテクスチャの座標を指定する時に使います。
	 * コンストラクタへそのまま値を渡します。
	 */
	public MMM_ModelRenderer addPartsTexture(Class pModelBoxBase, String pName, Object ... pArg) {
		pName = (new StringBuilder()).append(boxName).append(".").append(pName).toString();
		addCubeList(getModelBoxBase(pModelBoxBase, pArg).setBoxNameMM(pName));
		return this;
	}

	/**
	 * 自分でテクスチャの座標を指定する時に使います。
	 * コンストラクタへそのまま値を渡します。
	 */
	public MMM_ModelRenderer addPartsTexture(Class pModelBoxBase, Object ... pArg) {
		addCubeList(getModelBoxBase(pModelBoxBase, pArg));
		return this;
	}


	public MMM_ModelRenderer addBoxMM(float pX, float pY, float pZ,
			int pWidth, int pHeight, int pDepth, float pSizeAdjust) {
		addParts(MMM_ModelBox.class, pX, pY, pZ, pWidth, pHeight, pDepth, pSizeAdjust);
		return this;
	}
	@Override
	@Deprecated
	public ModelRenderer addBox(float par1, float par2, float par3,
			int par4, int par5, int par6) {
		return addBoxMM(par1, par2, par3, par4, par5, par6);
	}

	public MMM_ModelRenderer addBoxMM(float pX, float pY, float pZ,
			int pWidth, int pHeight, int pDepth) {
		addParts(MMM_ModelBox.class, pX, pY, pZ, pWidth, pHeight, pDepth, 0.0F);
		return this;
	}
	@Override
	@Deprecated
	public void addBox(float par1, float par2, float par3,
			int par4, int par5, int par6, float par7) {
		addBoxMM(par1, par2, par3, par4, par5, par6, par7);
	}

	public MMM_ModelRenderer addBoxMM(String pName, float pX, float pY, float pZ,
			int pWidth, int pHeight, int pDepth) {
		addParts(MMM_ModelBox.class, pName, pX, pY, pZ, pWidth, pHeight, pDepth, 0.0F);
		return this;
	}
	@Override
	@Deprecated
	public ModelRenderer addBox(String par1Str, float par2, float par3, float par4,
			int par5, int par6, int par7) {
		return addBoxMM(par1Str, par2, par3, par4, par5, par6, par7);
	}

	public MMM_ModelRenderer addPlate(float pX, float pY, float pZ,
			int pWidth, int pHeight, int pFacePlane) {
		addParts(MMM_ModelPlate.class, pX, pY, pZ, pWidth, pHeight, pFacePlane, 0.0F);
		return this;
	}

	public MMM_ModelRenderer addPlate(float pX, float pY, float pZ,
			int pWidth, int pHeight, int pFacePlane, float pSizeAdjust) {
		addParts(MMM_ModelPlate.class, pX, pY, pZ, pWidth, pHeight, pFacePlane, pSizeAdjust);
		return this;
	}

	public MMM_ModelRenderer addPlate(String pName, float pX, float pY, float pZ,
			int pWidth, int pHeight, int pFacePlane) {
		addParts(MMM_ModelPlate.class, pName, pX, pY, pZ, pWidth, pHeight, pFacePlane, 0.0F);
		return this;
	}

	/**
	 * 描画用のボックス、子供をクリアする
	 */
	public void clearCubeList() {
		cubeList.clear();
		compiled = false;
		childModels.clear();
	}

	@Deprecated
	public MMM_ModelRenderer setItemStack(ItemStack pItemStack) {
		itemstack = pItemStack;
		return this;
	}

	// TODO:このあたりは要修正
	public boolean renderItems(MMM_ModelMultiBase pModelMulti, boolean pRealBlock, int pIndex) {
		ItemStack[] litemstacks = (ItemStack[])MMM_ModelCapsHelper.getCapsValue(pModelMulti.entityCaps, caps_Items);
		if (litemstacks == null) return false;
		EnumAction[] lactions = (EnumAction[])MMM_ModelCapsHelper.getCapsValue(pModelMulti.entityCaps, caps_Actions);
		EntityLiving lentity = (EntityLiving)pModelMulti.entityCaps.getCapsValue(caps_Entity);

		renderItems(lentity, pModelMulti.render, pRealBlock, lactions[pIndex], litemstacks[pIndex]);
		return true;
	}

	public void renderItemsHead(MMM_ModelMultiBase pModelMulti) {
		ItemStack lis = (ItemStack)pModelMulti.entityCaps.getCapsValue(caps_HeadMount);
		EntityLiving lentity = (EntityLiving)pModelMulti.entityCaps.getCapsValue(caps_Entity);

		renderItems(lentity, pModelMulti.render, true, null, lis);
	}

	public void renderItems(EntityLiving pEntityLiving, Render pRender,
			boolean pRealBlock, EnumAction pAction, ItemStack pItemStack) {
		itemstack = pItemStack;
		renderItems(pEntityLiving, pRender, pRealBlock, pAction);
	}

	public void renderItems(EntityLiving pEntityLiving, Render pRender, boolean pRealBlock, EnumAction pAction) {
		if (itemstack == null) return;

		// アイテムのレンダリング
		GL11.glPushMatrix();

		// アイテムの種類による表示位置の補正
		if (adjust) {
			// GL11.glTranslatef(-0.0625F, 0.4375F, 0.0625F);

			if (pRealBlock && (itemstack.getItem() instanceof ItemBlock)) {
				float f2 = 0.625F;
				GL11.glScalef(f2, -f2, -f2);
				GL11.glRotatef(270F, 0F, 1F, 0);
			} else if (pRealBlock && (itemstack.getItem() instanceof ItemSkull)) {
				float f2 = 1.0625F;
				GL11.glScalef(f2, -f2, -f2);
			} else {
				float var6;
				if (itemstack.itemID < 256
						&& RenderBlocks.renderItemIn3d(Block.blocksList[itemstack.itemID].getRenderType())) {
					var6 = 0.5F;
					// GL11.glTranslatef(0.0F, 0.1875F, -0.3125F);
					GL11.glTranslatef(0.0F, 0.1875F, -0.2125F);
					var6 *= 0.75F;
					GL11.glRotatef(20.0F, 1.0F, 0.0F, 0.0F);
					GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
					GL11.glScalef(var6, -var6, var6);
				} else if (itemstack.getItem() instanceof ItemBow) {
					var6 = 0.625F;
					GL11.glTranslatef(-0.05F, 0.125F, 0.3125F);
					GL11.glRotatef(-20.0F, 0.0F, 1.0F, 0.0F);
					GL11.glScalef(var6, -var6, var6);
					GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
					GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
				} else if (Item.itemsList[itemstack.itemID].isFull3D()) {
					var6 = 0.625F;

					if (Item.itemsList[itemstack.itemID]
							.shouldRotateAroundWhenRendering()) {
						GL11.glRotatef(180.0F, 0.0F, 0.0F, 1.0F);
						GL11.glTranslatef(0.0F, -0.125F, 0.0F);
					}

					if (pAction == EnumAction.block) {
						GL11.glTranslatef(0.05F, 0.0F, -0.1F);
						GL11.glRotatef(-50.0F, 0.0F, 1.0F, 0.0F);
						GL11.glRotatef(-10.0F, 1.0F, 0.0F, 0.0F);
						GL11.glRotatef(-60.0F, 0.0F, 0.0F, 1.0F);
					}

					GL11.glTranslatef(0.0F, 0.1875F, 0.1F);
					GL11.glScalef(var6, -var6, var6);
					GL11.glRotatef(-100.0F, 1.0F, 0.0F, 0.0F);
					GL11.glRotatef(45.0F, 0.0F, 1.0F, 0.0F);
				} else {
					var6 = 0.375F;
					GL11.glTranslatef(0.15F, 0.15F, -0.05F);
					// GL11.glTranslatef(0.25F, 0.1875F, -0.1875F);
					GL11.glScalef(var6, var6, var6);
					GL11.glRotatef(60.0F, 0.0F, 0.0F, 1.0F);
					GL11.glRotatef(-90.0F, 1.0F, 0.0F, 0.0F);
					GL11.glRotatef(20.0F, 0.0F, 0.0F, 1.0F);
				}
			}
		}

		if (pRealBlock && itemstack.getItem() instanceof ItemSkull) {
			String lsowner = "";
			if (itemstack.hasTagCompound() && itemstack.getTagCompound().hasKey("SkullOwner")) {
				lsowner = itemstack.getTagCompound().getString("SkullOwner");
			}
			TileEntitySkullRenderer.skullRenderer.func_82393_a(-0.5F, -0.25F, -0.5F, 1, 180.0F,
					itemstack.getItemDamage(), lsowner);
		} else if (pRealBlock && itemstack.getItem() instanceof ItemBlock) {
			pRender.loadTexture("/terrain.png");
			GL11.glEnable(GL11.GL_CULL_FACE);
			pRender.renderBlocks.renderBlockAsItem(
					Block.blocksList[itemstack.itemID],
					itemstack.getItemDamage(), 1.0F);
			GL11.glDisable(GL11.GL_CULL_FACE);
		} else {
			// アイテムに色付け
			pRender.loadTexture("/gui/items.png");
			for (int j = 0; j <= (itemstack.getItem()
					.requiresMultipleRenderPasses() ? 1 : 0); j++) {
				int k = itemstack.getItem().getColorFromItemStack(itemstack, j);
				float f15 = (float) (k >> 16 & 0xff) / 255F;
				float f17 = (float) (k >> 8 & 0xff) / 255F;
				float f19 = (float) (k & 0xff) / 255F;
				GL11.glColor4f(f15, f17, f19, 1.0F);
				pRender.renderManager.itemRenderer.renderItem(pEntityLiving,
						itemstack, j);
			}
		}

		GL11.glPopMatrix();
	}

	/**
	 *  回転変換を行う順序を指定。
	 * @param pValue
	 * Rot???を指定する
	 */
	public void setRotatePriority(int pValue) {
		rotatePriority = pValue;
	}

	/**
	 * 内部実行用、座標変換部
	 */
	public void setRotation() {
		// 変換順位の設定
		switch (rotatePriority) {
		case RotXYZ:
			if (rotateAngleZ != 0.0F) {
				GL11.glRotatef(rotateAngleZ * radFactor, 0.0F, 0.0F, 1.0F);
			}
			if (rotateAngleY != 0.0F) {
				GL11.glRotatef(rotateAngleY * radFactor, 0.0F, 1.0F, 0.0F);
			}
			if (rotateAngleX != 0.0F) {
				GL11.glRotatef(rotateAngleX * radFactor, 1.0F, 0.0F, 0.0F);
			}
			break;
		case RotXZY:
			if (rotateAngleY != 0.0F) {
				GL11.glRotatef(rotateAngleY * radFactor, 0.0F, 1.0F, 0.0F);
			}
			if (rotateAngleZ != 0.0F) {
				GL11.glRotatef(rotateAngleZ * radFactor, 0.0F, 0.0F, 1.0F);
			}
			if (rotateAngleX != 0.0F) {
				GL11.glRotatef(rotateAngleX * radFactor, 1.0F, 0.0F, 0.0F);
			}
			break;
		case RotYXZ:
			if (rotateAngleZ != 0.0F) {
				GL11.glRotatef(rotateAngleZ * radFactor, 0.0F, 0.0F, 1.0F);
			}
			if (rotateAngleX != 0.0F) {
				GL11.glRotatef(rotateAngleX * radFactor, 1.0F, 0.0F, 0.0F);
			}
			if (rotateAngleY != 0.0F) {
				GL11.glRotatef(rotateAngleY * radFactor, 0.0F, 1.0F, 0.0F);
			}
			break;
		case RotYZX:
			if (rotateAngleX != 0.0F) {
				GL11.glRotatef(rotateAngleX * radFactor, 1.0F, 0.0F, 0.0F);
			}
			if (rotateAngleZ != 0.0F) {
				GL11.glRotatef(rotateAngleZ * radFactor, 0.0F, 0.0F, 1.0F);
			}
			if (rotateAngleY != 0.0F) {
				GL11.glRotatef(rotateAngleY * radFactor, 0.0F, 1.0F, 0.0F);
			}
			break;
		case RotZXY:
			if (rotateAngleY != 0.0F) {
				GL11.glRotatef(rotateAngleY * radFactor, 0.0F, 1.0F, 0.0F);
			}
			if (rotateAngleX != 0.0F) {
				GL11.glRotatef(rotateAngleX * radFactor, 1.0F, 0.0F, 0.0F);
			}
			if (rotateAngleZ != 0.0F) {
				GL11.glRotatef(rotateAngleZ * radFactor, 0.0F, 0.0F, 1.0F);
			}
			break;
		case RotZYX:
			if (rotateAngleX != 0.0F) {
				GL11.glRotatef(rotateAngleX * radFactor, 1.0F, 0.0F, 0.0F);
			}
			if (rotateAngleY != 0.0F) {
				GL11.glRotatef(rotateAngleY * radFactor, 0.0F, 1.0F, 0.0F);
			}
			if (rotateAngleZ != 0.0F) {
				GL11.glRotatef(rotateAngleZ * radFactor, 0.0F, 0.0F, 1.0F);
			}
			break;
		}
	}

	/**
	 * 内部実行用、レンダリング部分。
	 */
	public void renderObject(float par1) {
		// レンダリング、あと子供も
		GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX, matrix);
		if (!(baseModel instanceof MMM_ModelMultiBase) || ((MMM_ModelMultiBase)baseModel).isRendering) {
			GL11.glPushMatrix();
			GL11.glScalef(scaleX, scaleY, scaleZ);
			GL11.glCallList(displayList);
			GL11.glPopMatrix();
		}

		if (childModels != null) {
			for (int li = 0; li < childModels.size(); li++) {
				((MMM_ModelRenderer) childModels.get(li)).renderMM(par1);
			}
		}
	}

	// TODO:アップデート時はここをチェックすること
	public void renderMM(float par1) {
		if (isHidden) {
			return;
		}

		if (!showModel) {
			return;
		}

		if (!compiled) {
			compileDisplayList(par1);
		}

		if (rotateAngleX != 0.0F || rotateAngleY != 0.0F || rotateAngleZ != 0.0F) {
			GL11.glPushMatrix();
			GL11.glTranslatef(rotationPointX * par1, rotationPointY * par1, rotationPointZ * par1);

			setRotation();
			renderObject(par1);

			GL11.glPopMatrix();
		} else if (rotationPointX != 0.0F || rotationPointY != 0.0F || rotationPointZ != 0.0F) {
			GL11.glTranslatef(rotationPointX * par1, rotationPointY * par1, rotationPointZ * par1);

			renderObject(par1);

			GL11.glTranslatef(-rotationPointX * par1, -rotationPointY * par1, -rotationPointZ * par1);
		} else {
			renderObject(par1);
		}
	}
	@Override
	@Deprecated
	public void render(float par1) {
		renderMM(par1);
	}

	public void renderWithRotationMM(float par1) {
		if (isHidden) {
			return;
		}

		if (!showModel) {
			return;
		}

		if (!compiled) {
			compileDisplayList(par1);
		}

		GL11.glPushMatrix();
		GL11.glTranslatef(rotationPointX * par1, rotationPointY * par1,
				rotationPointZ * par1);

		setRotation();

		GL11.glCallList(displayList);
		GL11.glPopMatrix();
	}
	@Override
	@Deprecated
	public void renderWithRotation(float par1) {
		renderWithRotationMM(par1);
	}

	public void postRenderMM(float par1) {
		if (isHidden) {
			return;
		}

		if (!showModel) {
			return;
		}

		if (!compiled) {
			compileDisplayList(par1);
		}

		if (pearent != null) {
			pearent.postRender(par1);
		}

		if (rotateAngleX != 0.0F || rotateAngleY != 0.0F || rotateAngleZ != 0.0F) {
			GL11.glTranslatef(rotationPointX * par1, rotationPointY * par1, rotationPointZ * par1);

			setRotation();
		} else if (rotationPointX != 0.0F || rotationPointY != 0.0F || rotationPointZ != 0.0F) {
			GL11.glTranslatef(rotationPointX * par1, rotationPointY * par1, rotationPointZ * par1);
		}
	}
	@Override
	@Deprecated
	public void postRender(float par1) {
		postRenderMM(par1);
	}

	/**
	 * 自分以下のパーツの座標変換を行う。
	 * レンダリングはしない。
	 * 主にマトリクスを獲得するのに使う。
	 */
	public void postRenderAll(float par1) {
		if (isHidden) {
			return;
		}

		if (!showModel) {
			return;
		}

		if (!compiled) {
			compileDisplayList(par1);
		}

		if (rotateAngleX != 0.0F || rotateAngleY != 0.0F || rotateAngleZ != 0.0F) {
			GL11.glTranslatef(rotationPointX * par1, rotationPointY * par1, rotationPointZ * par1);

			setRotation();
		} else if (rotationPointX != 0.0F || rotationPointY != 0.0F || rotationPointZ != 0.0F) {
			GL11.glTranslatef(rotationPointX * par1, rotationPointY * par1, rotationPointZ * par1);
		}
		// ポストレンダリング、あと子供も
		GL11.glGetFloat(GL11.GL_MODELVIEW_MATRIX, matrix);

		if (childModels != null) {
			for (int i = 0; i < childModels.size(); i++) {
				((MMM_ModelRenderer) childModels.get(i)).postRenderAll(par1);
			}
		}
	}

	public void compileDisplayList(float par1) {
		displayList = GLAllocation.generateDisplayLists(1);
		GL11.glNewList(displayList, GL11.GL_COMPILE);
		Tessellator tessellator = Tessellator.instance;

		for (int i = 0; i < cubeList.size(); i++) {
			((ModelBox) cubeList.get(i)).render(tessellator, par1);
		}

		GL11.glEndList();
		compiled = true;
	}

	/**
	 * パーツ描画時点のマトリクスを設定する。 これ以前に設定されたマトリクスは破棄される。
	 */
	public MMM_ModelRenderer loadMatrix() {
		GL11.glLoadMatrix(matrix);
		if (isInvertX) {
			GL11.glScalef(-1F, 1F, 1F);
		}
		return this;
	}


	// ゲッター、セッター

	public boolean getMirror() {
		return mirror;
	}

	public void setMirror(boolean flag) {
		mirror = flag;
	}

	public boolean getVisible() {
		return showModel;
	}

	public void setVisible(boolean flag) {
		showModel = flag;
	}


	// Deg付きは角度指定が度数法

	public float getRotateAngleX() {
		return rotateAngleX;
	}

	public float getRotateAngleDegX() {
		return rotateAngleX * radFactor;
	}

	public float setRotateAngleX(float value) {
		return rotateAngleX = value;
	}

	public float setRotateAngleDegX(float value) {
		return rotateAngleX = value * degFactor;
	}

	public float addRotateAngleX(float value) {
		return rotateAngleX += value;
	}

	public float addRotateAngleDegX(float value) {
		return rotateAngleX += value * degFactor;
	}

	public float getRotateAngleY() {
		return rotateAngleY;
	}

	public float getRotateAngleDegY() {
		return rotateAngleY * radFactor;
	}

	public float setRotateAngleY(float value) {
		return rotateAngleY = value;
	}

	public float setRotateAngleDegY(float value) {
		return rotateAngleY = value * degFactor;
	}

	public float addRotateAngleY(float value) {
		return rotateAngleY += value;
	}

	public float addRotateAngleDegY(float value) {
		return rotateAngleY += value * degFactor;
	}

	public float getRotateAngleZ() {
		return rotateAngleZ;
	}

	public float getRotateAngleDegZ() {
		return rotateAngleZ * radFactor;
	}

	public float setRotateAngleZ(float value) {
		return rotateAngleZ = value;
	}

	public float setRotateAngleDegZ(float value) {
		return rotateAngleZ = value * degFactor;
	}

	public float addRotateAngleZ(float value) {
		return rotateAngleZ += value;
	}

	public float addRotateAngleDegZ(float value) {
		return rotateAngleZ += value * degFactor;
	}

	public MMM_ModelRenderer setRotateAngle(float x, float y, float z) {
		rotateAngleX = x;
		rotateAngleY = y;
		rotateAngleZ = z;
		return this;
	}

	public MMM_ModelRenderer setRotateAngleDeg(float x, float y, float z) {
		rotateAngleX = x * degFactor;
		rotateAngleY = y * degFactor;
		rotateAngleZ = z * degFactor;
		return this;
	}

	public float getRotationPointX() {
		return rotationPointX;
	}

	public float setRotationPointX(float value) {
		return rotationPointX = value;
	}

	public float addRotationPointX(float value) {
		return rotationPointX += value;
	}

	public float getRotationPointY() {
		return rotationPointY;
	}

	public float setRotationPointY(float value) {
		return rotationPointY = value;
	}

	public float addRotationPointY(float value) {
		return rotationPointY += value;
	}

	public float getRotationPointZ() {
		return rotationPointZ;
	}

	public float setRotationPointZ(float value) {
		return rotationPointZ = value;
	}

	public float addRotationPointZ(float value) {
		return rotationPointZ += value;
	}

	public MMM_ModelRenderer setScale(float pX, float pY, float pZ) {
		scaleX = pX;
		scaleY = pY;
		scaleZ = pZ;
		return this;
	}

	public float setScaleX(float pValue) {
		return scaleX = pValue;
	}

	public float setScaleY(float pValue) {
		return scaleY = pValue;
	}

	public float setScaleZ(float pValue) {
		return scaleZ = pValue;
	}

}
