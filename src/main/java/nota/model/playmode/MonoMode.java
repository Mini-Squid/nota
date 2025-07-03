package nota.model.playmode;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundSource;
import nota.model.Layer;
import nota.model.Note;
import nota.model.Song;
import nota.utils.InstrumentUtils;
import nota.utils.NoteUtils;

/**
 * {@link Note} is played inside of {@link ServerPlayer}'s head.
 */
public class MonoMode extends ChannelMode {

	@Override
	public void play(ServerPlayer player, BlockPos pos, Song song, Layer layer, Note note, float volume, boolean doTranspose) {
		float pitch;
		if(doTranspose) {
			pitch = NoteUtils.getPitchTransposed(note);
		} else {
			pitch = NoteUtils.getPitchInOctave(note);
		}
		player.playNotifySound(InstrumentUtils.getInstrument(note.getInstrument()), SoundSource.RECORDS, volume, pitch);
	}
}
