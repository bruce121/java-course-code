package com.bruce121;

import org.apache.shardingsphere.sharding.api.sharding.standard.RangeShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.StandardShardingAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

public class TableShardingAlgorithm implements StandardShardingAlgorithm<String> {
    private static final Logger logger = LoggerFactory.getLogger(TableShardingAlgorithm.class);

    @Override
    public String doSharding(Collection<String> availableTargetNames, org.apache.shardingsphere.sharding.api.sharding.standard.PreciseShardingValue<String> shardingValue) {
        String orderId = shardingValue.getValue();

        // 根据取userId的最后一个字母
        char lastChar = orderId.charAt(orderId.length() - 1);
        int index = lastChar % 16;

        for (String targetName : availableTargetNames) {
            if (targetName.endsWith(String.valueOf(index))) {
                return targetName;
            }
        }
        throw new UnsupportedOperationException();
    }

    @Override
    public Collection<String> doSharding(Collection<String> collection, RangeShardingValue<String> rangeShardingValue) {
        return null;
    }

    @Override
    public void init() {
        logger.info("DbShardingAlgorithm inited.");
    }

    @Override
    public String getType() {
        return "TABLE_TEST";
    }
}
