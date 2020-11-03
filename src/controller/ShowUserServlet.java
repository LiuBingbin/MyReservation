package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DaoException;
import dao.UserDao;
import dao.UserDaoImpl;
import model.User;
import net.sf.json.JSONArray;

@WebServlet("/showUser")
public class ShowUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public ShowUserServlet() {
        super();
    }


	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/json;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		
		UserDao dao = new UserDaoImpl();
		List<User> list;
		PrintWriter out = response.getWriter();
		try {
			list = dao.listUser("user");
			JSONArray jsonUser = JSONArray.fromObject(list);
			out.print(jsonUser);
		} catch (DaoException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}



}
