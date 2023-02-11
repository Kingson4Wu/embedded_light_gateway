package com.kxw.light.gateway.tools.concurrent;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

import org.apache.commons.collections4.CollectionUtils;
import org.junit.Assert;
import org.junit.Test;

public class ReadWriteLockWrapperTest {

    @Test
    public void execute() {
        ReadWriteLockWrapper<String> lockWrapper = ReadWriteLockWrapper.newLock();

        Supplier<String> loadFromRemote = () -> {
            return "OK";
        };
        Supplier<String> loadFromCache = () -> {
            return "OK";
        };
        Function<String, Boolean> isExist = (v) -> {
            return Objects.nonNull(v);
        };

        String result = lockWrapper.execute(loadFromRemote, loadFromCache, isExist);

        Assert.assertEquals(result, "OK");

    }

    @Test
    public void execute2() {
        ReadWriteLockWrapper<List<String>> lockWrapper = ReadWriteLockWrapper.newLock();

        Supplier<List<String>> loadFromRemote = () -> {
            return Arrays.asList("fff");
        };
        Supplier<List<String>> loadFromCache = () -> {
            return Collections.emptyList();
        };
        Function<List<String>, Boolean> isExist = (v) -> {
            return CollectionUtils.isNotEmpty(v);
        };

        List<String> result = lockWrapper.execute(loadFromRemote, loadFromCache, isExist);

        Assert.assertEquals(result.get(0), "fff");

    }
}
