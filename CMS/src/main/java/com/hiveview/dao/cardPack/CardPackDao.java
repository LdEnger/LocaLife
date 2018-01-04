package main.java.com.hiveview.dao.cardPack;

import com.hiveview.dao.BaseDao;
import com.hiveview.entity.activity.Activity;
import com.hiveview.entity.card.BossOrderNew;
import com.hiveview.entity.card.Card;
import com.hiveview.entity.cardpack.CardPack;
import com.hiveview.entity.cardpack.ExportCardPack;
import com.hiveview.entity.count.CountRecord;
import com.hiveview.entity.count.IncomeRecord;
import com.hiveview.entity.sys.ZoneCity;
import org.apache.ibatis.annotations.Param;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public interface CardPackDao extends BaseDao<CardPack> {

    public List<CardPack> getList();
    public int qyadd(CardPack card);
    public int qyaddList(List list);
    public int updateTxcard(CardPack card);
    public ArrayList<CardPack> getExportAllList(ExportCardPack exportCard);
    public int updateExportCard(ExportCardPack exportCard);
    public int getStorgeNum(ExportCardPack exportCard);


}
