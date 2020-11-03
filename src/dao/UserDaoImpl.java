package dao;

import java.util.*;
import java.sql.*;
import model.User;

public class UserDaoImpl implements UserDao {
	//查询所有用户信息
	public List<User> listUser(String flag) throws DaoException {
		String sql = (flag.equals("user"))?  "SELECT * FROM user" : "SELECT * FROM admin";
		List<User> list = new ArrayList<User>();
		try (
				Connection conn = getConnection();
				PreparedStatement pstmt = conn.prepareStatement(sql);
				ResultSet rst = pstmt.executeQuery()) {
			while (rst.next()) {
				User user = new User();
				user.setId(rst.getInt("id"));
				user.setName(rst.getString("name"));
				user.setPwd(rst.getString("pwd"));
				user.setTel(rst.getString("tel"));
				user.setMail(rst.getString("mail"));
				user.setBalance(rst.getString("balance"));
				list.add(user);
			}
			return list;
		} catch (SQLException sqle) {
			System.out.println(sqle);
			return null;
		}
	}

	//添加一条用户信息
	public boolean addUser(User user) throws DaoException {
		String sql = "INSERT INTO user (id,name,pwd,tel,mail) VALUES(null,?,?,?,?)";
	   	   
	   	   try(
	   		 Connection conn = getConnection();
	   		 PreparedStatement pstmt = conn.prepareStatement(sql))
	   	   { 
	   	     pstmt.setString(1,user.getName());
	   	     pstmt.setString(2,user.getPwd());
	   	     pstmt.setString(3,user.getTel());
	   	     pstmt.setString(4,user.getMail());
	   	     pstmt.executeUpdate();
	   	     return true;
	   	   }catch(SQLException se){
	   		System.out.println(se);
	   		  return false;
	   	   }
	}

	public User findUser(String name) throws DaoException {
		String sql = "SELECT balance FROM user WHERE name =?";
		User user = new User();
		try (Connection conn = getConnection(); 
			PreparedStatement pstmt = conn.prepareStatement(sql)) {
			pstmt.setString(1, name);
			try (ResultSet rst = pstmt.executeQuery()) {
				if (rst.next()) {
					user.setBalance(rst.getString("balance"));
				}
				return user;
			}
		} catch (SQLException se) {
			return null;
		}
	}

	public boolean changeBal(User user) throws DaoException {
		String sql = "UPDATE user SET balance=? WHERE name=?";
	   	   
	   	   try(
	   		 Connection conn = getConnection();
	   		 PreparedStatement pstmt = conn.prepareStatement(sql))
	   	   { 
	   	     pstmt.setString(1,user.getBalance());
	   	     pstmt.setString(2,user.getName());
	   	     pstmt.executeUpdate();
	   	     return true;
	   	   }catch(SQLException se){
	   		System.out.println(se);
	   		  return false;
	   	   }
	}

	@Override
	public boolean delById(int id) throws DaoException {
		String sql = "DELETE FROM user WHERE id=?";
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
	public boolean changeUser(User user) throws DaoException {
		String sql = "UPDATE user SET name=?,tel=?,mail=?,balance=? WHERE id=?";
	   	   
	   	   try(
	   		 Connection conn = getConnection();
	   		 PreparedStatement pstmt = conn.prepareStatement(sql))
	   	   { 
	   	     pstmt.setString(1,user.getName());
	   	     pstmt.setString(2,user.getTel());
	   	     pstmt.setString(3,user.getMail());
	   	     pstmt.setString(4,user.getBalance());
	   	     pstmt.setInt(5,user.getId());
	   	     pstmt.executeUpdate();
	   	     return true;
	   	   }catch(SQLException se){
	   		System.out.println(se);
	   		  return false;
	   	   }
	}

}
