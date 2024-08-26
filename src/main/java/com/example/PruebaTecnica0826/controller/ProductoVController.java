package com.example.PruebaTecnica0826.controller;

import com.example.PruebaTecnica0826.modelos.CategoriaV;
import com.example.PruebaTecnica0826.modelos.ProductoV;
import com.example.PruebaTecnica0826.servicio.interfaces.ICategoriaVService;
import com.example.PruebaTecnica0826.servicio.interfaces.IProductoVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
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

    @PostMapping("/save")
    public String save(ProductoV productoV) {

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
