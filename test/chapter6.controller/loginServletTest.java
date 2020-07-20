package chapter6.controller;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;

import chapter6.beans.User;
import chapter6.service.LoginService;

@RunWith(PowerMockRunner.class)
@PrepareForTest({LoginServlet.class})
public class LoginServletTest {

    // テスト対象クラス
    private LoginServlet servlet;

    // 依存クラス
    @Mock
    LoginService service;

    @Before
    public void setup() {
        this.servlet = new LoginServlet();
    }

    /**
     * doGet
     * @throws ServletException
     * @throws IOException
     */
    @Test
    public void doGetTest1() throws IOException, ServletException {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        RequestDispatcher rd = mock(RequestDispatcher.class);
        when(request.getRequestDispatcher("login.jsp")).thenReturn(rd);

        servlet.doGet(request, response);
    }

    /**
     * doPost
     * ユーザが存在するとき
     * @throws Exception
     */
    @Test
    public void doPostTest1() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getParameter("accountOrEmail")).thenReturn("azuma");
        when(request.getParameter("password")).thenReturn("azuma");

        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);

        User user = new User();
        when(service.login((String) any(),(String) any())).thenReturn(user);
        PowerMockito.whenNew(LoginService.class).withNoArguments().thenReturn(service);

        servlet.doPost(request, response);
    }

    /**
     * doPost
     * ユーザが存在しないとき
     * @throws Exception
     */
    @Test
    public void doPostTest2() throws Exception {
        HttpServletRequest request = mock(HttpServletRequest.class);
        HttpServletResponse response = mock(HttpServletResponse.class);
        when(request.getParameter("accountOrEmail")).thenReturn("azuma");
        when(request.getParameter("password")).thenReturn("azuma");

        HttpSession session = mock(HttpSession.class);
        when(request.getSession()).thenReturn(session);

        when(service.login((String) any(),(String) any())).thenReturn(null);
        PowerMockito.whenNew(LoginService.class).withNoArguments().thenReturn(service);

        servlet.doPost(request, response);
    }
}
