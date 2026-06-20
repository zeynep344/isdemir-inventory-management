package com.example.isdemir;

import com.example.isdemir.model.ColdCoil;
import com.example.isdemir.model.HotCoil;
import com.example.isdemir.model.Plate;
import com.example.isdemir.model.Product;
import com.example.isdemir.repository.ColdCoilRepository;
import com.example.isdemir.repository.HotCoilRepository;
import com.example.isdemir.repository.PlateRepository;
import com.example.isdemir.repository.ProductRepository;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final HotCoilRepository hotRepo;
    private final ColdCoilRepository coldRepo;
    private final PlateRepository plateRepo;
    private final ProductRepository productRepo;

    public ProductController(HotCoilRepository hotRepo,
                             ColdCoilRepository coldRepo,
                             PlateRepository plateRepo,
                             ProductRepository productRepo) {
        this.hotRepo = hotRepo;
        this.coldRepo = coldRepo;
        this.plateRepo = plateRepo;
        this.productRepo = productRepo;
    }

    @GetMapping
    public String list(@RequestParam(value = "type", required = false, defaultValue = "all") String type,
                       Model model) {

        Map<Integer, HotCoil> hotMap = hotRepo.findAll().stream()
                .filter(h -> h.getProductId() != null)
                .collect(Collectors.toMap(HotCoil::getProductId, h -> h));

        Map<Integer, ColdCoil> coldMap = coldRepo.findAll().stream()
                .filter(c -> c.getProductId() != null)
                .collect(Collectors.toMap(ColdCoil::getProductId, c -> c));

        Map<Integer, Plate> plateMap = plateRepo.findAll().stream()
                .filter(p -> p.getProductId() != null)
                .collect(Collectors.toMap(Plate::getProductId, p -> p));

        List<Product> allProducts = productRepo.findAll();

        String reqType = (type == null) ? "all" : type.trim().toLowerCase();
        if ("plate".equals(reqType)) reqType = "plates";

        List<Product> products;
        if ("all".equalsIgnoreCase(reqType)) {
            products = allProducts;
        } else {
            final String t = reqType;
            products = allProducts.stream()
                    .filter(p -> p.getProductType() != null
                            && p.getProductType().equalsIgnoreCase(t))
                    .collect(Collectors.toList());
        }

        LinkedHashSet<String> productTypes = new LinkedHashSet<>(Arrays.asList("hot_coil", "cold_coil", "plates"));
        allProducts.stream()
                .map(Product::getProductType)
                .filter(Objects::nonNull)
                .map(s -> s.equalsIgnoreCase("plate") ? "plates" : s)
                .forEach(productTypes::add);

        boolean hasHot   = allProducts.stream().anyMatch(p -> "hot_coil".equalsIgnoreCase(p.getProductType()));
        boolean hasCold  = allProducts.stream().anyMatch(p -> "cold_coil".equalsIgnoreCase(p.getProductType()));
        boolean hasPlate = allProducts.stream().anyMatch(p ->
                "plates".equalsIgnoreCase(p.getProductType()) || "plate".equalsIgnoreCase(p.getProductType()));

        model.addAttribute("hotMap", hotMap);
        model.addAttribute("coldMap", coldMap);
        model.addAttribute("plateMap", plateMap);
        model.addAttribute("products", products);
        model.addAttribute("productCount", products.size());

        model.addAttribute("productTypes", productTypes);
        model.addAttribute("selectedType", reqType);
        model.addAttribute("hasHot", hasHot);
        model.addAttribute("hasCold", hasCold);
        model.addAttribute("hasPlate", hasPlate);

        // ==== ROL BAYRAKLARI ====
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        boolean isAdmin    = hasRole(auth, "ADMIN")    || hasRole(auth, "ROLE_ADMIN");
        boolean isOperator = hasRole(auth, "OPERATOR") || hasRole(auth, "ROLE_OPERATOR");

        boolean canCreate = isAdmin || isOperator;  // Yeni Ürün
        boolean canEdit   = isAdmin || isOperator;  // Düzenle  (OPERATÖR İZİNLİ)
        boolean canDelete = isAdmin;                // Silme sadece ADMIN

        model.addAttribute("canCreate", canCreate);
        model.addAttribute("canEdit",   canEdit);
        model.addAttribute("canDelete", canDelete);
        // ========================

        return "product-list";
    }

    private boolean hasRole(Authentication auth, String roleOrPrefixed) {
        if (auth == null || auth.getAuthorities() == null) return false;
        return auth.getAuthorities().stream().anyMatch(a -> {
            String g = a.getAuthority();
            if (g == null) return false;
            if (g.equals(roleOrPrefixed)) return true;
            String wanted = roleOrPrefixed.startsWith("ROLE_")
                    ? roleOrPrefixed.substring(5)
                    : roleOrPrefixed;
            String normalized = g.startsWith("ROLE_") ? g.substring(5) : g;
            return normalized.equalsIgnoreCase(wanted);
        });
    }

    /** YENİ FORM: ADMIN + OPERATOR */
    @PreAuthorize("hasAnyRole('ADMIN','OPERATOR')")
    @GetMapping("/new")
    public String newProductForm(Model model) {
        model.addAttribute("product", new Product());
        return "product-form";
    }

    /** DÜZENLE FORMU: ADMIN + OPERATOR */
    @PreAuthorize("hasAnyRole('ADMIN','OPERATOR')")
    @GetMapping("/{id}/edit")
    public String editProductForm(@PathVariable Integer id, Model model, RedirectAttributes ra) {
        return productRepo.findById(id)
                .map(p -> {
                    model.addAttribute("product", p);
                    return "product-form";
                })
                .orElseGet(() -> {
                    ra.addFlashAttribute("error", "Ürün bulunamadı (ID: " + id + ").");
                    return "redirect:/products";
                });
    }

    /** ALTERNATİF DÜZENLE URL'si: ADMIN + OPERATOR */
    @PreAuthorize("hasAnyRole('ADMIN','OPERATOR')")
    @GetMapping("/edit/{id}")
    public String editProductFormAlt(@PathVariable Integer id, Model model, RedirectAttributes ra) {
        return editProductForm(id, model, ra);
    }

    /** KAYDET: ADMIN + OPERATOR */
    @PreAuthorize("hasAnyRole('ADMIN','OPERATOR')")
    @PostMapping("/save")
    @Transactional
    public String save(@ModelAttribute("product") Product product,
                       @RequestParam(value = "hotTensileStr",   required = false) Integer hotTensileStr,
                       @RequestParam(value = "hotElongation",   required = false) Integer hotElongation,
                       @RequestParam(value = "hotThickness",    required = false) Integer hotThickness,
                       @RequestParam(value = "coldYieldStr",    required = false) Integer coldYieldStr,
                       @RequestParam(value = "coldElongation",  required = false) Integer coldElongation,
                       @RequestParam(value = "coldThickness",   required = false) Integer coldThickness,
                       @RequestParam(value = "plateThickness",  required = false) Integer plateThickness,
                       @RequestParam(value = "plateLength",     required = false) Integer plateLength,
                       @RequestParam(value = "plateWidth",      required = false) Integer plateWidth,
                       RedirectAttributes ra) {

        if (product.getProductId() == null && product.getCreatedAt() == null) {
            product.setCreatedAt(LocalDateTime.now());
        }

        if (product.getProductType() != null && product.getProductType().equalsIgnoreCase("plate")) {
            product.setProductType("plates");
        }

        Product saved = productRepo.save(product);
        Integer pid = saved.getProductId();

        hotRepo.findById(pid).ifPresent(h -> hotRepo.deleteById(pid));
        coldRepo.findById(pid).ifPresent(c -> coldRepo.deleteById(pid));
        plateRepo.findById(pid).ifPresent(p -> plateRepo.deleteById(pid));

        String t = (saved.getProductType() == null) ? "" : saved.getProductType().toLowerCase();

        switch (t) {
            case "hot_coil": {
                if (hotTensileStr != null || hotElongation != null || hotThickness != null) {
                    HotCoil hc = new HotCoil();
                    hc.setProductId(pid);
                    hc.setTensileStr(hotTensileStr);
                    hc.setElongation(hotElongation);
                    hc.setThickness(hotThickness);
                    hotRepo.save(hc);
                }
                break;
            }
            case "cold_coil": {
                if (coldYieldStr != null || coldElongation != null || coldThickness != null) {
                    ColdCoil cc = new ColdCoil();
                    cc.setProductId(pid);
                    cc.setYieldStr(coldYieldStr);
                    cc.setElongation(coldElongation);
                    cc.setThickness(coldThickness);
                    coldRepo.save(cc);
                }
                break;
            }
            case "plates": {
                if (plateThickness != null || plateLength != null || plateWidth != null) {
                    Plate pl = new Plate();
                    pl.setProductId(pid);
                    pl.setThickness(plateThickness);
                    pl.setLength(plateLength);
                    pl.setWidth(plateWidth);
                    plateRepo.save(pl);
                }
                break;
            }
            default:
                break;
        }

        ra.addFlashAttribute("ok", "Ürün kaydedildi.");
        return "redirect:/products";
    }

    /** SİL: SADECE ADMIN */
    @PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/{id}/delete")
    @Transactional
    public String delete(@PathVariable Integer id, RedirectAttributes ra) {
        hotRepo.findById(id).ifPresent(h -> hotRepo.deleteById(id));
        coldRepo.findById(id).ifPresent(c -> coldRepo.deleteById(id));
        plateRepo.findById(id).ifPresent(p -> plateRepo.deleteById(id));
        productRepo.deleteById(id);
        ra.addFlashAttribute("ok", "Kayıt silindi.");
        return "redirect:/products";
    }
}
