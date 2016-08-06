package com.youzan.test.faker.matcher;

/**
 * Created by libaixian on 16/8/2.
 */
public abstract class Matcher {
    protected String key = null;

    public boolean valid(String key) {
        if (!this.key.equalsIgnoreCase(key)) {
            return false;
        }

        return true;
    }

    public String getKey() {
        return this.key;
    }

    public abstract boolean match(String target);
}
