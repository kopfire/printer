package dev.kopfire.site.printer.api.controller;

import dev.kopfire.site.printer.core.model.CartridgeDTO;
import dev.kopfire.site.printer.core.service.CartridgesService;
import dev.kopfire.site.printer.core.service.ModelCartridgeService;
import dev.kopfire.site.printer.core.service.QRCodeService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.IOException;

@Controller
public class QRReaderController {

    private Logger logger = LoggerFactory.getLogger(QRReaderController.class);

    @Autowired
    private QRCodeService qrCodeService;

    @Autowired
    private CartridgesService cartridgesService;

    @Autowired
    private ModelCartridgeService modelCartridgeService;

    @GetMapping("/qr")
    public String show() {
        return "qr";
    }

    @PostMapping("/uploadQrCode")
    public String uploadQrCode(@RequestParam("qrCodeFile") MultipartFile qrCodeFile, RedirectAttributes redirectAttributes) {

        if(qrCodeFile.isEmpty()) {
            redirectAttributes.addFlashAttribute("errorMessage", "Файл не выбран.");
            return "redirect:/qr";
        }
        try {
            String qrContent = qrCodeService.decodeQR(qrCodeFile.getBytes());

            if (qrContent == null){
                redirectAttributes.addFlashAttribute("errorMessage", "Файл не содержит QR кода.");
                return "redirect:/qr";
            }

            if (!qrContent.startsWith("АГУ_Картридж")) {
                redirectAttributes.addFlashAttribute("errorMessage", "Некорректный QR код.");
                return "redirect:/qr";
            }

            CartridgeDTO cartridgeDTO = cartridgesService.getCartidge(qrContent);

            if (cartridgeDTO != null){
                redirectAttributes.addFlashAttribute("cartridge", qrContent);
                redirectAttributes.addFlashAttribute("qrContent", qrContent);
                redirectAttributes.addFlashAttribute("model", modelCartridgeService.getName(cartridgeDTO.getType_cartridge()));
                redirectAttributes.addFlashAttribute("status", cartridgeDTO.getStatus());
                if (cartridgeDTO.getStatus().equals("У пользователей"))
                    redirectAttributes.addFlashAttribute("place", cartridgeDTO.getText_status());
                return "redirect:/qr";
            }
            redirectAttributes.addFlashAttribute("new_cartridge", 1);
            redirectAttributes.addFlashAttribute("qrContent", qrContent);
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }

        return "redirect:/qr";
    }

    /*@GetMapping("/printer")
    public String show(@RequestParam(name="name", required=false, defaultValue="World") String name, Model model) {
        model.addAttribute("name", name);
        return "printer";
    }*/

}