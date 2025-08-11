package com.secondprojinitiumback.admin.extracurricular.domain;

import com.secondprojinitiumback.admin.extracurricular.domain.enums.ImgType;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name ="extracurricular_image")
@Getter @Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ExtracurricularImage {

    @Id
    @Column(name = "IMG_ID")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imgId;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "edu_mng_id", nullable = false)
    private ExtracurricularProgram extracurricularProgram; // 비교과 프로그램 ID (FK)

    @Column(name = "img_file_path_nm")
    private String imgFilePathNm; // 이미지 파일 경로

    @Column(name = "img_type")
    @Enumerated(EnumType.STRING)
    private ImgType imgType; // 이미지 타입

    @Column(name = "img_file_sz")
    private int imgFileSz; // 이미지 파일 크기 (바이트 단위)


}
