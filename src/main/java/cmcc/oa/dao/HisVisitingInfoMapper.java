package cmcc.oa.dao;

import java.util.List;

import cmcc.oa.entity.HisVisitingInfo;

public interface HisVisitingInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(HisVisitingInfo record);

    int insertSelective(HisVisitingInfo record);

    HisVisitingInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(HisVisitingInfo record);

    int updateByPrimaryKey(HisVisitingInfo record);
    
    HisVisitingInfo selectSingle(HisVisitingInfo record);
    
    List<HisVisitingInfo> selectList(HisVisitingInfo record);
}