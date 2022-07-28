package com.jeff_media.jefflib.data;

import io.papermc.paper.entity.LookAnchor;
import org.bukkit.*;
import org.bukkit.advancement.Advancement;
import org.bukkit.advancement.AdvancementProgress;
import org.bukkit.attribute.Attribute;
import org.bukkit.attribute.AttributeInstance;
import org.bukkit.block.Block;
import org.bukkit.block.BlockFace;
import org.bukkit.block.PistonMoveReaction;
import org.bukkit.block.Sign;
import org.bukkit.block.data.BlockData;
import org.bukkit.conversations.Conversation;
import org.bukkit.conversations.ConversationAbandonedEvent;
import org.bukkit.entity.*;
import org.bukkit.entity.memory.MemoryKey;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.player.PlayerTeleportEvent;
import org.bukkit.inventory.*;
import org.bukkit.map.MapView;
import org.bukkit.metadata.MetadataValue;
import org.bukkit.permissions.Permission;
import org.bukkit.permissions.PermissionAttachment;
import org.bukkit.permissions.PermissionAttachmentInfo;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.plugin.Plugin;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;
import org.bukkit.profile.PlayerProfile;
import org.bukkit.scoreboard.Scoreboard;
import org.bukkit.util.BoundingBox;
import org.bukkit.util.RayTraceResult;
import org.bukkit.util.Vector;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import java.net.InetSocketAddress;
import java.util.*;

// Todo: extend CraftPlayer instead and simply override the message methods
// Maybe also override other setter methods?
@SuppressWarnings("deprecation")
public class ShadowPlayer implements Player {

    private final Player player;

    public ShadowPlayer(@Nonnull final Player player) {
        this.player = player;
    }

    @Nonnull
    @Override
    public String getDisplayName() {
        return player.getDisplayName();
    }

    @Override
    public void setDisplayName(@Nullable final String s) {

    }

    @Nonnull
    @Override
    public String getPlayerListName() {
        return player.getPlayerListName();
    }

    @Override
    public void setPlayerListName(@Nullable final String s) {

    }

    @Nullable
    @Override
    public String getPlayerListHeader() {
        return player.getPlayerListHeader();
    }

    @Nullable
    @Override
    public String getPlayerListFooter() {
        return player.getPlayerListFooter();
    }

    @Override
    public void setPlayerListHeader(@Nullable final String s) {

    }

    @Override
    public void setPlayerListFooter(@Nullable final String s) {

    }

    @Override
    public void setPlayerListHeaderFooter(@Nullable final String s, @Nullable final String s1) {

    }

    @Override
    public void setCompassTarget(@Nonnull final Location location) {

    }

    @Nonnull
    @Override
    public Location getCompassTarget() {
        return player.getCompassTarget();
    }

    @Nullable
    @Override
    public InetSocketAddress getAddress() {
        return player.getAddress();
    }

    @Override
    public boolean isConversing() {
        return player.isConversing();
    }

    @Override
    public void acceptConversationInput(@Nonnull final String s) {

    }

    @Override
    public boolean beginConversation(@Nonnull final Conversation conversation) {
        return false;
    }

    @Override
    public void abandonConversation(@Nonnull final Conversation conversation) {

    }

    @Override
    public void abandonConversation(@Nonnull final Conversation conversation, @Nonnull final ConversationAbandonedEvent conversationAbandonedEvent) {

    }

    @Override
    public void sendRawMessage(@Nonnull final String s) {

    }

    @Override
    public void sendRawMessage(@Nullable final UUID uuid, @Nonnull final String s) {

    }

    @Override
    public void kickPlayer(@Nullable final String s) {

    }

    @Override
    public void chat(@Nonnull final String s) {

    }

    @Override
    public boolean performCommand(@Nonnull final String s) {
        return false;
    }

    @Nonnull
    @Override
    public Location getLocation() {
        return player.getLocation();
    }

    @Nullable
    @Override
    public Location getLocation(@Nullable final Location location) {
        return player.getLocation(location);
    }

    @Override
    public void setVelocity(@Nonnull final Vector vector) {

    }

    @Nonnull
    @Override
    public Vector getVelocity() {
        return player.getVelocity();
    }

    @Override
    public double getHeight() {
        return player.getHeight();
    }

    @Override
    public double getWidth() {
        return player.getWidth();
    }

    @Nonnull
    @Override
    public BoundingBox getBoundingBox() {
        return player.getBoundingBox();
    }

    @Override
    public boolean isOnGround() {
        return player.isOnGround();
    }

    @Override
    public boolean isInWater() {
        return player.isInWater();
    }

    @Nonnull
    @Override
    public World getWorld() {
        return player.getWorld();
    }

    @Override
    public void setRotation(final float v, final float v1) {

    }

    @Override
    public boolean teleport(@Nonnull final Location location) {
        return false;
    }

    @Override
    public boolean teleport(@Nonnull final Location location, @Nonnull final PlayerTeleportEvent.TeleportCause teleportCause) {
        return false;
    }

    @Override
    public boolean teleport(@Nonnull final Entity entity) {
        return false;
    }

    @Override
    public boolean teleport(@Nonnull final Entity entity, @Nonnull final PlayerTeleportEvent.TeleportCause teleportCause) {
        return false;
    }

