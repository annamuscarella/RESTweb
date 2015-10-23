package de.dhbw.meetme.servlet;

import de.dhbw.meetme.database.Transaction;
import de.dhbw.meetme.database.dao.ScoreDao;
import de.dhbw.meetme.domain.MD5;
import de.dhbw.meetme.domain.Score;
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
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Collection;
import de.dhbw.meetme.database.dao.UserDao;

/**
 *
 */
@WebServlet(name = "Registrierung2", urlPatterns = {"/registrierung2"})
public class Registrierung2 extends HttpServlet {
    /**
     * Processes requests for both HTTP
     * <code>GET</code> and
     * <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws javax.servlet.ServletException if a servlet-specific error occurs
     * @throws java.io.IOException if an I/O error occurs
     */
    private static final Logger log = LoggerFactory.getLogger(UserService.class);

    @Inject
    UserDao userDao;
    @Inject
    ScoreDao scoreDao;
    @Inject
    Transaction transaction;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {


        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();
        transaction.begin();

        try {
            String username = request.getParameter("username");
            String password = request.getParameter("password");
            String email = request.getParameter("email");
            String nation = request.getParameter("nation");
            String description = request.getParameter("description");



            log.debug("New Registration: " + username + ", " + password + ", " + email + ", " + nation + ", " + description);
            log.debug(username);
            log.debug(password);
            log.debug(email);
            log.debug(nation);
            log.debug(description);


            Collection<User> meineUsers = userDao.findByName(username);
            if (username.trim().isEmpty()) {
                out.println("<h2> Hallo bei TrueColers! Bitte registrieren Sie sich!</h2>");
            } else if (meineUsers.size() > 0) {
                    out.println("<h2>The Username: " + username + " is already taken!</h2>");
                } else {


                User a = new User();
                a.setName(username);
                a.setEmail(email);
                a.setPassword(MD5.getMD5(password));            //Verschlüsselung mit MD5 Hash des PW vor dem abspeichern
                a.setNation(nation);
                a.setDescription(description);
                Score s = new Score(username, 0);
                //a.setIdi();
                scoreDao.persist(s);
                userDao.persist(a);                             //in DB speichern

                    response.sendRedirect("LogIn2.html");
                  // response.sendRedirect("login.jsp?registry=succes");

                    out.println("<h2> Wilkommen " + username + " bei TrueColors!</h2>");
                    out.println("<a href=http://localhost:8087/meetmeserver/src/main/webapp/LogIn2.html> Hier geht's zum Login </a>");
                }

            out.println("</body>");
            out.println("</html>");
        } finally {
            if (out != null) {
                out.close();
            }
        }
        transaction.commit();
    }

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