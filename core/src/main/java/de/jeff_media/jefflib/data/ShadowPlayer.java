package de.jeff_media.jefflib.data;

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
import org.jetbrains.annotations.Nullable;

import java.net.InetSocketAddress;
import java.util.*;

// Todo: extend CraftPlayer instead and simply override the message methods
// Maybe also override other setter methods?
@SuppressWarnings("deprecation")
public class ShadowPlayer implements Player {

    private final Player player;

    public ShadowPlayer(@NotNull final Player player) {
        this.player = player;
    }

    @NotNull
    @Override
    public String getDisplayName() {
        return player.getDisplayName();
    }

    @Override
    public void setDisplayName(@Nullable final String s) {

    }

    @NotNull
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
    public void setCompassTarget(@NotNull final Location location) {

    }

    @NotNull
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
    public void acceptConversationInput(@NotNull final String s) {

    }

    @Override
    public boolean beginConversation(@NotNull final Conversation conversation) {
        return false;
    }

    @Override
    public void abandonConversation(@NotNull final Conversation conversation) {

    }

    @Override
    public void abandonConversation(@NotNull final Conversation conversation, @NotNull final ConversationAbandonedEvent conversationAbandonedEvent) {

    }

    @Override
    public void sendRawMessage(@NotNull final String s) {

    }

    @Override
    public void sendRawMessage(@Nullable final UUID uuid, @NotNull final String s) {

    }

    @Override
    public void kickPlayer(@Nullable final String s) {

    }

    @Override
    public void chat(@NotNull final String s) {

    }

    @Override
    public boolean performCommand(@NotNull final String s) {
        return false;
    }

    @NotNull
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
    public void setVelocity(@NotNull final Vector vector) {

    }

    @NotNull
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

    @NotNull
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

    @NotNull
    @Override
    public World getWorld() {
        return player.getWorld();
    }

    @Override
    public void setRotation(final float v, final float v1) {

    }

    @Override
    public boolean teleport(@NotNull final Location location) {
        return false;
    }

    @Override
    public boolean teleport(@NotNull final Location location, @NotNull final PlayerTeleportEvent.TeleportCause teleportCause) {
        return false;
    }

    @Override
    public boolean teleport(@NotNull final Entity entity) {
        return false;
    }

    @Override
    public boolean teleport(@NotNull final Entity entity, @NotNull final PlayerTeleportEvent.TeleportCause teleportCause) {
        return false;
    }

    @NotNull
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
    public void sendMessage(@NotNull final String s) {

    }

    @Override
    public void sendMessage(@NotNull final String[] strings) {

    }

    @Override
    public void sendMessage(@Nullable final UUID uuid, @NotNull final String s) {

    }

    @Override
    public void sendMessage(@Nullable final UUID uuid, @NotNull final String[] strings) {

    }

    @NotNull
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
    public boolean setPassenger(@NotNull final Entity entity) {
        return false;
    }

    @NotNull
    @Override
    public List<Entity> getPassengers() {
        return player.getPassengers();
    }

    @Override
    public boolean addPassenger(@NotNull final Entity entity) {
        return false;
    }

