package net.minecraft.src;

public class LMM_Statics {

	/*
	 * ����p�萔�A8bit�ڂ𗧂Ă��Entity�v��
	 */
	
	/*
	 * LMMPacet�̃t�H�[�}�b�g
	 * (Byte)
	 * 0	: ����(1byte)
	 * 1 - 4: EntityID(4Byte)�ꍇ�Ɋ���Ă͏ȗ� 
	 * 5 - 	: Data
	 * 
	 */
	/**
	 * �T�[�o�[���֑Ώۂ̃C���x���g���𑗐M����悤�Ɏw������B
	 * �X�|�[�����_�ł̓C���x���g����񂪖������߁B
	 * [0]		: 0x00;
	 * [1..4]	: EntityID(int);
	 */
	public static final byte LMN_Server_UpdateSlots		= (byte)0x80;
	/**
	 * �N���C�A���g���֘r�U����w������B
	 * �U�������̍Đ��������w�肷��B
	 * [0]		: 0x81;
	 * [1..4]	: EntityID(int);
	 * [5]		: ArmIndex(byte);
	 * [6..9]	: SoundIndex(int);
	 */
	public static final byte LMN_Client_SwingArm		= (byte)0x81;
	/**
	 * �T�[�o�[���֐����̎g�p��ʒm����B
	 * GUISelect�p�B
	 * [0]		: 0x02;
	 * [1]		: color(byte);
	 */
	public static final byte LMN_Server_DecDyePowder	= (byte)0x02;
	/**
	 * �T�[�o�[��IFF�̐ݒ�l���ύX���ꂽ���Ƃ�ʒm����B
	 * [0]		: 0x04;
	 * [1]		: IFFValue(byte);
	 * [2..5]	: Index(int);
	 * [6..]	: TargetName(str);
	 */
	public static final byte LMN_Server_SetIFFValue		= (byte)0x04;
	/**
	 * �N���C�A���g��IFF�̐ݒ�l��ʒm����B
	 * [0]		: 0x04;
	 * [1]		: IFFValue(byte);
	 * [2..5]	: Index(int);
	 */
	public static final byte LMN_Client_SetIFFValue		= (byte)0x04;
	/**
	 * �T�[�o�[�֌��݂�IFF�̐ݒ�l��v������B
	 * �v�����͈�ӂȎ��ʔԍ���t�^���邱�ƁB
	 * [0]		: 0x05;
	 * [1..4]	: Index(int);
	 * [5..]	: TargetName(str);
	 */
	public static final byte LMN_Server_GetIFFValue		= (byte)0x05;
	/**
	 * �T�[�o�[��IFF�̐ݒ�l��ۑ�����悤�Ɏw������B
	 * [0]		: 0x06;
	 */
	public static final byte LMN_Server_SaveIFF			= (byte)0x06;
	/**
	 * �N���C�A���g���։����𔭐�������悤�Ɏw������B
	 * �����̎��̂̓N���C�A���g���̓o�^�������g�p���邽�ߕW���̍Đ��菇���Ɖ����łȂ����߁B
	 * [0]		: 0x07;
	 * [1..4]	: EntityID(int);
	 * [5..8]	: SoundIndex(int);
	 */
	public static final byte LMN_Client_PlaySound		= (byte)0x89;


}