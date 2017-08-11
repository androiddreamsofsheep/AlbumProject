package edu.webapde.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;

import edu.webapde.bean.Album;
import edu.webapde.service.AlbumService;

/**
 * Servlet implementation class AlbumController
 */
@WebServlet(urlPatterns={"/albums", "/deletealbum", "/addalbum", "/ajax"})
@MultipartConfig
public class AlbumController extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public AlbumController() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// response.getWriter().append("Served at: ").append(request.getContextPath());
		
		String path = request.getServletPath();
		System.out.println(path);
		
		switch(path){
			case "/albums" : 
				getAllAlbums(request, response);
				break;
			case "/deletealbum" :
				deleteAlbum(request, response);
				break;
			case "/addalbum":
				addAlbum(request, response);
				break;
			case "/ajax":
				// sample ajax code
				
				// this ff commented out block will just return "Hello from server" in the $.ajax success function
				// response.getWriter().write("Hello from server!");
				
				// this ff code returns objects -- something needed when we want to load a list or an individual object via ajax
				// let's create the object we want to send back first
				Album a = new Album();
				a.setName("ajax album");
				a.setDescription("descr");
				
				// then we use Gson to convert object into a JSON string
				// you can do this manually, but this will be easier for us right now
				Gson g = new Gson();
				String jsonString = g.toJson(a);
				// don't forget to set the content type to json!
				response.setContentType("application/json");
				// finally return the json string
				response.getWriter().write(jsonString);
				break;
		}
		
	}

	private void addAlbum(HttpServletRequest request, HttpServletResponse response) throws IOException {
		// TODO Auto-generated method stub
		// get params
		String name = request.getParameter("name");
		String description = request.getParameter("description");
		boolean privacy 
			= Boolean.parseBoolean(request.getParameter("privacy"));
		
		// do business logic
		Album a = new Album();
		a.setName(name);
		a.setDescription(description);
		a.setPrivacy(privacy);
		AlbumService.addAlbum(a);
		
		
		// foward to view
		response.sendRedirect("");
	}

	private void deleteAlbum(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// get params from user
		int id = Integer.parseInt(request.getParameter("id"));
		// do business logic
		AlbumService.deleteAlbum(id);
		// create view for user (forward to view)
		// getAllAlbums(request, response);
		response.sendRedirect("");
	}

	private void getAllAlbums(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		// get inputs from the user
		
		// we do business logic: get all albums
		List<Album> albums = AlbumService.getAllAlbums();
		
		// forward it to a view
		request.setAttribute("albums", albums);
		System.out.println("Asdas");
		request.getRequestDispatcher("index.jsp")
			.forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
		
	}

}
