package pl.mlata.financecontrolservice.rest.service;

import static org.mockito.Mockito.when;

import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletResponseWrapper;

import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.Test;

import com.fasterxml.jackson.databind.ObjectMapper;

import global.UserRoles;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import pl.mlata.financecontrolservice.configuration.security.authentication.JwtSettings;
import pl.mlata.financecontrolservice.configuration.security.authentication.JwtTokenAuthentication;
import pl.mlata.financecontrolservice.exception.JwtExpiredTokenException;
import pl.mlata.financecontrolservice.persistance.model.User;

@Test
public class TokenAuthenticationServiceTest extends AbstractTestNGSpringContextTests {
	private TokenAuthenticationService tokenAuthenticationService;
	private JwtSettings jwtSettings;
	private ObjectMapper objectMapper;

	@Mock
	private UserService userServiceMock;

	private String header = "Authorization";

	User preparedUser;

	@BeforeClass
	public void setUpTestData() {
		MockitoAnnotations.initMocks(this);
		objectMapper = new ObjectMapper();

		prepareUserData();

		when(userServiceMock.getByEmail(preparedUser.getEmail())).thenReturn(preparedUser);
		when(userServiceMock.getCurrentUser()).thenReturn(preparedUser);

		tokenAuthenticationService = new TokenAuthenticationService(jwtSettings(), userServiceMock, objectMapper);
	}

	@Test(groups = "AddingAuthentication")
	public void whenAddingAuthentication_tokenIsAddedToResponse() throws Exception {
		MockHttpServletResponse httpResponse = new MockHttpServletResponse();
		tokenAuthenticationService.addAuthentication(httpResponse, preparedUser.getEmail());
		String headerValue = httpResponse.getHeader(header);
		Assert.assertNotNull(headerValue);
	}

	@Test(expectedExceptions = IllegalArgumentException.class, groups = "AddingAuthentication")
	public void whenAddingAuthentication_exceptionOnBlankUsername() throws Exception {
		MockHttpServletResponse httpResponse = new MockHttpServletResponse();
		tokenAuthenticationService.addAuthentication(httpResponse, "");
	}

	@Test(dependsOnGroups = "AddingAuthentication")
	public void whenGettingAuthentication_authenticationIsReturned() throws Exception {
		String validToken = buildValidJwtToken();
		JwtTokenAuthentication tokenAuth = 
				(JwtTokenAuthentication) tokenAuthenticationService.getAuthentication(validToken);
		
		Assert.assertNotNull(tokenAuth);
		Assert.assertEquals(tokenAuth.getPrincipal(), preparedUser);
		
		List<GrantedAuthority> authorities = (List<GrantedAuthority>) tokenAuth.getAuthorities();
		Assert.assertNotNull(authorities);
		Assert.assertEquals(authorities.size(), 1);
		Assert.assertEquals(authorities.get(0).getAuthority(), preparedUser.getRoles());
	}
	
	@Test(expectedExceptions = JwtExpiredTokenException.class, dependsOnGroups = "AddingAuthentication")
	public void whenGettingAuthentication_exceptionOnExpiredToken() throws Exception {
		String expiredToken = buildInvalidJwtToken();
		tokenAuthenticationService.getAuthentication(expiredToken);
	}

	private JwtSettings jwtSettings() {
		jwtSettings = new JwtSettings();
		jwtSettings.setExpirationTime(60);
		jwtSettings.setHeader(header);
		jwtSettings.setIssuer("pl.mlata");
		jwtSettings.setPrefix("Bearer ");
		jwtSettings.setSigningKey("zaq12wsxcde3");

		return jwtSettings;
	}
	
	private void prepareUserData() {
		BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

		preparedUser = new User();
		preparedUser.setEmail("TestUser@email.pl");
		preparedUser.setFirstName("Test");
		preparedUser.setLastName("User");
		preparedUser.setPassword(passwordEncoder.encode("12345"));
		preparedUser.setRoles(UserRoles.User.toString());
	}

	String buildValidJwtToken() {
		Date currentTime = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());

		Date expirationDate = Date.from(LocalDateTime.now().plusMinutes(jwtSettings.getExpirationTime())
				.atZone(ZoneId.systemDefault()).toInstant());

		String jwtBody = Jwts.builder().setSubject(preparedUser.getEmail()).setIssuer(jwtSettings.getIssuer())
				.setIssuedAt(currentTime).setExpiration(expirationDate)
				.signWith(SignatureAlgorithm.HS512, jwtSettings.getSigningKey()).compact();

		return jwtBody;
	}

	String buildInvalidJwtToken() {
		Date currentTime = Date.from(LocalDateTime.now().atZone(ZoneId.systemDefault()).toInstant());

		Date expirationDate = Date.from(LocalDateTime.now().minusMinutes(jwtSettings.getExpirationTime())
				.atZone(ZoneId.systemDefault()).toInstant());

		String jwtBody = Jwts.builder().setSubject(preparedUser.getEmail()).setIssuer(jwtSettings.getIssuer())
				.setIssuedAt(currentTime).setExpiration(expirationDate)
				.signWith(SignatureAlgorithm.HS512, jwtSettings.getSigningKey()).compact();

		return jwtBody;
	}
}
