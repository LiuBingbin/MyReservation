package dao;

import java.util.List;
import dao.DaoException;
import model.Food;

public interface FoodDao extends Dao{
	//查询所有菜品信息
    public List<Food> listFood() throws DaoException;
    //根据id查找菜品
    public Food findById(int id) throws DaoException;
  //根据id删除菜品
    public boolean delFood(int id) throws DaoException;
  //更改信息
    public boolean changeFood (Food food) throws DaoException;
}
