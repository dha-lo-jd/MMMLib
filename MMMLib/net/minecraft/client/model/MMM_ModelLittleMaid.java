package net.minecraft.client.model;

import org.lwjgl.opengl.GL11;

/**
 * LMM用に最適化
 */
public class MMM_ModelLittleMaid extends MMM_ModelMultiBase {

	//fields
	public MMM_ModelRenderer bipedHead;
	public MMM_ModelRenderer bipedBody;
	public MMM_ModelRenderer bipedRightArm;
	public MMM_ModelRenderer bipedLeftArm;
	public MMM_ModelRenderer bipedRightLeg;
	public MMM_ModelRenderer bipedLeftLeg;
	public MMM_ModelRenderer Skirt;



	/**
	 * コンストラクタは全て継承させること
	 */
	public MMM_ModelLittleMaid() {
		super();
	}
	/**
	 * コンストラクタは全て継承させること
	 */
	public MMM_ModelLittleMaid(float psize) {
		super(psize);
	}
	/**
	 * コンストラクタは全て継承させること
	 */
	public MMM_ModelLittleMaid(float psize, float pyoffset) {
		super(psize, pyoffset, 64, 32);
	}


	@Override
	public void initModel(float psize, float pyoffset) {
		// 標準型
		Arms = new MMM_ModelRenderer[18];
		// 手持ち
		Arms[0] = new MMM_ModelRenderer(this, 0, 0);
		Arms[0].setRotationPointMM(-1F, 5F, -1F);
		Arms[1] = new MMM_ModelRenderer(this, 0, 0);
		Arms[1].setRotationPointMM(1F, 5F, -1F);
		Arms[1].isInvertX = true;
		// バイプロダクトエフェクター
		Arms[2] = new MMM_ModelRenderer(this, 0, 0);
		Arms[2].setRotationPointMM(-3F, 9F, 6F);
		Arms[2].setRotateAngleDeg(45F, 0F, 0F);
		Arms[3] = new MMM_ModelRenderer(this, 0, 0);
		Arms[3].setRotationPointMM(3F, 9F, 6F);
		Arms[3].setRotateAngleDeg(45F, 0F, 0F);
		Arms[3].isInvertX = true;
		// テールソード
		Arms[4] = new MMM_ModelRenderer(this, 0, 0);
		Arms[4].setRotationPointMM(-2F, 0F, 0F);
		Arms[4].setRotateAngleDeg(180F, 0F, 0F);
		Arms[5] = new MMM_ModelRenderer(this, 0, 0);
		Arms[5].setRotationPointMM(2F, 0F, 0F);
		Arms[5].setRotateAngleDeg(180F, 0F, 0F);
		
		
//		Arms[8] = new MMM_ModelRenderer(this, "HeadTop");
//		Arms[8].setRotationPointMM(0F, -3F, 1F);
		HeadMount.setRotationPointMM(0F, -4F, 0F);
		HeadTop.setRotationPointMM(0F, -8F, 0F);
		
		
		bipedHead = new MMM_ModelRenderer(this, 0, 0);
		bipedHead.setTextureOffsetMM( 0,  0).addBoxMM(-4F, -8F, -4F, 8, 8, 8, psize);			// Head
		bipedHead.setTextureOffsetMM(24,  0).addBoxMM(-4F, 0F, 1F, 8, 4, 3, psize);			// Hire
		bipedHead.setTextureOffsetMM(24, 18).addBoxMM(-5F, -7F, 0.2F, 1, 3, 3, psize);		// ChignonR
		bipedHead.setTextureOffsetMM(24, 18).addBoxMM(4F, -7F, 0.2F, 1, 3, 3, psize);		// ChignonL
		bipedHead.setTextureOffsetMM(52, 10).addBoxMM(-2F, -7.2F, 4F, 4, 4, 2, psize);		// ChignonB
		bipedHead.setTextureOffsetMM(46, 20).addBoxMM(-1.5F, -6.8F, 4F, 3, 9, 3, psize);	// Tail
		bipedHead.setTextureOffsetMM(58, 21).addBoxMM(-5.5F, -6.8F, 0.9F, 1, 8, 2, psize);	// SideTailR
		bipedHead.setMirror(true);
		bipedHead.setTextureOffsetMM(58, 21).addBoxMM(4.5F, -6.8F, 0.9F, 1, 8, 2, psize);	// SideTailL
		bipedHead.setRotationPointMM(0F, 0F, 0F);
//		bipedHead.addChildMM(Arms[8]);
		bipedHead.addChildMM(HeadMount);
		bipedHead.addChildMM(HeadTop);
		
		bipedRightArm = new MMM_ModelRenderer(this, 48, 0);
		bipedRightArm.addBoxMM(-2.0F, -1F, -1F, 2, 8, 2, psize);
		bipedRightArm.setRotationPointMM(-3.0F, 1.5F, 0F);
		bipedRightArm.addChildMM(Arms[0]);
		bipedRightArm.addChildMM(Arms[2]);
		
		bipedLeftArm = new MMM_ModelRenderer(this, 56, 0);
		bipedLeftArm.addBoxMM(0.0F, -1F, -1F, 2, 8, 2, psize);
		bipedLeftArm.setRotationPointMM(3.0F, 1.5F, 0F);
		bipedLeftArm.addChildMM(Arms[1]);
		bipedLeftArm.addChildMM(Arms[3]);
		
		bipedRightLeg = new MMM_ModelRenderer(this, 32, 19);
		bipedRightLeg.addBoxMM(-2F, 0F, -2F, 3, 9, 4, psize);
		bipedRightLeg.setRotationPointMM(-1F, 7F, 0F);
		
		bipedLeftLeg = new MMM_ModelRenderer(this, 32, 19);
		bipedLeftLeg.setMirror(true);
		bipedLeftLeg.addBoxMM(-1F, 0F, -2F, 3, 9, 4, psize);
		bipedLeftLeg.setRotationPointMM(1F, 7F, 0F);
		
		Skirt = new MMM_ModelRenderer(this, 0, 16);
		Skirt.addBoxMM(-4F, -2F, -4F, 8, 8, 8, psize);
		Skirt.setRotationPointMM(0F, 7F, 0F);
		
		bipedBody = new MMM_ModelRenderer(this, 32, 8);
		bipedBody.addBoxMM(-3F, 0F, -2F, 6, 7, 4, psize);
		bipedBody.setRotationPointMM(0F, 0F, 0F);
		bipedBody.addChildMM(bipedRightArm);
		bipedBody.addChildMM(bipedLeftArm);
		bipedBody.addChildMM(Arms[4]);
		bipedBody.addChildMM(Arms[5]);
		
		mainFrame = new MMM_ModelRenderer(this, 0, 0);
		mainFrame.setRotationPointMM(0F, 0F + pyoffset + 8F, 0F);
		mainFrame.addChildMM(bipedHead);
		mainFrame.addChildMM(bipedBody);
		mainFrame.addChildMM(bipedRightLeg);
		mainFrame.addChildMM(bipedLeftLeg);
		mainFrame.addChildMM(Skirt);
		
		
	}

