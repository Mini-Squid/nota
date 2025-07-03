package nota.player;

import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import nota.Nota;
import nota.model.Layer;
import nota.model.Note;
import nota.model.Playlist;
import nota.model.Song;

/**
 * SongPlayer created at a specified BlockPos
 */
public class PositionSongPlayer extends RangeSongPlayer {
	private BlockPos pos;
	ServerLevel world;

	public PositionSongPlayer(Song song, ServerLevel world) {
		super(song);
		this.world = world;
	}

	public PositionSongPlayer(Playlist playlist, ServerLevel world) {
		super(playlist);
		this.world = world;
	}

	/**
	 * Gets location on which is the PositionSongPlayer playing
	 *
	 * @return {@link BlockPos}
	 */
	public BlockPos getBlockPos() {
		return this.pos;
	}

	/**
	 * Sets location on which is the PositionSongPlayer playing
	 */
	public void setBlockPos(BlockPos pos) {
		this.pos = pos;
	}

	@Override
	public void playTick(ServerPlayer player, int tick) {
		if(!player.level().registryAccess().equals(world.registryAccess())) {
			return; // not in same world
		}

		byte playerVolume = Nota.getPlayerVolume(player);

		for(Layer layer : song.getLayerHashMap().values()) {
			Note note = layer.getNote(tick);
			if(note == null) continue;

			double dist = player.position().distanceTo(this.pos.getCenter());

			float vol = 1 / (float) (((layer.getVolume() * (int) this.volume * (int) playerVolume * note.getVelocity()) / 100_00_00_00F)
								* ((1F / 16F) * dist));

			vol /= 10;

			if(isInRange(player)) {
				this.playerList.put(player.getUUID(), true);
				this.channelMode.play(player, pos, song, layer, note, vol, !enable10Octave);
			}
			else {
				this.playerList.put(player.getUUID(), false);
			}
		}
	}

	/**
	 * Returns true if the Player is able to hear the current PositionSongPlayer
	 *
	 * @param player in range
	 * @return ability to hear the current PositionSongPlayer
	 */
	@Override
	public boolean isInRange(ServerPlayer player) {
		return player.blockPosition().distManhattan(pos) <= getDistance();
	}
}
