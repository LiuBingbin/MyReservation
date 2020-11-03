package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dao.OrderDao;
import dao.OrderDaoImpl;

@WebServlet("/deleteOrder")
public class DeleteOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public DeleteOrderServlet() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		OrderDao dao = new OrderDaoImpl();
		PrintWriter out = response.getWriter();
		try {
			boolean success = dao.delOrder(Integer.parseInt(request.getParameter("queue")));

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