	@Override
	public String getUsingTexture() {
		return "default";
	}

	@Override
	public float[] getArmorModelsSize() {
		return new float[] {0.1F, 0.5F};
	}

	@Override
	public float getHeight() {
		return 1.35F;
	}

	@Override
	public float getWidth() {
		return 0.5F;
	}

	@Override
	public float getyOffset() {
		return 1.215F;
	}

	@Override
	public void setLivingAnimationsMM(float par2, float par3, float pRenderPartialTicks) {
		float angle = MMM_ModelCapsHelper.getCapsValueFloat(entityCaps, caps_interestedAngle, (Float)pRenderPartialTicks);
		bipedHead.setRotateAngleZ(angle);
	}

	/**
	 * 姿勢制御用
	 * 独自追加分
	 */
	@Override
	public void setRotationAnglesMM(float par1, float par2,
			float pTicksExisted, float pHeadYaw, float pHeadPitch, float par6) {
		bipedHead.setRotateAngleY(pHeadYaw / 57.29578F);
		bipedHead.setRotateAngleX(pHeadPitch / 57.29578F);
		bipedRightArm.setRotateAngleX(mh_cos(par1 * 0.6662F + 3.141593F) * 2.0F * par2 * 0.5F);
		bipedLeftArm.setRotateAngleX(mh_cos(par1 * 0.6662F) * 2.0F * par2 * 0.5F);
		bipedRightArm.setRotateAngleZ(0.0F);
		bipedLeftArm.setRotateAngleZ(0.0F);
		bipedRightLeg.setRotateAngleX(mh_cos(par1 * 0.6662F) * 1.4F * par2);
		bipedLeftLeg.setRotateAngleX(mh_cos(par1 * 0.6662F + 3.141593F) * 1.4F * par2);
		bipedRightLeg.setRotateAngleY(0.0F);
		bipedLeftLeg.setRotateAngleY(0.0F);
		
		if (isRiding) {
			// 乗り物に乗っている
			bipedRightArm.addRotateAngleX(-0.6283185F);
			bipedLeftArm.addRotateAngleX(-0.6283185F);
			bipedRightLeg.setRotateAngleX(-1.256637F);
			bipedLeftLeg.setRotateAngleX(-1.256637F);
			bipedRightLeg.setRotateAngleY(0.3141593F);
			bipedLeftLeg.setRotateAngleY(-0.3141593F);
		}
		
		
		// アイテム持ってるときの腕振りを抑える+表示角オフセット
		if (heldItemLeft != 0) {
			bipedLeftArm.setRotateAngleX(bipedLeftArm.getRotateAngleX() * 0.5F);
			bipedLeftArm.addRotateAngleDegX(-18F * (float)heldItemLeft);
		}
		if (heldItemRight != 0) {
			bipedRightArm.setRotateAngleX(bipedRightArm.getRotateAngleX() * 0.5F);
			bipedRightArm.addRotateAngleDegX(-18F * (float)heldItemRight);
		}
		
		bipedRightArm.setRotateAngleY(0.0F);
		bipedLeftArm.setRotateAngleY(0.0F);
		
//		float[] lgrounds = null;
//		float onGroundR = 0;
//		float onGroundL = 0;
//		lgrounds = (float[])MMM_ModelCapsHelper.getCapsValue(entityCaps, caps_Grounds);
//		if (lgrounds != null) {
//			onGroundR = lgrounds[0];
//			onGroundL = lgrounds[1];
//		}
//		if (lgrounds == null) {
//			onGroundR = onGround;
//		}
		float onGroundR = MMM_ModelCapsHelper.getCapsValueFloat(entityCaps, caps_Ground, 0, onGround);
		float onGroundL = MMM_ModelCapsHelper.getCapsValueFloat(entityCaps, caps_Ground, 1, 0F);
		if ((onGroundR > -9990F || onGroundL > -9990F) && !aimedBow) {
			// 腕振り
			float f6, f7, f8;
			f6 = mh_sin(mh_sqrt_float(onGroundR) * (float)Math.PI * 2.0F);
			f7 = mh_sin(mh_sqrt_float(onGroundL) * (float)Math.PI * 2.0F);
			bipedBody.setRotateAngleY((f6 - f7) * 0.2F);
			Skirt.setRotateAngleY(bipedBody.rotateAngleY);
			bipedRightArm.addRotateAngleY(bipedBody.rotateAngleY);
			bipedLeftArm.addRotateAngleY(bipedBody.rotateAngleY);
			// R
			if (onGroundR > 0F) {
				f6 = 1.0F - onGroundR;
				f6 *= f6;
				f6 *= f6;
				f6 = 1.0F - f6;
				f7 = mh_sin(f6 * (float)Math.PI);
				f8 = mh_sin(onGroundR * (float)Math.PI) * -(bipedHead.rotateAngleX - 0.7F) * 0.75F;
				bipedRightArm.addRotateAngleX(-f7 * 1.2F - f8);
				bipedRightArm.addRotateAngleY(bipedBody.rotateAngleY * 2.0F);
				bipedRightArm.setRotateAngleZ(mh_sin(onGroundR * 3.141593F) * -0.4F);
			} else {
				bipedRightArm.addRotateAngleX(bipedBody.rotateAngleY);
			}
			// L
			if (onGroundL > 0F) {
				f6 = 1.0F - onGroundL;
				f6 *= f6;
				f6 *= f6;
				f6 = 1.0F - f6;
				f7 = mh_sin(f6 * (float)Math.PI);
				f8 = mh_sin(onGroundL * (float)Math.PI) * -(bipedHead.rotateAngleX - 0.7F) * 0.75F;
				bipedLeftArm.rotateAngleX -= (double)f7 * 1.2D + (double)f8;
				bipedLeftArm.rotateAngleY += bipedBody.rotateAngleY * 2.0F;
				bipedLeftArm.setRotateAngleZ(mh_sin(onGroundL * 3.141593F) * 0.4F);
			} else {
				bipedLeftArm.rotateAngleX += bipedBody.rotateAngleY;
			}
		}
		if(isSneak) {
			// しゃがみ
			bipedBody.setRotateAngleX(0.5F);
			bipedRightLeg.rotateAngleX -= 0.0F;
			bipedLeftLeg.rotateAngleX -= 0.0F;
			bipedRightArm.rotateAngleX += 0.2F;
			bipedLeftArm.rotateAngleX += 0.2F;
			bipedRightLeg.rotationPointZ = 3F;
			bipedLeftLeg.rotationPointZ = 3F;
			bipedRightLeg.rotationPointY = 6F;
			bipedLeftLeg.rotationPointY = 6F;
			bipedHead.rotationPointY = 1.0F;
			Skirt.setRotationPointMM(0.0F, 5.8F, 2.7F);
//			Skirt.rotationPointY = 5.8F;
//			Skirt.rotationPointZ = 2.7F;
			Skirt.setRotateAngleX(0.2F);
		} else {
			// 通常立ち
			bipedBody.setRotateAngleX(0.0F);
			bipedRightLeg.rotationPointZ = 0.0F;
			bipedLeftLeg.rotationPointZ = 0.0F;
			bipedRightLeg.rotationPointY = 7F;
			bipedLeftLeg.rotationPointY = 7F;
			bipedHead.rotationPointY = 0.0F;
			Skirt.setRotationPointMM(0.0F, 7.0F, 0.0F);
//			Skirt.rotationPointY = 7.0F;
//			Skirt.rotationPointZ = 0.0F;
			Skirt.setRotateAngleX(0.0F);
		}
		if (isWait) {
			//待機状態の特別表示
			float lx = mh_sin(pTicksExisted * 0.067F) * 0.05F -0.7F;
			bipedRightArm.setRotateAngle(lx, 0.0F, -0.4F);
			bipedLeftArm.setRotateAngle(lx, 0.0F, 0.4F);
		} else {
			float la, lb, lc;
			if (aimedBow) {
				// 弓構え
				float f6 = mh_sin(onGround * 3.141593F);
				float f7 = mh_sin((1.0F - (1.0F - onGround) * (1.0F - onGround)) * 3.141593F);
				la = 0.1F - f6 * 0.6F;
				bipedRightArm.setRotateAngle(-1.470796F, -la, 0.0F);
				bipedLeftArm.setRotateAngle(-1.470796F, la, 0.0F);
				la = bipedHead.rotateAngleX;
				lb = mh_sin(pTicksExisted * 0.067F) * 0.05F;
				lc = f6 * 1.2F - f7 * 0.4F;
				bipedRightArm.addRotateAngleX(la + lb - lc);
				bipedLeftArm.addRotateAngleX(la - lb - lc);
				la = bipedHead.rotateAngleY;
				bipedRightArm.addRotateAngleY(la);
				bipedLeftArm.addRotateAngleY(la);
				la = mh_cos(pTicksExisted * 0.09F) * 0.05F + 0.05F;
				bipedRightArm.addRotateAngleZ(la);
				bipedLeftArm.addRotateAngleZ(-la);
			} else {
				// 通常
				la = mh_sin(pTicksExisted * 0.067F) * 0.05F;
				lc = 0.5F + mh_cos(pTicksExisted * 0.09F) * 0.05F + 0.05F;
				bipedRightArm.addRotateAngleX(la);
				bipedLeftArm.addRotateAngleX(-la);
				bipedRightArm.addRotateAngleZ(lc);
				bipedLeftArm.addRotateAngleZ(-lc);
			}
		}
		
		
		//
		Arms[2].setRotateAngle(-0.78539816339744830961566084581988F - bipedRightArm.getRotateAngleX(), 0F, 0F);
		Arms[3].setRotateAngle(-0.78539816339744830961566084581988F - bipedLeftArm.getRotateAngleX(), 0F, 0F);
	}

