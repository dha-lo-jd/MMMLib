package net.minecraft.src;

import java.util.Map;

import org.lwjgl.opengl.EXTRescaleNormal;
import org.lwjgl.opengl.GL11;

public class LMM_GuiTextureSelect extends GuiScreen {

	private String screenTitle = "Texture Select";
	private boolean lastDebug;
	protected GuiScreen owner;
	protected LMM_GuiTextureSlot selectPanel;
	protected GuiButton modeButton[] = new GuiButton[2];
	protected LMM_EntityLittleMaid theMaid;
	public int canSelectColor;
	public int selectColor;
	protected boolean toServer;


	public LMM_GuiTextureSelect(GuiScreen pOwner, LMM_EntityLittleMaid pEntity, int pColor, boolean pToServer) {
		owner = pOwner;
		theMaid = pEntity;
		canSelectColor = pColor;
		selectColor = pEntity.maidColor;
		toServer = pToServer;
		lastDebug = mod_LMM_littleMaidMob.DebugMessage;
		mod_LMM_littleMaidMob.DebugMessage = false;
	}

	@Override
	public void initGui() {
		selectPanel = new LMM_GuiTextureSlot(this);
		selectPanel.registerScrollButtons(buttonList, 3, 4);
		buttonList.add(modeButton[0] = new GuiButton(100, width / 2 - 55, height - 55, 80, 20, "Texture"));
		buttonList.add(modeButton[1] = new GuiButton(101, width / 2 + 30, height - 55, 80, 20, "Armor"));
		buttonList.add(new GuiButton(200, width / 2 - 10, height - 30, 120, 20, "Select"));
		modeButton[0].enabled = false;
	}

	@Override
	protected void keyTyped(char par1, int par2) {
		if (par2 == 1) {
			mc.displayGuiScreen(owner);
		}
	}

	@Override
	public void onGuiClosed() {
		super.onGuiClosed();
		mod_LMM_littleMaidMob.DebugMessage = lastDebug;
	}

	@Override
	public void drawScreen(int par1, int par2, float par3) {
		drawDefaultBackground();
		selectPanel.drawScreen(par1, par2, par3);
		drawCenteredString(fontRenderer, StatCollector.translateToLocal(screenTitle), width / 2, 4, 0xffffff);
		
		super.drawScreen(par1, par2, par3);
		
		GL11.glPushMatrix();
		GL11.glEnable(EXTRescaleNormal.GL_RESCALE_NORMAL_EXT);
		GL11.glEnable(GL11.GL_COLOR_MATERIAL);
		GL11.glEnable(GL11.GL_DEPTH_TEST);
		GL11.glColor3f(1.0F, 1.0F, 1.0F);
		RenderHelper.enableGUIStandardItemLighting();
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240.0F, 240.0F);
		MMM_TextureBox lbox = selectPanel.getSelectedBox();
		GL11.glTranslatef(width / 2 - 115F, height - 5F, 100F);
		GL11.glScalef(60F, -60F, 60F);
//		selectPanel.maid.maidContract = true;
		selectPanel.maid.renderYawOffset = -25F;
		selectPanel.maid.rotationYawHead = -10F;
		if (selectPanel.mode) {
			selectPanel.maid.textureBox[0] = selectPanel.blankBox;
			selectPanel.maid.textureBox[1] = lbox;
			Map<Integer, String> lmap = lbox.armors.get("default");
			selectPanel.maid.textureArmor1[0] = selectPanel.maid.textureArmor1[1] = 
					selectPanel.maid.textureArmor1[2] = selectPanel.maid.textureArmor1[3] =
					(new StringBuilder()).append(lbox.textureDir[1])
					.append(lbox.fileName.replace('.', '/'))
					.append(lmap.get(0x0040))
					.toString();
			selectPanel.maid.textureArmor2[0] = selectPanel.maid.textureArmor2[1] = 
					selectPanel.maid.textureArmor2[2] = selectPanel.maid.textureArmor2[3] =
					(new StringBuilder()).append(lbox.textureDir[1]).append(lbox.fileName.replace('.', '/')).append(lmap.get(0x0050)).toString();
			selectPanel.maid.textureBox[1] = lbox;
		} else {
			selectPanel.maid.textureBox[0] = lbox;
			selectPanel.maid.textureBox[1] = selectPanel.blankBox;
			selectPanel.maid.maidColor = selectColor;
			selectPanel.maid.texture = lbox.getTextureName(selectColor + (selectPanel.isContract ? 0 : MMM_TextureManager.tx_wild));
		}
		RenderManager.instance.renderEntityWithPosYaw(selectPanel.maid, 0.0D, 0.0D, 0.0D, 0.0F, 1.0F);
		for (int li = 0; li < 16; li++) {
			if (lbox.hasColor(li)) {
				break;
			}
		}
		GL11.glDisable(EXTRescaleNormal.GL_RESCALE_NORMAL_EXT);
		GL11.glPopMatrix();
		
		
	}

	@Override
	protected void actionPerformed(GuiButton par1GuiButton) {
		switch (par1GuiButton.id) {
		case 100:
			modeButton[0].enabled = false;
			modeButton[1].enabled = true;
			selectPanel.setMode(false);
			break;
		case 101:
			modeButton[0].enabled = true;
			modeButton[1].enabled = false;
			selectPanel.setMode(true);
			break;
		case 200:
			mod_LMM_littleMaidMob.DebugMessage = lastDebug;
			boolean lflag = false;
			theMaid.maidColor = selectColor;
			if (selectPanel.texsel[0] > -1) {
				theMaid.textureBox[0] = selectPanel.getSelectedBox(false);
			}
			if (selectPanel.texsel[1] > -1) {
				theMaid.textureBox[1] = selectPanel.getSelectedBox(true);
			}
//			theMaid.setTextureNames();
			if (toServer) {
				if (selectColor != selectPanel.color) {
					// �F���̐ݒ�
//					theMaid.maidColor = selectPanel.color | 0x010000 | (selectColor << 8);
					// �T�[�o�[�֐����̎g�p��ʒm
					byte ldata[] = new byte[2];
					ldata[0] = LMM_Net.LMN_Server_DecDyePowder;
					ldata[1] = (byte)selectColor;
					LMM_Net.sendToServer(ldata);
				}
				theMaid.sendTextureToServer();
			} else {
				theMaid.textureIndex[0] = MMM_TextureManager.getIndexTextureBoxServer(theMaid, theMaid.textureBox[0].textureName);
				theMaid.textureIndex[1] = MMM_TextureManager.getIndexTextureBoxServer(theMaid, theMaid.textureBox[1].textureName);
			}
			System.out.println(String.format("select: %d(%s), %d(%s)",
					selectPanel.texsel[0], theMaid.textureBox[0].textureName,
					selectPanel.texsel[1], theMaid.textureBox[1].textureName));
			mc.displayGuiScreen(owner);
			break;
		}
	}

	@Override
	protected void mouseClicked(int par1, int par2, int par3) {
		super.mouseClicked(par1, par2, par3);
		
	}

}