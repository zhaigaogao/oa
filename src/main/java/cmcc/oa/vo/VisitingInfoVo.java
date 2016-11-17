package cmcc.oa.vo;

import cmcc.oa.entity.VisitingInfo;

/**
 *
 * @author renlinggao
 * @Date 2016年10月25日
 */
public class VisitingInfoVo extends VisitingInfo{
	private String createUserName;
	private String createUserAllOrgName;
	private String createUserMobile;
	/**
	 * @Method 判断用户的预约来访状态
	 *         1:用户在预约状态，且在预约时间内
	 *         2:用户在预约状态，且比预约时间早
	 *         3:用户在预约状态，且比预约时间晚
	 *         4:用户不在预约状态
	 */
	private String VisitStatus;

	public String getVisitStatus() {
		return VisitStatus;
	}

	public void setVisitStatus(String visitStatus) {
		this.VisitStatus = visitStatus;
	}
	
	public String getCreateUserName() {
		return createUserName;
	}
	public void setCreateUserName(String createUserName) {
		this.createUserName = createUserName;
	}
	public String getCreateUserAllOrgName() {
		return createUserAllOrgName;
	}
	public void setCreateUserAllOrgName(String createUserAllOrgName) {
		this.createUserAllOrgName = createUserAllOrgName;
	}
	public String getCreateUserMobile() {
		return createUserMobile;
	}
	public void setCreateUserMobile(String createUserMobile) {
		this.createUserMobile = createUserMobile;
	}
	
	
}