    @Nonnull
    @Override
    public List<Entity> getNearbyEntities(final double v, final double v1, final double v2) {
        return player.getNearbyEntities(v,v1,v2);
    }

    @Override
    public int getEntityId() {
        return player.getEntityId();
    }

    @Override
    public int getFireTicks() {
        return player.getFireTicks();
    }

    @Override
    public int getMaxFireTicks() {
        return player.getMaxFireTicks();
    }

    @Override
    public void setFireTicks(final int i) {

    }

    @Override
    public void setVisualFire(final boolean b) {

    }

    @Override
    public boolean isVisualFire() {
        return player.isVisualFire();
    }

    @Override
    public int getFreezeTicks() {
        return player.getFreezeTicks();
    }

    @Override
    public int getMaxFreezeTicks() {
        return player.getMaxFreezeTicks();
    }

    @Override
    public void setFreezeTicks(final int i) {

    }

    @Override
    public boolean isFrozen() {
        return player.isFrozen();
    }

    @Override
    public void remove() {

    }

    @Override
    public boolean isDead() {
        return player.isDead();
    }

    @Override
    public boolean isValid() {
        return player.isValid();
    }

    @Override
    public void sendMessage(@Nonnull final String s) {

    }

    @Override
    public void sendMessage(@Nonnull final String[] strings) {

    }

    @Override
    public void sendMessage(@Nullable final UUID uuid, @Nonnull final String s) {

    }

    @Override
    public void sendMessage(@Nullable final UUID uuid, @Nonnull final String[] strings) {

    }

    @Nonnull
    @Override
    public Server getServer() {
        return player.getServer();
    }

    @Override
    public boolean isPersistent() {
        return player.isPersistent();
    }

    @Override
    public void setPersistent(final boolean b) {

    }

    @Nullable
    @Override
    public Entity getPassenger() {
        return player.getPassenger();
    }

    @Override
    public boolean setPassenger(@Nonnull final Entity entity) {
        return false;
    }

    @Nonnull
    @Override
    public List<Entity> getPassengers() {
        return player.getPassengers();
    }

    @Override
    public boolean addPassenger(@Nonnull final Entity entity) {
        return false;
    }

    @Override
    public boolean removePassenger(@Nonnull final Entity entity) {
        return false;
    }

    @Override
    public boolean isEmpty() {
        return player.isEmpty();
    }

    @Override
    public boolean eject() {
        return false;
    }

    @Override
    public float getFallDistance() {
        return player.getFallDistance();
    }

    @Override
    public void setFallDistance(final float v) {

    }

    @Override
    public void setLastDamageCause(@Nullable final EntityDamageEvent entityDamageEvent) {

    }

    @Nullable
    @Override
    public EntityDamageEvent getLastDamageCause() {
        return player.getLastDamageCause();
    }

    @Nonnull
    @Override
    public UUID getUniqueId() {
        return player.getUniqueId();
    }

    @Nonnull
    @Override
    public PlayerProfile getPlayerProfile() {
        return player.getPlayerProfile();
    }

    @Override
    public int getTicksLived() {
        return player.getTicksLived();
    }

    @Override
    public void setTicksLived(final int i) {

    }

    @Override
    public void playEffect(@Nonnull final EntityEffect entityEffect) {

    }

    @Nonnull
    @Override
    public EntityType getType() {
        return player.getType();
    }

    @Override
    public boolean isInsideVehicle() {
        return player.isInsideVehicle();
    }

    @Override
    public boolean leaveVehicle() {
        return false;
    }

    @Nullable
    @Override
    public Entity getVehicle() {
        return player.getVehicle();
    }

    @Override
    public void setCustomNameVisible(final boolean b) {

    }

    @Override
    public boolean isCustomNameVisible() {
        return player.isCustomNameVisible();
    }

    @Override
    public void setGlowing(final boolean b) {

    }

    @Override
    public boolean isGlowing() {
        return player.isGlowing();
    }

    @Override
    public void setInvulnerable(final boolean b) {

    }

    @Override
    public boolean isInvulnerable() {
        return player.isInvulnerable();
    }

    @Override
    public boolean isSilent() {
        return player.isSilent();
    }

    @Override
    public void setSilent(final boolean b) {

    }

    @Override
    public boolean hasGravity() {
        return player.hasGravity();
    }

    @Override
    public void setGravity(final boolean b) {

    }

    @Override
    public int getPortalCooldown() {
        return player.getPortalCooldown();
    }

    @Override
    public void setPortalCooldown(final int i) {

    }

    @Nonnull
    @Override
    public Set<String> getScoreboardTags() {
        return player.getScoreboardTags();
    }

    @Override
    public boolean addScoreboardTag(@Nonnull final String s) {
        return false;
    }

    @Override
    public boolean removeScoreboardTag(@Nonnull final String s) {
        return false;
    }

    @Nonnull
    @Override
    public PistonMoveReaction getPistonMoveReaction() {
        return player.getPistonMoveReaction();
    }

    @Nonnull
    @Override
    public BlockFace getFacing() {
        return player.getFacing();
    }

    @Nonnull
    @Override
    public Pose getPose() {
        return player.getPose();
    }

