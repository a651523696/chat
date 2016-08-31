package cn.edu.hdu.chat.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.autoconfigure.web.BasicErrorController;
import org.springframework.boot.autoconfigure.web.ErrorAttributes;
import org.springframework.boot.autoconfigure.web.ErrorProperties;
import org.springframework.http.ResponseEntity;
import org.springframework.web.servlet.ModelAndView;

public class ErrorPageController extends BasicErrorController {

	public ErrorPageController(ErrorAttributes errorAttributes) {
		super(errorAttributes);
		// TODO Auto-generated constructor stub
	}

	@Override
	public String getErrorPath() {
		// TODO Auto-generated method stub
		return super.getErrorPath();
	}

	@Override
	public ModelAndView errorHtml(HttpServletRequest request, HttpServletResponse response) {
		// TODO Auto-generated method stub
		return super.errorHtml(request, response);
	}

	@Override
	public ResponseEntity<Map<String, Object>> error(HttpServletRequest request) {
		// TODO Auto-generated method stub
		return super.error(request);
	}

	@Override
	protected ErrorProperties getErrorProperties() {
		// TODO Auto-generated method stub
		return super.getErrorProperties();
	}

}
