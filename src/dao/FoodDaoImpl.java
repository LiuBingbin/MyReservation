package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import model.Food;

public class FoodDaoImpl implements FoodDao {

	@Override
	public List<Food> listFood() throws DaoException {
		String sql = "SELECT * FROM food";
		List<Food> list = new ArrayList<Food>();
		try (Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rst = pstmt.executeQuery()) {
			while (rst.next()) {
				Food food = new Food();
				food.setId(rst.getInt("id"));
				food.setName(rst.getString("name"));
				food.setPrice(rst.getString("price"));
				food.setDetail(rst.getString("detail"));
				food.setOprice(rst.getString("oprice"));
				food.setImage(rst.getString("image"));
				food.setSales(rst.getString("sales"));
				list.add(food);
			}
			return list;
		} catch (SQLException sqle) {
			System.out.println(sqle);
			return null;
		}
	}

	public Food findById(int id) throws DaoException {
		String sql = "SELECT * FROM food WHERE id =?";
		Food food = new Food();
		try (Connection conn = getConnection(); PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setInt(1, id);
			try (ResultSet rst = pstmt.executeQuery()) {
				if (rst.next()) {
					food.setId(rst.getInt("id"));
					food.setName(rst.getString("name"));
					food.setPrice(rst.getString("price"));
					food.setDetail(rst.getString("detail"));
					food.setOprice(rst.getString("oprice"));
					food.setImage(rst.getString("image"));
					food.setSales(rst.getString("sales"));
				}
				return food;
			}
		} catch (SQLException se) {
			return null;
		}
	}

	@Override
	public boolean delFood(int id) throws DaoException {
		String sql = "DELETE FROM food WHERE id=?";
		try(
		   		 Connection conn = getConnection();
		   		 PreparedStatement pstmt = conn.prepareStatement(sql))
		   	   { 
		   	     pstmt.setInt(1,id);
		   	     pstmt.executeUpdate();
		   	     return true;
		   	   }catch(SQLException se){
		   		System.out.println(se);
		   		  return false;
		   	   }
	}

	@Override
	public boolean changeFood(Food food) throws DaoException {
		String sql = "UPDATE food SET name=?,price=?,detail=?,oprice=?,sales=? WHERE id=?";
	   	   
	   	   try(
	   		 Connection conn = getConnection();
	   		 PreparedStatement pstmt = conn.prepareStatement(sql))
	   	   { 
	   	     pstmt.setString(1,food.getName());
	   	     pstmt.setString(2,food.getPrice());
	   	     pstmt.setString(3,food.getDetail());
	   	     pstmt.setString(4,food.getOprice());
	   	     pstmt.setString(5,food.getSales());
	   	     pstmt.setInt(6,food.getId());
	   	     pstmt.executeUpdate();
	   	     return true;
	   	   }catch(SQLException se){
	   		System.out.println(se);
	   		  return false;
	   	   }
	}

}
