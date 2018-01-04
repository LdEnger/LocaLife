package main.java.com.hiveview.dao.py;

import com.hiveview.entity.py.CardPy;

import java.util.List;

public interface CardPyDao {

    List<CardPy> getList(CardPy cardPy);

    int count(CardPy cardPy);

    int pyaddList(CardPy cardPy);
}