    @Nonnull
    @Override
    public SpawnCategory getSpawnCategory() {
        return player.getSpawnCategory();
    }

    @Override
    public boolean isSneaking() {
        return player.isSneaking();
    }

    @Override
    public void setSneaking(final boolean b) {

    }

    @Override
    public boolean isSprinting() {
        return player.isSprinting();
    }

    @Override
    public void setSprinting(final boolean b) {

    }

    @Override
    public void saveData() {

    }

    @Override
    public void loadData() {

    }

    @Override
    public void setSleepingIgnored(final boolean b) {

    }

    @Override
    public boolean isSleepingIgnored() {
        return player.isSleepingIgnored();
    }

    @Override
    public boolean isOnline() {
        return player.isOnline();
    }

    @Override
    public boolean isBanned() {
        return player.isBanned();
    }

    @Override
    public boolean isWhitelisted() {
        return player.isWhitelisted();
    }

    @Override
    public void setWhitelisted(final boolean b) {

    }

    @Nullable
    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public long getFirstPlayed() {
        return player.getFirstPlayed();
    }

    @Override
    public long getLastPlayed() {
        return player.getLastPlayed();
    }

    @Override
    public boolean hasPlayedBefore() {
        return player.hasPlayedBefore();
    }

    @Nullable
    @Override
    public Location getBedSpawnLocation() {
        return player.getBedSpawnLocation();
    }

    @Override
    public void incrementStatistic(@Nonnull final Statistic statistic) throws IllegalArgumentException {

    }

    @Override
    public void decrementStatistic(@Nonnull final Statistic statistic) throws IllegalArgumentException {

    }

    @Override
    public void incrementStatistic(@Nonnull final Statistic statistic, final int i) throws IllegalArgumentException {

    }

    @Override
    public void decrementStatistic(@Nonnull final Statistic statistic, final int i) throws IllegalArgumentException {

    }

    @Override
    public void setStatistic(@Nonnull final Statistic statistic, final int i) throws IllegalArgumentException {

    }

    @Override
    public int getStatistic(@Nonnull final Statistic statistic) throws IllegalArgumentException {
        return player.getStatistic(statistic);
    }

    @Override
    public void incrementStatistic(@Nonnull final Statistic statistic, @Nonnull final Material material) throws IllegalArgumentException {

    }

    @Override
    public void decrementStatistic(@Nonnull final Statistic statistic, @Nonnull final Material material) throws IllegalArgumentException {

    }

    @Override
    public int getStatistic(@Nonnull final Statistic statistic, @Nonnull final Material material) throws IllegalArgumentException {
        return player.getStatistic(statistic, material);
    }

    @Override
    public void incrementStatistic(@Nonnull final Statistic statistic, @Nonnull final Material material, final int i) throws IllegalArgumentException {

    }

    @Override
    public void decrementStatistic(@Nonnull final Statistic statistic, @Nonnull final Material material, final int i) throws IllegalArgumentException {

    }

    @Override
    public void setStatistic(@Nonnull final Statistic statistic, @Nonnull final Material material, final int i) throws IllegalArgumentException {

    }

    @Override
    public void incrementStatistic(@Nonnull final Statistic statistic, @Nonnull final EntityType entityType) throws IllegalArgumentException {

    }

    @Override
    public void decrementStatistic(@Nonnull final Statistic statistic, @Nonnull final EntityType entityType) throws IllegalArgumentException {

    }

    @Override
    public int getStatistic(@Nonnull final Statistic statistic, @Nonnull final EntityType entityType) throws IllegalArgumentException {
        return player.getStatistic(statistic, entityType);
    }

    @Override
    public void incrementStatistic(@Nonnull final Statistic statistic, @Nonnull final EntityType entityType, final int i) throws IllegalArgumentException {

    }

    @Override
    public void decrementStatistic(@Nonnull final Statistic statistic, @Nonnull final EntityType entityType, final int i) {

    }

    @Override
    public void setStatistic(@Nonnull final Statistic statistic, @Nonnull final EntityType entityType, final int i) {

    }

    @Override
    public void setBedSpawnLocation(@Nullable final Location location) {

    }

    @Override
    public void setBedSpawnLocation(@Nullable final Location location, final boolean b) {

    }

    @Override
    public void playNote(@Nonnull final Location location, final byte b, final byte b1) {

    }

    @Override
    public void playNote(@Nonnull final Location location, @Nonnull final Instrument instrument, @Nonnull final Note note) {

    }

    @Override
    public void playSound(@Nonnull final Location location, @Nonnull final Sound sound, final float v, final float v1) {

    }

    @Override
    public void playSound(@Nonnull final Location location, @Nonnull final String s, final float v, final float v1) {

    }

    @Override
    public void playSound(@Nonnull final Location location, @Nonnull final Sound sound, @Nonnull final SoundCategory soundCategory, final float v, final float v1) {

    }

    @Override
    public void playSound(@Nonnull final Location location, @Nonnull final String s, @Nonnull final SoundCategory soundCategory, final float v, final float v1) {

    }

    @Override
    public void playSound(@Nonnull final Entity entity, @Nonnull final Sound sound, final float volume, final float pitch) {

    }

