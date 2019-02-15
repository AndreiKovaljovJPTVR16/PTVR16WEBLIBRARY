/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package servlets;

import entity.Book;
import entity.History;
import entity.Reader;
import entity.Role;
import entity.User;
import entity.UserRoles;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.List;
import javax.ejb.EJB;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import session.BookFacade;
import session.HistoryFacade;
import session.ReaderFacade;
import session.RoleFacade;
import session.UserFacade;
import session.UserRolesFacade;
import utils.Encription;



@WebServlet(name = "AdminController", loadOnStartup = 1, urlPatterns = {
    "/showListReaders",
    "/showChangePassword",
    "/showPageForGiveBook",
    "/showPageForReturnBook",
    "/showAddNewBook",
    "/showAddNewReader",
    "/giveBook",
    "/returnBook",
    "/changePassword",
    "/addNewBook",
})
public class AdminController extends HttpServlet {
    
    @EJB private BookFacade bookFacade;
    @EJB private ReaderFacade readerFacade;
    @EJB private HistoryFacade historyFacade;
    @EJB private UserFacade userFacade;
    @EJB private UserRolesFacade userFolesFacade;
    @EJB private RoleFacade roleFacade;
    
    
    @Override
    public void init() throws ServletException {
    
        List<User> listUsers = userFacade.findAll();
        if(listUsers.size() !=0){return;}
        Reader reader = new Reader("andrei.kovaljov@ivkhk.ee", "Andrei", "Kovaljov");
        readerFacade.create(reader);
        Encription encription = new Encription();
        String password = encription.getEncriptionPass("admin");
        User user = new User("admin", password, true, reader);
        userFacade.create(user);
        Role role = new Role("Administrator");
        roleFacade.create(role);
        UserRoles ur = new UserRoles();
        ur.setRole(role);
        ur.setUser(user);
        userRolesFacade.create(ur);
        role.setName("User");
        roleFacade.create(role);
        ur.setRole(role);
        ur.setUser(user);
        userRolesFacade.create(ur);
    
    
    
    
    }

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
        Encription encription = new Encription();
        Calendar c = new GregorianCalendar();
        String path = request.getServletPath();
        HttpSession session = request.getSession(false);
        if(session == null){
        
            request.setAttribute("info", "Войдите!");
            request.getRequestDispatcher("/showLogin").forward(request, response);
               
        }
        
        Boolean isRole = userRolesFacade.isRole("Administrator", regUser);
        if(!isRole){
        
            request.setAttribute("info", "Вы должны быть администратором!");
            request.getRequestDispatcher("showLogin").forward(request, response);
        
        }
        
        if(null != path)switch (path){
        
            case "/showListReaders":{
            
                List<Reader> listReaders = readerFacade.findAll();
                request.setAttribute("listReaders", listReaders);
                request.setAttribute("info", "showListReaders, привет!");
                request.getRequestDispatcher("/WEB-INF/showPageForGiveBook.jsp").forward(request, response);
                break;
            
            
            }
            case "/giveBook":{
            
                String bookId = request.getParameter("bookId");
                String readerId = request.getParameter("readerId");
                Book book = bookFacade.find(new Long(bookId));
                Reader reader = readerFacade.find(new Long(readerId));
                if(book.getCount()>0){
                
                    book.setCount(book.getCount()-1);
                    bookFacade.edit(book);
                    History history = new History(book, reader, c.getTime());
                    historyFacade.create(history);
                    request.setAttribute("info", "Книга " + book.getName() + " выдана!");
                                
                }else{
                
                    request.setAttribute("info", "Все книги выданы!");
                
                }
             
                request.getRequestDispatcher("/index.jsp").forward(request, response);
                break;
                
            }
            
            case "/showAddNewBook":
                                
                request.getRequestDispatcher("/WEB-INF/showAddNewBook.jsp").forward(request, response);
                break;
            
            case "/addNewBook":{
            
                
                String name = request.getParameter("name");
                String author = request.getParameter("author");
                String isbn = request.getParameter("isbn");
                String count = request.getParameter("count");
                Book book = new Book(isbn, name, author, new Integer(count));
                bookFacade.create(book);
                request.setAttribute("info", "Книга \""+book.getName()+"\"добавлена!");
                request.getRequestDispatcher("/index.jsp").forward(request, response);
                break;
            
            
            
            }
            
            case "/showAddNewReader":
                                
                request.getRequestDispatcher("/WEB-INF/showAddNewReader.jsp").forward(request, response);
                break;
                
            case "/showPageForReturnBook":
                
                List<History> listHistories = historyFacade.findGivenBooks();
                request.setAttribute("listHistories", listHistories);
                request.getRequestDispatcher("/WEB-INF/showReturnBook.jsp").forward(request, response);
                break;
                
            case "/returnBook":{
            
                String historyId = request.getParameter("returnHistoryId");
                History history = historyFacade.find(new Long(historyId));
                if(history == null){
                
                    request.setAttribute("info", "Не выдалось такой книги!");
                    request.getRequestDispatcher("/index.jsp").forward(request,response);
                    return;
                               
                }
                
                Book book = history.getBook();
                if(book.getQuantity()>book.getCount()){
                
                    book.setCount(book.getCount()+1);
                    bookFacade.edit(book);
                    history.setDateEnd(c.getTime());
                    historyFacade.edit(history);
                    request.setAttribute("info", "Книга "+book.getName()+" возвращена!");
                
                
                } else {
                
                    request.setAttribute("info", "Все книги уже возвращены!");
                
                } 
                
                request.getRequestDispatcher("/index.jsp").forward(request, response);
                break;
            
            }
            case "/showChangePassword":
                
                session = request.getSession(false);
                if(session == null){
                
                    request.setAttribute("info", "Вы должны войти!");
                    request.getRequestDispatcher("/showLogin.jsp").forward(request, response);
                    break;
                
                }
                
                regUser = (User) session.getAttribute("regUser");
                if(regUser == null){
                
                    request.setAttribute("info", "Вы должны войти!");
                    request.getRequestDispatcher("/showLogin.jsp").forward(request, response);
                    break;
                
                }
                
                String username = regUser.getReader().getName()+" "+regUser.getReader().getSruname();
                request.setAttribute("username", username);
                String login = regUser.getLogin();
                request.setAttribute("login", login);
                request.getRequestDispatcher("/changePassword.jsp").forward(request, response);
                break;
                
            case "/showPassword":
                
                session = request.getSession();
                regUser = (User) session.getAttribute("regUser");
                String oldPassword = request.getParameter("oldPassword");
                
                String encriptOldPassword = encription.getEcnriptionPass(oldPassword);
                if(!encriptionOldPassword.equals(regUser.getPassword())){
                
                    request.setAttribute("info", "Вы должны войти!");
                    request.getRequestDispatcher("/showLogin.jsp").forward(request, response);
                    break;
                         
                }
                
                String newPassword1 = request.getParameter("newPassword1");
                String newPassword2 = request.getParameter("newPassword2");
                if(newPassword1.equals(newPassword2)){
                
                    regUser.setPassword(encription.getEncriptionPass(newPassword1));
                    userFacade.edit(regUser);
                
                }
                
                request.setAttribute("info", "Вы успешно изменили пароль!");
                request.getRequestDispatcher("/logout");
                request.getRequestDispatcher("/showLogin.jsp").forward(request, response);
                break;
                
                                    
            }
        
        
        
        
        
        }
        
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
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
