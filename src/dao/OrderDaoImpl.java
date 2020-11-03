package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Order;

public class OrderDaoImpl implements OrderDao {

	public boolean addOrder(Order order) throws DaoException {
		String sql = "INSERT INTO orders (id,uname,foodid,foodnum,fooddate,queue,total) VALUES(null,?,?,?,?,?,?)";

		try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, order.getUname());
			pstmt.setInt(2, order.getFoodid());
			pstmt.setInt(3, order.getFoodnum());
			pstmt.setString(4, order.getFooddate());
			pstmt.setInt(5, order.getQueue());
			pstmt.setString(6, order.getTotal());
			pstmt.executeUpdate();
			return true;
		} catch (SQLException se) {
			System.out.println(se);
			return false;
		}
	}

	@Override
	public List<Order> findOrder(String name) throws DaoException {
		String sql = "SELECT * FROM orders WHERE uname = ?";
		List<Order> list = new ArrayList<Order>();
		try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, name);
			try (ResultSet rst = pstmt.executeQuery()) {
				while (rst.next()) {
					Order order = new Order();
					order.setId(rst.getInt("id"));
					order.setUname(rst.getString("uname"));
					order.setFoodid(rst.getInt("foodid"));
					order.setFoodnum(rst.getInt("foodnum"));
					order.setFooddate(rst.getString("fooddate"));
					order.setQueue(rst.getInt("queue"));
					order.setTotal(rst.getString("total"));
					list.add(order);
				}
				return list;
			}
		} catch (SQLException sqle) {
			System.out.println(sqle);
			return null;
		}
	}

	@Override
	public List<Order> showOrder() throws DaoException {
		String sql = "SELECT * FROM orders";
		List<Order> list = new ArrayList<Order>();
		try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			try (ResultSet rst = pstmt.executeQuery()) {
				while (rst.next()) {
					Order order = new Order();
					order.setId(rst.getInt("id"));
					order.setUname(rst.getString("uname"));
					order.setFoodid(rst.getInt("foodid"));
					order.setFoodnum(rst.getInt("foodnum"));
					order.setFooddate(rst.getString("fooddate"));
					order.setQueue(rst.getInt("queue"));
					order.setTotal(rst.getString("total"));
					list.add(order);
				}
				return list;
			}
		} catch (SQLException sqle) {
			System.out.println(sqle);
			return null;
		}
	}

	@Override
	public boolean delOrder(int queue) throws DaoException {
		String sql = "DELETE FROM orders WHERE queue=?";
		try(
		   		 Connection conn = getConnection();
		   		 PreparedStatement pstmt = conn.prepareStatement(sql))
		   	   { 
		   	     pstmt.setInt(1,queue);
		   	     pstmt.executeUpdate();
		   	     return true;
		   	   }catch(SQLException se){
		   		System.out.println(se);
		   		  return false;
		   	   }
	}

}
