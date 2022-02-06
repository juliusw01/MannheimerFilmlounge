package de.wi2020sebgruppe4.KinoTicketRes;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Locale;
import java.util.Map;

import javax.servlet.AsyncContext;
import javax.servlet.DispatcherType;
import javax.servlet.FilterChain;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletInputStream;
import javax.servlet.ServletOutputStream;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;

import de.wi2020sebgruppe4.KinoTicketRes.config.SecurityConfig;

@SpringBootTest
@TestPropertySource(locations="classpath:test.properties")
class KinoTicketResApplicationTests {
	
	@Test
	void mainTest() {
		//KinoTicketResApplication.main(new String[] {"localhost"});
		
	}
	
	
	@Test
	void testSecurityConfig() {
		SecurityConfig s = new SecurityConfig();
		ServletRequest req = new ServletRequest() {
			
			@Override
			public AsyncContext startAsync(ServletRequest servletRequest, ServletResponse servletResponse)
					throws IllegalStateException {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public AsyncContext startAsync() throws IllegalStateException {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public void setCharacterEncoding(String env) throws UnsupportedEncodingException {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void setAttribute(String name, Object o) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void removeAttribute(String name) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public boolean isSecure() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean isAsyncSupported() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public boolean isAsyncStarted() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public ServletContext getServletContext() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public int getServerPort() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public String getServerName() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getScheme() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public RequestDispatcher getRequestDispatcher(String path) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public int getRemotePort() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public String getRemoteHost() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getRemoteAddr() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getRealPath(String path) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public BufferedReader getReader() throws IOException {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getProtocol() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String[] getParameterValues(String name) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Enumeration<String> getParameterNames() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Map<String, String[]> getParameterMap() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getParameter(String name) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Enumeration<Locale> getLocales() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Locale getLocale() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public int getLocalPort() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public String getLocalName() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getLocalAddr() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public ServletInputStream getInputStream() throws IOException {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public DispatcherType getDispatcherType() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getContentType() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public long getContentLengthLong() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public int getContentLength() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public String getCharacterEncoding() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Enumeration<String> getAttributeNames() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Object getAttribute(String name) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public AsyncContext getAsyncContext() {
				// TODO Auto-generated method stub
				return null;
			}
		};
		HttpServletResponse r = new HttpServletResponse() {
			
			@Override
			public void setLocale(Locale loc) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void setContentType(String type) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void setContentLengthLong(long length) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void setContentLength(int len) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void setCharacterEncoding(String charset) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void setBufferSize(int size) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void resetBuffer() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void reset() {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public boolean isCommitted() {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public PrintWriter getWriter() throws IOException {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public ServletOutputStream getOutputStream() throws IOException {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Locale getLocale() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getContentType() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getCharacterEncoding() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public int getBufferSize() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public void flushBuffer() throws IOException {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void setStatus(int sc, String sm) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void setStatus(int sc) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void setIntHeader(String name, int value) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void setHeader(String name, String value) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void setDateHeader(String name, long date) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void sendRedirect(String location) throws IOException {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void sendError(int sc, String msg) throws IOException {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void sendError(int sc) throws IOException {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public int getStatus() {
				// TODO Auto-generated method stub
				return 0;
			}
			
			@Override
			public Collection<String> getHeaders(String name) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public Collection<String> getHeaderNames() {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String getHeader(String name) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String encodeUrl(String url) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String encodeURL(String url) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String encodeRedirectUrl(String url) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public String encodeRedirectURL(String url) {
				// TODO Auto-generated method stub
				return null;
			}
			
			@Override
			public boolean containsHeader(String name) {
				// TODO Auto-generated method stub
				return false;
			}
			
			@Override
			public void addIntHeader(String name, int value) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void addHeader(String name, String value) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void addDateHeader(String name, long date) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void addCookie(Cookie cookie) {
				// TODO Auto-generated method stub
				
			}
		};
		FilterChain chain = new FilterChain() {
			
			@Override
			public void doFilter(ServletRequest request, ServletResponse response) throws IOException, ServletException {
				// TODO Auto-generated method stub
				
			}
		};
				
		try {
			s.doFilter(req, r, chain);
		} catch (IOException | ServletException e) {
			e.printStackTrace();
		}
	}
}