    @Override
    public void playSound(@Nonnull final Entity entity, @Nonnull final Sound sound, @Nonnull final SoundCategory category, final float volume, final float pitch) {

    }

    @Override
    public void stopSound(@Nonnull final Sound sound) {

    }

    @Override
    public void stopSound(@Nonnull final String s) {

    }

    @Override
    public void stopSound(@Nonnull final Sound sound, @Nullable final SoundCategory soundCategory) {

    }

    @Override
    public void stopSound(@Nonnull final String s, @Nullable final SoundCategory soundCategory) {

    }

    @Override
    public void stopSound(@NotNull SoundCategory soundCategory) {

    }

    @Override
    public void stopAllSounds() {

    }

    @Override
    public void playEffect(@Nonnull final Location location, @Nonnull final Effect effect, final int i) {

    }

    @Override
    public <T> void playEffect(@Nonnull final Location location, @Nonnull final Effect effect, @Nullable final T t) {

    }

    @Override
    public boolean breakBlock(@Nonnull final Block block) {
        return false;
    }

    @Override
    public void sendBlockChange(@Nonnull final Location location, @Nonnull final Material material, final byte b) {

    }

    @Override
    public void sendBlockChange(@Nonnull final Location location, @Nonnull final BlockData blockData) {

    }

    @Override
    public void sendBlockDamage(@Nonnull final Location location, final float v) {

    }

    @Override
    public void sendEquipmentChange(@Nonnull final LivingEntity livingEntity, @Nonnull final EquipmentSlot equipmentSlot, @Nonnull final ItemStack itemStack) {

    }

    // No Override, was removed in 1.18
    @SuppressWarnings("MethodMayBeStatic")
    public boolean sendChunkChange(@Nonnull final Location location, final int i, final int i1, final int i2, final byte[] bytes) {
        return false;
    }

    @Override
    public void sendSignChange(@Nonnull final Location location, @Nullable final String[] strings) throws IllegalArgumentException {

    }

    @Override
    public void sendSignChange(@Nonnull final Location location, @Nullable final String[] strings, @Nonnull final DyeColor dyeColor) throws IllegalArgumentException {

    }

    @Override
    public void sendSignChange(@Nonnull final Location location, @Nullable final String[] strings, @Nonnull final DyeColor dyeColor, final boolean b) throws IllegalArgumentException {

    }

    @Override
    public void sendMap(@Nonnull final MapView mapView) {

    }

    @Override
    public void updateInventory() {

    }

    @Nullable
    @Override
    public GameMode getPreviousGameMode() {
        return player.getPreviousGameMode();
    }

    @Override
    public void setPlayerTime(final long l, final boolean b) {

    }

    @Override
    public long getPlayerTime() {
        return player.getPlayerTime();
    }

    @Override
    public long getPlayerTimeOffset() {
        return player.getPlayerTimeOffset();
    }

    @Override
    public boolean isPlayerTimeRelative() {
        return player.isPlayerTimeRelative();
    }

    @Override
    public void resetPlayerTime() {

    }

    @Override
    public void setPlayerWeather(@Nonnull final WeatherType weatherType) {

    }

    @Nullable
    @Override
    public WeatherType getPlayerWeather() {
        return player.getPlayerWeather();
    }

    @Override
    public void resetPlayerWeather() {

    }

    @Override
    public void giveExp(final int i) {

    }

    @Override
    public void giveExpLevels(final int i) {

    }

    @Override
    public float getExp() {
        return player.getExp();
    }

    @Override
    public void setExp(final float v) {

    }

    @Override
    public int getLevel() {
        return player.getLevel();
    }

    @Override
    public void setLevel(final int i) {

    }

    @Override
    public int getTotalExperience() {
        return player.getTotalExperience();
    }

    @Override
    public void setTotalExperience(final int i) {

    }

    @Override
    public void sendExperienceChange(final float v) {

    }

    @Override
    public void sendExperienceChange(final float v, final int i) {

    }

    @Override
    public boolean getAllowFlight() {
        return player.getAllowFlight();
    }

    @Override
    public void setAllowFlight(final boolean b) {

    }

    @Override
    public void hidePlayer(@Nonnull final Player player) {

    }

    @Override
    public void hidePlayer(@Nonnull final Plugin plugin, @Nonnull final Player player) {

    }

    @Override
    public void showPlayer(@Nonnull final Player player) {

    }

    @Override
    public void showPlayer(@Nonnull final Plugin plugin, @Nonnull final Player player) {

    }

    @Override
    public boolean canSee(@Nonnull final Player player) {
        return this.player.canSee(player);
    }

    public void hideEntity(@Nonnull final Plugin plugin, @Nonnull final Entity entity) {

    }

    public void showEntity(@Nonnull final Plugin plugin, @Nonnull final Entity entity) {

    }

    public boolean canSee(@Nonnull final Entity entity) {
        return player.canSee(entity);
    }

    @Override
    public boolean isFlying() {
        return player.isFlying();
    }

    @Override
    public void setFlying(final boolean b) {

    }

    @Override
    public void setFlySpeed(final float v) throws IllegalArgumentException {

    }

    @Override
    public void setWalkSpeed(final float v) throws IllegalArgumentException {

    }

    @Override
    public float getFlySpeed() {
        return player.getFlySpeed();
    }