	@Override
	public void showAllParts() {
		// 表示制限を解除してすべての部品を表示
		bipedHead.setVisible(true);
		bipedBody.setVisible(true);
		bipedRightArm.setVisible(true);
		bipedLeftArm.setVisible(true);
		Skirt.setVisible(true);
		bipedRightLeg.setVisible(true);
		bipedLeftLeg.setVisible(true);
	}

	@Override
	public int showArmorParts(int parts) {
		// 鎧の表示用
		boolean f;
		// 兜
		f = parts == 3 ? true : false;
		bipedHead.setVisible(f);
		// 鎧
		f = parts == 2 ? true : false;
		bipedBody.setVisible(f);
		bipedRightArm.setVisible(f);
		bipedLeftArm.setVisible(f);
		// 脚甲
		f = parts == 1 ? true : false;
		Skirt.setVisible(f);
		// 臑当
		f = parts == 0 ? true : false;
		bipedRightLeg.setVisible(f);
		bipedLeftLeg.setVisible(f);
		
		return -1;
	}

	@Override
	public void renderItems() {
		// 手持ちの表示
		GL11.glPushMatrix();
		// R
		Arms[0].loadMatrix();
		GL11.glTranslatef(0F, 0.05F, -0.05F);
		Arms[0].renderItems(this, false, 0);
		// L
		Arms[1].loadMatrix();
		GL11.glTranslatef(0F, 0.05F, -0.05F);
		Arms[1].renderItems(this, false, 1);
		// 頭部装飾品
		boolean lplanter = MMM_ModelCapsHelper.getCapsValueBoolean(entityCaps, caps_isPlanter);
		if (MMM_ModelCapsHelper.getCapsValueBoolean(entityCaps, caps_isCamouflage) || lplanter) {
			HeadMount.loadMatrix();
			if (lplanter) {
				HeadTop.renderItemsHead(this);
			} else {
				HeadMount.renderItemsHead(this);
			}
		}
		GL11.glPopMatrix();
	}

}
