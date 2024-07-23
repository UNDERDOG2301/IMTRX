package controladores;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import modelo.dao.ProductosDAO;
import modelo.dto.CarritoDTO;
import modelo.dto.ProductoDTO;

@WebServlet(name = "SVProductos", urlPatterns = {"/SVProductos"})
public class SVProductos extends HttpServlet {

    ProductosDAO prodao = new ProductosDAO();
    List<ProductoDTO> productos = new ArrayList<>();
    int item;
    double totalPagar = 0;
    int cantidad = 1;

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try {
            String accion = request.getParameter("accion");
            String tipoProducto = request.getParameter("tipo");
            HttpSession session = request.getSession();
            List<CarritoDTO> listaCarrito = (List<CarritoDTO>) session.getAttribute("listaCarrito");
            if (listaCarrito == null) {
                listaCarrito = new ArrayList<>();
            }

            if ("Auriculares".equalsIgnoreCase(tipoProducto)) {
                productos = prodao.listarAuriculares();
            } else if ("Almacenamiento".equalsIgnoreCase(tipoProducto)) {
                productos = prodao.listarAlmac();
            } else if ("Cooler".equalsIgnoreCase(tipoProducto)) {
                productos = prodao.listarCooler();
            } else if ("Memoriaram".equalsIgnoreCase(tipoProducto)) {
                productos = prodao.listarMRAM();
            } else if ("Mouse".equalsIgnoreCase(tipoProducto)) {
                productos = prodao.listarMouses();
            } else if ("Placamadre".equalsIgnoreCase(tipoProducto)) {
                productos = prodao.listarPLM();
            }else if ("Procesador".equalsIgnoreCase(tipoProducto)) {
                productos = prodao.listarProsc();
            }else if ("Teclado".equalsIgnoreCase(tipoProducto)) {
                productos = prodao.listarTeclados();
            }else if ("Monitor".equalsIgnoreCase(tipoProducto)) {
                productos = prodao.listarMonitores();
            }else {
                productos = new ArrayList<>();
            }

            switch (accion != null ? accion : "default") {
                case "AgregarCarrito":
                    int idp = Integer.parseInt(request.getParameter("id"));
                    ProductoDTO p = prodao.listarId(idp);
                    item = listaCarrito.size() + 1;
                    CarritoDTO car = new CarritoDTO();
                    car.setItem(item);
                    car.setIdProducto(p.getIdProducto());
                    car.setNombre(p.getNombre());
                    car.setDescripcion(p.getDescripcion());
                    car.setCantidad(cantidad);
                    car.setSubTotal(cantidad * p.getPrecio());
                    listaCarrito.add(car);
                    session.setAttribute("listaCarrito", listaCarrito);
                    session.setAttribute("contador", listaCarrito.size());
                    request.setAttribute("contador", listaCarrito.size());
                    request.setAttribute("productos", productos);
                    if ("Auriculares".equalsIgnoreCase(tipoProducto)) {
                        request.getRequestDispatcher("/vista/ComponenteAuriculares.jsp").forward(request, response);
                    } else if ("Almacenamiento".equalsIgnoreCase(tipoProducto)) {
                        request.getRequestDispatcher("/vista/ComponenteAlmacenamiento.jsp").forward(request, response);
                    } else if ("Cooler".equalsIgnoreCase(tipoProducto)) {
                        request.getRequestDispatcher("/vista/ComponenteCooler.jsp").forward(request, response);
                    } else if ("Memoriaram".equalsIgnoreCase(tipoProducto)) {
                        request.getRequestDispatcher("/vista/ComponenteMemoriaram.jsp").forward(request, response);
                    } else if ("Mouse".equalsIgnoreCase(tipoProducto)) {
                        request.getRequestDispatcher("/vista/ComponenteMouse.jsp").forward(request, response);
                    }else if ("Placamadre".equalsIgnoreCase(tipoProducto)) {
                        request.getRequestDispatcher("/vista/ComponentePlacaMadre.jsp").forward(request, response);
                    }else if ("Procesador".equalsIgnoreCase(tipoProducto)) {
                        request.getRequestDispatcher("/vista/ComponenteProcesador.jsp").forward(request, response);
                    }else if ("Teclado".equalsIgnoreCase(tipoProducto)) {
                        request.getRequestDispatcher("/vista/ComponenteTeclado.jsp").forward(request, response);
                    }else if ("Monitor".equalsIgnoreCase(tipoProducto)) {
                        request.getRequestDispatcher("/vista/Monitores.jsp").forward(request, response);
                    }
                    return;
                case "Carrito":
                    totalPagar = 0.0;
                    request.setAttribute("carrito", listaCarrito);
                    request.getRequestDispatcher("/vista/Carrito.jsp").forward(request, response);
                    return;
                case "default":
                    session.setAttribute("contador", listaCarrito.size());
                    request.setAttribute("contador", listaCarrito.size());
                    request.setAttribute("productos", productos);
                    if ("Auriculares".equalsIgnoreCase(tipoProducto)) {
                        request.getRequestDispatcher("/vista/ComponenteAuriculares.jsp").forward(request, response);
                    } else if ("Almacenamiento".equalsIgnoreCase(tipoProducto)) {
                        request.getRequestDispatcher("/vista/ComponenteAlmacenamiento.jsp").forward(request, response);
                    } else if ("Cooler".equalsIgnoreCase(tipoProducto)) {
                        request.getRequestDispatcher("/vista/ComponenteCooler.jsp").forward(request, response);
                    } else if ("Memoriaram".equalsIgnoreCase(tipoProducto)) {
                        request.getRequestDispatcher("/vista/ComponenteMemoriaram.jsp").forward(request, response);
                    }else if ("Mouse".equalsIgnoreCase(tipoProducto)) {
                        request.getRequestDispatcher("/vista/ComponenteMouse.jsp").forward(request, response);
                    }else if ("Placamadre".equalsIgnoreCase(tipoProducto)) {
                        request.getRequestDispatcher("/vista/ComponentePlacaMadre.jsp").forward(request, response);
                    }else if ("Procesador".equalsIgnoreCase(tipoProducto)) {
                        request.getRequestDispatcher("/vista/ComponenteProcesador.jsp").forward(request, response);
                    }else if ("Teclado".equalsIgnoreCase(tipoProducto)) {
                        request.getRequestDispatcher("/vista/ComponenteTeclado.jsp").forward(request, response);
                    }else if ("Monitor".equalsIgnoreCase(tipoProducto)) {
                        request.getRequestDispatcher("/vista/Monitores.jsp").forward(request, response);
                    }
                    return;
            }
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Error en el servidor: " + e.getMessage());
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }
}