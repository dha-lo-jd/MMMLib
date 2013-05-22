package net.minecraft.client.model;

import net.minecraft.entity.Entity;
import net.minecraft.src.MMM_EquippedStabilizer;

public abstract class MMM_ModelStabilizerBase extends MMM_ModelBase {

	public MMM_ModelStabilizerBase() {
	}

	/**
	 * そのハードポイントに装備可能かどうかを返す。
	 * pName:ハードポイントの識別名称。
	 */
	public boolean checkEquipment(String pName) {
		return true;
	}

	/**
	 * 同じハードポイントに装備できるかどうか。
	 */
	public int getExclusive() {
		return 0;
	}

	/**
	 * パーツの名称。
	 */
	public abstract String getName();

	/**
	 * 使用されるテクスチャを返す。
	 */
	public String getTexture() {
		return "";
	}

	/**
	 * 初期化時に実行される
	 */
	public void init(MMM_EquippedStabilizer pequipped) {
		// 変数などを定義する
	}

	/**
	 * メイドさんのテクスチャをそのまま使わずに、違うテクスチャを使うか？
	 */
	public boolean isLoadAnotherTexture() {
		return false;
	}

	/*
		@Deprecated
		@Override
		public void render(Entity par1Entity, float par2, float par3, float par4, float par5, float par6, float par7) {
			super.render(par1Entity, par2, par3, par4, par5, par6, par7);
		}

		/**
		 * レンダリングは基本こちらを呼ぶこと
		 */
	public void render(MMM_ModelMultiBase pModel, Entity par1Entity, float par2, float par3, float par4, float par5,
			float par6, float par7) {
		render(par1Entity, par2, par3, par4, par5, par6, par7);
	}

}
