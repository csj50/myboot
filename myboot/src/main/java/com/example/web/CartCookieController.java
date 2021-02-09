package com.example.web;

import java.util.UUID;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.domain.CartCookie;
import com.example.domain.CartUserPage;
import com.example.service.RedisExampleService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description = "未登录状态下的购物车")
@RestController
@RequestMapping("/cartCookie")
public class CartCookieController {

	@Autowired
	RedisExampleService redisExampleService;
	
	@ApiOperation("增加购物车商品")
	@PostMapping("/addCart")
	public void addCart(HttpServletRequest request, HttpServletResponse response, CartCookie cart) {
		//获取cookie中的cartId
		String id = getCookieCartId(request, response);
		redisExampleService.cookieAddCart(cart, id);
	}
	
	@ApiOperation("修改购物车数量")
	@PostMapping("/updateCart")
	public void updateCart(HttpServletRequest request, HttpServletResponse response, CartCookie cart) {
		//获取cookie中的cartId
		String id = getCookieCartId(request, response);
		redisExampleService.cookieUpdateCart(cart, id);
	}
	
	@ApiOperation("删除购物车商品")
	@PostMapping("/delCart")
	public void delCart(HttpServletRequest request, HttpServletResponse response, String productId) {
		//获取cookie中的cartId
		String id = getCookieCartId(request, response);
		redisExampleService.cookieDelCart(productId, id);
	}
	
	@ApiOperation("查询所有商品")
	@PostMapping("/findAll")
	public CartUserPage<CartCookie> findAll(HttpServletRequest request, HttpServletResponse response) {
		//获取cookie中的cartId
		String id = getCookieCartId(request, response);
		return redisExampleService.cookieFindAll(id);
	}
	
	@ApiOperation("合并购物车到登录用户")
	@PostMapping("/mergeCart")
	public void mergeCart(HttpServletRequest request, HttpServletResponse response, long userId) {
		//获取cookie中的cartId
		String id = getCookieCartId(request, response);
		redisExampleService.cookieMergeCart(userId, id);
		//删除用户本地cookie
		Cookie cookie = new Cookie("cartId", null);
		cookie.setMaxAge(0);
		response.addCookie(cookie);
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	private String getCookieCartId(HttpServletRequest request, HttpServletResponse response) {
		Cookie[] cookies = request.getCookies();
		if (cookies != null) {
			for (Cookie cookie : cookies) {
				if (cookie.getName().equals("cartId")) {
					return cookie.getValue();
				}
			}
		}
		String id = UUID.randomUUID().toString();
		Cookie cookie = new Cookie("cartId", id);
		response.addCookie(cookie);
		return id;
	}
}
