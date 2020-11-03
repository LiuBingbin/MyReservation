package dao;

import model.User;
import java.util.List;
import dao.DaoException;

public interface UserDao extends Dao{
	//查询所有用户信息
    public List<User> listUser(String flag) throws DaoException;
    //添加用户信息
    public boolean addUser (User user) throws DaoException;
    //根据用户名查找信息
    public User findUser(String name) throws DaoException;
    //改变用户余额
    public boolean changeBal (User user) throws DaoException;
    //根据id删除信息
    public boolean delById (int id) throws DaoException;
    //更改信息
    public boolean changeUser (User user) throws DaoException;
}
