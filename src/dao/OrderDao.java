package dao;

import java.util.List;
import model.Order;

public interface OrderDao extends Dao{
	//添加订单
	public boolean addOrder (Order order) throws DaoException;
	//通过名字查询
	public List<Order> findOrder(String uname) throws DaoException;
	//查询所有订单
	public List<Order> showOrder() throws DaoException;
	//删除订单
	public boolean delOrder (int queue) throws DaoException;
}