    @Override
    public float getWalkSpeed() {
        return player.getWalkSpeed();
    }

    @Override
    public void setTexturePack(@Nonnull final String s) {

    }

    @Override
    public void setResourcePack(@Nonnull final String s) {

    }

    @Override
    public void setResourcePack(@Nonnull final String s, final byte[] bytes) {

    }

    @Override
    public void setResourcePack(@Nonnull final String url, @Nullable final byte[] hash, @Nullable final String prompt) {

    }

    @Override
    public void setResourcePack(@Nonnull final String url, @Nullable final byte[] hash, final boolean force) {

    }

    @Override
    public void setResourcePack(@Nonnull final String url, @Nullable final byte[] hash, @Nullable final String prompt, final boolean force) {

    }

    @Nonnull
    @Override
    public Scoreboard getScoreboard() {
        return player.getScoreboard();
    }

    @Override
    public void setScoreboard(@Nonnull final Scoreboard scoreboard) throws IllegalArgumentException, IllegalStateException {

    }

    @Nullable
    @Override
    public WorldBorder getWorldBorder() {
        return player.getWorldBorder();
    }

    @Override
    public void setWorldBorder(@Nullable final WorldBorder worldBorder) {

    }

    @Override
    public boolean isHealthScaled() {
        return player.isHealthScaled();
    }

    @Override
    public void setHealthScaled(final boolean b) {

    }

    @Override
    public void setHealthScale(final double v) throws IllegalArgumentException {

    }

    @Override
    public double getHealthScale() {
        return player.getHealthScale();
    }

    @Nullable
    @Override
    public Entity getSpectatorTarget() {
        return player.getSpectatorTarget();
    }

    @Override
    public void setSpectatorTarget(@Nullable final Entity entity) {

    }

    @Override
    public void sendTitle(@Nullable final String s, @Nullable final String s1) {

    }

    @Override
    public void sendTitle(@Nullable final String s, @Nullable final String s1, final int i, final int i1, final int i2) {

    }

    @Override
    public void resetTitle() {

    }

    @Override
    public void spawnParticle(@Nonnull final Particle particle, @Nonnull final Location location, final int i) {

    }

    @Override
    public void spawnParticle(@Nonnull final Particle particle, final double v, final double v1, final double v2, final int i) {

    }

    @Override
    public <T> void spawnParticle(@Nonnull final Particle particle, @Nonnull final Location location, final int i, @Nullable final T t) {

    }

    @Override
    public <T> void spawnParticle(@Nonnull final Particle particle, final double v, final double v1, final double v2, final int i, @Nullable final T t) {

    }

    @Override
    public void spawnParticle(@Nonnull final Particle particle, @Nonnull final Location location, final int i, final double v, final double v1, final double v2) {

    }

    @Override
    public void spawnParticle(@Nonnull final Particle particle, final double v, final double v1, final double v2, final int i, final double v3, final double v4, final double v5) {

    }

    @Override
    public <T> void spawnParticle(@Nonnull final Particle particle, @Nonnull final Location location, final int i, final double v, final double v1, final double v2, @Nullable final T t) {

    }

    @Override
    public <T> void spawnParticle(@Nonnull final Particle particle, final double v, final double v1, final double v2, final int i, final double v3, final double v4, final double v5, @Nullable final T t) {

    }

    @Override
    public void spawnParticle(@Nonnull final Particle particle, @Nonnull final Location location, final int i, final double v, final double v1, final double v2, final double v3) {

    }

    @Override
    public void spawnParticle(@Nonnull final Particle particle, final double v, final double v1, final double v2, final int i, final double v3, final double v4, final double v5, final double v6) {

    }

    @Override
    public <T> void spawnParticle(@Nonnull final Particle particle, @Nonnull final Location location, final int i, final double v, final double v1, final double v2, final double v3, @Nullable final T t) {

    }

    @Override
    public <T> void spawnParticle(@Nonnull final Particle particle, final double v, final double v1, final double v2, final int i, final double v3, final double v4, final double v5, final double v6, @Nullable final T t) {

    }

    @Nonnull
    @Override
    public AdvancementProgress getAdvancementProgress(@Nonnull final Advancement advancement) {
        return player.getAdvancementProgress(advancement);
    }

    @Override
    public int getClientViewDistance() {
        return player.getClientViewDistance();
    }

    @Override
    public int getPing() {
        return player.getPing();
    }

    @Nonnull
    @Override
    public String getLocale() {
        return player.getLocale();
    }

    @Override
    public void updateCommands() {

    }

    @Override
    public void openBook(@Nonnull final ItemStack itemStack) {

    }

    public void openSign(@Nonnull final Sign sign) {

    }

    @Override
    public void showDemoScreen() {

    }

    @Override
    public boolean isAllowingServerListings() {
        return false;
    }

    @Nonnull
    @Override
    public Spigot spigot() {
        return player.spigot();
    }

    @Nonnull
    @Override
    public Map<String, Object> serialize() {
        return player.serialize();
    }

    @Nonnull
    @Override
    public String getName() {
        return player.getName();
    }

    @Nonnull
    @Override
    public PlayerInventory getInventory() {
        return player.getInventory();
    }

