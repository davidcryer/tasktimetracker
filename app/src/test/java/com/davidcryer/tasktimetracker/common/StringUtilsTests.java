package com.davidcryer.tasktimetracker.common;

import junit.framework.Assert;

import org.junit.Test;

public class StringUtilsTests {

    @Test
    public void concatenate() {
        Assert.assertEquals(StringUtils.concatenate(new String[] {"Message 1", "Message 2"}, ", "), "Message 1, Message 2");
    }
}
