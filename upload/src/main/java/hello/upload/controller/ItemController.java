package hello.upload.controller;

import hello.upload.domain.Item;
import hello.upload.domain.ItemRepository;
import hello.upload.domain.UploadFile;
import hello.upload.file.FileStore;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.util.UriUtils;

import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.charset.StandardCharsets;
import java.util.List;

@Slf4j
@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemRepository itemRepository;
    private final FileStore fileStore;

    @GetMapping("/items/new")
    public String newItem(@ModelAttribute ItemForm itemForm) {
        return "item-form";
    }

    @PostMapping("/items/new")
    public String newItemPost(@ModelAttribute ItemForm itemForm, RedirectAttributes redirectAttributes) throws IOException {
        UploadFile attachFile = fileStore.storeFile(itemForm.getAttachFile());
        List<UploadFile> storedImageFiles = fileStore.storeFiles(itemForm.getImageFiles());

        Item item = new Item();
        item.setItemName(itemForm.getItemName());
        item.setAttachFile(attachFile);
        item.setImageFiles(storedImageFiles);
        itemRepository.save(item);

        redirectAttributes.addAttribute("itemId", item.getId());

        return "redirect:/items/{itemId}";
    }

    @GetMapping("/items/{id}")
    public String items(@PathVariable Long id, Model model) {
        Item item = itemRepository.findById(id);
        model.addAttribute("item", item);
        return "item-view";
    }

    @GetMapping("/images/{fileName}")
    @ResponseBody
    public Resource getImage(@PathVariable String fileName) throws MalformedURLException {
        //"file:D:/uploadFiles/UUID.png"
        return new UrlResource("file:" + fileStore.getFullPath(fileName)); // ?????? ?????????
    }

    @GetMapping("/attach/{itemId}")
    public ResponseEntity<Resource> download(@PathVariable Long itemId) throws MalformedURLException {
        Item item = itemRepository.findById(itemId);
        String storeFileName = item.getAttachFile().getStoreFileName(); // ????????? ????????? ??? ????????? ??????
        String uploadFileName = item.getAttachFile().getUploadFileName(); // ?????? ????????? ???????????? ??????
        log.info("uploadFileName = {}", uploadFileName);

        String encodedUploadFileName = UriUtils.encode(uploadFileName, StandardCharsets.UTF_8); // ????????? ????????? ??? "??????"?????? ????????? ????????? ?????? ?????? ??????

        UrlResource urlResource = new UrlResource("file:" + fileStore.getFullPath(storeFileName)); // ?????? ?????????
        String contentDisposition = "attachment; filename=\"" + encodedUploadFileName + "\""; // ????????? ???????????? ?????? ??? ??????

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, contentDisposition)
                .body(urlResource);
    }
}
