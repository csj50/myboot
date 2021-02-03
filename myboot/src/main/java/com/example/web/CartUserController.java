package com.example.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.domain.CartUser;
import com.example.domain.CartUserPage;
import com.example.service.RedisExampleService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(description = "登录状态下的购物车")
@RestController
@RequestMapping("/cartUser")
public class CartUserController {

	@Autowired
	RedisExampleService redisExampleService;

	@ApiOperation("增加购物车商品")
	@PostMapping("/addCart")
	public void addCart(CartUser cart) {
		redisExampleService.userAddCart(cart);
	}

	@ApiOperation("修改购物车数量")
	@PostMapping("/updateCart")
	public void updateCart(CartUser cart) {
		redisExampleService.userUpdateCart(cart);
	}

	@ApiOperation("删除购物车商品")
	@PostMapping("/delCart")
	public void delCart(long userId, long productId) {
		redisExampleService.userDelCart(userId, productId);
	}

	@ApiOperation("查询所有商品")
	@PostMapping("/findAll")
	public CartUserPage<CartUser> findAll(long userId) {
		return redisExampleService.findAll(userId);
	}
}