    @Nonnull
    @Override
    public Inventory getEnderChest() {
        return player.getEnderChest();
    }

    @Nonnull
    @Override
    public MainHand getMainHand() {
        return player.getMainHand();
    }

    @Override
    public boolean setWindowProperty(@Nonnull final InventoryView.Property property, final int i) {
        return false;
    }

    @Nonnull
    @Override
    public InventoryView getOpenInventory() {
        return player.getOpenInventory();
    }

    @Nullable
    @Override
    public InventoryView openInventory(@Nonnull final Inventory inventory) {
        return null;
    }

    @Nullable
    @Override
    public InventoryView openWorkbench(@Nullable final Location location, final boolean b) {
        return null;
    }

    @Nullable
    @Override
    public InventoryView openEnchanting(@Nullable final Location location, final boolean b) {
        return null;
    }

    @Override
    public void openInventory(@Nonnull final InventoryView inventoryView) {

    }

    @Nullable
    @Override
    public InventoryView openMerchant(@Nonnull final Villager villager, final boolean b) {
        return null;
    }

    @Nullable
    @Override
    public InventoryView openMerchant(@Nonnull final Merchant merchant, final boolean b) {
        return null;
    }

    @Override
    public void closeInventory() {

    }

    @Nonnull
    @Override
    public ItemStack getItemInHand() {
        return player.getItemInHand();
    }

    @Override
    public void setItemInHand(@Nullable final ItemStack itemStack) {

    }

    @Nonnull
    @Override
    public ItemStack getItemOnCursor() {
        return player.getItemOnCursor();
    }

    @Override
    public void setItemOnCursor(@Nullable final ItemStack itemStack) {

    }

    @Override
    public boolean hasCooldown(@Nonnull final Material material) {
        return player.hasCooldown(material);
    }

    @Override
    public int getCooldown(@Nonnull final Material material) {
        return player.getCooldown(material);
    }

    @Override
    public void setCooldown(@Nonnull final Material material, final int i) {

    }

    @Override
    public int getSleepTicks() {
        return player.getSleepTicks();
    }

    @Override
    public boolean sleep(@Nonnull final Location location, final boolean b) {
        return false;
    }

    @Override
    public void wakeup(final boolean b) {

    }

    @Nonnull
    @Override
    public Location getBedLocation() {
        return player.getBedLocation();
    }

    @Nonnull
    @Override
    public GameMode getGameMode() {
        return player.getGameMode();
    }

    @Override
    public void setGameMode(@Nonnull final GameMode gameMode) {

    }

    @Override
    public boolean isBlocking() {
        return player.isBlocking();
    }

    @Override
    public boolean isHandRaised() {
        return player.isHandRaised();
    }

    @Nullable
    @Override
    public ItemStack getItemInUse() {
        return player.getItemInUse();
    }

    @Override
    public int getExpToLevel() {
        return player.getExpToLevel();
    }

    @Override
    public float getAttackCooldown() {
        return player.getAttackCooldown();
    }

    @Override
    public boolean discoverRecipe(@Nonnull final NamespacedKey namespacedKey) {
        return false;
    }

    @Override
    public int discoverRecipes(@Nonnull final Collection<NamespacedKey> collection) {
        return 0;
    }

    @Override
    public boolean undiscoverRecipe(@Nonnull final NamespacedKey namespacedKey) {
        return false;
    }

    @Override
    public int undiscoverRecipes(@Nonnull final Collection<NamespacedKey> collection) {
        return 0;
    }

    @Override
    public boolean hasDiscoveredRecipe(@Nonnull final NamespacedKey namespacedKey) {
        return player.hasDiscoveredRecipe(namespacedKey);
    }

    @Nonnull
    @Override
    public Set<NamespacedKey> getDiscoveredRecipes() {
        return player.getDiscoveredRecipes();
    }

    @Nullable
    @Override
    public Entity getShoulderEntityLeft() {
        return player.getShoulderEntityLeft();
    }

    @Override
    public void setShoulderEntityLeft(@Nullable final Entity entity) {

    }

    @Nullable
    @Override
    public Entity getShoulderEntityRight() {
        return player.getShoulderEntityRight();
    }

    @Override
    public void setShoulderEntityRight(@Nullable final Entity entity) {

    }

    @Override
    public boolean dropItem(final boolean b) {
        return false;
    }

    @Override
    public float getExhaustion() {
        return player.getExhaustion();
    }

    @Override
    public void setExhaustion(final float v) {

    }

    @Override
    public float getSaturation() {
        return player.getSaturation();
    }

    @Override
    public void setSaturation(final float v) {

    }

    @Override
    public int getFoodLevel() {
        return player.getFoodLevel();
    }

    @Override
    public void setFoodLevel(final int i) {

    }

    @Override
    public int getSaturatedRegenRate() {
        return player.getSaturatedRegenRate();
    }

    @Override
    public void setSaturatedRegenRate(final int i) {

    }

    @Override
    public int getUnsaturatedRegenRate() {
        return player.getUnsaturatedRegenRate();
    }

    @Override
    public void setUnsaturatedRegenRate(final int i) {

    }

    @Override
    public int getStarvationRate() {
        return player.getStarvationRate();
    }

