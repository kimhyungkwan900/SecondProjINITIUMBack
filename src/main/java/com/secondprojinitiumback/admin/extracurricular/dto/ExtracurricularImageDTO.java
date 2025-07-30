package com.secondprojinitiumback.admin.extracurricular.dto;

import com.secondprojinitiumback.admin.extracurricular.domain.ExtracurricularImage;
import com.secondprojinitiumback.admin.extracurricular.domain.enums.ImgType;
import lombok.*;
import org.modelmapper.ModelMapper;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExtracurricularImageDTO {
    private Long imgId;
    private Long eduMngId;
    private String imgFilePathNm;
    private ImgType imgType;
    private int imgFileSz;

    private static ModelMapper modelMapper = new ModelMapper();

    public static ExtracurricularImageDTO of(ExtracurricularImage extracurricularImage) {
        if(extracurricularImage == null) return null;

        return modelMapper.map(extracurricularImage, ExtracurricularImageDTO.class);
    }
}
