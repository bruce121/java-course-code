package com.bruce121;

import org.apache.shardingsphere.sharding.api.sharding.standard.PreciseShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.RangeShardingValue;
import org.apache.shardingsphere.sharding.api.sharding.standard.StandardShardingAlgorithm;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;

public class DbShardingAlgorithm implements StandardShardingAlgorithm<String> {
    private static final Logger logger = LoggerFactory.getLogger(DbShardingAlgorithm.class);

    @Override
    public String doSharding(Collection<String> availableTargetNames, PreciseShardingValue<String> shardingValue) {
        String userId = shardingValue.getValue();

        // 根据取userId的最后一个字母
        char lastChar = userId.charAt(userId.length() - 1);
        int index = lastChar % 2;

        for (String dataSourceName : availableTargetNames) {
            if (dataSourceName.endsWith(String.valueOf(index))) {
                return dataSourceName;
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
        logger.info("DB_TEST getTYPE");
        return "DB_TEST";
    }
}
