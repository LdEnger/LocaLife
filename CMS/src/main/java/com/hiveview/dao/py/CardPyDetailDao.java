package main.java.com.hiveview.dao.py;

import com.hiveview.entity.py.CardPyDetail;
import com.hiveview.entity.py.ExportCardPy;

import java.util.ArrayList;
import java.util.List;

public interface CardPyDetailDao {
    List<CardPyDetail> getList(CardPyDetail cardPyDetail);

    int count(CardPyDetail cardPyDetail);

    int insertPyDetail(List<CardPyDetail> parm);

    ArrayList<CardPyDetail> getExportAllList(ExportCardPy exportCardPy);

    ArrayList<CardPyDetail> getExportDetailAllList(CardPyDetail cardPyDetail);

    CardPyDetail getCardPyById(Integer id);

    CardPyDetail getCardPyByOrderId(String card_order_py);

    void updateCardPyDetail(CardPyDetail cardPyDetail);
}
