package net.minecraft.src;

import java.util.Map;
import java.util.Map.Entry;
import java.util.TreeMap;

import net.minecraft.client.model.MMM_ModelStabilizerBase;
import net.minecraft.client.model.ModelBase;

/**
 * 追加パーツたるスタビライザーを管理する
 */
public class MMM_StabilizerManager extends MMM_ManagerBase<MMM_ModelStabilizerBase> {

	public static final String preFix = "ModelStabilizer";
	public static Map<String, MMM_ModelStabilizerBase> stabilizerList = new TreeMap<String, MMM_ModelStabilizerBase>();

	/**
	 * 指定された名称のスタビライザーモデルを返す。
	 */
	public static MMM_EquippedStabilizer getStabilizer(String pname, String pequippoint) {
		if (!stabilizerList.containsKey(pname)) {
			return null;
		}

		MMM_EquippedStabilizer lequip = new MMM_EquippedStabilizer();
		lequip.stabilizer = stabilizerList.get(pname);
		lequip.stabilizer.init(lequip);
		lequip.equipPointName = pequippoint;

		return lequip;
	}

	public static void init() {
		// 特定名称をプリフィックスに持つmodファイをを獲得
		MMM_FileManager.getModFile("Stabilizer", preFix);
	}

	public static void loadStabilizer() {
		(new MMM_StabilizerManager()).load(MMM_ModelStabilizerBase.class);
	}

	/**
	 * 実装場所のアップデート
	 */
	public static void updateEquippedPoint(Map<String, MMM_EquippedStabilizer> pMap, ModelBase pModel) {
		for (Entry<String, MMM_EquippedStabilizer> le : pMap.entrySet()) {
			le.getValue().updateEquippedPoint(pModel);
		}
	}

	@Override
	public boolean append(Class<? extends MMM_ModelStabilizerBase> pclass) {
		if (!(MMM_ModelStabilizerBase.class).isAssignableFrom(pclass)) {
			return false;
		}

		try {
			MMM_ModelStabilizerBase lms = pclass.newInstance();
			stabilizerList.put(lms.getName(), lms);
			return true;
		} catch (Exception e) {
		}

		return false;
	}

	@Override
	public String getPreFix() {
		return preFix;
	}

}
