package com.example.config;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(name = "MyServlet1", urlPatterns = "/servlet1")
public class MyServlet1 extends HttpServlet {

	private static final long serialVersionUID = -2449675904917739591L;

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("MyServlet1 in");
		resp.getWriter().print("hello servlet1");
		resp.getWriter().flush();
		resp.getWriter().close();
		System.out.println("MyServlet1 out");
	}

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		this.doPost(req, resp);
	}

}
