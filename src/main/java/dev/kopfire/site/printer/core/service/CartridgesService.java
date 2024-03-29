package dev.kopfire.site.printer.core.service;

import dev.kopfire.site.printer.core.model.CartridgeDTO;
import dev.kopfire.site.printer.core.model.HousingsDTO;
import dev.kopfire.site.printer.core.model.TypesCartridgesDTO;
import dev.kopfire.site.printer.db.entity.Cartridge;
import dev.kopfire.site.printer.db.entity.Offices;
import dev.kopfire.site.printer.db.entity.TypesCartridges;
import dev.kopfire.site.printer.db.repository.CartridgesRepository;
import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CartridgesService {

    private ModelMapper modelMapper = new ModelMapper();

    private final CartridgesRepository cartridgesRepository;

    public CartridgesService(CartridgesRepository cartridgesRepository) {
        this.cartridgesRepository = cartridgesRepository;
    }

    public void addCartridge(CartridgeDTO cartridge) {

        Cartridge cartridgesNew = modelMapper.map(cartridge, Cartridge.class);
        cartridgesRepository.save(cartridgesNew);
    }

    public void changeCartridge(CartridgeDTO cartridge) {
        Cartridge cartridgesOld = cartridgesRepository.findByQR(cartridge.getText_qr()).get(0);

        cartridgesOld.setStatus(cartridge.getStatus());
        cartridgesOld.setText_status(cartridge.getText_status());
        cartridgesOld.setOffice(modelMapper.map(cartridge.getOffice(), Offices.class));

        cartridgesRepository.save(cartridgesOld);
    }

    public void changeCartridgeFull(CartridgeDTO cartridge, Long id) {
        Cartridge cartridgesOld = cartridgesRepository.findByID(id);

        cartridgesOld.setText_qr(cartridge.getText_qr());
        cartridgesOld.setType_cartridge(modelMapper.map(cartridge.getType_cartridge(), TypesCartridges.class));
        cartridgesOld.setStatus(cartridge.getStatus());
        cartridgesOld.setText_status(cartridge.getText_status());
        cartridgesOld.setOffice(modelMapper.map(cartridge.getOffice(), Offices.class));

        cartridgesRepository.save(cartridgesOld);
    }

    public CartridgeDTO getCartridge(String text_qr) {
        List<Cartridge> cartridgeList = cartridgesRepository.findByQR(text_qr);
        if (cartridgeList.size() == 0)
            return null;
        return modelMapper.map(cartridgeList.get(0), CartridgeDTO.class);
    }

    public List<CartridgeDTO> findAll() {
        return modelMapper.map(cartridgesRepository.findAll(Sort.by(Sort.Direction.ASC, "id")), new TypeToken<List<CartridgeDTO>>() {}.getType());
    }

    public void deleteCartridge(Long id) {
        cartridgesRepository.delete(cartridgesRepository.getReferenceById(id));
    }

    public List<CartridgeDTO> findByTypeCartridges(TypesCartridgesDTO typesCartridgesDTO) {
        return modelMapper.map(cartridgesRepository.findByTypeCartridge(modelMapper.map(typesCartridgesDTO, TypesCartridges.class)), new TypeToken<List<CartridgeDTO>>() {}.getType());
    }
}
