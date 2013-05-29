package net.minecraft.src;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.EntityLiving;

public interface MMM_IModelBaseMMM extends MMM_IModelCaps {

	public void renderItems(EntityLiving pEntity, Render pRender);
	public void showArmorParts(int pParts);
	public void setEntityCaps(MMM_IModelCaps pModelCaps);
	public void setRender(Render pRender);
	public void setArmorRendering(boolean pFlag);

}
