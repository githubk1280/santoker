package org.springframework.samples.mvc.simple;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.WebRequest;

import java.util.Date;

@Controller
public class SimpleController {

	@RequestMapping("/simple")
	public @ResponseBody String simple() {
		return "Hello world!";
	}

	@RequestMapping("/last-modify/{now}")
	public String myHandleMethod(WebRequest webRequest, Model model,@PathVariable String now) {
		long lastModified =1L; // 1. application-specific calculation
		if("now".equals(now)){
			lastModified = new Date().getTime();
		}

		if (webRequest.checkNotModified(lastModified)) {
			// 2. shortcut exit - no further processing necessary
			return null;
		}

		// 3. or otherwise further request processing, actually preparing content
		model.addAttribute("var",lastModified);
		return "last-m";
	}

}
