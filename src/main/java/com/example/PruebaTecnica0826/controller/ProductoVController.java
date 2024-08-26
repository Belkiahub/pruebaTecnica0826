package com.example.PruebaTecnica0826.controller;

import com.example.PruebaTecnica0826.modelos.CategoriaV;
import com.example.PruebaTecnica0826.modelos.EtiquetaV;
import com.example.PruebaTecnica0826.modelos.ProductoV;
import com.example.PruebaTecnica0826.servicio.interfaces.ICategoriaVService;
import com.example.PruebaTecnica0826.servicio.interfaces.IProductoVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/productos")
public class ProductoVController {
    @Autowired
    private IProductoVService productoService;
    @Autowired
    private ICategoriaVService categoriaService;

    @GetMapping()
    public String index(Model model, @RequestParam("page") Optional<Integer> page, @RequestParam("size") Optional<Integer> size) {
        int currentPage = page.orElse(1) - 1;
        int pageSize = size.orElse(10);
        Pageable pageable = PageRequest.of(currentPage, pageSize);

        Page<ProductoV> productos = productoService.obtenerTodos(pageable);
        model.addAttribute("producto", productos);

        int totalPages = productos.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages)
                    .boxed()
                    .collect(Collectors.toList());
            model.addAttribute("pageNumbers", pageNumbers);
        }

        return "producto/index";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("producto", new ProductoV());
        model.addAttribute("categoria", categoriaService.listarTodosLasCategoria());
        return "producto/create";
    }

    @PostMapping("/addproductos")
    public String addProduct(ProductoV producto, Model model) {
        if (producto.getEtiquetaV() == null)
            producto.setEtiquetaV(new ArrayList<>());
        producto.getEtiquetaV().add(new EtiquetaV(producto, ""));

        if (producto.getEtiquetaV() != null) {
            Long idDet = 0l;
            for (EtiquetaV item : producto.getEtiquetaV()) {
                if (item.getId() == null || item.getId() < 1) {
                    idDet--;
                    item.setId(idDet);
                }
            }
        }

        model.addAttribute("categoria", categoriaService.listarTodosLasCategoria());

        model.addAttribute(producto);
        if (producto.getId() != null && producto.getId() > 0)
            return "producto/edit";
        else
            return "producto/create";
    }

    @PostMapping("/delproductos/{id}")
    public String delProduct(@PathVariable("id") Long id, ProductoV producto, Model model) {
        producto.getEtiquetaV().removeIf(elemento -> elemento.getId() == id);

        model.addAttribute("categoria", categoriaService.listarTodosLasCategoria());

        model.addAttribute(producto);
        if (producto.getId() != null && producto.getId() > 0)
            return "producto/edit";
        else
            return "producto/create";
    }

    @PostMapping("/save")
    public String save(ProductoV productoV, BindingResult result, Model model, RedirectAttributes attributes) {
        if (result.hasErrors()) {
            model.addAttribute(productoV);
            attributes.addFlashAttribute("error", "No se pudo guardar debido a un error.");
            return "producto/create";
        }

        if (productoV.getEtiquetaV() != null) {
            for (EtiquetaV item : productoV.getEtiquetaV()) {
                if (item.getId() != null && item.getId() < 0)
                    item.setId(null);
                item.setProductoV(productoV);
            }
        }

        if (productoV.getId() != null && productoV.getId() > 0) {
            ProductoV productoUpdate = productoService.buscarPorId(productoV.getId()).get();
            Map<Long, EtiquetaV> etiquetaData = new HashMap<>();
            if (productoUpdate.getEtiquetaV() != null) {
                for (EtiquetaV item : productoUpdate.getEtiquetaV()) {
                    etiquetaData.put(item.getId(), item);
                }
            }
            productoUpdate.setNombreV(productoV.getNombreV());
            productoUpdate.setPrecioV(productoV.getPrecioV());
            productoUpdate.setExistenciaV(productoV.getExistenciaV());
            productoUpdate.setCategoriaV(productoV.getCategoriaV());
            if (productoV.getEtiquetaV() != null) {
                for (EtiquetaV item : productoV.getEtiquetaV()) {
                    if (item.getId() == null) {
                        if (productoUpdate.getEtiquetaV() == null)
                            productoUpdate.setEtiquetaV(new ArrayList<>());
                        item.setProductoV(productoUpdate);
                        productoUpdate.getEtiquetaV().add(item);
                    } else {
                        if (etiquetaData.containsKey(item.getId())) {
                            EtiquetaV etiquetaUpdate = etiquetaData.get(item.getId());
                            etiquetaUpdate.setNombreV(item.getNombreV());
                            etiquetaData.remove(item.getId());
                        }
                    }
                }
            }
            if (!etiquetaData.isEmpty()) {
                for (Map.Entry<Long, EtiquetaV> item : etiquetaData.entrySet()) {
                    productoUpdate.getEtiquetaV().removeIf(elemento -> elemento.getId().equals(item.getKey()));
                }
            }
            productoV = productoUpdate;
        }

        productoService.crearOEditar(productoV);
        return "redirect:/productos";

    }

    @GetMapping("/details/{id}")
    public String details(@PathVariable("id") Long id, Model model) {
        ProductoV productoV= productoService.buscarPorId(id).get();
        model.addAttribute("producto", productoV);
        return "producto/details";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable("id") Long id, Model model) {

        List<CategoriaV> categoriaVS = categoriaService.listarTodosLasCategoria();
        ProductoV productoV = productoService.buscarPorId(id).get();
        model.addAttribute("categoria", categoriaVS);
        model.addAttribute("producto", productoV);
        return "producto/edit";
    }

    @GetMapping("/remove/{id}")
    public String remove(@PathVariable("id") Long id, Model model) {
        Optional<ProductoV> producto = productoService.buscarPorId(id);
        if (producto.isPresent()) {
            model.addAttribute("producto", producto.get());
            return "producto/delete";
        } else {
            return "redirect:/productos";
        }
    }

    @PostMapping("/delete")
    public String delete(ProductoV producto, RedirectAttributes attributes)  {
        productoService.eliminarPorId(producto.getId());
        attributes.addFlashAttribute("msg", "Producto eliminado correctamente");
        return "redirect:/productos";
    }

}
