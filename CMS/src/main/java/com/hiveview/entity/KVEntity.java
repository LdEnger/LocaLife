package main.java.com.hiveview.entity;

/**
 * kv实体
 * @author hiveview
 *
 */
public class KVEntity {
	public String key;
	public String name;
	public String appendData;  //额外数据，json格式

	public String getKey() {
		return key;
	}

	public String getName() {
		return name;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getAppendData() {
		return appendData;
	}

	public void setAppendData(String appendData) {
		this.appendData = appendData;
	}

}