    @Override
    public void setStarvationRate(final int i) {

    }

    @Nullable
    @Override
    public Location getLastDeathLocation() {
        return player.getLastDeathLocation();
    }

    @Override
    public void setLastDeathLocation(@Nullable final Location location) {

    }

    @Override
    public double getEyeHeight() {
        return player.getEyeHeight();
    }

    @Override
    public double getEyeHeight(final boolean b) {
        return player.getEyeHeight(b);
    }

    @Nonnull
    @Override
    public Location getEyeLocation() {
        return player.getEyeLocation();
    }

    @Nonnull
    @Override
    public List<Block> getLineOfSight(@Nullable final Set<Material> set, final int i) {
        return player.getLineOfSight(set, i);
    }

    @Nonnull
    @Override
    public Block getTargetBlock(@Nullable final Set<Material> set, final int i) {
        return player.getTargetBlock(set, i);
    }

    @Nonnull
    @Override
    public List<Block> getLastTwoTargetBlocks(@Nullable final Set<Material> set, final int i) {
        return player.getLastTwoTargetBlocks(set, i);
    }

    @Nullable
    @Override
    public Block getTargetBlockExact(final int i) {
        return player.getTargetBlockExact(i);
    }

    @Nullable
    @Override
    public Block getTargetBlockExact(final int i, @Nonnull final FluidCollisionMode fluidCollisionMode) {
        return player.getTargetBlockExact(i, fluidCollisionMode);
    }

    @Nullable
    @Override
    public RayTraceResult rayTraceBlocks(final double v) {
        return player.rayTraceBlocks(v);
    }

    @Nullable
    @Override
    public RayTraceResult rayTraceBlocks(final double v, @Nonnull final FluidCollisionMode fluidCollisionMode) {
        return player.rayTraceBlocks(v, fluidCollisionMode);
    }

    @Override
    public int getRemainingAir() {
        return player.getRemainingAir();
    }

    @Override
    public void setRemainingAir(final int i) {

    }

    @Override
    public int getMaximumAir() {
        return player.getMaximumAir();
    }

    @Override
    public void setMaximumAir(final int i) {

    }

    @Override
    public int getArrowCooldown() {
        return player.getArrowCooldown();
    }

    @Override
    public void setArrowCooldown(final int i) {

    }

    @Override
    public int getArrowsInBody() {
        return player.getArrowsInBody();
    }

    @Override
    public void setArrowsInBody(final int i) {

    }

    @Override
    public int getMaximumNoDamageTicks() {
        return player.getMaximumNoDamageTicks();
    }

    @Override
    public void setMaximumNoDamageTicks(final int i) {

    }

    @Override
    public double getLastDamage() {
        return player.getLastDamage();
    }

    @Override
    public void setLastDamage(final double v) {

    }

    @Override
    public int getNoDamageTicks() {
        return player.getNoDamageTicks();
    }

    @Override
    public void setNoDamageTicks(final int i) {

    }

    @Nullable
    @Override
    public Player getKiller() {
        return player.getKiller();
    }

    @Override
    public boolean addPotionEffect(@Nonnull final PotionEffect potionEffect) {
        return false;
    }

    @Override
    public boolean addPotionEffect(@Nonnull final PotionEffect potionEffect, final boolean b) {
        return false;
    }

    @Override
    public boolean addPotionEffects(@Nonnull final Collection<PotionEffect> collection) {
        return false;
    }

    @Override
    public boolean hasPotionEffect(@Nonnull final PotionEffectType potionEffectType) {
        return player.hasPotionEffect(potionEffectType);
    }

    @Nullable
    @Override
    public PotionEffect getPotionEffect(@Nonnull final PotionEffectType potionEffectType) {
        return player.getPotionEffect(potionEffectType);
    }

    @Override
    public void removePotionEffect(@Nonnull final PotionEffectType potionEffectType) {

    }

    @Nonnull
    @Override
    public Collection<PotionEffect> getActivePotionEffects() {
        return player.getActivePotionEffects();
    }

    @Override
    public boolean hasLineOfSight(@Nonnull final Entity entity) {
        return player.hasLineOfSight(entity);
    }

    @Override
    public boolean getRemoveWhenFarAway() {
        return player.getRemoveWhenFarAway();
    }

    @Override
    public void setRemoveWhenFarAway(final boolean b) {

    }

    @Nullable
    @Override
    public EntityEquipment getEquipment() {
        return player.getEquipment();
    }

    @Override
    public void setCanPickupItems(final boolean b) {

    }

    @Override
    public boolean getCanPickupItems() {
        return player.getCanPickupItems();
    }

    @Override
    public boolean isLeashed() {
        return player.isLeashed();
    }

    @Nonnull
    @Override
    public Entity getLeashHolder() throws IllegalStateException {
        return player.getLeashHolder();
    }

    @Override
    public boolean setLeashHolder(@Nullable final Entity entity) {
        return false;
    }

    @Override
    public boolean isGliding() {
        return player.isGliding();
    }

    @Override
    public void setGliding(final boolean b) {

    }

    @Override
    public boolean isSwimming() {
        return player.isSwimming();
    }

    @Override
    public void setSwimming(final boolean b) {

    }

