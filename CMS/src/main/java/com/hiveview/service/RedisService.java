package main.java.com.hiveview.service;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.data.redis.connection.RedisConnection;
import org.springframework.data.redis.core.RedisCallback;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JacksonJsonRedisSerializer;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class RedisService {

	private Logger LOG = LoggerFactory.getLogger(RedisService.class);

	@Autowired
	private List<RedisTemplate<String, String>> redisTemplateList;
	
	@Autowired
	private RedisTemplate<String, String> redisTemplate;

	/**
	 * 从缓存中删除指定的key
	 * @param keys
	 */
	public void del(final String... keys) {
		for (RedisTemplate<String, String> redisTemplate : redisTemplateList) {
			try {
				redisTemplate.execute(new RedisCallback<Long>() {
					public Long doInRedis(RedisConnection connection) throws DataAccessException {
						long result = 0;
						for (int i = 0; i < keys.length; i++) {
							result = connection.del(keys[i].getBytes());
						}
						return result;
					}
				});

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 重缓存中删除指定的key 模式匹配，效率低
	 * @param keys
	 */
	public void delByReg(final String... keys) {
		for (RedisTemplate<String, String> redisTemplate : redisTemplateList) {
			try {
				redisTemplate.execute(new RedisCallback<Long>() {
					public Long doInRedis(RedisConnection connection) throws DataAccessException {
						long result = 0;
						for (int i = 0; i < keys.length; i++) {
							Set<byte[]> keyset = connection.keys((keys[i] + "*").getBytes());
							for (byte[] key : keyset) {
								result = connection.del(key);
							}
						}
						return result;
					}
				});

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 判断一个键是否存在于缓存中
	 * @param key
	 * @return
	 */
	public boolean exists(final String key) {
		Boolean result = false;
		for (RedisTemplate<String, String> redisTemplate : redisTemplateList) {
			try {
				result = (Boolean) redisTemplate.execute(new RedisCallback<Object>() {
					public Boolean doInRedis(RedisConnection connection) throws DataAccessException {
						return connection.exists(key.getBytes());
					}
				});

				if (result) {
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}

		return result;
	}

	/**
	 * 向缓存中插入数据
	 * @param key
	 * @param value
	 * @param liveTime
	 */
	public void set(final byte[] key, final byte[] value, final long liveTime) {
		for (RedisTemplate<String, String> redisTemplate : redisTemplateList) {
			try {
				redisTemplate.execute(new RedisCallback<Object>() {
					public Long doInRedis(final RedisConnection connection) throws DataAccessException {
						connection.set(key, value);
						if (liveTime > 0) {
							connection.expire(key, liveTime);
						}
						return 1L;
					}
				});
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	public <T> void set(String key, T value, long liveTime, Class<T> type) {
		if (value == null)
			return;
		try {
			JacksonJsonRedisSerializer<T> serializer = new JacksonJsonRedisSerializer<T>(type);
			byte[] _value = serializer.serialize(value);
			this.set(key.getBytes(), _value, liveTime);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void set(String key, String value, long liveTime) {
		try {
			byte [] values =value.getBytes("utf-8");
			this.set(key.getBytes(), values, liveTime);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
	}
	
	public <T> List<T> lRange(final String key, final Long begin, final Long end, final Class<T> type) {
		return redisTemplate.execute(new RedisCallback<List<T>>() {

			public List<T> doInRedis(RedisConnection connection) throws DataAccessException {
				JacksonJsonRedisSerializer<T> serializer = new JacksonJsonRedisSerializer<T>(type);
				List<T> list = new ArrayList<T>();
				for (byte[] element : connection.lRange(key.getBytes(), begin, end)) {
					T bean = serializer.deserialize(element);
					list.add(bean);
				}
				return list;
			}
		});
	}

	/**
	 * 从缓存中获取数据
	 * @param key
	 * @return
	 */
	public String get(final String key) {
		String cacheValue = "";
		for (RedisTemplate<String, String> redisTemplate : redisTemplateList) {
			try {
				cacheValue = redisTemplate.execute(new RedisCallback<String>() {
					public String doInRedis(RedisConnection connection) throws DataAccessException {
						try {
							byte[] cacheBytes = connection.get(key.getBytes());
							if (cacheBytes != null) {
								String cacheStr = new String(cacheBytes, "utf-8");
								return cacheStr;
							}
						} catch (Exception e) {
							LOG.info(e.getMessage());
						}
						return "";
					}
				});
				if (!StringUtils.isEmpty(cacheValue))
					break;
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return cacheValue;
	}

	public <T> T get(final String key, final Class<T> clazz) {
		T t = null;
		for (RedisTemplate<String, String> redisTemplate : redisTemplateList) {
			t = redisTemplate.execute(new RedisCallback<T>() {
				public T doInRedis(RedisConnection connection) throws DataAccessException {
					byte[] data = connection.get(key.getBytes());
					if (data != null)
						return new JacksonJsonRedisSerializer<T>(clazz).deserialize(data);
					return null;
				}
			});

			if (t != null)
				break;
		}
		return t;
	}
	
	public <T> Long rPush(final String key, final List<T> list, final Class<T> type, final long expire) {
		if (list == null) {
			return 0L;
		}
		return redisTemplate.execute(new RedisCallback<Long>() {

			public Long doInRedis(RedisConnection connection) throws DataAccessException {
				JacksonJsonRedisSerializer<T> serializer = new JacksonJsonRedisSerializer<T>(type);
				connection.multi();
				for (T value : list) {
					connection.rPush(key.getBytes(), serializer.serialize(value));
				}
				if (expire > 0) {
					connection.expire(key.getBytes(), expire);
				}
				connection.exec();
				return new Long(list.size());
			}

		});
	}

	/**
	 * 清空缓存
	 */
	public void flushDB() {
		for (RedisTemplate<String, String> redisTemplate : redisTemplateList) {
			try {
				redisTemplate.execute(new RedisCallback<String>() {
					public String doInRedis(RedisConnection connection) throws DataAccessException {
						connection.flushDb();
						return "ok";
					}
				});
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 添加至有序集合
	 * @param key
	 * @param score
	 * @param value
	 */
	public void zadd(final byte[] key, final double score, final byte[] value) {
		for (RedisTemplate<String, String> redisTemplate : redisTemplateList) {
			try {
				redisTemplate.execute(new RedisCallback<String>() {
					public String doInRedis(RedisConnection connection) throws DataAccessException {
						connection.zAdd(key, score, value);
						return "ok";
					}
				});
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 按条件获取有序集合元素子集
	 * @param key
	 *            有序集合key
	 * @param min
	 *            范围最小值
	 * @param max
	 *            范围最大值
	 * @param offset
	 *            从第0ffset+1个元素起
	 * @param count
	 *            返回上限
	 * @return
	 */
	public Set<byte[]> zRangeByScore(final byte[] key, final double min, final double max, final long offset,
			final long count) {
		Set<byte[]> set = null;
		for (RedisTemplate<String, String> redisTemplate : redisTemplateList) {
			try {
				set = redisTemplate.execute(new RedisCallback<Set<byte[]>>() {

					public Set<byte[]> doInRedis(RedisConnection connection) throws DataAccessException {
						return connection.zRangeByScore(key, min, max, offset, count);
					}

				});
				if (set != null) {
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return set;
	}

	/**
	 * 添加指定map至缓存
	 * @param key map唯一标识
	 * @param hashes
	 */
	@SuppressWarnings({ "unchecked", "rawtypes" })
	public void hMSet(final byte[] key, final Map<byte[], byte[]> hashes) {
		for (RedisTemplate<String, String> redisTemplate : redisTemplateList) {
			try {
				redisTemplate.execute(new RedisCallback() {
					public Object doInRedis(RedisConnection connection) throws DataAccessException {
						connection.hMSet(key, hashes);
						return null;
					}
				});

			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * 获取指定map中指定键对应的值列表
	 * @param key map的唯一标识
	 * @param fields 键数组
	 * @return 值数组
	 */
	public List<byte[]> hMGet(final byte[] key, final byte[]... fields) {
		List<byte[]> cacheValue = null;
		for (RedisTemplate<String, String> redisTemplate : redisTemplateList) {
			try {
				cacheValue = redisTemplate.execute(new RedisCallback<List<byte[]>>() {
					public List<byte[]> doInRedis(RedisConnection connection) throws DataAccessException {
						return connection.hMGet(key, fields);
					}
				});
				if (cacheValue != null) {
					break;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		return cacheValue;
	}

	// getters and setters
	public List<RedisTemplate<String, String>> getRedisTemplateList() {
		return redisTemplateList;
	}

	public void setRedisTemplateList(List<RedisTemplate<String, String>> redisTemplateList) {
		this.redisTemplateList = redisTemplateList;
	}

}
