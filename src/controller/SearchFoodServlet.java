package controller;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.DaoException;
import dao.FoodDao;
import dao.FoodDaoImpl;
import model.Food;
import net.sf.json.JSONObject;


@WebServlet("/searchFood")
public class SearchFoodServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    
    public SearchFoodServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		response.setContentType("text/json;charset=UTF-8");
		response.setCharacterEncoding("UTF-8");
		FoodDao dao = new FoodDaoImpl();
		try {
			Food food = dao.findById(Integer.parseInt(request.getParameter("id")));
			JSONObject obj = JSONObject.fromObject(food);
			PrintWriter out = response.getWriter();
			out.println(obj);
		} catch (DaoException e) {
			// TODO 自动生成的 catch 块
			e.printStackTrace();
		}
	}


}
