package com.brodwy.MyEcommerce.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PaymentController {

	@GetMapping("/pay")
	public String getEsewa() {
		return "/payment/pay";
	}
}
