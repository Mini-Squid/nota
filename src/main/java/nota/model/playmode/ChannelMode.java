package nota.model.playmode;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerPlayer;
import nota.model.Layer;
import nota.model.Note;
import nota.model.Song;

/**
 * Decides how is {@link Note} played to {@link PlayerEntity}
 */
public abstract class ChannelMode {

	public abstract void play(ServerPlayer player, BlockPos pos, Song song, Layer layer, Note note, float volume, boolean doTranspose);
}
