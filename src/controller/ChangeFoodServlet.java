package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.FoodDao;
import dao.FoodDaoImpl;
import model.Food;

@WebServlet("/changeFood")
public class ChangeFoodServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public ChangeFoodServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		FoodDao dao = new FoodDaoImpl();
		Food food = new Food();
		PrintWriter out = response.getWriter();
		try {
			food.setId(Integer.parseInt(request.getParameter("id")));
			food.setName(request.getParameter("name"));
			food.setPrice(request.getParameter("price"));
			food.setDetail(request.getParameter("detail"));
			food.setOprice(request.getParameter("oprice"));
			food.setSales(request.getParameter("sales"));
			boolean success = dao.changeFood(food);

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
