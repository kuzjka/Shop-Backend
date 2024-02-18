package com.example.authserverresourceserversameapp.dto;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Data
public class PhotoDto {
    private long productId;

    private List<MultipartFile> photos;
}
