package org.appa.planning.util;

import java.security.Principal;
import java.util.List;

import org.springframework.flex.security3.SpringSecurityLoginCommand;
import org.springframework.security.authentication.AuthenticationManager;

public class MyLoginCommand extends SpringSecurityLoginCommand{

	//@Autowired
	//private UserContext userContext;

	public MyLoginCommand(AuthenticationManager authManager) {
		super(authManager);
		//		List<LogoutHandler> logoutHandlers = new ArrayList<LogoutHandler>();
		//		logoutHandlers.add(new SecurityContextLogoutHandler());
		//		setLogoutHandlers(logoutHandlers);
	}

	@Override
	public Principal doAuthentication(String username, Object credentials) {
		Principal principal = super.doAuthentication(username, credentials);
		//TODO voir pour récupérer le bon user
		//userContext.setUtilisateur(null);
		//userContext.setAuthentication(SecurityContextHolder.getContext().getAuthentication());
		return principal;
	}

	@Override
	public boolean doAuthorization(Principal principal, List roles) {
		return super.doAuthorization(principal, roles);
	}

	@Override
	public boolean logout(Principal principal) {
		//FlexClientScope.removeCurrentUserContext();
		return super.logout(principal);
	}
}
