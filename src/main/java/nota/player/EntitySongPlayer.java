package nota.player;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import nota.Nota;
import nota.model.Layer;
import nota.model.Note;
import nota.model.Playlist;
import nota.model.Song;

@SuppressWarnings("unused")
public class EntitySongPlayer extends RangeSongPlayer {

	private Entity entity;

	public EntitySongPlayer(Song song) {
		super(song);
	}

	public EntitySongPlayer(Playlist playlist) {
		super(playlist);
	}

	/**
	 * Returns true if the Player is able to hear the current {@link EntitySongPlayer}
	 *
	 * @param player in range
	 * @return ability to hear the current {@link EntitySongPlayer}
	 */
	@Override
	public boolean isInRange(ServerPlayer player) {
		return player.blockPosition().distManhattan(entity.blockPosition()) <= getDistance();
	}

	/**
	 * Set entity associated with this {@link EntitySongPlayer}
	 *
	 * @param entity entity
	 */
	public void setEntity(Entity entity) {
		this.entity = entity;
	}

	/**
	 * Get {@link Entity} associated with this {@link EntitySongPlayer}
	 *
	 * @return entity
	 */
	public Entity getEntity() {
		return entity;
	}

	@Override
	public void playTick(ServerPlayer player, int tick) {
		if(!entity.isAlive()) {
			if(autoDestroy) {
				destroy();
			}
			else {
				setPlaying(false);
			}
		}
		if (!player.level().registryAccess().equals(entity.level().registryAccess())) {
			return; // not in same world
		}

		byte playerVolume = Nota.getPlayerVolume(player);

		for(Layer layer : song.getLayerHashMap().values()) {
			Note note = layer.getNote(tick);
			if(note == null) continue;

			float volume = ((layer.getVolume() * (int) this.volume * (int) playerVolume * note.getVelocity()) / 100_00_00_00F)
					* ((1F / 16F) * getDistance());

			if(isInRange(player)) {
				playerList.put(player.getUUID(), true);
				channelMode.play(player, entity.blockPosition(), song, layer, note, volume, !enable10Octave);
			}
			else {
				playerList.put(player.getUUID(), false);
			}
		}
	}
}
