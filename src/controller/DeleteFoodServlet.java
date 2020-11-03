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

@WebServlet("/deleteFood")
public class DeleteFoodServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public DeleteFoodServlet() {
        super();
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		FoodDao dao = new FoodDaoImpl();
		PrintWriter out = response.getWriter();
		try {
			boolean success = dao.delFood(Integer.parseInt(request.getParameter("id")));

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
