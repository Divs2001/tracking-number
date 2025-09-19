package com.teleport.parcel_tracking.util;

import com.teleport.parcel_tracking.config.SnowflakeProperties;
import org.springframework.stereotype.Component;

@Component
public class SnowflakeIdGenerator {
    private final long epoch = 1672531200000L; // Jan 1, 2023 custom epoch

    private final long machineIdBits = 10L;
    private final long sequenceBits = 12L;

    private final long maxMachineId = ~(-1L << machineIdBits);
    private final long maxSequence = ~(-1L << sequenceBits);

    private final long machineIdShift = sequenceBits;
    private final long timestampShift = sequenceBits + machineIdBits;

    private long machineId;
    private long lastTimestamp = -1L;
    private long sequence = 0L;

    public SnowflakeIdGenerator(SnowflakeProperties properties) {
        // For simplicity, machineId = 1 (could be env var for multi-instance setup)
        this.machineId = properties.getMachineId();
        if (machineId > maxMachineId) {
            throw new IllegalArgumentException("Machine ID exceeds max allowed");
        }
    }

    public synchronized long nextId() {
        long timestamp = currentTime();

        if (timestamp < lastTimestamp) {
            throw new RuntimeException("Clock moved backwards. Refusing to generate id.");
        }

        if (timestamp == lastTimestamp) {
            sequence = (sequence + 1) & maxSequence;
            if (sequence == 0) {
                timestamp = waitNextMillis(lastTimestamp);
            }
        } else {
            sequence = 0L;
        }

        lastTimestamp = timestamp;

        return ((timestamp - epoch) << timestampShift)
                | (machineId << machineIdShift)
                | sequence;
    }

    private long waitNextMillis(long lastTimestamp) {
        long timestamp = currentTime();
        while (timestamp <= lastTimestamp) {
            timestamp = currentTime();
        }
        return timestamp;
    }

    private long currentTime() {
        return System.currentTimeMillis();
    }
}

