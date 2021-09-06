/*
package de.jeff_media.jefflib.internal.nms;

import net.minecraft.world.entity.player.EntityHuman;
import org.bukkit.World;
import org.bukkit.craftbukkit.v1_17_R1.CraftWorld;

import java.util.UUID;

public final class MapUpdater extends EntityHuman {
    public static final UUID ID = UUID.randomUUID();
    public static final String NAME = "__JeffLib__";

    public MapUpdater(World world) {

        super(((CraftWorld) world).getHandle(),new BlockPosition(0, Bukkit.getWorld("world").getHighestBlockYAt(0, 0),0),0, new GameProfile(ID, NAME));


    }

    @Override
    public boolean isCreative() {
        return false;
    }

    @Override
    public boolean isSpectator() {
        return false;
    }

    public void update(MapView mapView) {

        ArrayList<org.bukkit.Chunk> chunks = new ArrayList<>();

        if (mapView == null) {
            throw new IllegalArgumentException("mapView cannot be null");
        }

        if (((CraftWorld) mapView.getWorld()).getHandle() != getWorld()) {
            throw new IllegalArgumentException("world of mapView cannot be different");
        }

        Chunk center = mapView.getWorld().getChunkAt(mapView.getCenterX() >> 4, mapView.getCenterZ() >> 4);

        //We loop to load the region 32*32 around the middle chunk
        for(int x = center.getX() -16; x <= center.getX() + 16; x++){

            for(int z = center.getZ() -16; z <= center.getZ() + 16; z++){
                org.bukkit.Chunk chunk = mapView.getWorld().getChunkAt(x, z);
                chunk.addPluginChunkTicket(RushToTheBeacon.getInstance());
                chunks.add(chunk);
            }
        }

        // We render on the map
        Bukkit.getScheduler().scheduleSyncDelayedTask(RushToTheBeacon.getInstance(), ()->{

            try {
                Field field = CraftMapView.class.getDeclaredField("worldMap");
                field.setAccessible(true);
                WorldMap worldMap = (WorldMap) field.get(mapView);

                int size = 128 << mapView.getScale().getValue();

                for (int x = mapView.getCenterX() - size / 2; x <= mapView.getCenterX() + size / 2; x += 16) {
                    for (int z = mapView.getCenterZ() - size / 2; z <= mapView.getCenterZ() + size / 2; z += 16) {

                        setPositionRaw(x, 1., z);
                        ((ItemWorldMap) Items.pp).a(getWorld(), this, worldMap);
                    }
                }
            } catch (NoSuchFieldException | SecurityException | IllegalArgumentException | IllegalAccessException e) {
                e.printStackTrace();
            }

            // We unload the chunk loaded previously
            for(Chunk c : chunks){
                c.removePluginChunkTicket(RushToTheBeacon.getInstance());
            }

        }, 5L);


    }
}*/
