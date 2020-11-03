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
import dao.OrderDao;
import dao.OrderDaoImpl;
import model.Order;
import net.sf.json.JSONArray;

@WebServlet("/searchOrder")
public class SearchOrderServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public SearchOrderServlet() {
        super();
    }
    
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	OrderDao dao = new OrderDaoImpl();
		List<Order> list;
		PrintWriter out = response.getWriter();
		try {
			list = dao.findOrder(request.getParameter("uname"));
			// 把list转成json数组
			JSONArray json = JSONArray.fromObject(list);
			out.print(json);
		} catch (DaoException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}

	

}
