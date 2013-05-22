package net.minecraft.client.model;

import java.util.Map;
import java.util.Random;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.src.MMM_IModelCaps;

import org.lwjgl.opengl.GL11;

/**
 * アーマーの二重描画用クラス。
 * 必ずInner側にはモデルを設定すること。
 * 通常のRendererで描画するためのクラスなので、Renderをちゃんと記述するならいらないクラスです。
 */
public class MMM_ModelDuo extends ModelBase implements MMM_IModelCaps {

	public MMM_ModelDuo capsLink;
	public boolean isAlphablend;
	public boolean isModelAlphablend;
	public MMM_ModelMultiBase modelArmorInner;
	public MMM_ModelMultiBase modelArmorOuter;
	public RenderLiving renderLiving;
	/**
	 * 描画されるアーマーの部位。
	 * shouldRenderPassとかで指定する。
	 */
	public int renderParts;
	/**
	 * 部位毎のアーマーテクスチャの指定。
	 * 内側。
	 */
	public String[] textureInner;
	/**
	 * 部位毎のアーマーテクスチャの指定。
	 * 外側。
	 */
	public String[] textureOuter;

	public MMM_ModelDuo(RenderLiving pRender) {
		renderLiving = pRender;
		renderParts = 0;
	}

	@Override
	public Object getCapsValue(int pIndex, Object... pArg) {
		return modelArmorInner == null ? null : modelArmorInner.getCapsValue(pIndex, pArg);
	}

	@Override
	public Map<String, Integer> getModelCaps() {
		return modelArmorInner == null ? null : modelArmorInner.getModelCaps();
	}

	@Override
	public ModelRenderer getRandomModelBox(Random par1Random) {
		return modelArmorInner.getRandomModelBoxMM(par1Random);
	}

	@Override
	public TextureOffset getTextureOffset(String par1Str) {
		return modelArmorInner.getTextureOffsetMM(par1Str);
	}

	@Override
	public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7) {
		if (isAlphablend) {
			if (isModelAlphablend) {
				GL11.glEnable(GL11.GL_BLEND);
				GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
			} else {
				GL11.glDisable(GL11.GL_BLEND);
			}
		}
		while (modelArmorInner != null) {
			if (textureInner != null) {
				if (textureInner[renderParts] == null) {
					break;
				}
				renderLiving.loadTexture(textureInner[renderParts]);
			}
			modelArmorInner.renderMM(par2, par3, par4, par5, par6, par7);
			break;
		}
		while (modelArmorOuter != null) {
			if (textureOuter != null) {
				if (textureOuter[renderParts] == null) {
					break;
				}
				renderLiving.loadTexture(textureOuter[renderParts]);
			}
			modelArmorOuter.renderMM(par2, par3, par4, par5, par6, par7);
			break;
		}
		isAlphablend = false;
	}

	public void renderItems(EntityLiving pEntity, Render pRender) {
		if (modelArmorInner != null) {
			modelArmorInner.renderItems();
		}
	}

	public void setArmorRendering(boolean pFlag) {
		if (modelArmorInner != null) {
			modelArmorInner.isRendering = pFlag;
		}
	}

	@Override
	public boolean setCapsValue(int pIndex, Object... pArg) {
		if (capsLink != null) {
			capsLink.setCapsValue(pIndex, pArg);
		}
		if (modelArmorOuter != null) {
			modelArmorOuter.setCapsValue(pIndex, pArg);
		}
		if (modelArmorInner != null) {
			return modelArmorInner.setCapsValue(pIndex, pArg);
		}
		return false;
	}

	/**
	 * Renderer辺でこの変数を設定する。
	 * 設定値はMMM_IModelCapsを継承したEntitiyとかを想定。
	 */
	public void setEntityCaps(MMM_IModelCaps pModelCaps) {
		if (modelArmorInner != null) {
			modelArmorInner.setEntityCaps(pModelCaps);
		}
		if (modelArmorOuter != null) {
			modelArmorOuter.setEntityCaps(pModelCaps);
		}
		if (capsLink != null) {
			capsLink.setEntityCaps(pModelCaps);
		}
	}

	@Override
	public void setLivingAnimations(EntityLiving par1EntityLiving, float par2, float par3, float par4) {
		if (modelArmorInner != null) {
			modelArmorInner.setLivingAnimationsMM(par2, par3, par4);
		}
		if (modelArmorOuter != null) {
			modelArmorOuter.setLivingAnimationsMM(par2, par3, par4);
		}
		isAlphablend = true;
	}

	public void setRender(Render pRender) {
		if (modelArmorInner != null) {
			modelArmorInner.render = pRender;
		}
		if (modelArmorOuter != null) {
			modelArmorOuter.render = pRender;
		}
	}

	@Override
	public void setRotationAngles(float par1, float par2, float par3,
			float par4, float par5, float par6, Entity par7Entity) {
		if (modelArmorInner != null) {
			modelArmorInner.setRotationAnglesMM(par1, par2, par3, par4, par5, par6);
		}
		if (modelArmorOuter != null) {
			modelArmorOuter.setRotationAnglesMM(par1, par2, par3, par4, par5, par6);
		}
	}

	public void showArmorParts(int pIndex) {
		if (modelArmorInner != null) {
			modelArmorInner.showArmorParts(pIndex);
		}
		if (modelArmorOuter != null) {
			modelArmorOuter.showArmorParts(pIndex);
		}
	}

}
