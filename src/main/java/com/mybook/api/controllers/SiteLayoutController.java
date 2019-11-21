package com.mybook.api.controllers;

import com.mybook.api.dto.SiteLayoutDTO;
import com.mybook.api.models.Role;
import com.mybook.api.models.User;
import com.mybook.api.services.UserService;
import lombok.AllArgsConstructor;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@RestController
@AllArgsConstructor
public class SiteLayoutController {
    private UserService userService;

    @GetMapping("/api/profile")
    public SiteLayoutDTO profile() {
        User currentUser = userService.getCurrentUser();
        List<Role> roles = currentUser.getRoles();
        boolean isAdmin = false;
        for (Role role : roles) {
            if (role.getName().equals("ROLE_ADMIN"))
                isAdmin = true;
        }
        SiteLayoutDTO profile = SiteLayoutDTO.builder()
                .name(currentUser.getProfile().getName())
                .surname(currentUser.getProfile().getSurname())
                .admin(isAdmin)
                .massages(true)
                .friends(true)
                .build();
        if (currentUser.getProfile().getAvatar() != null)
            profile.setAvatar("/api/profile/avatar");
        return profile;
    }

    @PostMapping("/api/profile/avatar")
    public void setAvatar(@RequestParam MultipartFile file) throws IOException {
        File path = new File(
                System.getProperty("user.home")
                        + File.separator
                        + userService.getCurrentUser().getEmail()
                        + File.separator
                        + "avatar"
        );
        if (!path.exists()) {
            path.mkdirs();
        }
//        FileUtils.cleanDirectory(path); //clear dir with avatars
        String substring = file.getOriginalFilename().substring(file.getOriginalFilename().lastIndexOf('.'));
        File pathFile = new File(path.getAbsolutePath() + File.separator + UUID.randomUUID() + substring);
        byte[] bytes = file.getBytes();
        BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(pathFile));
        stream.write(bytes);
        stream.flush();
        stream.close();
        User currentUser = userService.getCurrentUser();
        currentUser.getProfile().setAvatar(pathFile.getAbsolutePath());
        currentUser.getProfile().setUpdated(new Date());
        userService.save(currentUser);

    }

    @GetMapping("/api/profile/avatar")
    public ResponseEntity<Resource> getAvatar() {
        String filePath = userService.getCurrentUser().getProfile().getAvatar();
        File file = new File(filePath);
        Resource resource = new FileSystemResource(file);
        return ResponseEntity.ok()
                .contentType(MediaType.IMAGE_JPEG)
                .body(resource);
    }

}
