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
import model.Order;

@WebServlet("/addOrder")
public class AddOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public AddOrderServlet() {
        super();
    }


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		OrderDao dao = new OrderDaoImpl();
		Order order = new Order();
		PrintWriter out = response.getWriter();
		try {
			order.setUname(request.getParameter("uname"));
			order.setFoodid(Integer.parseInt(request.getParameter("foodid")));
			order.setFoodnum(Integer.parseInt(request.getParameter("foodnum")));
			order.setFooddate(request.getParameter("fooddate"));
			order.setQueue(Integer.parseInt(request.getParameter("queue")));
			order.setTotal(request.getParameter("total"));
			boolean success = dao.addOrder(order);

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
