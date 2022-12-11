package telran.java45.security;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

public class ExpiredPasswordFilter implements Filter {

	@Override
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain)
			throws IOException, ServletException {
		HttpServletRequest request = (HttpServletRequest) req;
		HttpServletResponse response = (HttpServletResponse) resp;
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		if (checkEndPoint(request.getMethod(), request.getServletPath()) && authentication != null 
				&& authentication.getPrincipal() instanceof UserProfile) {
			UserProfile userProfile = (UserProfile) authentication.getPrincipal();
			if(!userProfile.isPasswordNotExpired()) {
				response.sendError(403, "password expired");
				return;
			}
		}
		chain.doFilter(request, response);
	}

	private boolean checkEndPoint(String method, String path) {
		return !("Put".equalsIgnoreCase(method)&& path.matches("/account/password/?"));
	}

}
