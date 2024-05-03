package com.myclinic.clinic;

import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class Clinic {
    private Integer id;
    private String name;
    private String phone;
    private String location;
}
