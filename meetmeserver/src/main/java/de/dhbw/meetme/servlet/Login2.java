package de.dhbw.meetme.servlet;

import de.dhbw.meetme.database.Transaction;
import de.dhbw.meetme.domain.MD5;
import de.dhbw.meetme.domain.User;
import de.dhbw.meetme.rest.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import de.dhbw.meetme.database.dao.UserDao;

@WebServlet(name="Login2", urlPatterns ={"/login2"})
public class Login2 extends HttpServlet {
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    @Inject
    UserDao userDao;
    @Inject
    Transaction transaction;

        protected void processRequest(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {

            response.setContentType("text/html;charset=UTF-8");
            PrintWriter out = response.getWriter();
            //transaction.begin();

            out.println("<html>");
            out.println("<head><title>Login</title></head>");

            String email = request.getParameter("email");
            String password = request.getParameter("password");

            User angemeldet;
            Collection<User> meineUsers = userDao.findByEmail(email);
            angemeldet = meineUsers.iterator().next();
            out.println(angemeldet.getPassword());
            out.println(MD5.getMD5(password));
            if (email != null && !email.trim().isEmpty()) {
                if (MD5.getMD5(password).equals(angemeldet.getPassword())) {
                    out.println("<html>");
                    out.println("<head><title>Login</title></head>");
                    out.println("<h2>Hallo " + email + "!</h2>");
                    out.println("<a href=\"login\">Zurück</a>");
                    out.println("</body>");
                    out.println("</html>");

                    HttpSession session = request.getSession();                      //Beginn einer Session

                    //Eigenschaften aus DB laden und in Session Variablen speichern
                    session.setAttribute("userid", email);
                    //session.setAttribute("Vorname", angemeldet.getVorname());
                    //session.setAttribute("Nachname", angemeldet.getName());
                    log.debug(angemeldet.getName());
                    //session.setAttribute("email", angemeldet.getEmail());

                    response.sendRedirect("advisorInterface.html");
                }else {response.sendRedirect("loginPage.html");}
            }}
        // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">

        /**
         * Handles the HTTP
         * <code>GET</code> method.
         *
         * @param request  servlet request
         * @param response servlet response
         * @throws javax.servlet.ServletException if a servlet-specific error occurs
         * @throws java.io.IOException            if an I/O error occurs
         */
        @Override
        protected void doGet(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
            processRequest(request, response);
        }
        /**
         * Handles the HTTP
         * <code>POST</code> method.
         *
         * @param request  servlet request
         * @param response servlet response
         * @throws javax.servlet.ServletException if a servlet-specific error occurs
         * @throws java.io.IOException            if an I/O error occurs
         */
        @Override
        protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
            processRequest(request, response);
        }

        /**
         * Returns a short description of the servlet.
         *
         * @return a String containing servlet description
         */
        @Override
        public String getServletInfo() {
            return "Short description";
        }// </editor-fold>
}