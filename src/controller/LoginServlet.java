package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dao.UserDao;
import dao.UserDaoImpl;
import model.User;

@WebServlet("/login")
public class LoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public LoginServlet() {
		super();
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		UserDao dao = new UserDaoImpl();
		List<User> list;
		PrintWriter out = response.getWriter();

		try {
			list = dao.listUser(request.getParameter("flag"));
			for (User user : list) {
				if (user.getName().equals(request.getParameter("username"))
						&& user.getPwd().equals(request.getParameter("password"))) {
					out.println(200);
					return;
				}
			}
		} catch (dao.DaoException e) {
			e.printStackTrace();
		}

	}
}