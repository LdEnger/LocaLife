package com.hiveview.util;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.springframework.beans.factory.annotation.Qualifier;

import com.hiveview.pay.entity.PayTypeEnum;

/**
 * Title：订单类型处理
 * Description：
 * Company：hiveview.com
 * Author：孙辉 
 * Email：sunhui@hiveview.com 
 * 2014-3-18
 */
@Qualifier
public class PayTypeEnumHandler extends BaseTypeHandler<PayTypeEnum> {
	private Class<PayTypeEnum> type;

	private PayTypeEnum[] enums;

	public PayTypeEnumHandler() {
	}

	/**
	 * 设置配置文件设置的转换类以及枚举类内容，供其他方法更便捷高效的实现
	 * @param type 配置文件中设置的转换类
	 */
	public PayTypeEnumHandler(Class<PayTypeEnum> type) {
		if (type == null)
			throw new IllegalArgumentException("Type argument cannot be null");
		this.type = type;
		this.enums = type.getEnumConstants();
		if (this.enums == null)
			throw new IllegalArgumentException(type.getSimpleName() + " does not represent an enum type.");
	}

	@Override
	public PayTypeEnum getNullableResult(ResultSet rs, String columnName) throws SQLException {
		// 根据数据库存储类型决定获取类型，本例子中数据库中存放INT类型
		int i = rs.getInt(columnName);
		if (rs.wasNull()) {
			return null;
		} else {
			// 根据数据库中的code值，定位WalletStatusEnum子类
			return locateWalletStatusEnum(i);
		}
	}

	@Override
	public PayTypeEnum getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
		// 根据数据库存储类型决定获取类型，本例子中数据库中存放INT类型
		int i = rs.getInt(columnIndex);
		if (rs.wasNull()) {
			return null;
		} else {
			// 根据数据库中的code值，定位WalletStatusEnum子类
			return locateWalletStatusEnum(i);
		}
	}

	@Override
	public PayTypeEnum getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
		// 根据数据库存储类型决定获取类型，本例子中数据库中存放INT类型
		int i = cs.getInt(columnIndex);
		if (cs.wasNull()) {
			return null;
		} else {
			// 根据数据库中的code值，定位WalletStatusEnum子类
			return locateWalletStatusEnum(i);
		}
	}

	@Override
	public void setNonNullParameter(PreparedStatement ps, int i, PayTypeEnum parameter, JdbcType jdbcType)
			throws SQLException {
		if (jdbcType.equals(JdbcType.INTEGER)) {
			ps.setInt(i, parameter.getId());
		} else {
			ps.setString(i, parameter.getTypeName());
		}
	}

	/**
	 * 枚举类型转换，由于构造函数获取了枚举的子类enums，让遍历更加高效快捷
	 * @param code 数据库中存储的自定义code属性
	 * @return code对应的枚举类
	 */
	private PayTypeEnum locateWalletStatusEnum(int code) {
		for (PayTypeEnum status : enums) {
			if (status.getId() == code) {
				return status;
			}
		}
		throw new IllegalArgumentException("未知的枚举类型：" + code + ",请核对" + type.getSimpleName());
	}
}
