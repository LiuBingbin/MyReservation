package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.UserDao;
import dao.UserDaoImpl;
import model.User;

@WebServlet("/changeBalance")
public class ChangeBalanceServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
  
    public ChangeBalanceServlet() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserDao dao = new UserDaoImpl();
		User user = new User();
		PrintWriter out = response.getWriter();
		try {
			user.setBalance(request.getParameter("balance"));
			user.setName(request.getParameter("name"));
			boolean success = dao.changeBal(user);

			if (success) {
				out.println(200);
			} else {
				System.out.println(500);
			}
		} catch (Exception e) {
			out.println(e);
			out.println(500);
		}
	}

}
