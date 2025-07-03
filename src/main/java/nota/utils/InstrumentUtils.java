package nota.utils;

import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import nota.model.Sound;
//import net.minecraft.sound.SoundEvent;

/**
 * Various methods for working with instruments
 */
public class InstrumentUtils {

	/**
	 * Returns the org.bukkit.Sound enum for the current server version
	 *
	 * @param instrument
	 * @return Sound enum (for the current server version)
	 * @see Sound
	 */
	public static SoundEvent getInstrument(byte instrument) {
		return Sound.getFromBukkitName(getInstrumentName(instrument));
	}

	/**
	 * Add suffix to vanilla instrument to use sound outside 2 octave range
	 *
	 * @param instrument instrument id
	 * @param key        sound key
	 * @param pitch
	 * @return warped name
	 */
	public static String warpNameOutOfRange(byte instrument, byte key, short pitch) {
		return warpNameOutOfRange(getSoundNameByInstrument(instrument), key, pitch);
	}

	/**
	 * Add suffix to qualified name to use sound outside 2 octave range
	 *
	 * @param name  qualified name
	 * @param key   sound key
	 * @param pitch
	 * @return warped name
	 */
	public static String warpNameOutOfRange(String name, byte key, short pitch) {
		key = NoteUtils.applyPitchToKey(key, pitch);
		// -15 base_-2
		// 9 base_-1
		// 33 base
		// 57 base_1
		// 81 base_2
		// 105 base_3
		if(key < 9) name += "_-2";
		else if(key < 33) name += "_-1";
		else //noinspection StatementWithEmptyBody
			if(key < 57) ;
			else if(key < 81) name += "_1";
			else if(key < 105) name += "_2";
		return name;
	}

	/**
	 * Returns the name of vanilla instrument
	 *
	 * @param instrument instrument identifier
	 * @return Sound name with full qualified name
	 */
	public static String getSoundNameByInstrument(byte instrument) {
		//noinspection RedundantSuppression
		return switch (instrument) {
			case 0 ->
				//noinspection DuplicateBranchesInSwitch
				"minecraft:block.note_block.harp";
			case 1 -> "minecraft:block.note_block.bass";
			case 2 ->
				//noinspection SpellCheckingInspection
				"minecraft:block.note_block.basedrum";
			case 3 -> "minecraft:block.note_block.snare";
			case 4 -> "minecraft:block.note_block.hat";
			case 5 -> "minecraft:block.note_block.guitar";
			case 6 -> "minecraft:block.note_block.flute";
			case 7 -> "minecraft:block.note_block.bell";
			case 8 -> "minecraft:block.note_block.chime";
			case 9 -> "minecraft:block.note_block.xylophone";
			case 10 -> "minecraft:block.note_block.iron_xylophone";
			case 11 -> "minecraft:block.note_block.cow_bell";
			case 12 -> "minecraft:block.note_block.didgeridoo";
			case 13 -> "minecraft:block.note_block.bit";
			case 14 -> "minecraft:block.note_block.banjo";
			case 15 ->
				//noinspection SpellCheckingInspection
				"minecraft:block.note_block.pling";
			default -> "minecraft:block.note_block.harp";
		};
	}

	/**
	 * Returns the name of the org.bukkit.Sound enum for the current server version
	 *
	 * @param instrument
	 * @return Sound enum name (for the current server version)
	 * @see Sound
	 */
	public static String getInstrumentName(byte instrument) {
		return switch(instrument) {
			case 1 -> "BLOCK_NOTE_BLOCK_BASS";
			case 2 -> "BLOCK_NOTE_BLOCK_BASEDRUM";
			case 3 -> "BLOCK_NOTE_BLOCK_SNARE";
			case 4 -> "BLOCK_NOTE_BLOCK_HAT";
			case 5 -> "BLOCK_NOTE_BLOCK_GUITAR";
			case 6 -> "BLOCK_NOTE_BLOCK_FLUTE";
			case 7 -> "BLOCK_NOTE_BLOCK_BELL";
			case 8 -> "BLOCK_NOTE_BLOCK_CHIME";
			case 9 -> "BLOCK_NOTE_BLOCK_XYLOPHONE";
			case 10 -> "BLOCK_NOTE_BLOCK_IRON_XYLOPHONE";
			case 11 -> "BLOCK_NOTE_BLOCK_COW_BELL";
			case 12 -> "BLOCK_NOTE_BLOCK_DIDGERIDOO";
			case 13 -> "BLOCK_NOTE_BLOCK_BIT";
			case 14 -> "BLOCK_NOTE_BLOCK_BANJO";
			case 15 -> "BLOCK_NOTE_BLOCK_PLING";
			default -> "BLOCK_NOTE_BLOCK_HARP";
		};
	}

	/**
	 * Returns the name of the org.bukkit.NoteBlockInstrument enum for the current server version
	 *
	 * @param instrument
	 * @return NoteBlockInstrument enum (for the current server version)
	 */
	public static NoteBlockInstrument getBukkitInstrument(byte instrument) {
		return switch (instrument) {
			case 1 -> NoteBlockInstrument.BASS;
			case 2 -> NoteBlockInstrument.BASEDRUM;
			case 3 -> NoteBlockInstrument.SNARE;
			case 4 -> NoteBlockInstrument.HAT;
			case 5 -> NoteBlockInstrument.GUITAR;
			case 6 -> NoteBlockInstrument.FLUTE;
			case 7 -> NoteBlockInstrument.BELL;
			case 8 -> NoteBlockInstrument.CHIME;
			case 9 -> NoteBlockInstrument.XYLOPHONE;
			case 10 -> NoteBlockInstrument.IRON_XYLOPHONE;
			case 11 -> NoteBlockInstrument.COW_BELL;
			case 12 -> NoteBlockInstrument.DIDGERIDOO;
			case 13 -> NoteBlockInstrument.BIT;
			case 14 -> NoteBlockInstrument.BANJO;
			case 15 -> NoteBlockInstrument.PLING;
			default -> NoteBlockInstrument.HARP;
		};
	}

	/**
	 * If true, the byte given represents a custom instrument
	 *
	 * @param instrument
	 * @return whether the byte represents a custom instrument
	 */
	public static boolean isCustomInstrument(byte instrument) {
		return instrument >= getCustomInstrumentFirstIndex();
	}

	/**
	 * Gets the first index in which a custom instrument
	 * can be added to the existing list of instruments
	 *
	 * @return index where an instrument can be added
	 */
	public static byte getCustomInstrumentFirstIndex() {
		return 16;
	}

}
