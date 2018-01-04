package main.java.com.hiveview.service;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.hiveview.common.ParamConstants;

/**
 * Title：token相关服务类
 * Description：
 * Company：hiveview.com
 * Author：郝伟革 
 * Email：haoweige@hiveview.com 
 * Mar 13, 2014
 */
@Service
public class TokenService {

	/**
	 * token校验
	 * @param payParams 支付参数字符串
	 * @param token	请求的token
	 * @param partnerKey 合作方私钥
	 * @return
	 */
	public boolean auth(String payParams, String token, String partnerKey) {
		if (StringUtils.isEmpty(payParams) || StringUtils.isEmpty(token) || StringUtils.isEmpty(partnerKey)) {
			return false;
		}
		String _token = generateToken(payParams, partnerKey);
		if (_token.equals(token)) {
			return true;//token一致则认证通过
		} else {
			_token = generateToken(payParams,ParamConstants.HIVE_PARTNER_KEY);
			if (_token.equals(token)) {
				return true;//token一致则认证通过
			}
		}
		return false;
	}

	/**
	 * 请求参数有效时长：同一请求参数，30分钟内有效
	 */
	private static Long interval = 30 * 60 * 1000L;

	/**
	 * 请求时效性验证
	 * 
	 * @param sendtime 客户端发来的时间
	 * @return
	 */
	public boolean isValid(Long sendtime) {
		Long nowtime = System.currentTimeMillis();
		if (Math.abs(nowtime - sendtime) < interval) {
			return true;
		}
		return false;
	}

	/**
	 * 生成token
	 * @param payParams
	 * @param partnerKey
	 * @return
	 */
	public String generateToken(String payParams, String partnerKey) {
		String plainText = payParams + "|" + partnerKey;
		return DigestUtils.md5Hex(plainText);
	}
}
