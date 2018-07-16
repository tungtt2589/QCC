package com.storm.quora.cache.redis;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

import java.util.List;
import java.util.Set;

public abstract class CacheService {
    private JedisPool underlyingPool = null;

    protected abstract String getKeyPrefix();

    protected CacheService() {
    }

    private String makeKey(String inputKey) {
        return this.getKeyPrefix() + inputKey;
    }

    protected CacheService(JedisPool pool) {
        this.underlyingPool = pool;
    }

    public boolean exists(String key) {
        Jedis jedis = null;
        Boolean ret = false;

        try {
            jedis = this.underlyingPool.getResource();
            ret = jedis.exists(this.makeKey(key));
        } finally {
            if (jedis != null) {
                jedis.close();
            }

            return ret;
        }
    }

    public void setValue(String key, String value) {
        Jedis jedis = null;

        try {
            jedis = this.underlyingPool.getResource();
            jedis.set(this.makeKey(key), value);
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

    }

    public void setValue(String key, String value, Integer expiryTime) {
        Jedis jedis = null;

        try {
            jedis = this.underlyingPool.getResource();
            jedis.setex(this.makeKey(key), expiryTime, value);
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

    }

    public String getValue(String key) {
        Jedis jedis = null;
        String ret = null;

        try {
            jedis = this.underlyingPool.getResource();
            ret = jedis.get(this.makeKey(key));
        } finally {
            if (jedis != null) {
                jedis.close();
            }

            return ret;
        }
    }

    public String getSet(String key, String value) {
        Jedis jedis = null;
        String ret = null;

        try {
            jedis = this.underlyingPool.getResource();
            ret = jedis.getSet(this.makeKey(key), value);
        } finally {
            if (jedis != null) {
                jedis.close();
            }

            return ret;
        }
    }

    public void setIntValue(String key, Integer value) {
        this.setValue(key, value.toString());
    }

    public void setIntValue(String key, Integer value, Integer expiryTime) {
        this.setValue(key, value.toString(), expiryTime);
    }

    public Integer getIntValue(String key) {
        try {
            return Integer.parseInt(this.getValue(key));
        } catch (Exception var3) {
            return null;
        }
    }

    public Integer getIntValue(String key, Integer defaultValue) {
        try {
            return Integer.parseInt(this.getValue(key));
        } catch (Exception var4) {
            return defaultValue;
        }
    }

    public void setFloatValue(String key, Float value) {
        this.setValue(key, String.valueOf(value));
    }

    public void setFloatValue(String key, Float value, Integer expiryTime) {
        this.setValue(key, String.valueOf(value), expiryTime);
    }

    public Float getFloatValue(String key) {
        try {
            return Float.parseFloat(this.getValue(key));
        } catch (Exception var3) {
            return null;
        }
    }

    public Float getFloatValue(String key, Float defaultValue) {
        try {
            return Float.parseFloat(this.getValue(key));
        } catch (Exception var4) {
            return defaultValue;
        }
    }

    public void setDoubleValue(String key, Double value) {
        this.setValue(key, String.valueOf(value));
    }

    public void setDoubleValue(String key, Double value, Integer expiryTime) {
        this.setValue(key, String.valueOf(value), expiryTime);
    }

    public Double getDoubleValue(String key) {
        try {
            return Double.parseDouble(this.getValue(key));
        } catch (Exception var3) {
            return null;
        }
    }

    public Double getDoubleValue(String key, Double defaultValue) {
        try {
            return Double.parseDouble(this.getValue(key));
        } catch (Exception var4) {
            return defaultValue;
        }
    }

    public Long incrInt(String key, Integer value) {
        Jedis jedis = null;
        Long ret = null;

        try {
            jedis = this.underlyingPool.getResource();
            ret = jedis.incrBy(this.makeKey(key), (long) value);
        } finally {
            if (jedis != null) {
                jedis.close();
            }

            return ret;
        }
    }

    public Double incrFloat(String key, Double value) {
        Jedis jedis = null;
        Double ret = null;

        try {
            jedis = this.underlyingPool.getResource();
            ret = jedis.incrByFloat(this.makeKey(key), value);
        } finally {
            if (jedis != null) {
                jedis.close();
            }

            return ret;
        }
    }

    public void remove(String key) {
        Jedis jedis = null;

        try {
            jedis = this.underlyingPool.getResource();
            jedis.del(this.makeKey(key));
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

    }

    /**
     * thêm 1 phần tử vào đầu danh sách
     *
     * @param key
     * @param item
     * @return ret
     */
    public Long lput(String key, String item) {
        Jedis jedis = null;
        Long ret = 0L;

        try {
            jedis = this.underlyingPool.getResource();
            String appliedKey = this.makeKey(key);
            jedis.lpush(appliedKey, new String[]{item});
            ret = jedis.llen(appliedKey);
        } finally {
            if (jedis != null) {
                jedis.close();
            }

            return ret;
        }
    }

    /**
     * pop ra phần tử cuối ds
     *
     * @param key
     * @return ret
     */
    public String lpop(String key) {
        Jedis jedis = null;
        String ret = null;

        try {
            jedis = this.underlyingPool.getResource();
            String appliedKey = this.makeKey(key);
            ret = jedis.rpop(appliedKey);
        } finally {
            if (jedis != null) {
                jedis.close();
            }

            return ret;
        }
    }

    public String lindex(String key, Long index) {
        Jedis jedis = null;
        String ret = null;

        try {
            jedis = this.underlyingPool.getResource();
            ret = jedis.lindex(this.makeKey(key), index);
        } finally {
            if (jedis != null) {
                jedis.close();
            }
            return ret;
        }
    }

    /**
     * số phần tử của ds
     *
     * @param key
     * @return ret
     */
    public Long llen(String key) {
        Jedis jedis = null;
        Long ret = 0L;

        try {
            jedis = this.underlyingPool.getResource();
            ret = jedis.llen(this.makeKey(key));
        } finally {
            if (jedis != null) {
                jedis.close();
            }

            return ret;
        }
    }

    /**
     * lấy ds theo 1 khoảng chỉ số
     *
     * @param key
     * @param start
     * @param end
     * @return
     */
    public List<String> lrange(String key, long start, long end) {
        Jedis jedis = null;
        List ret = null;

        try {
            jedis = this.underlyingPool.getResource();
            ret = jedis.lrange(this.makeKey(key), start, end);
        } finally {
            if (jedis != null) {
                jedis.close();
            }

            return ret;
        }
    }

    public String hget(String key, String field) {
        Jedis jedis = null;
        String ret = null;

        try {
            jedis = this.underlyingPool.getResource();
            String appliedKey = this.makeKey(key);
            ret = jedis.hget(appliedKey, field);
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

        return ret;
    }

    public void hset(String key, String field, String value) {
        Jedis jedis = null;

        try {
            jedis = this.underlyingPool.getResource();
            jedis.hset(this.makeKey(key), field, value);
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }

    }

    public void hdel(String key, String field) {
        Jedis jedis = null;

        try {
            jedis = this.underlyingPool.getResource();
            jedis.hdel(this.makeKey(key), new String[]{field});
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }
    }

    public long hlen(String key) {
        Jedis jedis = null;
        long len;

        try {
            jedis = this.underlyingPool.getResource();
            len = jedis.hlen(this.makeKey(key));
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
        return len;
    }

    public Set<String> hkeys(String key) {
        Jedis jedis = null;

        try {
            jedis = this.underlyingPool.getResource();
            return jedis.hkeys(this.makeKey(key));
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }

    public boolean hexists(String key, String field) {
        Jedis jedis = null;

        try {
            jedis = this.underlyingPool.getResource();
            return jedis.hexists(this.makeKey(key), field);
        } finally {
            if (jedis != null) {
                jedis.close();
            }

        }
    }

    public boolean keys(String key) {
        Jedis jedis = null;

        try {
            jedis = this.underlyingPool.getResource();
            return jedis.keys(this.makeKey(key)).size() > 0;
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }

    }

    public Set<String> lkeys(String key) {
        Jedis jedis = null;

        try {
            jedis = this.underlyingPool.getResource();
            return jedis.keys(this.makeKey(key));
        } finally {
            if (jedis != null) {
                jedis.close();
            }
        }
    }
}