    @Override
    public boolean removePassenger(@NotNull final Entity entity) {
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

    @NotNull
    @Override
    public UUID getUniqueId() {
        return player.getUniqueId();
    }

    @NotNull
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
    public void playEffect(@NotNull final EntityEffect entityEffect) {

    }

    @NotNull
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

    @NotNull
    @Override
    public Set<String> getScoreboardTags() {
        return player.getScoreboardTags();
    }

    @Override
    public boolean addScoreboardTag(@NotNull final String s) {
        return false;
    }

    @Override
    public boolean removeScoreboardTag(@NotNull final String s) {
        return false;
    }

    @NotNull
    @Override
    public PistonMoveReaction getPistonMoveReaction() {
        return player.getPistonMoveReaction();
    }

    @NotNull
    @Override
    public BlockFace getFacing() {
        return player.getFacing();
    }

    @NotNull
    @Override
    public Pose getPose() {
        return player.getPose();
    }

    @NotNull
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
    public void incrementStatistic(@NotNull final Statistic statistic) throws IllegalArgumentException {

    }

    @Override
    public void decrementStatistic(@NotNull final Statistic statistic) throws IllegalArgumentException {

    }

    @Override
    public void incrementStatistic(@NotNull final Statistic statistic, final int i) throws IllegalArgumentException {

    }

    @Override
    public void decrementStatistic(@NotNull final Statistic statistic, final int i) throws IllegalArgumentException {

    }

    @Override
    public void setStatistic(@NotNull final Statistic statistic, final int i) throws IllegalArgumentException {

    }

    @Override
    public int getStatistic(@NotNull final Statistic statistic) throws IllegalArgumentException {
        return player.getStatistic(statistic);
    }

    @Override
    public void incrementStatistic(@NotNull final Statistic statistic, @NotNull final Material material) throws IllegalArgumentException {

    }

    @Override
    public void decrementStatistic(@NotNull final Statistic statistic, @NotNull final Material material) throws IllegalArgumentException {

    }

    @Override
    public int getStatistic(@NotNull final Statistic statistic, @NotNull final Material material) throws IllegalArgumentException {
        return player.getStatistic(statistic, material);
    }

    @Override
    public void incrementStatistic(@NotNull final Statistic statistic, @NotNull final Material material, final int i) throws IllegalArgumentException {

    }

    @Override
    public void decrementStatistic(@NotNull final Statistic statistic, @NotNull final Material material, final int i) throws IllegalArgumentException {

    }

    @Override
    public void setStatistic(@NotNull final Statistic statistic, @NotNull final Material material, final int i) throws IllegalArgumentException {

    }

    @Override
    public void incrementStatistic(@NotNull final Statistic statistic, @NotNull final EntityType entityType) throws IllegalArgumentException {

    }

    @Override
    public void decrementStatistic(@NotNull final Statistic statistic, @NotNull final EntityType entityType) throws IllegalArgumentException {

    }

    @Override
    public int getStatistic(@NotNull final Statistic statistic, @NotNull final EntityType entityType) throws IllegalArgumentException {
        return player.getStatistic(statistic, entityType);
    }

    @Override
    public void incrementStatistic(@NotNull final Statistic statistic, @NotNull final EntityType entityType, final int i) throws IllegalArgumentException {

    }

    @Override
    public void decrementStatistic(@NotNull final Statistic statistic, @NotNull final EntityType entityType, final int i) {

    }

    @Override
    public void setStatistic(@NotNull final Statistic statistic, @NotNull final EntityType entityType, final int i) {

    }

    @Override
    public void setBedSpawnLocation(@Nullable final Location location) {

    }

    @Override
    public void setBedSpawnLocation(@Nullable final Location location, final boolean b) {

    }

    @Override
    public void playNote(@NotNull final Location location, final byte b, final byte b1) {

    }

    @Override
    public void playNote(@NotNull final Location location, @NotNull final Instrument instrument, @NotNull final Note note) {

    }

    @Override
    public void playSound(@NotNull final Location location, @NotNull final Sound sound, final float v, final float v1) {

    }

    @Override
    public void playSound(@NotNull final Location location, @NotNull final String s, final float v, final float v1) {

    }

    @Override
    public void playSound(@NotNull final Location location, @NotNull final Sound sound, @NotNull final SoundCategory soundCategory, final float v, final float v1) {

    }

    @Override
    public void playSound(@NotNull final Location location, @NotNull final String s, @NotNull final SoundCategory soundCategory, final float v, final float v1) {

    }

    @Override
    public void playSound(@NotNull Entity entity, @NotNull Sound sound, float volume, float pitch) {

    }

    @Override
    public void playSound(@NotNull Entity entity, @NotNull Sound sound, @NotNull SoundCategory category, float volume, float pitch) {

    }

    @Override
    public void stopSound(@NotNull final Sound sound) {

    }

    @Override
    public void stopSound(@NotNull final String s) {

    }

    @Override
    public void stopSound(@NotNull final Sound sound, @Nullable final SoundCategory soundCategory) {

    }

    @Override
    public void stopSound(@NotNull final String s, @Nullable final SoundCategory soundCategory) {

    }

    @Override
    public void stopAllSounds() {

    }

    @Override
    public void playEffect(@NotNull final Location location, @NotNull final Effect effect, final int i) {

    }

    @Override
    public <T> void playEffect(@NotNull final Location location, @NotNull final Effect effect, @Nullable final T t) {

    }

    @Override
    public boolean breakBlock(@NotNull final Block block) {
        return false;
    }

    @Override
    public void sendBlockChange(@NotNull final Location location, @NotNull final Material material, final byte b) {

    }

    @Override
    public void sendBlockChange(@NotNull final Location location, @NotNull final BlockData blockData) {

    }

    @Override
    public void sendBlockDamage(@NotNull final Location location, final float v) {

    }

    @Override
    public void sendEquipmentChange(@NotNull final LivingEntity livingEntity, @NotNull final EquipmentSlot equipmentSlot, @NotNull final ItemStack itemStack) {

    }

    // No Override, was removed in 1.18
    @SuppressWarnings("MethodMayBeStatic")
    public boolean sendChunkChange(@NotNull final Location location, final int i, final int i1, final int i2, final byte[] bytes) {
        return false;
    }

    @Override
    public void sendSignChange(@NotNull final Location location, @Nullable final String[] strings) throws IllegalArgumentException {

    }

    @Override
    public void sendSignChange(@NotNull final Location location, @Nullable final String[] strings, @NotNull final DyeColor dyeColor) throws IllegalArgumentException {

    }

    @Override
    public void sendSignChange(@NotNull final Location location, @Nullable final String[] strings, @NotNull final DyeColor dyeColor, final boolean b) throws IllegalArgumentException {

    }

    @Override
    public void sendMap(@NotNull final MapView mapView) {

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
    public void setPlayerWeather(@NotNull final WeatherType weatherType) {

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
    public void hidePlayer(@NotNull final Player player) {

    }

    @Override
    public void hidePlayer(@NotNull final Plugin plugin, @NotNull final Player player) {

    }

    @Override
    public void showPlayer(@NotNull final Player player) {

    }

    @Override
    public void showPlayer(@NotNull final Plugin plugin, @NotNull final Player player) {

    }

    @Override
    public boolean canSee(@NotNull final Player player) {
        return this.player.canSee(player);
    }

    public void hideEntity(@NotNull final Plugin plugin, @NotNull Entity entity) {

    }

    public void showEntity(@NotNull final Plugin plugin, @NotNull Entity entity) {

    }

    public boolean canSee(@NotNull final Entity entity) {
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
    public void setTexturePack(@NotNull final String s) {

    }

    @Override
    public void setResourcePack(@NotNull final String s) {

    }

    @Override
    public void setResourcePack(@NotNull final String s, final byte[] bytes) {

    }

    @Override
    public void setResourcePack(@NotNull String url, @Nullable byte[] hash, @Nullable String prompt) {

    }

    @Override
    public void setResourcePack(@NotNull String url, @Nullable byte[] hash, boolean force) {

    }

    @Override
    public void setResourcePack(@NotNull String url, @Nullable byte[] hash, @Nullable String prompt, boolean force) {

    }

    @NotNull
    @Override
    public Scoreboard getScoreboard() {
        return player.getScoreboard();
    }

    @Override
    public void setScoreboard(@NotNull final Scoreboard scoreboard) throws IllegalArgumentException, IllegalStateException {

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
    public void spawnParticle(@NotNull final Particle particle, @NotNull final Location location, final int i) {

    }

    @Override
    public void spawnParticle(@NotNull final Particle particle, final double v, final double v1, final double v2, final int i) {

    }

    @Override
    public <T> void spawnParticle(@NotNull final Particle particle, @NotNull final Location location, final int i, @Nullable final T t) {

    }

    @Override
    public <T> void spawnParticle(@NotNull final Particle particle, final double v, final double v1, final double v2, final int i, @Nullable final T t) {

    }

    @Override
    public void spawnParticle(@NotNull final Particle particle, @NotNull final Location location, final int i, final double v, final double v1, final double v2) {

    }

    @Override
    public void spawnParticle(@NotNull final Particle particle, final double v, final double v1, final double v2, final int i, final double v3, final double v4, final double v5) {

    }

    @Override
    public <T> void spawnParticle(@NotNull final Particle particle, @NotNull final Location location, final int i, final double v, final double v1, final double v2, @Nullable final T t) {

    }

    @Override
    public <T> void spawnParticle(@NotNull final Particle particle, final double v, final double v1, final double v2, final int i, final double v3, final double v4, final double v5, @Nullable final T t) {

    }

    @Override
    public void spawnParticle(@NotNull final Particle particle, @NotNull final Location location, final int i, final double v, final double v1, final double v2, final double v3) {

    }

    @Override
    public void spawnParticle(@NotNull final Particle particle, final double v, final double v1, final double v2, final int i, final double v3, final double v4, final double v5, final double v6) {

    }

    @Override
    public <T> void spawnParticle(@NotNull final Particle particle, @NotNull final Location location, final int i, final double v, final double v1, final double v2, final double v3, @Nullable final T t) {

    }

    @Override
    public <T> void spawnParticle(@NotNull final Particle particle, final double v, final double v1, final double v2, final int i, final double v3, final double v4, final double v5, final double v6, @Nullable final T t) {

    }

    @NotNull
    @Override
    public AdvancementProgress getAdvancementProgress(@NotNull final Advancement advancement) {
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

    @NotNull
    @Override
    public String getLocale() {
        return player.getLocale();
    }

    @Override
    public void updateCommands() {

    }

    @Override
    public void openBook(@NotNull final ItemStack itemStack) {

    }

    public void openSign(@NotNull final Sign sign) {

    }

    @Override
    public void showDemoScreen() {

    }

    @Override
    public boolean isAllowingServerListings() {
        return false;
    }

    @NotNull
    @Override
    public Spigot spigot() {
        return player.spigot();
    }

    @NotNull
    @Override
    public Map<String, Object> serialize() {
        return player.serialize();
    }

    @NotNull
    @Override
    public String getName() {
        return player.getName();
    }

    @NotNull
    @Override
    public PlayerInventory getInventory() {
        return player.getInventory();
    }

    @NotNull
    @Override
    public Inventory getEnderChest() {
        return player.getEnderChest();
    }

    @NotNull
    @Override
    public MainHand getMainHand() {
        return player.getMainHand();
    }

    @Override
    public boolean setWindowProperty(@NotNull final InventoryView.Property property, final int i) {
        return false;
    }

    @NotNull
    @Override
    public InventoryView getOpenInventory() {
        return player.getOpenInventory();
    }

    @Nullable
    @Override
    public InventoryView openInventory(@NotNull final Inventory inventory) {
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
    public void openInventory(@NotNull final InventoryView inventoryView) {

    }

    @Nullable
    @Override
    public InventoryView openMerchant(@NotNull final Villager villager, final boolean b) {
        return null;
    }

    @Nullable
    @Override
    public InventoryView openMerchant(@NotNull final Merchant merchant, final boolean b) {
        return null;
    }

    @Override
    public void closeInventory() {

    }

    @NotNull
    @Override
    public ItemStack getItemInHand() {
        return player.getItemInHand();
    }

    @Override
    public void setItemInHand(@Nullable final ItemStack itemStack) {

    }

    @NotNull
    @Override
    public ItemStack getItemOnCursor() {
        return player.getItemOnCursor();
    }

    @Override
    public void setItemOnCursor(@Nullable final ItemStack itemStack) {

    }

    @Override
    public boolean hasCooldown(@NotNull final Material material) {
        return player.hasCooldown(material);
    }

    @Override
    public int getCooldown(@NotNull final Material material) {
        return player.getCooldown(material);
    }

    @Override
    public void setCooldown(@NotNull final Material material, final int i) {

    }

    @Override
    public int getSleepTicks() {
        return player.getSleepTicks();
    }

    @Override
    public boolean sleep(@NotNull final Location location, final boolean b) {
        return false;
    }

    @Override
    public void wakeup(final boolean b) {

    }

    @NotNull
    @Override
    public Location getBedLocation() {
        return player.getBedLocation();
    }

    @NotNull
    @Override
    public GameMode getGameMode() {
        return player.getGameMode();
    }

    @Override
    public void setGameMode(@NotNull final GameMode gameMode) {

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
    public boolean discoverRecipe(@NotNull final NamespacedKey namespacedKey) {
        return false;
    }

    @Override
    public int discoverRecipes(@NotNull final Collection<NamespacedKey> collection) {
        return 0;
    }

    @Override
    public boolean undiscoverRecipe(@NotNull final NamespacedKey namespacedKey) {
        return false;
    }

    @Override
    public int undiscoverRecipes(@NotNull final Collection<NamespacedKey> collection) {
        return 0;
    }

    @Override
    public boolean hasDiscoveredRecipe(@NotNull final NamespacedKey namespacedKey) {
        return player.hasDiscoveredRecipe(namespacedKey);
    }

    @NotNull
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

    @Override
    public double getEyeHeight() {
        return player.getEyeHeight();
    }

    @Override
    public double getEyeHeight(final boolean b) {
        return player.getEyeHeight(b);
    }

    @NotNull
    @Override
    public Location getEyeLocation() {
        return player.getEyeLocation();
    }

    @NotNull
    @Override
    public List<Block> getLineOfSight(@Nullable final Set<Material> set, final int i) {
        return player.getLineOfSight(set, i);
    }

    @NotNull
    @Override
    public Block getTargetBlock(@Nullable final Set<Material> set, final int i) {
        return player.getTargetBlock(set, i);
    }

    @NotNull
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
    public Block getTargetBlockExact(final int i, @NotNull final FluidCollisionMode fluidCollisionMode) {
        return player.getTargetBlockExact(i, fluidCollisionMode);
    }

    @Nullable
    @Override
    public RayTraceResult rayTraceBlocks(final double v) {
        return player.rayTraceBlocks(v);
    }

    @Nullable
    @Override
    public RayTraceResult rayTraceBlocks(final double v, @NotNull final FluidCollisionMode fluidCollisionMode) {
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
    public boolean addPotionEffect(@NotNull final PotionEffect potionEffect) {
        return false;
    }

    @Override
    public boolean addPotionEffect(@NotNull final PotionEffect potionEffect, final boolean b) {
        return false;
    }

    @Override
    public boolean addPotionEffects(@NotNull final Collection<PotionEffect> collection) {
        return false;
    }

    @Override
    public boolean hasPotionEffect(@NotNull final PotionEffectType potionEffectType) {
        return player.hasPotionEffect(potionEffectType);
    }

    @Nullable
    @Override
    public PotionEffect getPotionEffect(@NotNull final PotionEffectType potionEffectType) {
        return player.getPotionEffect(potionEffectType);
    }

    @Override
    public void removePotionEffect(@NotNull final PotionEffectType potionEffectType) {

    }

    @NotNull
    @Override
    public Collection<PotionEffect> getActivePotionEffects() {
        return player.getActivePotionEffects();
    }

    @Override
    public boolean hasLineOfSight(@NotNull final Entity entity) {
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

    @NotNull
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
    public void attack(@NotNull final Entity entity) {

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

    @NotNull
    @Override
    public Set<UUID> getCollidableExemptions() {
        return player.getCollidableExemptions();
    }

    @Nullable
    @Override
    public <T> T getMemory(@NotNull final MemoryKey<T> memoryKey) {
        return player.getMemory(memoryKey);
    }

    @Override
    public <T> void setMemory(@NotNull final MemoryKey<T> memoryKey, @Nullable final T t) {

    }

    @NotNull
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
    public AttributeInstance getAttribute(@NotNull final Attribute attribute) {
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
    public void setMetadata(@NotNull final String s, @NotNull final MetadataValue metadataValue) {

    }

    @NotNull
    @Override
    public List<MetadataValue> getMetadata(@NotNull final String s) {
        return player.getMetadata(s);
    }

    @Override
    public boolean hasMetadata(@NotNull final String s) {
        return player.hasMetadata(s);
    }

    @Override
    public void removeMetadata(@NotNull final String s, @NotNull final Plugin plugin) {

    }

    @Override
    public boolean isPermissionSet(@NotNull final String s) {
        return player.isPermissionSet(s);
    }

    @Override
    public boolean isPermissionSet(@NotNull final Permission permission) {
        return player.isPermissionSet(permission);
    }

    @Override
    public boolean hasPermission(@NotNull final String s) {
        return player.hasPermission(s);
    }

    @Override
    public boolean hasPermission(@NotNull final Permission permission) {
        return player.hasPermission(permission);
    }

    @NotNull
    @Override
    public PermissionAttachment addAttachment(@NotNull final Plugin plugin, @NotNull final String s, final boolean b) {
        return player.addAttachment(plugin, s, b);
    }

    @NotNull
    @Override
    public PermissionAttachment addAttachment(@NotNull final Plugin plugin) {
        return player.addAttachment(plugin);
    }

    @Nullable
    @Override
    public PermissionAttachment addAttachment(@NotNull final Plugin plugin, @NotNull final String s, final boolean b, final int i) {
        return null;
    }

    @Nullable
    @Override
    public PermissionAttachment addAttachment(@NotNull final Plugin plugin, final int i) {
        return null;
    }

    @Override
    public void removeAttachment(@NotNull final PermissionAttachment permissionAttachment) {

    }

    @Override
    public void recalculatePermissions() {

    }

    @NotNull
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

    @NotNull
    @Override
    public PersistentDataContainer getPersistentDataContainer() {
        return player.getPersistentDataContainer();
    }

    @Override
    public void sendPluginMessage(@NotNull final Plugin plugin, @NotNull final String s, final byte[] bytes) {

    }

    @NotNull
    @Override
    public Set<String> getListeningPluginChannels() {
        return player.getListeningPluginChannels();
    }

    @NotNull
    @Override
    public <T extends Projectile> T launchProjectile(@NotNull final Class<? extends T> aClass) {
        return player.launchProjectile(aClass);
    }

    @NotNull
    @Override
    public <T extends Projectile> T launchProjectile(@NotNull final Class<? extends T> aClass, @Nullable final Vector vector) {
        return player.launchProjectile(aClass, vector);
    }
}
