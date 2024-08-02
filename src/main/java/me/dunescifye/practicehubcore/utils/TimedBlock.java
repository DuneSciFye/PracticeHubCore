package me.dunescifye.practicehubcore.utils;

import org.bukkit.block.Block;

import java.time.Instant;

public class TimedBlock {
    private final Block block;
    private final Instant time;

    public TimedBlock(Block block, Instant time) {
        this.block = block;
        this.time = time;
    }

    public Block getBlock() {
        return block;
    }

    public Instant getTime() {
        return time;
    }

}