    @Override
    public boolean isRiptiding() {
        return player.isRiptiding();
    }

    @Override
    public boolean isSleeping() {
        return player.isSleeping();
    }

    @Override
    public boolean isClimbing() {
        return player.isClimbing();
    }

    @Override
    public void setAI(final boolean b) {

    }

    @Override
    public boolean hasAI() {
        return player.hasAI();
    }

    @Override
    public void attack(@Nonnull final Entity entity) {

    }

    @Override
    public void swingMainHand() {

    }

    @Override
    public void swingOffHand() {

    }

    @Override
    public void setCollidable(final boolean b) {

    }

    @Override
    public boolean isCollidable() {
        return player.isCollidable();
    }

    @Nonnull
    @Override
    public Set<UUID> getCollidableExemptions() {
        return player.getCollidableExemptions();
    }

    @Nullable
    @Override
    public <T> T getMemory(@Nonnull final MemoryKey<T> memoryKey) {
        return player.getMemory(memoryKey);
    }

    @Override
    public <T> void setMemory(@Nonnull final MemoryKey<T> memoryKey, @Nullable final T t) {

    }

    @Nonnull
    @Override
    public EntityCategory getCategory() {
        return player.getCategory();
    }

    @Override
    public void setInvisible(final boolean b) {

    }

    @Override
    public boolean isInvisible() {
        return player.isInvisible();
    }

    @Nullable
    @Override
    public AttributeInstance getAttribute(@Nonnull final Attribute attribute) {
        return player.getAttribute(attribute);
    }

    @Override
    public void damage(final double v) {

    }

    @Override
    public void damage(final double v, @Nullable final Entity entity) {

    }

    @Override
    public double getHealth() {
        return player.getHealth();
    }

    @Override
    public void setHealth(final double v) {

    }

    @Override
    public double getAbsorptionAmount() {
        return player.getAbsorptionAmount();
    }

    @Override
    public void setAbsorptionAmount(final double v) {

    }

    @Override
    public double getMaxHealth() {
        return player.getMaxHealth();
    }

    @Override
    public void setMaxHealth(final double v) {

    }

    @Override
    public void resetMaxHealth() {

    }

    @Nullable
    @Override
    public String getCustomName() {
        return player.getCustomName();
    }

    @Override
    public void setCustomName(@Nullable final String s) {

    }

    @Override
    public void setMetadata(@Nonnull final String s, @Nonnull final MetadataValue metadataValue) {

    }

    @Nonnull
    @Override
    public List<MetadataValue> getMetadata(@Nonnull final String s) {
        return player.getMetadata(s);
    }

    @Override
    public boolean hasMetadata(@Nonnull final String s) {
        return player.hasMetadata(s);
    }

    @Override
    public void removeMetadata(@Nonnull final String s, @Nonnull final Plugin plugin) {

    }

    @Override
    public boolean isPermissionSet(@Nonnull final String s) {
        return player.isPermissionSet(s);
    }

    @Override
    public boolean isPermissionSet(@Nonnull final Permission permission) {
        return player.isPermissionSet(permission);
    }

    @Override
    public boolean hasPermission(@Nonnull final String s) {
        return player.hasPermission(s);
    }

    @Override
    public boolean hasPermission(@Nonnull final Permission permission) {
        return player.hasPermission(permission);
    }

    @Nonnull
    @Override
    public PermissionAttachment addAttachment(@Nonnull final Plugin plugin, @Nonnull final String s, final boolean b) {
        return player.addAttachment(plugin, s, b);
    }

    @Nonnull
    @Override
    public PermissionAttachment addAttachment(@Nonnull final Plugin plugin) {
        return player.addAttachment(plugin);
    }

    @Nullable
    @Override
    public PermissionAttachment addAttachment(@Nonnull final Plugin plugin, @Nonnull final String s, final boolean b, final int i) {
        return null;
    }

    @Nullable
    @Override
    public PermissionAttachment addAttachment(@Nonnull final Plugin plugin, final int i) {
        return null;
    }

    @Override
    public void removeAttachment(@Nonnull final PermissionAttachment permissionAttachment) {

    }

    @Override
    public void recalculatePermissions() {

    }

    @Nonnull
    @Override
    public Set<PermissionAttachmentInfo> getEffectivePermissions() {
        return player.getEffectivePermissions();
    }

    @Override
    public boolean isOp() {
        return player.isOp();
    }

    @Override
    public void setOp(final boolean b) {

    }

    @Nonnull
    @Override
    public PersistentDataContainer getPersistentDataContainer() {
        return player.getPersistentDataContainer();
    }

    @Override
    public void sendPluginMessage(@Nonnull final Plugin plugin, @Nonnull final String s, final byte[] bytes) {

    }

    @Nonnull
    @Override
    public Set<String> getListeningPluginChannels() {
        return player.getListeningPluginChannels();
    }

    @Nonnull
    @Override
    public <T extends Projectile> T launchProjectile(@Nonnull final Class<? extends T> aClass) {
        return player.launchProjectile(aClass);
    }

    @Nonnull
    @Override
    public <T extends Projectile> T launchProjectile(@Nonnull final Class<? extends T> aClass, @Nullable final Vector vector) {
        return player.launchProjectile(aClass, vector);
    }
}
