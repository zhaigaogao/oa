package cmcc.oa.dao;

import java.util.List;
import cmcc.oa.entity.ProcessInfo;

public interface ProcessInfoMapper {
    int deleteByPrimaryKey(Long id);

    int insert(ProcessInfo record);

    int insertSelective(ProcessInfo record);

    ProcessInfo selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(ProcessInfo record);

    int updateByPrimaryKey(ProcessInfo record);
    
    ProcessInfo selectSingle(ProcessInfo record);
    
    List<ProcessInfo> selectList(ProcessInfo record);
}