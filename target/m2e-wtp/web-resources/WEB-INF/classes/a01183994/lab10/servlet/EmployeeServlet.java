package a01183994.lab10.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import a01183994.data.Employee;
import a01183994.data.service.EmployeeService;
import a01183994.database.util.ErrorCode;

import java.io.IOException;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;


@WebServlet(urlPatterns = "")
public class EmployeeServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    private EmployeeService employeeController;

    @Override
    public void init() throws ServletException {
        super.init();
        String url = getServletContext().getInitParameter("db.url");
        String username = getServletContext().getInitParameter("db.username");
        String password = getServletContext().getInitParameter("db.password");

        createConnection(url, username, password);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("delete".equalsIgnoreCase(action)) {
            handleDelete(request, response);
        } else {
            handleAdd(request, response);
        }
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        handleGet(req, resp);
    }

    /**
     * Handles the deletion of an employee.
     */
    private void handleDelete(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            String deleteId = request.getParameter("deleteId");
            ErrorCode result = employeeController.deleteEmployee(deleteId);

            // Set delete result attributes
            request.setAttribute("deleteResultCode", result.getCode());
            request.setAttribute("deleteResultDescription", result.getDescription());

        } catch (Exception e) {
            request.setAttribute("deleteResultCode", ErrorCode.DELETE_UNSUCCESSFUL.getCode());
            request.setAttribute("deleteResultDescription", ErrorCode.DELETE_UNSUCCESSFUL.getDescription());
        }

        // Always fetch employees list for display
        try {
            List<Employee> employees = employeeController.getEmployees();
            request.setAttribute("employees", employees);
        } catch (Exception ex) {
            request.setAttribute("error", "Error getting the list of employees");
        }

        // Forward to JSP
        request.getRequestDispatcher("/index.jsp").forward(request, response);
    }

    /**
     * Handles adding a new employee.
     */
    private void handleAdd(HttpServletRequest request, HttpServletResponse response)
            throws IOException {
        try {
            String id = request.getParameter("id");
            String firstName = request.getParameter("firstName");
            String lastName = request.getParameter("lastName");
            LocalDate dob = LocalDate.parse(request.getParameter("dob"));

            Employee employee = new Employee(id, firstName, lastName, dob);

            ErrorCode result = employeeController.validateAndAddEmployee(employee);

            // Store result in session
            storeSessionAttributes(request, "resultCode", result.getCode(), "resultDescription",
                    result.getDescription());

            if (result == ErrorCode.SUCCESS_ADD) {
                request.getSession().setAttribute("success", true);
            }

        } catch (Exception e) {
            storeSessionAttributes(request, "resultCode", ErrorCode.INVALID_DATA.getCode(),
                    "resultDescription", ErrorCode.INVALID_DATA.getDescription());
        }

        // Redirect to avoid form resubmission
        response.sendRedirect(request.getContextPath() + "/");
    }

    /**
     * Handles displaying employees and search functionality.
     */
    private void handleGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        // Handle delete messages
        transferSessionAttributesToRequest(req, "deleteResultCode", "deleteResultDescription");

        // Handle search requests
        String searchId = req.getParameter("searchId");
        if (searchId != null && !searchId.trim().isEmpty()) {
            handleSearch(req, searchId);
        }

        // Fetch and set all employees
        try {
            List<Employee> employees = employeeController.getEmployees();
            req.setAttribute("employees", employees);
        } catch (Exception e) {
            req.setAttribute("error", "Error fetching employees.");
        }

        // Handle add messages
        transferSessionAttributesToRequest(req, "resultCode", "resultDescription");

        // Forward to JSP
        req.getRequestDispatcher("/index.jsp").forward(req, resp);
    }

    /**
     * Handles searching for an employee by ID.
     */
    private void handleSearch(HttpServletRequest req, String searchId) {
        try {
            Employee employee = employeeController.findEmployee(searchId.toUpperCase());
            if (employee != null) {
                req.setAttribute("searchResult",
                        employee.getFirstName() + " " + employee.getLastName());
                req.setAttribute("searchResultCode", ErrorCode.SUCCESS_FIND.getCode());
                req.setAttribute("searchResultDescription", ErrorCode.SUCCESS_FIND.getDescription());
            } else {
                req.setAttribute("searchResultCode", ErrorCode.NO_MATCH.getCode());
                req.setAttribute("searchResultDescription", ErrorCode.NO_MATCH.getDescription());
            }
        } catch (Exception e) {
            req.setAttribute("searchResultCode", ErrorCode.INVALID_DATA.getCode());
            req.setAttribute("searchResultDescription", ErrorCode.INVALID_DATA.getDescription());
        }
    }

    /**
     * Creates a connection to the database and initializes the EmployeeService.
     */
    private void createConnection(String url, String username, String password) {
        System.out.println("Connecting to the database...");
        try {
            employeeController = new EmployeeService(url, username, password);
        } catch (IOException | SQLException e) {
            System.out.println("Error connecting to the database: " + e.getMessage());
            throw new RuntimeException(e); // Fail fast if connection cannot be established
        }
    }

    /**
     * Transfers session attributes to the current request and clears them from the session.
     */
    private void transferSessionAttributesToRequest(HttpServletRequest req,
                                                    String... attributeNames) {
        for (String attributeName : attributeNames) {
            Object value = req.getSession().getAttribute(attributeName);
            if (value != null) {
                req.setAttribute(attributeName, value);
                req.getSession().removeAttribute(attributeName);
            }
        }
    }

    /**
     * Stores multiple attributes in the session.
     */
    private void storeSessionAttributes(HttpServletRequest request,
                                        Object... keyValuePairs) {
        for (int i = 0; i < keyValuePairs.length; i += 2) {
            String key = (String) keyValuePairs[i];
            Object value = keyValuePairs[i + 1];
            request.getSession().setAttribute(key, value);
        }
    }
}
