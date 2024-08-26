package com.example.PruebaTecnica0826.controller;

import com.example.PruebaTecnica0826.modelos.CategoriaV;
import com.example.PruebaTecnica0826.servicio.implementacion.CategoriaVService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/categorias")
public class CategoriaVController {
    @Autowired
    private CategoriaVService categoriaVService;

    @GetMapping
    public String listarMarcas(Model model) {
        List<CategoriaV> categoriaVS = categoriaVService.obtenerTodos();
        model.addAttribute("categorias", categoriaVS);
        return "categoria/listar";
    }

    @GetMapping("/crear")
    public String mostrarFormularioCreacion(Model model) {
        model.addAttribute("marca", new CategoriaV());
        return "categoria/form";
    }

    @PostMapping("/guardar")
    public String guardarMarca(@ModelAttribute CategoriaV categoriaV) {
        categoriaVService.crearOEditar(categoriaV);
        return "redirect:/categorias";
    }

    @GetMapping("/editar/{id}")
    public String mostrarFormularioEdicion(@PathVariable Long id, Model model) {
        Optional<CategoriaV> marcaOpt = categoriaVService.buscarPorId(id);
        if (marcaOpt.isPresent()) {
            model.addAttribute("categorias", marcaOpt.get());
            return "categoria/editar";
        }
        return "redirect:/categorias";
    }

    @GetMapping("/eliminar/{id}")
    public String eliminarConfirmacion(@PathVariable("id") Long id, Model model) {
        var categoria = categoriaVService.buscarPorId(id).orElseThrow(() -> new RuntimeException("Categoria no encontrada"));
        model.addAttribute("categorias", categoria);
        return "categoria/eliminar";
    }

    @PostMapping("/delete")
    public String eliminar(@RequestParam("id") Long id) {
        categoriaVService.eliminarPorId(id);
        return "redirect:/categorias";
    }

    @GetMapping("/detalle/{id}")
    public String verDetalle(@PathVariable("id") Long id, Model model) {
        var categoria = categoriaVService.buscarPorId(id).orElseThrow(() -> new RuntimeException("Marca no encontrada"));
        model.addAttribute("categorias", categoria);
        return "categoria/detalle";
    }
}
