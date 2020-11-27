package ru.andersen.travelagency.WEB;

import ru.andersen.travelagency.entity.Tour;
import ru.andersen.travelagency.entity.User;
import ru.andersen.travelagency.hibreq.TourHib;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Optional;

public class TourServlet extends HttpServlet {

    private TourHib tourHib;

    public void init() {
        tourHib = new TourHib();
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getServletPath();

        try {
            switch (action) {
                case "/new":
                    showNewForm(request, response);
                    break;
                case "/insert":
                    insertTour(request, response);
                    break;
                case "/delete":
                    deleteTour(request, response);
                    break;
                case "/edit":
                    showEditForm(request, response);
                    break;
                case "/update":
                    updateTour(request, response);
                    break;
                default:
                    listTour(request, response);
                    break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }

    private void listTour(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException, ServletException {
        List<Tour> listUser = tourHib.show();
        request.setAttribute("listUser", listUser);
        RequestDispatcher dispatcher = request.getRequestDispatcher("tour-list.jsp");
        dispatcher.forward(request, response);
    }

    private void showNewForm(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("tour-form.jsp");
        dispatcher.forward(request, response);
    }

    private void showEditForm(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("tour_id"));
        // mb mistake
        Optional<Tour> existingUser = tourHib.showById(id);
        RequestDispatcher dispatcher = request.getRequestDispatcher("tour-form.jsp");
        request.setAttribute("tour", existingUser);
        dispatcher.forward(request, response);

    }

    private void insertTour(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException{

        String name = request.getParameter("name");
        String summary = request.getParameter("summary");
        String description = request.getParameter("description");
        String departureDate = request.getParameter("departure_date");
        String arrivalDate = request.getParameter("arrival_date");
        double price = Double.parseDouble(request.getParameter("price"));

        Tour newTour = new Tour(name, summary, description, departureDate, arrivalDate, price);
        tourHib.save(newTour);
        response.sendRedirect("list");
    }

    private void updateTour(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("tour_id"));
        String name = request.getParameter("name");
        String summary = request.getParameter("summary");
        String description = request.getParameter("description");
        String departureDate = request.getParameter("departure_date");
        String arrivalDate = request.getParameter("arrival_date");
        double price = Double.parseDouble(request.getParameter("price"));

        Tour tour = new Tour(name, summary, description, departureDate, arrivalDate, price);

        tourHib.update(tour);
        response.sendRedirect("list");
    }

    private void deleteTour(HttpServletRequest request, HttpServletResponse response)
            throws SQLException, IOException {
        int id = Integer.parseInt(request.getParameter("tour_id"));
        tourHib.delete(id);
        response.sendRedirect("list");
    }
}
