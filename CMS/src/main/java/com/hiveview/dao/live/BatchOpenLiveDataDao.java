package main.java.com.hiveview.dao.live;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.hiveview.entity.vo.BatchOpenLiveVo;

public interface BatchOpenLiveDataDao {

	List<BatchOpenLiveVo> getBatchOpenLiveData();

	int updateType(@Param("id") int id, @Param("type") int type);
}
