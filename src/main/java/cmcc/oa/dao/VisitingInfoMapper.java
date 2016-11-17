package cmcc.oa.dao;

import java.util.List;
import java.util.Map;

import cmcc.oa.entity.VisitingInfo;
import cmcc.oa.vo.VisitingInfoVo;

public interface VisitingInfoMapper {
	int deleteByPrimaryKey(Long id);

	int insert(VisitingInfo record);

	int insertSelective(VisitingInfo record);

	VisitingInfo selectByPrimaryKey(Long id);

	int updateByPrimaryKeySelective(VisitingInfo record);

	int updateByPrimaryKey(VisitingInfo record);

	VisitingInfo selectSingle(VisitingInfo record);

	List<VisitingInfo> selectList(VisitingInfo record);

	void batchInsert(List<VisitingInfo> infos);

	void updateByParameters(Map<String, Object> params);

	VisitingInfoVo findByCodeId(VisitingInfo record);
}