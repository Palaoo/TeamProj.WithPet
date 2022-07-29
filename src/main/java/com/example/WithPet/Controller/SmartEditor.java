package com.example.WithPet.Controller;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@JsonIgnoreProperties({"hibernateLazyInitializer", "handler"})
public class SmartEditor {
    private String sFileURL;
    private String sFileName;
    private String bNewLine;
}
