package controller;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dao.DaoException;
import dao.UserDao;
import dao.UserDaoImpl;
import model.User;
import net.sf.json.JSONObject;

@WebServlet("/searchUser")
public class SearchUserServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public SearchUserServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		UserDao dao = new UserDaoImpl();
		try {
			User user = dao.findUser(request.getParameter("name"));
			JSONObject obj = JSONObject.fromObject(user);
			PrintWriter out = response.getWriter();
			out.println(obj);
		} catch (DaoException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}


}
