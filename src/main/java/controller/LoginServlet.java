package controller;

import businesslayer.UserService;
import model.User;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Servlet implementation class LoginServlet
 *
 * This servlet handles user authentication by processing login requests. It validates
 * the user's credentials and, based on the user's type, redirects them to the appropriate
 * page within the application.
 *
 * The servlet interacts with the UserService to retrieve user information from the
 * database and manages user sessions by setting session attributes for authenticated
 * users. If authentication fails, it displays an error message.
 *
 * @version 1.0
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/LoginServlet"})
public class LoginServlet extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     * Validates the user's credentials and redirects them to their respective home page
     * based on their user type. If the credentials are invalid, an error message is displayed.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email");
        String password = request.getParameter("password");
        UserService userdao = new UserService();

        try {
            User user = userdao.getUserByEmail(email);
            if(user != null && user.getUserPassword().equals(password)){
                int userID = user.getUserID();
                String username = user.getUserName();
                String location = user.getUserCity();
                String userEmail = user.getUserEmail();
                String userType = user.getUserType();

                HttpSession session = request.getSession();
                session.setAttribute("username", username);
                session.setAttribute("userID", userID);
                session.setAttribute("userEmail", userEmail);
                session.setAttribute("location", location);
                session.setAttribute("userType", userType);

                if(user.getUserType().equals("consumer")){
                    session.setAttribute("home", "DiscountViewServlet");
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/DiscountViewServlet");
                    dispatcher.forward(request, response);
                } else if(user.getUserType().equals("retailer")){
                    session.setAttribute("home", "RetailerViewServlet");
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/RetailerViewServlet");
                    dispatcher.forward(request, response);
                } else if(user.getUserType().equals("charitable organization")){
                    session.setAttribute("home", "DonationViewServlet");
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/DonationViewServlet");
                    dispatcher.forward(request, response);
                } else if(user.getUserType().equals("admin")){
                    session.setAttribute("home", "AdminServlet");
                    RequestDispatcher dispatcher = request.getRequestDispatcher("/AdminServlet");
                    dispatcher.forward(request, response);
                }

            } else {
                response.setContentType("text/html;charset=UTF-8");
                try ( PrintWriter out = response.getWriter()) {
                    out.println("<!DOCTYPE html>");
                    out.println("<html>");
                    out.println("<head>");
                    out.println("<title>Username or password incorrect!</title>");
                    out.println("</head>");
                    out.println("<body>");
                    out.println("<h2>Username or password incorrect!</h2>");
                    out.println("<a href='views/login.jsp'><button>Back, try again.</button></a>");
                    out.println("</body>");
                    out.println("</html>");
                }
            }

        } catch (SQLException ex) {
            Logger.getLogger(LoginServlet.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Handles the HTTP <code>GET</code> method.
     * Delegates to the <code>processRequest</code> method to handle the request.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     * Delegates to the <code>processRequest</code> method to handle the request.
     *
     * @param request  servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException      if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing a short description of the servlet's functionality
     */
    @Override
    public String getServletInfo() {
        return "Servlet for handling user login and authentication";
    }

}