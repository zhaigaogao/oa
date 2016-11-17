package cmcc.oa.vo;

import org.apache.commons.codec.binary.Base64;

/**
 * Created by han64 on 2016/10/24.
 */

/**
 * @author Administrator
 *
 */
public class MMS {
	private String user_number;
	private String user_name;
	/** 记录创建时间 */
	private String send_date;

	/** 预约进入时间 */
	private String start_time;
	/** 预约离开时间 */
	private String end_time;
	private String mms_data;
	private String msg;

	public String getUser_number() {
		return user_number;
	}

	public String getUser_name() {
		return user_name;
	}

	public String getSend_date() {
		return send_date;
	}

	public String getMsg() {
		return msg;
	}

	public void setUser_number(String user_number) {
		this.user_number = user_number;
	}

	public void setUser_name(String user_name) {
		this.user_name = user_name;
	}

	public void setSend_date(String send_date) {
		this.send_date = send_date;
	}

	public String getStart_time() {
		return start_time;
	}

	public void setStart_time(String start_time) {
		this.start_time = start_time;
	}

	public String getEnd_time() {
		return end_time;
	}

	public void setEnd_time(String end_time) {
		this.end_time = end_time;
	}

	public void encodeData2Mms_data(byte[] data) {
		mms_data = Base64.encodeBase64String(data);
	}

	public byte[] decodeDataFromMms_data() {
		return Base64.decodeBase64(mms_data);
	}

	public void setMsg(String msg) {
		this.msg = msg;
	}

	public void setMms_data(String mms_data) {
		this.mms_data = mms_data;
	}

	public String getMms_data() {
		return mms_data;
	}
}
