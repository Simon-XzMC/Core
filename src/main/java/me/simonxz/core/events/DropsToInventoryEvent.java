package me.simonxz.core.events;

import java.util.List;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

public class DropsToInventoryEvent extends Event implements Cancellable {
    private static final HandlerList handlers = new HandlerList();

    private final Player player;

    private List<ItemStack> drops;

    private boolean cancelled;

    private Block block;

    private int xp;

    public DropsToInventoryEvent(Player paramPlayer, List<ItemStack> paramList, Block paramBlock, int paramInt) {
        this.player = paramPlayer;
        this.drops = paramList;
        setBlock(paramBlock);
        this.xp = paramInt;
    }

    public String getName() {
        return this.player.getName();
    }

    public Player getPlayer() {
        return this.player;
    }

    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }

    public boolean isCancelled() {
        return this.cancelled;
    }

    public void setCancelled(boolean paramBoolean) {
        this.cancelled = paramBoolean;
    }

    public List<ItemStack> getDrops() {
        return this.drops;
    }

    public void setDrops(List<ItemStack> paramList) {
        this.drops = paramList;
    }

    public Block getBlock() {
        return this.block;
    }

    public void setBlock(Block paramBlock) {
        this.block = paramBlock;
    }

    public int getXp() {
        return this.xp;
    }

    public void setXp(int paramInt) {
        this.xp = paramInt;
    }
}
